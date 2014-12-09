package com.wt.uum2.dao;

import java.util.List;
import java.util.Map;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceSyn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

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
public interface UserDao extends BaseDao<User>
{

	/**
	 * 方法说明：通过用户编码获得用户对象
	 * 
	 * @param id
	 *            id
	 * @return User
	 */
	public User readById(String id);

	/**
	 * 方法说明：获得所有用户集合
	 * 
	 * @return List
	 */
	public List<User> getAllUsers();

	/**
	 * 方法说明：通过部门唯一标识获得该部门下的用户集合
	 * 
	 * @param departmentUUID
	 *            departmentUUID
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	@Deprecated
	public List<User> readUsersByDepartmentUUID(String departmentUUID, Integer start, Integer size);

	/**
	 * 方法说明：通过组的唯一标识获得该组下的所有用户集合
	 * 
	 * @param groupUUID
	 *            groupUUID
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	public List<User> getUsersByGroupUUID(String groupUUID, Integer start, Integer size);

	/**
	 * 方法说明：通过用户名搜索用户集合
	 * 
	 * @param key
	 *            key
	 * @return List
	 */
	@Deprecated
	public List<User> searchUserByKey(String key);


	/**
	 * 方法说明：通过组的唯一标识集合获得该集合下的所有被管理用户集合
	 * 
	 * @param uuids
	 *            uuids
	 * @return List
	 */
	@Deprecated
	public List<User> getAdmUserByGroupUuid(List<String> uuids);

	/**
	 * 方法说明：通过条件搜索用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchUserByCondition(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：通过父部门得到部门下的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return getUserMembersByDepartment
	 */
	public UserPageResult getUserMembersByDepartment(int page, int pagesize, Department department);

	/**
	 * 方法说明：通过部门集合获得集合下的所有用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return 集合下的所有用户
	 */
	public UserPageResult getUserMembersByDepartments(int page, int pagesize,
			List<Department> department);

	/**
	 * 方法说明：获得应用系统管理的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return 用系统管理的用户
	 */
	public UserPageResult getManagerUnderApplication(int page, int pagesize, Application application);

	/**
	 * 方法说明：获得组下的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return 组下的用户
	 */
	public UserPageResult getUsersUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：获得不在组下的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return 组下的用户
	 */
	public UserPageResult getUsersNotUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：获得组下的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return 组下的用户
	 */
	public UserPageResult getUsersUnderGroupOrderByDept(int page, int pagesize, Group group);

	/**
	 * 方法说明：带权限得到组下面的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param userAppGroups
	 *            userAppGroups
	 * @return 组下面的用户
	 */
	public UserPageResult getUsersUnderGroupAndLoginUser(int page, int pagesize, Group group,
			User user, List<Group> userAppGroups);

	/**
	 * 方法说明：搜索组下面的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return 组下面的用户
	 */
	public UserPageResult searchUserUnderGroup(int page, int pagesize, Condition condition,
			Group group);

	/**
	 * 方法说明：把用户从某个组下面移除
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	public int removeUserUnderGroup(Group group, User user);

	/**
	 * 方法说明：把某个用户加到某个组下面
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	public int addUserUnderGroup(Group group, User user);

	/**
	 * 方法说明：搜索应用组下面的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return 应用组下面的用户
	 */
	public UserPageResult searchUserUnderApplicationGroup(int page, int pagesize,
			Condition condition, Group group);

	/**
	 * 方法说明：获得组所管理的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return 组所管理的用户
	 */
	public UserPageResult getUserManagedUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：获得组下面逻辑删除的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return 组下面逻辑删除的用户
	 */
	public UserPageResult getLogicDeleteUsersUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：搜索组下面逻辑删除的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return 组下面逻辑删除的用户
	 */
	public UserPageResult searchLogicDeleteUsersUnderGroup(int page, int pagesize,
			Condition condition, Group group);

	/**
	 * 方法说明：搜索管理组下面的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param admGroups
	 *            admGroups
	 * @param isInSuperGroup
	 *            isInSuperGroup
	 * @return 管理组下面的用户
	 */
	public UserPageResult searchUsersManagedByAdmGroups(int page, int pagesize,
			Condition condition, List<Group> admGroups, boolean isInSuperGroup);

