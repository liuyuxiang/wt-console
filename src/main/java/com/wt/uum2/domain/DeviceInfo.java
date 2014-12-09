package com.wt.uum2.domain;

import java.util.Date;

import com.wt.uum.device.DeviceStatus;

/**
 * <pre>
 * 业务名: 设备验证
 * 功能说明: 设备验证信息
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceInfo
{

	/**
	 * UUID
	 */
	private String uuid;

	/**
	 * user ID
	 */
	private String userId;

	/**
	 * 设备 ID
	 */
	private String deviceId;

	/**
	 * 设备的验证码
	 */
	private String authCode;

	/**
	 * 最后更新时间
	 */
	private Date modified;

	/**
	 * 设备名
	 */
	private String name;

	/**
	 * 版本
	 */
	private String version;

	/**
	 * IP address
	 */
	private String ip;

	/**
	 * 状态
	 */
	private DeviceStatus status;

	/**
	 * 计数器
	 */
	private Integer count;

	/**
	 * 
	 */
	public DeviceInfo()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param name
	 *            userId
	 * @param version
	 *            version
	 */
	public DeviceInfo(String userId, String deviceId, String name, String version)
	{
		this();
		this.userId = userId;
		this.deviceId = deviceId;
		this.name = name;
		this.version = version;
	}

	public String getUuid()
	{
		return uuid;
	}

	protected void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getDeviceId()
	{
		return deviceId;
	}

	public void setDeviceId(String deviceId)
	{
		this.deviceId = deviceId;
	}

	public String getAuthCode()
	{
		return authCode;
	}

	public void setAuthCode(String authCode)
	{
		this.authCode = authCode;
	}

	public Date getModified()
	{
		return modified;
	}

	public void setModified(Date modified)
	{
		this.modified = modified;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public DeviceStatus getStatus()
	{
		return status;
	}

	public void setStatus(DeviceStatus status)
	{
		this.status = status;
	}

	public Integer getCount()
	{
		return count;
	}

	public void setCount(Integer count)
	{
		this.count = count;
	}

	/**
	 * 方法说明：addCount
	 * 
	 */
	public void addCount()
	{
		if (this.count == null) {
			setCount(1);
			return;
		}

		setCount(++count);

	}

}
