package com.wt.hea.rbac.service;

import java.util.List;

import com.hirisun.hea.api.domain.Department;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 权限管理Service
 * 编写日期:	2011-3-21
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface RBACService {

	/**
	 * 
	 * 方法说明：
	 *	根据uuid获取部门信息
	 * @param uuid 部门uuid
	 * @return 部门信息
	 */
	Department getDepartmentByUUID(String uuid);

	/**
	 *  方法说明：得到子机构
	 * 
	 * @param parentUUID 父级部门uuid
	 * @return 部门列表
	 */
	List<Department> getDepartmentChildren(String parentUUID);

	/**
	 * 方法说明：得到部门的管理组
	 * 
	 * @param department 部门对象
	 * @return 该部门所能管理的组
	 */
	List<Group> getDepartmentManagedGroups(Department department);

	/**
	 * 方法说明：根据父组得到所有子组
	 * @param groupId 组id
	 * @return 该组下的所有子组
	 */
	List<Group> getGroupsByParentGroup(String groupId);

	/**
	 * 方法说明：通过UUID获取组
	 * 
	 * @param uuid 组uuid
	 * @return 组对象
	 */
	Group getGroupByUuid(String uuid);

	/**
	 * 方法说明：验证user id 是否唯一
	 * 
	 * @param userId 用户id
	 * @return 该用户是否存在
	 */
	boolean existUserId(String userId);

	/**
	 * 方法说明：通过 ID 获取用户
	 * 
	 * @param id 用户id
	 * @return user信息
	 */
	User getUserByUserId(String id);
	

	/**
	 * 方法说明：通过 UUID 获取用户
	 * 
	 * @param uuid 用户uuid
	 * @return 用户信息
	 */
	User getUserByUuid(String uuid);

	/**
	 * 方法说明：获取该用户所在的部门
	 * 
	 * @param user user对象
	 * @return 用户所在部门
	 */
	Department getUserDepartment(User user);
	
	/**
	 * 
	 * 方法说明：获取用户所在的单位编码
	 *
	 * @param user 用户对象
	 * @return 单位编码值
	 */
	public String getUserOrgCode(User user);
	

	/**
	 * 方法说明：获取用户所在组
	 * 
	 * @param user 用户对象
	 * @return 用户所在组列表
	 */
	List<Group> getUserGroups(User user);

	/**
	 * 方法说明：验证用户
	 * 
	 * @param id 用户id
	 * @param pwd  用户密码
	 * @return 用户名和密码是否匹配
	 */
	boolean userValidate(String id,String pwd);
	
	/**
	 * 
	 * 方法说明：获取所有用户组
	 *
	 * @return 所有用户组
	 */
	List<Group> getAllGroups();
	
	/**
	 * 
	 * 方法说明：判断用户是不是管理员
	 * @param user 用户对象
	 * @return 是否是管理员
	 */
	boolean isAdmin(User user);

	/**
	 * 
	 * 方法说明：获取组可管理的所有权限组
	 *
	 * @param groupId 组ID
	 * @return 该组可管理的组
	 */
	List<Group> getManagerGroup(String groupId);
	
	/**
	 * 
	 * 方法说明：获取组可管理的所有权限组
	 *
	 * @param groupIds 组IDs
	 * @return 该组可管理的组
	 */
	List<Group> getManagerGroups(String[] groupIds);
	
	/**
	 * 
	 * 方法说明：获取父级组
	 *
	 * @param groupId 组id
	 * @return 该组的父级组信息
	 */
	List<Group> getParentGroup(String groupId);
	
	/**
	 * 
	 * 方法说明：获取根级组
	 *
	 * @return 获取根级组
	 */
	public Group getRootGroup();
	
	/**
	 * 
	 * 方法说明：获取根级部门
	 *
	 * @return 获取根级部门
	 */
	public Department getRootDepartment();
	
	/**
	 * 方法说明：判断是否有下级组
	 * @param groupUuid 组uuid
	 * @return 是否有子组
	 */
	public boolean hasChildGroup(String groupUuid);
	
}
