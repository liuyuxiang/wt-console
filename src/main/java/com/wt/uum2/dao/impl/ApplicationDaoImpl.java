package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.ApplicationDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ApplicationDaoImpl extends BaseDaoSupport<Application> implements ApplicationDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getAllApplications(int, int)
	 */
	/**
	 * 方法说明：getAllApplications
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<Application> getAllApplications(int page, int pagesize)
	{

		UserPageResult<Application> result = new UserPageResult<Application>();

		if (page != -1) {

			Pager pager = new Pager();
			pager.setCurrentPage(page);
			pager.setPageSize(pagesize);
			Long count = (Long) getSession().createCriteria(Application.class)
					.setProjection(Projections.rowCount()).uniqueResult();
			pager.setQuantityOfData(count);
			pager.compute();
			result.setPager(pager);

			result.setList(getSession().createCriteria(Application.class)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		} else {
			result.setList(getSession().createCriteria(Application.class)
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getApplicationByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getApplicationByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Application
	 */
	public Application getApplicationByUuid(String uuid)
	{
		if (uuid == null) {
			return null;
		}
		return (Application) getSession().createCriteria(Application.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getApplicationsUnderGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getApplicationsUnderGroup
	 * 
	 * @param group
	 *            group
	 * @return Application
	 */
	public Application getApplicationsUnderGroup(Group group)
	{
		return (Application) getSession().createCriteria(Application.class)
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getApplicationsManagedUnderGroup(java.util.List)
	 */
	/**
	 * 方法说明：getApplicationsManagedUnderGroup
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Application> getApplicationsManagedUnderGroup(List<Group> group)
	{
		List<String> uuids = new ArrayList<String>();
		if (group != null) {
			for (int i = 0; i < group.size(); i++) {
				uuids.add(group.get(i).getUuid());
			}
		}
		return getSession().createCriteria(Application.class).createCriteria("adminGroups")
				.add(Restrictions.in("uuid", uuids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getUsersUnderApplication(java.lang.Integer, java.lang.Integer, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<UserApplication> getUsersUnderApplication(Integer page, Integer pagesize,
			Application application)
	{
		UserPageResult<UserApplication> result = new UserPageResult<UserApplication>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData(((Long) getSession()
				.createQuery(
						"select count(user.uuid) from User as user,UserApplication as ua where ua.useruuid=user.uuid and ua.applicationuuid= :applicationuuid")
				.setString("applicationuuid", application.getUuid()).uniqueResult()).intValue());
		pager.compute();
		result.setPager(pager);

		String hql = "select ua from User as u,UserApplication as ua where ua.useruuid=u.uuid and ua.applicationuuid= :applicationuuid";

		Query q = getSession().createQuery(hql).setString("applicationuuid", application.getUuid());
		q.setFirstResult(pager.getDataStart());
		q.setMaxResults(pager.getPageSize());
		List list = q.list();
		result.setList(list);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getApplicationByAppId(java.lang.String)
	 */
	/**
	 * 方法说明：getApplicationByAppId
	 * 
	 * @param appId
	 *            appId
	 * @return Application
	 */
	public Application getApplicationByAppId(String appId)
	{
		return (Application) getSession().createCriteria(Application.class)
				.add(Restrictions.eq("code", appId)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#searchUserByApplication(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：searchUserByApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	public UserPageResult<Object> searchUserByApplication(Integer page, Integer pagesize,
			Condition condition, Application app)
	{
		UserPageResult<Object> result = new UserPageResult<Object>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select u.uuid useruuid,u.id,u.name,d.path,ua.uuid uauuid,ua.mappendUserid,ua.mappendpassword,ua.loginable from U2_User u left join U2_UserApplication ua on u.uuid=ua.useruuid and ua.applicationuuid=:applicationuuid,u2_department d where u.primarydepartmentuuid=d.uuid ");

		Criteria count = getSession().createCriteria(User.class).add(
				Restrictions.eq("status", ResourceStatus.NORMAL.intValue()));

		// 如果姓名判断
		if (StringUtils.isNotBlank(condition.getUserName())) {
			sb.append("and u.name like :name");

			pager.setQuantityOfData((Long) count
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			List list = getSession().createSQLQuery(sb.toString())
					.setString("applicationuuid", app.getUuid())
					.setString("name", "%" + condition.getUserName() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list();
			result.setList(list);

			result.setPager(pager);

			return result;
		}

		// 如果ID判断
		if (StringUtils.isNotBlank(condition.getUserId())) {
			sb.append("and u.id like :id");

			pager.setQuantityOfData((Long) count
					.add(Restrictions.like("id", "%" + condition.getUserId() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			List list = getSession().createSQLQuery(sb.toString())
					.setString("applicationuuid", app.getUuid())
					.setString("id", "%" + condition.getUserId() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list();
			result.setList(list);

			result.setPager(pager);

			return result;
		}

		pager.setQuantityOfData((Long) count
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		List list = getSession().createSQLQuery(sb.toString())
				.setString("applicationuuid", app.getUuid()).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize()).list();

		result.setList(list);

		result.setPager(pager);

		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#searchUserUnderApplication(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：searchUserUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	public UserPageResult<UserApplication> searchUserUnderApplication(Integer page,
			Integer pagesize, Condition condition, Application app)
	{
		UserPageResult<UserApplication> result = new UserPageResult<UserApplication>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		StringBuilder countHql = new StringBuilder();

		countHql.append("select count(ua.uuid) from UserApplication ua where ua.user.status=:status and ua.applicationuuid=:applicationuuid ");

		StringBuilder listHql = new StringBuilder();

		listHql.append("from UserApplication ua where ua.user.status=:status and ua.applicationuuid=:applicationuuid ");

		// 如果姓名判断
		if (StringUtils.isNotBlank(condition.getUserName())) {
			countHql.append("and ua.user.name like :name");
			listHql.append("and ua.user.name like :name");

			Query list = getSession().createQuery(listHql.toString());
			list.setInteger("status", ResourceStatus.NORMAL.intValue());
			list.setString("applicationuuid", app.getUuid());
			list.setString("name", "%" + condition.getUserName() + "%");

			Query count = getSession().createQuery(countHql.toString());
			count.setInteger("status", ResourceStatus.NORMAL.intValue());
			count.setString("applicationuuid", app.getUuid());
			count.setString("name", "%" + condition.getUserName() + "%");

			pager.setQuantityOfData((Long) count.uniqueResult());

			pager.compute();

			result.setList(list.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);

			return result;
		}

		// 如果ID判断
		if (StringUtils.isNotBlank(condition.getUserId())) {
			countHql.append("and ua.user.id like :id");
			listHql.append("and ua.user.id like :id");

			Query list = getSession().createQuery(listHql.toString());
			list.setInteger("status", ResourceStatus.NORMAL.intValue());
			list.setString("applicationuuid", app.getUuid());
			list.setString("id", "%" + condition.getUserId() + "%");

			Query count = getSession().createQuery(countHql.toString());
			count.setInteger("status", ResourceStatus.NORMAL.intValue());
			count.setString("applicationuuid", app.getUuid());
			count.setString("id", "%" + condition.getUserId() + "%");

			pager.setQuantityOfData((Long) count.uniqueResult());

			pager.compute();

			result.setList(list.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);

			return result;
		}

		Query list = getSession().createQuery(listHql.toString());
		list.setInteger("status", ResourceStatus.NORMAL.intValue());
		list.setString("applicationuuid", app.getUuid());

		Query count = getSession().createQuery(countHql.toString());
		count.setInteger("status", ResourceStatus.NORMAL.intValue());
		count.setString("applicationuuid", app.getUuid());

		pager.setQuantityOfData((Long) count.uniqueResult());

		pager.compute();

		result.setList(list.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);

		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#searchUserNotUnderApplication(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：searchUserNotUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUserNotUnderApplication(Integer page, Integer pagesize,
			Condition condition, Application application)
	{
		StringBuilder hqlC = new StringBuilder();
		StringBuilder hqlL = new StringBuilder();
		hqlC.append("select count(u.uuid) from User u where not exists (select ua.uuid from UserApplication ua where ua.useruuid = u.uuid and ua.applicationuuid = :applicationuuid) and u.status = :status");
		hqlL.append("from User u where not exists (select ua.uuid from UserApplication ua where ua.useruuid = u.uuid and ua.applicationuuid = :applicationuuid) and u.status = :status");

		UserPageResult<User> result = new UserPageResult<User>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		// 如果姓名判断
		if (StringUtils.isNotBlank(condition.getUserName())) {
			hqlC.append(" and name like :name");
			hqlL.append(" and name like :name");

			pager.setQuantityOfData(Integer.valueOf(getSession().createQuery(hqlC.toString())
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("applicationuuid", application.getUuid())
					.setString("name", "%" + condition.getUserName() + "%").list().get(0)
					.toString()));
			pager.compute();
			result.setPager(pager);

			result.setList(getSession().createQuery(hqlL.toString())
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("applicationuuid", application.getUuid())
					.setString("name", "%" + condition.getUserName() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
			return result;
		}

		// 如果ID判断
		if (StringUtils.isNotBlank(condition.getUserId())) {
			hqlC.append(" and id like :id");
			hqlL.append(" and id like :id");

			pager.setQuantityOfData(Integer.valueOf(getSession().createQuery(hqlC.toString())
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("applicationuuid", application.getUuid())
					.setString("id", "%" + condition.getUserId() + "%").list().get(0).toString()));
			pager.compute();
			result.setPager(pager);

			result.setList(getSession().createQuery(hqlL.toString())
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("applicationuuid", application.getUuid())
					.setString("id", "%" + condition.getUserId() + "%")
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
			return result;
		}

		pager.setQuantityOfData(Integer.valueOf(getSession().createQuery(hqlC.toString())
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("applicationuuid", application.getUuid()).list().get(0).toString()));
		pager.compute();
		result.setPager(pager);

		result.setList(getSession().createQuery(hqlL.toString())
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("applicationuuid", application.getUuid())
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ApplicationDao#getUsersNotUnderApplication(java.lang.Integer, java.lang.Integer, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getUsersNotUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<User> getUsersNotUnderApplication(Integer page, Integer pagesize,
			Application application)
	{
		String hqlC = "select count(u.uuid) from User u where not exists (select ua.useruuid from UserApplication ua where ua.useruuid = u.uuid and ua.applicationuuid = :applicationuuid) and u.status = :status";
		String hqlL = "from User u where not exists (select ua.useruuid from UserApplication ua where ua.useruuid = u.uuid and ua.applicationuuid = :applicationuuid) and u.status = :status";
		UserPageResult<User> result = new UserPageResult<User>();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData(Integer.valueOf(getSession().createQuery(hqlC)
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("applicationuuid", application.getUuid()).list().get(0).toString()));
		pager.compute();
		result.setPager(pager);

		result.setList(getSession().createQuery(hqlL)
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("applicationuuid", application.getUuid())
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}
}