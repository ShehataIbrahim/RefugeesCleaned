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
 *   2018/02/01		00.01								 新規作成
 */

package jp.co.people.core.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.postgresql.util.PGobject;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.people.core.app.common.GenericAcquisitionConstants;
import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.NotExistsApiException;
import jp.co.people.core.app.utilities.JsonUtils;

/**
 * <PRE>
 * クラス名：
 *   汎用データ取得クラス
 *
 * 機能説明：
 *   SQLを用いた汎用的なデータ取得を行う
 * </PRE>
 */
@Service
public class GenericAcquisitionService extends BaseService {
	
	/** SQLテンプレート取得SQL */
	private static final String GET_SQL_TEMPLATE_INFO_SQL = "select main.sql, main.db_name, main.response_type from provisional_sql_templates main where main.name = :name;";
	
	/** SQLテンプレート情報のSQL名 */
	private static final String SQL_TEMPLATE_INFO_SQL_NAME = "name";
	/** SQLテンプレート情報のSQL */
	private static final String SQL_TEMPLATE_INFO_SQL = "sql";
	/** SQLテンプレート情報のDB名 */
	private static final String SQL_TEMPLATE_INFO_DB_NAME = "db_name";
	/** SQLテンプレート情報のレスポンス種別 */
	private static final String SQL_TEMPLATE_INFO_RESPONSE_TYPE = "response_type";
	
	/** レスポンス種別:json */
	private static final String RESPONSE_TYPE_JSON = "json";
	/** レスポンス種別:recordset */
	@SuppressWarnings("unused")
	private static final String RESPONSE_TYPE_RECORDSET = "recordset";
	
	/** レスポンス種別:jsonにおける固定の列名 */
	private static final String RESPONSE_TYPE_JSON_FIXED_CLOUMN_NAME = "json_data";

	/**
	 * 汎用データ取得を実行する
	 * 
	 * @param sqlName SQL名
	 * @param bindParameter リクエストパラメータのバインドパラメータ
	 * @return 出力データの設定する取得結果
	 * @throws NotExistsApiException SQL名のSQLテンプレートが存在しない
	 * @throws Exception 予期しない例外
	 */
	public Object executeQuery(String sqlName, JSONObject bindParameter) throws NotExistsApiException, Exception {
		// SQL文の取得
		Map<String, Object> sqlTemplateInfo = this.getSqlTemplateInfo(sqlName);
		String sql = (String)sqlTemplateInfo.get(SQL_TEMPLATE_INFO_SQL);
		String dbName = (String)sqlTemplateInfo.get(SQL_TEMPLATE_INFO_DB_NAME);
		String responseType = (String)sqlTemplateInfo.get(SQL_TEMPLATE_INFO_RESPONSE_TYPE);
		
		// バインド変数の設定
		MapSqlParameterSource params = this.generateBindParameter(bindParameter);

		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(dbName);
				
		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(sql, params);
		
		if(result.size() == 0){
			// 取得件数が0件でnullを返却する
			return null;
		}else if(responseType.equals(RESPONSE_TYPE_JSON)){
			// Mapのリストへ変換して返却
			List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
			for(int index = 0; index < result.size(); index++){
				PGobject pgObj = (PGobject) result.get(index).get(RESPONSE_TYPE_JSON_FIXED_CLOUMN_NAME);
				if(pgObj == null){
					throw new Exception(this.messages.get(GenericAcquisitionConstants.MID_E20010));
				}
				String jsonString = pgObj.getValue();
				Map<String, Object> map = JsonUtils.convertToMap(new JSONObject(jsonString));
				mapList.add(map);
			}
			return mapList;
		}else{
			// そのまま返却
			return result;
		}
	}
	
	/**
	 * SQL名を条件にSQLテンプレートを取得する
	 * 
	 * @param sqlName SQL名
	 * @return SQLテンプレート情報
	 * @throws NotExistsApiException SQL名のSQLテンプレートが存在しない
	 * @throws Exception 予期しない例外
	 */
	private Map<String, Object> getSqlTemplateInfo(String sqlName) throws NotExistsApiException, Exception{
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
				
		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(SQL_TEMPLATE_INFO_SQL_NAME, sqlName);
				
		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(GET_SQL_TEMPLATE_INFO_SQL, params);
		
		if (result.size() == 0) {
			// 取得件数0件でAPIが存在しないエラー
			throw new NotExistsApiException(SystemConstants.MID_E00007, this.messages.get(SystemConstants.MID_E00007));
		}
		
		// SQLテンプレートの情報を返却（1件しか取得されない）
		return result.get(0);
	}

	/**
	 * バインド変数に設定するパラメータを生成する
	 * 
	 * @param bindParameter　リクエストパラメータのバインドパラメータ
	 * @return MapSqlParameterSource型のバインド変数情報
	 * @throws JSONException JSONオブジェクトにエラーがある場合
	 */
	private MapSqlParameterSource generateBindParameter(JSONObject bindParameter) throws JSONException {
		// バインド変数を格納する変数
		MapSqlParameterSource params = new MapSqlParameterSource();

		Map<String, Object> paramMap = JsonUtils.convertToMap(bindParameter);
		params = new MapSqlParameterSource(paramMap);
		
		return params;
	}
}
