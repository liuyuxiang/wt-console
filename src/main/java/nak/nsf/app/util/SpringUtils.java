package nak.nsf.app.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SpringUtils
{
	private static ApplicationContext ctx;

	static {
		Properties props = new Properties();
		try {
			props
					.load(ClassLoader
							.getSystemResourceAsStream("com/hirisun/uum2/log4j/log4j.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		PropertyConfigurator.configure(props);
		ctx = new ClassPathXmlApplicationContext(
				"com/hirisun/uum2/context/applicationContext.xml");
	}

	public static ApplicationContext getApplicationContext() {
		return ctx;
	}
}
