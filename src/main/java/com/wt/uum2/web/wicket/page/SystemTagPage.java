package com.wt.uum2.web.wicket.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.panel.system.SystemContentPanel;

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
public class SystemTagPage extends IndexPage
{

	/**
	 * 
	 */
	public SystemTagPage()
	{
		setType(MenuItemType.system);
	}

	/**
	 * @param tagename
	 *            tagename
	 */
	public SystemTagPage(String tagename)
	{
		super(tagename);
		setType(MenuItemType.system);
	}

	/**
	 * @param model
	 *            model
	 */
	public SystemTagPage(IModel<?> model)
	{
		super(model);
		setType(MenuItemType.system);
	}

	/**
	 * @param parameters
	 *            parameters
	 */
	public SystemTagPage(PageParameters parameters)
	{
		super(parameters);
		setType(MenuItemType.system);
	}

	@Override
	protected void init()
	{
		SystemContentPanel content = new SystemContentPanel("contentPanel");
		add(content);

	}
}
