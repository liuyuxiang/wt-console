package com.wt.uum2.dao.impl;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentTempDao;
import com.wt.uum2.domain.DepartmentTemp;
import com.wt.uum2.userlist.DeptTempType;

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
public class DepartmentTempDaoImpl extends BaseDaoSupport<DepartmentTemp> implements
		DepartmentTempDao
{

	/**
	 * 方法说明：getDeptTempSql
	 * 
	 * @param type
	 *            type
	 * @param string
	 *            string
	 * @return String
	 */
	private String getDeptTempSql(DeptTempType type, String string)
	{
		String q = "select XXXX from U2_DepartmentTemp t,U2_Department d XXXXX";
		switch (type) {
		case erpDept:
		{
			q = q.replace("XXXXX", " where t.deptcode=d.deptcode(+)");
		}
			break;
		case mapping:
		{
			q = q.replace("XXXXX", " where t.deptcode=d.deptcode");
		}
			break;
		case noMapping:
		{
			q = q.replace("XXXXX", " where t.deptcode=d.deptcode(+) and d.deptcode is null");
		}
			break;
		}
		return q.replace("XXXX", string);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempDao#getDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.userlist.DeptTempType)
	 */
	/**
	 * 方法说明：getDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize, DeptTempType type)
	{
		String c = getDeptTempSql(type, "count(t.uuid)");
		String l = getDeptTempSql(
				type,
				"t.uuid,t.deptName,t.deptCode,t.parentDeptCode,t.deptOrgCode,t.deptOrder,d.uuid as did,t.lastUpdateTime");
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Integer count = Integer.valueOf(getSession().createSQLQuery(c).uniqueResult().toString());
		pager.setQuantityOfData(count);
		pager.compute();
		result.setPager(pager);
		result.setList(getSession().createSQLQuery(l).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempDao#searchDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.userlist.DeptTempType)
	 */
	/**
	 * 方法说明：searchDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition, DeptTempType type)
	{
		String l = "t.uuid,t.deptName,t.deptCode,t.parentDeptCode,t.deptOrgCode,t.deptOrder,d.uuid as did";
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (StringUtils.isNotBlank(condition.getUserName())) {
			String a = " and t.deptName like :deptName";
			Integer count = Integer.valueOf(getSession()
					.createSQLQuery(getDeptTempSql(type, "count(t.uuid)") + a)
					.setString("deptName", "%" + condition.getUserName() + "%").uniqueResult()
					.toString());

			pager.setQuantityOfData(count);

			pager.compute();

			result.setList(getSession().createSQLQuery(getDeptTempSql(type, l) + a)
					.setString("deptName", "%" + condition.getUserName() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			String a = " and t.deptCode like :deptCode";
			Integer count = Integer.valueOf(getSession()
					.createSQLQuery(getDeptTempSql(type, "count(t.uuid)") + a)
					.setString("deptCode", "%" + condition.getUserId() + "%").uniqueResult()
					.toString());

			pager.setQuantityOfData(count);

			pager.compute();

			result.setList(getSession().createSQLQuery(getDeptTempSql(type, l) + a)
					.setString("deptCode", "%" + condition.getUserId() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempDao#getDepartmentTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTemp
	 */
	public DepartmentTemp getDepartmentTempByUuid(String deptUuid)
	{
		return (DepartmentTemp) getSession().createCriteria(DepartmentTemp.class)
				.add(Restrictions.eq("uuid", deptUuid)).uniqueResult();

	}

}
