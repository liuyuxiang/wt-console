package com.wt.uum2.quartz;

import java.util.List;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTempLog;

/**
 * <pre>
 * 业务名:反向同步接口
 * 功能说明: 定义基本方法
 * 编写日期:	2012-12-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface SyncListener
{
	/**
	 * 方法说明：入口
	 * 
	 */
	public void handle();

	/**
	 * 方法说明：同步部门
	 * 
	 */
	public void DepartmentSyncFrom();

	/**
	 * 方法说明：同步用户
	 * 
	 */
	public void UserSyncFrom();

	/**
	 * 方法说明：同步指定部门
	 * 
	 * @param depts
	 *            指定部门
	 */
	public void DepartmentSyncFrom(List<DepartmentTempLog> depts);

	/**
	 * 方法说明：同步指定用户
	 * 
	 * @param users
	 *            指定用户
	 */
	public void UserSyncFrom(List<UserTempLog> users);

	/**
	 * 方法说明：创建部门事件
	 * 
	 * @param deptTemp
	 *            部门信息
	 * @return Department
	 */
	public Department createDepartmentHandle(DepartmentTempLog deptTemp);

	/**
	 * 方法说明：更新部门事件
	 * 
	 * @param deptTemp
	 *            部门信息
	 * @return Department
	 */
	public Department updateDepartmentHandle(DepartmentTempLog deptTemp);

	/**
	 * 方法说明：删除部门事件
	 * 
	 * @param deptTemp
	 *            部门信息
	 * @return Department
	 */
	public Department deleteDepartmentHandle(DepartmentTempLog deptTemp);

	/**
	 * 方法说明：创建用户事件
	 * 
	 * @param userTemp
	 *            用户信息
	 * @return User
	 */
	public User createUserHandle(UserTempLog userTemp);

	/**
	 * 方法说明：更新用户事件
	 * 
	 * @param userTemp
	 *            用户信息
	 * @return User
	 */
	public User updateUserHandle(UserTempLog userTemp);

	/**
	 * 方法说明：删除用户事件
	 * 
	 * @param userTemp
	 *            用户信息
	 * @return User
	 */
	public User deleteUserHandle(UserTempLog userTemp);

	/**
	 * 方法说明：忽略用户信息
	 * 
	 * @param userTemp
	 *            用户信息
	 */
	public void ignoreUserTemp(UserTempLog userTemp);

	/**
	 * 方法说明：忽略部门信息
	 * 
	 * @param deptTemp
	 *            部门信息
	 */
	public void ignoreDepartmentTemp(DepartmentTempLog deptTemp);

	/**
	 * 方法说明：忽略用户信息
	 * 
	 * @param users
	 *            指定用户
	 */
	public void ignoreUserTemp(List<UserTempLog> users);

	/**
	 * 方法说明：忽略部门信息
	 * 
	 * @param depts
	 *            指定部门
	 */
	public void ignoreDepartmentTemp(List<DepartmentTempLog> depts);

}
