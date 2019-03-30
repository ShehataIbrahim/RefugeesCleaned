/*
 *Copyright (C) Hitachi, Ltd. All rights reserved.
 *
 * プロジェクト名　：
 *   PeOPLe基盤開発
 *
 * 機能仕様　：
 *
 * パラメータのコーリングシーケンス　：
 *
 * 備考　：
 *   なし
 *
 * 履歴　：
 *   日付			バージョン			Ｐ票番号				 内容
 *   2018/02/02		00.01								 新規作成
 */
package jp.co.people.core.app.services;

import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.DataConflictException;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.exceptions.NotRegisteredAnchorResourceException;
import jp.co.people.core.app.services.model.ConsolidationItemDto;
import jp.co.people.core.app.services.model.ConsolidationPriorityDto;
import jp.co.people.core.app.services.model.IssueAnchorResourceResult;
import jp.co.people.core.app.utilities.DateTimeUtil;
import jp.co.people.core.app.utilities.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 集約処理のサービス
 */
@Service
public class ConsolidateService extends BaseService {
	//! リソースキー変換や発行機能を提供するサービス
	@Autowired
	private AnchorResourcesService anchorResourcesService;
	
	private final String REQUEST_KEY_RESOURCE_KEY = "resource_key";
	private final String API_NAME = "consolidate";
	
	// [{0}]件目の個人データ処理中にエラーが発生しました。
	private final String MID_E20005 = "E20005";

	//! 登録元取得SQL
	private final static String SQL_GET_RESOURCE_TYPE
		= "SELECT\n"
		+ "    resource_type\n"
		+ "FROM\n"
		+ "    auth_keys\n"
		+ "WHERE\n"
		+ "    auth_key = :auth_key\n"
		+ ";"
		;
	
	//! 集約マスタ取得SQLの基本部分
	private final static String SQL_GET_CONSOLIDATION_ITEMS_BASE
		= "SELECT\n"
		+ "    consolidation_items.from_resource_type,\n"
		+ "    consolidation_items.from_item_name,\n"
		+ "    consolidation_items.from_is_reference_datetime,\n"
		+ "    consolidation_items.to_db_name,\n"
		+ "    consolidation_items.to_resource_type,\n"
		+ "    consolidation_items.to_table_name,\n"
		+ "    consolidation_items.to_item_class_code,\n"
		+ "    consolidation_items.to_item_name,\n"
		+ "    consolidation_priorities.priority\n"
		+ "FROM\n"
		+ "    consolidation_items\n"
		+ "LEFT JOIN\n"
		+ "    consolidation_priorities\n"
		+ "ON\n"
		+ "    consolidation_items.to_db_name = consolidation_priorities.db_name\n"
		+ "AND consolidation_items.to_table_name = consolidation_priorities.table_name\n"
		+ "WHERE\n"
		+ "    consolidation_items.from_resource_type = :from_resource_type\n"
		+ " 	AND consolidation_items.to_db_name is not null\n"
		;
	/**
	 * 集約マスタ取得SQLのオプション条件(集約先リソース種別(大分類)：省略された場合は付与しない)
     * 集約先リソース種別を指定した場合、基準日時項目も取得できるようにする。
     */
	private final static String SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE_LARGE
		= "AND (   SPLIT_PART(consolidation_items.to_resource_type, '#', 1) = :to_resource_type_large\n"
		+ "     OR consolidation_items.from_is_reference_datetime = 'Y')\n";
	//! 集約マスタ取得SQLのオプション条件(集約先リソース種別(中分類)：省略された場合は付与しない)
	private final static String SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE_MIDDLE
		= "AND ((    SPLIT_PART(consolidation_items.to_resource_type, '#', 1) = :to_resource_type_large\n"
		+ "      AND SPLIT_PART(consolidation_items.to_resource_type, '#', 2) = :to_resource_type_middle\n"
		+ "     ) OR consolidation_items.from_is_reference_datetime = 'Y')"
		+ "";
	//! 集約マスタ取得SQLのオプション条件(集約先リソース種別(小分類⑨：省略された場合は付与しない)
	private final static String SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE
		= "AND (consolidation_items.to_resource_type = :to_resource_type\n"
		+ "     OR consolidation_items.from_is_reference_datetime = 'Y')";
	//! 集約マスタのORDER BY部分
	private final static String SQL_GET_CONSOLIDATIOn_ITEMS_ORDER_BY
		= "ORDER BY\n"
		+ "    consolidation_priorities.priority ASC,\n"
		+ "    consolidation_items.to_item_class_code ASC,\n"
		+ "    consolidation_items.to_item_name ASC\n"
		+ ";"
		;
	
