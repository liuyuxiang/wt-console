package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.Query;

import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.dao.ResourceAdminGroupDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceAdminGroup;

/**
 * <pre>
 * 业务名: ResourceAdminGroupDaoImpl
 * 功能说明: ResourceAdminGroupDaoImpl
 * 编写日期:	2011-11-9
 * 作者:	Liu 
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ResourceAdminGroupDaoImpl extends BaseDaoSupport<ResourceAdminGroup> implements
		ResourceAdminGroupDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceAdminGroupDao#add(com.wt.uum2.domain.Group, com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：add
	 * 
	 * @param group
	 *            group
	 * @param resource
	 *            resource
	 */
	public void add(Group group, Resource resource)
	{
		ResourceAdminGroup uag = new ResourceAdminGroup();
		uag.setGroup(group);
		uag.setResource(resource);
		getSession().saveOrUpdate(uag);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceAdminGroupDao#getAdminGroups(com.wt.uum2.domain.Resource)
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
		String hql = "select group from Group as group,ResourceAdminGroup as ug where group.status=:status and group=ug.group and ug.resource=:resource)";
		Query q = getSession().createQuery(hql);
		q.setEntity("resource", resource);
		q.setInteger("status", ResourceStatus.NORMAL.intValue());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceAdminGroupDao#remove(com.wt.uum2.domain.Group, com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：remove
	 * 
	 * @param group
	 *            group
	 * @param resource
	 *            resource
	 */
	public void remove(Group group, Resource resource)
	{
		String hql = "delete ResourceAdminGroup ug where ug.group =:group and ug.resource=:resource";
		getSession().createQuery(hql).setEntity("group", group).setEntity("resource", resource)
				.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceAdminGroupDao#removeByResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：removeByResource
	 * 
	 * @param resource
	 *            resource
	 */
	public void removeByResource(Resource resource)
	{
		String hql = "delete ResourceAdminGroup ug where ug.resource=:resource";
		getSession().createQuery(hql).setEntity("resource", resource).executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceAdminGroupDao#removeByAdminGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：removeByAdminGroup
	 * 
	 * @param adminGroup
	 *            adminGroup
	 */
	public void removeByAdminGroup(Group adminGroup)
	{
		String hql = "delete ResourceAdminGroup ug where ug.group =:group";
		getSession().createQuery(hql).setEntity("group", adminGroup).executeUpdate();
	}

}
