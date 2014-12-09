package com.wt.uum2.web.wicket.panel.application;

import java.util.Collection;

import javax.swing.tree.DefaultTreeModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.wt.uum2.constants.MenuItemType;
import com.wt.uum2.domain.Application;
import com.wt.uum2.web.wicket.panel.AreYouSurePanel;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.index.DeptTreePanel;
import com.wt.uum2.web.wicket.panel.index.WelcomePanel;

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
public class AppListToolbarPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1918512800881651195L;
	/**
	 * 
	 */
	private Application app;

	/**
	 * @param id
	 *            id
	 * @param app
	 *            app
	 * @param visblelinks
	 *            visblelinks
	 */
	public AppListToolbarPanel(String id, final Application app, boolean visblelinks)
	{
		super(id);
		this.app = app;
		add(new Label("currentDeptPath", "【" + app.getName() + "】成员列表"));
		AreYouSurePanel delapp = new AreYouSurePanel("delete", "删除当前应用", "是否删除应用\"" + app.getName()
				+ "\"？") {

			@Override
			protected void onConfirm(AjaxRequestTarget target)
			{
				// if ("ROOT".equalsIgnoreCase(app.getName())) {
				// target.appendJavaScript("alert('根节点不能删除！')");
				// return;
				// }
				// if (getUUMService().hasSubApplication(app)) {
				// target.appendJavaScript("alert('该部门内存在子部门，不能被删除！')");
				// return;
				// }
				// if (getUUMService().existUserUnderApplication(app)) {
				// target.appendJavaScript("alert('该部门内存在用户，不能被删除！')");
				// return;
				// }

				// Event event = getEventFactory().createEventDeleteDept(app.getUuid());
				getUUMService().deleteApplication(app);
				// getEventListenerHandler().handle(event);// 删除部门

				removeTreeNode(app, target);// 删除树中此节点
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

		add(delapp);

		AjaxLink updateapp = new AjaxLink("modify") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if ("ROOT".equalsIgnoreCase(app.getName())) {
					target.appendJavaScript("alert('根节点不能更新！')");
					return;
				}
				WebMarkupContainer mainContainer = getMainContainer();
				mainContainer.addOrReplace(new AppPanel("mainPanel", app) {

					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public void goTo(AjaxRequestTarget target, Application newDept,
							Application parentDept)
					{
						// BaseTree tree = ((DeptTreePanel) getPage().get(
						// "contentPanel:contentForm:treePanel")).getTree();
						// DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModelObject();
						// AppTreeNode node = getCurrentAppNode(tree);
						// node.setUserObject(newDept.getName());
						// treeModel.nodeChanged(node);
						// tree.updateTree(target);
						// WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						// "contentPanel:contentForm:mainContainer");
						// refreshMainContainer(mainContainer, target);
					}

				});
				target.add(mainContainer);
			}

		};
		add(updateapp);
		if (!visblelinks) {
			delapp.setVisible(false);
		}

		AppSearchToolbarPanel search = new AppSearchToolbarPanel("search", null, null) {

			/**
			 * 
			 */
			private static final long serialVersionUID = -6385779371962222187L;

		};
		add(search.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));
	}

	/**
	 * 方法说明：getAppTree
	 * 
	 * @return AppTreePanel
	 */
	public AppTreePanel getAppTree()
	{
		return (AppTreePanel) getPage().get("contentPanel:contentForm:treePanel");
	}

	public WebMarkupContainer getMainContainer()
	{
		return (WebMarkupContainer) getPage().get("contentPanel:contentForm:mainContainer");
	}

	/**
	 * 方法说明：getCurrentAppNode
	 * 
	 * @param tree
	 *            tree
	 * @return AppTreeNode
	 */
	public AppTreeNode getCurrentAppNode(BaseTree tree)
	{
		Collection<Object> selectedNodes = tree.getTreeState().getSelectedNodes();
		for (Object node : selectedNodes) {
			if (((AppTreeNode) node).getApplication().equals(app)) {
				return (AppTreeNode) node;
			}
		}
		return (AppTreeNode) selectedNodes.iterator().next();
	}

	/**
	 * 方法说明：removeTreeNode
	 * 
	 * @param app
	 *            app
	 * @param target
	 *            target
	 */
	public void removeTreeNode(Application app, AjaxRequestTarget target)
	{
		BaseTree tree = ((DeptTreePanel) getPage().get("contentPanel:contentForm:treePanel"))
				.getTree();
		DefaultTreeModel treeModel = (DefaultTreeModel) tree.getModelObject();
		treeModel.removeNodeFromParent(getCurrentAppNode(tree));
		tree.updateTree(target);
		WebMarkupContainer mainContainer = getMainContainer();
		mainContainer.addOrReplace(new WelcomePanel("mainPanel"));
		target.add(mainContainer);// 刷新右侧panel
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

		if (getSetting().getNormalMenuList().contains(MenuItemType.app)
				|| getSetting().getAdminMenuList().contains(MenuItemType.app)) {
			mainContainer.addOrReplace(new AppListPanel("mainPanel", app));
		}
		target.add(mainContainer);// 刷新右侧panel
	}

}
