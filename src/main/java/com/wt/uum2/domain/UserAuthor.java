package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author Alex
 * 
 *         用户审核
 */
public class UserAuthor implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9114972771888008632L;

	/**
	 * 主键
	 */
	private String uuid;

	/**
	 * 用户审核所在的组
	 */
	private Group group;

	/**
	 * 用户审核对应的用户
	 */
	private User user;

	/**
	 * 用户的审核级别
	 */
	private String levels;

	public String getLevels()
	{
		return levels;
	}

	public void setLevels(String levels)
	{
		this.levels = levels;
	}

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

	public String getUuid()
	{
		return uuid;
	}

	@SuppressWarnings("unused")
	private void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

}
