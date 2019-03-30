package com.refugees.servlets.doctor;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.refugees.db.model.RefugeeUser;
import com.refugees.db.service.RefugeeUserService;

import net.hitachifbbot.filter.CSRFFilter;
import net.hitachifbbot.servlet.AppServlet;
import net.hitachifbbot.utils.DBUtils;
import net.hitachifbbot.utils.Template;

public class ScreeningDetailServlet extends AppServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Integer namminID = Integer.parseInt(req.getParameter("n_id"));
		Integer categoryID = Integer.parseInt(req.getParameter("c_id"));
		try {
			List<ScreeningDetail> detail = DBUtils.preparedStatement(
					"select sa.screening_q_id,sq.screening_q_text ,sa.answer from view_last_screening_answer as sa "
							+ "join screening_q as sq on sq.screening_q_id = sa.screening_q_id "
							+ "where sa.status='INITIAL' AND sa.nammin_id = ? AND sq.category_id = ? order by sa.screening_q_id;", // 選択されたカテゴリの問診結果を取得する
					ps -> {
						ps.setInt(1, namminID);
						ps.setInt(2, categoryID);
					}, r -> {
						List<ScreeningDetail> list = new ArrayList<>();
						while (r.next()) {
							int i = 0;
							int questionId = r.getInt("screening_q_id");
							list.add(new ScreeningDetail(r.getString("screening_q_text"), r.getString("answer"), questionId));
						}
						return list;
					});

			String categoryName = DBUtils.preparedStatement(
					"select sc.category_name from screening_category as sc where sc.category_id = ?;", ps -> {
						ps.setInt(1, categoryID);
					}, r -> {
						r.next();
						return r.getString(1);
					});
			RefugeeUser user = RefugeeUserService.findUserById(namminID);
			req.setAttribute("namminID", namminID);
			req.setAttribute("details", detail);
			req.setAttribute("nammin_name", user.getUserName());
			req.setAttribute("category_name", categoryName);
			req.setAttribute("category_id", categoryID);
			req.setAttribute("csrfTokenName", CSRFFilter.CSRF_TOKEN_PARAM_NAME);
			req.setAttribute("csrfToken", CSRFFilter.getCSRFToken(req));
			req.setAttribute("csrfToken2", CSRFFilter.getCSRFToken(req));
			forwardJSP("/doctor/personal_details.jsp", req, resp);
		} catch (SQLException e) {
			throw new IOException(e);
		}
	}

	/**
	 * POST
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	}

	private final static List<Integer> YES_NO_QUESTIONS;
	static {
		YES_NO_QUESTIONS = new ArrayList<Integer>();
		YES_NO_QUESTIONS.add(0);
		YES_NO_QUESTIONS.add(1);
		YES_NO_QUESTIONS.add(2);
		YES_NO_QUESTIONS.add(3);
		YES_NO_QUESTIONS.add(6);
		YES_NO_QUESTIONS.add(7);
		YES_NO_QUESTIONS.add(9);
		YES_NO_QUESTIONS.add(11);
		YES_NO_QUESTIONS.add(12);
		YES_NO_QUESTIONS.add(13);
		YES_NO_QUESTIONS.add(14);
		YES_NO_QUESTIONS.add(15);
		YES_NO_QUESTIONS.add(16);
		YES_NO_QUESTIONS.add(17);
		YES_NO_QUESTIONS.add(18);
	}

	public class ScreeningDetail {

		public String question;
		public String answer;
		public int questionId;

		public ScreeningDetail(String question, String answer, int questionId) {
			this.question = question;
			this.answer = answer;
			this.questionId = questionId;
		}

		public String getQuestion() {
			return question;
		}

		public String getAnswer() {
			return answer;
		}

		public int getQuestionId() {
			return questionId;
		}
	}
}