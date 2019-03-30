package net.hitachifbbot.filter;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hitachifbbot.Consts;
import net.hitachifbbot.model.NamminUserData;
import net.hitachifbbot.session.AppSession;

public class RequireNamminLoginFilter extends HTTPServletFilter {

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {
		// なにもしない
	}

	@Override
	protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		boolean result = AppSession.getUserData(request) instanceof NamminUserData;
		if(!result){
			response.sendRedirect(Consts.LOGIN_NAMMIN_SERVLET_URL);
		}
		return result;
	}

	@Override
	public void destroy() {
		// なにもしない
	}

}
