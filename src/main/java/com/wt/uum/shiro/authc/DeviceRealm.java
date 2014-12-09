package com.wt.uum.shiro.authc;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.realm.AuthenticatingRealm;

import com.wt.uum2.domain.DeviceInfo;
import com.wt.uum2.service.DeviceService;

/**
 * <pre>
 * 业务名: DeviceRealm
 * 功能说明: Device 信息 的realm
 * 编写日期:	2011-11-25
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceRealm extends AuthenticatingRealm
{

	/**
	 * DeviceService
	 */
	private DeviceService deviceService;

	public void setDeviceService(DeviceService deviceService)
	{
		this.deviceService = deviceService;
	}

	public String getName()
	{
		return "Device-Realm";
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.AuthenticationToken)
	 */
	/**
	 * 方法说明：supports
	 * 
	 * @param token
	 *            token
	 * @return boolean
	 */
	public boolean supports(AuthenticationToken token)
	{

		if (token != null && token instanceof DeviceToken) {
			return true;
		}

		return false;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
	{

		if (token != null) {
			DeviceToken devToken = (DeviceToken) token;
			DeviceInfo deviceInfo = deviceService.getByDeviceIdAndUserId(devToken.getDevId(),
					devToken.getUserId());

			if (deviceInfo != null) {

				List<String> principals = new ArrayList<String>();
				principals.add(deviceInfo.getDeviceId());
				principals.add(deviceInfo.getUserId());

				return new SimpleAuthenticationInfo(principals, deviceInfo.getAuthCode(), getName());
			}

		}

		return null;

		// return new SimpleAuthenticationInfo("devid", "code", getName());
	}

}
