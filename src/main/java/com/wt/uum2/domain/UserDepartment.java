package com.wt.uum2.domain;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

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
public class UserDepartment implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7090453342346372680L;
	/**
	 * 
	 */
	private User user;
	/**
	 * 
	 */
	private Department department;
	/**
	 * 
	 */
	private String userUUID;
	/**
	 * 
	 */
	private String departmentUUID;

	/**
	 * 
	 */
	public UserDepartment()
	{
		super();
	}

	/**
	 * @param dept
	 *            dept
	 * @param user
	 *            user
	 */
	public UserDepartment(Department dept, User user)
	{
		this();
		setDepartment(dept);
		setUser(user);
	}

	public User getUser()
	{
		return user;
	}

	/**
	 * 方法说明：setUser
	 * 
	 * @param user
	 *            user
	 */
	public void setUser(User user)
	{
		this.user = user;
		if (user != null && StringUtils.isNotBlank(user.getUuid())) {
			setUserUUID(user.getUuid());
		}
	}

	public Department getDepartment()
	{
		return department;
	}

	/**
	 * 方法说明：setDepartment
	 * 
	 * @param department
	 *            department
	 */
	public void setDepartment(Department department)
	{
		this.department = department;
		if (department != null && StringUtils.isNotBlank(department.getUuid())) {
			setDepartmentUUID(department.getUuid());
		}
	}

	public String getUserUUID()
	{
		return userUUID;
	}

	public void setUserUUID(String userUUID)
	{
		this.userUUID = userUUID;
	}

	public String getDepartmentUUID()
	{
		return departmentUUID;
	}

	public void setDepartmentUUID(String departmentUUID)
	{
		this.departmentUUID = departmentUUID;
	}
}
