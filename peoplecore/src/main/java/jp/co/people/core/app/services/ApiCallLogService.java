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
 *   2018/01/31		00.01								 新規作成
 */

package jp.co.people.core.app.services;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import jp.co.people.core.app.common.SystemConstants;

/**
 * <PRE>
 * クラス名：
 *   API呼び出しログクラス
 *
 * 機能説明：
 *   API呼び出しログを登録する
 * </PRE>
 */
@Service
public class ApiCallLogService extends BaseService {
	
	/** API呼び出しログ登録SQL */
	private static final String REGISTRATION_API_CALL_LOG_SQL = "insert into log_api_call (sequence_id,result,api_name,api_parameters,message_id,message,system_message,ip_address,created_at)values(nextval('seq_log_api_call'),:result,:api_name,:api_parameters,:message_id,:message,:system_message,:ip_address,current_timestamp);";

	/**
	 * API呼び出しログを登録する。
	 * 
	 * @param logResult APIの結果（'N'：正常、'W'：警告、'E'：エラー）
	 * @param apiName　呼び出したAPIの名前
	 * @param inputParameters 入力パラメータ
	 * @param messageId　警告やエラー時のメッセージID
	 * @param message　警告やエラー時のメッセージ
	 * @param systemMessage　システムから発生したException等の詳細なメッセージ
	 * @param ipAddress APIを呼び出したクライアントのIPアドレス
	 * @throws Exception ログの登録失敗
	 */
	public void writeLog(
			String logResult,
			String apiName,
			String inputParameters,
			String messageId,
			String message,
			String systemMessage,
			String ipAddress) throws Exception{
		// 接続情報の取得
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);

		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource();
		params.addValue("result", logResult);
		params.addValue("api_name", apiName);
		params.addValue("api_parameters", inputParameters);
		params.addValue("message_id", messageId);
		params.addValue("message", message);
		params.addValue("system_message", systemMessage);
		params.addValue("ip_address", ipAddress);

		// SQLの実行
		template.update(REGISTRATION_API_CALL_LOG_SQL, params);
	}
}
