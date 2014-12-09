package com.wt.uum2.web.wicket.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.panel.index.ContentPanel;

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
public class IndexPage extends BasePage
{

	/**
	 * 
	 */
	public IndexPage()
	{
		super();
		setType(MenuItemType.user);
		init();
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public IndexPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.user);
		init();
	}

	/**
	 * @param model
	 *            model
	 */
	public IndexPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.user);
		init();
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public IndexPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.valueOf(parameters.get("tagName").toString("user")));
		init();
	}

	/**
	 * 方法说明：
	 * 
	 */
	protected void init()
	{
		ContentPanel content = new ContentPanel("contentPanel");
		add(content);
	}

}
