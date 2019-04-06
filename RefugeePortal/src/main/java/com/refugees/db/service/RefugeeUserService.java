package com.refugees.db.service;

import com.refugees.db.config.HibernateConfigurator;
import com.refugees.db.model.RefugeeUser;
import net.hitachifbbot.DB;
import net.hitachifbbot.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RefugeeUserService {
	static Logger logger = Logger.getLogger(RefugeeUserService.class.getCanonicalName());

	public static RefugeeUser findUserByEmail(String email) {
		String sql = "select nammin_id,nammin_name,facebook_user_id,facebook_info,mail_address,pass_hash,pass_salt,translate_lang_code,last_screening_time,last_screening_request_time from nammin_user where lower(mail_address) ='"
				+ email.toLowerCase() + "'";
		Connection connection = HibernateConfigurator.getConnection();
		RefugeeUser user = null;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				user = mapToRefugeeUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return user;
	}
	
	public static RefugeeUser findUserById(int userId) {
		String sql = "select nammin_id,nammin_name,facebook_user_id,facebook_info,mail_address,pass_hash,pass_salt,translate_lang_code,last_screening_time,last_screening_request_time from nammin_user where nammin_id="+userId;
		Connection connection = HibernateConfigurator.getConnection();
		RefugeeUser user = null;
		try {
			PreparedStatement statement = connection.prepareStatement(sql);

			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				user = mapToRefugeeUser(rs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return user;
	}

	public static boolean bulkUpdate(List<DB.NamminAnswer> answers) {
		boolean result = true;
		Connection connection = HibernateConfigurator.getConnection();
		String sql = "update nammin_answer set answer= ? where nammin_id= ? and nammin_q_id= ?";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (DB.NamminAnswer ans : answers) {
				statement.setString(1, ans.answer);
				statement.setInt(2, ans.namminID);
				statement.setInt(3, ans.namminQID);
				statement.execute();
			}
		} catch (Exception e) {
			e.getMessage();
			return false;
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return result;
	}

	public static boolean bulkInsert(List<DB.NamminAnswer> answers,int userId) {
		boolean result = true;
		Connection connection = HibernateConfigurator.getConnection();
		String sql = "INSERT INTO screening_answer(screening_id,screening_q_id,answer) values(?,?,?)";
		try {
			int screenId=addScreeningInfo(userId);
			PreparedStatement statement = connection.prepareStatement(sql);
			for (DB.NamminAnswer ans : answers) {
				statement.setString(3, ans.answer);
				statement.setInt(1, screenId);
				statement.setInt(2, ans.namminQID);
				statement.execute();
			}
		} catch (Exception e) {
			e.getMessage();
			return false;
		} finally {
			try {
				bulkInsert(answers, connection);
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return result;
	}
	
	private static boolean bulkInsert(List<DB.NamminAnswer> answers,Connection connection) {
		boolean result = true;
		String sql = "insert into nammin_answer(answer,nammin_id,nammin_q_id) values(?,?,?) ;";
		try {
			PreparedStatement statement = connection.prepareStatement(sql);
			for (DB.NamminAnswer ans : answers) {
				statement.setString(1, ans.answer);
				statement.setInt(2, ans.namminID);
				statement.setInt(3, ans.namminQID);
				statement.execute();
			}
		} catch (Exception e) {
			e.getMessage();
			return false;
		} finally {
			try {

				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return result;
	}
	private static RefugeeUser mapToRefugeeUser(ResultSet rs) throws SQLException {
		RefugeeUser user = new RefugeeUser();
		user.setUserId(rs.getInt("nammin_id"));
		user.setUserName(rs.getString("nammin_name"));
		user.setDefaultLanguage(rs.getString("translate_lang_code"));
		user.setEmail(rs.getString("mail_address"));
		user.setFacebookId(rs.getString("facebook_user_id"));
		user.setFacebookInfo(rs.getString("facebook_info"));
		user.setPassword(rs.getString("pass_hash"));
		user.setPasswordSalt(rs.getString("pass_salt"));
		user.setLastScreeningDate(rs.getDate("last_screening_time"));
		user.setLastScreeningRequest(rs.getDate("last_screening_request_time"));
		return user;
	}

	public static void registerUser(RefugeeUser user) throws SQLException {

		Connection connection = HibernateConfigurator.getConnection();
		try {
			String sql = "insert into nammin_user (nammin_name,mail_address,pass_hash,pass_salt) values( ? , ?, ?, ?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getEmail());
			statement.setString(3, user.getPassword());
			statement.setString(4, user.getPasswordSalt());
			statement.execute();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		
	}

	public static int addScreeningInfo(int userId) throws SQLException {

		Connection connection = HibernateConfigurator.getConnection();
		int id =0;
		try {
			PreparedStatement stmt = connection.prepareStatement("select max(screening_id+1) scId from screening");
			ResultSet rs = stmt.executeQuery();
		
			if(rs.next())
				id=rs.getInt("scId");
			String sql = "INSERT into screening(nammin_id,screening_id,status) values(?,?,'INITIAL')";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setInt(1, userId);
			statement.setInt(2, id);
			statement.execute();

		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.warning(e.getMessage());
			}
		}
		return id;

	}
    public static ArrayList<DB.NamminAnswer> getUserAnswers(int namminID) throws SQLException {
        return DBUtils.preparedStatement("select sc.nammin_id, ans.screening_q_id nammin_q_id, ans.answer ,question.screening_q_text from (screening sc inner join screening_answer ans on sc.screening_id = ans.screening_id) inner join screening_q question on ans.screening_q_id= question.screening_q_id WHERE nammin_id = ?",
                ps -> {
                    ps.setInt(1, namminID);
                },
                r -> {
                    ArrayList<DB.NamminAnswer> result = new ArrayList<>();
                    while (r.next()) {
                        result.add(DB.NamminAnswer.getFromResultSet(r));
                    }
                    return result;
                });
    }
}
