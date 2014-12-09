package com.wt.uum2.constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * <pre>
 * 业务名:自定义PropertyPlaceholderConfigurer
 * 功能说明: 利用CustomizedPropertyPlaceholderConfigurer.getContextProperty(String key)获取PropertyPlaceholderConfigurer定义的环境变量
 * 编写日期:	2012-11-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class CustomizedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer
{

	private static Map<String, Object> ctxPropertiesMap;

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#processProperties(org.springframework.beans.factory.config.ConfigurableListableBeanFactory, java.util.Properties)
	 */
	/**
	 * 方法说明：processProperties
	 * 
	 * @param beanFactoryToProcess
	 *            beanFactoryToProcess
	 * @param props
	 *            props
	 * @throws BeansException
	 *             BeansException
	 */
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException
	{
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<String, Object>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

	/**
	 * 方法说明：getContextProperty
	 * 
	 * @param name
	 *            name
	 * @return Object
	 */
	public static Object getContextProperty(String name)
	{
		return ctxPropertiesMap.get(name);
	}

	public static Map<String, Object> getPropertiesMap()
	{
		return ctxPropertiesMap;
	}

}