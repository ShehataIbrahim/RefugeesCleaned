package net.hitachifbbot.filter;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CommonFilter extends HTTPServletFilter {

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

	@Override
	protected boolean doFilter(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {
		request.setCharacterEncoding("utf8");
		return true;
	}

	@Override
	public void destroy() {
	}

}
