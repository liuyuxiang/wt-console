package com.wt.uum2.init;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-3
 * 作者:	Liu yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SpringUtils
{

	/**
	 * 
	 */
	private static ApplicationContext ctx;

	/**
	 * 
	 */
	final public static String SYS_ENV_NAME = "nsf.project.id";

	static {
		Properties props = new Properties();
		try {
			props.load(ClassLoader
					.getSystemResourceAsStream("com/hirisun/uum2/log4j/log4j-app.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
	}

	/**
	 * 方法说明：getApplicationContext
	 * 
	 * @param projectId
	 *            projectId
	 * @return ApplicationContext
	 */
	public static ApplicationContext getApplicationContext(String projectId)
	{
		if (ctx == null) {
			System.setProperty(SYS_ENV_NAME, projectId);
			String url = "com/hirisun/uum2/context/" + projectId
					+ "/applicationContext-config-app.xml";
			ctx = new ClassPathXmlApplicationContext(url);
		}
		return ctx;
	}

}
