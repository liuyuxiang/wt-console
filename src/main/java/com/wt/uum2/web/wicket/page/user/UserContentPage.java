package com.wt.uum2.web.wicket.page.user;

import com.wt.uum2.web.wicket.page.UUMBasePage;
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
public class UserContentPage extends UUMBasePage
{

	/**
	 * 
	 */
	public UserContentPage()
	{
		super();
		init();
	}

	/**
	 * 方法说明：init
	 * 
	 */
	private void init()
	{
		UserContentPanel content = new UserContentPanel("contentPanel");
		add(content);

	}
}
