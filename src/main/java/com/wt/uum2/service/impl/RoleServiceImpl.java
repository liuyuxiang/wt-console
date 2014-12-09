package com.wt.uum2.service.impl;

import java.util.List;

import nak.nsf.pager.Pager;

import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.RoleDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Role;
import com.wt.uum2.service.RoleService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class RoleServiceImpl implements RoleService
{

	/**
	 * 
	 */
	private RoleDao roleDao;

	/**
	 * @param roleDao
	 *            the roleDao to set
	 */
	public void setRoleDao(RoleDao roleDao)
	{
		this.roleDao = roleDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#getList(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getList
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Role> getList(Department dept)
	{
		return roleDao.readList(dept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#getList(int, int, com.wt.uum2.domain.Department)
	 */
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
	@Transactional(readOnly = true)
	public UserPageResult<Role> getList(int page, int pagesize, Department dept)
	{

		UserPageResult<Role> result = new UserPageResult<Role>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		pager.setQuantityOfData(roleDao.count(dept));

		pager.compute();

		List<Role> list = roleDao.readList(pager.getDataStart(), pager.getPageSize(), dept);

		result.setList(list);
		result.setPager(pager);

		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#create(com.wt.uum2.domain.Role)
	 */
	/**
	 * 方法说明：create
	 * 
	 * @param role
	 *            role
	 */
	@Transactional(readOnly = false)
	public void create(Role role)
	{
		roleDao.save(role);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#update(com.wt.uum2.domain.Role)
	 */
	/**
	 * 方法说明：update
	 * 
	 * @param role
	 *            role
	 */
	@Transactional(readOnly = false)
	public void update(Role role)
	{
		roleDao.update(role);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#getDepartment(com.wt.uum2.domain.Role)
	 */
	/**
	 * 方法说明：getDepartment
	 * 
	 * @param role
	 *            role
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartment(Role role)
	{
		return roleDao.readDepartment(role);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#alreadyHasRoleName(com.wt.uum2.domain.Department, java.lang.String)
	 */
	/**
	 * 方法说明：alreadyHasRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean alreadyHasRoleName(Department dept, String roleName)
	{
		return roleDao.alreadyHasRoleName(dept, roleName);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#getByRoleName(com.wt.uum2.domain.Department, java.lang.String)
	 */
	/**
	 * 方法说明：getByRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return Role
	 */
	@Transactional(readOnly = true)
	public Role getByRoleName(Department dept, String roleName)
	{
		return roleDao.readByRoleName(dept, roleName);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.RoleService#remove(java.util.List)
	 */
	/**
	 * 方法说明：remove
	 * 
	 * @param list
	 *            list
	 */
	@Transactional(readOnly = false)
	public void remove(List<Role> list)
	{
		for (Role role : list) {
			roleDao.delete(role);
		}
	}

}
