package com.wt.uum2.dao;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AuthenticationProfile;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface AuthenticationProfileDao extends BaseDao<AuthenticationProfile>
{

	/**
	 * 
	 * 方法说明：根据appid取得相应的应用系统
	 * 
	 * @param appId
	 *            应用系统id
	 * @return 应用系统对象
	 */
	public AuthenticationProfile getAuthenticationProfileByAppId(String appId);

	/**
	 * 方法说明：getAuthenticationProfileByApp
	 * 
	 * @param ap
	 *            ap
	 * @return AuthenticationProfile
	 */
	public AuthenticationProfile getAuthenticationProfileByApp(Application ap);

}