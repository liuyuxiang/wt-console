package com.wt.uum2.dao.impl;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.AuthenticationProfileDao;
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
public class AuthenticationProfileDaoImpl extends BaseDaoSupport<AuthenticationProfile> implements
		AuthenticationProfileDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AuthenticationProfileDao#getAuthenticationProfileByAppId(java.lang.String)
	 */
	/**
	 * 方法说明：getAuthenticationProfileByAppId
	 * 
	 * @param appId
	 *            appId
	 * @return AuthenticationProfile
	 */
	public AuthenticationProfile getAuthenticationProfileByAppId(String appId)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AuthenticationProfileDao#getAuthenticationProfileByApp(com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getAuthenticationProfileByApp
	 * 
	 * @param ap
	 *            ap
	 * @return AuthenticationProfile
	 */
	public AuthenticationProfile getAuthenticationProfileByApp(Application ap)
	{
		// TODO Auto-generated method stub
		return (AuthenticationProfile) getSession().createCriteria(AuthenticationProfile.class)
				.add(Restrictions.eq("applicationUUID", ap.getUuid())).uniqueResult();
	}

}