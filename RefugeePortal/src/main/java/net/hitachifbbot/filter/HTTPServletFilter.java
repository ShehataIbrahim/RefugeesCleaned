package net.hitachifbbot.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class HTTPServletFilter implements Filter {
	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		if(!(request instanceof HttpServletRequest)) { // assert
			chain.doFilter(request, response);
			return;
		}
		if(!(response instanceof HttpServletResponse)){ // assert
			chain.doFilter(request, response);
			return;
		}
		if(doFilter((HttpServletRequest)request,(HttpServletResponse)response)){
			chain.doFilter(request, response);
		}
	}

	/**
	 * フィルタリングを行う
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @return requestを中断させる場合はfalse,そうでなければtrue
	 * @throws IOException
	 * @throws ServletException
	 */
	protected abstract boolean doFilter(HttpServletRequest request,HttpServletResponse response)
			throws IOException, ServletException;

}
