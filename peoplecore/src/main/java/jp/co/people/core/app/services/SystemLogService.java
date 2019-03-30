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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.co.people.core.app.common.SystemConstants;

/**
 * システムログへのアクセスを提供するクラス
 */
@Service
public class SystemLogService extends BaseService {
	/*! システムログ登録SQL */
	private final static String SQL_INSERT_SYSTEM_LOG
		= "INSERT INTO log_system (\n"
		+ "    sequence_id,\n"
		+ "    process_number,\n"
		+ "    level,\n"
		+ "    event_code,\n"
		+ "    message,\n"
		+ "    function_name,\n"
		+ "    derived_from,\n"
		+ "    ip_address,\n"
		+ "    created_at\n"
		+ ") VALUES (\n"
		+ "    NEXTVAL('seq_log_system_id'),\n"
		+ "    :process_number,\n"
		+ "    :level,\n"
		+ "    :event_code,\n"
		+ "    :message,\n"
		+ "    :function_name,\n"
		+ "    :derived_from,\n"
		+ "    :ip_address,\n"
		+ "    CURRENT_TIMESTAMP\n"
		+ ");\n"
		;

	/*! 処理番号取得SQL */
	private final static String SQL_NEXTVAL_SEQ_LOG_SYSTEM_PROCESS_NUMBER
		= "SELECT NEXTVAL('seq_log_system_process_number') AS sequence;"
		;

	/*! 処理番号確認SQL */
	private final static String SQL_PROCESS_NUMBER_EXISTS
		= "SELECT\n"
		+ "    process_number\n"
		+ "FROM\n"
		+ "    log_system\n"
		+ "WHERE\n"
		+ "    process_number = :process_number;"
		;

	/*! 登録元取得SQL */
	private final static String SQL_GET_RESOURCE_TYPE
		= "SELECT\n"
		+ "    resource_type\n"
		+ "FROM\n"
		+ "    auth_keys\n"
		+ "WHERE\n"
		+ "    auth_key = :auth_key;\n"
		;
	
	
	/**
	 * コンストラクタ
	 */
	public SystemLogService() {
		// 特に処理なし
	}
	
	
	/**
	 * システムログを登録する。
	 * 
	 * @param processNumber 処理番号
	 * @param level 		ログレベル
	 * @param eventCode 	イベントコード
	 * @param message		メッセージ
	 * @param functionName	アプリ定義の機能名
	 * @param derivedFrom	登録元
	 * @param ipAddress		呼出元IPアドレス
	 * 
	 * @return 登録件数
	 * 
	 * @throws SQLException 
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int insert(
			long processNumber,
			String level,
			String eventCode,
			String message,
			String functionName,
			String derivedFrom,
			String ipAddress) throws SQLException {
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
		
		// バインド変数の設定
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("process_number", processNumber)
			.addValue("level", level)
			.addValue("event_code", eventCode)
			.addValue("message", message)
			.addValue("function_name", functionName)
			.addValue("derived_from", derivedFrom)
			.addValue("ip_address", ipAddress);
		
		return template.update(SQL_INSERT_SYSTEM_LOG, params);
	}


	/**
	 * 処理番号を新規に採番して返す。
	 * @throws Exception 
	 */
	public long getNextProcessNumber() throws SQLException {
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
		
		MapSqlParameterSource params = new MapSqlParameterSource();
		
		List<Map<String, Object>> rows = template.queryForList(SQL_NEXTVAL_SEQ_LOG_SYSTEM_PROCESS_NUMBER, params);
		return (long)rows.get(0).get("sequence");
	}
	
	
	/**
	 * 指定された処理番号が存在する場合にtrueを返す。存在しない場合はfalseを返す。
	 * 
	 * @param processNumber 処理番号
	 * @return true:存在する。 false:存在しない。
	 * @throws SQLException
	 */
	public Boolean processNumberExists(long processNumber) throws SQLException {
		NamedParameterJdbcTemplate template = this.dataSourceManager.getNamedParameterJdbcTemplate(SystemConstants.DATASOURCE_CORE_KEY);
		
		MapSqlParameterSource params = new MapSqlParameterSource()
			.addValue("process_number", processNumber);
		
		List<Map<String, Object>> rows = template.queryForList(SQL_PROCESS_NUMBER_EXISTS, params);
		return rows.size() > 0;
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
	 * 英数字および記号でのみ構成される文字列の場合、trueを返す。それ以外はfalseを返す。
	 * 
	 * @param value 検証したい文字列。
	 * 
	 * @return true:英数字のみ。false:それ以外の文字が混ざっている。
	 */
	public static Boolean isCode(String value) {
	    String regex = "^[a-zA-Z0-9_\\-]+$";
	    Pattern p = Pattern.compile(regex);
	    Matcher m = p.matcher(value);
	    
	    return m.find();
	}
}
