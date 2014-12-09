package com.wt.hea.webbuilder.service.impl;

import com.wt.hea.rbac.service.RBACService;
import com.wt.hea.webbuilder.dao.BaseInfoDao;
import com.wt.hea.webbuilder.dao.LayoutDefinitionDao;
import com.wt.hea.webbuilder.dao.PersonalSystemParameterDao;
import com.wt.hea.webbuilder.dao.PlaceHolderDao;
import com.wt.hea.webbuilder.dao.PlaceHolderGroupDao;
import com.wt.hea.webbuilder.dao.PopWindowDao;
import com.wt.hea.webbuilder.dao.PortletPropertyDao;
import com.wt.hea.webbuilder.dao.ResourceDao;
import com.wt.hea.webbuilder.dao.SiteManageDao;
import com.wt.hea.webbuilder.dao.TemplateLayoutDao;
import com.wt.hea.webbuilder.dao.TemplatePageDao;
import com.wt.hea.webbuilder.dao.TemplatePortletInfoDao;
import com.wt.hea.webbuilder.dao.ThemeDefinitionDao;
import com.wt.hea.webbuilder.dao.UserPageDao;
import com.wt.hea.webbuilder.dao.UserPersonalInfoDao;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-3-21
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BaseService
{
	/**
	 * 用户个性化页面dao
	 */
	protected UserPageDao userPageDao;

	/**
	 * portletdao
	 */
	protected PortletPropertyDao portletPropertyDao;

	/**
	 * 用户个性化信息dao
	 */
	protected UserPersonalInfoDao userPersonalInfoDao;

	/**
	 * 布局定义dao
	 */
	protected LayoutDefinitionDao layoutDefinitionDao;

	/**
	 * 主题定义dao
	 */
	protected ThemeDefinitionDao themeDefinitionDao;

	/**
	 * 模板页面dao
	 */
	protected TemplatePageDao templatePageDao;

	/**
	 * 模板portletdao
	 */
	protected TemplatePortletInfoDao templatePortletInfoDao;

	/**
	 * 
	 */
	protected PlaceHolderDao placeHolderDao;

	/**
	 * 模板布局dao
	 */
	protected TemplateLayoutDao templateLayoutDao;

	/**
	 * 站点管理Dao
	 */
	protected SiteManageDao siteManageDao;

	/**
	 * 个性化信息参数dao
	 */
	protected PersonalSystemParameterDao personalSystemParameterDao;

	/**
	 * 占位符权限dao
	 */
	protected PlaceHolderGroupDao placeHolderGroupDao;

	/**
	 * 首页信息dao
	 */
	protected BaseInfoDao baseInfoDao;

	/**
	 * 资源dao
	 */
	protected ResourceDao resourceDao;

	/**
	 * 用户权限service
	 */
	protected RBACService rbacService;

	/**
	 * 弹出窗口Dao
	 */
	protected PopWindowDao popWindowDao;

	public void setUserPageDao(UserPageDao userPageDaoImpl)
	{
		this.userPageDao = userPageDaoImpl;
	}

	public void setPortletPropertyDao(PortletPropertyDao portletPropertyDaoImpl)
	{
		this.portletPropertyDao = portletPropertyDaoImpl;
	}

	public void setUserPersonalInfoDao(UserPersonalInfoDao userPersonalInfoDaoImpl)
	{
		this.userPersonalInfoDao = userPersonalInfoDaoImpl;
	}

	public void setLayoutDefinitionDao(LayoutDefinitionDao layoutDefinitionDaoImpl)
	{
		this.layoutDefinitionDao = layoutDefinitionDaoImpl;
	}

	public void setThemeDefinitionDao(ThemeDefinitionDao themeDefinitionDaoImpl)
	{
		this.themeDefinitionDao = themeDefinitionDaoImpl;
	}

	public void setTemplatePageDao(TemplatePageDao templatePageDaoImpl)
	{
		this.templatePageDao = templatePageDaoImpl;
	}

	public void setPlaceHolderDao(PlaceHolderDao placeHolderDaoImpl)
	{
		this.placeHolderDao = placeHolderDaoImpl;
	}

	public void setTemplateLayoutDao(TemplateLayoutDao templateLayoutDaoImpl)
	{
		this.templateLayoutDao = templateLayoutDaoImpl;
	}

	public void setSiteManageDao(SiteManageDao siteManageDaoImpl)
	{
		this.siteManageDao = siteManageDaoImpl;
	}

	public void setPersonalSystemParameterDao(
			PersonalSystemParameterDao personalSystemParameterDaoImpl)
	{
		this.personalSystemParameterDao = personalSystemParameterDaoImpl;
	}

	public void setTemplatePortletInfoDao(TemplatePortletInfoDao templatePortletInfoDaoImpl)
	{
		this.templatePortletInfoDao = templatePortletInfoDaoImpl;
	}

	public void setPlaceHolderGroupDao(PlaceHolderGroupDao placeHolderGroupDao)
	{
		this.placeHolderGroupDao = placeHolderGroupDao;
	}

	public void setBaseInfoDao(BaseInfoDao baseInfoDao)
	{
		this.baseInfoDao = baseInfoDao;
	}

	public void setResourceDao(ResourceDao resourceDao)
	{
		this.resourceDao = resourceDao;
	}

	public void setRbacService(RBACService rbacService)
	{
		this.rbacService = rbacService;
	}

	public void setPopWindowDao(PopWindowDao popWindowDao)
	{
		this.popWindowDao = popWindowDao;
	}

}
