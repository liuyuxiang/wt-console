package com.wt.uum2.web.wicket.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.panel.index.UserContentPanel;

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
public class UserTagPage extends IndexPage
{



	/**
	 * 
	 */
	public UserTagPage()
	{
		super();
		setType(MenuItemType.user);
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public UserTagPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.user);
	}

	/**
	 * @param model
	 *            model
	 */
	public UserTagPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.user);
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public UserTagPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.valueOf(parameters.get("tagName").toString("user")));
	}

	@Override
	protected void init()
	{
		UserContentPanel content = new UserContentPanel("contentPanel");
		add(content);

	}
}
