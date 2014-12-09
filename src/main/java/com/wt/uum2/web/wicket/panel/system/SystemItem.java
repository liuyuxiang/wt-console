package com.wt.uum2.web.wicket.panel.system;

import org.apache.wicket.Page;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SystemItem implements java.io.Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3028739986579025901L;

	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String linkUrl;
	/**
	 * 
	 */
	private Class<? extends Panel> panelClass;

	/**
	 * 
	 */
	private Class<? extends Page> pageClass;
	/**
	 * 
	 */
	private SystemItemType type;

	/**
	 * @param title
	 *            title
	 * @param url
	 *            url
	 */
	public SystemItem(String title, String url)
	{
		setTitle(title);
		setLinkUrl(url);
		setType(SystemItemType.link);
	}

	/**
	 * @param title
	 *            title
	 * @param class1
	 *            class1
	 */
	public SystemItem(String title, Class<? extends Panel> class1)
	{
		setTitle(title);
		setPanelClass(class1);
		setType(SystemItemType.panel);
	}

	/**
	 * @param title
	 *            title
	 * @param class1
	 *            class1
	 * @param type
	 *            type
	 */
	public SystemItem(String title, Class<? extends Page> class1, SystemItemType type)
	{
		setTitle(title);
		setPageClass(class1);
		if (type == null) {
			type = SystemItemType.page;
		}
		setType(type);
	}

	public boolean isLinkUrl()
	{
		return getType().compareTo(SystemItemType.link) == 0;
	}

	public boolean isPanelClass()
	{
		return getType().compareTo(SystemItemType.panel) == 0;
	}

	public boolean isPageClass()
	{
		return getType() == SystemItemType.page;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getLinkUrl()
	{
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl)
	{
		this.linkUrl = linkUrl;
	}

	public Class<? extends Panel> getPanelClass()
	{
		return panelClass;
	}

	public void setPanelClass(Class<? extends Panel> panelClass)
	{
		this.panelClass = panelClass;
	}

	public SystemItemType getType()
	{
		return type;
	}

	public void setType(SystemItemType type)
	{
		this.type = type;
	}

	public Class<? extends Page> getPageClass()
	{
		return pageClass;
	}

	public void setPageClass(Class<? extends Page> pageClass)
	{
		this.pageClass = pageClass;
	}

}

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
enum SystemItemType
{
	/**
	 * 
	 */
	link,
	/**
	 * 
	 */
	panel,
	/**
	 * 
	 */
	page;
}
