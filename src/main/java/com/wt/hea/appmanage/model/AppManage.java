package com.wt.hea.appmanage.model;

import java.io.Serializable;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-26
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class AppManage implements Serializable
{

	/**
	 * 应用id
	 */
	private String appId;

	/**
	 * 应用标识
	 */
	private String appNo;

	/**
	 * 应用名称
	 */
	private String appName;

	/**
	 * 应用描述
	 */
	private String appDesc;

	/**
	 * 系统类型,01应用、02门户
	 */
	private String appType;

	/**
	 * 系统地址
	 */
	private String appAddr;

	/**
	 * 系统目录
	 */
	private String appCataLog;

	/**
	 * 数据库信息
	 */
	private String dbInfo;

	/**
	 * 数据库用户
	 */
	private String dbUser;

	/**
	 * 数据库密码
	 */
	private String dbPassword;

	/**
	 * 系统状态，使用01不使用02
	 */
	private String appStatus;

	/**
	 * 运行状态
	 */
	private String runStatus;

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getAppNo()
	{
		return appNo;
	}

	public void setAppNo(String appNo)
	{
		this.appNo = appNo;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getAppDesc()
	{
		return appDesc;
	}

	public void setAppDesc(String appDesc)
	{
		this.appDesc = appDesc;
	}

	public String getAppType()
	{
		return appType;
	}

	public void setAppType(String appType)
	{
		this.appType = appType;
	}

	public String getAppAddr()
	{
		return appAddr;
	}

	public void setAppAddr(String appAddr)
	{
		this.appAddr = appAddr;
	}

	public String getAppCataLog()
	{
		return appCataLog;
	}

	public void setAppCataLog(String appCataLog)
	{
		this.appCataLog = appCataLog;
	}

	public String getDbInfo()
	{
		return dbInfo;
	}

	public void setDbInfo(String dbInfo)
	{
		this.dbInfo = dbInfo;
	}

	public String getDbUser()
	{
		return dbUser;
	}

	public void setDbUser(String dbUser)
	{
		this.dbUser = dbUser;
	}

	public String getDbPassword()
	{
		return dbPassword;
	}

	public void setDbPassword(String dbPassword)
	{
		this.dbPassword = dbPassword;
	}

	public String getAppStatus()
	{
		return appStatus;
	}

	public void setAppStatus(String appStatus)
	{
		this.appStatus = appStatus;
	}

	public String getRunStatus()
	{
		return runStatus;
	}

	public void setRunStatus(String runStatus)
	{
		this.runStatus = runStatus;
	}
}
