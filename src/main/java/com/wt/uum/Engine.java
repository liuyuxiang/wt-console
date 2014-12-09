package com.wt.uum;

import java.util.Properties;
import java.util.TimeZone;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.hirisun.components.appserver.detector.AppServerDetector;
import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.components.other.runtime.RuntimeResolver;
import com.wt.hea.rs.client.RSConfig;
import com.wt.uum.version.UumCoreVersionUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-8
 * 作者:	xiao noah
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Engine
{

	private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

	private static ApplicationContext springContext;

	private static boolean alreadyConfigDefaultLog4j;

	private static boolean running;

	/**
	 * 方法说明：start
	 * 
	 */
	public static void start()
	{
		setRunning(true);
		TimeZone.setDefault(TimeZone.getTimeZone("CTT"));
		configDefaultLog4j();
		UumCoreVersionUtils.init();
		RuntimeResolver.initSysEnv();
		configLog4j();
		ProjectResolver.initSysEnv("nsf.project.id");
		AppServerDetector.initSysEnv();
	}
	
	public static boolean isRunning()
	{
		return running;
	}

	private static void setRunning(boolean running)
	{
		Engine.running = running;
	}

	public static ApplicationContext getSpringContext()
	{
		return springContext;
	}

	public static void setSpringContext(ApplicationContext springContext) {
		Engine.springContext = springContext;
	}

	/**
	 * 方法说明：getBean
	 * 
	 * @param name
	 *            name
	 * @return Object
	 */
	public static Object getBean(String name)
	{
		return springContext.getBean(name);
	}

	/**
	 * 方法说明：stop
	 * 
	 */
	public static void stop()
	{
		springContext = null;
		setRunning(false);
	}

	/**
	 * 方法说明：configDefaultLog4j
	 * 
	 */
	public static void configDefaultLog4j()
	{
		if (!alreadyConfigDefaultLog4j) {
			alreadyConfigDefaultLog4j = true;

			Properties props = new Properties();
			try {
				props.load(Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("com/hirisun/uum2/log4j/log4j-default.properties"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			PropertyConfigurator.configure(props);
		}
	}

	/**
	 * 方法说明：configLog4j
	 * 
	 */
	public static void configLog4j()
	{
		Properties props = new Properties();
		try {
			props.load(Thread
					.currentThread()
					.getContextClassLoader()
					.getResourceAsStream("com/hirisun/hea/common/config/log4j.properties"));
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		PropertyConfigurator.configure(props);
	}
	
	private static String appURL;

	public static String getAppURL() {
		return appURL;
	}

	public static void setAppURL(String appBasePath) {
		appURL = appBasePath;
		RSConfig.setServerUrl(appURL+"/rest");
	}
}
