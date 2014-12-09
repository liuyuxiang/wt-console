package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceSyn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.UserDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-9
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserDaoImpl extends BaseDaoSupport<User> implements UserDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#readById(java.lang.String)
	 */
	/**
	 * 方法说明：readById
	 * 
	 * @param id
	 *            id
	 * @return User
	 */
	@Override
	public User read(String uuid) {
		
		Criteria c = getSession().createCriteria(User.class)
				.setCacheable(true)
				.add(Restrictions.idEq(uuid));
		return (User)c.uniqueResult();
	}

	public User readById(String id)
	{
		Criteria c = getSession().createCriteria(User.class);
		c.setCacheable(true);
		c.add(Restrictions.eq("id", id).ignoreCase());
		return (User) c.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#readUsersByDepartmentUUID(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：readUsersByDepartmentUUID
	 * 
	 * @param departmentUUID
	 *            departmentUUID
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<User> readUsersByDepartmentUUID(String departmentUUID, Integer start, Integer size)
	{
		return getSession()
				.createQuery(
						"select distinct(user) from User as user,Department department where department in elements(user.departments) and department.uuid=:departmentUUID and user.status=:userStatus order by user.order,user.id")
				.setString("departmentUUID", departmentUUID)
				.setInteger("userStatus", ResourceStatus.NORMAL.intValue()).list();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers()
	{
		return getSession().createCriteria(User.class).list();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllNormalUsers()
	{
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue())).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersByGroupUUID(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getUsersByGroupUUID
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersByGroupUUID(String groupUUID, Integer start, Integer size)
	{
		return getSession()
				.createQuery(
						"select distinct(user) from User as user,Group as group where group in elements(user.groups) and group.uuid=:groupUUID and user.status=:userStatus order by user.order,user.id")
				.setString("groupUUID", groupUUID)
				.setInteger("userStatus", ResourceStatus.NORMAL.intValue()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#userValidate(java.lang.String)
	 */
	/**
	 * 方法说明：userValidate
	 * 
	 * @param name
	 *            name
	 * @return boolean
	 */
	public boolean userValidate(String name)
	{
		return ((Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.add(Restrictions.eq("name", name)).setProjection(Projections.rowCount())
				.uniqueResult()) > 0;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getAdmUserByGroupUuid(java.util.List)
	 */
	/**
	 * 方法说明：getAdmUserByGroupUuid
	 * 
	 * @param uuids
	 *            uuids
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<User> getAdmUserByGroupUuid(List<String> uuids)
	{
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.addOrder(Order.asc("name")).createCriteria("admUserGroups")
				.add(Restrictions.in("uuid", uuids)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserByKey(java.lang.String)
	 */
	/**
	 * 方法说明：searchUserByKey
	 * 
	 * @param key
	 *            key
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	@Deprecated
	public List<User> searchUserByKey(String key)
	{
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.add(Restrictions.eq("name", key)).addOrder(Order.asc("name")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserByCondition(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchUserByCondition
	 * 
	 * @param condition
	 *            condition
	 * @return List
	 */
	public List<User> searchUserByCondition(Condition condition)
	{
		Criteria list = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		if (StringUtils.isNotBlank(condition.getUserName())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.name) like ? escape'/'",
					vagueSqlValueHandle(condition.getUserName()), Hibernate.STRING);
			list.add(c);
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
					vagueSqlValueHandle(condition.getUserId()), Hibernate.STRING);
			list.add(c);
		}
		return list.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserByCondition(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchUserByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchUserByCondition(int page, int pagesize, Condition condition)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition.isEmpty()) {
			pager.setQuantityOfData(0);
			pager.compute();
			result.setList(ListUtils.EMPTY_LIST);
			result.setPager(pager);
			return result;
		}

		Criteria list = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Criteria num = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setProjection(Projections.rowCount());

		if (StringUtils.isNotBlank(condition.getUserName())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.name) like ? escape'/'",
					vagueSqlValueHandle(condition.getUserName()), Hibernate.STRING);
			list.add(c);
			num.add(c);
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
					vagueSqlValueHandle(condition.getUserId()), Hibernate.STRING);
			list.add(c);
			num.add(c);
		}

		pager.setQuantityOfData((Long) num.uniqueResult());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}

		pager.compute();

		result.setList(list.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;

	}

	/**
	 * 方法说明：getUserMembersByDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getUserMembersByDepartment(int page, int pagesize, Department department)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		Long size = (Long) getSession().createCriteria(User.class)
				.setProjection(Projections.countDistinct("uuid"))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("departments").add(Restrictions.idEq(department.getUuid()))
				.uniqueResult();

		pager.setQuantityOfData(size.intValue());

		pager.compute();

		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.addOrder(Order.asc("order")).addOrder(Order.asc("name"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.createCriteria("departments").add(Restrictions.idEq(department.getUuid()));

		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;
	}

	/**
	 * 方法说明：getUserMembersByDepartments
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getUserMembersByDepartments(int page, int pagesize,
			List<Department> department)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		List<String> uuid = new ArrayList<String>();
		for (int i = 0; i < department.size(); i++) {
			uuid.add(department.get(i).getUuid());
		}
		Long size = (Long) getSession().createCriteria(User.class)
				.setProjection(Projections.countDistinct("uuid"))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("departments").add(Restrictions.in("uuid", uuid)).uniqueResult();

		pager.setQuantityOfData(size.intValue());

		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("departments").add(Restrictions.in("uuid", uuid))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		// pager.setQuantityOfData(c.list()
		// .size());

		pager.compute();

		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;
	}

	/**
	 * 方法说明：getManagerUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getUserMembersUnderDepartment(int page, int pagesize,
			Department department)
	{
		return getUsersInSubDepartmentByStatus(page, pagesize, department, ResourceStatus.NORMAL);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getManagerUnderApplication(int, int, com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getManagerUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult getManagerUnderApplication(int page, int pagesize, Application application)
	{
		Set<Group> appGroups = application.getAdminGroups();
		List<String> uuids = new ArrayList<String>();

		if (appGroups == null || appGroups.size() == 0) {
			return null;
		} else {
			java.util.Iterator<Group> iterator = appGroups.iterator();
			while (iterator.hasNext()) {
				uuids.add(iterator.next().getUuid());
			}
		}
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("groups").add(Restrictions.in("uuid", uuids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pager.setQuantityOfData((Long) getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("groups").add(Restrictions.in("uuid", uuids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUsersUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderGroup(int page, int pagesize, Group group)
	{

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		Long size = (Long) getSession().createCriteria(User.class)
				.setProjection(Projections.countDistinct("uuid"))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("groups").add(Restrictions.idEq(group.getUuid())).uniqueResult();

		pager.setQuantityOfData(size.intValue());

		pager.compute();

		result.setPager(pager);

		result.setList(getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		return result;
	}

	/**
	 * 方法说明：getUsersNotUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersNotUnderGroup(int page, int pagesize, Group group)
	{

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		String hql = "from User u where u.status='1' and not exists ( select ug.user from UserGroup ug where ug.user=u and ug.groupUUID in('"
				+ group.getUuid() + "'))";

		Long size = (Long) getSession().createQuery("select count(u.uuid) " + hql)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult();

		pager.setQuantityOfData(size.intValue());

		pager.compute();

		result.setPager(pager);

		result.setList(getSession().createQuery(hql)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersUnderGroupOrderByDept(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUsersUnderGroupOrderByDept
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderGroupOrderByDept(int page, int pagesize, Group group)
	{

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		Long size = (Long) getSession().createCriteria(User.class)
				.setProjection(Projections.countDistinct("uuid"))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("groups").add(Restrictions.idEq(group.getUuid())).uniqueResult();

		pager.setQuantityOfData(size.intValue());

		pager.compute();

		result.setPager(pager);

		result.setList(getSession()
				.createQuery(
						"select user from User as user,Department as department,Group as group "
								+ "where department in elements(user.departments) and group in elements(user.groups) and user.status="
								+ ResourceStatus.NORMAL.intValue() + " and group.uuid='"
								+ group.getUuid() + "' order by department.path")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserUnderGroup(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：searchUserUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUserUnderGroup(int page, int pagesize, Condition condition,
			Group group)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#addUserUnderGroup(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：addUserUnderGroup
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	public int addUserUnderGroup(Group group, User user)
	{
		user.getGroups().add(group);
		getSession().update(user);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#removeUserUnderGroup(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：removeUserUnderGroup
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	public int removeUserUnderGroup(Group group, User user)
	{
		user.getGroups().remove(group);
		getSession().update(user);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserUnderApplicationGroup(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：searchUserUnderApplicationGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUserUnderApplicationGroup(int page, int pagesize,
			Condition condition, Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserManagedUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUserManagedUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUserManagedUnderGroup(int page, int pagesize, Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getLogicDeleteUsersUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getLogicDeleteUsersUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderGroup(int page, int pagesize, Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchLogicDeleteUsersUnderGroup(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：searchLogicDeleteUsersUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsersUnderGroup(int page, int pagesize,
			Condition condition, Group group)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.idEq(group.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());

		}
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersManagedByAdmGroups(int, int, com.wt.uum2.constants.Condition, java.util.List, boolean)
	 */
	/**
	 * 方法说明：searchUsersManagedByAdmGroups
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param admGroups
	 *            admGroups
	 * @param isInSuperGroup
	 *            isInSuperGroup
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersManagedByAdmGroups(int page, int pagesize,
			Condition condition, List<Group> admGroups, boolean isInSuperGroup)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition == null
				|| (condition.getUserId().equals("") && condition.getUserName().equals(""))) {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());
			result.setPager(pager);
			return result;
		}

		List<String> uuids = new ArrayList<String>();
		if (admGroups != null) {
			for (int i = 0; i < admGroups.size(); i++) {
				uuids.add(admGroups.get(i).getUuid());
			}
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			String from = null;
			if (isInSuperGroup) {
				from = " from User as res " + "where res.status=:status and res.name like :name";
			} else {
				from = " from User as res,ResourceAdminGroup as rag "
						+ "where rag.resourceUUID=res.uuid and res.status=:status and  res.name like :name and rag.groupUUID in (:uuids)";
			}

			String countHql = "select count(distinct res.uuid) " + from;

			String listHql = "select distinct res " + from;

			Query countQuery = getSession().createQuery(countHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("name", "%" + condition.getUserName() + "%");

			Query listQuery = getSession().createQuery(listHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("name", "%" + condition.getUserName() + "%");

			if (!isInSuperGroup) {
				countQuery.setParameterList("uuids", uuids);
				listQuery.setParameterList("uuids", uuids);
			}

			pager.setQuantityOfData((Long) countQuery.uniqueResult());

			pager.compute();

			listQuery.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());

			result.setList(listQuery.list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			String from = null;
			if (isInSuperGroup) {
				from = " from User as res "
						+ "where res.status=:status and lower(res.id) like :code";
			} else {
				from = " from User as res,ResourceAdminGroup as rag "
						+ "where rag.resourceUUID=res.uuid and res.status=:status and lower(res.id) like :code and rag.groupUUID in (:uuids)";
			}

			String countHql = "select count(distinct res.uuid) " + from;

			String listHql = "select distinct res " + from;

			Query countQuery = getSession().createQuery(countHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("code", "%" + condition.getUserId() + "%");

			Query listQuery = getSession().createQuery(listHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("code", "%" + condition.getUserId() + "%");

			if (!isInSuperGroup) {
				countQuery.setParameterList("uuids", uuids);
				listQuery.setParameterList("uuids", uuids);
			}

			pager.setQuantityOfData((Long) countQuery.uniqueResult());

			pager.compute();

			listQuery.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());

			result.setList(listQuery.list());

		}
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getLogicDeleteUsersUnderDepartment(int, int, java.util.List)
	 */
	/**
	 * 方法说明：getLogicDeleteUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param departments
	 *            departments
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderDepartment(int page, int pagesize,
			List<Department> departments)
	{
		List<String> uuids = new ArrayList<String>();
		if (departments != null) {
			for (int i = 0; i < departments.size(); i++) {
				uuids.add(departments.get(i).getUuid());
			}
		}
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.createCriteria("departments").add(Restrictions.in("uuid", uuids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.createCriteria("departments").add(Restrictions.in("uuid", uuids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getLogicDeleteUsersUnderDepartment(int, int, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getLogicDeleteUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Department department)
	{
		return getUsersInSubDepartmentByStatus(page, pagesize, department,
				ResourceStatus.DELETE_LOGIC);
	}

	/**
	 * 方法说明：getUsersInSubDepartmentByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult<User> getUsersInSubDepartmentByStatus(int page, int pagesize,
			Department department, ResourceStatus rs)
	{
		final String count = "select count(distinct r.uuid) from Resource r,UserDepartment ud,DepartmentPath dp where r.status=:status and dp.elder=:dept and dp.juniorUUID=ud.departmentUUID and ud.userUUID=r.uuid";
		final String hql = "select u from User u,UserDepartment ud,DepartmentPath dp where u.status=:status and dp.elder=:dept and dp.juniorUUID=ud.departmentUUID and ud.userUUID=u.uuid";

		Query num = getSession().createQuery(count)
				.setString("status", String.valueOf(rs.intValue())).setEntity("dept", department);

		Query res = getSession().createQuery(hql)
				.setString("status", String.valueOf(rs.intValue())).setEntity("dept", department)
				.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setQuantityOfData((Long) num.uniqueResult());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}
		pager.compute();

		result.setPager(pager);
		res.setFirstResult(pager.getDataStart());
		res.setMaxResults(pager.getPageSize());
		result.setList(res.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getMoveUsersUnderDepartment(int, int, java.util.List)
	 */
	/**
	 * 方法说明：getMoveUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param departments
	 *            departments
	 * @return UserPageResult
	 */
	public UserPageResult getMoveUsersUnderDepartment(int page, int pagesize,
			List<Department> departments)
	{
		List<String> uuids = new ArrayList<String>();
		if (departments != null) {
			for (int i = 0; i < departments.size(); i++) {
				uuids.add(departments.get(i).getUuid());
			}
		}
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class).add(
				Restrictions.in("primaryDepartmentUUID", uuids));
		Criteria c1 = getSession().createCriteria(User.class).add(
				Restrictions.in("primaryDepartmentUUID", uuids));
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchLogicDeleteUsersUnderDepartment(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：searchLogicDeleteUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Condition condition, Department department)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("departments").add(Restrictions.idEq(department.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("departments").add(Restrictions.idEq(department.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("departments")
					.add(Restrictions.idEq(department.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("departments")
					.add(Restrictions.idEq(department.getUuid()))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());

		}
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, java.util.List)
	 */
	/**
	 * 方法说明：getNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, List<Group> appGroups)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		Query q = getSession()
				.createQuery(
						"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
								+ ResourceStatus.NORMAL.intValue()
								+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appGroup
								+ "' and (attribute.value is null or attribute.value ='"
								+ ResourceStatus.UNDEAL.intValue() + "')");
		Query q1 = getSession()
				.createQuery(
						"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
								+ ResourceStatus.NORMAL.intValue()
								+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appGroup
								+ "' and (attribute.value is null or attribute.value ='"
								+ ResourceStatus.UNDEAL.intValue() + "')");
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.uniqueResult().toString()));
		pager.compute();
		result.setPager(pager);
		q.setFirstResult(pager.getDataStart());
		q.setMaxResults(pager.getPageSize());
		result.setList(q.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Department dept)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());

		String list = "select u,dp.junior from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		list += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup"
				+ " and (attribute.value is null or attribute.value =:value)";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		count += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup"
				+ " and (attribute.value is null or attribute.value =:value)";

		Query q = getSession().createQuery(list);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setEntity("dept", dept);
		q.setString("appGroup", appGroup);
		q.setString("value", String.valueOf(ResourceStatus.UNDEAL.intValue()));

		Query q1 = getSession().createQuery(count);
		q1.setInteger("status", ResourceStatus.NORMAL.intValue());
		q1.setEntity("dept", dept);
		q1.setString("appGroup", appGroup);
		q1.setString("value", String.valueOf(ResourceStatus.UNDEAL.intValue()));

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setQuantityOfData((Long) q1.uniqueResult());
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}
		pager.compute();
		result.setPager(pager);
		q.setFirstResult(pager.getDataStart());
		q.setMaxResults(pager.getPageSize());
		result.setList(q.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, java.util.List)
	 */
	/**
	 * 方法说明：getFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, List<Group> appGroups)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		Query q = getSession()
				.createQuery(
						"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
								+ ResourceStatus.NORMAL.intValue()
								+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appGroup
								+ "' and attribute.value ='"
								+ ResourceStatus.FILTER.intValue() + "'");
		Query q1 = getSession()
				.createQuery(
						"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
								+ ResourceStatus.NORMAL.intValue()
								+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appGroup
								+ "' and attribute.value ='"
								+ ResourceStatus.FILTER.intValue() + "'");
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.uniqueResult().toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Department dept)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());

		String list = "select u,dp.junior from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		list += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup" + " and attribute.value =:value";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		count += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup" + " and attribute.value =:value";

		Query q = getSession().createQuery(list);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setEntity("dept", dept);
		q.setString("appGroup", appGroup);
		q.setString("value", String.valueOf(ResourceStatus.FILTER.intValue()));

		Query q1 = getSession().createQuery(count);
		q1.setInteger("status", ResourceStatus.NORMAL.intValue());
		q1.setEntity("dept", dept);
		q1.setString("appGroup", appGroup);
		q1.setString("value", String.valueOf(ResourceStatus.FILTER.intValue()));

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setQuantityOfData(Integer.valueOf(q1.uniqueResult().toString()));
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition, java.util.List)
	 */
	/**
	 * 方法说明：searchNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	@SuppressWarnings("rawtypes")
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, List<Group> appGroups)
	{
		return searchNormalUsersUnderApplication(page, pagesize, group, user, condition);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：searchNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	@SuppressWarnings("rawtypes")
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, Department dept)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();

		String list = "select u,dp.junior from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		list += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup"
				+ " and (attribute.value =:value or attribute.value is null)";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		count += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup"
				+ " and (attribute.value =:value or attribute.value is null)";

		if (StringUtils.isNotBlank(condition.getUserName())) {
			list += " and u.name like :condition";
			count += " and u.name like :condition";
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			list += " and lower(u.id) like :condition";
			count += " and lower(u.id) like :condition";
		}

		Query q = getSession().createQuery(list);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setEntity("dept", dept);
		q.setString("appGroup", appGroup);
		q.setString("value", String.valueOf(ResourceStatus.UNDEAL.intValue()));

		Query q1 = getSession().createQuery(count);
		q1.setInteger("status", ResourceStatus.NORMAL.intValue());
		q1.setEntity("dept", dept);
		q1.setString("appGroup", appGroup);
		q1.setString("value", String.valueOf(ResourceStatus.UNDEAL.intValue()));

		if (StringUtils.isNotBlank(condition.getUserName())) {
			q.setString("condition", "%" + condition.getUserName() + "%");
			q1.setString("condition", "%" + condition.getUserName() + "%");
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			q.setString("condition", "%" + condition.getUserId().toLowerCase() + "%");
			q1.setString("condition", "%" + condition.getUserId().toLowerCase() + "%");
		}

		pager.setCurrentPage(page);
		pager.setQuantityOfData((Long) q1.uniqueResult());
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition, java.util.List)
	 */
	/**
	 * 方法说明：searchFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, List<Group> appGroups)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		Query q = null;
		Query q1 = null;
		if (!condition.getUserName().equalsIgnoreCase("")) {
			q = getSession()
					.createQuery(
							"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and attribute.value ='"
									+ ResourceStatus.FILTER.intValue()
									+ "' and user.name like '%"
									+ condition.getUserName().trim() + "%'");
			q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and attribute.value ='"
									+ ResourceStatus.FILTER.intValue()
									+ "' and user.name like '%"
									+ condition.getUserName().trim() + "%'");
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			q = getSession()
					.createQuery(
							"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and attribute.value ='"
									+ ResourceStatus.FILTER.intValue()
									+ "' and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'");
			q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and attribute.value ='"
									+ ResourceStatus.FILTER.intValue()
									+ "' and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'");
		} else {
			return getFilterUsersUnderApplication(page, pagesize, group, user, appGroups);
		}
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：searchFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, Department dept)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();

		String list = "select u,dp.junior from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		list += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup" + " and attribute.value =:value";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";
		count += " and attribute.ownerResource=u" + " and attribute.typeUUID=attrType.uuid"
				+ " and attrType.id =:appGroup" + " and attribute.value =:value";

		if (StringUtils.isNotBlank(condition.getUserName())) {
			list += " and u.name like :condition";
			count += " and u.name like :condition";
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			list += " and lower(u.id) like :condition";
			count += " and lower(u.id) like :condition";
		}

		Query q = getSession().createQuery(list);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setEntity("dept", dept);
		q.setString("appGroup", appGroup);
		q.setString("value", String.valueOf(ResourceStatus.FILTER.intValue()));

		Query q1 = getSession().createQuery(count);
		q1.setInteger("status", ResourceStatus.NORMAL.intValue());
		q1.setEntity("dept", dept);
		q1.setString("appGroup", appGroup);
		q1.setString("value", String.valueOf(ResourceStatus.FILTER.intValue()));

		if (StringUtils.isNotBlank(condition.getUserName())) {
			q.setString("condition", "%" + condition.getUserName() + "%");
			q1.setString("condition", "%" + condition.getUserName() + "%");
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			q.setString("condition", "%" + condition.getUserId().toLowerCase() + "%");
			q1.setString("condition", "%" + condition.getUserId().toLowerCase() + "%");
		}

		pager.setCurrentPage(page);
		pager.setQuantityOfData((Long) q1.uniqueResult());
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getCreatedUsersManagedUnderGroups(int, int, java.util.List)
	 */
	/**
	 * 方法说明：getCreatedUsersManagedUnderGroups
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param groups
	 *            groups
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedUsersManagedUnderGroups(int page, int pagesize,
			List<Group> groups)
	{
		UserPageResult result = new UserPageResult();
		List<String> uuidlist = new ArrayList<String>();
		if (groups != null && groups.size() > 0) {
			for (int i = 0; i < groups.size(); i++) {
				uuidlist.add("'" + groups.get(i).getUuid() + "'");
			}
		}
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		List<User> allUsers = getSession()
				.createQuery(
						"select user from User as user,UserAuthor as userAuthor where userAuthor in "
								+ "elements(user.userAuthors) and user.currentAuthorLevel is not null and user.currentAuthorLevel>0 and "
								+ "user.currentAuthorLevel=userAuthor.levels and userAuthor.group.uuid in ("
								+ StringUtils.join(uuidlist, ",") + ") and (user.status="
								+ ResourceStatus.CREATED.intValue() + " or user.status="
								+ ResourceStatus.MOVEDEPT.intValue() + " or user.status="
								+ ResourceStatus.CHANGEPOSITION.intValue() + ")")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
		allUsers.addAll(getSession()
				.createQuery(
						"select user from User as user,Group as group where group in elements(user.groups) and group.uuid in ("
								+ StringUtils.join(uuidlist, ",")
								+ ") and (user.status="
								+ ResourceStatus.CREATED.intValue()
								+ " or user.status="
								+ ResourceStatus.MOVEDEPT.intValue()
								+ " or user.status="
								+ ResourceStatus.CHANGEPOSITION.intValue() + ")")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		List<String> ids = new ArrayList<String>();
		for (int i = 0; i < allUsers.size(); i++) {
			ids.add(allUsers.get(i).getId());
		}
		if (allUsers.size() == 0) {
			ids.add("null");
		}
		pager.setQuantityOfData((Long) getSession().createCriteria(User.class)
				.add(Restrictions.in("id", ids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(User.class).add(Restrictions.in("id", ids))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersMembersByLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User, com.wt.uum2.domain.Group, java.util.List)
	 */
	/**
	 * 方法说明：searchUsersMembersByLoginUser
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param loginUser
	 *            loginUser
	 * @param appGroup
	 *            appGroup
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group appGroup, List<Group> appGroups)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(size);
		String appStatus = InitParameters.getAppStatusCode().replace("XXXX", appGroup.getCode());
		String hql = "select user from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
				+ ResourceStatus.NORMAL.intValue()
				+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "'";
		if (!condition.getUserName().equalsIgnoreCase("")) {
			hql += " and user.name like '%" + condition.getUserName() + "%'";
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			hql += " and lower(user.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
		} else {
			return null;
		}

		String hql2 = "select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
				+ ResourceStatus.NORMAL.intValue()
				+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "'";
		if (!condition.getUserName().equalsIgnoreCase("")) {
			hql2 += " and user.name like '%" + condition.getUserName() + "%'";
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			hql2 += " and lower(user.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
		} else {
			return null;
		}
		Query q = getSession().createQuery(hql);
		Query q2 = getSession().createQuery(hql2);
		pager.setQuantityOfData((Long) q2.uniqueResult());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}

		pager.compute();
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getReAuthorUserMembersByLoginUser(int, int, com.wt.uum2.domain.User, com.wt.uum2.domain.Group, java.util.List)
	 */
	/**
	 * 方法说明：getReAuthorUserMembersByLoginUser
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param loginUser
	 *            loginUser
	 * @param appGroup
	 *            appGroup
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getReAuthorUserMembersByLoginUser(int size, int pagesize, User loginUser,
			Group appGroup, List<Group> appGroups)
	{
		UserPageResult result = new UserPageResult();
		List list = new ArrayList();
		Pager pager = new Pager();
		pager.setCurrentPage(size);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		String appStatus = InitParameters.getAppStatusCode().replace("XXXX", appGroup.getCode());

		Query q = getSession()
				.createQuery(
						"select user from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
								+ ResourceStatus.NORMAL.intValue()
								+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appStatus
								+ "' and attribute.value ='"
								+ ResourceStatus.AUTHORIZE.intValue() + "'");
		Query qd = getSession().createQuery(
				"select user from User as user,UserGroup as ug where ug.userUUID=user.uuid and ug.groupUUID='"
						+ appGroup.getUuid() + "')");
		list.addAll(q.list());
		list.removeAll(qd.list());
		Query q1 = getSession()
				.createQuery(
						"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
								+ ResourceStatus.NORMAL.intValue()
								+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
								+ appStatus
								+ "' and attribute.value ='"
								+ ResourceStatus.AUTHORIZE.intValue()
								+ "' and not exists (select user.uuid from UserGroup as ug where ug.userUUID=user.uuid and ug.groupUUID='"
								+ appGroup.getUuid() + "')");
		pager.setQuantityOfData((Long) q1.uniqueResult());
		pager.compute();
		int first = (pager.getCurrentPage() - 1) * pager.getPageSize();
		int max = pager.getCurrentPage() * pager.getPageSize();
		if (max > pager.getQuantityOfData()) {
			max = pager.getQuantityOfData();
		}
		result.setList(list.subList(first, max));
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchReAuthorUserMembersByLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User, com.wt.uum2.domain.Group, java.util.List)
	 */
	/**
	 * 方法说明：searchReAuthorUserMembersByLoginUser
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param loginUser
	 *            loginUser
	 * @param appGroup
	 *            appGroup
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchReAuthorUserMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group appGroup, List<Group> appGroups)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(size);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		String appStatus = InitParameters.getAppStatusCode().replace("XXXX", appGroup.getCode());
		String hql = "select user from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
				+ ResourceStatus.NORMAL.intValue()
				+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "' and not exists (select user.uuid from UserGroup as ug where ug.userUUID=user.uuid and ug.groupUUID='"
				+ appGroup.getUuid() + "')";
		if (!condition.getUserName().equalsIgnoreCase("")) {
			hql += " and user.name like '%" + condition.getUserName() + "%'";
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			hql += " and lower(user.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
		}
		Query q = getSession().createQuery(hql);

		String hql1 = "select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status= "
				+ ResourceStatus.NORMAL.intValue()
				+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "' and not exists (select user.uuid from UserGroup as ug where ug.userUUID=user.uuid and ug.groupUUID='"
				+ appGroup.getUuid() + "')";
		if (!condition.getUserName().equalsIgnoreCase("")) {
			hql1 += " and user.name like '%" + condition.getUserName() + "%'";
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			hql1 += " and lower(user.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
		}
		Query q1 = getSession().createQuery(hql1);
		pager.setQuantityOfData((Long) q1.uniqueResult());
		pager.compute();
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersUnderGroupAndLoginUser(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, java.util.List)
	 */
	/**
	 * 方法说明：getUsersUnderGroupAndLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param userAppGroups
	 *            userAppGroups
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderGroupAndLoginUser(int page, int pagesize, Group group,
			User user, List<Group> userAppGroups)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		Criteria list = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		list.createCriteria("groups").add(Restrictions.idEq(group.getUuid()));

		Criteria num = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		num.createCriteria("groups").add(Restrictions.idEq(group.getUuid()));
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) num.setProjection(Projections.rowCount()).uniqueResult());
		pager.compute();
		result.setPager(pager);
		result.setList(list.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByConditionAndLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User, java.util.List)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndLoginUser(int page, int pagesize,
			Condition condition, User user, List<Group> appGroups)
	{
		UserPageResult result = new UserPageResult();
		Iterator<Group> groups = user.getGroups().iterator();
		List<String> uuids = new ArrayList<String>();
		uuids.add("null");
		while (groups.hasNext()) {
			Group userGroup = groups.next();
			if (!appGroups.contains(userGroup)) {
				uuids.add(userGroup.getUuid());
			}
		}
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.in("uuid", uuids))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.createCriteria("groups").add(Restrictions.in("uuid", uuids))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.in("uuid", uuids))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("groups")
					.add(Restrictions.in("uuid", uuids))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByConditionAndOrgCode(int, int, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndOrgCode
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param orgCode
	 *            orgCode
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + escapeSQLLike(condition.getUserName())
							+ "%")).createCriteria("primaryDepartment")
					.add(Restrictions.eq("orgCode", orgCode))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + escapeSQLLike(condition.getUserName())
							+ "%")).createCriteria("primaryDepartment")
					.add(Restrictions.eq("orgCode", orgCode))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("primaryDepartment")
					.add(Restrictions.eq("orgCode", orgCode))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING)).createCriteria("primaryDepartment")
					.add(Restrictions.eq("orgCode", orgCode))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

			result.setPager(pager);
			return result;
		} else {
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersUnderApplicationByOrg(int, int, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUsersUnderApplicationByOrg
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderApplicationByOrg(int page, int pagesize, Department dept,
			Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		String list = "select u from User u,UserDepartment ud,UserGroup ug,DepartmentPath dp where u.status=:status and u.uuid=ug.userUUID and u.uuid=ud.userUUID and ug.groupUUID=:groupUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";

		String count = "select count(u.uuid) from User u,UserDepartment ud,UserGroup ug,DepartmentPath dp where u.status=:status and u.uuid=ug.userUUID and u.uuid=ud.userUUID and ug.groupUUID=:groupUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept";

		Query l = getSession().createQuery(list);
		l.setInteger("status", ResourceStatus.NORMAL.intValue());
		l.setString("groupUUID", group.getUuid());
		l.setEntity("dept", dept);

		Query c = getSession().createQuery(count);
		c.setInteger("status", ResourceStatus.NORMAL.intValue());
		c.setString("groupUUID", group.getUuid());
		c.setEntity("dept", dept);

		pager.setQuantityOfData((Long) c.uniqueResult());

		pager.compute();

		result.setList(l.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByConditionAndUserCameFrom(int, int, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndUserCameFrom
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
	public UserPageResult searchUsersByConditionAndUserCameFrom(int page, int pagesize,
			Condition condition, String type)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Query q = getSession()
					.createQuery(
							"select user from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + " "
									+ "and user.name like '%" + condition.getUserName() + "%'")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			Query q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + " "
									+ "and user.name like '%" + condition.getUserName() + "%'")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) q1.uniqueResult());
			pager.compute();

			result.setList(q.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Query q = getSession()
					.createQuery(
							"select user from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + " "
									+ "and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'").setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Query q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + " "
									+ "and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'").setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) q1.uniqueResult());
			pager.compute();

			result.setList(q.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		} else {
			Query q = getSession()
					.createQuery(
							"select user from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + "").setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			Query q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,StringValue as av "
									+ "where attribute.ownerResource=user "
									+ "and attribute.type.id ='dataCameFrom' and av.attribute=attribute and "
									+ "av.value ='" + type + "' and user.status="
									+ ResourceStatus.NORMAL.intValue() + "").setResultTransformer(
							CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			pager.setQuantityOfData((Long) q1.uniqueResult());
			pager.compute();

			result.setList(q.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());
			result.setPager(pager);
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getLogicDeleteUsers(int, int)
	 */
	/**
	 * 方法说明：getLogicDeleteUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsers(int page, int pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchLogicDeleteUsers(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchLogicDeleteUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsers(int page, int pagesize, Condition condition)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))

					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))

					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			Criteria c = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			Criteria c1 = getSession()
					.createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.DELETE_LOGIC.intValue()))
					.add(Restrictions
							.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
									"%" + this.escapeSQLLike(condition.getUserId()) + "%",
									Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(c.setFirstResult(pager.getDataStart())
					.setMaxResults(pager.getPageSize()).list());

		} else {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());
		}
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getRelationUsers(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getRelationUsers
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<User> getRelationUsers(User user)
	{
		if (user.getPrimaryUserUUID() != null) {
			return getSession()
					.createQuery(
							"select user from User as user where (user.primaryUserUUID=:primaryUserUUID or user.uuid=:uuid) and user.status=1")
					.setString("primaryUserUUID", user.getUuid())
					.setString("uuid", user.getPrimaryUserUUID()).list();
		}
		return getSession()
				.createQuery(
						"select user from User as user where user.primaryUserUUID=:primaryUserUUID and user.status=1")
				.setString("primaryUserUUID", user.getUuid()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserResourceSyn(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserResourceSyn
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<ResourceSyn> getUserResourceSyn(User user)
	{
		List<ResourceSyn> returnList = new ArrayList<ResourceSyn>();
		List list = getSession()
				.createQuery(
						"select new list(at.id,av.value) from User as user,Attribute as attribute,StringValue as av,AttributeType as at,ResourceSync as rs "
								+ "where attribute.ownerResource=user "
								+ "and av.attribute=attribute and attribute.type=at and at.id=rs.propName "
								+ "and rs.propType!='b' and user.uuid='" + user.getUuid() + "'")
				.list();
		for (int i = 0; i < list.size(); i++) {
			ResourceSyn rs = new ResourceSyn();
			rs.setPropName(((List) list.get(i)).get(0).toString());
			if (((List) list.get(i)).get(1) == null) {
				rs.setPropValue("");
			} else {
				rs.setPropValue(((List) list.get(i)).get(1).toString());
			}
			returnList.add(rs);
		}
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersByName(java.lang.String)
	 */
	/**
	 * 方法说明：getUsersByName
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<User> getUsersByName(String name)
	{
		return getSession().createCriteria(User.class).add(Restrictions.eq("name", name)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user)
	{
		Query q = getSession()
				.createQuery(
						"select distinct user from User as user,Attribute as attribute,StringValue as av where attribute.ownerResource=user "
								+ " and attribute.type.id like '%"
								+ group.getCode()
								+ "%' and attribute.type.id like '%Status%' and av.attribute=attribute and av.value ='"
								+ ResourceStatus.FILTER.intValue() + "'").setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Query q1 = getSession()
				.createQuery(
						"select count(*) from User as user,Attribute as attribute,StringValue as av where attribute.ownerResource=user "
								+ " and attribute.type.id like '%"
								+ group.getCode()
								+ "%' and attribute.type.id like '%Status%' and av.attribute=attribute and av.value ='"
								+ ResourceStatus.FILTER.intValue() + "'").setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user)
	{
		Query q = getSession()
				.createQuery(
						"select distinct user from User as user,Attribute as attribute,StringValue as av where attribute.ownerResource=user "
								+ " and attribute.type.id like '%"
								+ group.getCode()
								+ "%' and attribute.type.id like '%Status%' and av.attribute=attribute and (av.value ='"
								+ ResourceStatus.UNDEAL.intValue() + "' or av.value is null )")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Query q1 = getSession()
				.createQuery(
						"select count(*) from User as user,Attribute as attribute,StringValue as av where attribute.ownerResource=user "
								+ " and attribute.type.id like '%"
								+ group.getCode()
								+ "%' and attribute.type.id like '%Status%' and av.attribute=attribute and (av.value ='"
								+ ResourceStatus.UNDEAL.intValue() + "' or av.value is null )")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchFilterUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition)
	{
		return searchFilterUsersUnderApplication(page, pagesize, group, user, condition,
				new ArrayList<Group>());
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchNormalUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition)
	{
		String appGroup = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		Query q = null;
		Query q1 = null;
		if (!condition.getUserName().equalsIgnoreCase("")) {
			q = getSession()
					.createQuery(
							"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and (attribute.value ='"
									+ ResourceStatus.UNDEAL.intValue()
									+ "' or attribute.value is null) and user.name like '%"
									+ condition.getUserName().trim() + "%'");
			q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and (attribute.value ='"
									+ ResourceStatus.UNDEAL.intValue()
									+ "' or attribute.value is null) and user.name like '%"
									+ condition.getUserName().trim() + "%'");
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			q = getSession()
					.createQuery(
							"select user,dept from User as user,Department as dept,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and dept.uuid=user.primaryDepartmentUUID and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and (attribute.value ='"
									+ ResourceStatus.UNDEAL.intValue()
									+ "' or attribute.value is null) and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'");
			q1 = getSession()
					.createQuery(
							"select count(user.uuid) from User as user,Attribute as attribute,AttributeType as attrType where user.status="
									+ ResourceStatus.NORMAL.intValue()
									+ " and attribute.ownerResource=user and attribute.typeUUID=attrType.uuid and attrType.id ='"
									+ appGroup
									+ "' and (attribute.value ='"
									+ ResourceStatus.UNDEAL.intValue()
									+ "' or attribute.value is null) and lower(user.id) like '%"
									+ this.escapeSQLLike(condition.getUserId().toLowerCase())
									+ "%' escape'/'");
		} else {
			return getNormalUsersUnderApplication(page, pagesize, group, user,
					new ArrayList<Group>());
		}
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.uniqueResult().toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#clearSession()
	 */
	/**
	 * 方法说明：clearSession
	 * 
	 * @return int
	 */
	public int clearSession()
	{
		getSession().flush();
		getSession().clear();
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getLeftUsersByLoginUser(int, int, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getLeftUsersByLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult getLeftUsersByLoginUser(int page, int pagesize, User user)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		List<String> groupList = new ArrayList<String>();
		boolean isSuperManager = false;
		for (Iterator iterator = user.getGroups().iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			groupList.add(group.getUuid());
			if (group.getCode().equals(InitParameters.getSuperGroupCode())) {
				isSuperManager = true;
				break;
			}
		}
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.LEAVE.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!isSuperManager) {
			c.createCriteria("groups").add(Restrictions.in("uuid", groupList));
		}

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.LEAVE.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!isSuperManager) {
			c1.createCriteria("groups").add(Restrictions.in("uuid", groupList));
		}
		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchLeftUsersByLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchLeftUsersByLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult searchLeftUsersByLoginUser(int page, int pagesize, Condition condition,
			User user)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition == null
				|| (condition.getUserId().equals("") && condition.getUserName().equals(""))) {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());
			result.setPager(pager);
			return result;
		}
		List<String> groupList = new ArrayList<String>();
		boolean isSuperManager = false;
		for (Iterator iterator = user.getGroups().iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			groupList.add(group.getUuid());
			if (group.getCode().equals(InitParameters.getSuperGroupCode())) {
				isSuperManager = true;
				break;
			}
		}
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.LEAVE.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!isSuperManager) {
			c.createCriteria("groups").add(Restrictions.in("uuid", groupList));
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {

			c.add(Restrictions.like("name", "%" + condition.getUserName() + "%"));
		} else if (!condition.getUserId().equalsIgnoreCase("")) {

			c.add(Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'", "%"
					+ this.escapeSQLLike(condition.getUserId()) + "%", Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.LEAVE.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!isSuperManager) {
			c1.createCriteria("groups").add(Restrictions.in("uuid", groupList));
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {

			c1.add(Restrictions.like("name", "%" + condition.getUserName() + "%"));
		} else if (!condition.getUserId().equalsIgnoreCase("")) {

			c1.add(Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'", "%"
					+ this.escapeSQLLike(condition.getUserId()) + "%", Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserResourceSyn(com.wt.uum2.domain.Resource, java.util.List)
	 */
	/**
	 * 方法说明：getUserResourceSyn
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getUserResourceSyn(Resource resource, List<String> keys)
	{
		Map<String, String> map = getAttributesMapByResource(resource);

		Map<String, String> returnList = new HashMap<String, String>();

		for (String key : keys) {
			if (map.get(key) != null) {
				returnList.put(key, map.get(key));
			}

		}
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getAllUsersUnderDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getAllUsersUnderDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getAllUsersUnderDepartment(Department department)
	{
		Query q = getSession()
				.createQuery(
						"select ud.user from DepartmentPath as dp,UserDepartment as ud where ud.user.status=:status and dp.juniorUUID=ud.departmentUUID and dp.elderUUID=:deptuuid");
		q.setString("deptuuid", department.getUuid());
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserDepartments(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserDepartments
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getUserDepartments(User user)
	{
		Query q = getSession()
				.createQuery(
						"select dept from Department as dept,UserDepartment as ud where dept.uuid=ud.departmentUUID and ud.userUUID=:useruuid)");
		q.setCacheable(true);
		q.setString("useruuid", user.getUuid());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserGroups(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserGroups
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserGroups(User user)
	{
		if (user == null) {
			return new ArrayList<Group>();
		}
		Query q = getSession()
				.createQuery(
						"select g from Group as g,UserGroup as ug where g.uuid=ug.groupUUID and ug.userUUID=:useruuid)");
		q.setCacheable(true);
		q.setString("useruuid", user.getUuid());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserAdminGroup(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserAdminGroup
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserAdminGroup(User user)
	{
		Query q = getSession()
				.createQuery(
						"select g from Group as g,ResourceAdminGroup as rag where g.uuid=rag.groupUUID and rag.resourceUUID=:useruuid)");
		q.setString("useruuid", user.getUuid());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getAttributesMapByResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getAttributesMapByResource
	 * 
	 * @param res
	 *            res
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResource(Resource res)
	{
		Map<String, String> returnList = new HashMap<String, String>();
		List list = getSession()
				.createQuery(
						"select new list(t.id,a.value) from Attribute as a,AttributeType as t"
								+ " where a.type=t and a.ownerResourceUUID=:resourceuuid and a.value is not null")
				.setString("resourceuuid", res.getUuid()).list();
		for (int i = 0; i < list.size(); i++) {
			List obj = (List) list.get(i);
			if (StringUtils.isNotBlank(obj.get(1).toString())) {
				returnList.put(obj.get(0).toString(), obj.get(1).toString());
			}
		}
		return returnList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByConditionAndERPCodeAndOrgCode(int, int, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndERPCodeAndOrgCode
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param orgCode
	 *            orgCode
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndERPCodeAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		Query q = getSession()
				.createQuery(
						"select user from User as user,Attribute as attribute,Department as department "
								+ "where attribute.ownerResource=user and department in elements(user.departments) and department.orgCode='"
								+ orgCode + "' "
								+ " and attribute.type.id ='sgccEmployeeCode' and "
								+ " attribute.value ='" + escapeSQLLike(condition.getUserId())
								+ "' and user.status= " + ResourceStatus.NORMAL.intValue()
								+ " order by user.order asc").setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Long count = (Long) getSession()
				.createQuery(
						"select count(user.uuid) from User as user,Attribute as attribute,Department as department "
								+ "where attribute.ownerResource=user and department in elements(user.departments) and department.orgCode='"
								+ orgCode + "' " + "and attribute.type.id ='sgccEmployeeCode' and "
								+ "attribute.value ='" + escapeSQLLike(condition.getUserId())
								+ "' and user.status=" + ResourceStatus.NORMAL.intValue() + "")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult();

		pager.setQuantityOfData(count.intValue());
		pager.compute();

		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByConditionAndERPCode(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndERPCode
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndERPCode(int page, int pagesize,
			Condition condition)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		Query q = getSession().createQuery(
				"select user from User as user,Attribute as attribute "
						+ " where attribute.ownerResource=user "
						+ " and attribute.type.id ='sgccEmployeeCode' and " + " attribute.value ='"
						+ escapeSQLLike(condition.getUserId()) + "' and user.status= "
						+ ResourceStatus.NORMAL.intValue() + " order by user.order asc")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Long count = (Long) getSession()
				.createQuery(
						"select count(user.uuid) from User as user,Attribute as attribute "
								+ " where attribute.ownerResource=user "
								+ " and attribute.type.id ='sgccEmployeeCode' and "
								+ " attribute.value ='" + escapeSQLLike(condition.getUserId())
								+ "' and user.status= " + ResourceStatus.NORMAL.intValue() + "")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult();

		pager.setQuantityOfData(count.intValue());
		pager.compute();

		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersByDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getUsersByDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getUsersByDepartment(Department department)
	{
		return getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.addOrder(Order.asc("order")).createCriteria("departments")
				.add(Restrictions.idEq(department.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUsersByDepartments(int, int, java.util.List)
	 */
	/**
	 * 方法说明：getUsersByDepartments
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult getUsersByDepartments(int page, int pagesize, List<Department> dept)
	{
		List<String> deptUuids = new ArrayList<String>();

		List<List<String>> list = new ArrayList<List<String>>();
		if (dept.size() > 1000) {

			List<String> list1 = new ArrayList<String>();
			for (int i = 0; i < dept.size(); i++) {
				list1.add("'" + dept.get(i).getUuid() + "'");
				if (i % 1000 == 0) {
					list.add(list1);
					list1 = new ArrayList<String>();
				}
			}
			List<String> newlist = new ArrayList<String>();
			for (List<String> listsub : list) {

				if (!listsub.isEmpty()) {
					newlist.add(" uuid in (" + StringUtils.join(listsub, ",") + ") ");
				}
			}

			String sql = " (" + StringUtils.join(newlist, " or ") + ") ";

			UserPageResult result = new UserPageResult();
			Pager pager = new Pager();
			pager.setCurrentPage(page);
			pager.setPageSize(pagesize);

			Long count = (Long) getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.setProjection(Projections.count("uuid"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.createCriteria("departments").add(Restrictions.sqlRestriction(sql))
					.uniqueResult();
			pager.setQuantityOfData(count);

			pager.compute();

			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.addOrder(Order.asc("order")).addOrder(Order.asc("name"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.createCriteria("departments").add(Restrictions.sqlRestriction(sql))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());

			result.setPager(pager);
			result.setList(c.list());

			return result;

		} else {
			for (Department list2 : dept) {
				deptUuids.add(list2.getUuid());
			}
			UserPageResult result = new UserPageResult();
			Pager pager = new Pager();
			pager.setCurrentPage(page);
			pager.setPageSize(pagesize);
			Long count = (Long) getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.setProjection(Projections.count("uuid")).createCriteria("departments")
					.add(Restrictions.in("uuid", deptUuids))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.uniqueResult();
			pager.setQuantityOfData(count);

			pager.compute();

			Criteria c = getSession().createCriteria(User.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.addOrder(Order.asc("order")).addOrder(Order.asc("name"))
					.createCriteria("departments").add(Restrictions.in("uuid", deptUuids))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

			result.setPager(pager);
			result.setList(c.list());
			return result;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserManagedGroups(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserManagedGroups
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserManagedGroups(User user)
	{
		Query q = getSession()
				.createQuery(
						"select group from Group as group,ResourceAdminGroup as ug where group.status=:status and group.uuid=ug.groupUUID and ug.resourceUUID=:useruuid)");
		q.setString("useruuid", user.getUuid()).setInteger("status",
				ResourceStatus.NORMAL.intValue());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#countNum(java.lang.String)
	 */
	/**
	 * 方法说明：countNum
	 * 
	 * @param typeid
	 *            typeid
	 * @return String
	 */
	public String countNum(String typeid)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("select value");
		sb.append("  from (select t.id, t.name, a.value");
		sb.append("          from U2_AttributeType t, U2_Attribute a");
		sb.append("         where t.id = :typeid");
		sb.append("           and t.uuid = a.typeuuid");
		sb.append("           and a.value like '06%'");
		sb.append("         order by a.value desc)");
		sb.append(" where rownum = 1");
		List l = getSession().createSQLQuery(sb.toString()).setString("typeid", typeid).list();
		if (l.isEmpty()) {
			return "0";
		} else {
			return l.get(0).toString();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByERPCode(java.lang.String)
	 */
	/**
	 * 方法说明：searchUsersByERPCode
	 * 
	 * @param userCode
	 *            userCode
	 * @return List
	 */
	public List<User> searchUsersByERPCode(String userCode)
	{
		return getUserByAttribute("sgccEmployeeCode", userCode);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserByAttribute(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getUserByAttribute
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<User> getUserByAttribute(String attrName, String attrValue)
	{
		// String hql = "select user from User as user,Attribute as attribute "
		// + "where attribute.ownerResource=user "
		// + "and attribute.type.id =:attrName";
		String hql = "select user from User as user,Attribute as attribute,StringValue as av "
				+ " where attribute.ownerResource=user " + " and av.attribute=attribute"
				+ " and attribute.type.id =:attrName";
		if (StringUtils.isNotBlank(attrValue)) {
			hql += " and av.value =:attrValue";
		} else {
			hql += " and av.value is null";
		}
		Query q = getSession().createQuery(hql);
		q.setString("attrName", attrName);
		if (StringUtils.isNotBlank(attrValue)) {
			q.setString("attrValue", attrValue);
		}
		return q.list();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserByAttributes(java.util.List, java.lang.String)
	 */
	/**
	 * 方法说明：getUserByAttributes
	 * 
	 * @param attList
	 *            attList
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<User> getUserByAttributes(List<AttributeType> attList, String attrValue)
	{
		String hql = "select user from User as user,Attribute as attribute "
				+ "where attribute.ownerResource=user "
				+ "and attribute.type in (:types) and attribute.value =:attrValue";
		Query q = getSession().createQuery(hql).setParameterList("types", attList)
				.setString("attrValue", attrValue);
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByDepartmentCondition(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.DepartmentCondition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchUsersByDepartmentCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dc
	 *            dc
	 * @param loginUser
	 *            loginUser
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUsersByDepartmentCondition(Integer page, Integer pagesize,
			DepartmentCondition dc, User loginUser)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		String orgcode = null;
		if(loginUser!=null){
			Query org = getSession().createSQLQuery("select orgcode from u2_department d where d.uuid=:uuid").setString("uuid", loginUser.getPrimaryDepartmentUUID());
			orgcode = (String) org.uniqueResult();
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append("select distinct user from User as user,UserDepartment as ud,Department as dept ")
				.append(" where ud.user=user ").append(" and ud.department=dept ");

		if (StringUtils.isNotBlank(dc.getId())) {
			sb.append(" and (dept.code=:code or dept.deptCode=:code) ");
		} else if (StringUtils.isNotBlank(dc.getName())) {
			sb.append(" and dept.name like :name ");
		}

		if (StringUtils.isNotBlank(orgcode)) {
			sb.append(" and dept.orgCode='" + orgcode + "' ");
		}

		sb.append(" and dept.status= ").append(ResourceStatus.NORMAL.intValue())
				.append(" and user.status= ").append(ResourceStatus.NORMAL.intValue())
				.append(" order by user.order asc, user.id");

		StringBuilder sb1 = new StringBuilder();
		sb1.append(
				"select count(distinct user.uuid) from User as user,UserDepartment as ud,Department as dept ")
				.append(" where ud.user=user ").append(" and ud.department=dept ");

		if (StringUtils.isNotBlank(dc.getId())) {
			sb1.append(" and (dept.code=:code or dept.deptCode=:code) ");
		} else if (StringUtils.isNotBlank(dc.getName())) {
			sb1.append(" and dept.name like :name ");
		}

		if (StringUtils.isNotBlank(orgcode)) {
			sb1.append(" and dept.orgCode='" + orgcode + "' ");
		}

		sb1.append(" and dept.status= ").append(ResourceStatus.NORMAL.intValue())
				.append(" and user.status= ").append(ResourceStatus.NORMAL.intValue())
				.append(" order by user.order asc");

		Query q = getSession().createQuery(sb.toString());

		if (StringUtils.isNotBlank(dc.getId())) {
			q.setString("code", escapeSQLLike(dc.getId()));
		} else if (StringUtils.isNotBlank(dc.getName())) {
			q.setString("name", "%" + escapeSQLLike(dc.getName()) + "%");
		}
		q.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Query l = getSession().createQuery(sb1.toString());

		if (StringUtils.isNotBlank(dc.getId())) {
			l.setString("code", escapeSQLLike(dc.getId()));
		} else if (StringUtils.isNotBlank(dc.getName())) {
			l.setString("name", "%" + escapeSQLLike(dc.getName()) + "%");
		}
		Long count = (Long) l.uniqueResult();

		pager.setQuantityOfData(count.intValue());
		pager.compute();

		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserListByStatus(int, int, com.wt.uum2.domain.User, com.wt.uum2.constants.ResourceStatus)
	 */
	/**
	 * 方法说明：getUserListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult getUserListByStatus(int page, int pagesize, User user, ResourceStatus rs)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);

		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", rs.intValue()))
				.addOrder(Order.desc("lastUpdateTime")).addOrder(Order.asc("id"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", rs.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUserListByStatus(int, int, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition, com.wt.uum2.constants.ResourceStatus)
	 */
	/**
	 * 方法说明：searchUserListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult searchUserListByStatus(int page, int pagesize, User user,
			Condition condition, ResourceStatus rs)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition == null
				|| (StringUtils.isEmpty(condition.getUserId()) && StringUtils.isEmpty(condition
						.getUserName()))) {
			pager.setQuantityOfData(0);
			pager.compute();
			result.setList(new ArrayList());
			result.setPager(pager);
			return result;
		}
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", rs.intValue()))
				.addOrder(Order.desc("lastUpdateTime")).addOrder(Order.asc("id"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!condition.getUserName().equalsIgnoreCase("")) {

			c.add(Restrictions.like("name", "%" + condition.getUserName() + "%"));
		} else if (!condition.getUserId().equalsIgnoreCase("")) {

			c.add(Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'", "%"
					+ this.escapeSQLLike(condition.getUserId()) + "%", Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", rs.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		if (!condition.getUserName().equalsIgnoreCase("")) {

			c1.add(Restrictions.like("name", "%" + condition.getUserName() + "%"));
		} else if (!condition.getUserId().equalsIgnoreCase("")) {

			c1.add(Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'", "%"
					+ this.escapeSQLLike(condition.getUserId()) + "%", Hibernate.STRING))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		}

		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#removePrimaryUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：removePrimaryUser
	 * 
	 * @param user
	 *            user
	 */
	public void removePrimaryUser(User user)
	{
		List<User> c = getSession().createCriteria(User.class)
				.add(Restrictions.eq("primaryUserUUID", user.getUuid())).list();
		if (CollectionUtils.isNotEmpty(c)) {
			for (User user2 : c) {
				user2.setPrimaryUser(null);
				user2.setPrimaryUserUUID(null);
				update(user2);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getCreatedUsers(int, int)
	 */
	/**
	 * 方法说明：getCreatedUsers
	 * 
	 * @param page
	 *            getCreatedUsers
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedUsers(int page, int pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Query q = getSession()
				.createQuery(
						"select user from User as user,UserAuthor as userAuthor where userAuthor in "
								+ "elements(user.userAuthors) and user.currentAuthorLevel is not null and user.currentAuthorLevel>0 and "
								+ "user.currentAuthorLevel=userAuthor.levels and (user.status="
								+ ResourceStatus.CREATED.intValue() + " or user.status="
								+ ResourceStatus.MOVEDEPT.intValue() + " or user.status="
								+ ResourceStatus.CHANGEPOSITION.intValue() + ")");
		Query q1 = getSession()
				.createQuery(
						"select count(user) from User as user,UserAuthor as userAuthor where userAuthor in "
								+ "elements(user.userAuthors) and user.currentAuthorLevel is not null and user.currentAuthorLevel>0 and "
								+ "user.currentAuthorLevel=userAuthor.levels and (user.status="
								+ ResourceStatus.CREATED.intValue() + " or user.status="
								+ ResourceStatus.MOVEDEPT.intValue() + " or user.status="
								+ ResourceStatus.CHANGEPOSITION.intValue() + ")");
		pager.setQuantityOfData((Long) q1.uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	// Added by Guo Tianci, -start 2011 -08 -08 --start
	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserCount()
	 */
	/**
	 * 方法说明：getUserCount
	 * 
	 * @return long
	 */
	public long getUserCount()
	{
		String hql = "select count(*) from User";
		Query q = getSession().createQuery(hql);
		return ((Long) q.uniqueResult()).longValue();
	}

	// --end

	// Added by Guo Tianci, -start 2011 -08 -12 --start
	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getSubDeptCount(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getSubDeptCount
	 * 
	 * @param dept
	 *            dept
	 * @return long
	 */
	public long getSubDeptCount(Department dept)
	{
		String hql = "select count(*) from Department as t where t.parent = :parent";
		Query q = getSession().createQuery(hql);
		q.setEntity("parent", dept);
		long count = ((Long) q.uniqueResult()).longValue();
		return count;
	}

	// --end

	// Added by Guo Tianci, -start 2011 -08 -17 --start
	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getUserListByStatuses(int, int, com.wt.uum2.domain.User, com.wt.uum2.constants.ResourceStatus[])
	 */
	/**
	 * 方法说明：getUserListByStatuses
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult getUserListByStatuses(int page, int pagesize, User user,
			ResourceStatus... rs)
	{

		Set<Integer> statuses = new HashSet<Integer>();

		for (ResourceStatus status : rs) {
			statuses.add(status.intValue());
		}

		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Criteria c = getSession().createCriteria(User.class)
				.add(Restrictions.in("status", statuses))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		Criteria c1 = getSession().createCriteria(User.class)
				.add(Restrictions.in("status", statuses))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		pager.setQuantityOfData((Long) c1.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(c.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	// --end

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getReAuthorUsersUnderApplicationByOrg(java.lang.Integer, java.lang.Integer, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getReAuthorUsersUnderApplicationByOrg
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @param appGroup
	 *            appGroup
	 * @return UserPageResult
	 */
	public UserPageResult getReAuthorUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Department dept, Group appGroup)
	{
		UserPageResult result = new UserPageResult();
		List list = new ArrayList();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		String appStatus = InitParameters.getAppStatusCode().replace("XXXX", appGroup.getCode());

		String l = "select u from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept"
				+ " and attribute.ownerResource=u and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "'";

		Query q = getSession().createQuery(l);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		q.setEntity("dept", dept);

		Query qd = getSession().createQuery(
				"select user from User as user,UserGroup as ug where ug.userUUID=user.uuid and ug.groupUUID='"
						+ appGroup.getUuid() + "')");
		list.addAll(q.list());
		list.removeAll(qd.list());

		pager.setQuantityOfData(list.size());

		pager.compute();

		int first = (pager.getCurrentPage() - 1) * pager.getPageSize();
		int max = pager.getCurrentPage() * pager.getPageSize();
		if (max > pager.getQuantityOfData()) {
			max = pager.getQuantityOfData();
		}
		result.setList(list.subList(first, max));
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersUnderApplicationByOrg(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：searchUsersUnderApplicationByOrg
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param org
	 *            org
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Condition condition, Department org, Group group)
	{

		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		String appStatus = InitParameters.getAppStatusCode().replace("XXXX", group.getCode());

		String list = "select u from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept"
				+ " and attribute.ownerResource=u and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "'";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Attribute as attribute,AttributeType as attrType where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept"
				+ " and attribute.ownerResource=u and attribute.typeUUID=attrType.uuid and attrType.id ='"
				+ appStatus
				+ "' and attribute.value ='"
				+ ResourceStatus.AUTHORIZE.intValue()
				+ "'";

		if (!condition.getUserName().equalsIgnoreCase("")) {
			list += " and u.name like '%" + condition.getUserName() + "%'";
			count += " and u.name like '%" + condition.getUserName() + "%'";
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			list += " and lower(u.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
			count += " and lower(u.id) like '%"
					+ this.escapeSQLLike(condition.getUserId().toLowerCase()) + "%' escape'/'";
		}

		Query l = getSession().createQuery(list);
		l.setInteger("status", ResourceStatus.NORMAL.intValue());
		l.setEntity("dept", org);

		Query c = getSession().createQuery(count);
		c.setInteger("status", ResourceStatus.NORMAL.intValue());
		c.setEntity("dept", org);

		pager.setQuantityOfData((Long) c.uniqueResult());

		pager.compute();

		result.setList(l.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#searchUsersByDepartmentConditionAndOrgCode(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.DepartmentCondition, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：searchUsersByDepartmentConditionAndOrgCode
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUsersByDepartmentConditionAndOrgCode(Integer page,
			Integer pagesize, DepartmentCondition condition, Department dept)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		String list = "select u from User u,UserDepartment ud,DepartmentPath dp,Department d where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept and dp.junior=d ";

		String count = "select count(u.uuid) from User u,UserDepartment ud,DepartmentPath dp,Department d where u.status=:status and u.uuid=ud.userUUID and ud.departmentUUID=dp.juniorUUID and dp.elder=:dept and dp.junior=d ";

		if (StringUtils.isNotBlank(condition.getName())) {
			list += " and d.name like '%" + condition.getName() + "%'";
			count += " and d.name like '%" + condition.getName() + "%'";
		} else {
			list += " and (d.code=:code or d.deptCode=:code) ";
			count += " and (d.code=:code or d.deptCode=:code) ";
		}

		Query l = getSession().createQuery(list);
		l.setInteger("status", ResourceStatus.NORMAL.intValue());
		l.setEntity("dept", dept);

		Query c = getSession().createQuery(count);
		c.setInteger("status", ResourceStatus.NORMAL.intValue());
		c.setEntity("dept", dept);

		if (StringUtils.isNotBlank(condition.getId())) {
			l.setString("code", condition.getId());
			c.setString("code", condition.getId());
		}

		pager.setQuantityOfData((Long) c.uniqueResult());

		pager.compute();

		result.setList(l.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());

		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserDao#getResource(java.lang.String)
	 */
	/**
	 * 方法说明：getResource
	 * 
	 * @param uuid
	 *            getResource
	 * @return Resource
	 */
	public Resource getResource(String uuid)
	{
		Resource resource = (Resource) getSession().createCriteria(Resource.class)
				.add(Restrictions.idEq(uuid)).uniqueResult();
		if (resource == null) {
			return null;
		}
		return resource;
	}

	public int getNumberOfUsers()
	{
		return ((Long) getSession().createCriteria(User.class)
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

}