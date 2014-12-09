package com.wt.uum2.dao;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDepartment;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserDepartmentDao extends BaseDao<UserDepartment>
{

	/**
	 * 方法说明：在部门下用户的数量,包括子部门
	 * 
	 * @param department
	 *            department
	 * @return Long
	 */
	public Long countUserInSubDepartment(Department department);

	/**
	 * 方法说明：在部门下用户的数量,不包括子部门
	 * 
	 * @param department
	 *            department
	 * @return Long
	 */
	public Long countUserInDepartment(Department department);

	/**
	 * 
	 * 方法说明：删除用户的所有部门关系
	 * 
	 * @param user
	 *            user
	 */
	public void delete(User user);

}
