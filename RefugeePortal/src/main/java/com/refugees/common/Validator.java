package com.refugees.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.hitachifbbot.Consts;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.session.AppSession;


public class Validator {
	private final static List<Integer> TEXT_QUESTIONS = new ArrayList<>();
	private final static List<Integer> NUMERIC_QUESTIONS = new ArrayList<>();
	static {
		TEXT_QUESTIONS.add(4);
		TEXT_QUESTIONS.add(5);
		TEXT_QUESTIONS.add(8);
		NUMERIC_QUESTIONS.add(11);
	}

	public static boolean validateSession(HttpServletRequest request) {
		HttpSession userSession = request.getSession();
		return (userSession.getAttribute("userData") != null)||AppSession.getNamminUserData(request)!=null;
	}

	public static boolean isText(int questionId) {
		return TEXT_QUESTIONS.contains(questionId);
	}

	public static boolean isNumeric(int questionId) {
		return NUMERIC_QUESTIONS.contains(questionId);
	}

	public static boolean isYesNo(int questionId) {
		return !(isNumeric(questionId) || isText(questionId));
	}

	public static String evaluateQuestionValue(int questionId, String value) {
		if (isYesNo(questionId)) {
			try {
				if (Integer.parseInt(value) == 1)
					return "Yes";
			} catch (Exception e) {
				return "No";
			}
			return "No";

		} else
			return value;
	}

	public static NamminUserData getNamminUserData(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		if (validateSession(request)) {
			Object initData=AppSession.getNamminUserData(request);
			if(initData!=null)
				return (NamminUserData) initData;
			HttpSession userSession = request.getSession();
			return (NamminUserData) userSession.getAttribute("userData");
		}
		response.sendRedirect(Consts.LOGIN_NAMMIN_SERVLET_URL);
		return null;
	}
}
