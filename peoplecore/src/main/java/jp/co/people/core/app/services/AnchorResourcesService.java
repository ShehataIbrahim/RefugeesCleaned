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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.people.core.app.common.IssueResultEnum;
import jp.co.people.core.app.common.SystemConstants;
import jp.co.people.core.app.exceptions.InputParametersException;
import jp.co.people.core.app.exceptions.MasterInconsistencyException;
import jp.co.people.core.app.exceptions.NotRegisteredAnchorResourceException;
import jp.co.people.core.app.services.model.IssueAnchorResourceResult;

/**
 * <PRE>
 * クラス名：
 *   インデックス情報発行クラス
 *
 * 機能説明：
 *   アンカーキーの発行を行う
 *   リソースキーの発行を行う。
 *   インデックス情報の登録を行う。
 * </PRE>
 */
@Service
public class AnchorResourcesService extends BaseService {
	private static final String TITLE_RESOURCE_KEY = "リソースキー";
	private static final String TITLE_RESOURCE_TYPE = "リソース種別";
	//! リソースキー変換SQL
	private static final String SQL_CONVERT_RESOURCE_KEY
		= "SELECT\n"
		+ "    main.resource_key resource_key\n"
		+ "FROM\n"
		+ "    anchor_resources main\n"
		+ "INNER JOIN\n"
		+ "    (\n"
		+ "        SELECT\n"
		+ "            anchor_key\n"
		+ "        FROM\n"
		+ "            anchor_resources\n"
		+ "        WHERE\n"
		+ "            resource_key = :resource_key\n"
		+ "    ) sub\n"
		+ "ON\n"
		+ "    main.anchor_key = sub.anchor_key\n"
		+ "WHERE\n"
		+ "    resource_type = :resource_type\n"
		+ "LIMIT 1\n"
		+ ";\n"
		;

	/** リソース種別存在チェックSQL */
	private static final String VALIDATE_TYPE_SQL = "SELECT COUNT(main.type) cnt FROM resource_types main WHERE main.type = :resource_type;";

	/** アンカーキー用SEQ取得SQL */
	private static final String GET_SEQ_FOR_ANCHOR_KEY_SQL = "SELECT NEXTVAL('seq_anchor_key') AS sequence;";

	/** リソースキー用SEQ取得SQL */
	private static final String GET_SEQ_FOR_RESOURCE_KEY_SQL = "SELECT NEXTVAL('seq_resource_key') AS sequence;";

	/** インデックス情報取得（リソースキーが条件）SQL */
	private static final String GET_ANCHOR_RESOURCE_01_SQL = "SELECT main.anchor_key, main.resource_type, main.resource_key FROM anchor_resources main WHERE main.resource_key = :resource_key;";

	/** インデックス情報取得（アンカーキー、リソース種別が条件）SQL */
	private static final String GET_ANCHOR_RESOURCE_02_SQL = "SELECT main.anchor_key, main.resource_type, main.resource_key FROM anchor_resources main WHERE main.anchor_key = :anchor_key and main.resource_type = :resource_type;";

	/** インデックス情報登録SQL */
	private static final String REGISTRATION_ANCHOR_RESOURCE_SQL = "INSERT INTO anchor_resources (anchor_key, resource_type, resource_key, created_by, created_at, updated_by, updated_at) values (:anchor_key, :resource_type, :resource_key, :created_by, current_timestamp, :updated_by, current_timestamp);";

	/** インデックス情報発行の結果の項目名 */
	public static final String ISSUE_ANCHOR_RESOURCE_RESULT = "result";
	

