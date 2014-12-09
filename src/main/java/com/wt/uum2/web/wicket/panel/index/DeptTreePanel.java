package com.wt.uum2.web.wicket.panel.index;

import java.util.List;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.request.resource.ContextRelativeResource;

import com.wt.uum2.domain.Department;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.dept.DepartmentTreeNode;

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
public class DeptTreePanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private DepartmentTreeNode rootNode;

	/**
	 * 
	 */
	private DepartmentTreeNode selectedNode;
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
	public DeptTreePanel(String id)
	{
		super(id);
		add(new Image("img", new ContextRelativeResource("/style/hirisun/images/root.gif")));

		tree = new LinkTree("tree", createTreeModel()) {

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node)
			{
				loadSubTree(target, node);
				((DepartmentTreeNode) node).setJunctionClicked(true);
			}

			@Override
			protected void onNodeLinkClicked(Object node, BaseTree tree, AjaxRequestTarget target)
			{
				super.onNodeLinkClicked(node, tree, target);
				tree.getTreeState().selectNode(node, true);
				DeptTreePanel.this.onNodeLinkClicked((DepartmentTreeNode) node, tree, target);

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

		DepartmentTreeNode current = (DepartmentTreeNode) node;
		if (!current.isJunctionClicked()) {
			List<Department> depts = getUUMService().getDepartmentChildren(
					current.getDepartment().getUuid(), loginUser);
			int[] changes = new int[depts.size()];
			for (int i = 0; i < depts.size(); i++) {
				DepartmentTreeNode subNode = new DepartmentTreeNode(depts.get(i));
				current.add(subNode);
				changes[i] = model.getIndexOfChild(current, subNode);
			}
			model.nodesWereInserted(current, changes);
			if (target != null) {
				tree.updateTree(target);
			}
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @return TreeModel
	 */
	protected TreeModel createTreeModel()
	{

		rootNode = new DepartmentTreeNode(getUUMService().getDepartmentRoot());
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
	public void onNodeLinkClicked(DepartmentTreeNode node, BaseTree tree, AjaxRequestTarget target)
	{

	}
}
