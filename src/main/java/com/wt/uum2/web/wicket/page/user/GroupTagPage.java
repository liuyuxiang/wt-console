package com.wt.uum2.web.wicket.page.user;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.page.IndexPage;
import com.wt.uum2.web.wicket.panel.group.GroupContentPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupTagPage extends IndexPage
{

	/**
	 * 
	 */
	public GroupTagPage()
	{
		setType(MenuItemType.org);
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public GroupTagPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.org);
	}

	/**
	 * @param model
	 *            model
	 */
	public GroupTagPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.org);
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public GroupTagPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.valueOf(parameters.get("tagName").toString("org")));
	}

	@Override
	protected void init()
	{
		GroupContentPanel content = new GroupContentPanel("contentPanel");
		add(content.setOutputMarkupId(true));
	}
}