	/**
	 * 指定されたリソースキーを、指定されたリソース種別のリソースキーに変換する。
	 * 
	 * @param resourceKeyFrom 変換したいリソースキー
	 * @param resourceTypeTo  変換先リソース種別
	 * 
	 * @return 変換後のリソースキー。対象のリソース種別のインデックス情報が存在しない場合はNULL。
	 * @throws InputParametersException 入力パラメータが正しくない 
	 * @throws Exception 不具合があった場合(予期しない例外)
	 */
	public UUID convert(UUID resourceKeyFrom, String resourceTypeTo) throws InputParametersException, Exception {
		UUID retval = null;
		
		// 入力パラメータチェック
		// リソースキーチェック
		if (resourceKeyFrom == null) {
			throw new InputParametersException(
				SystemConstants.MID_E00004,
				this.messages.get(SystemConstants.MID_E00004, TITLE_RESOURCE_KEY));
		}

		// リソース種別チェック
		if (resourceTypeTo == null || resourceTypeTo.isEmpty()) {
			throw new InputParametersException(
				SystemConstants.MID_E00004,
				this.messages.get(SystemConstants.MID_E00004, TITLE_RESOURCE_TYPE));
		}
		
		// リソースキーの変換
		NamedParameterJdbcTemplate coreTemplate = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		MapSqlParameterSource params = new MapSqlParameterSource()
				.addValue("resource_key", resourceKeyFrom)
				.addValue("resource_type", resourceTypeTo);
		
		List<Map<String, Object>> rows = coreTemplate.queryForList(SQL_CONVERT_RESOURCE_KEY, params);
		if (rows.size() > 0) {
			retval = (UUID)rows.get(0).get("resource_key");
		}
		
		return retval;
	}
	
	
	/**
	 * インデックス情報を発行する
	 * 
	 * @param type　リソース種別
	 * @param resourceKey リソースキー
	 * @param requestBy 呼出元（ユーザーやAPI）
	 * @return　インデックス情報発行の結果
	 * @throws InputParametersException
	 * @throws NotRegisteredAnchorResourceException
	 * @throws Exception
	 */
	public IssueAnchorResourceResult issue(String type, UUID resourceKey, String requestBy)
			throws InputParametersException, NotRegisteredAnchorResourceException, Exception {
		IssueAnchorResourceResult result = null;

		// 入力パラメータチェック
		this.validateInputParameters(type);

		if (resourceKey != null) {
			// インデクス情報をリソースキーを条件に取得
			Map<String, Object> anchorResource01 = this.getAnchorResource(resourceKey);

			// 取得したインデクス情報のアンカーキー、入力パラメータのリソース種別を条件に取得
			List<Map<String, Object>> anchorResource02 = this
					.getAnchorResource((UUID) anchorResource01.get(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY), type);

			// 存在する場合、取得結果を返却
			if (anchorResource02.size() == 0) {
				// 存在しない場合、リソースキーの発行
				UUID resource_key = this.issueResourceKey();

				// インデックス情報の登録
				this.resistrationAnchorResource(
						(UUID) anchorResource01.get(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY), type, resource_key,
						requestBy);

				// 出力データに設定
				result = new IssueAnchorResourceResult(
						IssueResultEnum.RESOURCE,
						(UUID) anchorResource01.get(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY),
						type,
						resource_key);
			} else {
				// 出力データに設定
				Map<String, Object> existedAnchorResource = anchorResource02.get(0);
				result = new IssueAnchorResourceResult(
						IssueResultEnum.ALREADY,
						(UUID) existedAnchorResource.get(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY),
						(String) existedAnchorResource.get(SystemConstants.ANCHOR_RESOURCES_TYPE),
						(UUID) existedAnchorResource.get(SystemConstants.ANCHOR_RESOURCES_RESOURCE_KEY));
			}

		} else {
			// アンカーキーの発行
			UUID anchor_key = this.issueAnchorKey();

			// リソースキーの発行
			UUID resource_key = this.issueResourceKey();

			// インデックス情報の登録
			this.resistrationAnchorResource(anchor_key, type, resource_key, requestBy);

			// 出力データに設定
			result = new IssueAnchorResourceResult(
					IssueResultEnum.ANCHOR,
					anchor_key,
					type,
					resource_key);
		}

		return result;
	}

	/**
	 * リソース種別がリソース種別マスタに存在するかをチェックする
	 * 
	 * @param type
	 *            リソース種別
	 * @throws InputParametersException
	 *             リソース種別がリソース種別マスタに存在しない
	 * @throws Exception
	 *             予期しない例外
	 */
	private void validateInputParameters(String type) throws InputParametersException, Exception {
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager
				.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(SystemConstants.ANCHOR_RESOURCES_TYPE, type);

		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(VALIDATE_TYPE_SQL, params);

		if (result.size() == 0 || (Long)result.get(0).get("cnt") == 0) {
			// 取得件数0件でマスタ不整合エラーー
			// リソース種別マスタに不整合があります。リソース種別[{0}]は存在しません。
			throw new MasterInconsistencyException(SystemConstants.MID_E00017, this.messages.get(SystemConstants.MID_E00017, type));
		}
	}

