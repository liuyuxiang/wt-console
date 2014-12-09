package com.wt.uum2.web.exception.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RedirctFilter implements Filter
{

	/**
	 * LOG
	 */
	private static final Log LOGGER = LogFactory.getLog(RedirctFilter.class);

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	/**
	 * 方法说明：init
	 * 
	 * @param filterConfig
	 *            filterConfig
	 * @throws ServletException
	 *             ServletException
	 */
	public void init(FilterConfig filterConfig) throws ServletException
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	/**
	 * 方法说明：doFilter
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param chain
	 *            chain
	 * @throws IOException
	 * @throws ServletException
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest a = (HttpServletRequest)request;
		//if(a.getRequestURI())
			chain.doFilter(request, response);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#destroy()
	 */
	/**
	 * 方法说明：destroy
	 * 
	 */
	public void destroy()
	{
		// TODO Auto-generated method stub

	}

}
