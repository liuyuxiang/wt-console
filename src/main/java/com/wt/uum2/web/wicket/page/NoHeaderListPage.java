package com.wt.uum2.web.wicket.page;

import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;

import com.hirisun.components.other.project.ProjectResolver;

public class NoHeaderListPage extends WebPage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2484691236759835556L;

	public NoHeaderListPage()
	{
		super();
	}

	public String getContentPanelId()
	{
		return "contentPanel";
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference("style/hirisun/wstyle.css");
		response.renderCSSReference("style/" + ProjectResolver.getId() + "/wstyle.css");
		response.renderJavaScriptReference("js/jquery/jquery-1.6.2.min.js");

	}
}
