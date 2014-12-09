package com.wt.uum2.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
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
import org.apache.commons.lang.StringUtils;

import com.hirisun.hea.api.domain.UserCondition;
import com.wt.uum.shiro.authc.UserCredentialsMatcher;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-11-16
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource extends ResResource
{

	/**
	 * 方法说明：get
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@Path("/{userUUID}")
	@GET
	public Response get(@PathParam("userUUID") String userUUID)
	{
		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.status(Status.OK).entity(buildObject(user)).build();

	}

	/**
	 * 方法说明：create
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@PUT
	public Response create(Map<String, String> attrs)
	{
		String userid = attrs.get("user.id");
		String username = attrs.get("user.name");
		String upd = attrs.get("user.password");
		String deptuuid = attrs.get("dept.uuid");
		if (StringUtils.isBlank(userid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		Department dept = getUumService().getDepartmentByUUID(deptuuid);
		if (dept == null) {
			dept = getUumService().getDepartmentRoot();
		}

		User u = getUumService().getUserByUserId(userid);
		if (u == null) {
			u = new User();
			u.setId(userid);
		}
		u.setName(username);
		u.setPassword(upd);
		u.setPrimaryDepartment(dept);
		if (StringUtils.isBlank(u.getUuid())) {
			getUumService().createUser(u);
		} else {
			getUumService().updateUser(u);
		}
		super.modifyAttributeValues(u.getUuid(), attrs);
		return Response.status(Status.OK).entity(buildObject(u)).build();

	}

	/**
	 * 方法说明：getByID
	 * 
	 * @param userID
	 *            userID
	 * @return Response
	 */
	@Path("/id/{userID}")
	@GET
	public Response getByID(@PathParam("userID") String userID)
	{

		if (StringUtils.isBlank(userID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUserId(userID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		return Response.status(Status.OK).entity(buildObject(user)).build();
	}

	/**
	 * 方法说明：getDepartments
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@Path("/depts/{userUUID}")
	@GET
	public Response getDepartments(@PathParam("userUUID") String userUUID)
	{

		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		List<Department> depts = getUumService().getUserDepartments(user);

		if (CollectionUtils.isEmpty(depts)) {
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.OK).entity(buildObject(depts)).build();
	}

	/**
	 * 方法说明：getDepartment
	 * 
	 * @param uuid
	 *            uuid
	 * @return Response
	 */
	@Path("/dept/{userUUID}")
	@GET
	public Response getDepartment(@PathParam("userUUID") String uuid)
	{

		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(uuid);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Department dept = getUumService().getDepartmentByUUID(new String(user.getPrimaryDepartmentUUID()));

		if (dept == null) {
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.OK).entity(buildObject(dept)).build();

	}

	/**
	 * 方法说明：getGroups
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@Path("/groups/{userUUID}")
	@GET
	public Response getGroups(@PathParam("userUUID") String userUUID)
	{

		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		List<Group> groups = getUumService().getUserGroups(user);

		return Response.status(Status.OK).entity(buildObject(groups)).build();

	}

	/**
	 * 方法说明：getAttributeValue
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param uuid
	 *            uuid
	 * @param attrName
	 *            attrName
	 * @return Response
	 */
	@Path("/attr")
	@GET
	public Response getAttributeValue(@QueryParam("userUUID") String userUUID,
			@QueryParam("uuid") String uuid, @QueryParam("attrName") String attrName)
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = userUUID;
		}
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (StringUtils.isBlank(attrName)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		String value = super.getAttributeValue(uuid, attrName);

		return Response.status(Status.OK).entity(buildObject(value)).build();
	}

	/**
	 * 方法说明：modifyAttributeValue
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param uuid
	 *            uuid
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return boolean
	 */
	@Path("/attr")
	@PUT
	public Response modifyAttributeValue(@QueryParam("userUUID") String userUUID,
			@QueryParam("uuid") String uuid, @QueryParam("attrName") String attrName,
			@QueryParam("attrValue") String attrValue)
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = userUUID;
		}
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// return super.modifyAttributeValue(uuid, attrName, attrValue);
		return Response.status(Status.OK)
				.entity(buildObject(super.modifyAttributeValue(uuid, attrName, attrValue))).build();
	}

	/**
	 * 方法说明：getAttributeValues
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param uuid
	 *            uuid
	 * @param attrNames
	 *            attrNames
	 * @return Map
	 */
	@Path("/attrs")
	@GET
	public Response getAttributeValues(@QueryParam("userUUID") String userUUID,
			@QueryParam("uuid") String uuid, @QueryParam("attrNames") String[] attrNames)
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = userUUID;
		}
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// return super.getAttributeValues(uuid, attrNames);

		return Response.status(Status.OK)
				.entity(buildObject(super.getAttributeValues(uuid, attrNames))).build();
	}

	/**
	 * 方法说明：modifyAttributeValues
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param uuid
	 *            uuid
	 * @param paraMap
	 *            paraMap
	 * @return boolean
	 */
	@Path("/attrs")
	@PUT
	public Response modifyAttributeValues(@QueryParam("userUUID") String userUUID,
			@QueryParam("uuid") String uuid, Map<String, String> paraMap)
	{
		if (StringUtils.isBlank(uuid)) {
			uuid = userUUID;
		}
		if (StringUtils.isBlank(uuid)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		// return super.modifyAttributeValues(uuid, paraMap);
		return Response.status(Status.OK)
				.entity(buildObject(super.modifyAttributeValues(uuid, paraMap))).build();
	}

	/**
	 * 方法说明：getAll
	 * 
	 * @return Response
	 */
	@Path("/all")
	@GET
	public Response getAll()
	{

		List<User> users = getUumService().getAllNormalUsers();

		if (CollectionUtils.isEmpty(users)) {

			return Response.status(Status.NO_CONTENT).entity(users).build();

		}
		return Response.status(Status.OK).entity(buildObject(users)).build();

	}

	/**
	 * 方法说明：isInGroups
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param groupUUIDs
	 *            groupUUIDs
	 * @return Response
	 */
	@Path("/ingroups")
	@GET
	public Response isInGroups(@QueryParam("userUUID") String userUUID,
			@QueryParam("groupUUIDs") String[] groupUUIDs)
	{
		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		if (groupUUIDs == null || groupUUIDs.length == 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		List<Group> ug = getUumService().getUserGroups(user);

		if (CollectionUtils.isEmpty(ug)) {
			return Response.status(Status.NO_CONTENT).build();
		}

		for (String string : groupUUIDs) {
			if (ug.contains(getUumService().getGroupByUuid(string))) {
				return Response.status(Status.OK).entity(buildObject(true)).build();
			}
		}
		return Response.status(Status.OK).entity(buildObject(false)).build();
	}

	/**
	 * 方法说明：verifyPwd
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param pwd
	 *            pwd
	 * @return boolean
	 */
	@Path("/verifypwd")
	@GET
	public Response verifyPwdResponse(@QueryParam("userUUID") String userUUID,
			@QueryParam("pwd") String pwd)
	{
		if (StringUtils.isEmpty(userUUID) || StringUtils.isEmpty(pwd)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		User u = getUumService().getUserByUuid(userUUID);
		if (u == null) {
			return Response.status(Status.NO_CONTENT).build();
		}

		return Response.status(Status.OK).entity(buildObject(verifyPwd(userUUID, pwd))).build();

	}

	public boolean verifyPwd(String userUUID, String pwd)
	{
		if (StringUtils.isEmpty(userUUID) || StringUtils.isEmpty(pwd)) {
			return false;
		}
		User u = getUumService().getUserByUuid(userUUID);
		if (u == null) {
			return false;
		}

		UserCredentialsMatcher ucm = new UserCredentialsMatcher();

		if (ucm.isFakeLogin(u.getId(), pwd)) {
			return true;
		}

		if (InitParameters.isPlainPassword()) {
			return StringUtils.equals(u.getPlainPassword(), pwd);
		} else {
			if (StringUtils.equals("true", InitParameters.getMD5EncodePassTurnOn())) {
				pwd = StringParse.md5(pwd);
			}

			return StringUtils.equals(u.getPlainPassword(), pwd);
		}

	}

	/**
	 * 方法说明：verifyPwdByID
	 * 
	 * @param userID
	 *            userID
	 * @param pwd
	 *            pwd
	 * @return boolean
	 */
	@Path("/verifypwd/id")
	@GET
	public Response verifyPwdByID(@QueryParam("userID") String userID, @QueryParam("pwd") String pwd)
	{
		if (StringUtils.isEmpty(userID) || StringUtils.isEmpty(pwd)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		User u = getUumService().getUserByUserId(userID);
		if (u == null) {
			return Response.status(Status.NO_CONTENT).build();
		}
		boolean flag = verifyPwd(u.getUuid(), pwd);

		return Response.status(Status.OK).entity(buildObject(flag)).build();
	}

	/**
	 * 方法说明：current
	 * 
	 * @return User
	 */
	@Path("/current")
	@GET
	public Response current()
	{
		return Response.status(Status.OK).entity(buildObject(getUumService().getLoginUser()))
				.build();
	}

	/**
	 * 方法说明：updateUserApplication
	 * 
	 * @param userUUID
	 *            userUUID
	 * @param appUUID
	 *            appUUID
	 * @param account
	 *            account
	 * @param pwd
	 *            pwd
	 * @return Response
	 */
	@Path("/appmessage")
	@PUT
	public Response updateUserApplication(@QueryParam("userUUID") String userUUID,
			@QueryParam("appUUID") String appUUID, @QueryParam("account") String account,
			@QueryParam("pwd") String pwd)
	{
		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		if (StringUtils.isBlank(appUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		Application app = getUumService().getApplicationByUuid(appUUID);

		if (app == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		try {
			getUumAppService().saveOrUpdateUserApplication(app, user, account, pwd, null, null);
			return Response.status(Status.OK).entity(buildObject(true)).build();
		} catch (Exception e) {
			return Response.status(Status.NOT_MODIFIED).entity(false).build();
		}
	}

	/**
	 * 方法说明：getResourceByAttribute
	 * 
	 * @param userCondition
	 *            userCondition
	 * @return List
	 */
	@Path("/list")
	@POST
	public Response getResourceByAttribute(UserCondition userCondition)
	{
		List<User> list = new ArrayList<User>();
		if (userCondition.isExact()) {
			// 精确查找
			if (CollectionUtils.isNotEmpty(userCondition.getAttributeMatch().entrySet())) {
				for (Map.Entry<String, String> entry : userCondition.getAttributeMatch().entrySet()) {
					list.addAll(getUumService()
							.getUserByAttribute(entry.getKey(), entry.getValue()));
				}
			}
			if (StringUtils.isNotBlank(userCondition.getIdLike())
					|| StringUtils.isNotBlank(userCondition.getNameLike())) {
				if (StringUtils.isNotBlank(userCondition.getIdLike())) {
					list.add(getUumService().getUserByUserId(userCondition.getIdLike()));
				}
				if (StringUtils.isNotBlank(userCondition.getNameLike())) {
					list.addAll(getUumService().getUserByUserName(userCondition.getNameLike()));
				}
			}
		} else {
			// 模糊匹配
			if (CollectionUtils.isNotEmpty(userCondition.getAttributeMatch().entrySet())) {
				for (Map.Entry<String, String> entry : userCondition.getAttributeMatch().entrySet()) {
					list.addAll(getUumService().searchUserByAttribute(entry.getKey(),
							entry.getValue()));
				}
			}
			if (StringUtils.isNotBlank(userCondition.getIdLike())
					|| StringUtils.isNotBlank(userCondition.getNameLike())) {
				Condition c = new Condition();
				c.setUserId(userCondition.getIdLike());
				c.setUserName(userCondition.getNameLike());
				list.addAll(getUumService().searchUsersByCondition(1, -1, c).getList());
			}
		}
		return Response.status(Status.OK).entity(buildObject(list)).build();
	}

	/**
	 * 方法说明：getGroups
	 * 
	 * @param userUUID
	 *            userUUID
	 * @return Response
	 */
	@Path("/{userUUID}/duty")
	@GET
	public Response getDuty(@PathParam("userUUID") String userUUID)
	{

		if (StringUtils.isBlank(userUUID)) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		User user = getUumService().getUserByUuid(userUUID);

		if (user == null) {
			return Response.status(Status.NOT_FOUND).build();
		}

		List<Duty> duty = getDutyService().getDuty(user);

		return Response.status(Status.OK).entity(buildObject(duty)).build();

	}


}
