package net.hitachifbbot.filter;

import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.hitachifbbot.Consts;
import net.hitachifbbot.model.DoctorUserData;
import net.hitachifbbot.session.AppSession;

public class RequireDoctorLoginFilter extends HTTPServletFilter {

	@Override
	public void init(FilterConfig paramFilterConfig) throws ServletException {
		// なにもしない
	}

	@Override
	protected boolean doFilter(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		boolean result = AppSession.getUserData(request) instanceof DoctorUserData;
		if(!result){
			response.sendRedirect(Consts.LOGIN_DOCTOR_SERVLET_URL);
		}
		return result;
	}

	@Override
	public void destroy() {
		// なにもしない
	}

}
