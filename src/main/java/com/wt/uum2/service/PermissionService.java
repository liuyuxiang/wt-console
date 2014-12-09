package com.wt.uum2.service;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-26
 * 作者:	Liu yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PermissionService
{

	/**
	 * 方法说明：是否超级管理员
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isSuperAdmin(User user);

	/**
	 * 方法说明：是否管理员(目前重庆使用中)
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isManager(User user);

	/**
	 * 方法说明：是否是部门管理员
	 * 
	 * @param user
	 *            user
	 * @param dept
	 *            dept
	 * @return boolean
	 */
	public boolean isDepartmentManager(User user, Department dept);

	/**
	 * 方法说明：是否是某资源的管理员
	 * 
	 * @param user
	 *            user
	 * @param resource
	 *            resource
	 * @return boolean
	 */
	public boolean isManager(User user, Resource resource);

}
