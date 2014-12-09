package com.wt.hea.common.util;

import org.apache.commons.lang.StringEscapeUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 扩展第三方的转码工具类
 * 编写日期:	2011-11-9
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EscapeUtils
{

	/**
	 * 方法说明： html代码转码
	 * 
	 * @param str
	 *            htmlcode
	 * @return 转码后的html串
	 */
	public static String escapeHtml(String str)
	{
		return StringEscapeUtils.escapeHtml(str);
	}

	/**
	 * 方法说明： 解码html代码
	 * 
	 * @param str
	 *            转码后的htmlcode
	 * @return 解码后的htmlcode
	 */
	public static String unescapeHtml(String str)
	{
		return StringEscapeUtils.unescapeHtml(str);
	}

	/**
	 * 方法说明： 转码java代码
	 * 
	 * @param str
	 *            javacode
	 * @return 转码后的java代码
	 */
	public static String escapeJava(String str)
	{
		return StringEscapeUtils.unescapeJava(str);
	}

	/**
	 * 方法说明： 解码java代码
	 * 
	 * @param str
	 *            转码后的javacode
	 * @return javacode
	 */
	public static String unescapeJava(String str)
	{
		return StringEscapeUtils.unescapeJava(str);
	}

	/**
	 * 方法说明： 转码javascript
	 * 
	 * @param str
	 *            javascript
	 * @return 转码后的javascript
	 */
	public static String escapeJavaScript(String str)
	{
		return StringEscapeUtils.escapeJavaScript(str);
	}

	/**
	 * 方法说明： 解码javascript
	 * 
	 * @param str
	 *            转码后的javascript
	 * @return javascript
	 */
	public static String unescapeJavaScript(String str)
	{
		return StringEscapeUtils.unescapeJavaScript(str);
	}

	/**
	 * 方法说明： 转码XML
	 * 
	 * @param str
	 *            XML
	 * @return 转码后的XML
	 */
	public static String escapeXml(String str)
	{
		return StringEscapeUtils.escapeXml(str);
	}

	/**
	 * 方法说明： 解码XML
	 * 
	 * @param str
	 *            解码后的XML
	 * @return XML
	 */
	public static String unescapeXml(String str)
	{
		return StringEscapeUtils.unescapeXml(str);
	}

}
