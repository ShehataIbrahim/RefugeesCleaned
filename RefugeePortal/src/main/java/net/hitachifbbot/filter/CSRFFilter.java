package net.hitachifbbot.filter;

import net.hitachifbbot.Consts;
import net.hitachifbbot.session.AppSession;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CSRFFilter extends HTTPServletFilter {

	public final static String CSRF_TOKEN_PARAM_NAME = "csrf_token";

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {
		// なにもしない
	}

	@Override
	protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String csrfToken = request.getParameter(CSRF_TOKEN_PARAM_NAME);
		if (csrfToken == null || csrfToken.isEmpty()){ // CSRFトークンが存在しなければエラー
			response.setStatus(400);
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/csrf_bad_request.jsp").forward(request, response);
			return false;
		}
		if (!AppSession.useToken(request,csrfToken)){ // CSRFトークンが正常でなければエラー
			response.setStatus(400);
			request.getServletContext().getRequestDispatcher("/WEB-INF/jsp/error/csrf_bad_request.jsp").forward(request, response);
			return false;
		}
		return true;
	}

	@Override
	public void destroy() {
		// なにもしない
	}

	/**
	 * inputタグを生成します
	 * @param req
	 * @return
	 */
	public static final String getInputTagString(HttpServletRequest req){
		return "<input type=\"hidden\" value=\"" + AppSession.createToken(req) + "\" name=\"" + CSRF_TOKEN_PARAM_NAME + "\">";
	}

	/**
	 * inputタグを生成します
	 * @param req
	 * @return
	 */
	public static final String getCSRFToken(HttpServletRequest req){
		return AppSession.createToken(req);
	}
}