	//! 集約優先順位マスタ取得SQL
	private final static String SQL_GET_CONSOLIDATION_PRIORITIES_BASE
		= "SELECT\n"
		+ "    db_name,\n"
		+ "    table_name,\n"
		+ "    resource_type\n"
		+ "FROM\n"
		+ "    consolidation_priorities\n"
		;
	private final static String SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_LARGE
		= "WHERE\n"
		+ "    SPLIT_PART(consolidation_priorities.resource_type, '#', 1) = :resource_type_large\n"
		;
	private final static String SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_MIDDLE
		= "WHERE\n"
		+ "    SPLIT_PART(consolidation_priorities.resource_type, '#', 1) = :resource_type_large\n"
		+ "AND SPLIT_PART(consolidation_priorities.resource_type, '#', 2) = :resource_type_middle\n"
		;
	private final static String SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_SMALL
		= "WHERE\n"
		+ "    consolidation_priorities.resource_type = :resource_type\n"
		;
	private final static String SQL_GET_CONSOLIDATION_PRIORITIES_ORDER_BY
		= "ORDER BY\n"
		+ "    priority ASC\n"
		+ ";"
		;
	
	private final static String SQL_DELETE
		= "DELETE FROM {0}"
	    + " WHERE resource_key = :resource_key"
	    + " AND reference_datetime = :reference_datetime"
	    + " AND derived_from = :derived_from"
	    + ";"
	    ;
	
	
	//! 集約データ登録SQL
	private final static String SQL_INSERT
		= "INSERT INTO {0} ("
		+ "    resource_key,"     
		+ "    reference_datetime,"       
		+ "    item_class_code,"      
		+ "    derived_from,"     
		+ "    json_data,"        
		+ "    created_by,"       
		+ "    created_at,"       
		+ "    updated_by,"       
		+ "    updated_at"       
		+ ") VALUES ("        
		+ "    :resource_key,"        
		+ "    :reference_datetime,"      
		+ "    :item_class_code,"     
		+ "    :derived_from,"        
		+ "    :json_data::jsonb,"       
		+ "    ''consolidate'',"
		+ "    current_timestamp,"        
		+ "    ''consolidate'',"        
		+ "    current_timestamp"     
		+ ");"        
		;
	
