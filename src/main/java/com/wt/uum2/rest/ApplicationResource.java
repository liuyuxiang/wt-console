package com.wt.uum2.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import org.apache.commons.lang.StringUtils;

import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

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
@Path("/app")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationResource extends ResResource
{

	/**
	 * 方法说明：getAll
	 * 
	 * @return List
	 */
	@Path("/all")
	@GET
	public List<Application> getAll()
	{
		List<Application> appl = getUumService().getAllApplication(0, 999).getList();

		// List<Group> groupl = getUumService().getAllAppGroup();
		// List<Application> appl = new ArrayList<Application>();
		// for (Group g : groupl) {
		// Application a = new Application();
		// a.setCode(g.getCode());
		// a.setUuid(g.getUuid());
		// a.setName(g.getName());
		// appl.add(a);
		// }
		return appl;
	}

	/**
	 * 方法说明：get
	 * 
	 * @param appUUID
	 *            appUUID
	 * @return Response
	 */
	@Path("/{appUUID}")
	@GET
	public Response get(@PathParam("appUUID") String appUUID)
	{

		if (StringUtils.isBlank(appUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Application app = getUumService().getApplicationByUuid(appUUID);

		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.status(Status.OK).entity(app).build();
	}

	/**
	 * 方法说明：getMappingUser
	 * 
	 * @param appUUID
	 *            appUUID
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@Path("/mapping/{appUUID}")
	@POST
	public Response getMappingUser(@PathParam("appUUID") String appUUID,
			@QueryParam("userUUID") String userUUID)
	{
		if (StringUtils.isBlank(appUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		Application app = getUumService().getApplicationByUuid(appUUID);

		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		UserApplication ua = getUumAppService().getUserApplication(app, user);

		if (ua == null) {
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.OK).entity(ua).build();
	}

	/**
	 * 方法说明：getProfile
	 * 
	 * @param profileUUID
	 *            profileUUID
	 * @return AuthenticationProfile
	 */
	@Path("/profile/{profileUUID}")
	@GET
	public AuthenticationProfile getProfile(@PathParam("profileUUID") String profileUUID)
	{
		AuthenticationProfile authe = getUumAppService().getAuthenticationProfileByUUID(profileUUID);

		return authe;
	}

	/**
	 * 方法说明：getByID
	 * 
	 * @param appId
	 *            appId
	 * @return Application
	 */
	@Path("/id/{appId}")
	@GET
	public Application getByID(@PathParam("appId") String appId)
	{
		Application app = getUumService().getApplicationByAppId(appId);
		Set<AuthenticationProfile> auths = new HashSet<AuthenticationProfile>();
		auths.add(getUumAppService().getAuthenticationProfile(app));
		app.setAuthenticationProfile(auths);
		return app;
	}

	/**
	 * 方法说明：getAttributeValues
	 * 
	 * @param appUUID
	 *            appUUID
	 * @param uuid
	 *            uuid
	 * @param attrNames
	 *            attrNames
	 * @return Map
	 */
	@Path("/attrs")
	@GET
	@Deprecated
	public Map<String, String> getAttributeValues(@QueryParam("appUUID") String appUUID,
			@QueryParam("uuid") String uuid, @QueryParam("attrNames") String[] attrNames)
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = appUUID;
		}
		if (StringUtils.isBlank(uuid)) {
			return null;
		}
		return super.getAttributeValues(uuid, attrNames);
	}

	/**
	 * 方法说明：getAdmingGroups
	 * 
	 * @param appUUID
	 *            appUUID
	 * @return List
	 */
	@Path("/admingroup")
	@GET
	public List<Group> getAdmingGroups(@QueryParam("appUUID") String appUUID)
	{
		Group group = getUumService().getGroupByUuid(appUUID);
		if (group == null) {
			return null;
		}
		List<Group> grouplist = getUumService().getGroupManagedGroups(group);
		return grouplist;
	}

	/**
	 * 方法说明：创建用户映射对象
	 * 
	 * @param ua
	 * @return
	 */

	@Path("/ua/{useruuid}/{appuuid}")
	@PUT
	public Response createUserApplication(@PathParam("useruuid") String useruuid,
			@PathParam("appuuid") String appuuid, UserApplication ua)
	{
		if (StringUtils.isBlank(useruuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(appuuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(useruuid);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Application app = getUumService().getApplicationByUuid(appuuid);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		getUumAppService().saveOrUpdateUserApplication(app, user, ua.getMappendUserid(),
				ua.getMappendPassword(), true, null);

		return Response.status(Status.OK).entity(ua).build();
	}

	/**
	 * 方法说明：更新用户映射对象
	 * 
	 * @param ua
	 * @return
	 */
	@Path("/ua/{useruuid}/{appuuid}")
	@POST
	public Response updateUserApplication(@PathParam("useruuid") String useruuid,
			@PathParam("appuuid") String appuuid, UserApplication ua)
	{
		if (ua == null) {
			return Response.status(Status.NOT_FOUND).entity(false).build();
		}
		createUserApplication(useruuid, appuuid, ua);
		return Response.status(Status.OK).entity(!"".equals(ua.getUuid())).build();
	}

	/**
	 * 方法说明：删除映射对象
	 * 
	 * @param ua
	 * @return
	 */
	@Path("/ua/{useruuid}/{appuuid}")
	@DELETE
	public Response deleteUserApplication(@PathParam("useruuid") String useruuid,
			@PathParam("appuuid") String appuuid)
	{
		if (StringUtils.isBlank(useruuid)) {
			return Response.status(Status.BAD_REQUEST).entity(true).build();
		}
		if (StringUtils.isBlank(appuuid)) {
			return Response.status(Status.BAD_REQUEST).entity(true).build();
		}

		User user = getUumService().getUserByUuid(useruuid);
		if (user == null) {
			return Response.status(Status.NOT_FOUND).entity(true).build();
		}

		Application app = getUumService().getApplicationByUuid(appuuid);
		if (app == null) {
			return Response.status(Status.NOT_FOUND).entity(true).build();
		}
		UserApplication ua = getUumAppService().getUserApplication(app, user);
		if (ua == null) {
			return Response.status(Status.OK).entity(true).build();
		} else {
			getUumAppService().deleteUserApplication(ua);
		}
		return Response.status(Status.OK).entity(true).build();
	}
}
