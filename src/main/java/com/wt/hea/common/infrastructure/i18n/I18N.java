package com.wt.hea.common.infrastructure.i18n;

import java.util.Locale;

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
public interface I18N
{

	/**
	 * 跟据本地化对象，和键值，获取国际化后的资源文件值、
	 * 
	 * @param key
	 *            资源文件键名称
	 * @param locale
	 *            本地化对象
	 * @return 返回国际化后的值
	 */
	String getValue(String key, Locale locale);

	/**
	 * 
	 * 方法说明： 跟据键值获取国际化后的资源文件值
	 * 
	 * @param key
	 *            资源文件键名称
	 * @return 返回国际化后的值
	 */
	String getValue(String key);
}
