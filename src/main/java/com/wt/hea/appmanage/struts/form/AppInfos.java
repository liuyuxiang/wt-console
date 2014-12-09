package com.wt.hea.appmanage.struts.form;

import java.io.Serializable;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-31
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class AppInfos implements Serializable
{
	/**
	 * 
	 */
	private String logoSystemId;

	/**
	 * 
	 */
	private String logoId;

	/**
	 * 
	 */
	private String logoName;

	/**
	 * 
	 */
	private String bannerSystemId;

	/**
	 * 
	 */
	private String bannerId;

	/**
	 * 
	 */
	private String bannerName;

	/**
	 * 
	 */
	private String themeSystemId;

	/**
	 * 
	 */
	private String themeId;

	/**
	 * 
	 */
	private String themeName;

	public String getLogoSystemId()
	{
		return logoSystemId;
	}

	public void setLogoSystemId(String logoSystemId)
	{
		this.logoSystemId = logoSystemId;
	}

	public String getLogoId()
	{
		return logoId;
	}

	public void setLogoId(String logoId)
	{
		this.logoId = logoId;
	}

	public String getBannerSystemId()
	{
		return bannerSystemId;
	}

	public void setBannerSystemId(String bannerSystemId)
	{
		this.bannerSystemId = bannerSystemId;
	}

	public String getBannerId()
	{
		return bannerId;
	}

	public void setBannerId(String bannerId)
	{
		this.bannerId = bannerId;
	}

	public String getThemeSystemId()
	{
		return themeSystemId;
	}

	public void setThemeSystemId(String themeSystemId)
	{
		this.themeSystemId = themeSystemId;
	}

	public String getThemeId()
	{
		return themeId;
	}

	public void setThemeId(String themeId)
	{
		this.themeId = themeId;
	}

	public String getLogoName()
	{
		return logoName;
	}

	public void setLogoName(String logoName)
	{
		this.logoName = logoName;
	}

	public String getBannerName()
	{
		return bannerName;
	}

	public void setBannerName(String bannerName)
	{
		this.bannerName = bannerName;
	}

	public String getThemeName()
	{
		return themeName;
	}

	public void setThemeName(String themeName)
	{
		this.themeName = themeName;
	}

}
