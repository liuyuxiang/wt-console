package com.wt.uum2.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 属性值
 * 编写日期:	2013-1-7
 * 作者:	liuyx
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ServerLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1554455254874360847L;

	public static String STARTING = "Starting!";

	public static String STOPPING = "Stopping!";

	public static String UNKNOWN = "Unknown Status!";

	/**
	 * @param status
	 *            status
	 */
	public ServerLog(String status)
	{
		this();
		setStatus(StringUtils.isBlank(status) ? UNKNOWN : status);
	}

	/**
	 * 
	 */
	public ServerLog()
	{
		super();
		setHandleTime(Calendar.getInstance().getTime());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	/**
	 * 方法说明：hashCode
	 * 
	 * @return int
	 */
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	/**
	 * 方法说明：equals
	 * 
	 * @param obj
	 *            obj
	 * @return boolean
	 */
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServerLog other = (ServerLog) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/**
	 * 主键
	 */
	private String uuid;
	private String osArch;
	private String osName;
	private String osVersion;
	private String javaHome;
	private String javaVersion;
	private String userName;
	private String userDir;
	private String userHome;
	private String fileEncoding;
	private String mac;
	private String ip;
	private Date handleTime;
	private String status;
	private String projectVer;
	private String projectId;

	/**
	 * @return ssssssss
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getOsArch()
	{
		return osArch;
	}

	public void setOsArch(String osArch)
	{
		this.osArch = osArch;
	}

	public String getOsName()
	{
		return osName;
	}

	public void setOsName(String osName)
	{
		this.osName = osName;
	}

	public String getOsVersion()
	{
		return osVersion;
	}

	public void setOsVersion(String osVersion)
	{
		this.osVersion = osVersion;
	}

	public String getJavaHome()
	{
		return javaHome;
	}

	public void setJavaHome(String javaHome)
	{
		this.javaHome = javaHome;
	}

	public String getJavaVersion()
	{
		return javaVersion;
	}

	public void setJavaVersion(String javaVersion)
	{
		this.javaVersion = javaVersion;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getUserDir()
	{
		return userDir;
	}

	public void setUserDir(String userDir)
	{
		this.userDir = userDir;
	}

	public String getUserHome()
	{
		return userHome;
	}

	public void setUserHome(String userHome)
	{
		this.userHome = userHome;
	}

	public String getFileEncoding()
	{
		return fileEncoding;
	}

	public void setFileEncoding(String fileEncoding)
	{
		this.fileEncoding = fileEncoding;
	}

	public String getMac()
	{
		return mac;
	}

	public void setMac(String mac)
	{
		this.mac = mac;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public Date getHandleTime()
	{
		return handleTime;
	}

	public void setHandleTime(Date handleTime)
	{
		this.handleTime = handleTime;
	}

	public String getStatus()
	{
		return status;
	}

	public void setStatus(String status)
	{
		this.status = status;
	}

	public String getProjectVer()
	{
		return projectVer;
	}

	public void setProjectVer(String projectVer)
	{
		this.projectVer = projectVer;
	}

	public String getProjectId()
	{
		return projectId;
	}

	public void setProjectId(String projectId)
	{
		this.projectId = projectId;
	}

}
