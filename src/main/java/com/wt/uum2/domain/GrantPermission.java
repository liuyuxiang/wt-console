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
public class GrantPermission implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6749413984809794136L;

	private Permission permission;

	private String permissionUUID;

	private String resUUID;

	private Role role;

	private String roleUUID;

	private String uuid;

	/**
	 * @return the permission
	 */
	public Permission getPermission() {
		return permission;
	}

	/**
	 * @return the permissionUUID
	 */
	public String getPermissionUUID() {
		return permissionUUID;
	}

	/**
	 * @return the resUUID
	 */
	public String getResUUID() {
		return resUUID;
	}

	/**
	 * @return the role
	 */
	public Role getRole() {
		return role;
	}

	/**
	 * @return the roleUUID
	 */
	public String getRoleUUID() {
		return roleUUID;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	/**
	 * @param permissionUUID
	 *            the permissionUUID to set
	 */
	public void setPermissionUUID(String permissionUUID) {
		this.permissionUUID = permissionUUID;
	}

	/**
	 * @param resUUID
	 *            the resUUID to set
	 */
	public void setResUUID(String resUUID) {
		this.resUUID = resUUID;
	}

	/**
	 * @param role
	 *            the role to set
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * @param roleUUID
	 *            the roleUUID to set
	 */
	public void setRoleUUID(String roleUUID) {
		this.roleUUID = roleUUID;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

}
