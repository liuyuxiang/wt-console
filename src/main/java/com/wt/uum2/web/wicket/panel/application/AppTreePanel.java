package com.wt.uum2.web.wicket.panel.application;

import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.request.resource.ContextRelativeResource;

import com.wt.uum2.domain.Application;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

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
public class AppTreePanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9161589269246934232L;

	/**
	 * 
	 */
	private AppTreeNode rootNode;

	/**
	 * 
	 */
	private LinkTree tree;
	/**
	 * 
	 */
	private DefaultTreeModel model;

	/**
	 * @param id
	 *            id
	 */
	public AppTreePanel(String id)
	{
		super(id);
		add(new Image("img", new ContextRelativeResource("/style/hirisun/images/root.gif")));

		tree = new LinkTree("tree", createTreeModel()) {

			@Override
			protected void onNodeLinkClicked(Object node, BaseTree tree, AjaxRequestTarget target)
			{
				super.onNodeLinkClicked(node, tree, target);
				tree.getTreeState().selectNode(node, true);
				AppTreePanel.this.onNodeLinkClicked((AppTreeNode) node, tree, target);

			}

		};
		add(tree);
		tree.getTreeState().expandNode(rootNode);
		tree.setRootLess(true);
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param node
	 *            node
	 */
	public void loadSubTree(AjaxRequestTarget target, Object node)
	{

		AppTreeNode current = (AppTreeNode) node;
			List<Application> apps = getUUMService().getAllApplication(-1,0).getList();
			int[] changes = new int[apps.size()];
			for (int i = 0; i < apps.size(); i++) {
				AppTreeNode subNode = new AppTreeNode(apps.get(i));
				current.add(subNode);
				changes[i] = model.getIndexOfChild(current, subNode);
			}
			model.nodesWereInserted(current, changes);
			if (target != null) {
				tree.updateTree(target);
			}
	}

	/**
	 * 方法说明：
	 * 
	 * @return TreeModel
	 */
	protected TreeModel createTreeModel()
	{
		rootNode = new AppTreeNode();
		model = new DefaultTreeModel(rootNode);
		loadSubTree(null, rootNode);
		return model;
	}

	public LinkTree getTree()
	{
		return tree;
	}

	/**
	 * 方法说明：
	 * 
	 * @param node
	 *            node
	 * @param tree
	 *            tree
	 * @param target
	 *            target
	 */
	public void onNodeLinkClicked(AppTreeNode node, BaseTree tree, AjaxRequestTarget target)
	{

	}
}
