package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

/**
 * @author Alex
 * 
 */
public interface UUMAppService
{

	/**
	 * 
	 * 方法说明：添加用户应用系统映射
	 * 
	 * @param u
	 *            用户对象
	 * @param app
	 *            应用系统对象
	 * @param loginable
	 *            允许登录标识,true为允许登录,false为不允许登录
	 * @param mappendUserid
	 *            应用系统映射登录名
	 * @param mappendPassword
	 *            应用系统映射登录密码,明文
	 * @param remark
	 *            描述
	 * @return int
	 */
	public int addUserToApplication(User u, Application app, Boolean loginable,
			String mappendUserid, String mappendPassword, String remark);

	/**
	 * 
	 * 方法说明：通过profile的uuid取得profile对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return AuthenticationProfile
	 */
	public AuthenticationProfile getAuthenticationProfileByUUID(String uuid);

	/**
	 * 方法说明：取得应用系统的profile文件
	 * 
	 * @param ap
	 *            ap
	 * @return AuthenticationProfile
	 */
	public AuthenticationProfile getAuthenticationProfile(Application ap);

	/**
	 * 方法说明：更新应用系统profile
	 * 
	 * @param apo
	 *            apo
	 * @return int
	 */
	public int updateAuthenticationProfile(AuthenticationProfile apo);

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
	public void saveOrUpdateUserApplication(Application a, User u, String account, String pwd,
			Boolean loginable, String remark);

	/**
	 * 方法说明：通过应用系统,取得应用系统下所有用户映射
	 * 
	 * @param app
	 *            app
	 * @return List
	 */
	public List<UserApplication> getUserApplicationByApplication(Application app);

	/**
	 * 方法说明：删除应用系统
	 * 
	 * @param app
	 *            app
	 */
	public void deleteApplication(Application app);

	/**
	 * 方法说明：根据应用系统和用户取映射关系
	 * 
	 * @param app
	 *            app
	 * @param u
	 *            u
	 * @return UserApplication
	 */
	public UserApplication getUserApplication(Application app, User u);

	/**
	 * 方法说明：delete应用系统和用户的映射
	 * 
	 * @param ua
	 *            ua
	 */
	public void deleteUserApplication(UserApplication ua);

}