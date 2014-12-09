package com.wt.uum2.mail.impl;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hirisun.components.mail.MailException;
import com.hirisun.components.mail.MailService;
import com.hirisun.rd1.secret.Encryptor;
import com.wt.uum2.mail.SendMail;

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
public class SendJavaMail implements SendMail
{

	/**
	 * log
	 */
	private static final Log LOGGER = LogFactory.getLog(SendJavaMail.class);

	/**
	 * 
	 */
	private MailService mailService;

	/**
	 * @param mailService
	 *            the mailService to set
	 */
	public void setMailService(MailService mailService)
	{
		this.mailService = mailService;
	}

	/**
	 * 方法说明：para
	 * 
	 * @param propName
	 *            propName
	 * @return String
	 */
	private String para(String propName)
	{
		Properties prop = new Properties();
		InputStream propIn = getClass().getClassLoader().getResourceAsStream("mail.properties");
		String value = "";
		try {
			prop.load(propIn);
			value = prop.getProperty(propName);
		} catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			try {
				propIn.close();
			} catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return value;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.mail.SendMail#resetPasswordMail(javax.mail.internet.InternetAddress)
	 */
	/**
	 * 方法说明：resetPasswordMail
	 * 
	 * @param to
	 *            to
	 */
	public void resetPasswordMail(InternetAddress to)
	{

		String[] user = to.getPersonal().split(";");
		String userid = user[0];
		String username = user[0];
		if (user.length > 1) {
			username = user[1];
		}
		InternetAddress from = new InternetAddress();
		from.setAddress(para("hirisun.components.mail.username"));
		try {
			from.setPersonal("统一用户管理平台", "UTF-8");
			to.setPersonal(username);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		Encryptor encryptor = new Encryptor();
		String url = null;
		url = "http://portal.hirisun.com/uum2/w/updatePassword?type=reset&key=";
		url += encryptor.encryptString2URL("resetPassword", System.currentTimeMillis() + ","
				+ userid);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("uname", username);
		map.put("url", url);
		map.put("mailto", para("uum.web.mail.manager"));

		try {
			mailService.sendMailByTemplate("重置密码", "resetPwd", map, from, to);
		} catch (MailException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.mail.SendMail#openMail(javax.mail.internet.InternetAddress, java.lang.String)
	 */
	/**
	 * 方法说明：openMail
	 * 
	 * @param from
	 *            from
	 * @param msgs
	 *            msgs
	 */
	public void openMail(InternetAddress from, String msgs)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("usermail", msgs.replaceAll(";", "<br>"));

		String tos = para("uum.web.mail.manager");

		InternetAddress[] to;
		try {
			to = InternetAddress.parse(tos);
			mailService.sendMailByTemplate("请开通新入职员工的邮件", "openMail", map, from, to);
		} catch (AddressException e) {
			LOGGER.error(e.getMessage(), e);
		} catch (MailException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.mail.SendMail#sendMail(java.lang.String, javax.mail.internet.InternetAddress, javax.mail.internet.InternetAddress, java.lang.String)
	 */
	/**
	 * 方法说明：sendMail
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
	public void sendMail(String subject, InternetAddress from, InternetAddress to, String text)
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("content", text);

		try {
			mailService.sendMailByTemplate(subject, "testMail", map, from, to);
		} catch (MailException e) {
			LOGGER.error(e.getMessage(), e);
		}

	}

}
