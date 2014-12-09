package com.wt.uum2.web.wicket.panel.application;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;

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
public class AppContentPanel extends ContentPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1843619265413573870L;

	/**
	 * @param id
	 *            id
	 */
	public AppContentPanel(String id)
	{
		super(id);
	}

	@Override
	protected Panel getTreePanel()
	{
		return new AppTreePanel("treePanel") {

			@Override
			public void onNodeLinkClicked(AppTreeNode node, BaseTree tree,
					AjaxRequestTarget target)
			{
				WebMarkupContainer container = getMainContainer();
				AppListPanel appListPanel = new AppListPanel(getMainPanel().getId(),
						node.getApplication());
				container.addOrReplace(appListPanel.setOutputMarkupId(true));
				target.add(container);
			}
		};
	}

}
