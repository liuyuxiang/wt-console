package com.wt.hea.common.infrastructure.verify.impl;

import java.text.SimpleDateFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.DateValidator;
import org.apache.commons.validator.EmailValidator;
import org.apache.commons.validator.UrlValidator;

import com.wt.hea.common.infrastructure.verify.Verify;

/**
 * 
 * <pre>
 * 业务名:服务器端验证接口
 * 功能说明: 常用服务器端验证(注:该较验类已移至components-data组件内!)
 * 编写日期:	2011-3-29
 * 作者:	yuanmingmin
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Verifier implements Verify
{

	/**
	 * 私有构造函数
	 */
	private Verifier()
	{
	}

	/**
	 * 方法说明:检查字符串是否为null或"" ，如果是，返回真
	 * 
	 * @param str
	 *            字符串引用对象
	 * @return 检查字符串是否为null或"" ，如果是，返回真
	 */
	public boolean isEmpty(String str)
	{
		return StringUtils.isEmpty(str);
	}

	/**
	 * 方法说明:检查字符串是否为数值，如果是，返回真
	 * 
	 * @param str
	 *            字符串引用对象
	 * @return 检查检查字符串是否为数值，如果是，返回真
	 */
	public boolean isNumeric(String str)
	{
		if (isEmpty(str))
			return false;

		return StringUtils.isNumeric(str);
	}

	/**
	 * 方法说明:检查字符串是否为日期，如果是，返回真
	 * 
	 * @param dateStr
	 *            字符串引用对象
	 * @return 检查检查字符串是否为日期，如果是，返回真
	 */
	public boolean isDate(String dateStr)
	{
		if (isEmpty(dateStr))
			return false;

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			sdf.setLenient(false);
			sdf.parse(dateStr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 方法说明:检查字符串是否邮件格式，如果是，返回真
	 * 
	 * @param email
	 *            邮件格式字符串引用对象
	 * @return 检查检查字符串是否为日期，如果是，返回真
	 */
	public boolean isEmail(String email)
	{
		return EmailValidator.getInstance().isValid(email);
	}

	/**
	 * 方法说明:检查字符串是否非空
	 * 
	 * @param str
	 *            要检验的字符串
	 * @return 检查检查字符串不是null或""，就返回真
	 */
	public boolean isNotEmpty(String str)
	{
		return StringUtils.isNotEmpty(str);
	}

	/**
	 * @param url
	 *            url
	 * @return boolean
	 */
	public boolean isUrl(String url)
	{
		return new UrlValidator().isValid(url);
	}

	/**
	 * @param date
	 *            date
	 * @param dateParttern
	 *            dateParttern
	 * @param full
	 *            full
	 * @return boolean
	 */
	public boolean isDate(String date, String dateParttern, boolean full)
	{
		return DateValidator.getInstance().isValid(date, dateParttern, full);
	}

	/**
	 * @param str
	 *            str
	 * @return boolean
	 */
	public boolean isAlpha(String str)
	{
		return StringUtils.isAlpha(str);
	}

	/**
	 * @param str
	 *            str
	 * @return boolean
	 */
	public boolean isBlank(String str)
	{
		return StringUtils.isBlank(str);
	}

}
