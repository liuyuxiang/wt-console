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

/**
 * <pre>
 * 业务名: 异常处理
 * 功能说明: 捕获异常，log输出，并在500.jsp中显示
 * 编写日期:	2011-10-13
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ExceptionFilter implements Filter
{

	/**
	 * LOG
	 */
	private static final Log LOGGER = LogFactory.getLog(ExceptionFilter.class);

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
		try {
			chain.doFilter(request, response);
		} catch (Throwable t) {
			if (!(t instanceof java.net.SocketException && t.getMessage().contains(
					"Connection reset by peer: socket write error (ClientIpInfoFilter.java:77)"))) {
				LOGGER.error(t, t);
				HttpServletRequest req = (HttpServletRequest) request;
				request.setAttribute("javax.servlet.error.exception", t);
				req.getRequestDispatcher("/500.jsp").forward(request, response);
			}
		}
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
