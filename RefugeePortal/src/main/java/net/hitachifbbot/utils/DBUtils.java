package net.hitachifbbot.utils;

import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import com.refugees.db.config.HibernateConfigurator;

/**
 * DBに関する記述を簡潔にするためのUtil
 */
public class DBUtils {
	/**
	 * SQLExceptionをスローするConsumer
	 * @param <T>
	 */
	public interface SQLConsumer<T>{
	    void accept(T t) throws SQLException;
	}

	/**
	 * SQLExceptionをスローするFunction
	 * @param <P>
	 * @param <R>
	 */
	public interface SQLFunction<P,R>{
	    R accept(P p) throws SQLException;
	}

	/**
	 * データソース
	 */
	protected DataSource dataSource = null;

	/**
	 * DBUtils instance
	 */
	public static DBUtils instance = new DBUtils();

	/**
	 * コンストラクタ.
	 * バッチモードの判定と初期化を行う
	 */
	protected DBUtils(){
		/*InputStream batchSettingInputStream = getClass().getClassLoader().getResourceAsStream("batch_db.properties");
		if(batchSettingInputStream != null){ // バッチ設定が見つかったなら接続を試行
			try(InputStream in = batchSettingInputStream){
				Properties prop = new Properties();
				prop.load(in);
				dataSource = BasicDataSourceFactory.createDataSource(prop);
				return;
			} catch (Exception e) {
				System.out.println("Batch DB Initialize Failed.");
				e.printStackTrace();
			}
		}*/
	}

