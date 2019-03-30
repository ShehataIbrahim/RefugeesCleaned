package com.refugees.servlets.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.refugees.common.Validator;
import com.refugees.db.model.RefugeeUser;
import com.refugees.db.service.CategoryService;
import com.refugees.db.service.RefugeeUserService;

import net.arnx.jsonic.JSON;
import net.hitachifbbot.DB;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.servlet.AppServlet;
import net.hitachifbbot.session.AppSession;

/**
 * Servlet implementation class UserLogin
 */
public class UserHomeServlet extends AppServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserHomeServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			request.setAttribute("categories", CategoryService.getCategoryQuestions());
			RefugeeUser user = (RefugeeUser) request.getSession().getAttribute("userData");
			if(user==null)
			{
				NamminUserData u = AppSession.getNamminUserData(request);
				if(u instanceof RefugeeUser)
					user=(RefugeeUser)u ;
				else
					user=RefugeeUser.build(u);
			}
			ArrayList<DB.NamminAnswer> answer = RefugeeUserService.getUserAnswers(user.getUserId());
			HashMap<String, String> answers = new HashMap<>();
			for (DB.NamminAnswer a : answer) {
				answers.put("" + a.namminQID, a.answer);
			}
			request.setAttribute("answers", JSON.encode(answers));
			forwardJSP("/user/home.jsp", request, response);
		} catch (SQLException e) {
			throw new ServletException(e);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Enumeration<String> attrs = request.getParameterNames();
		RefugeeUser user = (RefugeeUser) request.getSession().getAttribute("userData");
		HashMap<Integer, DB.NamminAnswer> answersMap = new HashMap<>();
		ArrayList<DB.NamminAnswer> answers=null;
		try {
			answers = RefugeeUserService.getUserAnswers(user.getUserId());
			for (DB.NamminAnswer ans : answers)
				answersMap.put(ans.namminQID, ans);
		} catch (SQLException e) {
			e.printStackTrace();
			answers=new ArrayList<>();
		}
		ArrayList<DB.NamminAnswer> newAnswers = new ArrayList<>();
		while (attrs.hasMoreElements()) {
			String key = attrs.nextElement();
			try {
				int questionId = Integer.parseInt(key);
				String value = request.getParameter(key);
				DB.NamminAnswer answer = null;
				if (answersMap.containsKey(questionId)) {
					answer = answersMap.get(questionId);
					answer.answer = value;
				} else {
					newAnswers.add(new DB.NamminAnswer(user.getUserId(), questionId, value));

				}

			} catch (Exception e) {
				log("not numeric value " + key);
				String result="{\"success\":false,\"message\":\"Error occured\"}";
			}
		}
//		RefugeeUserService.bulkUpdate(answers);
		RefugeeUserService.bulkInsert(newAnswers,user.getUserId());
		response.setContentType("application/json");
		String result="{\"success\":true,\"message\":\"Data reflected successfuly\"}";
		response.getWriter().print(result);
	}

}
