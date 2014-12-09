package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentTempLogDao;
import com.wt.uum2.domain.DepartmentTempLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-09-2
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DepartmentTempLogDaoImpl extends BaseDaoSupport<DepartmentTempLog> implements
		DepartmentTempLogDao
{

	/**
	 * <pre>
	 * 业务名:
	 * 功能说明: 
	 * 编写日期:	2011-09-2
	 * 作者:	Liu yuxiang
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	public enum LogType
	{
		/**
		 * 
		 */
		all, abnormal, normal, ignore
	};

	public List<DepartmentTempLog> getModifyDeptTemp()
	{
		return getDepartmentTempListByStatus(0L);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#updateDeptTemp(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：updateDeptTemp
	 * 
	 * @param newDept
	 *            newDept
	 * @return int
	 */
	public int updateDeptTemp(DepartmentTempLog newDept)
	{
		getSession().update(newDept);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#getDepartmentTempListByStatus(java.lang.Integer, java.lang.Integer, java.lang.Long)
	 */
	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param i
	 *            i
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempListByStatus(Integer page, Integer pagesize, Long i)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(DepartmentTempLog.class)
				.add(Restrictions.eq("modifyStatus", i)).add(Restrictions.isNotNull("modifyType"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();
		result.setPager(pager);
		result.setList(getSession()
				.createSQLQuery(
						"select * from U2_DepartmentTempLog u where u.modifystatus = :modifystatus and u.modifytype is not null order by u.lastupdatetime desc nulls last")
				.addEntity(DepartmentTempLog.class).setLong("modifystatus", i)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/**
	 * 方法说明：getDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(DepartmentTempLog.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();
		result.setPager(pager);
		// result.setList(getSession().createCriteria(DepartmentTempLog.class)
		// .addOrder(Order.desc("lastUpdateTime")).setFirstResult(
		// pager.getDataStart())
		// .setMaxResults(pager.getPageSize()).setResultTransformer(
		// CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		result.setList(getSession()
				.createSQLQuery(
						"select * from U2_DepartmentTempLog u where u.modifytype is not null order by u.lastupdatetime desc nulls last")
				.addEntity(DepartmentTempLog.class).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#searchDepartmentTempListByStatus(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.Long)
	 */
	/**
	 * 方法说明：searchDepartmentTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentTempListByStatus(Integer page, Integer pagesize,
			Condition condition, Long l)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(DepartmentTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("deptName", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			// result.setList(getSession().createCriteria(DepartmentTempLog.class)
			// .add(Restrictions.eq("modifyStatus", l)).add(
			// Restrictions.isNotNull("modifyType")).add(
			// Restrictions.like("deptName", "%"
			// + condition.getUserName() + "%")).addOrder(
			// Order.desc("lastUpdateTime")).setFirstResult(
			// pager.getDataStart()).setMaxResults(
			// pager.getPageSize()).setResultTransformer(
			// CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setList(getSession()
					.createSQLQuery(
							"select * from U2_DepartmentTempLog u where u.modifystatus = :modifystatus and u.modifytype is not null and u.deptname like :deptname order by u.lastupdatetime desc nulls last")
					.addEntity(DepartmentTempLog.class).setLong("modifystatus", l)
					.setString("deptname", "%" + condition.getUserName() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession()
					.createCriteria(DepartmentTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("deptCode", "%" + condition.getUserId() + "%"),
							Restrictions.like("deptId", "%" + condition.getUserId() + "%")))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			// result.setList(getSession().createCriteria(DepartmentTempLog.class)
			// .add(Restrictions.eq("modifyStatus", l)).add(
			// Restrictions.isNotNull("modifyType")).add(
			// Restrictions.or(Restrictions.like("deptCode", "%"
			// + condition.getUserId() + "%"),
			// Restrictions.like("deptId", "%"
			// + condition.getUserId() + "%")))
			// .addOrder(Order.desc("lastUpdateTime")).setFirstResult(
			// pager.getDataStart()).setMaxResults(
			// pager.getPageSize()).setResultTransformer(
			// CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
			result.setList(getSession()
					.createSQLQuery(
							"select * from U2_DepartmentTempLog u where u.modifystatus = :modifystatus and u.modifytype is not null and (u.deptcode like :deptcode or u.deptid like :deptid) order by u.lastupdatetime desc nulls last")
					.addEntity(DepartmentTempLog.class).setLong("modifystatus", l)
					.setString("deptcode", "%" + condition.getUserId() + "%")
					.setString("deptid", "%" + condition.getUserId() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 方法说明：searchDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(DepartmentTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("deptName", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			// result.setList(getSession().createCriteria(DepartmentTempLog.class)
			// .add(Restrictions.isNotNull("modifyType")).add(
			// Restrictions.like("deptName", "%"
			// + condition.getUserName() + "%")).addOrder(
			// Order.desc("lastUpdateTime")).setFirstResult(
			// pager.getDataStart()).setMaxResults(
			// pager.getPageSize()).setResultTransformer(
			// CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
			result.setList(getSession()
					.createSQLQuery(
							"select * from U2_DepartmentTempLog u where u.modifytype is not null and u.deptname like :deptname order by u.lastupdatetime desc nulls last")
					.addEntity(DepartmentTempLog.class)
					.setString("deptname", "%" + condition.getUserName() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession()
					.createCriteria(DepartmentTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("deptCode", "%" + condition.getUserId() + "%"),
							Restrictions.like("deptId", "%" + condition.getUserId() + "%")))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			// result.setList(getSession().createCriteria(DepartmentTempLog.class)
			// .add(Restrictions.isNotNull("modifyType")).add(
			// Restrictions.or(Restrictions.like("deptCode", "%"
			// + condition.getUserId() + "%"),
			// Restrictions.like("deptId", "%"
			// + condition.getUserId() + "%")))
			// .addOrder(Order.desc("lastUpdateTime")).setFirstResult(
			// pager.getDataStart()).setMaxResults(
			// pager.getPageSize()).setResultTransformer(
			// CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setList(getSession()
					.createSQLQuery(
							"select * from U2_DepartmentTempLog u where u.modifytype is not null and (u.deptcode like :deptcode or u.deptid like :deptid) order by u.lastupdatetime desc nulls last")
					.addEntity(DepartmentTempLog.class)
					.setString("deptcode", "%" + condition.getUserId() + "%")
					.setString("deptid", "%" + condition.getUserId() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#getDepartmentTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTempLog
	 */
	public DepartmentTempLog getDepartmentTempByUuid(String deptUuid)
	{
		return (DepartmentTempLog) getSession().createCriteria(DepartmentTempLog.class)
				.add(Restrictions.eq("uuid", deptUuid)).uniqueResult();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#getDepartmentTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	public List<DepartmentTempLog> getDepartmentTempListByStatus(Long l)
	{
		// return getSession().createCriteria(DepartmentTempLog.class).add(
		// Restrictions.eq("modifyStatus", l)).add(
		// Restrictions.isNotNull("modifyType")).addOrder(
		// Order.asc("lastUpdateTime")).addOrder(Order.asc("uuid"))
		// .addOrder(Order.asc("deptCode")).list();
		return getSession()
				.createSQLQuery(
						"select * from U2_DepartmentTempLog u where u.modifyStatus = :modifyStatus and u.modifytype is not null order by u.lastupdatetime asc nulls last,u.uuid asc,u.deptcode asc")
				.addEntity(DepartmentTempLog.class).setLong("modifyStatus", l).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#getDepartmentTempListByStatus(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	public List<DepartmentTempLog> getDepartmentTempListByStatus(String type)
	{
		return getDepartmentTempListByStatus(getListType(type));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#getDepartmentTempListByStatus(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempListByStatus(Integer page, Integer pagesize, String type)
	{
		Long modifyStatus = getListType(type);
		if (modifyStatus == null) {
			return getDepartmentTempList(page, pagesize);
		}
		return getDepartmentTempListByStatus(page, pagesize, modifyStatus);
	}

	/**
	 * 方法说明：getListType
	 * 
	 * @param type
	 *            type
	 * @return Long
	 */
	private Long getListType(String type)
	{
		if (type == null) {
			return null;
		}
		switch (DepartmentTempLogDaoImpl.LogType.valueOf(type)) {
		case abnormal:
			return 1L;
		case normal:
			return 2L;
		case ignore:
			return 3L;
		default:
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentTempLogDao#searchDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.String)
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
			Condition condition, String type)
	{
		Long modifyStatus = getListType(type);
		if (modifyStatus == null) {
			return searchDepartmentTempList(page, pagesize, condition);
		}
		return searchDepartmentTempListByStatus(page, pagesize, condition, modifyStatus);
	}

}
