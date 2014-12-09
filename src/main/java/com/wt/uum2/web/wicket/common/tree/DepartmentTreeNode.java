package com.wt.uum2.web.wicket.common.tree;

import com.wt.uum2.domain.Department;

/**
 * <pre>
 * 业务名:部门树节点
 * 功能说明: 
 * 编写日期:	2014年4月8日
 * 作者:	lcy
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DepartmentTreeNode extends ResourceMutableTreeNode<Department>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3104946662360151607L;

	/**
	 * @param department
	 *            department
	 */
	public DepartmentTreeNode(Department department)
	{
		super(department, true);
		setUserObject(department.getName());
	}

}
