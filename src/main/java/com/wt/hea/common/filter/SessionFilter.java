package com.wt.hea.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import com.hirisun.hea.api.domain.User;
import com.wt.hea.common.util.WebUtil;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 应用的sessionFilter，用于控制用户登录拦截 
 * 编写日期:	2011-4-15
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：2011-10-26
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class SessionFilter implements Filter
{

	/**
	 * @param filterConfig
	 *            filterConfig
	 */
	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	/**
	 * 
	 */
	public void destroy()
	{

	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param req
	 *            req
	 * @param res
	 *            res
	 * @param filterChain
	 *            filterChain
	 * @throws ServletException
	 * @throws IOException
	 *             IOException
	 */
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
			throws IOException, ServletException
	{

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		User user = WebUtil.getSessionUser(request);
		if (user != null) {
			String url = request.getRequestURL().toString();
			if (url.matches(".*/heaUserAction\\.hea.*")) {
				if (StringUtils.isEmpty(request.getParameter("action"))) { // 过滤用户直接在浏览器回车登陆地址
					failedForward(request, response);
					return;
				}
			}
			filterChain.doFilter(request, response);
		} else {
			String url = request.getRequestURL().toString();
			if (url.matches(".*/heaUserAction\\.hea.*")
					|| url.matches(".*/codeSort/manage\\.hea.*")
					|| url.matches(WebUtil.getAppBasePath(request)) || url.matches(".*/rest/.*")
					|| url.matches(".*.nsf") || url.matches(".*/login\\.jsp.*")
					|| url.matches(".*\\.js.*") || url.matches(".*\\.css.*")
					|| url.matches(".*image.*")) {
				if (StringUtils.isEmpty(request.getParameter("action"))
						&& url.matches(".*/heaUserAction\\.hea.*")) { // 过滤用户直接在浏览器回车登陆地址
					failedForward(request, response);
					return;
				}
				filterChain.doFilter(request, response);

			} else {
				failedForward(request, response);
			}
		}
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	private void failedForward(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException
	{
		String str = "javascript:top.location.href='" + WebUtil.getAppBasePath(request) + "' ";
		request.setAttribute("message", "会话过期,请重新登陆!<a href=\"" + str + "\">登陆</a>");
		request.getRequestDispatcher("/heaconsole/web/message.jsp").forward(request, response);
	}

}