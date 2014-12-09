package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Role;

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
public interface RoleDao extends BaseDao<Role>
{

	/**
	 * 方法说明：readList
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Role> readList(Department dept);

	/**
	 * 方法说明：readDepartment
	 * 
	 * @param role
	 *            role
	 * @return Department
	 */
	public Department readDepartment(Role role);

	/**
	 * 方法说明：alreadyHasRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return boolean
	 */
	public boolean alreadyHasRoleName(Department dept, String roleName);

	/**
	 * 方法说明：readByRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return Role
	 */
	public Role readByRoleName(Department dept, String roleName);

	/**
	 * 方法说明：readList
	 * 
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Role> readList(int start, int size, Department dept);

	/**
	 * 方法说明：count
	 * 
	 * @param dept
	 *            dept
	 * @return Long
	 */
	public Long count(Department dept);
}
