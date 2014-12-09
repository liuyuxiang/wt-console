package com.wt.uum2.web.wicket.panel.system;

import org.apache.wicket.markup.html.link.InlineFrame;
import org.apache.wicket.markup.html.pages.RedirectPage;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

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
public class SystemPanel extends BaseUUMPanel
{

	/**
	 * @param id
	 *            id
	 * @param url
	 *            url
	 */
	public SystemPanel(String id, String url)
	{
		super(id);
		RedirectPage page = new RedirectPage(getRequest().getContextPath() + url);
		InlineFrame frame = new InlineFrame("systemframe", page);
		add(frame);
	}

}
