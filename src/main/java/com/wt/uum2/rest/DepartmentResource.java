package com.wt.uum2.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.hea.api.domain.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
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
@Path("/dept")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentResource extends ResResource
{

	/**
	 * 方法说明：get
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Department
	 */
	@Path("/{deptUUID}")
	@GET
	public Response getResponse(@PathParam("deptUUID") String deptUUID)
	{

		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(buildObject(get(deptUUID))).build();
	}

	private Department get(String deptUUID)
	{
		return getUumService().getDepartmentByUUID(deptUUID);
	}

	/**
	 * 方法说明：getByID
	 * 
	 * @param deptID
	 *            deptID
	 * @return Department
	 */
	@Path("/id/{deptID}")
	@GET
	public Response getByID(@PathParam("deptID") String deptID)
	{
		if (StringUtils.isBlank(deptID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getDepartmentByDeptCode(deptID))).build();
	}

	/**
	 * 方法说明：getParent
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Department
	 */
	@Path("/parent/{deptUUID}")
	@GET
	public Response getParent(@PathParam("deptUUID") String deptUUID)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department dept = get(deptUUID);
		if (dept == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getParentDepartment(dept))).build();
	}

	/**
	 * 方法说明：getOrganization
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Department
	 */
	@Path("/org/{deptUUID}")
	@GET
	public Response getOrganization(@PathParam("deptUUID") String deptUUID)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department org = new Department();
		Department dept = getUumService().getDepartmentByUUID(deptUUID);
		if (dept == null) {
			return Response.status(Status.OK).entity(buildObject(org)).build();
		}

		if (StringUtils.equals(ProjectResolver.getId(), "zdj")) {

			List<Department> allElder = getDepartmentPathService().getAllElderDepts(dept);

			Stack<Department> stack = new Stack<Department>();

			for (Department department : allElder) {
				if ("01".equals(getAttributeValue(department.getUuid(), "isorg"))) {
					stack.push(department);
				}
			}

			if (CollectionUtils.isNotEmpty(stack)) {
				org = stack.pop();
			}
		}

		if (StringUtils.equals(ProjectResolver.getId(), "default")) {

			List<Department> allElder = getDepartmentPathService().getAllElderDepts(dept);

			Stack<Department> stack = new Stack<Department>();

			for (Department department : allElder) {
				if ("company".equals(getAttributeValue(department.getUuid(), "depttype"))) {
					stack.push(department);
				}
			}

			if (CollectionUtils.isNotEmpty(stack)) {
				org = stack.pop();
			}
		}

		if (org.getUuid() == null) {
			org = getUumService().getOrganization(dept.getUuid());
		}
		return Response.status(Status.OK).entity(buildObject(org)).build();
	}

	/**
	 * 方法说明：getChildren
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return List
	 */
	@Path("/children/{deptUUID}")
	@GET
	public Response getChildren(@PathParam("deptUUID") String deptUUID)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getUumService().getDepartmentChildren(deptUUID))).build();
	}

	/**
	 * 方法说明：getChildren
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @param deptCondition
	 *            deptCondition
	 * @return List
	 */
	@Path("/children/{deptUUID}")
	@POST
	public Response getChildren(@PathParam("deptUUID") String deptUUID,
			DepartmentCondition deptCondition)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (deptCondition != null && deptCondition.isAllChildren()) {
			Department dept = get(deptUUID);
			if (dept == null) {
				if (StringUtils.isBlank(deptUUID)) {
					return Response.status(Status.NO_CONTENT).build();
				}
			}
			List<Department> children = getUumService().getDepartmentsByParent(dept);
			children.remove(dept);
			return Response.status(Status.OK).entity(buildObject(children)).build();
		}
		return Response.status(Status.OK).entity(buildObject(getChildren(deptUUID))).build();
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return List
	 */
	@Path("/users/{deptUUID}")
	@GET
	@Deprecated
	public Response getUsers(@PathParam("deptUUID") String deptUUID)
	{
		return getUsers(deptUUID, false);
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return List
	 */
	@Path("/{deptUUID}/users")
	@GET
	public Response getUsers(@PathParam("deptUUID") String deptUUID,
			@QueryParam("include") Boolean include)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department dept = get(deptUUID);
		if (dept == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<User> list = new ArrayList<User>();
		if (BooleanUtils.toBooleanDefaultIfNull(include, false)) {
			list.addAll(getUumService().getAllUsersUnderDepartment(dept));
		}else{
			list.addAll(getUumService().getUsersUnderDepartment(dept));
		}
		HashSet<User> us = new HashSet<User>(list);
		return Response.status(Status.OK).entity(buildObject(us)).build();
	}

	@Path("/all")
	@GET
	public Response getAll()
	{
		return Response.status(Status.OK).entity(buildObject(getUumService().getAllDepartments()))
				.build();
	}

	@Path("/root")
	@GET
	public Response getRoot()
	{
		return Response.status(Status.OK).entity(buildObject(getUumService().getDepartmentRoot()))
				.build();
	}

	/**
	 * 方法说明：hasChild
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return boolean
	 */
	@Path("/haschild/{deptUUID}")
	@GET
	public Response hasChild(@PathParam("deptUUID") String deptUUID)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department dept = get(deptUUID);
		if (dept == null) {
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.OK)
				.entity(buildObject(getUumService().hasSubDepartment(dept))).build();
	}

	@Path("/{deptUUID}/managegroups")
	@GET
	public Response manageGroups(@PathParam("deptUUID") String deptUUID)
	{
		if (StringUtils.isBlank(deptUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department dept = get(deptUUID);
		if (dept == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		// the dept managedgroups
		Collection<Group> list = new ArrayList<Group>(getUumService().getDepartmentManagedGroups(
				dept));
		// the dept all elder depts managedgroups
		List<Department> allElderDepts = getDepartmentPathService().getAllElderDepts(dept);
		for (Iterator<Department> iterator = allElderDepts.iterator(); iterator.hasNext();) {
			Department department = iterator.next();
			List<Group> groups = getUumService().getDepartmentManagedGroups(department);
			list = CollectionUtils.union(list, groups);
		}
		// super group
		Group adminGroup = getUumService().getGroupByCode(InitParameters.getSuperGroupCode());
		if (!list.contains(adminGroup)) {
			list.add(adminGroup);
		}
		return Response.status(Status.OK).entity(buildObject(list)).build();
	}

}
