package com.wt.uum2.web.wicket.panel.dept;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wt.uum2.domain.Department;

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
public class DepartmentTreeNode extends DefaultMutableTreeNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3104946662360151607L;

	/**
	 * 
	 */
	private Department department;

	/**
	 * 
	 */
	private boolean junctionClicked;
	
	

	/**
	 * @param department
	 *            department
	 */
	public DepartmentTreeNode(Department department) {
		super(department.getName());
		setAllowsChildren(true);
		this.department = department;
		junctionClicked=false;
	}

	/**
	 * @return the department
	 */
	public Department getDepartment() {
		return department;
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
