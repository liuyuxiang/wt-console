package com.wt.uum2.dao;

import java.util.Collection;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupList;
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
public interface UserGroupDao extends BaseDao<UserGroup>
{

	/**
	 * 删除用户权限组
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	public void delete(User user, Collection<Group> groups);

	/**
	 * 删除组下所有用户信息
	 * 
	 * @param group
	 *            group
	 */
	public void deleteUserGroup(Group group);

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
	public UserPageResult<GroupList> getUsersGroupsByGroup(int page, int pagesize, Group group);

	/**
	 * 
	 * 方法说明：删除所有用户的权限组
	 * 
	 * @param user
	 *            user
	 */
	public void delete(User user);

	/**
	 * 方法说明：添加用户权限组
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	public void add(User user, Group group);
}
