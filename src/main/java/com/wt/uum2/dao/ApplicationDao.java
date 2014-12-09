package com.wt.uum2.dao;

import java.util.List;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

import nak.nsf.dao.support.BaseDao;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ApplicationDao extends BaseDao<Application>
{

	/**
	 * 方法说明：获得所有应用系统
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getAllApplications(int page, int pagesize);

	/**
	 * 方法说明：通过关键字获得应用系统
	 * 
	 * @param uuid
	 *            uuid
	 * @return Application
	 */
	public Application getApplicationByUuid(String uuid);

	/**
	 * 方法说明：通过组获得应用系统
	 * 
	 * @param group
	 *            group
	 * @return Application
	 */
	public Application getApplicationsUnderGroup(Group group);

	/**
	 * 方法说明：获得在组所管理的应用系统
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Application> getApplicationsManagedUnderGroup(List<Group> group);

	/**
	 * 
	 * 方法说明： 取得应用下的用户（带分页信息）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<UserApplication> getUsersUnderApplication(Integer page, Integer pagesize,
			Application application);

	/**
	 * 
	 * 方法说明：根据appid取得相应的应用系统
	 * 
	 * @param appId
	 *            应用系统id
	 * @return 应用系统对象
	 */
	public Application getApplicationByAppId(String appId);

	/**
	 * 方法说明：根据应用系统来取得用户信息
	 * 
	 * @param page
	 *            当前页
	 * @param pagesize
	 *            页面
	 * @param condition
	 *            搜索内容
	 * @param app
	 *            应用系统对象
	 * @return 返回一个object[]
	 */
	public UserPageResult<Object> searchUserByApplication(Integer page, Integer pagesize,
			Condition condition, Application app);

	/**
	 * 方法说明：根据应用系统来取得用户信息
	 * 
	 * @param page
	 *            当前页
	 * @param pagesize
	 *            页面
	 * @param condition
	 *            搜索内容
	 * @param app
	 *            应用系统对象
	 * @return 返回一个object[]
	 */
	public UserPageResult<UserApplication> searchUserUnderApplication(Integer page,
			Integer pagesize, Condition condition, Application app);

	/**
	 * 方法说明：根据应用系统来取得用户信息
	 * 
	 * @param page
	 *            当前页
	 * @param pagesize
	 *            页面
	 * @param condition
	 *            搜索内容
	 * @param app
	 *            应用系统对象
	 * @return 返回一个object[]
	 */
	public UserPageResult<User> searchUserNotUnderApplication(Integer page, Integer pagesize,
			Condition condition, Application app);

	/**
	 * 
	 * 方法说明： 取得非应用下的用户（带分页信息）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<User> getUsersNotUnderApplication(Integer page, Integer pagesize,
			Application application);
}