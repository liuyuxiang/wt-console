package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.GroupDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupRelationship;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-26
 * 作者:	lichenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupDaoImpl extends BaseDaoSupport<Group> implements GroupDao
{

	@SuppressWarnings("unchecked")
	public List<Group> getAllGroups()
	{
		return getSession().createCriteria(Group.class).list();
	}

	/**
	 * 方法说明：searchGroupByNameOne
	 * 
	 * @param name
	 *            name
	 * @return Group
	 */
	public Group searchGroupByNameOne(String name)
	{
		return (Group) getSession().createCriteria(Group.class).add(Restrictions.eq("name", name))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue())).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getGroupByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Group
	 */
	public Group getGroupByUuid(String uuid)
	{
		Group group = (Group) getSession().createCriteria(Group.class).setCacheable(true)
				.add(Restrictions.eq("uuid", uuid))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue())).uniqueResult();
		return group;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByParent(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupsByParent
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByParent(Group group)
	{
		return getSession().createQuery(
				"select group1 from Group as group1 left join group1.parents as parents where parents.uuid='"
						+ group.getUuid() + "' and group1.status="
						+ ResourceStatus.NORMAL.intValue()
						+ " order by group1.order,group1.hasChildren,group1.code desc").list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getRootGroup()
	 */
	/**
	 * 方法说明：getRootGroup
	 * 
	 * @return Group
	 */
	public Group getRootGroup()
	{
		Group group = (Group) getSession().createCriteria(Group.class)
				.add(Restrictions.eq("code", InitParameters.getRootGroupCode())).uniqueResult();
		return group;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupManagedUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupManagedUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getGroupManagedUnderGroup(int page, int pagesize, Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setQuantityOfData((Long) getSession().createCriteria(Group.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(Group.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#searchGroupsManagedByAdmGroups(int, int, com.wt.uum2.constants.Condition, java.util.List, boolean)
	 */
	/**
	 * 方法说明：searchGroupsManagedByAdmGroups
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
	public UserPageResult searchGroupsManagedByAdmGroups(int page, int pagesize,
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
				from = " from Group as res " + "where res.status=:status and res.name like :name";
			} else {
				from = " from Group as res,ResourceAdminGroup as rag "
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
				from = " from Group as res " + "where res.status=:status and res.code like :code";
			} else {
				from = " from Group as res,ResourceAdminGroup as rag "
						+ "where rag.resourceUUID=dept.uuid and res.status=:status and  res.code like :code and rag.groupUUID in (:uuids)";
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
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByParentUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getGroupsByParentUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	public List<Group> getGroupsByParentUUID(String uuid)
	{
		return getSession().createQuery(
				"select group1 from Group as group1 left join group1.parents as parents where parents.uuid='"
						+ uuid + "' and group1.status=" + ResourceStatus.NORMAL.intValue()
						+ " order by group1.code desc").list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getAllGroupsByParent(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getAllGroupsByParent
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getAllGroupsByParent(Group group)
	{
		List<Group> all = new ArrayList<Group>();
		if(isMysqlDB()){
			List<GroupRelationship> grs = getGroupByParentUUID(new ArrayList<GroupRelationship>(),group.getUuid());
			for (GroupRelationship groupRelationship : grs) {
				all.add(groupRelationship.getChild());
			}
		}else{
			all = (List<Group>) getSession()
				.createSQLQuery(
						"select g.*,r.* from (select gs.childuuid from u2_grouprelationship gs start with gs.parentuuid=:uuid connect by prior gs.childuuid=gs.parentuuid) a ,u2_group g ,u2_resource r where g.uuid=r.uuid and r.STATUS=:status and a.childuuid=g.uuid")
				.addEntity(Group.class).setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("uuid", group.getUuid()).list();
		}
		return all;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByCondition(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：getGroupsByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult getGroupsByCondition(int page, int pagesize, Condition condition)
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

		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(Group.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(Group.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(Group.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("code", "%" + condition.getUserId() + "%").ignoreCase())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(Group.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("code", "%" + condition.getUserId() + "%").ignoreCase())
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

		}
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupByCode(java.lang.String)
	 */
	/**
	 * 方法说明：getGroupByCode
	 * 
	 * @param code
	 *            code
	 * @return Group
	 */
	public Group getGroupByCode(String code)
	{
		return (Group) getSession().createCriteria(Group.class).add(Restrictions.eq("code", code))
				.uniqueResult();
	}

	public List<Group> getAllAppGroup()
	{
		return getSession().createCriteria(Group.class).add(Restrictions.eq("groupType", "1"))
				.list();
	}

	public List<Group> getDefaultAddAppGroup()
	{
		return getSession().createCriteria(Group.class).add(Restrictions.eq("groupType", "1"))
				.add(Restrictions.eq("appGroupType", "1")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getAllGroupsByChildUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getAllGroupsByChildUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	public List<Group> getAllGroupsByChildUUID(String uuid)
	{
		List<Group> groupList = new ArrayList<Group>();
		if(isMysqlDB()){
			List<GroupRelationship> grs = getGroupByChildUUID(new ArrayList<GroupRelationship>(),uuid);
			for (GroupRelationship groupRelationship : grs) {
				groupList.add(groupRelationship.getParent());
			}
		}else{
			groupList = (List<Group>) getSession()
					.createSQLQuery(
							"select g.*,r.* from (select gs.parentuuid from u2_grouprelationship gs start with gs.childuuid=:uuid connect by prior gs.parentuuid=gs.childuuid) a ,u2_group g ,u2_resource r where g.uuid=r.uuid and r.STATUS=:status and a.parentuuid=g.uuid")
					.addEntity(Group.class).setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("uuid", uuid).list();
		}
		return groupList;
	}

	private List<GroupRelationship> getGroupByChildUUID(List<GroupRelationship> ships,String uuid) {
		
		List<GroupRelationship> reship = (List<GroupRelationship>) getSession()
				.createCriteria(GroupRelationship.class).add(
						Restrictions.eq("childUUID", uuid)).list();
		ships.addAll(reship);
		for (GroupRelationship ship : reship) {
			List<GroupRelationship> adf = (List<GroupRelationship>) getSession()
					.createCriteria(GroupRelationship.class).add(
							Restrictions.eq("child", ship.getParent())).list();
			if(adf!=null){
				ships.addAll(adf);
				return getGroupByChildUUID(ships,ship.getParentUUID());
			}
		}
		return ships;
	}

	private List<GroupRelationship> getGroupByParentUUID(List<GroupRelationship> ships,String uuid) {
		
		List<GroupRelationship> reship = (List<GroupRelationship>) getSession()
				.createCriteria(GroupRelationship.class).add(
						Restrictions.eq("parentUUID", uuid)).list();
		ships.addAll(reship);
		for (GroupRelationship ship : reship) {
			List<GroupRelationship> adf = (List<GroupRelationship>) getSession()
					.createCriteria(GroupRelationship.class).add(
							Restrictions.eq("parent", ship.getChild())).list();
			if(adf!=null){
				ships.addAll(adf);
				getGroupByParentUUID(ships,ship.getChildUUID());
			}
		}
		return ships;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByManagedUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getGroupsByManagedUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getGroupsByManagedUser(User user)
	{
		List<Group> groupList = (List<Group>) getSession()
				.createSQLQuery(
						"select g.*,r.* from U2_AdminGroupResource ag,U2_Group g,U2_Resource r where r.status=:status and r.uuid=g.uuid and ag.groupuuid=g.uuid and ag.resourceuuid=:uuid")
				.addEntity(Group.class).setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("uuid", user.getUuid()).list();
		return groupList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByChild(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupsByChild
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByChild(Group group)
	{
		List<Group> groupList = (List<Group>) getSession()
				.createSQLQuery(
						"select g.*,r.* from U2_Resource r, U2_Group g ,U2_GroupRelationship gs where r.status=:status and r.uuid=g.uuid and gs.parentuuid=g.uuid and gs.childuuid=:uuid")
				.addEntity(Group.class).setInteger("status", ResourceStatus.NORMAL.intValue())
				.setString("uuid", group.getUuid()).list();
		return groupList;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#existsResourceUnderGroups(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：existsResourceUnderGroups
	 * 
	 * @param group
	 *            group
	 * @return boolean
	 */
	public boolean existsResourceUnderGroups(Group group)
	{
		List list = getSession()
				.createSQLQuery("select ag.* from U2_AdminGroupResource ag where ag.groupuuid=:uuid")
				.setString("uuid", group.getUuid()).list();
		return !CollectionUtils.isEmpty(list);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getGroupsByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getGroupsByUser(User user)
	{
		return getSession()
				.createQuery(
						"select group from Group as group,User as user where group in elements(user.groups) and user.uuid=:uuid")
				.setString("uuid", user.getUuid()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getAdminGroupsByUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getAdminGroupsByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getAdminGroupsByUser(User user)
	{
		return getSession()
				.createQuery(
						"select group from Group as group,Resource as resource where group in elements(resource.adminGroups) and resource.uuid=:uuid")
				.setString("uuid", user.getUuid()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getGroupsByManagedGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupsByManagedGroup
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByManagedGroup(Group group)
	{
		Query q = getSession()
				.createQuery(
						"select group from Group as group,ResourceAdminGroup as ug where group.status=:status and group.uuid=ug.groupUUID and ug.resourceUUID=:uuid)");
		q.setString("uuid", group.getUuid()).setInteger("status", ResourceStatus.NORMAL.intValue());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupDao#getAdminGroups(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getAdminGroups
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getAdminGroups(Resource resource)
	{
		Query q = getSession()
				.createQuery(
						"select group from Group as group,ResourceAdminGroup as ug where group.status=:status and group.uuid=ug.groupUUID and ug.resource=:resource)");
		q.setEntity("resource", resource).setInteger("status", ResourceStatus.NORMAL.intValue());
		return q.list();
	}

}