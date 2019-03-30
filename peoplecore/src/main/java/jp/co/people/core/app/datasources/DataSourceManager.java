/*
 *　Copyright (C) Hitachi, Ltd. All rights reserved.
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

package jp.co.people.core.app.datasources;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import jp.co.people.core.app.common.SystemConstants;

/**
 * データソースを管理するクラス
 */
@Component
public class DataSourceManager {
	// ! DB名からデータソースを取得するためのハッシュマップ
	private HashMap<String, DataSource> dataSourceFromName;

	/**
	 * コンストラクタ
	 * 
	 * @param core
	 *            core DBのデータソース
	 * @param health
	 *            health DBのデータソース
	 * @param personal
	 *            personal DBのデータソース
	 */
	public DataSourceManager(
			@Qualifier("core") DataSource core,
			@Qualifier("health") DataSource health,
			@Qualifier("personal") DataSource personal) {
		this.dataSourceFromName = new HashMap<String, DataSource>();

		this.dataSourceFromName.put(SystemConstants.DATASOURCE_CORE_KEY, core);
		this.dataSourceFromName.put(SystemConstants.DATASOURCE_HEALTH_KEY, health);
		this.dataSourceFromName.put(SystemConstants.DATASOURCE_PERSONAL_KEY, personal);
	}

	/**
	 * 指定されたDB名から対応する DataSource を返す。
	 * 
	 * @param dbName
	 *            DB名。SystemConstants の定数を使用する。
	 * 
	 * @return 対応するデータソース。該当するDBが存在しない場合は null を返す。
	 */
	public DataSource getDataSource(String dbName) {
		return dataSourceFromName.get(dbName);
	}

	/**
	 * 指定されたDB名から対応する JdbcTemplate を返す。
	 * 
	 * @param dbName
	 *            DB名。SystemConstants の定数を使用する。
	 * 
	 * @return 対応するJdbcTemplate。該当するDBが存在しない場合は null を返す。
	 */
	public JdbcTemplate getJdbcTemplate(String dbName) {
		return new JdbcTemplate(dataSourceFromName.get(dbName));
	}

	/**
	 * NamedParameterJdbcTemplate
	 * 
	 * @param dbName
	 *            DB名。SystemConstants の定数を使用する。
	 * 
	 * @return 対応するNamedParameterJdbcTemplate。該当するDBが存在しない場合は null を返す。
	 */
	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate(String dbName) {
		return new NamedParameterJdbcTemplate(dataSourceFromName.get(dbName));
	}

	/**
	 * 汎用的に使えるトランザクション。指定したDBに対してSQLを実行する。
	 * 
	 * @param dbName
	 *            DB名。SystemConstants の定数を使用する。
	 * @param sqlSentence
	 *            実行したいSQL文。
	 * 
	 * @return 処理したデータの件数。
	 * @throws SQLException
	 */
	@Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
	public int executeTransaction(String dbName, String sqlSentence) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		int retval = 0;

		try {
			connection = dataSourceFromName.get(dbName).getConnection();
			statement = connection.createStatement();
			retval = statement.executeUpdate(sqlSentence);
		} finally {
			statement.close();
		}

		return retval;
	}
}
