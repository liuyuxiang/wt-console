package com.wt.uum2.rest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
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

import com.wt.uum2.domain.Duty;
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
@Path("/duty")
@Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
@Consumes(MediaType.APPLICATION_JSON+";charset=UTF-8")
public class DutyResource extends ResResource
{

	/**
	 * 方法说明：get
	 * 
	 * @param uuid
	 *            uuid
	 * @return Duty
	 */
	@Path("/{uuid}")
	@GET
	public Response getResponse(@PathParam("uuid") String uuid)
	{

		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK).entity(buildObject(get(uuid))).build();
	}

	/**
	 * 方法说明：get
	 * 
	 * @param uuid
	 *            uuid
	 * @return Duty
	 */
	@Path("/getid")
	@GET
	public Response getDutyId(@QueryParam("name") String name)
	{
		if (StringUtils.isBlank(name)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		String id = getDutyService().getDutyId(name);
		return Response.status(Status.OK).entity(buildObject(id)).build();
	}

	private Duty get(String uuid)
	{
		
		return getDutyService().get(uuid);
	}

	/**
	 * 方法说明：getByID
	 * 
	 * @param dutyID
	 *            dutyID
	 * @return Duty
	 */
	@Path("/id/{dutyID}")
	@GET
	public Response getByID(@PathParam("dutyID") String dutyID)
	{
		
		//TO DO
		if (StringUtils.isBlank(dutyID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getDutyService().getByID(dutyID))).build();
	}

	/**
	 * 方法说明：getParent
	 * 
	 * @param uuid
	 *            uuid
	 * @return Duty
	 */
	@Path("/{uuid}/parent")
	@GET
	public Response getParent(@PathParam("uuid") String uuid,
			@QueryParam("include") Boolean include)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty duty = get(uuid);
		if (duty == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getDutyService().getParent(duty,include))).build();
	}

	/**
	 * 方法说明：getChildren
	 * 
	 * @param uuid
	 *            uuid
	 * @param dutyCondition
	 *            dutyCondition
	 * @return List
	 */
	@Path("/{uuid}/child")
	@POST
	public Response getChildren(@PathParam("uuid") String uuid,
			@QueryParam("include") Boolean include)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty duty = get(uuid);
		if (duty == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		return Response.status(Status.OK)
				.entity(buildObject(getDutyService().getChildren(duty,include))).build();
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	@Path("/{uuid}/users")
	@GET
	public Response getUsers(@PathParam("uuid") String uuid,
			@QueryParam("include") Boolean include)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty duty = get(uuid);
		if (duty == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<User> list = new ArrayList<User>();
			List<Duty> dutys = getDutyService().getChildren(duty,BooleanUtils.toBooleanDefaultIfNull(include, false));
			for (Duty d : dutys) {
				list.addAll(getDutyService().getUsers(d));
			}
		list.addAll(getDutyService().getUsers(duty));
		HashSet<User> us = new HashSet<User>(list);
		list.clear();
		list.addAll(us);
		return Response.status(Status.OK).entity(buildObject(list)).build();
	}

	/**
	 * 方法说明：getUsers
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	@Path("/{uuid}/hasusers")
	@GET
	public Response hasUsers(@PathParam("uuid") String uuid)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty duty = get(uuid);
		if (duty == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		List<User> list = getDutyService().getUsers(duty);
		return Response.status(Status.OK).entity(buildObject(CollectionUtils.isNotEmpty(list))).build();
	}

	@Path("/all")
	@GET
	public Response getAll()
	{
		return Response.status(Status.OK).entity(buildObject(getDutyService().getAll()))
				.build();
	}
	
	@POST
	public Response modify(@FormParam("id") String id, @FormParam("level") int level,
			@FormParam("name") String name, @FormParam("uuid") String uuid)
	{
		Duty d;
		if(StringUtils.isBlank(uuid)){
			d = new Duty();
		}else{
			d = getDutyService().get(uuid);
		}
		d.setId(id);
		d.setLevel(level);
		d.setName(name);
		getDutyService().update(d);
		return Response.status(Status.OK).entity(buildObject(d)).build();
	}

	@Path("/{uuid}/add/{userid}")
	@POST
	public Response addUserDuty(@PathParam("uuid") String uuid, @PathParam("userid") String userid)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty d = getDutyService().get(uuid);
		if(d==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		User u = getUumService().getUserByUserId(userid);
		if(u==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).entity(buildObject(getDutyService().addUser(d, u))).build();
	}

	@Path("/{uuid}/remove/{userid}")
	@POST
	public Response removeUserDuty(@PathParam("uuid") String uuid, @PathParam("userid") String userid)
	{
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty d = getDutyService().get(uuid);
		if(d==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		User u = getUumService().getUserByUserId(userid);
		if(u==null){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.status(Status.OK).entity(buildObject(getDutyService().removeUser(d, u))).build();
	}

	@Path("/{uuid}")
	@DELETE
	public Response delete(@PathParam("uuid") String uuid){
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Duty d = get(uuid);
		getDutyService().delete(d);
		return Response.status(Status.OK).entity(buildObject(true)).build();

	}
	
	@Path("/getusers")
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED})
	public Response getUsersByRule(@FormParam("rule")String rule){
		if (StringUtils.isBlank(rule)) {
			return Response.status(Status.OK).build();
		}
		return Response.status(Status.OK).entity(buildObject(getAuthService().getUserByAuthItemRule(rule))).build();
	}

}
