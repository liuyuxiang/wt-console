package com.wt.hea.common.infrastructure.verify;

/**
 * 
 * <pre>
 * 业务名:服务器端验校验接口
 * 功能说明: 常用服务器端校验
 * 编写日期:	2011-3-29
 * 作者:	yuanmingmin
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface Verify
{

	/**
	 * 
	 * 方法说明： 测试字符串是否为数值
	 * 
	 * @param str
	 *            String value 被测试字符串
	 * @return boolean 数值为true
	 */
	public boolean isNumeric(String str);

	/**
	 * 
	 * 方法说明： 判断字符串是否为空
	 * 
	 * @param str
	 *            测试的字符串
	 * @return true or false 为空返回true
	 */
	public boolean isEmpty(String str);

	/**
	 * 
	 * 方法说明：校验是不否是邮件格式
	 * 
	 * @param email
	 *            邮件字符串
	 * @return 如果是邮件，返回真
	 */
	public boolean isEmail(String email);

	/**
	 * 
	 * 方法说明：是否是日期 日期格式为2010-09-09
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @return 如果是，返回真
	 */
	public boolean isDate(String dateStr);

	/**
	 * 
	 * 方法说明： 日期是否遵循dateParttn指定的格式，full参数为是否完全匹配
	 * 
	 * @param date
	 *            日期串
	 * @param dateParttern
	 *            匹配规则
	 * @param full
	 *            是否全部匹配
	 * @return 是否完全匹配dateparttn指定的格式
	 */
	public boolean isDate(String date, String dateParttern, boolean full);

	/**
	 * 方法说明:检查字符串是否非空
	 * 
	 * @param str
	 *            要检验的字符串
	 * @return 检查检查字符串不是null或""，就返回真
	 */
	public boolean isNotEmpty(String str);

	/**
	 * 
	 * 方法说明： 是否为url
	 * 
	 * @param url
	 *            校验字符串
	 * @return 校验结果
	 */
	public boolean isUrl(String url);

	/**
	 * 
	 * 方法说明： 是否全部为字母
	 * 
	 * @param str
	 *            校验字符串
	 * @return 校验结果
	 */
	public boolean isAlpha(String str);

	/**
	 * 
	 * 方法说明：是否为空白， 空白指：null，""，tab，回车，空格，和其它非显示字符
	 * 
	 * @param str
	 *            校验字符串
	 * @return 校验结果
	 */
	public boolean isBlank(String str);

}
