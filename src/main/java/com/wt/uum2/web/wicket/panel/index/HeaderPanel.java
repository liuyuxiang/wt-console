package com.wt.uum2.web.wicket.panel.index;

import org.apache.shiro.SecurityUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

import com.wt.uum2.web.wicket.page.LoginPage;
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
public class HeaderPanel extends BaseUUMPanel
{

	/**
	 * @param id
	 *            id
	 */
	public HeaderPanel(String id)
	{
		super(id);
		add(new Label("welcomeMsg", getUUMService().getUserPrimaryDepartment(loginUser).getName() + "&nbsp;&nbsp;"
				+ loginUser.getName() + "&nbsp;&nbsp;欢迎您").setEscapeModelStrings(false));
		add(new Link("logout") {

			@Override
			public void onClick()
			{
				SecurityUtils.getSubject().logout();
				setResponsePage(LoginPage.class);
			}

		});
	}

}
