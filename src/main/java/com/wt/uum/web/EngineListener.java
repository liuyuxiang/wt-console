package com.wt.uum.web;

import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.wt.uum.Engine;
import com.wt.uum.web.version.UumWebVersionUtils;
import com.wt.uum2.domain.ServerLog;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.impl.LifecycleServiceImpl;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EngineListener extends ContextLoader implements ServletContextListener,ServletRequestListener
{

	/**
	 * 
	 */
	private static final Log LOGGER = LogFactory.getLog(EngineListener.class);

	/**
	 * 
	 */
	private ContextLoader contextLoader;

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	/**
	 * 方法说明：contextInitialized
	 * 
	 * @param sce
	 *            sce
	 */
	public void contextInitialized(ServletContextEvent sce)
	{
		Engine.configDefaultLog4j();

		UumWebVersionUtils.init(sce.getServletContext());

		Engine.start();

		if (this.contextLoader == null) {
			this.contextLoader = this;
		}
		this.contextLoader.initWebApplicationContext(sce.getServletContext());

		setWebSpringContext(EngineListener.getCurrentWebApplicationContext());
		
		Engine.setSpringContext(EngineListener.getCurrentWebApplicationContext());
		createServerLog(ServerLog.STARTING);

	}

	/**
	 * 
	 */
	private static WebApplicationContext webSpringContext;

	public static WebApplicationContext getWebSpringContext()
	{
		return webSpringContext;
	}

	private static void setWebSpringContext(WebApplicationContext webSpringContext)
	{
		EngineListener.webSpringContext = webSpringContext;
	}
	
	/**
	 * 方法说明：createServerLog
	 * 
	 * @param status
	 *            status
	 */
	private void createServerLog(String status)
	{
		UUMService uumService = (UUMService) EngineListener.getCurrentWebApplicationContext()
				.getBean("uumService");
		LifecycleServiceImpl lifecycleService = new LifecycleServiceImpl();
		uumService.createServerLog(lifecycleService.getNewServerLog(status));
	}

	/* (non-Javadoc)
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	/**
	 * 方法说明：contextDestroyed
	 * 
	 * @param sce
	 *            sce
	 */
	public void contextDestroyed(ServletContextEvent sce)
	{

		createServerLog(ServerLog.STOPPING);

		Engine.stop();

		EngineListener.getCurrentWebApplicationContext().getBean("lifecycleService");

		if (this.contextLoader != null) {
			this.contextLoader.closeWebApplicationContext(sce.getServletContext());
		}
		cleanupAttributes(sce.getServletContext());

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 方法说明：cleanupAttributes
	 * 
	 * @param sc
	 *            sc
	 */
	@SuppressWarnings("unchecked")
	static void cleanupAttributes(ServletContext sc)
	{
		Enumeration attrNames = sc.getAttributeNames();
		while (attrNames.hasMoreElements()) {
			String attrName = (String) attrNames.nextElement();
			if (attrName.startsWith("org.springframework.")) {
				Object attrValue = sc.getAttribute(attrName);
				if (attrValue instanceof DisposableBean) {
					try {
						((DisposableBean) attrValue).destroy();
					} catch (Throwable ex) {
						LOGGER.error("Couldn't invoke destroy method of attribute with name '"
								+ attrName + "'", ex);
					}
				}
			}
		}
	}

	public void requestDestroyed(ServletRequestEvent sre) {
	}

	public void requestInitialized(ServletRequestEvent sre) {
		if(StringUtils.isBlank(Engine.getAppURL())){
			Engine.setAppURL(getServerURL(sre));
		}
	}

	private String getServerURL(ServletRequestEvent sre) {
		
		StringBuilder sb = new StringBuilder();
		sb.append(sre.getServletRequest().getScheme());
		sb.append("://");
		sb.append(sre.getServletRequest().getServerName());
		if(sre.getServletRequest().getServerPort()!=80){
			sb.append(":").append(sre.getServletRequest().getServerPort());
		}
		sb.append(sre.getServletContext().getContextPath());
		return sb.toString();
	}

}
