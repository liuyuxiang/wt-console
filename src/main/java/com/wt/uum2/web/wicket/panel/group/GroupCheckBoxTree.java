package com.wt.uum2.web.wicket.panel.group;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.tree.BaseTree;

import com.hirisun.components.web.wicket.tree.CheckBoxTree;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Group;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-23
 * 作者:	LI chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class GroupCheckBoxTree extends BaseUUMPanel
{

	/**
	 * 
	 */
	private GroupTreeNode rootNode;

	/**
	 * 
	 */
	private List<Group> selectedGroups;

	/**
	 * 
	 */
	private final Set<Group> pathGroups = new HashSet<Group>();
	/**
	 * 是否显示应用系统授权管理
	 */
	private boolean haveAppGroup = false;


	/**
	 * 
	 */
	private static final long serialVersionUID = 5864867816948359058L;

	/**
	 * 
	 */
	private final CheckBoxTree tree;

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param node
	 *            node
	 */
	protected void loadSubTree(AjaxRequestTarget target, Object node) {

		GroupTreeNode current = (GroupTreeNode) node;

		if (!current.isLeaf()) {
	
			/*List<Group> groups = getUUMService().getGroupsByParentGroup(current.getGroup());*/
			List<Group> groups=null;
			if (isModifyGroup(loginUser)) {
				groups = getUUMService().getGroupsByParentGroup(current.getGroup());
				if(!getUUMService().isUserinSuperGroup(loginUser)){
					groups.remove(getUUMService().getGroupByCode(InitParameters.getSuperGroupCode()));
				}
			} else {
				if (InitParameters.isCqGroupAuthor()) {
					groups = getUUMService().getChildGroupByParentGroupAndUserCQ(current.getGroup(), loginUser);
				} else {
					groups = getUUMService().getChildGroupByParentGroupAndUser(current.getGroup(), loginUser);
				}
			}
			for (Group g : groups) {
				if (!haveAppGroup && g.getCode().equals(InitParameters.getRootApplicationGroup())) {
					continue;
				}// 是否显示appGroup(应用系统授权管理)
				GroupTreeNode subNode = new GroupTreeNode(g);
				current.add(subNode);

				boolean nowSelected = selectedGroups.contains(g);

				if (nowSelected) {
					tree.getTreeState().selectNode(subNode, true);
				}

				if (pathGroups.contains(g)) {
					loadSubTree(target, subNode);
					tree.getTreeState().expandNode(subNode);
				}
			}

			if (groups.size() > 0) {
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
	 * @param groups
	 *            groups
	 * @param haveAppGroup
	 *            haveAppGroup
	 */
	public GroupCheckBoxTree(String id, List<Group> groups, boolean haveAppGroup)
	{
		super(id);
		if (haveAppGroup) {
			this.haveAppGroup = haveAppGroup;
		}
		if (groups == null) {
			selectedGroups = new LinkedList<Group>();
		} else {
			selectedGroups = groups;
//			for (Group group : selectedGroups) {
//				List<Group> elderGroups = getUumService().getGroupsByParentGroup(
//						getUumService().getRootGroup());
				// List<Department> elderDepts = getDepartmentPathService()
				// .getAllElderDepts(dept);
				// elderDepts.remove(dept);

				// pathDepts.addAll(elderDepts);///////////////???????????????/

//			}
		}

		tree = new CheckBoxTree("tree", createTreeModel()) {

			/**
			 * 
			 */
			private static final long serialVersionUID = 5080465077526612215L;

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target,
					Object node) {
				super.onJunctionLinkClicked(target, node);
				if(!((GroupTreeNode) node).isJunctionClicked()){
					loadSubTree(target, node);
					((GroupTreeNode) node).setJunctionClicked(true);
				}
			}

			@Override
			protected void onNodeCheckUpdated(TreeNode node, BaseTree tree,
					AjaxRequestTarget target) {
				super.onNodeCheckUpdated(node, tree, target);

				onGroupNodeLinkClicked((GroupTreeNode) node, tree, target);
			}

		};
		tree.setRootLess(true);
		tree.getTreeState().setAllowSelectMultiple(true);
		loadSubTree(null, rootNode);
		tree.getTreeState().expandNode(rootNode);// 展开节点
		add(tree);
	}

	/**
	 * 方法说明：
	 * 
	 * @return TreeModel
	 */
	protected TreeModel createTreeModel() {
		TreeModel model = null;
		Group rootGroup = getUUMService().getRootGroup();
		rootNode = new GroupTreeNode(rootGroup);
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
	 *            tree
	 */
	public abstract void onGroupNodeLinkClicked(GroupTreeNode node,
			BaseTree tree, AjaxRequestTarget target);
}
