package com.wt.hea.common.infrastructure.configuration;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明:定义配置管理接口 
 * 编写日期:	2011-3-30
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-30
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public interface Configuration {
	/**
	 * 
	 * 方法说明：获取容器的Bean对象
	 *
	 * @param beanId 容器的内的bean标识
	 * @return 返回容器对象
	 */
	Object  getBean(String beanId);
	
	/**
	 * 
	 * 方法说明：获取属性文件里值对象
	 *
	 * @param key key
	 * @param propFilePath properties文件路径
	 * @return 属性文件里值对象
	 */
	String  getPropValue(String key, String propFilePath);
	
}
