package com.wt.uum2.event;

import java.util.Collection;
import java.util.Map;

import com.wt.uum2.domain.Event;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface EventFactory
{

	/**
	 * 方法说明：删除用户部门
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveDepartment(String userUUID, Collection<String> deptUUIDs);

	/**
	 * 方法说明：createUserEventRemoveDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveDepartment(String userUUID, String... deptUUIDs);

	/**
	 * 方法说明：添加用户部门
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventAddDepartment(String userUUID, Collection<String> deptUUIDs);

	/**
	 * 方法说明：createUserEventAddDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventAddDepartment(String userUUID, String... deptUUIDs);

	/**
	 * 方法说明：删除用户组
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveGroup(String userUUID, Collection<String> groupUUIDs);

	/**
	 * 方法说明：createUserEventRemoveGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveGroup(String userUUID, String... groupUUIDs);

	/**
	 * 方法说明：添加用户组
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventAddGroup(String userUUID, Collection<String> groupUUIDs);

	/**
	 * 方法说明：createUserEventAddGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventAddGroup(String userUUID, String... groupUUIDs);

	/**
	 * 方法说明：删除父组
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventRemoveGroup(String groupUUID, Collection<String> parentUUIDs);

	/**
	 * 方法说明：createGroupEventRemoveGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventRemoveGroup(String groupUUID, String... parentUUIDs);

	/**
	 * 方法说明：添加父组
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventAddGroup(String groupUUID, Collection<String> parentUUIDs);

	/**
	 * 方法说明：createGroupEventAddGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventAddGroup(String groupUUID, String... parentUUIDs);

	/**
	 * 方法说明：删除用户
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Event
	 */
	public Event createEventDeleteUser(String userUUID);

	/**
	 * 方法说明：创建用户
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Event
	 */
	public Event createEventCreateUser(String userUUID);

	/**
	 * 方法说明：更新用户
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateUser(String userUUID, Map<String, String[]> attrs);

	/**
	 * 方法说明：删除组
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return Event
	 */
	public Event createEventDeleteGroup(String groupUUID);

	/**
	 * 方法说明：创建组
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return Event
	 */
	public Event createEventCreateGroup(String groupUUID);

	/**
	 * 方法说明：更新组
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateGroup(String groupUUID, Map<String, String[]> attrs);

	/**
	 * 方法说明：删除部门
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Event
	 */
	public Event createEventDeleteDept(String deptUUID);

	/**
	 * 方法说明：创建部门
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Event
	 */
	public Event createEventCreateDept(String deptUUID);

	/**
	 * 方法说明：更新部门
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateDept(String deptUUID, Map<String, String[]> attrs);

	/**
	 * 方法说明：createUserEventAppLoginEnabled
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param appUUID
	 *            appUUID
	 * @return Event
	 */
	public Event createUserEventAppLoginEnabled(String userUUID, String appUUID);

	/**
	 * 方法说明：createUserEventAppLoginDisabled
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param appUUID
	 *            appUUID
	 * @return Event
	 */
	public Event createUserEventAppLoginDisabled(String userUUID, String appUUID);

}