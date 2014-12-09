package com.wt.uum2.web.wicket.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.web.wicket.panel.index.UserContentPanel;
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
public class SystemListPage extends NoHeaderListPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2484691236759835556L;

	public SystemListPage()
	{
		super();
		SystemContentPanel content = new SystemContentPanel("contentPanel");
		add(content);

	}
}
