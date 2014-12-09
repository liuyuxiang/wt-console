package com.wt.uum.shiro.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;

import com.wt.uum.shiro.authc.DeviceToken;

/**
 * <pre>
 * 业务名: 设备验证的filter
 * 功能说明:  设备验证的filter
 * 编写日期:	2011-11-25
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceFilter extends AuthenticationFilter
{

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
			throws Exception
	{

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;

		if (isDeviceRequest(request)) {

			String devId = httpServletRequest.getHeader("devid");
			String userId = httpServletRequest.getHeader("uid");
			String authCode = httpServletRequest.getHeader("authcode");

			DeviceToken deviceToken = new DeviceToken(devId, userId, authCode);
			return authDevice(deviceToken, request, response);

		}

		return true;
	}

	/**
	 * 方法说明：authDevice
	 * 
	 * @param deviceToken
	 *            deviceToken
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return boolean
	 */
	private boolean authDevice(DeviceToken deviceToken, ServletRequest request,
			ServletResponse response)
	{
		try {
			Subject subject = getSubject(request, response);
			subject.login(deviceToken);
			return true;
		} catch (AuthenticationException e) {
			return true;
		}
	}

	/**
	 * 方法说明：isDeviceRequest
	 * 
	 * @param request
	 *            request
	 * @return boolean
	 */
	private boolean isDeviceRequest(ServletRequest request)
	{
		if (request != null) {
			HttpServletRequest req = (HttpServletRequest) request;
			if (req.getHeader("devid") != null) {
				return true;
			}
		}
		return false;
	}

}
