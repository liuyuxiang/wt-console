package com.wt.uum2.rest;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wt.uum.device.AuthStatus;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.DeviceService;

/**
 * <pre>
 * 业务名: 设备信息
 * 功能说明: 设备信息rest
 * 编写日期:	2011-11-1
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Path("/dev")
public class DeviceResource
{

	/**
	 * log
	 */
	private static final Log LOGGER = LogFactory.getLog(DeviceResource.class);

	/**
	 * UserResource
	 */
	private UserResource userResource;

	/**
	 * 设备服务接口
	 */
	private DeviceService deviceService;

	/**
	 * 方法说明： 根据用户ID和密码进行验证，通过之后返回验证码，并将设备信息持久化
	 * 
	 * @param userId
	 *            userId
	 * @param pwd
	 *            密码
	 * @param name
	 *            设备名
	 * @param version
	 *            版本信息
	 * @param deviceId
	 *            deviceId
	 * @return 验证码，如果用户名密码验证错误返回null
	 */
	@Path("/reg")
	@POST
	public Response getAuthCode(@QueryParam("uid") String userId, @QueryParam("pwd") String pwd,
			@QueryParam("devname") String name, @QueryParam("devver") String version,
			@QueryParam("devid") String deviceId)
	{

		if (StringUtils.isEmpty(deviceId)) {
			LOGGER.debug("Argument 'devid' is null");
			return Response.status(Status.PRECONDITION_FAILED).entity("Argument 'devid' is null")
					.build();
		}

		if (deviceId.length() > 120) {
			LOGGER.debug("Argument 'devid' is over length");
			return Response.status(Status.PRECONDITION_FAILED)
					.entity("Argument 'devid' is over length").build();
		}

		if (name != null && name.length() > 120) {
			LOGGER.debug("Argument 'devname' is over length");
			return Response.status(Status.PRECONDITION_FAILED)
					.entity("Argument 'devname' is over length").build();
		}

		if (version != null && version.length() > 120) {
			LOGGER.debug("Argument 'devver' is over length");
			return Response.status(Status.PRECONDITION_FAILED)
					.entity("Argument 'devver' is over length").build();
		}

		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(pwd)) {
			LOGGER.debug("Empty uid or pwd");

			return Response.status(Status.BAD_REQUEST).entity("Verify userId and password failed")
					.build();
		}

		User u = new User();
		
		try {
			u = (User) getUserResource().getByID(userId).getEntity();
		} catch (Exception e) {
		} finally {
			if(u.getUuid()==null){
				LOGGER.debug("User is not exists");
				return Response.status(Status.NOT_FOUND).entity("User has not exists, verify failed")
						.build();
			}
		}

		if (getUserResource().verifyPwd(u.getUuid(), pwd)) {
			LOGGER.debug("Verfy userId and password successfull");

			String code = getDeviceService().getAuthCode(userId, name, version, deviceId);

			return Response.status(Status.OK).entity(code).build();

		} else {
			LOGGER.debug("Verify userId and password failed");

			return Response.status(Status.BAD_REQUEST).entity("Verify userId and password failed")
					.build();
		}

	}

	/**
	 * 方法说明：authenticateDevice
	 * 
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param authCode
	 *            authCode
	 * @return Response
	 */
	@Path("/auth")
	@POST
	public Response authenticateDevice(@QueryParam("uid") final String userId,
			@QueryParam("devid") final String deviceId,
			@QueryParam("authcode") final String authCode)
	{

		AuthStatus status = getDeviceService().auth(userId, deviceId, authCode);

		if (status != null && status.equals(AuthStatus.OK)) {
			return Response.status(Status.OK).build();
		}

		return Response.status(Status.NO_CONTENT).build();

	}

	/**
	 * 方法说明：removeDev
	 * 
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param authCode
	 *            authCode
	 */
	@Path("/unreg")
	@DELETE
	public void removeDev(@QueryParam("uid") final String userId,
			@QueryParam("devid") final String deviceId,
			@QueryParam("authcode") final String authCode)
	{

	}

	/**
	 * 方法说明：unRegisterDevWithPassword
	 * 
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param authCode
	 *            authCode
	 */
	@Path("/unregpwd")
	@DELETE
	public void unRegisterDevWithPassword(@QueryParam("uid") final String userId,
			@QueryParam("devid") final String deviceId, @QueryParam("pwd") final String authCode)
	{

	}

	public UserResource getUserResource()
	{
		return userResource;
	}

	public void setUserResource(UserResource userResource)
	{
		this.userResource = userResource;
	}

	public DeviceService getDeviceService()
	{
		return deviceService;
	}

	public void setDeviceService(DeviceService deviceService)
	{
		this.deviceService = deviceService;
	}

}
