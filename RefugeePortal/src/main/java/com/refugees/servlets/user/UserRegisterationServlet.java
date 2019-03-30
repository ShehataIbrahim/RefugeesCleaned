package com.refugees.servlets.user;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang3.StringUtils;

import com.refugees.common.Validator;
import com.refugees.db.model.RefugeeUser;
import com.refugees.db.service.RefugeeUserService;

import net.hitachifbbot.Consts;
import net.hitachifbbot.DB;
import net.hitachifbbot.model.PasswordHash;
import net.hitachifbbot.servlet.AppServlet;

/**
 * Servlet implementation class User Registeration
 */
public class UserRegisterationServlet extends AppServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserRegisterationServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (Validator.validateSession(request)) {
			response.sendRedirect(Consts.USER_HOME_SERVLET_URL);
		} else
			forwardJSP("/user/register.jsp", request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// get user data
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String confirm_password = request.getParameter("confirm_password");
		// data validations
		boolean hasError = false;
		if (StringUtils.isEmpty(username)) {
			forwardJSPWithError("/user/register.jsp", request, response, "register.emptyUsername");
			hasError = true;
		}
		if (StringUtils.isEmpty(password)) {
			forwardJSPWithError("/user/register.jsp", request, response, "register.emptyPassword");
			hasError = true;
		}
		if (StringUtils.isEmpty(confirm_password)) {
			forwardJSPWithError("/user/register.jsp", request, response, "register.emptyConfirmPassword");
			hasError = true;
		}
		if (StringUtils.isEmpty(email)) {
			forwardJSPWithError("/user/register.jsp", request, response, "register.emptyEmail");
			hasError = true;
		}
		if (!password.equals(confirm_password)) {
			forwardJSPWithError("/user/register.jsp", request, response, "register.passwordConfirmation");
			hasError = true;
		}
		RefugeeUser user = new RefugeeUser();
		user.setUserName(username);
		try {
			PasswordHash hash = new PasswordHash(password);
			user.setPassword(hash.hash);
			user.setPasswordSalt(hash.salt);
		} catch (GeneralSecurityException e) {
			log(e.getMessage(), e);
			forwardJSPWithError("/user/register.jsp", request, response, "login.general_error");
			hasError = true;
		} catch (DecoderException e) {
			log(e.getMessage(), e);
			forwardJSPWithError("/user/register.jsp", request, response, "login.general_error");
			hasError = true;
		}
		user.setEmail(email);
		try {
			RefugeeUserService.registerUser(user);
		} catch (SQLException e) {
			log(e.getMessage(), e);
			forwardJSPWithError("/user/register.jsp", request, response, "login.general_error");
			hasError = true;
		}
		if (!hasError)
			response.sendRedirect(Consts.USER_LOGIN_SERVLET_URL);

	}

	private void forwardJSPWithError(String jsp, HttpServletRequest request, HttpServletResponse response,
			String messageCode) throws ServletException, IOException {
		request.setAttribute("errorMessage", messageCode);
		forwardJSP("/user/login.jsp", request, response);
	}
}
