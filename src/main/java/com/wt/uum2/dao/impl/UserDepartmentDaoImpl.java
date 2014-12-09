package com.wt.uum2.dao.impl;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.dao.UserDepartmentDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDepartment;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-10-9
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserDepartmentDaoImpl extends BaseDaoSupport<UserDepartment> implements
		UserDepartmentDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDepartmentDao#countUserInSubDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：countUserInSubDepartment
	 * 
	 * @param department
	 *            department
	 * @return Long
	 */
	public Long countUserInSubDepartment(Department department)
	{
		String hql = "select count(ud.userUUID) from User as u, UserDepartment as ud,DepartmentPath as dp where u.uuid=ud.userUUID and u.status=:status and dp.elder=:department and dp.juniorUUID=ud.departmentUUID";
		return (Long) getSession().createQuery(hql)
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setEntity("department", department).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDepartmentDao#countUserInDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：countUserInDepartment
	 * 
	 * @param department
	 *            department
	 * @return Long
	 */
	public Long countUserInDepartment(Department department)
	{
		if (department == null) {
			return 0L;
		}
		String hql = "select count(ud.userUUID) from User as u, UserDepartment as ud where u.uuid=ud.userUUID and u.status=:status and ud.departmentUUID=:deptUUID";
		return (Long) getSession().createQuery(hql)
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("deptUUID", department.getUuid()).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDepartmentDao#delete(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param user
	 *            user
	 */
	public void delete(User user)
	{
		String hql = "delete UserDepartment ud where ud.user =:user";
		getSession().createQuery(hql).setEntity("user", user).executeUpdate();
	}

}
