package com.wt.uum2.web.wicket.page;

import com.wt.uum2.web.wicket.panel.index.DeptContentPanel;

public class DeptListPage extends NoHeaderListPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2484691236759835556L;

	public DeptListPage()
	{
		super();
		DeptContentPanel content = new DeptContentPanel(getContentPanelId());
		add(content);
	}

}
