package com.wt.uum2.domain;

import java.io.Serializable;

/**
 * @author Alex
 * 
 *         部门审核
 */
public class DepartmentAuthor implements Serializable
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
	 * 部门审核所在的组
	 */
	private Group group;

	/**
	 * 部门审核对应的用户
	 */
	private Department department;

	/**
	 * 部门的审核级别
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

	public Department getDepartment()
	{
		return department;
	}

	public void setDepartment(Department department)
	{
		this.department = department;
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
