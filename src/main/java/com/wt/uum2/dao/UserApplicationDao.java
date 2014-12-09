package com.wt.uum2.dao;

import java.util.Collection;
import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

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
public interface UserApplicationDao extends BaseDao<UserApplication>
{

	/**
	 * 方法说明：删除用户应用系统映射
	 * 
	 * @param user
	 *            user
	 * @param application
	 *            application
	 */
	public void delete(User user, Collection<Application> application);

	/**
	 * 方法说明：添加用户应用系统信息
	 * 
	 * @param app
	 *            app
	 */
	public void addUserApplication(UserApplication app);

	/**
	 * 方法说明：删除用户应用系统信息
	 * 
	 * @param app
	 *            app
	 */
	public void removeUserApplication(UserApplication app);

	/**
	 * 
	 * 方法说明：通过应用系统和用户取得映射关系
	 * 
	 * @param a
	 *            应用参数
	 * @param u
	 *            u
	 * @return UserApplication
	 */
	public UserApplication getUserApplication(Application a, User u);

	/**
	 * 
	 * 方法说明：通过应用系统取得用户映射列表
	 * 
	 * @param app
	 *            app
	 * @return List
	 */
	public List<UserApplication> getUserApplication(Application app);

}
