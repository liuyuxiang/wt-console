package com.wt.uum2.dao;

import java.util.Collection;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupRelationship;

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
public interface GroupRelationshipDao extends BaseDao<GroupRelationship>
{

	/**
	 * 方法说明：删除角色上级组
	 * 
	 * @param group
	 *            group
	 * @param groups
	 *            groups
	 */
	public void delete(Group group, Collection<Group> groups);

}
