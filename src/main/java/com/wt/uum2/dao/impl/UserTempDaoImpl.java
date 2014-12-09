package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.UserTempDao;
import com.wt.uum2.domain.UserTemp;
import com.wt.uum2.userlist.UserTempType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserTempDaoImpl extends BaseDaoSupport<UserTemp> implements UserTempDao
{

	static final String MAPPINGUSER_LIST = "select t.uuid," + "       t.username,"
			+ "       t.usercode," + "       t.deptcode," + "       t.deptname," + "       u.id,"
			+ "       u.name uname," + "       t.lastUpdateTime,t.modifyType"
			+ "  from U2_User u, U2_ResourceMapping rm, U2_UserTemp t,U2_Resource r"
			+ " where rm.mappingid = t.usercode" + " and u.uuid = r.uuid and r.status=1"
			+ " and u.uuid = rm.resourceuuid order by t.usercode";

	static final String MAPPINGUSER_COUNT = "select count(t.uuid)"
			+ "  from U2_User u, U2_ResourceMapping rm, U2_UserTemp t,U2_Resource r"
			+ " where rm.mappingid = t.usercode" + " and u.uuid = r.uuid and r.status=1"
			+ " and u.uuid = rm.resourceuuid order by t.usercode";
	static final String NOMAPPINGUSER_LIST = "select t.uuid,"
			+ "       t.username,"
			+ "       t.usercode,"
			+ "       t.deptcode,"
			+ "       t.deptname,"
			+ "       '' \"id\","
			+ "       '' \"name\","
			+ "       t.lastUpdateTime,t.modifyType"
			+ "  from U2_UserTemp t"
			+ " where not exists"
			+ " (select * from U2_ResourceMapping s where s.mappingid = t.usercode) order by t.usercode";

	static final String NOMAPPINGUSER_COUNT = "select count(t.uuid)" + "  from U2_UserTemp t"
			+ " where not exists"
			+ " (select * from U2_ResourceMapping s where s.mappingid = t.usercode)";

	static final String SEARCH_MAPPINGUSER_LIST = "select t.uuid," + "       t.username,"
			+ "       t.usercode," + "       t.deptcode," + "       t.deptname dname,"
			+ "       u.id," + "       u.name uname," + "       t.lastUpdateTime,t.modifyType"
			+ "  from U2_User u, U2_ResourceMapping rm, U2_UserTemp t,U2_Resource r"
			+ " where rm.mappingid = t.usercode" + " and u.uuid = r.uuid and r.status=1"
			+ " and u.uuid = rm.resourceuuid ";

	static final String SEARCH_MAPPINGUSER_COUNT = "select count(t.uuid)"
			+ "  from U2_User u, U2_ResourceMapping rm, U2_UserTemp t,U2_Resource r"
			+ " where rm.mappingid = t.usercode" + " and u.uuid = r.uuid and r.status=1"
			+ " and u.uuid = rm.resourceuuid ";
	static final String SEARCH_NOMAPPINGUSER_LIST = "select t.uuid,"
			+ "       t.username,"
			+ "       t.usercode,"
			+ "       t.deptcode,"
			+ "       t.deptname,"
			+ "       '' \"id\","
			+ "       '' \"name\","
			+ "       t.lastUpdateTime,t.modifyType"
			+ "  from U2_UserTemp t"
			+ " where not exists"
			+ " (select * from U2_ResourceMapping s,U2_Resource r where r.status=1 and r.uuid=s.resourceuuid and s.mappingid = t.usercode) ";

	static final String SEARCH_NOMAPPINGUSER_COUNT = "select count(t.uuid)"
			+ "  from U2_UserTemp t"
			+ " where not exists"
			+ " (select * from U2_ResourceMapping s,U2_Resource r where r.status=1 and r.uuid=s.resourceuuid and s.mappingid = t.usercode) ";

	static final String ERPUSER_LIST = "select t.uuid,t.username,t.usercode,t.deptcode,t.deptname ,u.id,u.name ,t.lastUpdateTime,t.modifyType from U2_UserTemp t left join U2_ResourceMapping rm on t.usercode = rm.mappingid left join U2_User u on u.uuid = rm.resourceuuid where 1=1 ";
	static final String ERPUSER_COUNT = "select count(t.uuid) from U2_UserTemp t left join U2_ResourceMapping rm on t.usercode = rm.mappingid left join U2_User u on u.uuid = rm.resourceuuid where 1=1 ";

	public List<UserTemp> getUserTempList()
	{
		return getSession().createCriteria(UserTemp.class).list();
	}

	/**
	 * 方法说明：updateUserTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	public int updateUserTemp(UserTemp newUser)
	{
		getSession().update(newUser);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#getUserTempList(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<UserTemp> getUserTempList(Integer page, Integer pagesize)
	{
		UserPageResult<UserTemp> result = new UserPageResult<UserTemp>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Long count = (Long) getSession().createCriteria(UserTemp.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.count("uuid")).uniqueResult();
		pager.setQuantityOfData(count);
		pager.compute();
		result.setPager(pager);
		result.setList(getSession().createCriteria(UserTemp.class)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#getUserTempMappingList(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getUserTempMappingList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getUserTempMappingList(Integer page, Integer pagesize)
	{
		UserPageResult<UserTemp> result = new UserPageResult<UserTemp>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData(Integer.valueOf(getSession().createSQLQuery(MAPPINGUSER_COUNT)
				.uniqueResult().toString()));

		pager.compute();
		result.setPager(pager);
		result.setList(getSession().createSQLQuery(MAPPINGUSER_LIST)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#getUserTempConditionList(java.lang.Integer, java.lang.Integer, com.wt.uum2.userlist.UserTempType)
	 */
	/**
	 * 方法说明：getUserTempConditionList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getUserTempConditionList(Integer page, Integer pagesize, UserTempType type)
	{
		String list = ERPUSER_LIST;
		String count = ERPUSER_COUNT;
		switch (type) {
		case mapping:
		{
			list = MAPPINGUSER_LIST;
			count = MAPPINGUSER_COUNT;
		}
			break;

		case noMapping:
		{
			list = NOMAPPINGUSER_LIST;
			count = NOMAPPINGUSER_COUNT;
		}
			break;
		}
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData(Integer.valueOf(getSession().createSQLQuery(count).uniqueResult()
				.toString()));

		pager.compute();
		result.setPager(pager);
		result.setList(getSession().createSQLQuery(list).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#searchUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.userlist.UserTempType)
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
	public UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition,
			UserTempType type)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		String c = ERPUSER_COUNT;
		String l = ERPUSER_LIST;

		switch (type) {
		case noMapping:
		{
			c = SEARCH_NOMAPPINGUSER_COUNT;
			l = SEARCH_NOMAPPINGUSER_LIST;
		}
			break;

		case mapping:
		{
			c = SEARCH_MAPPINGUSER_COUNT;
			l = SEARCH_MAPPINGUSER_LIST;
		}
			break;
		}

		if (!StringUtils.equalsIgnoreCase(condition.getUserName(), "")) {
			String condtions = "%" + condition.getUserName() + "%";
			c += " and t.userName like :userName";
			l += " and t.userName like :userName";
			Integer count = Integer.valueOf(getSession().createSQLQuery(c)
					.setString("userName", condtions).uniqueResult().toString());

			pager.setQuantityOfData(count);
			pager.compute();
			result.setList(getSession().createSQLQuery(l).setString("userName", condtions)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			String condtions = "%" + condition.getUserId() + "%";
			c += " and t.userCode like :userCode";
			l += " and t.userCode like :userCode";
			Integer count = Integer.valueOf(getSession().createSQLQuery(c)
					.setString("userCode", condtions).uniqueResult().toString());

			pager.setQuantityOfData(count);
			pager.compute();
			result.setList(getSession().createSQLQuery(l).setString("userCode", condtions)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else {
			pager.setQuantityOfData(0);
			pager.compute();
			result.setList(new ArrayList<UserTemp>());
			result.setPager(pager);
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#searchUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition)
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
	 * @return UserPageResult
	 */
	public UserPageResult<UserTemp> searchUserTempList(Integer page, Integer pagesize,
			Condition condition)
	{
		UserPageResult<UserTemp> result = new UserPageResult<UserTemp>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!StringUtils.equalsIgnoreCase(condition.getUserName(), "")) {
			String condtions = "%" + condition.getUserName() + "%";
			Long count = (Long) getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userName", condtions))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.count("uuid")).uniqueResult();
			pager.setQuantityOfData(count);
			pager.compute();
			result.setList(getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userName", condtions))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			String condtions = "%" + condition.getUserId() + "%";
			Long count = (Long) getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userCode", condtions))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.count("uuid")).uniqueResult();
			pager.setQuantityOfData(count);
			pager.compute();
			result.setList(getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userCode", condtions))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else {
			pager.setQuantityOfData(0);
			pager.compute();
			result.setList(new ArrayList<UserTemp>());
			result.setPager(pager);
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#searchUserTempMappingList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchUserTempMappingList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult<UserTemp> searchUserTempMappingList(Integer page, Integer pagesize,
			Condition condition)
	{
		UserPageResult<UserTemp> result = new UserPageResult<UserTemp>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (StringUtils.equalsIgnoreCase(condition.getUserName(), "")) {
			String condtions = "%" + condition.getUserName() + "%";
			pager.setQuantityOfData(Integer.valueOf(getSession()
					.createQuery("select count(*) from UserTemp where userName like :userName")
					.setString("userName", condtions).uniqueResult().toString()));
			pager.compute();
			result.setList(getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userName", condtions))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			String condtions = "%" + condition.getUserId() + "%";
			pager.setQuantityOfData(Integer.valueOf(getSession()
					.createQuery("select count(*) from UserTemp where userCode like :userCode")
					.setString("userCode", condtions).uniqueResult().toString()));
			pager.compute();
			result.setList(getSession().createCriteria(UserTemp.class)
					.add(Restrictions.like("userCode", condtions))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#getUserTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param userUuid
	 *            userUuid
	 * @return UserTemp
	 */
	public UserTemp getUserTempByUuid(String userUuid)
	{
		return (UserTemp) getSession().createCriteria(UserTemp.class)
				.add(Restrictions.eq("uuid", userUuid)).uniqueResult();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserTempDao#getUserTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	public List<UserTemp> getUserTempListByStatus(Long l)
	{
		return getSession().createCriteria(UserTemp.class).add(Restrictions.eq("modifyStatus", l))
				.add(Restrictions.isNotNull("modifyType")).list();
	}

}