	/**
	 * 設定ファイルを読み込みコネクションを取得する<br>
	 * @return SQLコネクション
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException{
		return HibernateConfigurator.getConnection();
	}
	public Connection getHealthConnection() throws SQLException{
		return HibernateConfigurator.getHealthConnection();
	}
	/**
	 * コネクションを受け取り、処理を実行する際のコードを楽に書けるようにしたUtil
	 * @param c コネクションを用いて行う処理
	 * @throws SQLException
	 */
	public static void connection(SQLConsumer<Connection> c) throws SQLException {
		Connection conn = null;
		try {
			conn =  HibernateConfigurator.getConnection();
			c.accept(conn);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
//to validate datasource creation based on server type
	private String serverInfo;

	public String getServerInfo() {
		return serverInfo;
	}

	public void setServerInfo(String serverInfo) {
		this.serverInfo = serverInfo;
	}

	/**
	 * コネクションを受け取り、処理を実行する際のコードを楽に書けるようにしたUtil
	 * @param c コネクションを用いて行う処理
	 * @throws SQLException
	 */
	public static <R> R connection(SQLFunction<Connection,R> c) throws SQLException {
		Connection conn = null;
		try {
			conn =  HibernateConfigurator.getConnection();
			return c.accept(conn);
		} finally {
			if (conn != null) {
				try {
					conn.setAutoCommit(true);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}


	/**
	 * PreparedStatementを文字列からお越し、処理を実行するコードを楽に書けるようにしたUtil
	 * @param sql PreparedStatementを作成するSQL文字列
	 * @param c PreparedStatementの初期化を行うラムダ式。executeはラムダ式内で行う必要がある.
	 * @throws SQLException
	 */
	public static void preparedStatement(Connection conn, String sql, SQLConsumer<PreparedStatement> c) throws SQLException {
		PreparedStatement statement = null;
		try{
			statement = conn.prepareStatement(sql);
			c.accept(statement);
		} finally {
			if (statement != null){
				try{
					statement.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * SQLで戻り値を受け取るような処理を簡潔に記述できるようにしたUtil
	 * @param conn 有効なSQLコネクション
	 * @param sql PreparedStatementを作成するSQL文字列
	 * @param c PreparedStatementの初期化を行うラムダ式。executeQueryはしなくて良い
	 * @param r ResultSetを受け取り戻り値を生成するラムダ式
	 * @return ラムダ式が生成した戻り値
	 * @throws SQLException
	 */
	public static <R> R preparedStatement(Connection conn, String sql, SQLConsumer<PreparedStatement> c, SQLFunction<ResultSet,R> r) throws SQLException {
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareStatement(sql);
			c.accept(statement);
			rs = statement.executeQuery();
			return r.accept(rs);
		} finally {
			if (rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (statement != null){
				try{
					statement.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * PreparedStatementを文字列からお越し、処理を実行するコードを楽に書けるようにしたUtil
	 * @param sql PreparedStatementを作成するSQL文字列
	 * @param c PreparedStatementの初期化を行うラムダ式。executeはラムダ式内で行う必要がある.
	 * @throws SQLException
	 */
	public static void preparedStatement(String sql, SQLConsumer<PreparedStatement> c) throws SQLException {
		connection((conn) -> {
			PreparedStatement statement = null;
			try{
				statement = conn.prepareStatement(sql);
				c.accept(statement);
			} finally {
				if (statement != null){
					try{
						statement.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
	}

	/**
	 * SQLで戻り値を受け取るような処理を簡潔に記述できるようにしたUtil
	 * @param sql PreparedStatementを作成するSQL文字列
	 * @param c PreparedStatementの初期化を行うラムダ式。executeQueryはしなくて良い
	 * @param r ResultSetを受け取り戻り値を生成するラムダ式
	 * @return ラムダ式が生成した戻り値
	 * @throws SQLException
	 */
	public static <R> R preparedStatement(String sql, SQLConsumer<PreparedStatement> c, SQLFunction<ResultSet,R> r) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = HibernateConfigurator.getConnection();
			statement = conn.prepareStatement(sql);
			c.accept(statement);
			rs = statement.executeQuery();
			return r.accept(rs);
		} finally {
			if (rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (statement != null){
				try{
					statement.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static <R> R preparedStatementHealth(String sql, SQLConsumer<PreparedStatement> c, SQLFunction<ResultSet,R> r) throws SQLException {
		Connection conn = null;
		PreparedStatement statement = null;
		ResultSet rs = null;
		try {
			conn = HibernateConfigurator.getHealthConnection();
			statement = conn.prepareStatement(sql);
			c.accept(statement);
			rs = statement.executeQuery();
			return r.accept(rs);
		} finally {
			if (rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (statement != null){
				try{
					statement.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 * SQLで戻り値を受け取るような処理を簡潔に記述できるようにしたUtil
	 * @param conn SQLコネクション
	 * @param sql CallableStatementを作成するSQL文字列
	 * @param c CallableStatementの初期化を行うラムダ式。executeQueryはしなくて良い
	 * @param r ResultSetを受け取り戻り値を生成するラムダ式
	 * @return ラムダ式が生成した戻り値
	 * @throws SQLException
	 */
	public static <R> R prepareCall(Connection conn,String sql, SQLConsumer<CallableStatement> c, SQLFunction<ResultSet,R> r) throws SQLException {
		CallableStatement statement = null;
		ResultSet rs = null;
		try {
			statement = conn.prepareCall(sql);
			c.accept(statement);
			rs = statement.executeQuery();
			return r.accept(rs);
		} finally {
			if (rs != null){
				try{
					rs.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			if (statement != null){
				try{
					statement.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * SQLで戻り値を受け取るような処理を簡潔に記述できるようにしたUtil
	 * @param sql CallableStatementを作成するSQL文字列
	 * @param c CallableStatementの初期化を行うラムダ式。executeQueryはしなくて良い
	 * @param r ResultSetを受け取り戻り値を生成するラムダ式
	 * @return ラムダ式が生成した戻り値
	 * @throws SQLException
	 */
	public static <R> R prepareCall(String sql, SQLConsumer<CallableStatement> c, SQLFunction<ResultSet,R> r) throws SQLException {
		return DBUtils.connection(conn -> {
			CallableStatement statement = null;
			ResultSet rs = null;
			try {
				statement = conn.prepareCall(sql);
				c.accept(statement);
				rs = statement.executeQuery();
				return r.accept(rs);
			} finally {
				if (rs != null){
					try{
						rs.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
				if (statement != null){
					try{
						statement.close();
					}catch(SQLException e){
						e.printStackTrace();
					}
				}
			}
		});
	}
}
