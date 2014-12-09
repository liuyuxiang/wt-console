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
public class GroupRole implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4948336162125249012L;

	private Group group;

	private String groupUUID;

	private Role role;

	private String roleUUID;

	private String uuid;

	/**
	 * @return the group
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * @return the groupUUID
	 */
	public String getGroupUUID() {
		return groupUUID;
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
	 * @param group
	 *            the group to set
	 */
	public void setGroup(Group group) {
		this.group = group;
	}

	/**
	 * @param groupUUID
	 *            the groupUUID to set
	 */
	public void setGroupUUID(String groupUUID) {
		this.groupUUID = groupUUID;
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
