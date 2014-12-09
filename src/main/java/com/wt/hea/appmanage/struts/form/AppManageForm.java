package com.wt.hea.appmanage.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.appmanage.model.AppManage;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.model.ThemeDefinition;

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
public class AppManageForm extends ActionForm
{

	/**
	 * 
	 */
	private AppManage appManage = new AppManage();

	/**
	 * 
	 */
	private AppInfos appInfos = new AppInfos();

	/**
	 * 
	 */
	private ResourceSite resource = new ResourceSite();

	/**
	 * 
	 */
	private ThemeDefinition themeDef = new ThemeDefinition();

	public AppManage getAppManage()
	{
		return appManage;
	}

	public void setAppManage(AppManage appManage)
	{
		this.appManage = appManage;
	}

	public AppInfos getAppInfos()
	{
		return appInfos;
	}

	public void setAppInfos(AppInfos appInfos)
	{
		this.appInfos = appInfos;
	}

	public ResourceSite getResource()
	{
		return resource;
	}

	public void setResource(ResourceSite resource)
	{
		this.resource = resource;
	}

	public ThemeDefinition getThemeDef()
	{
		return themeDef;
	}

	public void setThemeDef(ThemeDefinition themeDef)
	{
		this.themeDef = themeDef;
	}
}
