package com.wt.uum2.web.wicket.page;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum.web.version.UumWebVersionUtils;
import com.wt.uum2.bean.Setting;
import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.domain.User;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.mail.SendMail;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.TaskListService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.service.UserListService;
import com.wt.uum2.web.wicket.BaseUUMApplication;
import com.wt.uum2.web.wicket.panel.index.FooterPanel;
import com.wt.uum2.web.wicket.panel.index.HeaderPanel;
import com.wt.uum2.web.wicket.panel.index.MenuPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-16
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class BasePage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private MenuItemType type;

	/**
	 * 
	 */
	public BasePage()
	{
		this.type = MenuItemType.user;
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public BasePage(String tagename)
	{
		if (StringUtils.isBlank(tagename)) {
			this.type = MenuItemType.user;
		} else {
			this.type = MenuItemType.valueOf(tagename);
		}

	}

	/**
	 * @param model
	 *            model
	 */
	public BasePage(IModel<?> model)
	{
		super(model);
		this.type = MenuItemType.user;
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public BasePage(PageParameters parameters)
	{
		super(parameters);
		this.type = MenuItemType.valueOf(parameters.get("tagName").toString("user"));
	}

	@Override
	protected void onInitialize()
	{
		super.onInitialize();
		add(new Label("pageTitle", "统一用户管理 (" + UumWebVersionUtils.getVersion().getWholeVersion()
				+ ")"));
		add(new HeaderPanel("header"));
		add(new FooterPanel("footer"));
		/*Form form = new Form("form");
		add(form);*/
		MenuPanel menuPanel = new MenuPanel("menuPanel", type);
		add(menuPanel);
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference("style/hirisun/wstyle.css");
		response.renderCSSReference("style/" + ProjectResolver.getId()
				+ "/wstyle.css");
		response.renderJavaScriptReference("js/jquery/jquery-1.6.2.min.js");

	}

	public MenuItemType getType()
	{
		return type;
	}

	public void setType(MenuItemType type)
	{
		this.type = type;
	}

	/**
	 * 
	 */
	protected User loginUser = getUUMService().getLoginUser();

	protected UUMService getUUMService()
	{
		return ((BaseUUMApplication) getApplication()).getUUMService();
	}

	protected DepartmentPathService getDepartmentPathService()
	{
		return ((BaseUUMApplication) getApplication()).getDepartmentPathService();
	}

	protected EventFactory getEventFactory()
	{
		return ((BaseUUMApplication) getApplication()).getEventFactory();
	}

	protected EventListenerHandler getEventListenerHandler()
	{
		return ((BaseUUMApplication) getApplication()).getEventListenerHandler();
	}

	protected UUMTempDataService getUumTempDataService()
	{
		return ((BaseUUMApplication) getApplication()).getUumTempDataService();
	}

	protected Setting getSetting()
	{
		return ((BaseUUMApplication) getApplication()).getSetting();
	}

	protected SendMail getSendMail()
	{
		return ((BaseUUMApplication) getApplication()).getSendMail();
	}

	protected UserListService getUserListService()
	{
		return ((BaseUUMApplication) getApplication()).getUserListService();
	}

	protected TaskListService getTaskListService()
	{
		return ((BaseUUMApplication) getApplication()).getTaskListService();
	}
}
