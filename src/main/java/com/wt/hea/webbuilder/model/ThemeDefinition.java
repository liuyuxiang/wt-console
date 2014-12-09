package com.wt.hea.webbuilder.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 主题定义
 * 
 * @author xiaoqi
 * 
 */
public class ThemeDefinition implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 主题编号 由数据库维护
	 */
	private String themeCode;

	/**
	 * 主题名称
	 */
	private String themeName;

	/**
	 * 主题缩略图路径
	 */
	private String themePicPath;

	/**
	 * 主题内容
	 */
	private String themeContent;

	/**
	 * 创建时间
	 */
	private Date createDate;

	/**
	 * 修改时间
	 */
	private Date modifyDate;

	/**
	 * 主题路径
	 */
	private String themePath;

	/**
	 * 应用id，用于标识主题属于那个应用
	 */
	private String appId;

	public String getThemeCode()
	{
		return themeCode;
	}

	public void setThemeCode(String themeCode)
	{
		this.themeCode = themeCode;
	}

	public String getThemeName()
	{
		return themeName;
	}

	public void setThemeName(String themeName)
	{
		this.themeName = themeName;
	}

	public String getThemePicPath()
	{
		return themePicPath;
	}

	public void setThemePicPath(String themePicPath)
	{
		this.themePicPath = themePicPath;
	}

	public String getThemeContent()
	{
		return themeContent;
	}

	public void setThemeContent(String themeContent)
	{
		this.themeContent = themeContent;
	}

	public Date getCreateDate()
	{
		return createDate;
	}

	public void setCreateDate(Date createDate)
	{
		this.createDate = createDate;
	}

	public Date getModifyDate()
	{
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate)
	{
		this.modifyDate = modifyDate;
	}

	public String getThemePath()
	{
		return themePath;
	}

	public void setThemePath(String themePath)
	{
		this.themePath = themePath;
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