	/**
	 * 集約処理
	 * @throws SQLException
	 * @throws Exception
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public List<String> consolidate(String resourceTypeTo, String derivedFrom, List<ConsolidationItemDto> consolidationItems, JSONArray data)
			throws SQLException, Exception {
		List<String> resourceKeys = new ArrayList<String>();
		
		for (int i = 0; i < data.length(); i++) {
			JSONObject personData = data.getJSONObject(i);
			JSONObject bindParameters = personData.getJSONObject("bind_parameters");
			
			// 名寄せ先個人リソースキー
			UUID personResourceKey = getResourceKeyFromString(personData.getString(REQUEST_KEY_RESOURCE_KEY)); 
			
			// 6-2) インデックス情報発行機能を呼び出してアンカーキー及び蓄積サーバー用リソースキーを割り当てる。
			IssueAnchorResourceResult result = this.anchorResourcesService.issue(derivedFrom, personResourceKey, API_NAME);
			
			// 6-3) [出力データ].[リソースキー群]に返却されたリソースキーを追加する。
			resourceKeys.add(result.getResourceKey().toString());
			
			// 7) 基準日時の生成
			// 集約マスタで基準日時項目となっている項目名をキーとして、バインドパラメータから値を取得
			String referenceDateTimeItemName = getReferenceDateTimeItemName(consolidationItems);
			Date referenceDateTime = DateTimeUtil.toDate(bindParameters.getString(referenceDateTimeItemName));
			
			// 8) [入力パラメータ].[処理方法]による処理分岐
			if (personData.getString("method").equals("update")) {
				this.delete(resourceTypeTo, personResourceKey, referenceDateTime, derivedFrom);
			}

			// 9) データの登録処理
			try {
				this.insert(result.getResourceKey(), referenceDateTime, derivedFrom, consolidationItems, personData);
			} catch (DuplicateKeyException e) {
				// [{0}]件目の個人データ処理中にエラーが発生しました。
				String message = this.messages.get(MID_E20005, i + 1)
							   + this.messages.get(SystemConstants.MID_E00016);
				// データが重複しています。
				throw new DataConflictException(SystemConstants.MID_E00016, message);
			}
		}
		
		return resourceKeys;
	}


	/**
	 * 認証キーマスタから、指定された認証キーをもとに、登録元となるリソース種別を返す。
	 * 
	 * @param authKey 認証キー。
	 * @return リソース種別の文字列。認証キーにリソース種別が関連付けられていない場合は null を返す。
	 * @throws Exception
	 */
	public String getResourceType(UUID authKey) throws SQLException {
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
		
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("auth_key", authKey);
		
		List<Map<String, Object>> rows = template.queryForList(SQL_GET_RESOURCE_TYPE, params);
		if (rows.size() == 0) {
			return null; // 認証が通っている時点であり得ない。認証が通った瞬間に認証キーが消された等。
		} else if (rows.get(0).get("resource_type") == null) {
			return null;
		} else {
			return rows.get(0).get("resource_type").toString();
		}
	}
	
	
	/**
	 * 集約マスタを登録優先順位の昇順で取得する。
	 * 
	 * @param resourceTypeFrom 集約元リソース種別
	 * @param resourceTypeTo   集約先リソース種別
	 * 
	 * @return レコードセット
	 */
	public List<ConsolidationItemDto> getConsolidationItems(String resourceTypeFrom, String resourceTypeTo)
			throws SQLException {
		RowMapper<ConsolidationItemDto> mapper = new BeanPropertyRowMapper<ConsolidationItemDto>(ConsolidationItemDto.class);
		StringBuilder 					sql = new StringBuilder(SQL_GET_CONSOLIDATION_ITEMS_BASE);
		NamedParameterJdbcTemplate		template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
	
		// バインドパラメータ設定
		MapSqlParameterSource 		params = new MapSqlParameterSource().addValue("from_resource_type", resourceTypeFrom);
		
		// 集約先リソースタイプが指定されている場合、条件とバインドパラメータを追加
		if (resourceTypeTo != null && resourceTypeTo.isEmpty() == false) {
			String[] classes = resourceTypeTo.split("#");
			
			if (classes.length == 1) {	// 大分類のみを指定
				sql.append(SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE_LARGE);
				params.addValue("to_resource_type_large", resourceTypeTo);
			} else if (classes.length == 2) { // 中分類まで指定
				sql.append(SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE_MIDDLE);
				params.addValue("to_resource_type_large", classes[0]);
				params.addValue("to_resource_type_middle", classes[1]);
			} else {
				sql.append(SQL_GET_CONSOLIDATION_ITEMS_CONDITION_TO_RESOURCE_TYPE);
				params.addValue("to_resource_type", resourceTypeTo);
			}
		}
		
		// ORDER BYを付与
		sql.append(SQL_GET_CONSOLIDATIOn_ITEMS_ORDER_BY);
		
		return template.query(sql.toString(), params, mapper);
	}
	
	
	/**
	 * 集約優先順位マスタを優先順に取得する。
	 * 
	 * @return レコードセット
	 */
	public List<ConsolidationPriorityDto> getConsolidationPriorities(String resourceTypeTo) throws SQLException {
		RowMapper<ConsolidationPriorityDto> mapper = new BeanPropertyRowMapper<ConsolidationPriorityDto>(ConsolidationPriorityDto.class);
		NamedParameterJdbcTemplate		    template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
		MapSqlParameterSource				params = new MapSqlParameterSource();
		
		StringBuilder 	sql = new StringBuilder(SQL_GET_CONSOLIDATION_PRIORITIES_BASE);
		
		// 集約先リソースタイプが指定されている場合、条件とバインドパラメータを追加
		if (StringUtils.isNullOrEmpty(resourceTypeTo) == false) {
			String[] classes = resourceTypeTo.split("#");
			
			if (classes.length == 1) {	// 大分類のみを指定
				sql.append(SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_LARGE);
				params.addValue("resource_type_large", resourceTypeTo);
			} else if (classes.length == 2) { // 中分類まで指定
				sql.append(SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_MIDDLE);
				params.addValue("resource_type_large", classes[0]);
				params.addValue("resource_type_middle", classes[1]);
			} else {
				sql.append(SQL_GET_CONSOLIDATION_PRIORITIES_RESOURCE_TYPE_SMALL);
				params.addValue("resource_type", resourceTypeTo);
			}
		}
		
		sql.append(SQL_GET_CONSOLIDATION_PRIORITIES_ORDER_BY);

		return template.query(sql.toString(), params, mapper);
	}
	
	
	/**
	 * 文字列型のリソースキーからUUID型のリソースキーに変換する。nullまたは空文字列の場合はnullを返す。
	 * @param resourceKey
	 * @return
	 */
	public static UUID getResourceKeyFromString(String resourceKey) {
		if (resourceKey == null || resourceKey.isEmpty()) {
			return null;
		}
		else {
			return UUID.fromString(resourceKey);
		}
	}
	
	
	/**
	 * 基準日時項目が "Y" になっている集約元項目名を取得する。
	 * @param consolidationItems 集約マスタ
	 * @return 基準日時項目が "Y" になっている集約元項目名。なかった場合はnullを返す。
	 */
	public static String getReferenceDateTimeItemName(List<ConsolidationItemDto> consolidationItems) {
		for (ConsolidationItemDto row : consolidationItems) {
			if (row.isReferenceDatetime()) {
				return row.getFromItemName();
			}
		}
		
		return null;
	}
	
	
	/**
	 * データ削除処理
	 * 
	 * @param resourceTypeTo	 	集約先リソース種別
	 * @param resourceKeyTo	 		名寄せ先個人リソースキー
	 * @param referenceDateTime		基準日時
	 * @param derivedFrom			登録元
	 * 
	 * @return データ削除件数
	 * @throws Exception 
	 * @throws InputParametersException 
	 */
	public int delete(String resourceTypeTo, UUID resourceKeyTo, Date referenceDateTime, String derivedFrom) throws InputParametersException, Exception {
		int deleteCount = 0;
		
		// 2) 集約優先順位マスタを取得する。
		List<ConsolidationPriorityDto> consolidationPriorities = this.getConsolidationPriorities(resourceTypeTo);
		
		// 3) 集約優先順位マスタの件数分、下記処理を繰り返す。
		for (ConsolidationPriorityDto row : consolidationPriorities) {
			// 3-1) バインド変数の値を作成
			// 3-1-1) リソースキー変換機能を使用して、[入力パラメータ].[名寄せ先個人リソースキー]を集約先リソース種別用に変換する。
			UUID resourceKey = this.anchorResourcesService.convert(resourceKeyTo, row.getResourceType());
			
			// 3-1-2) 変換後リソースキーが null の場合、以降の処理をスキップして次のレコードへ。
			if (resourceKey == null) {
				continue;
			}
			
			// 3-2) SQL文作成
			// 3-3) SQL実行
			NamedParameterJdbcTemplate	template = this.dataSourceManager.getNamedParameterJdbcTemplate(row.getDbName());
			MapSqlParameterSource 		params = new MapSqlParameterSource()
					.addValue("resource_key", resourceKey)
					.addValue("reference_datetime", referenceDateTime)
					.addValue("derived_from", derivedFrom);
			
			deleteCount += template.update(MessageFormat.format(SQL_DELETE, row.getTableName()), params);
		}
		
		return deleteCount;
	}

	
	/**
	 * データ登録処理
	 * 
	 * @param referenceDateTime 	基準日時
	 * @param derivedFrom 			登録元
	 * @param consolidationItems	集約マスタ
	 * @param personData			個人一人分のデータ
	 * 
	 * @return データ登録件数
	 * @throws Exception 
	 * @throws NotRegisteredAnchorResourceException 
	 * @throws InputParametersException 
	 */
	public int insert(UUID issuedResourceKey, Date referenceDateTime, String derivedFrom, List<ConsolidationItemDto> consolidationItems, JSONObject personData)
			throws InputParametersException, NotRegisteredAnchorResourceException, Exception {
		UUID		resourceKey		  = null;	// 集約先リソースキー 
		String      resourceType	  = "";		// 集約先リソースキーのリソース種別
		String 		toItemClassCode   = consolidationItems.get(0).getToItemClassCode();	// 処理中の集約先項目分類コード
		JSONObject 	jsonData		  = new JSONObject();	// JSONオブジェクト
		int         insertCount       = 0;					// データ登録件数
		JSONObject	bindParameters	  = personData.getJSONObject("bind_parameters");
		
		// 2) 集約マスタの件数分、下記処理を繰り返す。
		for (int i = 0; i < consolidationItems.size(); i++) {
			ConsolidationItemDto row = consolidationItems.get(i);
			
			// 2-1) 集約先が設定されていない場合、処理をスキップし、次のレコードへ。
			String toItemName = row.getToItemName();
			if (StringUtils.isNullOrEmpty(toItemName) || StringUtils.isNullOrEmpty(row.getToResourceType())) {
				continue;
			}
			
			// 2-2) [i].[集約元項目名]をキーに[入力パラメータ].[データ].[バインドパラメータ]の値を取得
			Object itemValue = bindParameters.opt(row.getFromItemName());
			
			// 2-3) [項目値]が null または空文字列の場合、以降の処理をスキップし、処理中のデータを初期化後、次の集約マスタレコードへ。
			if (itemValue == null || (itemValue instanceof String && StringUtils.isNullOrEmpty((String)itemValue))) {
				// 処理中の項目分類と同じ間、スキップする。
				while (i + 1 < consolidationItems.size() && toItemClassCode.equals(consolidationItems.get(i + 1).getToItemClassCode())) {
					i++;
				}
				jsonData = new JSONObject();
				toItemClassCode = i + 1 < consolidationItems.size() ? consolidationItems.get(i + 1).getToItemClassCode() : null;
				continue;
			}
			
			// 2-4) JSONデータを作成
			jsonData.put(toItemName, itemValue);
			
			// 2-5) [処理中の集約先項目分類コード]と[i].[集約先項目分類コード]が異なる場合、データの登録処理を行う。
			String nextToItemClassCode = i + 1 < consolidationItems.size() ? consolidationItems.get(i + 1).getToItemClassCode() : null;
			if (toItemClassCode.equals(nextToItemClassCode) == false) {
				// 2-5-1) 処理中の集約先リソース種別と、レコードのリソース種別が異なる場合、新規リソースキーを発行する。
				if (resourceType.equals(row.getToResourceType()) == false) {
					resourceType = row.getToResourceType();
					resourceKey  = this.anchorResourcesService.issue(resourceType, issuedResourceKey, API_NAME).getResourceKey();
				}
				
				// 2-5-2) SQL文生成
				NamedParameterJdbcTemplate	template = this.dataSourceManager.getNamedParameterJdbcTemplate(row.getToDbName());
				MapSqlParameterSource 		params = new MapSqlParameterSource()
						.addValue("resource_key", resourceKey)
						.addValue("reference_datetime", referenceDateTime)
						.addValue("item_class_code", toItemClassCode)
						.addValue("derived_from", derivedFrom)
						.addValue("json_data", jsonData.toString());
				// 2-5-3) SQL実行
				String sql = MessageFormat.format(SQL_INSERT, row.getToTableName());
				insertCount = template.update(sql, params);
				
				// 2-5-4) 変数初期化
				jsonData = new JSONObject();
				toItemClassCode = nextToItemClassCode;

			}
		}
		
		return insertCount;
	}
}
