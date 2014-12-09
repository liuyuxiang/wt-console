package com.wt.uum2.web.wicket.panel.index;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.wt.uum2.web.wicket.panel.dept.DepartmentTreeNode;
import com.wt.uum2.web.wicket.panel.dept.DeptListPanel;

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
public class DeptContentPanel extends ContentPanel
{

	/**
	 * @param id
	 *            id
	 */
	public DeptContentPanel(String id)
	{
		super(id);
	}

	@Override
	protected Panel getTreePanel()
	{
		return new DeptTreePanel("treePanel") {


			@Override
			public void onNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
					AjaxRequestTarget target)
			{
				WebMarkupContainer container=getMainContainer();
				DeptListPanel deptListPanel = new DeptListPanel(getMainPanel().getId(), node.getDepartment());
				container.addOrReplace(deptListPanel.setOutputMarkupId(true));
				target.add(container);
			}
		};
	}


	/*@Override
	protected void init()
	{
		Form contentForm = new Form("contentForm");
		add(contentForm);
		final WebMarkupContainer MAINCONTAINER = new WebMarkupContainer("mainContainer");
		contentForm.add(MAINCONTAINER.setOutputMarkupId(true));
		MAINCONTAINER.add(new WelcomePanel("mainPanel"));

		DeptTreePanel treePanel = new DeptTreePanel("treePanel") {

			@Override
			public void onNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
					AjaxRequestTarget target)
			{
				DeptListPanel deptListPanel = new DeptListPanel("mainPanel", node.getDepartment());
				MAINCONTAINER.addOrReplace(deptListPanel.setOutputMarkupId(true));
				target.add(MAINCONTAINER);
			}

		};
		contentForm.add(treePanel.setOutputMarkupId(true));
	}
*/

}
