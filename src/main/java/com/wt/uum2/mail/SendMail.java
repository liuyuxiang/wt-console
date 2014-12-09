package com.wt.uum2.mail;

import javax.mail.internet.InternetAddress;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface SendMail
{
	/**
	 * 
	 */
	final int TEMPLATE = 0;
	/**
	 * 
	 */
	final int RESETPWD = 1;
	/**
	 * 
	 */
	final int REGISTER = 2;

	/**
	 * 重置密码邮件
	 * 
	 * @param to
	 *            操作用户的信息
	 */
	public void resetPasswordMail(InternetAddress to);

	/**
	 * 开通邮箱
	 * 
	 * @param from
	 *            操作用户的信息
	 * @param msgs
	 *            待开通用户的信息
	 */
	public void openMail(InternetAddress from, String msgs);

	/**
	 * 发送邮件
	 * 
	 * @param subject
	 *            subject
	 * @param from
	 *            from
	 * @param to
	 *            to
	 * @param text
	 *            text
	 */
	public void sendMail(String subject, InternetAddress from, InternetAddress to, String text);

}
