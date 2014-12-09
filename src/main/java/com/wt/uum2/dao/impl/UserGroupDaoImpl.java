package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.Query;

import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceType;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.UserGroupDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupList;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserGroup;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserGroupDaoImpl extends BaseDaoSupport<UserGroup> implements UserGroupDao
{

	/**
	 * 方法说明：add
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	public void add(User user, Collection<Group> groups)
	{
		for (Group group : groups) {
			add(user, group);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserGroupDao#add(com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：add
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	public void add(User user, Group group)
	{
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		getSession().saveOrUpdate(userGroup);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserGroupDao#delete(com.wt.uum2.domain.User, java.util.Collection)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	public void delete(User user, Collection<Group> groups)
	{
		// Collection<String> groupUUIDs = new HashSet<String>();
		// for (Group group : groups) {
		// groupUUIDs.add(group.getUuid());
		// }
		String hql = "delete UserGroup ug where ug.user=:user and ug.group in (:groups)";
		getSession().createQuery(hql).setEntity("user", user).setParameterList("groups", groups)
				.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserGroupDao#deleteUserGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：deleteUserGroup
	 * 
	 * @param group
	 *            group
	 */
	public void deleteUserGroup(Group group)
	{
		String hql = "delete UserGroup ug where ug.group =:group";
		getSession().createQuery(hql).setEntity("group", group).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserGroupDao#getUsersGroupsByGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUsersGroupsByGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult<GroupList> getUsersGroupsByGroup(int page, int pagesize, Group group)
	{
		UserPageResult<GroupList> result = new UserPageResult<GroupList>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		Query countQuery = getSession()
				.createQuery(
						"select count(distinct res.uuid) from Resource as res,UserGroup as ug where ug.groupUUID=:groupUUID and res.uuid=ug.userUUID and res.status=:status")
				.setString("groupUUID", group.getUuid())
				.setInteger("status", ResourceStatus.NORMAL.intValue());
		pager.setQuantityOfData((Long) countQuery.uniqueResult());
		pager.compute();
		result.setPager(pager);
		List<Resource> list = getSession()
				.createQuery(
						"select distinct res from Resource as res,UserGroup as ug where ug.groupUUID=:groupUUID and res.uuid=ug.userUUID and res.status=:status")
				.setString("groupUUID", group.getUuid())
				.setInteger("status", ResourceStatus.NORMAL.intValue())
				.setFirstResult(pager.getDataStart()).setMaxResults(pagesize).list();
		List<GroupList> users = new ArrayList<GroupList>();
		List<GroupList> groups = new ArrayList<GroupList>();
		for (int i = 0; i < list.size(); i++) {
			Resource object = list.get(i);

			if (ResourceType.valueOf(object.getType()) == ResourceType.USER) {
				User u = (User) list.get(i);
				users.add(new GroupList(u.getUuid(), pager.getDataStart() + i + 1, u.getId(), u
						.getType(), u.getName(), "",/*u.getPrimaryDepartment().getName(),*/ ""));
			} else if (ResourceType.valueOf(object.getType()) == ResourceType.GROUP) {
				Group g = (Group) list.get(i);
				groups.add(new GroupList(g.getUuid(), pager.getDataStart() + i + 1, g.getCode(), g
						.getType(), g.getName(), "", ""));
			}

		}
		List<GroupList> groupList = new ArrayList<GroupList>();
		groupList.addAll(users);
		groupList.addAll(groups);
		result.setList(groupList);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserGroupDao#delete(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param user
	 *            user
	 */
	public void delete(User user)
	{
		String hql = "delete UserGroup ug where ug.user =:user";
		getSession().createQuery(hql).setEntity("user", user).executeUpdate();
	}

}
