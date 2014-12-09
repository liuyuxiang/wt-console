package com.wt.uum2.web.wicket.panel.dept;

import java.util.Collection;

import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.web.wicket.page.DeptTagPage;
import com.wt.uum2.web.wicket.page.IndexPage;
import com.wt.uum2.web.wicket.panel.AreYouSurePanel;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.index.DeptTreePanel;
import com.wt.uum2.web.wicket.panel.index.UserTreePanel;
import com.wt.uum2.web.wicket.panel.index.WelcomePanel;
import com.wt.uum2.web.wicket.panel.user.UserListPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-11-25
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeptListToolbarPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private Department dept;

	/**
	 * @param id
	 *            id
	 * @param dept
	 *            dept
	 * @param visblelinks
	 *            visblelinks
	 */
	public DeptListToolbarPanel(String id, final Department dept, boolean visblelinks)
	{
		super(id);
		this.dept = dept;
		add(new Label("currentDeptPath", dept.getPath()));
		AreYouSurePanel deldept = new AreYouSurePanel("deldept", "删除当前机构", "是否删除当前机构？") {

			@Override
			protected void onConfirm(AjaxRequestTarget target)
			{
				if ("ROOT".equalsIgnoreCase(dept.getName())) {
					target.appendJavaScript("alert('根节点不能删除！')");
					return;
				}
				if (getUUMService().hasSubDepartment(dept)) {
					target.appendJavaScript("alert('该部门内存在子部门，不能被删除！')");
					return;
				}
				if (getUUMService().existUserUnderDepartment(dept)) {
					target.appendJavaScript("alert('该部门内存在用户，不能被删除！')");
					return;
				}

				Event event = getEventFactory().createEventDeleteDept(dept.getUuid());
				getUUMService().deleteDepartment(dept);
				getEventListenerHandler().handle(event);// 删除部门

				removeTreeNode(dept, target);// 删除树中此节点
			}

			@Override
			protected void onCancel(AjaxRequestTarget target)
			{

			}

			@Override
			public String getButtonCssClass()
			{
				return "button";
			}

		};

		add(deldept);
		AjaxLink adddept = new AjaxLink("adddept") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				WebMarkupContainer mainContainer = getMainContainer();
				mainContainer.addOrReplace(new CreateDeptPanel("mainPanel", dept) {

					@Override
					public void goTo(AjaxRequestTarget target, Department newDept,
							boolean parentisroot)
					{
						Component treePanel = getPage().get("contentPanel:contentForm:treePanel");
						BaseTree tree = null;
						if (treePanel instanceof DeptTreePanel) {
							tree = ((DeptTreePanel) treePanel).getTree();
						} else if (treePanel instanceof UserTreePanel) {
							tree = ((UserTreePanel) treePanel).getTree();
						}

						DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModelObject();
						DepartmentTreeNode node = null;
						if (parentisroot) {
							node = (DepartmentTreeNode) treeModel.getRoot();
						} else {
							node = getCurrentDeptNode(tree);

						}
						DepartmentTreeNode newTreeNode = new DepartmentTreeNode(newDept);
						node.add(newTreeNode);
						int[] changes = new int[] { treeModel.getIndexOfChild(node, newTreeNode) };
						treeModel.nodesWereInserted(newTreeNode.getParent(), changes);
						tree.updateTree(target);

						WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
								"contentPanel:contentForm:mainContainer");
						refreshMainContainer(mainContainer, target);

					}

				});
				target.add(mainContainer);

			}

		};
		add(adddept);
		AjaxLink updatedept = new AjaxLink("updatedept") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if ("ROOT".equalsIgnoreCase(dept.getName())) {
					target.appendJavaScript("alert('根节点不能更新！')");
					return;
				}
				WebMarkupContainer mainContainer = getMainContainer();
				mainContainer.addOrReplace(new ModifyDeptPanel("mainPanel", dept) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void goTo(AjaxRequestTarget target, Department newDept,
							Department parentDept)
					{
						Component treePanel = getPage().get("contentPanel:contentForm:treePanel");
						BaseTree tree = null;
						if (treePanel instanceof DeptTreePanel) {
							tree = ((DeptTreePanel) treePanel).getTree();
						} else if (treePanel instanceof UserTreePanel) {
							tree = ((UserTreePanel) treePanel).getTree();
						} else {
							setResponsePage(getPage());
							return;
						}

						DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModelObject();
						DepartmentTreeNode node = getCurrentDeptNode(tree);
						node.setUserObject(newDept.getName());
						treeModel.nodeChanged(node);
						tree.updateTree(target);
						WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
								"contentPanel:contentForm:mainContainer");
						refreshMainContainer(mainContainer, target);
					}

				});
				target.add(mainContainer);
			}

		};
		add(updatedept);
		if (!visblelinks) {
			deldept.setVisible(false);
		}
	}

	public WebMarkupContainer getMainContainer()
	{
		return (WebMarkupContainer) getPage().get("contentPanel:contentForm:mainContainer");
	}

	/**
	 * 方法说明：getCurrentDeptNode
	 * 
	 * @param tree
	 *            tree
	 * @return DepartmentTreeNode
	 */
	public DepartmentTreeNode getCurrentDeptNode(BaseTree tree)
	{
		Collection<Object> selectedNodes = tree.getTreeState().getSelectedNodes();
		for (Object node : selectedNodes) {
			if (((DepartmentTreeNode) node).getDepartment().equals(dept)) {
				return (DepartmentTreeNode) node;
			}
		}
		return (DepartmentTreeNode) selectedNodes.iterator().next();
	}

	/**
	 * 方法说明：removeTreeNode
	 * 
	 * @param dept
	 *            dept
	 * @param target
	 *            target
	 */
	public void removeTreeNode(Department dept, AjaxRequestTarget target)
	{
		Component treePanel = getPage().get("contentPanel:contentForm:treePanel");
		BaseTree tree = null;
		if (treePanel instanceof DeptTreePanel) {
			tree = ((DeptTreePanel) treePanel).getTree();
		} else if (treePanel instanceof UserTreePanel) {
			tree = ((UserTreePanel) treePanel).getTree();
		}

		DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModelObject();
		treeModel.removeNodeFromParent(getCurrentDeptNode(tree));
		tree.updateTree(target);
		WebMarkupContainer mainContainer = getMainContainer();
		mainContainer.addOrReplace(new WelcomePanel("mainPanel"));
		target.add(mainContainer);// 刷新右侧panel
	}

	/**
	 * 方法说明：isDepartmentRoot
	 * 
	 * @param department
	 *            department
	 * @return boolean
	 */
	public boolean isDepartmentRoot(Department department)
	{
		boolean flag = false;
		if (getUUMService().getDepartmentRoot().equals(department)) {
			flag = true;
			if (getSetting().getNormalMenuList().contains(MenuItemType.dept)
					|| getSetting().getAdminMenuList().contains(MenuItemType.dept)) {
				setResponsePage(DeptTagPage.class);
			} else {
				setResponsePage(IndexPage.class);
			}
		}

		return flag;
	}

	/**
	 * 方法说明：refreshMainContainer
	 * 
	 * @param mainContainer
	 *            mainContainer
	 * @param target
	 *            target
	 */
	public void refreshMainContainer(WebMarkupContainer mainContainer, AjaxRequestTarget target)
	{

		if (getSetting().getNormalMenuList().contains(MenuItemType.dept)
				|| getSetting().getAdminMenuList().contains(MenuItemType.dept)) {
			mainContainer.addOrReplace(new DeptListPanel("mainPanel", dept));
		} else {
			mainContainer.addOrReplace(new UserListPanel("mainPanel", dept));
		}
		target.add(mainContainer);// 刷新右侧panel
	}
}
