package com.wt.hea.rs.client;

import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.slf4j.Logger;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-7-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class RSConfig
{

	/**
	 * 
	 */
	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RSConfig.class);

	/**
	 * 
	 */
	public static String SERVER_URL;

	public static String getServerUrl()
	{
		return SERVER_URL;
	}
	
	public static void setServerUrl(String url)
	{
		SERVER_URL = url;
		logger.warn(SERVER_URL);
	}

}
