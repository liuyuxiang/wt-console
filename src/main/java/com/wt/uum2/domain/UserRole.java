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
public class UserRole implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4203437230895616094L;

	private String uuid;

	private User user;

	private String userUUID;

	private Role role;

	private String roleUUID;

	private GroupRole groupRole;

	private String groupRoleUUID;

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the userUUID
	 */
	public String getUserUUID() {
		return userUUID;
	}

	/**
	 * @param userUUID
	 *            the userUUID to set
	 */
	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @return the roleUUID
	 */
	public String getRoleUUID() {
		return roleUUID;
	}

	/**
	 * @param roleUUID
	 *            the roleUUID to set
	 */
	public void setRoleUUID(String roleUUID) {
		this.roleUUID = roleUUID;
	}

	/**
	 * @return the groupRole
	 */
	public GroupRole getGroupRole() {
		return groupRole;
	}

	/**
	 * @param groupRole
	 *            the groupRole to set
	 */
	public void setGroupRole(GroupRole groupRole) {
		this.groupRole = groupRole;
	}

	/**
	 * @return the groupRoleUUID
	 */
	public String getGroupRoleUUID() {
		return groupRoleUUID;
	}

	/**
	 * @param groupRoleUUID
	 *            the groupRoleUUID to set
	 */
	public void setGroupRoleUUID(String groupRoleUUID) {
		this.groupRoleUUID = groupRoleUUID;
	}

}
