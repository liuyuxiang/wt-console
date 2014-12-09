package com.wt.uum2.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;

import com.hirisun.hea.api.domain.GroupCondition;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Path("/group")
@Produces(MediaType.APPLICATION_JSON)
public class GroupResource extends ResResource
{
	/**
	 * 方法说明：get
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return Group Response
	 */
	@Path("/{groupUUID}")
	@GET
	public Response getGroup(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// return getUumService().getGroupByUuid(groupUUID);

		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getGroupByUuid(groupUUID))).build();
	}

	/**
	 * 方法说明：get
	 * 
	 * @param groupUUID
	 * @return Group
	 */
	private Group get(String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return null;
		}
		return getUumService().getGroupByUuid(groupUUID);
	}

	/**
	 * 方法说明：create
	 * 
	 * @param group
	 *            group
	 * @return String
	 */
	@PUT
	public Response create(Group group)
	{
		Group g = getUumService().getGroupByCode(group.getCode());
		if (g != null) {
			// System.out.println("the group has " + group.getCode() + " exists!");
		} else {
			getUumService().createGroup(group);
			g = getUumService().getGroupByCode(group.getCode());
		}
		return Response.status(Status.OK).entity(buildObject(g == null ? null : g.getUuid()))
				.build();
	}

	/**
	 * 方法说明：update
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Path("/{groupUUID}")
	@POST
	public Response update(@PathParam("groupUUID") String groupUUID, Group group)
	{
		if (group == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		String uuid = StringUtils.isNotBlank(groupUUID) ? groupUUID : group.getUuid();
		Group g = getUumService().getGroupByUuid(uuid);
		if (g == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		g.setName(group.getName());
		g.setCode(group.getCode());
		getUumService().updateGroup(g);
		return Response.status(Status.OK).entity(buildObject(true)).build();
	}

	/**
	 * 方法说明：delete
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return boolean
	 */
	@SuppressWarnings("deprecation")
	@Path("/{groupUUID}")
	@DELETE
	public Response delete(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		if (CollectionUtils.isNotEmpty(getChildren(groupUUID))) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		if (CollectionUtils.isNotEmpty(getManagedGroup(groupUUID))) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		try {
			getUumService().deleteGroupByUuid(groupUUID);
			// getUumService().deleteGroup(group);
			return Response.status(Status.OK).entity(buildObject(true)).build();
		} catch (Exception e) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
	}

	/**
	 * 方法说明：getByID
	 * 
	 * @param id
	 *            id
	 * @return Group
	 */
	@Path("/id/{groupID}")
	@GET
	public Response getByID(@PathParam("groupID") String id)
	{
		if (StringUtils.isBlank(id)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(buildObject(getUumService().getGroupByCode(id)))
				.build();
	}

	/**
	 * 方法说明：getParent
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return List
	 */
	@Path("/parents/{groupUUID}")
	@GET
	public Response getParent(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = getUumService().getGroupByUuid(groupUUID);
		if (group == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getParentsGroup(group))).build();
	}

	/**
	 * 方法说明：getChildren
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return List
	 */
	@Path("/children/{groupUUID}")
	@GET
	public Response getChildrenResponse(@PathParam("groupUUID") String groupUUID,
			@QueryParam("groupUUID") String groupuid)
	{
		if (StringUtils.isBlank(groupUUID) && StringUtils.isBlank(groupuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		groupUUID = StringUtils.defaultIfEmpty(groupuid, groupUUID);
		Group group = getUumService().getGroupByUuid(groupUUID);
		if (group == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		// return getUumService().getGroupsByParentGroup(group);
		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getGroupsByParentGroup(group))).build();
	}

	private List<Group> getChildren(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return null;
		}
		Group group = getUumService().getGroupByUuid(groupUUID);
		if (group == null) {
			return null;
		}
		return getUumService().getGroupsByParentGroup(group);
	}

	/**
	 * 方法说明：getChildren
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param groupCondition
	 *            groupCondition
	 * @return List
	 */
	@Path("/children/{groupUUID}")
	@POST
	public Response getChildren(@PathParam("groupUUID") String groupUUID,
			GroupCondition groupCondition)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return List
	 */
	@Path("/users/{groupUUID}")
	@GET
	@Deprecated
	public Response getUsers(@PathParam("groupUUID") String groupUUID)
	{
		return getUsers(groupUUID, false);
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return List
	 */
	@Path("/{groupUUID}/users")
	@GET
	public Response getUsers(@PathParam("groupUUID") String groupUUID,
			@QueryParam("include") Boolean include)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<User> list = new ArrayList<User>();

		if (BooleanUtils.toBooleanDefaultIfNull(include, false)) {
			List<Group> groups = getUumService().getGroupsByParentGroup(group);
			for (Group child : groups) {
				list.addAll(getUumService().getUsersByGroup(child, 0, 0));
			}
		}
		list.addAll(getUumService().getUsersByGroup(group, 0, 0));
		HashSet<User> us = new HashSet<User>(list);
		list.clear();
		list.addAll(us);
		return Response.status(Status.OK).entity(buildObject(list)).build();
	}

	/**
	 * 方法说明：getManagedGroup
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @return List
	 */
	@Path("/managed/{groupUUID}")
	@GET
	public Response getManagedGroupResponse(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = getUumService().getGroupByUuid(groupUUID);
		if (group == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		if (StringUtils.equals(group.getCode(), InitParameters.getSuperGroupCode())) {
			return Response.status(Status.OK).entity(buildObject(getUumService().getAllGroups()))
					.build();
		}
		return Response
				.status(Status.OK)
				.entity(buildObject(getUumService().getGroupManagedUnderGroup(0, 999, group)
						.getList())).build();

	}

	private List<Group> getManagedGroup(@PathParam("groupUUID") String groupUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return null;
		}
		Group group = getUumService().getGroupByUuid(groupUUID);
		if (group == null) {
			return null;
		}
		if (StringUtils.equals(group.getCode(), InitParameters.getSuperGroupCode())) {
			return getUumService().getAllGroups();

		}
		return getUumService().getGroupManagedUnderGroup(0, 999, group).getList();

	}

	/**
	 * 方法说明：所有组
	 * 
	 * @return Response
	 */
	@Path("/all")
	@GET
	public Response getAll()
	{
		List<Group> groups = getUumService().getAllGroups();
		if (CollectionUtils.isEmpty(groups)) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.OK).entity(buildObject(groups)).build();
	}

	@Path("/root")
	@GET
	public Response getRoot()
	{
		return Response.status(Status.OK).entity(buildObject(getUumService().getRootGroup()))
				.build();
	}

	/**
	 * 方法说明：addMember
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param resourceUUID
	 *            resourceUUID
	 * @return boolean
	 */
	@Path("/member/{groupUUID}")
	@PUT
	public Response addMember(@PathParam("groupUUID") String groupUUID,
			@QueryParam("resourceUUID") String resourceUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(resourceUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		User user = getUumService().getUserByUuid(resourceUUID);
		if (user == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		if (getUumService().getUserGroups(user).contains(group)) {
			return Response.status(Status.OK).entity(buildObject(true)).build();
		}
		getUumService().addUserGroup(user, group);
		return Response.status(Status.OK).entity(buildObject(true)).build();
	}

	/**
	 * 方法说明：removeMember
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param resourceUUID
	 *            resourceUUID
	 * @return boolean
	 */
	@Path("/member/{groupUUID}")
	@DELETE
	public Response removeMember(@PathParam("groupUUID") String groupUUID,
			@QueryParam("resourceUUID") String resourceUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(resourceUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		User user = getUumService().getUserByUuid(resourceUUID);
		if (user == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		getUumService().deleteUserGroup(user, group);
		return Response.status(Status.OK).entity(buildObject(true)).build();
	}

	/**
	 * 方法说明：addOwner
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param ownerUUID
	 *            ownerUUID
	 * @return boolean
	 */
	@Path("/owner/{groupUUID}")
	@PUT
	public Response addOwner(@PathParam("groupUUID") String groupUUID,
			@QueryParam("ownerUUID") String ownerUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(ownerUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		Group owner = get(ownerUUID);
		if (owner == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		getUumService().addGroupManagedUnderGroup(group, owner);
		return Response.status(Status.OK).entity(buildObject(true)).build();
	}

	/**
	 * 方法说明：removeOwner
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param ownerUUID
	 *            ownerUUID
	 * @return boolean
	 */
	@Path("/owner/{groupUUID}")
	@DELETE
	public Response removeOwner(@PathParam("groupUUID") String groupUUID,
			@QueryParam("ownerUUID") String ownerUUID)
	{
		if (StringUtils.isBlank(groupUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(ownerUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Group group = get(groupUUID);
		if (group == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		Group owner = get(ownerUUID);
		if (owner == null) {
			return Response.status(Status.OK).entity(buildObject(false)).build();
		}
		getUumService().removeGroupManagedUnderGroup(group, owner);
		return Response.status(Status.OK).entity(buildObject(true)).build();
	}

	@Path("/list")
	@POST
	public Response getResourceByCondition(GroupCondition condition)
	{
		List<Group> list = new ArrayList<Group>();
		if (condition.getAttributeMatch() != null
				&& CollectionUtils.isNotEmpty(condition.getAttributeMatch().entrySet())) {
			for (Map.Entry<String, String> entry : condition.getAttributeMatch().entrySet()) {
				List<Resource> resources = getUumService().getResourceListByAttribute(
						entry.getKey(), entry.getValue());
				for (Iterator iterator = resources.iterator(); iterator.hasNext();) {
					Resource resource = (Resource) iterator.next();
					Group g = (Group) resource;
					list.add(g);
				}
				/*list.addAll(resources);*/
			}
		}

		Condition c = new Condition();
		if (StringUtils.isNotBlank(condition.getIdLike())
				|| StringUtils.isNotBlank(condition.getNameLike())) {
			if (StringUtils.isNotBlank(condition.getIdLike())) {
				c.setUserId(condition.getIdLike());
			}
			if (StringUtils.isNotBlank(condition.getNameLike())) {
				c.setUserName(condition.getNameLike());
			}
			list.addAll(getUumService().getGroups(0, Integer.MAX_VALUE, c).getList());
		}
		return Response.status(Status.OK).entity(buildObject(list)).build();
	}
}