	/**
	 * 方法说明：得到部门下逻辑删除的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param departments
	 *            departments
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderDepartment(int page, int pagesize,
			List<Department> departments);

	/**
	 * 方法说明：搜索部门下逻辑删除的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Condition condition, Department department);

	/**
	 * 方法说明：得到应用系统下正常用户分页（带应用系统）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, List<Group> appGroups);

	/**
	 * 方法说明：得到应用系统下正常用户分页（部门为约束条件）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Department department);

	/**
	 * 方法说明：得到应用系统下面已经过滤的用户分页（带应用系统）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, List<Group> appGroups);

	/**
	 * 方法说明：得到应用系统下面已经过滤的用户分页（部门为约束条件）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param org
	 *            org
	 * @return UserPageResult
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Department org);

	/**
	 * 方法说明：搜索应用系统下正常用户分页（带应用系统）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, List<Group> appGroups);

	/**
	 * 方法说明：搜索应用系统下正常用户分页（部门为约束条件）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param org
	 *            org
	 * @return UserPageResult
	 */
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, Department org);

	/**
	 * 方法说明：搜索应用系统下被过滤的用户分页（带应用系统）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, List<Group> appGroups);

	/**
	 * 方法说明：搜索应用系统下被过滤的用户分页（部门为约束条件）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param org
	 *            org
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition, Department org);

	/**
	 * 方法说明：得到应用系统下面正常的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user);

	/**
	 * 方法说明：得到应用系统下面被过滤的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user);

	/**
	 * 方法说明：搜素应用系统下正常的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition);

	/**
	 * 方法说明：搜索应用系统下被过滤的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition);

	/**
	 * 方法说明：得到需要被组审核的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param groups
	 *            groups
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedUsersManagedUnderGroups(int page, int pagesize,
			List<Group> groups);

	/**
	 * 方法说明：带权限搜索应用组下的用户分页
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param loginUser
	 *            loginUser
	 * @param group
	 *            group
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group group, List<Group> appGroups);

	/**
	 * 方法说明：列出应用系统中预授权的所有用户
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param loginUser
	 *            loginUser
	 * @param appGroup
	 *            appGroup
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult getReAuthorUserMembersByLoginUser(int size, int pagesize, User loginUser,
			Group appGroup, List<Group> appGroups);

	/**
	 * 方法说明：搜索应用系统中预授权的所有用户
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param loginUser
	 *            loginUser
	 * @param appgroup
	 *            appgroup
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchReAuthorUserMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group appgroup, List<Group> appGroups);

	/**
	 * 方法说明：带权限搜索应用组下的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @param appGroups
	 *            appGroups
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndLoginUser(int page, int pagesize,
			Condition condition, User user, List<Group> appGroups);

	/**
	 * 方法说明：根据用户来源搜索用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult searchUsersByConditionAndUserCameFrom(int page, int pagesize,
			Condition condition, String type);

	/**
	 * 方法说明：得到所有逻辑删除的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsers(int page, int pagesize);

	/**
	 * 方法说明：搜索逻辑删除的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsers(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：得到用户关联的用户集合
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<User> getRelationUsers(User user);

	/**
	 * 方法说明：得到用户需要同步的属性和值
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<ResourceSyn> getUserResourceSyn(User user);

	/**
	 * 方法说明：通过用户名获得用户集合
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<User> getUsersByName(String name);

	/**
	 * 方法说明：清除缓存
	 * 
	 * @return int
	 */
	@Deprecated
	public int clearSession();

	/**
	 * 方法说明：获取登录用户可管理的离职人员的列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult getLeftUsersByLoginUser(int page, int pagesize, User user);

	/**
	 * 方法说明：根据条件搜索登录用户可管理的离职人员的列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Deprecated
	public UserPageResult searchLeftUsersByLoginUser(int page, int pagesize, Condition condition,
			User user);

	/**
	 * 
	 * 方法说明：取得用户扩展属性(map类型)
	 * 
	 * @param resource
	 *            资源对象
	 * @param keys
	 *            key集合
	 * @return Map
	 */
	public Map<String, String> getUserResourceSyn(Resource resource, List<String> keys);

	/**
	 * 方法说明：得到部门下逻辑删除的用户分页
	 * 
	 * @param i
	 *            i
	 * @param j
	 *            j
	 * @param childrenDepartment
	 *            childrenDepartment
	 * @return UserPageResult
	 */
	public UserPageResult getMoveUsersUnderDepartment(int i, int j,
			List<Department> childrenDepartment);

	/**
	 * 方法说明：取得用户的部门列表
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getUserDepartments(User user);

	/**
	 * 方法说明：取得用户权限组列表
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserGroups(User user);

	/**
	 * 方法说明：根据用户ERP code search
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndERPCode(int page, int pagesize,
			Condition condition);

	/**
	 * 方法说明：根据用户ERP code search
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param orgCode
	 *            orgCode
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndERPCodeAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode);

	/**
	 * 方法说明：获取部门下所有的人员，包括正常的和逻辑删除的用户
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getAllUsersUnderDepartment(Department department);

	/**
	 * 方法说明：获取部门下面的人员
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getUsersByDepartment(Department department);

	/**
	 * 方法说明：获取输入部门下面的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult getUsersByDepartments(int page, int pagesize, List<Department> dept);

	/**
	 * 方法说明：搜索登陆用户所在单位的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param orgCode
	 *            orgCode
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode);

	/**
	 * 方法说明：取得应用系统下用户列表,如果
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderApplicationByOrg(int page, int pagesize, Department dept,
			Group group);

	/**
	 * 
	 * 方法说明：取得可管理用户的组
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getUserManagedGroups(User resource);

	/**
	 * 方法说明：取得用户所有管理组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserAdminGroup(User user);

	/**
	 * 
	 * 方法说明：取得所有正常用户列表
	 * 
	 * @return List
	 */
	public List<User> getAllNormalUsers();

	/**
	 * 方法说明：根据用户资源来取对应的属性
	 * 
	 * @param resource
	 *            resource
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResource(Resource resource);

	/**
	 * 
	 * 方法说明：北供专用取erp最大值
	 * 
	 * @param typeid
	 *            typeid
	 * @return String
	 */
	@Deprecated
	public String countNum(String typeid);

	/**
	 * 方法说明：通过部门取得所有的逻辑删除用户(包括下级部门的所有)
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Department department);

	/**
	 * 方法说明：取得所有下级部门的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getUserMembersUnderDepartment(int page, int pagesize,
			Department department);

	/**
	 * 
	 * 方法说明：通过erp编码取得用户列表
	 * 
	 * @param userCode
	 *            userCode
	 * @return List
	 */
	@Deprecated
	public List<User> searchUsersByERPCode(String userCode);

	/**
	 * 
	 * 方法说明：通过扩展属性取得用户列表
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<User> getUserByAttribute(String attrName, String attrValue);

	/**
	 * 方法说明：通过多个扩展属性取得用户列表
	 * 
	 * @param attList
	 *            attList
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<User> getUserByAttributes(List<AttributeType> attList, String attrValue);

	/**
	 * 搜索符合部门条件的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dc
	 *            部门具体条件
	 * @param loginUser
	 *            可以为空
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUsersByDepartmentCondition(Integer page, Integer pagesize,
			DepartmentCondition dc, User loginUser);

	/**
	 * 方法说明：根据用户状态取得用户分页信息
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult getUserListByStatus(int page, int pagesize, User user, ResourceStatus rs);

	/**
	 * 方法说明：搜索符合条件和用户状态的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @param condition
	 *            condition
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	public UserPageResult searchUserListByStatus(int page, int pagesize, User user,
			Condition condition, ResourceStatus rs);

	/**
	 * 方法说明：移除用户的关联用户
	 * 
	 * @param user
	 *            user
	 */
	public void removePrimaryUser(User user);

	/**
	 * 方法说明：得到需要被审核的用户分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedUsers(int page, int pagesize);

	// Added by Guo Tianci, -start 2011 -08 -08 --start

	/**
	 * 方法说明：获得用户数量
	 * 
	 * @return long
	 */
	public long getUserCount();

	// --end

	// Added by Guo Tianci, -start 2011 -08 -12 --start
	/**
	 * 查询部门下子部门的数量
	 * 
	 * @param dept
	 *            部门
	 * @return 子部门数量
	 */
	public long getSubDeptCount(Department dept);

	// --end

	// Added by Guo Tianci, -start 2011 -08 -17 --start
	/**
	 * 根据用户状态取得用户分页信息
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            每页数量
	 * @param user
	 *            User
	 * @param rs
	 *            人员状态
	 * @return UserPageResult
	 */
	public UserPageResult getUserListByStatuses(int page, int pagesize, User user,
			ResourceStatus... rs);

	// --end

	/**
	 * 方法说明：搜索某部门下预授权应用系统的用户列表,如果org为空,则全局搜索
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dept
	 *            dept
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getReAuthorUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Department dept, Group group);

	/**
	 * 方法说明：搜索应用系统下用户列表,如果org为空,则全局搜索
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param org
	 *            org
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Condition condition, Department org, Group group);

	/**
	 * 方法说明：搜索符合部门条件的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dc
	 *            部门具体条件
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUsersByDepartmentConditionAndOrgCode(Integer page,
			Integer pagesize, DepartmentCondition dc, Department dept);

	/**
	 * 方法说明：取得一个resource对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return Resource
	 */
	public Resource getResource(String uuid);

	/**
	 * 方法说明：获取用户数量
	 * 
	 * @return int
	 */
	public int getNumberOfUsers();

	/**
	 * 方法说明：通过条件搜索用户分页
	 * 
	 * @param condition
	 *            condition
	 * @return List
	 */
	public List<User> searchUserByCondition(Condition condition);

}