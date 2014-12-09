package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.ApplicationDao;
import com.wt.uum2.dao.AuthenticationProfileDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-3-22
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public abstract class ApplicationServiceImpl
{
	/**
	 * 
	 */
	protected ApplicationDao applicationDao;

	/**
	 * 
	 */
	private AuthenticationProfileDao authenticationProfileDao;

	public void setApplicationDao(ApplicationDao applicationDao)
	{
		this.applicationDao = applicationDao;
	}

	public void setAuthenticationProfileDao(AuthenticationProfileDao authenticationProfileDao)
	{
		this.authenticationProfileDao = authenticationProfileDao;
	}

	/**
	 * 方法说明：createApplication
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createApplication(Application application)
	{
		applicationDao.save(application);
		return 1;
	}

	/**
	 * 方法说明：deleteApplication
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteApplication(Application application)
	{
		applicationDao.delete(application);
		return 1;
	}

	/**
	 * 方法说明：getAllApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getAllApplication(int page, int pagesize)
	{
		return applicationDao.getAllApplications(page, pagesize);
	}

	/**
	 * 方法说明：UserPageResult
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateApplication(Application application)
	{
		applicationDao.update(application);
		return 1;
	}

	/**
	 * 方法说明：getApplicationByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Application
	 */
	@Transactional(readOnly = true)
	public Application getApplicationByUuid(String uuid)
	{
		return applicationDao.getApplicationByUuid(uuid);
	}

	/**
	 * 方法说明：getApplicationsUnderGroup
	 * 
	 * @param group
	 *            group
	 * @return Application
	 */
	@Transactional(readOnly = true)
	public Application getApplicationsUnderGroup(Group group)
	{
		return applicationDao.getApplicationsUnderGroup(group);
	}

	/**
	 * 方法说明：getApplicationsManagedUnderUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Application> getApplicationsManagedUnderUser(User user)
	{
		Set<Group> setGroup = user.getGroups();
		List<Group> list = new ArrayList<Group>();
		list.addAll(setGroup);
		return applicationDao.getApplicationsManagedUnderGroup(list);
	}

	/**
	 * 方法说明：getUsersUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<UserApplication> getUsersUnderApplication(Integer page, Integer pagesize,
			Application application)
	{
		return applicationDao.getUsersUnderApplication(page, pagesize, application);
	}

	/**
	 * 方法说明：getApplicationByAppId
	 * 
	 * @param appId
	 *            appId
	 * @return Application
	 */
	@Transactional(readOnly = true)
	public Application getApplicationByAppId(String appId)
	{
		return applicationDao.getApplicationByAppId(appId);
	}

	/**
	 * 方法说明：searchUserByApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<Object> searchUserByApplication(Integer page, Integer pagesize,
			Condition condition, Application app)
	{
		return applicationDao.searchUserByApplication(page, pagesize, condition, app);
	}

	/**
	 * 方法说明：searchUserUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<UserApplication> searchUserUnderApplication(Integer page,
			Integer pagesize, Condition condition, Application app)
	{
		return applicationDao.searchUserUnderApplication(page, pagesize, condition, app);
	}

	/**
	 * 方法说明：searchUserNotUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<User> searchUserNotUnderApplication(Integer page, Integer pagesize,
			Condition condition, Application app)
	{
		return applicationDao.searchUserNotUnderApplication(page, pagesize, condition, app);
	}

	/**
	 * 方法说明：getUsersNotUnderApplication
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<User> getUsersNotUnderApplication(Integer page, Integer pagesize,
			Application application)
	{
		return applicationDao.getUsersNotUnderApplication(page, pagesize, application);
	}

	/**
	 * 方法说明：createAuthenticationProfile
	 * 
	 * @param apo
	 *            apo
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createAuthenticationProfile(AuthenticationProfile apo)
	{
		authenticationProfileDao.save(apo);
		return 1;

	}

}
