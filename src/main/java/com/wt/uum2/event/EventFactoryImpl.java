package com.wt.uum2.event;

import java.util.Collection;
import java.util.Map;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceType;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名: eventfactory实现类
 * 功能说明: 
 * 编写日期:	2011-5-3
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class EventFactoryImpl implements EventFactory
{

	/**
	 * service bean
	 */
	private UUMService uumService;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * 
	 * 方法说明： EventParamBuilder
	 * 
	 * @return EventParamBuilder
	 */
	protected EventParamBuilder createEventParamBuilder()
	{
		return new EventParamBuilder();
	}

	/**
	 * 
	 * 方法说明：取得操作者
	 * 
	 * @return User
	 */
	private User getOperator()
	{
		User user = null;
		try {
			user = uumService.getLoginUser();
			// if(user==null){
			// user = uumService.getUserByUserId(InitParameters.getSuperUserCode());
			// }
		} catch (Exception e) {
			// //System.out.println(e.getMessage());
			// user = uumService.getUserByUserId(InitParameters.getSuperUserCode());
		}
		return user;
	}

	/**
	 * 
	 * 方法说明：填充资源信息
	 * 
	 * @param event
	 *            event
	 */
	private void fillResourceInfo(Event event)
	{
		// 首先填充操作者的信息
		fillOperatorInfo(event);
		String resourceId = String.valueOf("");
		String resourceName = String.valueOf("");
		switch (ResourceType.valueOfString(event.getResourceType())) {
		case USER:
		{
			User u = uumService.getUserByUuid(event.getResourceuuid());
			resourceId = u.getId();
			resourceName = u.getName();
		}
			break;

		case GROUP:
		{
			Group u = uumService.getGroupByUuid(event.getResourceuuid());
			resourceId = u.getCode();
			resourceName = u.getName();
		}
			break;

		case DEPARTMENT:
		{
			Department u = uumService.getDepartmentByUUID(event.getResourceuuid());
			resourceId = u.getDeptCode();
			resourceName = u.getName();
		}
			break;
		case APPLICATION:
		{
			Application u = uumService.getApplicationByUuid(event.getResourceuuid());
			resourceId = u.getCode();
			resourceName = u.getName();
		}
			break;

		default:
			break;
		}
		event.setResourceId(resourceId);
		event.setResourceName(resourceName);

	}

	/**
	 * 方法说明：填充操作者信息
	 * 
	 * @param event
	 *            event
	 */
	private void fillOperatorInfo(Event event)
	{
		User user = getOperator();
		if (user != null) {
			event.setOperUUID(user.getUuid());
			event.setOperator(user.getId());
			event.setOperatorName(user.getName());
			event.setOperatorDept(uumService.getUserPrimaryDepartment(user).getPath()
					.replace("→", "/"));
		}
		event.setOperatorIpAdderss(uumService.getOperatorIpAddress());
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventRemoveDepartment(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createUserEventRemoveDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveDepartment(String userUUID, Collection<String> deptUUIDs)
	{
		String[] uuids = new String[deptUUIDs.size()];
		return createUserEventRemoveDepartment(userUUID, deptUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventRemoveDepartment(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createUserEventRemoveDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveDepartment(String userUUID, String... deptUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.REMOVE_USER_DEPAREMENT.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < deptUUIDs.length; c++) {
			builder.add("deptUUID", "" + deptUUIDs[c], null);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAddDepartment(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createUserEventAddDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventAddDepartment(String userUUID, Collection<String> deptUUIDs)
	{
		String[] uuids = new String[deptUUIDs.size()];
		return createUserEventAddDepartment(userUUID, deptUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAddDepartment(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createUserEventAddDepartment
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param deptUUIDs
	 *            deptUUIDs
	 * @return Event
	 */
	public Event createUserEventAddDepartment(String userUUID, String... deptUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.ADD_USER_DEPAREMENT.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < deptUUIDs.length; c++) {
			builder.add("deptUUID", null, "" + deptUUIDs[c]);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventRemoveGroup(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createUserEventRemoveGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveGroup(String userUUID, Collection<String> groupUUIDs)
	{
		String[] uuids = new String[groupUUIDs.size()];
		return createUserEventRemoveGroup(userUUID, groupUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventRemoveGroup(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createUserEventRemoveGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventRemoveGroup(String userUUID, String... groupUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.REMOVE_USER_GROUP.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < groupUUIDs.length; c++) {
			builder.add("groupUUID", "" + groupUUIDs[c], null);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAddGroup(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createUserEventAddGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventAddGroup(String userUUID, Collection<String> groupUUIDs)
	{
		String[] uuids = new String[groupUUIDs.size()];
		return createUserEventAddGroup(userUUID, groupUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAddGroup(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createUserEventAddGroup
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Event
	 */
	public Event createUserEventAddGroup(String userUUID, String... groupUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.ADD_USER_GROUP.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < groupUUIDs.length; c++) {
			builder.add("groupUUID", null, "" + groupUUIDs[c]);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createGroupEventRemoveGroup(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createGroupEventRemoveGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventRemoveGroup(String groupUUID, Collection<String> parentUUIDs)
	{
		String[] uuids = new String[parentUUIDs.size()];
		return createGroupEventRemoveGroup(groupUUID, parentUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createGroupEventRemoveGroup(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createGroupEventRemoveGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventRemoveGroup(String groupUUID, String... parentUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.REMOVE_GROUP_GROUP.toString());
		event.setResourceuuid(groupUUID);
		event.setResourceType(ResourceType.GROUP.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < parentUUIDs.length; c++) {
			builder.add("parentUUID", "" + parentUUIDs[c], null);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createGroupEventAddGroup(java.lang.String, java.util.Collection)
	 */
	/**
	 * 方法说明：createGroupEventAddGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventAddGroup(String groupUUID, Collection<String> parentUUIDs)
	{
		String[] uuids = new String[parentUUIDs.size()];
		return createGroupEventAddGroup(groupUUID, parentUUIDs.toArray(uuids));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createGroupEventAddGroup(java.lang.String, java.lang.String[])
	 */
	/**
	 * 方法说明：createGroupEventAddGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param parentUUIDs
	 *            parentUUIDs
	 * @return Event
	 */
	public Event createGroupEventAddGroup(String groupUUID, String... parentUUIDs)
	{
		Event event = new Event();
		event.setType(EventType.ADD_GROUP_GROUP.toString());
		event.setResourceuuid(groupUUID);
		event.setResourceType(ResourceType.GROUP.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (int c = 0; c < parentUUIDs.length; c++) {
			builder.add("parentUUID", null, "" + parentUUIDs[c]);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventDeleteUser(java.lang.String)
	 */
	/**
	 * 方法说明：createEventDeleteUser
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Event
	 */
	public Event createEventDeleteUser(String userUUID)
	{
		Event event = new Event();
		event.setType(EventType.DELETE_USER.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		User user = uumService.getUserByUuid(userUUID);
		builder.add("id", user.getId(), null);
		builder.add("name", user.getName(), null);
		for (Department dept : uumService.getUserDepartments(user)) {
			builder.add("deptUUID", dept.getUuid(), null);
		}
		for (Group group : uumService.getUserGroups(user)) {
			builder.add("groupUUID", group.getUuid(), null);
		}
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(user)
				.entrySet()) {
			builder.add(entry.getKey(), entry.getValue(), null);
		}
		;
		event.setParams(builder.getList());
		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventCreateUser(java.lang.String)
	 */
	/**
	 * 方法说明：createEventCreateUser
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Event
	 */
	public Event createEventCreateUser(String userUUID)
	{
		Event event = new Event();
		event.setType(EventType.CREATE_USER.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		User user = uumService.getUserByUuid(userUUID);
		builder.add("id", null, user.getId());
		builder.add("name", null, user.getName());
		builder.add("order", null, user.getOrder().toString());
		if (InitParameters.getPlainPassword() != null
				&& InitParameters.getPlainPassword().equalsIgnoreCase("true")) {
			builder.add("userPassword", null, user.getPlainPassword());
		} else {
			builder.add("userPassword", null, user.getPassword());
		}
		for (Department dept : uumService.getUserDepartments(user)) {
			builder.add("deptUUID", null, dept.getUuid());
		}
		for (Group group : uumService.getUserGroups(user)) {
			builder.add("groupUUID", null, group.getUuid());
		}
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(user)
				.entrySet()) {
			builder.add(entry.getKey(), null, entry.getValue());
		}
		event.setParams(builder.getList());
		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventUpdateUser(java.lang.String, java.util.Map)
	 */
	/**
	 * 方法说明：createEventUpdateUser
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateUser(String userUUID, Map<String, String[]> attrs)
	{
		Event event = new Event();
		event.setType(EventType.UPDATE_USER.toString());
		event.setResourceuuid(userUUID);
		event.setResourceType(ResourceType.USER.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (Map.Entry<String, String[]> entry : attrs.entrySet()) {
			builder.add(entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
		}
		event.setParams(builder.getList());
		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventDeleteGroup(java.lang.String)
	 */
	/**
	 * 方法说明：createEventDeleteGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return Event
	 */
	public Event createEventDeleteGroup(String groupUUID)
	{
		Event event = new Event();
		event.setType(EventType.DELETE_GROUP.toString());
		event.setResourceuuid(groupUUID);
		event.setResourceType(ResourceType.GROUP.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		Group group = uumService.getGroupByUuid(groupUUID);
		builder.add("code", group.getCode(), null);
		builder.add("name", group.getName(), null);
		for (Group parent : uumService.getParentsGroupsByGroup(group)) {
			builder.add("parentUUID", parent.getUuid(), null);
		}
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(group)
				.entrySet()) {
			builder.add(entry.getKey(), entry.getValue(), null);
		}
		;
		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventCreateGroup(java.lang.String)
	 */
	/**
	 * 方法说明：createEventCreateGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return Event
	 */
	public Event createEventCreateGroup(String groupUUID)
	{
		Event event = new Event();
		event.setType(EventType.CREATE_GROUP.toString());
		event.setResourceuuid(groupUUID);
		event.setResourceType(ResourceType.GROUP.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		Group group = uumService.getGroupByUuid(groupUUID);
		builder.add("code", null, group.getCode());
		builder.add("name", null, group.getName());
		for (Group parent : uumService.getParentsGroupsByGroup(group)) {
			builder.add("parentUUID", null, parent.getUuid());
		}
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(group)
				.entrySet()) {
			builder.add(entry.getKey(), null, entry.getValue());
		}
		;
		event.setParams(builder.getList());
		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventUpdateGroup(java.lang.String, java.util.Map)
	 */
	/**
	 * 方法说明：createEventUpdateGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateGroup(String groupUUID, Map<String, String[]> attrs)
	{
		Event event = new Event();
		event.setType(EventType.UPDATE_GROUP.toString());
		event.setResourceuuid(groupUUID);
		event.setResourceType(ResourceType.GROUP.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (Map.Entry<String, String[]> entry : attrs.entrySet()) {
			builder.add(entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventDeleteDept(java.lang.String)
	 */
	/**
	 * 方法说明：createEventDeleteDept
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Event
	 */
	public Event createEventDeleteDept(String deptUUID)
	{
		Event event = new Event();
		event.setType(EventType.DELETE_DEPAREMENT.toString());
		event.setResourceuuid(deptUUID);
		event.setResourceType(ResourceType.DEPARTMENT.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		Department dept = uumService.getDepartmentByUUID(deptUUID);
		builder.add("code", dept.getCode(), null);
		builder.add("deptCode", dept.getDeptCode(), null);
		builder.add("parentCode", dept.getDeptParentCode(), null);
		builder.add("name", dept.getName(), null);
		builder.add("orgCode", dept.getOrgCode(), null);
		builder.add("parentUUID", dept.getParentUUID(), null);
		builder.add("order", String.valueOf(dept.getOrder()), null);
		builder.add("rtxCode", String.valueOf(dept.getRtxCode()), null);
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(dept)
				.entrySet()) {
			builder.add(entry.getKey(), entry.getValue(), null);
		}
		;

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventCreateDept(java.lang.String)
	 */
	/**
	 * 方法说明：createEventCreateDept
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Event
	 */
	public Event createEventCreateDept(String deptUUID)
	{
		Event event = new Event();
		event.setType(EventType.CREATE_DEPAREMENT.toString());
		event.setResourceuuid(deptUUID);
		event.setResourceType(ResourceType.DEPARTMENT.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();
		Department dept = uumService.getDepartmentByUUID(deptUUID);
		builder.add("code", null, dept.getCode());
		builder.add("deptCode", null, dept.getDeptCode());
		builder.add("parentCode", null, dept.getDeptParentCode());
		builder.add("name", null, dept.getName());
		builder.add("orgCode", null, dept.getOrgCode());
		builder.add("parentUUID", null, dept.getParentUUID());
		builder.add("order", null, String.valueOf(dept.getOrder()));
		builder.add("rtxCode", null, String.valueOf(dept.getRtxCode()));
		for (Map.Entry<String, String> entry : uumService.getAttributesMapByResource(dept)
				.entrySet()) {
			builder.add(entry.getKey(), null, entry.getValue());
		}
		;

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createEventUpdateDept(java.lang.String, java.util.Map)
	 */
	/**
	 * 方法说明：createEventUpdateDept
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @param attrs
	 *            attrs
	 * @return Event
	 */
	public Event createEventUpdateDept(String deptUUID, Map<String, String[]> attrs)
	{
		Event event = new Event();
		event.setType(EventType.UPDATE_DEPAREMENT.toString());
		event.setResourceuuid(deptUUID);
		event.setResourceType(ResourceType.DEPARTMENT.stringValue());
		fillResourceInfo(event);

		EventParamBuilder builder = createEventParamBuilder();

		for (Map.Entry<String, String[]> entry : attrs.entrySet()) {
			builder.add(entry.getKey(), entry.getValue()[0], entry.getValue()[1]);
		}

		event.setParams(builder.getList());

		return event;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAppLoginEnabled(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：createUserEventAppLoginEnabled
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param appUUID
	 *            appUUID
	 * @return Event
	 */
	public Event createUserEventAppLoginEnabled(String userUUID, String appUUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.event.EventFactory#createUserEventAppLoginDisabled(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：createUserEventAppLoginDisabled
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param appUUID
	 *            appUUID
	 * @return Event
	 */
	public Event createUserEventAppLoginDisabled(String userUUID, String appUUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

}
