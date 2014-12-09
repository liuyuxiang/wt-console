package com.wt.hea.common.filter;

import java.io.IOException;
import java.util.TimeZone;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.hirisun.components.ContextPathHolderFilter;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebComponentConfig;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-12-17
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SysGlobarInitContext extends ContextPathHolderFilter
{

	/**
	 * 获取日志实例
	 */
	private Logger log = LoggerService.getInstance();

	@Override
	public void destroy()
	{
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{

		if (!isInitContextPath) {
			initContextPathIfNot(((HttpServletRequest) request).getSession().getServletContext());
		}
		super.doFilter(request, response, chain);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{
		super.init(filterConfig);
		TimeZone tz = TimeZone.getTimeZone("Asia/Shanghai");
		TimeZone.setDefault(tz);
		log.info("设置时区为Asia/Shanghai");
		initContextPathIfNot(filterConfig.getServletContext());
	}

	/**
	 * 
	 */
	private boolean isInitContextPath = false;

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param servletContext
	 *            servletContext
	 */
	private void initContextPathIfNot(ServletContext servletContext)
	{

		if (!isInitContextPath) {

			String contextPath = getContextPath();
			if (contextPath != null) {
				servletContext.setAttribute("ctx", contextPath);
				servletContext.setAttribute("componentConfig", new WebComponentConfig(contextPath));
				// isInitContextPath = true;
			}
		}
	}

}
