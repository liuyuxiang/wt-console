package com.wt.uum2.web.wicket.panel.index;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
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
public class ContentPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private WebMarkupContainer mainContainer;
	/**
	 * @param id
	 *            id
	 */
	public ContentPanel(String id)
	{
		super(id);
		init();
	}

	/**
	 * 方法说明：
	 * 
	 */
	protected final void init()
	{
		Form<Void> contentForm = new Form<Void>("contentForm");
		add(contentForm);
		mainContainer = new WebMarkupContainer("mainContainer");
		contentForm.add(mainContainer.setOutputMarkupId(true));
		mainContainer.add(getMainPanel());
		
		
		contentForm.add(getTreePanel());
	}
	protected Panel getMainPanel(){
		return new WelcomePanel("mainPanel");
	}
	protected Panel getTreePanel(){
		return new DeptTreePanel("treePanel") {

			@Override
			public void onNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
					AjaxRequestTarget target)
			{
				WebMarkupContainer container=getMainContainer();
				UserListPanel userListPanel = new UserListPanel(getMainPanel().getId(),
						node.getDepartment());
				container.addOrReplace(userListPanel.setOutputMarkupId(true));
				target.add(container);
			}

		};
	}
	
	public WebMarkupContainer getMainContainer()
	{
		return mainContainer;
	}
}
