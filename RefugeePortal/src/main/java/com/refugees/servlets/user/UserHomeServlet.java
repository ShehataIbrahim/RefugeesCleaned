package com.refugees.servlets.user;

import com.refugees.db.model.AnswerTypesEnum;
import com.refugees.db.model.RefugeeUser;
import com.refugees.db.service.CategoryService;
import com.refugees.db.service.RefugeeUserService;
import com.refugees.consolidate.ConsolidationService;
import com.refugees.consolidate.model.InterviewAnswerBaseData;
import net.arnx.jsonic.JSON;
import net.hitachifbbot.DB;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.servlet.AppServlet;
import net.hitachifbbot.session.AppSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

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

			RefugeeUser user = (RefugeeUser) request.getSession().getAttribute("userData");
			if(user==null)
			{
				NamminUserData u = AppSession.getNamminUserData(request);
				if(u instanceof RefugeeUser)
					user=(RefugeeUser)u ;
				else
					user=RefugeeUser.build(u);
			}
			//eliminating old way of getting categories/questions/answers
			Map<String, InterviewAnswerBaseData> patientAnswers = ConsolidationService.getPatientAnswers(user.getUserId().toString());
			final Set<String> categorries=new HashSet<>();
			patientAnswers.values().stream().forEach(baseData->{
				categorries.add(baseData.getInterview_category_id());
			});
			request.setAttribute("categories", CategoryService.getCategoryQuestions(CategoryService.getHealtnCategories(String.join(",",categorries)),patientAnswers.values()));

			HashMap<String, String> answers = new HashMap<>();
			for (InterviewAnswerBaseData a:patientAnswers.values()) {
				if(AnswerTypesEnum.LIST.toString().equalsIgnoreCase(a.getAnswer_type()))
					answers.put("" + a.objectId(), Integer.valueOf(a.getInterview_answer())==1?"YES":"NO");
				else
					answers.put("" + a.objectId(), a.getInterview_answer());
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
