package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Role;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface RoleService
{

	/**
	 * 方法说明：getList
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Role> getList(Department dept);

	/**
	 * 方法说明：getList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult<Role> getList(int page, int pagesize, Department dept);

	/**
	 * 方法说明：create
	 * 
	 * @param role
	 *            role
	 */
	public void create(Role role);

	/**
	 * 方法说明：update
	 * 
	 * @param role
	 *            role
	 */
	public void update(Role role);

	/**
	 * 方法说明：getDepartment
	 * 
	 * @param role
	 *            role
	 * @return Department
	 */
	public Department getDepartment(Role role);

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
	 * 方法说明：getByRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return Role
	 */
	public Role getByRoleName(Department dept, String roleName);

	/**
	 * 方法说明：remove
	 * 
	 * @param list
	 *            list
	 */
	public void remove(List<Role> list);

}
