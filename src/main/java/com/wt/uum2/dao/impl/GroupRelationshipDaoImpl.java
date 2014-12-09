package com.wt.uum2.dao.impl;

import java.util.Collection;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum2.dao.GroupRelationshipDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupRelationship;

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
public class GroupRelationshipDaoImpl extends BaseDaoSupport<GroupRelationship> implements
		GroupRelationshipDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.GroupRelationshipDao#delete(com.wt.uum2.domain.Group, java.util.Collection)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param group
	 *            group
	 * @param groups
	 *            groups
	 */
	public void delete(Group group, Collection<Group> groups)
	{
		String hql = "delete GroupRelationship gr where gr.child=:group and gr.parent in (:groups)";
		getSession().createQuery(hql).setEntity("group", group).setParameterList("groups", groups)
				.executeUpdate();
	}

}
