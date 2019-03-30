package com.refugees.db.service;

import java.sql.Connection;
import java.sql.Statement;

import com.refugees.db.config.HibernateConfigurator;

import net.hitachifbbot.utils.DBUtils;

public class ScreeningService {
	public static boolean checkUserHasPendingScreening(int userId) {
		try {
			return DBUtils.preparedStatement(
					"select sa.screening_q_id,sq.screening_q_text ,sa.answer from view_last_screening_answer as sa "
							+ "join screening_q as sq on sq.screening_q_id = sa.screening_q_id "
							+ "where sa.status='INITIAL' AND sa.nammin_id = ? order by sa.screening_q_id;",
					ps -> {
						ps.setInt(1, userId);
					}, r -> {
						if (r.next()) {
							return true;
						}
						return false;
					});
		} catch (Exception e) {
			return false;
		}
	}

	public static void confirmScreen(String screenId) {
		Connection conn = null;
		Statement stmt = null;
		try {
			conn = HibernateConfigurator.getConnection();
			stmt = conn.createStatement();
			stmt.execute("update screening set status='CONFIRMED' WHERE screening_id=" + screenId);
			conn.commit();
		} catch (Exception e) {
		} finally {
			if (stmt != null)
				try {
					stmt.close();
				} catch (Exception e) {

				}
			if (conn != null)
				try {
					conn.close();
				} catch (Exception e) {

				}
		}
	}
}
