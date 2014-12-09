package com.wt.uum2.web.wicket.panel.index;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.wt.uum2.web.wicket.panel.dept.DepartmentTreeNode;
import com.wt.uum2.web.wicket.panel.user.UserListPanel;

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
public class UserContentPanel extends ContentPanel
{

	/**
	 * @param id
	 *            id
	 */
	public UserContentPanel(String id)
	{
		super(id);
	}

	@Override
	protected Panel getTreePanel()
	{
		return new UserTreePanel("treePanel") {


			@Override
			public void onNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
					AjaxRequestTarget target)
			{
				WebMarkupContainer container=getMainContainer();
				UserListPanel userListPanel = new UserListPanel(getMainPanel().getId(), node.getDepartment());
				container.addOrReplace(userListPanel.setOutputMarkupId(true));
				target.add(container);
			}
		};
	}

}
