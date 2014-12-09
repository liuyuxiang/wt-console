package com.wt.hea.common.action;

import com.wt.hea.common.service.SystemCodeService;
import com.wt.hea.rbac.service.IndexService;
import com.wt.hea.rbac.service.RBACService;
import com.wt.hea.webbuilder.service.BaseInfoService;
import com.wt.hea.webbuilder.service.JdbcIndexService;
import com.wt.hea.webbuilder.service.LayoutDefinitionService;
import com.wt.hea.webbuilder.service.PersonalSystemParameterService;
import com.wt.hea.webbuilder.service.PlaceHolderGroupService;
import com.wt.hea.webbuilder.service.PlaceHolderSerivce;
import com.wt.hea.webbuilder.service.PopWindowService;
import com.wt.hea.webbuilder.service.PortletPropertyService;
import com.wt.hea.webbuilder.service.ResourceService;
import com.wt.hea.webbuilder.service.SiteManageService;
import com.wt.hea.webbuilder.service.TemplateLayoutService;
import com.wt.hea.webbuilder.service.TemplatePageService;
import com.wt.hea.webbuilder.service.TemplatePortletInfoService;
import com.wt.hea.webbuilder.service.ThemeDefinitionService;
import com.wt.hea.webbuilder.service.UserPageService;
import com.wt.hea.webbuilder.service.UserPersonalInfoService;

/**
 * 
 * Action 层基类 spring容器装配业务对象都注入到此类，所有的Action子类将拥有业务对象，并就可以做相应的业务方法逻辑实现
 * 
 * @author 袁明敏
 * @since 1.0
 * @see #setDepartmentService(DepartmentService)
 * @see #setGroupService(GroupService)
 * @see #setIndexService(IndexService)
 * @see com.wt.hea.common.action.BaseDispatchAction
 * @see org.springframework.web.struts.DispatchActionSupport
 * 
 * 
 */
public class DispatchAction extends BaseDispatchAction
{

	/**
	 * 
	 */
	protected UserPersonalInfoService userPersonalInfoService;
	/**
	 * 
	 */
	protected UserPageService userPageService;
	/**
	 * 
	 */
	protected PortletPropertyService portletPropertyService;
	/**
	 * 
	 */
	protected LayoutDefinitionService layoutDefinitionService;
	/**
	 * 
	 */
	protected ThemeDefinitionService themeDefinitionService;
	/**
	 * 
	 */
	protected TemplatePageService templatePageService;
	/**
	 * 
	 */
	protected TemplateLayoutService templateLayoutService;
	/**
	 * 
	 */
	protected SiteManageService siteManageService;
	/**
	 * 
	 */
	protected PersonalSystemParameterService personalSystemParameterService;
	/**
	 * 
	 */
	protected TemplatePortletInfoService templatePortletInfoService;
	/**
	 * 
	 */
	protected PlaceHolderSerivce placeHolderService;
	/**
	 * 
	 */
	protected PlaceHolderGroupService placeHolderGroupService;

	/**
	 * 
	 */
	protected SystemCodeService systemCodeService;
	/**
	 * 
	 */
	protected BaseInfoService baseInfoService;
	/**
	 * 
	 */
	protected ResourceService resourceService;
	/**
	 * 
	 */
	protected IndexService indexService;

	/**
	 * 
	 */
	protected RBACService rbacService;
	/**
	 * 
	 */
	protected String root;
	/**
	 * 
	 */
	protected String rootGroup;
	/**
	 * 
	 */
	protected String rootIndex;
	/**
	 * 
	 */
	protected String rootDepartment;
	/**
	 * 
	 */
	protected String rootUser;
	/**
	 * 
	 */
	protected JdbcIndexService jdbcIndexService;
	/**
	 * 
	 */
	public void setJdbcIndexService(JdbcIndexService jdbcIndexService)
	{
		this.jdbcIndexService = jdbcIndexService;
	}

	/**
	 * 
	 */
	protected String rootSite;
	/**
	 * 
	 */
	protected String overall;
	/**
	 * 
	 */
	protected String personal;
	/**
	 * 
	 */
	protected String rootmenu;
	/**
	 * 
	 */
	protected String rootPath;
	/**
	 * 
	 */
	protected String contextPath;

	/**
	 * 
	 */
	protected String rootReportIndex;

	/**
	 * 
	 */
	protected PopWindowService popWindowService;

	public void setTemplateLayoutService(TemplateLayoutService templateLayoutService)
	{
		this.templateLayoutService = templateLayoutService;
	}

