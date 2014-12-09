package com.wt.uum2.web.wicket.page.application;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.page.IndexPage;
import com.wt.uum2.web.wicket.panel.application.AppContentPanel;
import com.wt.uum2.web.wicket.panel.index.ContentPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-10-31
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AppTagPage extends IndexPage
{



	/**
	 * 
	 */
	private static final long serialVersionUID = -3852223880571336358L;

	/**
	 * 
	 */
	public AppTagPage()
	{
		super();
		setType(MenuItemType.app);
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public AppTagPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.app);
	}

	/**
	 * @param model
	 *            model
	 */
	public AppTagPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.app);
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public AppTagPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.valueOf(parameters.get("tagName").toString("app")));
	}

	@Override
	protected void init()
	{
		ContentPanel content = new AppContentPanel("contentPanel");
		add(content);

	}
}
