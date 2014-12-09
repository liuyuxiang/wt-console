package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.UserTempLogDao;
import com.wt.uum2.domain.UserTempLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-2
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserTempLogDaoImpl extends BaseDaoSupport<UserTempLog> implements UserTempLogDao
{

	/**
	 * <pre>
	 * 业务名:
	 * 功能说明: 
	 * 编写日期:	2011-9-2
	 * 作者:	Liu Yuxiang
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
		all, /**
	 * 
	 */
		abnormal, /**
	 * 
	 */
		normal, /**
	 * 
	 */
		ignore
	};

	@SuppressWarnings("unchecked")
	public List<UserTempLog> getModifyUserTemp()
	{
		return getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.eq("modifyStatus", 0L)).add(Restrictions.isNotNull("modifyType"))
				.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#updateUserTemp(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：updateUserTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	public int updateUserTemp(UserTempLog newUser)
	{
		getSession().update(newUser);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#getUserTempListByStatus(java.lang.Integer, java.lang.Integer, java.lang.Long)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param i
	 *            i
	 * @return UserPageResult
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, Long i)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.eq("modifyStatus", i)).add(Restrictions.isNotNull("modifyType"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();
		result.setPager(pager);
		// result.setList(getSession().createCriteria(UserTempLog.class)
		// .add(Restrictions.eq("modifyStatus", i)).add(Restrictions.isNotNull("modifyType"))
		// .addOrder(Order.desc("lastUpdateTime")).addOrder(Order.asc("uuid"))
		// .setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
		// .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		result.setList(getSession()
				.createSQLQuery(
						"select * from U2_UserTempLog u where u.modifystatus = :modifystatus and u.modifytype is not null order by u.lastupdatetime desc nulls last,u.uuid asc")
				.addEntity(UserTempLog.class).setLong("modifystatus", i)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/**
	 * 方法说明：getUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserPageResult getUserTempList(Integer page, Integer pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.isNotNull("modifyType"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();
		result.setPager(pager);
		// result.setList(getSession().createCriteria(UserTempLog.class)
		// .add(Restrictions.isNotNull("modifyType"))
		// .addOrder(Order.desc("lastUpdateTime")).addOrder(Order.asc("uuid"))
		// .setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
		// .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		result.setList(getSession()
				.createSQLQuery(
						"select * from U2_UserTempLog u where u.modifytype is not null order by u.lastupdatetime desc nulls last,u.uuid asc")
				.addEntity(UserTempLog.class).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#searchUserTempListByStatus(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.Long)
	 */
	/**
	 * 方法说明：searchUserTempListByStatus
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
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserPageResult searchUserTempListByStatus(Integer page, Integer pagesize,
			Condition condition, Long l)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(UserTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("userName", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(UserTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("userName", "%" + condition.getUserName() + "%"))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession()
					.createCriteria(UserTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("userId", "%" + condition.getUserId() + "%"),
							Restrictions.like("userCode", "%" + condition.getUserId() + "%")))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession()
					.createCriteria(UserTempLog.class)
					.add(Restrictions.eq("modifyStatus", l))
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("userId", "%" + condition.getUserId() + "%"),
							Restrictions.like("userCode", "%" + condition.getUserId() + "%")))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/**
	 * 方法说明：searchUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(UserTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("userName", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(UserTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.like("userName", "%" + condition.getUserName() + "%"))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession()
					.createCriteria(UserTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("userId", "%" + condition.getUserId() + "%"),
							Restrictions.like("userCode", "%" + condition.getUserId() + "%")))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession()
					.createCriteria(UserTempLog.class)
					.add(Restrictions.isNotNull("modifyType"))
					.add(Restrictions.or(
							Restrictions.like("userId", "%" + condition.getUserId() + "%"),
							Restrictions.like("userCode", "%" + condition.getUserId() + "%")))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#getUserTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param userUuid
	 *            userUuid
	 * @return UserTempLog
	 */
	public UserTempLog getUserTempByUuid(String userUuid)
	{
		return (UserTempLog) getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.eq("uuid", userUuid)).uniqueResult();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#getUserTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<UserTempLog> getUserTempListByStatus(Long l)
	{
		return getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.eq("modifyStatus", l)).add(Restrictions.isNotNull("modifyType"))
				.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#getUserTempListByStatus(java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<UserTempLog> getUserTempListByStatus(String type)
	{
		Long modifyStatus = getListType(type);
		return getSession().createCriteria(UserTempLog.class)
				.add(Restrictions.eq("modifyStatus", modifyStatus))
				.add(Restrictions.isNotNull("modifyType")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#getUserTempListByStatus(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@SuppressWarnings("rawtypes")
	public UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, String type)
	{
		Long modifyStatus = getListType(type);
		if (modifyStatus == null) {
			return getUserTempList(page, pagesize);
		}
		return getUserTempListByStatus(page, pagesize, modifyStatus);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempLogDao#searchUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUserTempList
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
	@SuppressWarnings("rawtypes")
	public UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition,
			String type)
	{
		Long modifyStatus = getListType(type);
		if (modifyStatus == null) {
			return searchUserTempList(page, pagesize, condition);
		}
		return searchUserTempListByStatus(page, pagesize, condition, modifyStatus);
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

}