	public void setSiteManageService(SiteManageService siteManageService)
	{
		this.siteManageService = siteManageService;
	}

	public void setPersonalSystemParameterService(
			PersonalSystemParameterService personalSystemParameterService)
	{
		this.personalSystemParameterService = personalSystemParameterService;
	}

	public void setTemplatePortletInfoService(TemplatePortletInfoService templatePortletInfoService)
	{
		this.templatePortletInfoService = templatePortletInfoService;
	}

	public void setPlaceHolderService(PlaceHolderSerivce placeHolderService)
	{
		this.placeHolderService = placeHolderService;
	}

	public void setPlaceHolderGroupService(PlaceHolderGroupService placeHolderGroupService)
	{
		this.placeHolderGroupService = placeHolderGroupService;
	}

	public void setBaseInfoService(BaseInfoService baseInfoService)
	{
		this.baseInfoService = baseInfoService;
	}

	public void setResourceService(ResourceService resourceService)
	{
		this.resourceService = resourceService;
	}

	/**
	 * spring容器注入"指标"业务对象
	 * 
	 * @param indexServiceImpl
	 *            资源对象
	 */
	public void setIndexService(IndexService indexServiceImpl)
	{
		this.indexService = indexServiceImpl;
	}

	/**
	 * spring容器注入个性化对象
	 * 
	 * @param userPersonalInfoServiceImpl
	 *            用户个性化对象
	 */
	public void setUserPersonalInfoService(UserPersonalInfoService userPersonalInfoServiceImpl)
	{
		this.userPersonalInfoService = userPersonalInfoServiceImpl;
	}

	/**
	 * spring容器注入对象
	 * 
	 * @param userPageServiceImpl
	 *            用户页面服务 对象
	 */
	public void setUserPageService(UserPageService userPageServiceImpl)
	{
		this.userPageService = userPageServiceImpl;
	}

	/**
	 * spring容器注入对象
	 * 
	 * @param portletPropertyServiceImpl
	 *            portletPropertyServiceImpl
	 */
	public void setPortletPropertyService(PortletPropertyService portletPropertyServiceImpl)
	{
		this.portletPropertyService = portletPropertyServiceImpl;
	}

	/**
	 * spring容器注入对象
	 * 
	 * @param layoutDefinitionServiceImpl
	 *            注入layoutDefinitionService对象
	 */
	public void setLayoutDefinitionService(LayoutDefinitionService layoutDefinitionServiceImpl)
	{
		this.layoutDefinitionService = layoutDefinitionServiceImpl;
	}

	/**
	 * spring容器注入对象
	 * 
	 * @param themeDefinitionServiceImpl
	 *            themeDefinitionServiceImpl
	 */
	public void setThemeDefinitionService(ThemeDefinitionService themeDefinitionServiceImpl)
	{
		this.themeDefinitionService = themeDefinitionServiceImpl;
	}

	/**
	 * spring容器注入对象
	 * 
	 * @param templatePageServiceImpl
	 *            templatePageServiceImpl
	 */
	public void setTemplatePageService(TemplatePageService templatePageServiceImpl)
	{
		this.templatePageService = templatePageServiceImpl;
	}

	public void setRootSite(String rootSite)
	{
		this.rootSite = rootSite;
	}

	public void setOverall(String overall)
	{
		this.overall = overall;
	}

	public void setPersonal(String personal)
	{
		this.personal = personal;
	}

	public void setRootmenu(String rootmenu)
	{
		this.rootmenu = rootmenu;
	}

	public void setRootPath(String rootPath)
	{
		this.rootPath = rootPath;
	}

	public void setContextPath(String contextPath)
	{
		this.contextPath = contextPath;
	}

	public void setRoot(String root)
	{
		this.root = root;
	}

	public void setRootGroup(String rootGroup)
	{
		this.rootGroup = rootGroup;
	}

	public void setRootIndex(String rootIndex)
	{
		this.rootIndex = rootIndex;
	}

	public void setRootDepartment(String rootDepartment)
	{
		this.rootDepartment = rootDepartment;
	}

	public void setRootUser(String rootUser)
	{
		this.rootUser = rootUser;
	}

	public void setRbacService(RBACService rbacService)
	{
		this.rbacService = rbacService;
	}

	public void setSystemCodeService(SystemCodeService systemCodeService)
	{
		this.systemCodeService = systemCodeService;
	}

	public void setRootReportIndex(String rootReportIndex)
	{
		this.rootReportIndex = rootReportIndex;
	}

	public void setPopWindowService(PopWindowService popWindowService)
	{
		this.popWindowService = popWindowService;
	}

}
