package com.wt.uum2.web.wicket.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.panel.index.DeptContentPanel;

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
public class DeptTagPage extends IndexPage
{



	/**
	 * 
	 */
	public DeptTagPage()
	{
		super();
		setType(MenuItemType.dept);
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public DeptTagPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.dept);
	}

	/**
	 * @param model
	 *            model
	 */
	public DeptTagPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.dept);
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public DeptTagPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.valueOf(parameters.get("tagName").toString("dept")));
	}

	@Override
	protected void init()
	{
		DeptContentPanel content = new DeptContentPanel("contentPanel");
		add(content);

	}
}
