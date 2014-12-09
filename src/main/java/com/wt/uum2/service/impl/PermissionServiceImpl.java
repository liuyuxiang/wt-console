package com.wt.uum2.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.dao.DepartmentPathDao;
import com.wt.uum2.dao.GroupDao;
import com.wt.uum2.dao.UserDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.PermissionService;

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
@Transactional
public class PermissionServiceImpl implements PermissionService
{

	private UserDao userDao;

	private GroupDao groupDao;

	private DepartmentPathDao departmentPathDao;

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public void setGroupDao(GroupDao groupDao)
	{
		this.groupDao = groupDao;
	}

	public void setDepartmentPathDao(DepartmentPathDao departmentPathDao)
	{
		this.departmentPathDao = departmentPathDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.PermissionService#isDepartmentManager(com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：isDepartmentManager
	 * 
	 * @param user
	 *            user
	 * @param dept
	 *            dept
	 * @return boolean
	 */
	public boolean isDepartmentManager(User user, Department dept)
	{
		if (user == null) {
			return false;
		}
		if (isManager(user)) {
			return true;
		}
		List<Group> userGroups = userDao.getUserGroups(user);
		if (CollectionUtils.isEmpty(userGroups)) {
			// 没有权限组则返回false
			return false;
		}
		List<Department> depts = departmentPathDao.readAllElderDepts(dept);
		if (CollectionUtils.isEmpty(depts)) {
			// 如果没有部门则返回false
			return false;
		}
		for (Department department : depts) {
			// 遍历所有的父部门
			List<Group> adminGroups = groupDao.getAdminGroups(department);
			if (CollectionUtils.isNotEmpty(adminGroups)
					&& CollectionUtils.containsAny(userGroups, adminGroups)) {
				// 如果父部门有管理组,并且和用户组有匹配的组,则返回true
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.PermissionService#isManager(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：isManager
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isManager(User user)
	{
		if (user == null) {
			return false;
		}
		if (isSuperAdmin(user)) {
			return true;
		}
		if (InitParameters.isCqGroupAuthor()) {
			return userDao.getUserGroups(user).contains(
					groupDao.getGroupByCode(InitParameters.modifyUserGroupCode()));
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.PermissionService#isManager(com.wt.uum2.domain.User, com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：isManager
	 * 
	 * @param user
	 *            user
	 * @param resource
	 *            resource
	 * @return boolean
	 */
	public boolean isManager(User user, Resource resource)
	{
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.PermissionService#isSuperAdmin(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：isSuperAdmin
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isSuperAdmin(User user)
	{
		if (user == null) {
			return false;
		}
		Group g = groupDao.getGroupByCode(InitParameters.getSuperGroupCode());
		return userDao.getUserGroups(user).contains(g);
	}

}
