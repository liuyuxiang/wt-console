package com.wt.uum.shiro.authc;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * <pre>
 * 业务名: 设备token
 * 功能说明: 设备token
 * 编写日期:	2011-11-25
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceToken implements AuthenticationToken
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9147417345893163119L;

	/**
	 * 设备id
	 */
	private String devId;
	/**
	 * userid
	 */
	private String userId;
	/**
	 * 验证码
	 */
	private String authCode;

	/**
	 * @param devId
	 *            devId
	 * @param userId
	 *            userId
	 * @param authCode
	 *            authCode
	 */
	public DeviceToken(String devId, String userId, String authCode)
	{
		super();
		this.devId = devId;
		this.userId = userId;
		this.authCode = authCode;
	}

	public String getDevId()
	{
		return devId;
	}

	public void setDevId(String devId)
	{
		this.devId = devId;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getAuthCode()
	{
		return authCode;
	}

	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public Object getPrincipal()
	{
		return devId;
	}

	public Object getCredentials()
	{
		return authCode;
	}

}
