package com.wt.uum2.web.wicket.page;

import com.wt.uum2.web.wicket.panel.index.UserContentPanel;

public class UserListPage extends NoHeaderListPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2484691236759835556L;

	public UserListPage()
	{
		super();
		UserContentPanel content = new UserContentPanel(getContentPanelId());
		add(content);
	}

}
