package com.wt.uum2.web.wicket.panel.group;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wt.uum2.domain.Group;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-31
 * 作者:	Li Chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3104946662360151607L;

	/**
	 * 
	 */
	private final Group group;
	/**
	 * 
	 */
	private boolean junctionClicked;

	/**
	 * @param group
	 *            group
	 */
	public GroupTreeNode(Group group)
	{
		super(group.getName());
		setAllowsChildren(true);
		this.group = group;
	}

	public Group getGroup()
	{
		return group;
	}

	@Override
	public boolean isLeaf() {
		return !getAllowsChildren();
	}
	public boolean isJunctionClicked()
	{
		return junctionClicked;
	}

	public void setJunctionClicked(boolean junctionClicked)
	{
		this.junctionClicked = junctionClicked;
	}
}
