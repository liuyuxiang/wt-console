package com.wt.uum2.domain;

import java.io.Serializable;

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
public class UserGroup implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9117942066483500148L;
	private User user;
	private Group group;
	private String userUUID;
	private String groupUUID;

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	public Group getGroup()
	{
		return group;
	}

	public void setGroup(Group group)
	{
		this.group = group;
	}

	public String getUserUUID()
	{
		return userUUID;
	}

	public void setUserUUID(String userUUID)
	{
		this.userUUID = userUUID;
	}

	public String getGroupUUID()
	{
		return groupUUID;
	}

	public void setGroupUUID(String groupUUID)
	{
		this.groupUUID = groupUUID;
	}
}
