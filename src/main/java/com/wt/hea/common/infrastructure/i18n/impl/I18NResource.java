package com.wt.hea.common.infrastructure.i18n.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.wt.hea.common.infrastructure.i18n.I18N;

/**
 * 
 * <pre>
 * 业务名: 国际化接口实现
 * 功能说明: 实现应用系统的国际化功能，如异常信息 ，国际化抛出，页面按钮国际化等
 * 编写日期:	2011-3-31
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-28
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class I18NResource implements I18N
{
	/**
	 * 
	 */
	private I18NResource()
	{
	}

	/**
	 * 国际化资源Map存储
	 */
	private static Map<String, Properties> RESOURCE_MAP = null;

	/**
	 * 单例对象定义
	 */
	private static I18N I18NRESOURCE = null;
	static {
		I18NRESOURCE = new I18NResource();
	}

	/**
	 * 
	 * 方法说明：单例对象获取，国际化资源对象
	 * 
	 * @return I18N实例对象
	 */
	public static I18N getInstance()
	{
		if (I18NRESOURCE == null) {
			I18NRESOURCE = new I18NResource();
		}
		return I18NRESOURCE;
	}

	// 国际化资源文件初使
	static {
		try {
			RESOURCE_MAP = new HashMap<String, Properties>();
			Properties props_zh_CN = PropertiesLoaderUtils
					.loadAllProperties("com/hirisun/hea/common/config/ApplicationResources_zh_CN.properties");
			Properties props_en = PropertiesLoaderUtils
					.loadAllProperties("com/hirisun/hea/common/config/ApplicationResources_en.properties");

			RESOURCE_MAP.put(Locale.CHINESE.toString(), props_zh_CN);
			RESOURCE_MAP.put(Locale.CHINA.toString(), props_zh_CN);

			RESOURCE_MAP.put(Locale.ENGLISH.toString(), props_en);
			RESOURCE_MAP.put(Locale.US.toString(), props_en);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	/**
	 * 跟据本地化对象，和键值，获取国际化后的资源文件值、
	 * 
	 * @param key
	 *            资源文件键名称
	 * @param locale
	 *            本地化对象
	 * @return 返回国际化后的值
	 */
	public String getValue(String key, Locale locale)
	{

		return RESOURCE_MAP.get(locale.toString()).getProperty(key);
	}

	/**
	 * @param key
	 *            key
	 * @return String
	 */
	public String getValue(String key)
	{
		return getValue(key, Locale.getDefault());
	}

}
