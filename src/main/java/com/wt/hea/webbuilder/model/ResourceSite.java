package com.wt.hea.webbuilder.model;

import java.io.Serializable;

/**
 * 站点资源实体bean
 * 
 * @author 袁明敏
 * 
 */
public class ResourceSite implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 资源id
	 */
	private String resId;
	/**
	 * 资源类型 02左侧帘，03右侧帘，04飘窗，05LOGO，07banner
	 */
	private String resType;
	/**
	 * 资源名称
	 */
	private String resName;
	/**
	 * 资源地址
	 */
	private String resPath;
	/**
	 * 资源使用状态
	 */
	private String resStatus;
	/**
	 * 资源宽度
	 */
	private Integer resWidth;
	/**
	 * 资源高度
	 */
	private Integer resHeight;
	/**
	 * 文件大小
	 */
	private Long fileSize;
	/**
	 * 站点id
	 */
	private String siteId;
	/**
	 * 站点名称
	 */
	private String siteName;
	/**
	 * 资源编号
	 */
	private String resCode;

	/**
	 * 应用id
	 */
	private String appId;

	public String getResId()
	{
		return resId;
	}

	public void setResId(String resId)
	{
		this.resId = resId;
	}

	public String getResType()
	{
		return resType;
	}

	public void setResType(String resType)
	{
		this.resType = resType;
	}

	public String getResName()
	{
		return resName;
	}

	public void setResName(String resName)
	{
		this.resName = resName;
	}

	public String getResPath()
	{
		return resPath;
	}

	public void setResPath(String resPath)
	{
		this.resPath = resPath;
	}

	public String getResStatus()
	{
		return resStatus;
	}

	public void setResStatus(String resStatus)
	{
		this.resStatus = resStatus;
	}

	public Integer getResWidth()
	{
		return resWidth;
	}

	public void setResWidth(Integer resWidth)
	{
		this.resWidth = resWidth;
	}

	public Integer getResHeight()
	{
		return resHeight;
	}

	public void setResHeight(Integer resHeight)
	{
		this.resHeight = resHeight;
	}

	public Long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(Long fileSize)
	{
		this.fileSize = fileSize;
	}

	public String getSiteId()
	{
		return siteId;
	}

	public void setSiteId(String siteId)
	{
		this.siteId = siteId;
	}

	public String getSiteName()
	{
		return siteName;
	}

	public void setSiteName(String siteName)
	{
		this.siteName = siteName;
	}

	public String getResCode()
	{
		return resCode;
	}

	public void setResCode(String resCode)
	{
		this.resCode = resCode;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}
}
