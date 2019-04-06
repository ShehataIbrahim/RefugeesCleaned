package com.refugees.filters;

import com.refugees.common.Validator;
import net.hitachifbbot.Consts;
import net.hitachifbbot.filter.HTTPServletFilter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

public class UserSecurityFilter extends HTTPServletFilter {

	Logger logger=Logger.getLogger(UserSecurityFilter.class.getCanonicalName());
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.info("Applicaton is running on Server:"+filterConfig.getServletContext().getServerInfo());
	}

	@Override
	public void destroy() {
		
	}

	@Override
	protected boolean doFilter(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		if(!Validator.validateSession(request)) {
			response.sendRedirect(Consts.USER_LOGIN_SERVLET_URL);
		}
		return true;
	}
}
