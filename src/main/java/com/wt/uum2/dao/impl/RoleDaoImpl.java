package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.RoleDao;
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
public class RoleDaoImpl extends BaseDaoSupport<Role> implements RoleDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#readList(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：readList
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Role> readList(Department dept)
	{
		return getSession().createCriteria(Role.class).add(Restrictions.eq("owner", dept)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#readDepartment(com.wt.uum2.domain.Role)
	 */
	/**
	 * 方法说明：readDepartment
	 * 
	 * @param role
	 *            role
	 * @return Department
	 */
	public Department readDepartment(Role role)
	{
		return (Department) getSession().createCriteria(Department.class)
				.add(Restrictions.idEq(role.getOwnerUUID())).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#alreadyHasRoleName(com.wt.uum2.domain.Department, java.lang.String)
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
	public boolean alreadyHasRoleName(Department dept, String roleName)
	{
		Long count = (Long) getSession().createCriteria(Role.class)
				.add(Restrictions.eq("owner", dept)).add(Restrictions.eq("name", roleName))
				.setProjection(Projections.rowCount()).uniqueResult();
		return count > 0;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#readByRoleName(com.wt.uum2.domain.Department, java.lang.String)
	 */
	/**
	 * 方法说明：readByRoleName
	 * 
	 * @param dept
	 *            dept
	 * @param roleName
	 *            roleName
	 * @return Role
	 */
	public Role readByRoleName(Department dept, String roleName)
	{
		return (Role) getSession().createCriteria(Role.class).add(Restrictions.eq("owner", dept))
				.add(Restrictions.eq("name", roleName)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#count(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：count
	 * 
	 * @param dept
	 *            dept
	 * @return Long
	 */
	public Long count(Department dept)
	{
		return (Long) getSession().createCriteria(Role.class).add(Restrictions.eq("owner", dept))
				.setProjection(Projections.rowCount()).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.RoleDao#readList(int, int, com.wt.uum2.domain.Department)
	 */
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
	public List<Role> readList(int start, int size, Department dept)
	{
		return (List<Role>) getSession().createCriteria(Role.class)
				.add(Restrictions.eq("owner", dept)).setFirstResult(start).setMaxResults(size)
				.list();
	}

}
