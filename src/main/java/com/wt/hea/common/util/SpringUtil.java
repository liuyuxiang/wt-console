package com.wt.hea.common.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;



/**
 * 
 * <pre>
 * 业务名:为获取spring容器内的bean提供支持的工具类
 * 功能说明: 获取spring容器bean对象 (注：此类一般不用于业务方法调用，需解析xml有性能影响)
 * 编写日期:	2011-5-14
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-14
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class SpringUtil {
	/**
	 * spring 容器上下文对象
	 */
	private static ApplicationContext configs;
	static{
		 configs=new ClassPathXmlApplicationContext(new String[]{
			"com/hirisun/hea/common/config/applicationContext.xml"
		});
	}
	
	/**
	 * 获取spring容器里各种bean（注：只推荐使用业务bean）
	 * @param name beanid值
	 * @return 返回spring容器的bean对象
	 */
	public static Object getBean(String name){
		return configs.getBean( name);
	}
	
}
