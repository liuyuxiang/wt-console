package com.wt.uum2.web.wicket.panel.dept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.model.IModel;

import com.hirisun.components.web.wicket.tree.CheckBoxTree;
import com.wt.uum2.domain.Department;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.web.wicket.MainUUMApplication;

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
public abstract class DeptTreePanel extends Panel
{

	/**
	 * 
	 */
	private DepartmentTreeNode rootNode;

	/**
	 * 
	 */
	private List<Department> selectedDepts;

	/**
	 * 
	 */
	private List<Department> disableDepts;

	/**
	 * 无根的无
	 */
	private boolean rootLess;

	/**
	 * 
	 */
	private final Set<Department> pathDepts = new HashSet<Department>();

	protected UUMService getUumService()
	{
		return ((MainUUMApplication) getApplication()).getUUMService();
	}

	protected DepartmentPathService getDepartmentPathService()
	{
		return ((MainUUMApplication) getApplication()).getDepartmentPathService();
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 5864867816948359058L;

	/**
	 * 
	 */
	private CheckBoxTree tree;

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param node
	 *            node
	 */
	protected void loadSubTree(AjaxRequestTarget target, Object node)
	{

		DepartmentTreeNode current = (DepartmentTreeNode) node;

		if (!current.isLeaf()) {
			List<Department> depts = getUumService().getDepartmentChildren(
					current.getDepartment().getUuid());

			for (Department dept : depts) {
				DepartmentTreeNode subNode = new DepartmentTreeNode(dept);
				current.add(subNode);

				boolean nowSelected = selectedDepts.contains(dept);

				if (nowSelected) {
					tree.getTreeState().selectNode(subNode, true);
				}

				if (pathDepts.contains(dept)) {
					loadSubTree(target, subNode);
					tree.getTreeState().expandNode(subNode);
				}
			}

			if (depts.size() > 0) {
				current.setAllowsChildren(true);
			} else {
				current.setAllowsChildren(false);
			}

			if (target != null) {
				tree.updateTree(target);
			}
		}

	}

	/**
	 * @param id
	 *            id
	 * @param depts
	 *            depts
	 * @param disableDepts
	 *            disableDepts
	 * @param selectMultiple
	 *            selectMultiple
	 * @param rootLess
	 *            rootLess
	 */
	public DeptTreePanel(String id, List<Department> depts, List<Department> disableDepts,
			boolean selectMultiple, boolean rootLess)
	{
		super(id);

		if (depts == null) {
			selectedDepts = new LinkedList<Department>();
		} else {
			selectedDepts = depts;
			for (Department dept : selectedDepts) {
				List<Department> elderDepts = getDepartmentPathService().getAllElderDepts(dept);
				elderDepts.remove(dept);
				pathDepts.addAll(elderDepts);
			}
		}
		if (disableDepts == null) {
			disableDepts = new ArrayList<Department>();
		}
		this.disableDepts = disableDepts;
		initTree(selectMultiple, rootLess);
	}

	/**
	 * 方法说明：initTree
	 * 
	 * @param selectMultiple
	 *            selectMultiple
	 * @param isRootLess
	 *            isRootLess
	 */
	public void initTree(boolean selectMultiple, boolean isRootLess)
	{

		tree = new CheckBoxTree("tree", createTreeModel()) {

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node)
			{
				super.onJunctionLinkClicked(target, node);
				if (!((DepartmentTreeNode) node).isJunctionClicked()) {
					loadSubTree(target, node);
					((DepartmentTreeNode) node).setJunctionClicked(true);
				}

			}

			@Override
			protected void onNodeCheckUpdated(TreeNode node, BaseTree tree, AjaxRequestTarget target)
			{
				super.onNodeCheckUpdated(node, tree, target);

				onDeptNodeLinkClicked((DepartmentTreeNode) node, tree, target);
			}

			@Override
			protected void visibleCheckboxIconPanel(IModel<Object> model, CheckBox checkbox)
			{
				super.visibleCheckboxIconPanel(model, checkbox);
				if (CollectionUtils.isNotEmpty(disableDepts)) {
					DepartmentTreeNode node = (DepartmentTreeNode) model.getObject();
					if (disableDepts.contains(node.getDepartment())) {
						checkbox.setVisible(false);
					}
				}
			}

		};

		tree.getTreeState().setAllowSelectMultiple(selectMultiple);
		loadSubTree(null, rootNode);
		tree.getTreeState().expandNode(rootNode);
		tree.setRootLess(isRootLess);
		add(tree);
	}

	/**
	 * 方法说明：
	 * 
	 * @return TreeModel
	 */
	protected TreeModel createTreeModel()
	{
		TreeModel model = null;
		Department root = getUumService().getDepartmentRoot();
		rootNode = new DepartmentTreeNode(root);

		model = new DefaultTreeModel(rootNode);
		return model;
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
	public abstract void onDeptNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
			AjaxRequestTarget target);
}
