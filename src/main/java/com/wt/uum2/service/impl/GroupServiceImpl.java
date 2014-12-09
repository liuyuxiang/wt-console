package com.wt.uum2.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.GroupDao;
import com.wt.uum2.dao.UserDao;
import com.wt.uum2.dao.UserGroupDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupList;
import com.wt.uum2.service.GroupService;

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
@Transactional
public class GroupServiceImpl implements GroupService
{
	private GroupDao groupDao;
	private UserDao userDao;
	private UserGroupDao userGroupDao;

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupService#getUsersGroupsByGroup(int, int, com.wt.uum2.domain.Group)
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
	@Transactional(readOnly = true)
	public UserPageResult<GroupList> getUsersGroupsByGroup(int page, int pagesize, Group group)
	{
		return userGroupDao.getUsersGroupsByGroup(page, pagesize, group);
	}

	public GroupDao getGroupDao()
	{
		return this.groupDao;
	}

	public void setGroupDao(GroupDao groupDao)
	{
		this.groupDao = groupDao;
	}

	public UserDao getUserDao()
	{
		return this.userDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public UserGroupDao getUserGroupDao()
	{
		return this.userGroupDao;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao)
	{
		this.userGroupDao = userGroupDao;
	}

}
