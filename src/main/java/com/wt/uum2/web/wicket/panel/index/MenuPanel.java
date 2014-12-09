package com.wt.uum2.web.wicket.panel.index;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.ContextRelativeResource;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.page.DeptTagPage;
import com.wt.uum2.web.wicket.page.IndexPage;
import com.wt.uum2.web.wicket.page.SystemTagPage;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

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
public class MenuPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private MenuItemType selectMenuItemType;

	/**
	 * @param id
	 *            id
	 * @param type
	 *            type
	 */
	public MenuPanel(String id, MenuItemType type)
	{
		super(id);
		this.selectMenuItemType = type;
		ListView<MenuItem> listview = new ListView<MenuItem>("menuitem", getMenuItems()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void populateItem(ListItem<MenuItem> item)
			{
				item.add(item.getModelObject());
			}

		};

		add(listview);

	}

	/**
	 * 方法说明：
	 * 
	 * @return List
	 */
	public List<MenuItem> getMenuItems()
	{
		List<MenuItem> items = new ArrayList<MenuItem>();

		MenuItemType[] types = MenuItemType.values();

		for (MenuItemType menuItem : getSetting().getNormalMenuList()) {
			if (ArrayUtils.contains(types, menuItem)) {
				items.add(initMenuItem(menuItem));
			}
		}

		if (getUUMService().isUserinSuperGroup(loginUser)) {
			for (MenuItemType menuItem : getSetting().getAdminMenuList()) {
				if (ArrayUtils.contains(types, menuItem)) {
					items.add(initMenuItem(menuItem));
				}
			}
		}
		return items;

	}

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @return MenuItem
	 */
	public MenuItem initMenuItem(MenuItemType type)
	{
		MenuItem menuItem = null;

		switch (type) {
		case user:
			// 用户页面
			menuItem = getMenuItem(MenuItemType.user, "/style/default/images/tab4_d_yyxt.jpg",
					"/style/default/images/seltab_d_yh.jpg", IndexPage.class, null);// user
			break;
		case dept:
			// 部门页面
			menuItem = getMenuItem(MenuItemType.dept, "/style/default/images/tab1.gif",
					"/style/default/images/seltab1.gif", DeptTagPage.class, null);// dept
			break;
		case org:
			// 角色页面
			menuItem = getMenuItem(MenuItemType.org, "/style/default/images/tab2.gif",
					"/style/default/images/seltab2.gif", "/main.nsf?menuId=1");// org
			break;
		case application:
			// 应用系统组页面
			menuItem = getMenuItem(MenuItemType.application, "/style/default/images/tab4_d_yh.jpg",
					"/style/default/images/seltab_d_yyxt.jpg", "/main.nsf?menuId=5");// application
			break;
		case att:
			// 扩展属性页面
			menuItem = getMenuItem(MenuItemType.att, "/style/default/images/taby_k.gif",
					"/style/default/images/seltaby_k.gif", "/main.nsf?menuId=3");// att
			break;
		case task:
			// 代办页面
			menuItem = getMenuItem(MenuItemType.task, "/style/default/images/tab4_d.gif",
					"/style/default/images/seltab_d.gif", "/main.nsf?menuId=4");// task
			break;
		case system:
			// 系统页面
			menuItem = getMenuItem(MenuItemType.system, "/style/default/images/tab4_sys.gif",
					"/style/default/images/seltab_sys.gif", SystemTagPage.class, null);// system
			break;
		case app:
			// 应用系统页面
			menuItem = getMenuItem(MenuItemType.app, "/style/default/images/taby.gif",
					"/style/default/images/seltaby.gif", "/main.nsf?menuId=2");// app
			break;
		}
		return menuItem;
	}

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @param imageUrl
	 *            imageUrl
	 * @param selectedImageUrl
	 *            selectedImageUrl
	 * @param url
	 *            url
	 * @return MenuItem
	 */
	public MenuItem getMenuItem(MenuItemType type, final String imageUrl,
			final String selectedImageUrl, final String url)
	{
		String imagesrc = selectMenuItemType.compareTo(type) == 0 ? selectedImageUrl : imageUrl;
		MenuItem item = new MenuItem("menuImage", type, new ContextRelativeResource(imagesrc),
				imageUrl, selectedImageUrl) {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				setResponsePage(new RedirectPage(getRequest().getContextPath() + url));
			}

		};

		return item;
	}

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @param imageUrl
	 *            imageUrl
	 * @param selectedImageUrl
	 *            selectedImageUrl
	 * @param destinationPageClass
	 *            destinationPageClass
	 * @param paras
	 *            paras
	 * @return MenuItem
	 */
	public MenuItem getMenuItem(MenuItemType type, final String imageUrl,
			final String selectedImageUrl, final Class<? extends WebPage> destinationPageClass,
			final PageParameters paras)
	{
		String imagesrc = selectMenuItemType.compareTo(type) == 0 ? selectedImageUrl : imageUrl;
		MenuItem item = new MenuItem("menuImage", type, new ContextRelativeResource(imagesrc),
				imageUrl, selectedImageUrl) {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				setResponsePage(destinationPageClass, paras);
			}

		};

		return item;
	}

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @param imageUrl
	 *            imageUrl
	 * @param selectedImageUrl
	 *            selectedImageUrl
	 * @param destinationPage
	 *            destinationPage
	 * @return MenuItem
	 */
	public MenuItem getMenuItem(MenuItemType type, final String imageUrl,
			final String selectedImageUrl, final WebPage destinationPage)
	{
		String imagesrc = selectMenuItemType.compareTo(type) == 0 ? selectedImageUrl : imageUrl;
		MenuItem item = new MenuItem("menuImage", type, new ContextRelativeResource(imagesrc),
				imageUrl, selectedImageUrl) {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				setResponsePage(destinationPage);
			}
		};

		return item;
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param item
	 *            item
	 */
	public void onSubmit(AjaxRequestTarget target, MenuItem item)
	{

	}

}
