package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.DepartmentAuthorDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;

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
public class DepartmentAuthorDaoImpl extends BaseDaoSupport<DepartmentAuthor> implements
		DepartmentAuthorDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentAuthorDao#getDepartmentAuthorsByDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getDepartmentAuthorsByDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<DepartmentAuthor> getDepartmentAuthorsByDepartment(Department department)
	{
		return getSession().createCriteria(DepartmentAuthor.class)
				.add(Restrictions.eq("department", department)).list();
	}

}
