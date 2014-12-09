package com.wt.uum2.service.impl;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.dao.ApplicationDao;
import com.wt.uum2.dao.AuthenticationProfileDao;
import com.wt.uum2.dao.UserApplicationDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;
import com.wt.uum2.service.UUMAppService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class UUMAppServiceImpl implements UUMAppService
{

	/**
	 * 
	 */
	private ApplicationDao applicationDao;

	/**
	 * 
	 */
	private AuthenticationProfileDao authenticationProfileDao;

	/**
	 * 
	 */
	private UserApplicationDao userApplicationDao;

	/**
	 * @param applicationDao
	 *            the applicationDao to set
	 */
	public void setApplicationDao(ApplicationDao applicationDao)
	{
		this.applicationDao = applicationDao;
	}

	/**
	 * @param userApplicationDao
	 *            the userApplicationDao to set
	 */
	public void setUserApplicationDao(UserApplicationDao userApplicationDao)
	{
		this.userApplicationDao = userApplicationDao;
	}

	/**
	 * @param authenticationProfileDao
	 *            the authenticationProfileDao to set
	 */
	public void setAuthenticationProfileDao(AuthenticationProfileDao authenticationProfileDao)
	{
		this.authenticationProfileDao = authenticationProfileDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#addUserToApplication(com.wt.uum2.domain.User, com.wt.uum2.domain.Application, java.lang.Boolean, java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：addUserToApplication
	 * 
	 * @param u
	 *            u
	 * @param app
	 *            app
	 * @param loginable
	 *            loginable
	 * @param mappendUserid
	 *            mappendUserid
	 * @param mappendPassword
	 *            mappendPassword
	 * @param remark
	 *            remark
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addUserToApplication(User u, Application app, Boolean loginable,
			String mappendUserid, String mappendPassword, String remark)
	{
		this.saveOrUpdateUserApplication(app, u, mappendUserid, mappendPassword, loginable, remark);
		return 0;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#getAuthenticationProfile(com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getAuthenticationProfile
	 * 
	 * @param ap
	 *            ap
	 * @return AuthenticationProfile
	 */
	@Transactional(readOnly = true)
	public AuthenticationProfile getAuthenticationProfile(Application ap)
	{
		return authenticationProfileDao.getAuthenticationProfileByApp(ap);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#updateAuthenticationProfile(com.wt.uum2.domain.AuthenticationProfile)
	 */
	/**
	 * 方法说明：updateAuthenticationProfile
	 * 
	 * @param apo
	 *            apo
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateAuthenticationProfile(AuthenticationProfile apo)
	{
		authenticationProfileDao.saveOrUpdate(apo);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#getAuthenticationProfileByUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getAuthenticationProfileByUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return AuthenticationProfile
	 */
	@Transactional(readOnly = true)
	public AuthenticationProfile getAuthenticationProfileByUUID(String uuid)
	{
		return authenticationProfileDao.read(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#saveOrUpdateUserApplication(com.wt.uum2.domain.Application, com.wt.uum2.domain.User, java.lang.String, java.lang.String, java.lang.Boolean, java.lang.String)
	 */
	/**
	 * 方法说明：saveOrUpdateUserApplication
	 * 
	 * @param a
	 *            a
	 * @param u
	 *            u
	 * @param account
	 *            account
	 * @param pwd
	 *            pwd
	 * @param loginable
	 *            loginable
	 * @param remark
	 *            remark
	 */
	@Transactional(readOnly = false)
	public void saveOrUpdateUserApplication(Application a, User u, String account, String pwd,
			Boolean loginable, String remark)
	{
		UserApplication ua = userApplicationDao.getUserApplication(a, u);
		if (ua != null) {
			ua.setLoginable(BooleanUtils.isTrue(loginable));
			ua.setMappendUserid(account);
			ua.setMappendPassword(pwd);
			ua.setRemark(remark);
			ua.setLastmodifytime(Calendar.getInstance().getTime());
			userApplicationDao.update(ua);
		} else {
			ua = new UserApplication();
			ua.setApplication(a);
			ua.setLoginable(BooleanUtils.isTrue(loginable));
			ua.setMappendUserid(account);
			ua.setMappendPassword(pwd);
			ua.setRemark(remark);
			ua.setLastmodifytime(Calendar.getInstance().getTime());
			ua.setType("0");
			ua.setUser(u);
			userApplicationDao.save(ua);
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#getUserApplicationByApplication(com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getUserApplicationByApplication
	 * 
	 * @param app
	 *            app
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UserApplication> getUserApplicationByApplication(Application app)
	{
		return userApplicationDao.getUserApplication(app);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#deleteApplication(com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：deleteApplication
	 * 
	 * @param app
	 *            app
	 */
	@Transactional(readOnly = false)
	public void deleteApplication(Application app)
	{
		List<UserApplication> userList = getUserApplicationByApplication(app);
		if (CollectionUtils.isNotEmpty(userList)) {
			for (UserApplication userApplication : userList) {
				userApplicationDao.delete(userApplication);
			}
		}
		applicationDao.delete(app);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#getUserApplication(com.wt.uum2.domain.Application, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserApplication
	 * 
	 * @param app
	 *            app
	 * @param u
	 *            u
	 * @return UserApplication
	 */
	@Transactional(readOnly = true)
	public UserApplication getUserApplication(Application app, User u)
	{
		return userApplicationDao.getUserApplication(app, u);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMAppService#deleteUserApplication(com.wt.uum2.domain.UserApplication)
	 */
	/**
	 * 方法说明：deleteUserApplication
	 * 
	 * @param ua
	 *            ua
	 */
	@Transactional(readOnly = false)
	public void deleteUserApplication(UserApplication ua)
	{
		userApplicationDao.delete(ua);

	}

}