	/**
	 * アンカーキーを発行する
	 * 
	 * @return 発行したアンカーキー
	 * @throws Exception
	 *             予期しない例外
	 */
	private UUID issueAnchorKey() throws Exception {
		UUID anchorKey = null;

		// 接続情報の取得
		JdbcTemplate template = this.dataSourceManager.getJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// SQLの実行
		Map<String, Object> result = template.queryForMap(GET_SEQ_FOR_ANCHOR_KEY_SQL);

		// 日時フォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		
		// UUID発行のためのキー情報を作成
		Long   anchor_seq   = (Long)result.get("sequence");
		String now_datetime = dateFormat.format(new Date());
		String uuidKey      = SystemConstants.MD5_ANCHOR_KEY + anchor_seq + now_datetime;

		// アンカーキーの発行
		anchorKey = UUID.nameUUIDFromBytes(uuidKey.getBytes());

		return anchorKey;
	}

	/**
	 * リソースキーを発行する
	 * 
	 * @return 発行したリソースキー
	 * @throws Exception
	 *             予期しない例外
	 */
	private UUID issueResourceKey() throws Exception {
		UUID resourceKey = null;

		// 接続情報の取得
		JdbcTemplate template = this.dataSourceManager.getJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// SQLの実行
		Map<String, Object> result = template.queryForMap(GET_SEQ_FOR_RESOURCE_KEY_SQL);

		// 日時フォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

		// UUID発行のためのキー情報を作成
		Long   resource_seq = (Long)result.get("sequence");
		String now_datetime = dateFormat.format(new Date());
		String uuidKey = SystemConstants.MD5_RESOURCE_KEY + resource_seq + now_datetime;

		// リソースキーの発行
		resourceKey = UUID.nameUUIDFromBytes(uuidKey.getBytes());

		return resourceKey;
	}

	/**
	 * インデックス情報を登録する
	 * 
	 * @param anchorKey
	 *            登録するアンカーキー
	 * @param type
	 *            登録するリソース種別
	 * @param resourceKey
	 *            登録するリソース種別
	 * @param requestBy
	 *            呼出元（ユーザーやAPI）
	 * @throws Exception
	 *             SQLの実行エラーなどの予期しない例外
	 */
	private void resistrationAnchorResource(UUID anchorKey, String type, UUID resourceKey, String requestBy)
			throws Exception {
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager
				.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY, anchorKey);
		params.addValue(SystemConstants.ANCHOR_RESOURCES_TYPE, type);
		params.addValue(SystemConstants.ANCHOR_RESOURCES_RESOURCE_KEY, resourceKey);
		params.addValue("created_by", requestBy);
		params.addValue("updated_by", requestBy);

		// SQLの実行
		template.update(REGISTRATION_ANCHOR_RESOURCE_SQL, params);
	}

	/**
	 * リソースキーを条件にインデックス情報を取得する
	 * 
	 * @param resourceKey
	 *            紐づけ元のリソースキー
	 * @return 取得したインデックス情報
	 * @throws NotRegisteredAnchorResourceException
	 *             紐づけ元のリソースキーで取得したインデックス情報の件数が0件
	 * @throws Exception
	 *             予期しない例外
	 */
	private Map<String, Object> getAnchorResource(UUID resourceKey)
			throws NotRegisteredAnchorResourceException, Exception {
		Map<String, Object> anchorResource = new HashMap<String, Object>();

		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager
				.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(SystemConstants.ANCHOR_RESOURCES_RESOURCE_KEY, resourceKey);

		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(GET_ANCHOR_RESOURCE_01_SQL, params);

		if (result.size() == 0) {
			// 取得件数0件でインデックス情報未登録エラー
			throw new NotRegisteredAnchorResourceException(SystemConstants.MID_E10001,
					this.messages.get(SystemConstants.MID_E10001, resourceKey));
		}

		anchorResource = result.get(0);
		return anchorResource;
	}

	/**
	 * アンカーキー、リソース種別を条件にインデックス情報を取得する
	 * 
	 * @param anchorKey
	 *            取得したインデクス情報のアンカーキー
	 * @param type
	 *            入力パラメータのリソース種別
	 * @return 取得したインデックス情報のリスト
	 * @throws Exception
	 *             予期しない例外
	 */
	private List<Map<String, Object>> getAnchorResource(UUID anchorKey, String type) throws Exception {
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager
				.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue(SystemConstants.ANCHOR_RESOURCES_ANCHOR_KEY, anchorKey);
		params.addValue(SystemConstants.ANCHOR_RESOURCES_TYPE, type);

		// SQLの実行
		List<Map<String, Object>> result = template.queryForList(GET_ANCHOR_RESOURCE_02_SQL, params);

		return result;
	}
}
