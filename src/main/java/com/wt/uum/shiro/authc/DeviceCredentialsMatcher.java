package com.wt.uum.shiro.authc;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

/**
 * <pre>
 * 业务名: 设备验证的CredentialsMatcher
 * 功能说明: 验证设备信息
 * 编写日期:	2011-11-25
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceCredentialsMatcher implements CredentialsMatcher
{

	/* (non-Javadoc)
	 * @see org.apache.shiro.authc.credential.CredentialsMatcher#doCredentialsMatch(org.apache.shiro.authc.AuthenticationToken, org.apache.shiro.authc.AuthenticationInfo)
	 */
	/**
	 * 方法说明：doCredentialsMatch
	 * 
	 * @param token
	 *            token
	 * @param info
	 *            info
	 * @return boolean
	 */
	public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info)
	{

		if (token != null && info != null) {
			Object credentials = token.getCredentials();
			if (credentials != null && credentials.equals(info.getCredentials())) {
				return true;
			}

		}

		return false;
	}

}
