package com.wt.uum2.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.ResourceSyn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.AuthenticationProfile;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.ResourceSync;
import com.wt.uum2.domain.ServerLog;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;
import com.wt.uum2.domain.UserAuthor;
import com.wt.uum2.domain.UseridMapping;

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
public interface UUMService
{

	/**
	 * 方法说明：按uuid取机构
	 * 
	 * @param uuid
	 *            uuid
	 * @return Department
	 */
	public Department getDepartmentByUUID(String uuid);

	/**
	 * 方法说明：得到子机构
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @return List
	 */
	public List<Department> getDepartmentChildren(String parentUUID);

	/**
	 * 方法说明：得到子机构(带权限)
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	public List<Department> getDepartmentChildren(String parentUUID, User loginUser);

	/**
	 * 方法说明：得到子机构(带权限)
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	public List<Department> getDepartmentChildren(String parentUUID, String loginUser);

	/**
	 * 方法说明：得到根机构
	 * 
	 * @return Department
	 */
	public Department getDepartmentRoot();

	/**
	 * 方法说明：在一个组中取得人员列表
	 * 
	 * @param group
	 *            group
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	public List<User> getUsersByGroup(Group group, Integer start, Integer size);

	/**
	 * 方法说明：获得所有的用户
	 * 
	 * @return List
	 */
	public List<User> getAllUsers();

	/**
	 * 方法说明：获得所有的组
	 * 
	 * @return List
	 */
	public List<Group> getAllGroups();

	/**
	 * 方法说明：获得所有的部门
	 * 
	 * @return List
	 */
	public List<Department> getAllDepartments();

	/**
	 * 方法说明：新建一个用户
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	public int createUser(User user);

	/**
	 * 方法说明：新建一个组
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	public int createGroup(Group group);

	/**
	 * 方法说明：新建一个部门
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	public int createDepartment(Department department);

	/**
	 * 方法说明：更新用户
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	public int updateUser(User user);

	/**
	 * 方法说明：更新组
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	public int updateGroup(Group group);

	/**
	 * 方法说明：更部门
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	public int updateDepartment(Department department);

	/**
	 * 方法说明：更部门path
	 * 
	 * @param newPath
	 *            newPath
	 * @param oldPath
	 *            oldPath
	 * @return int
	 */
	public int updateDeptPath(String newPath, String oldPath);

	/**
	 * 方法说明：删除一个用户
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	public int deleteUser(User user);

	/**
	 * 方法说明：删除一个组
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	public int deleteGroup(Group group);

	/**
	 * 方法说明：删除一个部门
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	public int deleteDepartment(Department department);

	/**
	 * 方法说明：通过组UUid删除一个组
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	@Deprecated
	public int deleteGroupByUuid(String uuid);

	/**
	 * 方法说明：通过部门UUID删除一个部门
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	public int deleteDepartmentByUuid(String uuid);

	/**
	 * 方法说明：通过用户UUId获得一个用户
	 * 
	 * @param uuid
	 *            uuid
	 * @return User
	 */
	public User getUserByUuid(String uuid);

	/**
	 * 方法说明：通过组UUID获得一个组
	 * 
	 * @param uuid
	 *            uuid
	 * @return Group
	 */
	public Group getGroupByUuid(String uuid);

	/**
	 * 方法说明：验证用户名和密码
	 * 
	 * @param name
	 *            name
	 * @param password
	 *            password
	 * @return boolean
	 */
	public boolean userLoginValidate(String name, String password);

	/**
	 * 方法说明：通过模糊查询条件搜索用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult<User> searchUsersByCondition(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：通过模糊查询条件搜索用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public List<User> searchUsersByCondition(Condition condition);

	/**
	 * 方法说明：通过部门信息获得分页的用户列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	public UserPageResult getUserMembersByDepartment(int page, int pagesize, Department department);

	/**
	 * 方法说明：通过用户和部门关联用户
	 * 
	 * @param departments
	 *            departments
	 * @param user
	 *            user
	 * @return int
	 */
	@Deprecated
	public int saveDepartmentsToUsers(Set<Department> departments, User user);

	/**
	 * 方法说明：把用户从一个部门内移除
	 * 
	 * @param department
	 *            department
	 * @param user
	 *            user
	 * @return int
	 */
	public int removeUserFromDepartment(Department department, User user);

	/**
	 * 方法说明：把一个部门移动到另外一个父机构下
	 * 
	 * @param childDepartment
	 *            childDepartment
	 * @param newParentDepartment
	 *            newParentDepartment
	 * @return int
	 */
	public int moveDepartmentToNewParent(Department childDepartment, Department newParentDepartment);

	/**
	 * 方法说明：得到部门节点下所有用户包括子节点
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
	 * 方法说明：验证userid是否是唯一
	 * 
	 * @param userId
	 *            userId
	 * @return boolean
	 */
	public boolean existUserId(String userId);

	/**
	 * 方法说明：验证部门Code是否唯一
	 * 
	 * @param parentDepartment
	 *            parentDepartment
	 * @param departmentCode
	 *            departmentCode
	 * @return boolean
	 */
	public boolean existDepartmentCode(Department parentDepartment, String departmentCode);

	/**
	 * 方法说明：判断部门和子部门下是否存在用户
	 * 
	 * @param department
	 *            department
	 * @return boolean
	 */
	public boolean existUserUnderDepartment(Department department);

	// Added by Guotianci 2011-08-10 --start

	/**
	 * 方法说明：判断部下是否存在用户，不包括子部门
	 * 
	 * @param department
	 *            department
	 * @return boolean
	 */
	public boolean existsUserIndepartment(Department department);

	// Added by Guotianci 2011-08-10 --start

	/**
	 * 方法说明：创建应用
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	public int createApplication(Application application);

	/**
	 * 方法说明：创建带属性的应用
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Deprecated
	public int createApplication(Application application, Attribute attribute);

	/**
	 * 方法说明：更新应用
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	public int updateApplication(Application application);

	/**
	 * 方法说明：更新带应用的应用
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Deprecated
	public int updateApplication(Application application, Attribute attribute);

	/**
	 * 方法说明：删除应用
	 * 
	 * @param application
	 *            application
	 * @return int
	 */
	@Deprecated
	public int deleteApplication(Application application);

	/**
	 * 方法说明：删除带属性的某个应用
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Deprecated
	public int deleteApplication(Application application, Attribute attribute);

	/**
	 * 方法说明：获取所有应用列表
	 * 
	 * @param page
	 *            pagepage
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<Application> getAllApplication(int page, int pagesize);

	/**
	 * 方法说明：获得某个应用的管理员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param application
	 *            application
	 * @return UserPageResult
	 */
	public UserPageResult<User> getManagerUnderApplication(int page, int pagesize,
			Application application);

	/**
	 * 方法说明：增加某个应用组的管理员
	 * 
	 * @param application
	 *            application
	 * @param user
	 *            user
	 * @return int
	 */
	public int setApplicationManager(Application application, User user);

	/**
	 * 方法说明：去除某个应用组的管理员
	 * 
	 * @param application
	 *            application
	 * @param user
	 *            user
	 * @return int
	 */
	public int removeApplicationManager(Application application, User user);

	/**
	 * 方法说明：通过UUID获得应用
	 * 
	 * @param uuid
	 *            uuid
	 * @return Application
	 */
	public Application getApplicationByUuid(String uuid);

	/**
	 * 方法说明：添加某人到应用的人员列表中
	 * 
	 * @param appGroup
	 *            appGroup
	 * @param user
	 *            user
	 * @return int
	 */
	public int setApplicationMember(Group appGroup, User user);

	/**
	 * 方法说明：移除应用下的某人
	 * 
	 * @param appGroup
	 *            appGroup
	 * @param user
	 *            user
	 * @return int
	 */
	public int removeApplicationMember(Group appGroup, User user);

	/**
	 * 方法说明：搜索组下的人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUserUnderGroup(int page, int pagesize, Condition condition,
			Group group);

	/**
	 * 方法说明：获得登陆用户管理的应用组的人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param appGroup
	 *            appGroup
	 * @return UserPageResult
	 */
	public UserPageResult getUsersUnderApplicationGroup(int page, int pagesize, Group appGroup);

	/**
	 * 方法说明：获得资源的属性类型
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByResource(Resource resource, Integer type,
			String catagory);

	/**
	 * 方法说明：获得资源的属性类型(页面展示)
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            资源类型，可为空
	 * @param catagory
	 *            资源类别，0-基本，1通信，2-职务，3-应用
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByResourceOnPage(Resource resource, Integer type,
			String catagory);

	/**
	 * 方法说明：获得资源的属性类型
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeAllByResource(Resource resource, Integer type);

	/**
	 * 方法说明：获得资源的应用系统下的属性类型
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            资源类型，可为空
	 * @param appGroup
	 *            应用组
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByResourceAndAppGroup(Resource resource,
			Integer type, Group appGroup);

	/**
	 * 方法说明：把指定用户加到部门下
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return int
	 */
	public int addUserToDepartment(User user, Department department);

	/**
	 * 方法说明：判断用户是否在该部门下
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return boolean
	 */
	public boolean isUserUnderDepartment(User user, Department department);

	/**
	 * 方法说明：获得组下的人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUserUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：获得不在组下的人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUsersNotUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：获得组下的人员列表(按照部门排序)
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUserUnderGroupOrderByDept(int page, int pagesize, Group group);

	/**
	 * 方法说明：根据组来取该组所管理的应用
	 * 
	 * @param group
	 *            group
	 * @return Application
	 */
	public Application getApplicationsUnderGroup(Group group);

	/**
	 * 方法说明：根据父组得到所有子组
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByParentGroup(Group group);

	/**
	 * 方法说明：得到根组
	 * 
	 * @return Group
	 */
	public Group getRootGroup();

	/**
	 * 方法说明：通过应用组搜索该组下的所有成员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchUserUnderApplicationGroup(int page, int pagesize,
			Condition condition, Group group);

	/**
	 * 方法说明：得到被该组管理的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getUserManagedUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：得到被该组管理的部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentManagedUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：得到被该组管理的组
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getGroupManagedUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：把用户加到某个组下
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Deprecated
	public int setUserMemberUnderGroup(User user, Group group);

	/**
	 * 方法说明：把某个用户从该组中删除
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Deprecated
	public int removeUserMemberUnderGroup(User user, Group group);

	/**
	 * 方法说明：列出该组下被逻辑删除的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getLogicDeleteUsersUnderGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：搜索该组下被逻辑删除的用户列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult searchLogicDeleteUsersUnderGroup(int page, int pagesize,
			Condition condition, Group group);

	/**
	 * 方法说明：恢复被逻辑删除的用户
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	public int restoreUser(User user);

	/**
	 * 方法说明：获得某个资源的扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource);

	/**
	 * 方法说明：获得某个资源的在应用中扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceAndGroup(Resource resource, Group group);

	/**
	 * 方法说明：获得某个资源的扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            属性类别 0-基本，1-通信，2-职务，3-应用
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, String catagory);

	/**
	 * 方法说明：获得某个资源的扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, List<String> catagory);

	/**
	 * 方法说明：获得某个资源的扩展属性(页面展示属性)
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            属性类别 0-基本，1-通信，2-职务，3-应用
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceOnPage(Resource resource, String catagory);

	/**
	 * 方法说明：判断该用户是否在该部门下
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return boolean
	 */
	@Deprecated
	public boolean existUserUnderDepartment(User user, Department department);

	/**
	 * 方法说明：把该人加到管理组里面
	 * 
	 * @param user
	 *            user
	 * @param admGroups
	 *            管理组
	 * @return int
	 */
	public int addUserManagerGroups(User user, List<Group> admGroups);

	/**
	 * 方法说明：更新该人的管理组
	 * 
	 * @param user
	 *            user
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	public int updateUserManagerGroups(User user, List<Group> admGroups);

	/**
	 * 方法说明：把该部门加到管理组里
	 * 
	 * @param department
	 *            department
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	public int addDepartmentManagerGroups(Department department, List<Group> admGroups);

	/**
	 * 方法说明：更新该部门的管理组
	 * 
	 * @param department
	 *            部门
	 * @param admGroups
	 *            管理组
	 * @return 是或否
	 */
	public int updateDeartmentManagerGroups(Department department, List<Group> admGroups);

	/**
	 * 方法说明：返回该用户所在的部门
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getUserDepartments(User user);

	/**
	 * 方法说明：得到用户的管理组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserManagedGroups(User user);

	/**
	 * 方法说明：得到部门的管理组
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<Group> getDepartmentManagedGroups(Department department);

	/**
	 * 方法说明：获得登陆用户所在组中被管理的人(带权限的)
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            身份
	 * @param user
	 *            用户
	 * @return 被管理的用户
	 */
	public UserPageResult searchUsersManagedByUserGroups(int page, int pagesize,
			Condition condition, User user);

	/**
	 * 方法说明：获得登陆用户所在组中被管理的部门(带权限的)
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            身份
	 * @param user
	 *            用户
	 * @return 部门
	 */
	public UserPageResult searchDepartmentManagedByUserGroups(int page, int pagesize,
			Condition condition, User user);

	/**
	 * 方法说明：获得登陆用户所在组中被管理的组(带权限的)
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            身份
	 * @param user
	 *            用户
	 * @return 组
	 */
	public UserPageResult searchGroupManagedByUserGroups(int page, int pagesize,
			Condition condition, User user);

	/**
	 * 方法说明：得到该组的所在的的父亲组
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getParentsGroupsByGroup(Group group);

	/**
	 * 方法说明：判断组和该组的子组下是否有用户存在
	 * 
	 * @param group
	 *            group
	 * @return boolean
	 */
	public boolean exitUserUnderGroupAndSubGroups(Group group);

	/**
	 * 方法说明：把组加到管理组里
	 * 
	 * @param group
	 *            组
	 * @param admGroups
	 *            管理组
	 * @return int
	 */
	public int addGroupManagerGroups(Group group, List<Group> admGroups);

	/**
	 * 方法说明：更新组的管理组
	 * 
	 * @param group
	 *            组
	 * @param admGroups
	 *            管理组
	 * @return int
	 */
	public int updateGroupManagerGroups(Group group, List<Group> admGroups);

	/**
	 * 方法说明：判断组编号是否唯一
	 * 
	 * @param code
	 *            组编号
	 * @return 是否唯一
	 */
	public boolean existGroupCode(String code);

	/**
	 * 方法说明：得到组的管理组
	 * 
	 * @param group
	 *            组
	 * @return 管理组
	 */
	public List<Group> getGroupManagedGroups(Group group);

	/**
	 * 更新部门带新的父组
	 * 
	 * @param group
	 *            组
	 * @param newParentsGroups
	 *            父组
	 * @return int
	 */
	@Deprecated
	public int updateGroup(Group group, List<Group> newParentsGroups);

	/**
	 * 方法说明：列出部门下被逻辑删除的用户
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
	 * 方法说明：搜索部门下被逻辑删除的用户列表
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            身份
	 * @param department
	 *            部门
	 * @return 用户
	 */
	public UserPageResult searchLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Condition condition, Department department);

	/**
	 * 方法说明：获取登陆用户
	 * 
	 * @return 用户
	 */
	public User getLoginUser();

	/**
	 * 方法说明：获取登陆用户
	 * 
	 * @return String
	 */
	public String getOperatorIpAddress();

	/**
	 * 方法说明：判断用户是否在组中
	 * 
	 * @param user
	 *            用户
	 * @param group
	 *            组
	 * @return 是否
	 */
	@Deprecated
	public boolean isUserUnderGroup(User user, Group group);

	/**
	 * 方法说明：得到用户所在的组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserGroups(User user);

	/**
	 * 删除当前组可管理用户
	 * 
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return int
	 */
	public int deleteUserManagedUnderGroup(Group group, User user);

	/**
	 * 把用户加入当前管理组
	 * 
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return int
	 */
	public int addUserManagerUnderGroup(Group group, User user);

	/**
	 * 判断用户是否在当前管理组中
	 * 
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return 是否
	 */
	@Deprecated
	public boolean existUserManagerUnderGroup(Group group, User user);

	/**
	 * 判断部门是否在当前管理组中
	 * 
	 * @param department
	 *            部门
	 * @param group
	 *            组
	 * @return 是否
	 */
	@Deprecated
	public boolean existDepartmentManagedUnderGroup(Department department, Group group);

	/**
	 * 部门模糊搜索
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @return 部门
	 */
	public UserPageResult getDepartments(int page, int pagesize, Condition condition);

	/**
	 * 判断组是否在当前管理组中
	 * 
	 * @param managedGroup
	 *            管理组
	 * @param group
	 *            组
	 * @return 是否
	 */
	@Deprecated
	public boolean existGroupManagedUnderGroup(Group managedGroup, Group group);

	/**
	 * 组模糊搜索
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @return 组
	 */
	public UserPageResult getGroups(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：保存资源变更记录
	 * 
	 * @param resourceLog
	 *            资源记录
	 * @return int
	 */
	@Deprecated
	public int saveResourceLog(ResourceLog resourceLog);

	/**
	 * 方法说明：保存资源修改日志
	 * 
	 * @param user
	 *            用户
	 * @param res
	 *            资源
	 * @param logid
	 *            记录id
	 * @param beforeValue
	 *            原值
	 * @param afterValue
	 *            更改后值
	 * @param remark
	 *            描述
	 */
	@Deprecated
	public void saveResourceLog(User user, Resource res, String logid, String beforeValue,
			String afterValue, String remark);

	/**
	 * 方法说明：获得资源变更记录
	 * 
	 * @param resource
	 *            资源
	 * @return 变更记录
	 */
	@Deprecated
	public List<ResourceLog> getResourceLogs(Resource resource);

	/**
	 * 方法说明：得到资源的变更记录
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param resource
	 *            资源
	 * @return 变更记录
	 */
	@Deprecated
	public UserPageResult getResourceLogs(int page, int pagesize, Resource resource);

	/**
	 * 方法说明：得到资源删除的变更记录
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param type
	 *            类型
	 * @return 变更记录
	 */
	@Deprecated
	public UserPageResult getDelResourceLogs(int page, int pagesize, Integer type);

	/**
	 * 方法说明：更新资源变更记录
	 * 
	 * @param resourceLog
	 *            资源记录
	 * @return int
	 */
	@Deprecated
	public int updateResourceLog(ResourceLog resourceLog);

	/**
	 * 将一个部门加入到管理组
	 * 
	 * @param department
	 *            部门
	 * @param group
	 *            管理组
	 * @return int
	 */
	public int addDepartmentManagedUnderGroup(Department department, Group group);

	/**
	 * 将一个部门从管理组删除
	 * 
	 * @param department
	 *            部门
	 * @param group
	 *            管理组
	 * @return int
	 */
	public int removeDepartmentManagedUnderGroup(Department department, Group group);

	/**
	 * 将一个组加入管理组
	 * 
	 * @param managedGroup
	 *            组
	 * @param managerGroup
	 *            管理组
	 * @return int
	 */
	public int addGroupManagedUnderGroup(Group managedGroup, Group managerGroup);

	/**
	 * 将一个组从管理组删除
	 * 
	 * @param managedGroup
	 *            组
	 * @param managerGroup
	 *            管理组
	 * @return int
	 */
	public int removeGroupManagedUnderGroup(Group managedGroup, Group managerGroup);

	/**
	 * 重置密码
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int resetUserPassword(User user);

	/**
	 * 得到部门及子部门下的所有已经删除成员
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param department
	 *            部门
	 * @return 已删除成员
	 */
	public UserPageResult getLogicDelUsersUnderDeptAndSubDept(int page, int pagesize,
			Department department);

	/**
	 * 得到用户管理的应用系统
	 * 
	 * @param user
	 *            用户
	 * @return 应用系统
	 */
	public List<Application> getApplicationsManagedUnderUser(User user);

	/**
	 * 得到扩展属性
	 * 
	 * @param uuid
	 *            属性id
	 * @return 属性
	 */
	public Attribute getAttributeByUuid(String uuid);

	/**
	 * 更新扩展属性
	 * 
	 * @param attribute
	 *            属性
	 * @return int
	 */
	public int updateAttribute(Attribute attribute);

	/**
	 * 方法说明：得到应用系统下没被过滤或者授权的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return 没被过滤或者授权的用户
	 */
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user);

	/**
	 * 判断用户是否是该应用系统的超级管理员
	 * 
	 * @param application
	 *            应用系统
	 * @param user
	 *            用户
	 * @return 是否
	 */
	public boolean isSuperAppGroupManager(Group application, User user);

	/**
	 * 方法说明：得到应用系统下被过滤的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return 被过滤的用户
	 */
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user);

	/**
	 * 方法说明：搜索应用系统下没被过滤或者授权的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @param condition
	 *            身份权限
	 * @return 没被过滤或者授权的用户
	 */
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition);

	/**
	 * 方法说明：搜索应用系统下被过滤的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @param condition
	 *            身份权限
	 * @return 被过滤的用户
	 */
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition);

	/**
	 * 通过id得到用户
	 * 
	 * @param id
	 *            用户id
	 * @return 用户
	 */
	public User getUserByUserId(String id);

	/**
	 * 过滤应用下的用户
	 * 
	 * @param user
	 *            用户
	 * @param group
	 *            组
	 * @return int
	 */
	public int filterUserUnderApplication(User user, Group group);

	/**
	 * 方法说明：预授权应用下的用户
	 * 
	 * @param user
	 *            用户
	 * @param group
	 *            组
	 * @return 预授权应用下的用户
	 */
	public int authorUserUnderApplication(User user, Group group);

	/**
	 * 方法说明：获得用户管理的所有需要被审核的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param user
	 *            用户
	 * @return 需要被审核的用户
	 */
	public UserPageResult getCreatedUsersManagedUnderUser(int page, int pagesize, User user);

	/**
	 * 方法说明：获得用户管理的所有需要被审核的部门
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param user
	 *            用户
	 * @return 需要被审核的部门
	 */
	public UserPageResult getCreatedDepartmentsManagedUnderUser(int page, int pagesize, User user);

	/**
	 * 审核用户通过
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int authorUser(User user);

	/**
	 * 审核部门通过
	 * 
	 * @param department
	 *            部门
	 * @return int
	 */
	public int authorDepartment(Department department);

	/**
	 * 审核用户没通过并返回
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int rollbackUser(User user);

	/**
	 * 审核部门没通过并返回
	 * 
	 * @param department
	 *            部门
	 * @return int
	 */
	public int rollbackDepartment(Department department);

	/**
	 * 获得登陆用户的应用系统映射
	 * 
	 * @param user
	 *            用户
	 * @return 应用系统映射
	 */
	public List<UseridMapping> getUserMappingByUser(User user);

	/**
	 * 判断是否是应用系统组 true:是,false:不是
	 * 
	 * @param group
	 *            应用系统组
	 * @return 是否
	 */
	public boolean isApplicationGroup(Group group);

	/**
	 * 判断用户是否对该部门具有管理权限
	 * 
	 * @param user
	 *            用户
	 * @param department
	 *            部门
	 * @return 是否
	 */
	public boolean isDepartmentManager(User user, Department department);

	/**
	 * 判断用户是否对该组具有管理权限
	 * 
	 * @param user
	 *            用户
	 * @param group
	 *            组
	 * @return 是否
	 */
	public boolean isGroupManager(User user, Group group);

	/**
	 * 判断用户是否是超级管理员组成员
	 * 
	 * @param user
	 *            用户
	 * @return 是否
	 */
	public boolean isUserinSuperGroup(User user);

	/**
	 * 通过组编码获得组
	 * 
	 * @param code
	 *            组编码
	 * @return 组
	 */
	public Group getGroupByCode(String code);

	/**
	 * 新增扩展属性类型
	 * 
	 * @param attributeType
	 *            扩展属性类型
	 * @return int
	 */
	public int saveAttributeType(AttributeType attributeType);

	/**
	 * 修改扩展属性类型
	 * 
	 * @param attributeType
	 *            扩展属性类型
	 * @return int
	 */
	public int updateAttributeType(AttributeType attributeType);

	/**
	 * 删除扩展属性类型
	 * 
	 * @param attributeType
	 *            扩展属性类型
	 * @return int
	 */
	public int deleteAttributeType(AttributeType attributeType);

	/**
	 * 通过扩展属性类型ID获得扩展属性类型
	 * 
	 * @param id
	 *            扩展属性类型id
	 * @return 扩展属性类型
	 */
	public List<AttributeType> getAttributeTypeById(String id);

	/**
	 * 获得唯一扩展属性类型通过uuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return 唯一扩展属性类型
	 */
	public AttributeType getAttributeTypeByUuid(String uuid);

	/**
	 * 保存扩展属性
	 * 
	 * @param attribute
	 *            扩展属性
	 * @return int
	 */
	public int saveAttribute(Attribute attribute);

	/**
	 * 删除扩展属性
	 * 
	 * @param attribute
	 *            扩展属性
	 * @return int
	 */
	public int deleteAttribute(Attribute attribute);

	/**
	 * 方法说明：保存扩展属性值
	 * 
	 * @param stringValue
	 *            扩展属性值
	 * @return int
	 */
	@Deprecated
	public int saveStringValue(StringValue stringValue);

	/**
	 * 更新扩展属性值
	 * 
	 * @param stringValue
	 *            扩展属性值
	 * @return int
	 */
	@Deprecated
	public int updateStringValue(StringValue stringValue);

	/**
	 * 删除扩展属性值
	 * 
	 * @param stringValue
	 *            扩展属性值
	 * @return int
	 */
	@Deprecated
	public int deleteStringValue(StringValue stringValue);

	/**
	 * 方法说明：获得扩展属性值
	 * 
	 * @param attribute
	 *            扩展属性
	 * @return 扩展属性值
	 */
	@Deprecated
	public List<StringValue> getStringValuesUnderAttribute(Attribute attribute);

	/**
	 * 保存候选项
	 * 
	 * @param candidateItem
	 *            候选项
	 * @return int
	 */
	public int saveCandidateItem(CandidateItem candidateItem);

	/**
	 * 更新候选项
	 * 
	 * @param candidateItem
	 *            候选项
	 * @return int
	 */
	public int updateCandidateItem(CandidateItem candidateItem);

	/**
	 * 通过扩展属性获取候选项
	 * 
	 * @param attributeType
	 *            扩展属性
	 * @return 候选项
	 */
	public List<CandidateItem> getCandidateItemsByAttributeType(AttributeType attributeType);

	/**
	 * 删除候选项
	 * 
	 * @param candidateItem
	 *            候选项
	 * @return int
	 */
	public int deleteCandidateItem(CandidateItem candidateItem);

	/**
	 * 方法说明：获得扩展属性类型的分页查询
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param resourceType
	 *            资源类型
	 * @param condition
	 *            条件
	 * @return 扩展属性类型
	 */
	public UserPageResult searchAttributeTypesByResource(int page, int pagesize,
			Integer resourceType, Condition condition);

	/**
	 * 方法说明：获得扩展属性类型的分页
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param resourceType
	 *            资源类型
	 * @return 扩展属性类型
	 */
	public UserPageResult getAttributeTypesByResource(int page, int pagesize, Integer resourceType);

	/**
	 * 通过资源获得属性配置分页
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param resourceType
	 *            资源类型
	 * @return 属性配置
	 */
	public UserPageResult getResourceSyncByResource(int page, int pagesize, Integer resourceType);

	/**
	 * 通过资源获得属性配置
	 * 
	 * @param resourceType
	 *            资源类型
	 * @return 属性配置
	 */
	public List<ResourceSync> getResourceSyncByResource(Integer resourceType);

	/**
	 * 保存属性配置
	 * 
	 * @param resourceSync
	 *            属性配置
	 * @return int
	 */
	public int saveResourceSync(ResourceSync resourceSync);

	/**
	 * 修改属性配置
	 * 
	 * @param resourceSync
	 *            属性配置
	 * @return int
	 */
	public int updateResourceSync(ResourceSync resourceSync);

	/**
	 * 删除属性配置
	 * 
	 * @param resourceSync
	 *            属性配置
	 * @return int
	 */
	public int deleteResourceSync(ResourceSync resourceSync);

	/**
	 * 通过UUID 获得配置对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return 配置对象
	 */
	public ResourceSync getResourceSyncByUuid(String uuid);

	/**
	 * 得到用户所管理的应用组
	 * 
	 * @param user
	 *            用户
	 * @return 应用组
	 */
	public List<Group> getAppGroupsManagedUnderUser(User user);

	/**
	 * 判断父部门下是否存在相同部门名字
	 * 
	 * @param department
	 *            父部门
	 * @param newDeptname
	 *            部门名
	 * @return 是否
	 */
	public boolean existDepartmentName(Department department, String newDeptname);

	/**
	 * 方法说明：判断父组下是否存在相同组名字
	 * 
	 * @param group
	 *            父组
	 * @param newGroupName
	 *            组名字
	 * @return 是否
	 */
	public boolean existGroupName(Group group, String newGroupName);

	/**
	 * 根据登录用户取到用户所在组的子组
	 * 
	 * @param parentGroup
	 *            父组
	 * @param loginUser
	 *            用户
	 * @return 子组
	 */
	public List<Group> getChildGroupByParentGroupAndUser(Group parentGroup, User loginUser);

	/**
	 * 根据登录用户取到用户所在组的子组(重庆方式)
	 * 
	 * @param parentGroup
	 *            父组
	 * @param loginUser
	 *            用户
	 * @return 子组
	 */
	public List<Group> getChildGroupByParentGroupAndUserCQ(Group parentGroup, User loginUser);

	/**
	 * 方法说明：搜索登陆用户所在组中的应用用户
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @param loginUser
	 *            登录用户
	 * @param group
	 *            组
	 * @return 登陆用户所在组中的应用用户
	 */
	public UserPageResult searchUsersMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group group);

	/**
	 * 列出应用系统中预授权的所有用户
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            页数
	 * @param loginUser
	 *            登录用户
	 * @param appGroup
	 *            系统组
	 * @return 预授权的所有用户
	 */
	public UserPageResult getReAuthorUserMembersByLoginUser(int size, int pagesize, User loginUser,
			Group appGroup);

	/**
	 * 搜索应用系统中预授权的所有用户
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @param loginUser
	 *            登录用户
	 * @param appgroup
	 *            系统组
	 * @return 预授权的所有用户
	 */
	public UserPageResult searchReAuthorUserMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group appgroup);

	/**
	 * 通过扩展类型获得属性
	 * 
	 * @param attributeType
	 *            扩展类型
	 * @return 属性
	 */
	public List<Attribute> getAttributesByAttributeType(AttributeType attributeType);

	/**
	 * 检查扩展属性ID是否存在
	 * 
	 * @param id
	 *            扩展属性ID
	 * @return 是否
	 */
	public boolean existAttributeTypeId(String id);

	/**
	 * 检查扩展属性名字是否存在
	 * 
	 * @param name
	 *            扩展属性名字
	 * @return 是否
	 */
	public boolean existAttributeTypeName(String name);

	/**
	 * 检查同步属性名是否存在
	 * 
	 * @param propName
	 *            同步属性名
	 * @return 是否
	 */
	public boolean existResourceSyncPropName(String propName);

	/**
	 * 是否是用户审核员
	 * 
	 * @param user
	 *            用户审核员
	 * @return 是否
	 */
	public boolean isUserAssessor(User user);

	/**
	 * 是否是应用系统审核员
	 * 
	 * @param user
	 *            应用系统审核员
	 * @return 是否
	 */
	public boolean isAppAssessor(User user);

	/**
	 * 通过同步类型名获得对象
	 * 
	 * @param porpName
	 *            同步类型名
	 * @return 对象
	 */
	public List<ResourceSync> getResourceSyncByPorpName(String porpName);

	/**
	 * 方法说明：获得组下面的用户（带权限）
	 * 
	 * @param page
	 *            页
	 * @param pageSize
	 *            页数
	 * @param group
	 *            组
	 * @param user
	 *            用户
	 * @return 用户（带权限）
	 */
	public UserPageResult getUsersUnderGroupAndLoginUser(int page, int pageSize, Group group,
			User user);

	/**
	 * 通过部门编码获得部门
	 * 
	 * @param deptCode
	 *            部门编码
	 * @return 部门
	 */
	public Department getDepartmentByDeptCode(String deptCode);

	/**
	 * 通过部门编码获得部门
	 * 
	 * @param code
	 *            部门编码
	 * @return 部门
	 */
	public Department getDepartmentByCode(String code);

	/**
	 * 搜索登陆用户所在组的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @param loginUser
	 *            登录用户
	 * @return 所在组的用户
	 */
	public UserPageResult searchUsersByConditionAndLoginUser(int page, int pagesize,
			Condition condition, User loginUser);

	/**
	 * 方法说明：搜索登陆用户所在单位的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @param orgCode
	 *            orgCode
	 * @return 所在单位的用户
	 */
	public UserPageResult searchUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode);

	/**
	 * 取得应用系统下用户列表,如果
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param dept
	 *            公司
	 * @param group
	 *            应用系统
	 * @return 用户列表
	 */
	public UserPageResult getUsersUnderApplicationByOrg(int page, int pagesize, Department dept,
			Group group);

	/**
	 * 得到所有的应用组
	 * 
	 * @return 应用组
	 */
	public List<Group> getAllAppGroup();

	/**
	 * 方法说明：得到应用系统相关的组
	 * 
	 * @param parentGroup
	 *            组
	 * @param user
	 *            用户
	 * @return 应用系统相关的组
	 */
	public List<Group> getAppGroupsByLoginUser(Group parentGroup, User user);

	/**
	 * 搜索ERP用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @return ERP用户
	 */
	public UserPageResult searchERPUsersByCondition(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：搜索ERP用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @param orgCode
	 *            orgCode
	 * @return 搜索ERP用户
	 */
	public UserPageResult searchERPUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode);

	/**
	 * 判断部门编码是否存在
	 * 
	 * @param code
	 *            部门编码
	 * @return 是否
	 */
	public boolean existDepartmentCode(String code);

	/**
	 * 通过扩展属性类型ID关键字得到扩展属性
	 * 
	 * @param resource
	 *            扩展属性类型
	 * @param keys
	 *            keys
	 * @return 扩展属性
	 */
	public List<Attribute> getAttributesByAttributeTypeIdKey(Resource resource, List<String> keys);

	/**
	 * 通过扩展属性类型ID关键字得到扩展属性
	 * 
	 * @param resource
	 *            扩展属性类型
	 * @param keys
	 *            keys
	 * @return 扩展属性
	 */
	public Map<String, String> getUserResourceSynByAttributeTypeIdKey(Resource resource,
			List<String> keys);

	/**
	 * 获得默认加载应用组
	 * 
	 * @return 默认加载应用组
	 */
	public List<Group> getDefaultAddAppGroup();

	/**
	 * 通过部门名称和组织code得到部门
	 * 
	 * @param deptName
	 *            部门名称
	 * @param orgCode
	 *            组织code
	 * @return 部门
	 */
	public Department getDepartmentNameAndOrgCode(String deptName, String orgCode);

	/**
	 * 方法说明：得到所有逻辑删除的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @return 所有逻辑删除的用户
	 */
	public UserPageResult getLogicDeleteUsers(int page, int pagesize);

	/**
	 * 搜索逻辑删除的用户
	 * 
	 * @param page
	 *            页
	 * @param pagesize
	 *            页数
	 * @param condition
	 *            条件
	 * @return 逻辑删除的用户
	 */
	public UserPageResult searchLogicDeleteUsers(int page, int pagesize, Condition condition);

	/**
	 * 物理删除某人
	 * 
	 * @param user
	 *            用户
	 * @return int
	 */
	public int deleteUserWL(User user);

	/**
	 * 得到用户的关联账户
	 * 
	 * @param user
	 *            用户
	 * @return 关联账户
	 */
	public List<User> getRelationUsers(User user);

	/**
	 * 保存用户多级审核
	 * 
	 * @param userAuthor
	 *            用户多级审核
	 * @return int
	 */
	public int saveUserAuthor(UserAuthor userAuthor);

	/**
	 * 删除用户多级审核
	 * 
	 * @param userAuthor
	 *            用户多级审核
	 * @return int
	 */
	public int deleteUserAuthor(UserAuthor userAuthor);

	/**
	 * 更新用户多级审核
	 * 
	 * @param userAuthor
	 *            用户多级审核
	 * @return int
	 */
	public int updateUserAuthor(UserAuthor userAuthor);

	/**
	 * 方法说明：保存部门多级审核
	 * 
	 * @param departmentAuthor
	 *            部门多级审核
	 * @return int
	 */
	public int saveDepartmentAuthor(DepartmentAuthor departmentAuthor);

	/**
	 * 方法说明：保存部门多级审核
	 * 
	 * @param departmentAuthor
	 *            部门多级审核
	 * @return int
	 */
	public int deleteDepartmentAuthor(DepartmentAuthor departmentAuthor);

	/**
	 * 方法说明：保存部门多级审核
	 * 
	 * @param departmentAuthor
	 *            部门多级审核
	 * @return int
	 */
	public int updateDepartmentAuthor(DepartmentAuthor departmentAuthor);

	/**
	 * 获得用户的多级审核列表
	 * 
	 * @param user
	 *            用户
	 * @return 用户的多级审核列表
	 */
	public List<UserAuthor> getUserAuthorsByUser(User user);

	/**
	 * 获得部门的多级审核列表
	 * 
	 * @param department
	 *            部门
	 * @return 部门的多级审核列表
	 */
	public List<DepartmentAuthor> getDepartmentAuthorsByDepartment(Department department);

	/**
	 * 获得用户需要同步的属性
	 * 
	 * @param user
	 *            用户
	 * @return 属性
	 */
	public List<ResourceSyn> getUserResourceSyn(User user);

	/**
	 * 
	 * 方法说明：获得当前部门的个数
	 * 
	 * @return int
	 */
	public int countDepartment();

	/**
	 * 判断rtxcode是否存在
	 * 
	 * @param rtxCode
	 *            rtxCode
	 * @return 是否
	 */
	public boolean existDepartmentRTXCode(long rtxCode);

	/**
	 * 取rtxCode的最大值
	 * 
	 * @return rtxCode的最大值
	 */
	public Long countDepartmentForRtx();

	/**
	 * 
	 * 取得所有父部门节点（所有）
	 * 
	 * @param uuid
	 *            uuid
	 * 
	 * @return 部门列表
	 */

	public List<Department> getAllDepartmentByChildUuid(String uuid);

	/**
	 * 取得所有父部门节点（所有）
	 * 
	 * @param dept
	 *            dept
	 * @return 父部门节点
	 */
	public List<Department> getAllDepartmentByChild(Department dept);

	/**
	 * 
	 * 取得所有子角色节点（所有）
	 * 
	 * @param uuid
	 *            uuid
	 * 
	 * @return 角色列表
	 */

	public List<Group> getAllChildGroupByUuid(String uuid);

	/**
	 * 清理缓存
	 * 
	 * @return int
	 */
	@Deprecated
	public int clearSession();

	/**
	 * 方法说明：保存同步目地源
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	public int saveSyncED(SyncED syncED);

	/**
	 * 
	 * 方法说明：删除同步目的源删除同步目的源
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	public int deleteSyncED(SyncED syncED);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	public int updateSyncED(SyncED syncED);

	/**
	 * 方法说明：获得同步目地源列表
	 * 
	 * @return List
	 */
	public List<SyncED> getSyncEDList();

	/**
	 * 
	 * 方法说明：获得同步目地源分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getSyncED(int page, int pagesize);

	/**
	 * 方法说明：获得同步目地源
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncED
	 */
	public SyncED getSyncEDByUuid(String uuid);

	/**
	 * 方法说明：取得所有父级组
	 * 
	 * @param listGroups
	 *            listGroups
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getAllParentGroupsByGroup(List<Group> listGroups, Group group);

	/**
	 * 方法说明：甘肃取得组的类型(包括内容管理组和门户组两种)
	 * 
	 * @param group
	 *            group
	 * @return String
	 */
	public String getParentGroupCodeOnGS(Group group);

	/**
	 * 方法说明：取得离职人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getLeftUsers(int page, int pagesize);

	/**
	 * 方法说明：搜索符合条件的离职人员
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchLeftUsers(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：恢复移除的人员
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean rollbackUserFromDepartment(User user);

	/**
	 * 方法说明：得到该部门下的所有人员，包括正常状态下的和已经逻辑删除的用户
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getAllUsersUnderDepartment(Department department);

	/**
	 * 方法说明：取得用户的管理组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserAdminGroup(User user);

	/**
	 * 方法说明：取得所有正常用户
	 * 
	 * @return List
	 */
	public List<User> getAllNormalUsers();

	/**
	 * 方法说明：取最大的erp编码 北供专用
	 * 
	 * @param typeid
	 *            typeid
	 * @return String
	 */
	@Deprecated
	public String getLastUserId(String typeid);

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的map型
	 * 
	 * @param resource
	 *            resource
	 * @param key
	 *            key
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource, String... key);

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的map型
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource,
			Map<String, String> keys);

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的集合型
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource, AttributeType... types);

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的集合型
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource, Collection<AttributeType> types);

	/**
	 * 方法说明：通过扩展属性取得资源列表
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<Resource> getResourceListByAttribute(String attrName, String attrValue);

	/**
	 * 
	 * 方法说明：取得所有可管理用户的组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserManagedGroupsByHql(User user);

	/**
	 * 
	 * 方法说明：通过属性关联取得所有属性值
	 * 
	 * @param att
	 *            att
	 * @return List
	 */
	@Deprecated
	public List<AttributeValue> getAttributeValueByAttribute(Attribute att);

	/**
	 * 方法说明：带权限取部门列表
	 * 
	 * @param uuid
	 *            uuid
	 * @param loginUser
	 *            loginUser
	 * @param flag
	 *            flag
	 * @return List
	 */
	public List<Department> getDepartmentChildren(String uuid, User loginUser, String flag);

	/**
	 * 方法说明：添加用户权限组
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	public void addUserGroups(User user, Collection<Group> groups);

	/**
	 * 方法说明：添加用户权限组
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	public void addUserGroup(User user, Group group);

	/**
	 * 方法说明：删除用户权限组
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	public void deleteUserGroups(User user, Collection<Group> groups);

	/**
	 * 方法说明：删除用户权限组
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	public void deleteUserGroup(User user, Group group);

	/**
	 * 方法说明：取资源的所有扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResource(Resource resource);

	/**
	 * 方法说明：删除角色上级组
	 * 
	 * @param groupChildren
	 *            groupChildren
	 * @param collection
	 *            collection
	 */
	public void deleteGroupParentGroups(Group groupChildren, Collection<Group> collection);

	/**
	 * 方法说明：添加角色上级组
	 * 
	 * @param groupChildren
	 *            groupChildren
	 * @param collection
	 *            collection
	 */
	public void addGroupParentGroups(Group groupChildren, Collection<Group> collection);

	/**
	 * 方法说明：取得用户可管理组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getUserAdminGroups(User user);

	/**
	 * 方法说明：获得用户的主属部门
	 * 
	 * @param user
	 *            user
	 * @return Department
	 */
	public Department getUserPrimaryDepartment(User user);

	/**
	 * 方法说明：获取组的父亲组
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getParentsGroup(Group group);

	/**
	 * 方法说明：得到父部门
	 * 
	 * @param dept
	 *            dept
	 * @return Department
	 */
	public Department getParentDepartment(Department dept);

	/**
	 * 方法说明：获取分页的子部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param parentDept
	 *            parentDept
	 * @return UserPageResult
	 */
	public UserPageResult getSubDepartments(int page, int pagesize, Department parentDept);

	/**
	 * 方法说明：通过用户ID和应用系统UUID获取用户所在应用系统账号
	 * 
	 * @param userId
	 *            userId
	 * @param groupUuid
	 *            groupUuid
	 * @return List
	 */
	@Deprecated
	public List<String> getUserAccountInApplication(String userId, String groupUuid);

	/**
	 * 方法说明：通过类型ID模糊搜索
	 * 
	 * @param idKey
	 *            idKey
	 * @return List
	 */
	@Deprecated
	public List<AttributeType> getAttributeTypesLikeIdKey(String idKey);

	/**
	 * 方法说明：获取部门下面的人员
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<User> getUsersUnderDepartment(Department department);

	/**
	 * 
	 * 方法说明：获取部门集合下面的所有人员
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param depts
	 *            depts
	 * @return UserPageResult
	 */
	public UserPageResult getUsersByDepartments(int page, int pagesize, List<Department> depts);

	/**
	 * 方法说明：移除组下所有的用户
	 * 
	 * @param group
	 *            group
	 */
	public void removeAllUserFromGroup(Group group);

	/**
	 * 方法说明：根据部门名称模糊搜索部门
	 * 
	 * @param name
	 *            name
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, User loginUser);

	/**
	 * 方法说明：根据部门名称模糊搜索部门
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name);

	/**
	 * 方法说明：searchDepartmentsByNameCount
	 * 
	 * @param name
	 *            name
	 * @return long
	 */
	public long searchDepartmentsByNameCount(String name);

	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @param start
	 *            start
	 * @param max
	 *            max
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, int start, int max);

	/**
	 * 
	 * 方法说明：根据部门code模糊搜索部门
	 * 
	 * @param searchcontent
	 *            searchcontent
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	public List<Department> searchDepartmentsByCode(String searchcontent, User loginUser);

	/**
	 * 
	 * 方法说明：获取用户公司信息
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getUserOrgDepartment(User user);

	/**
	 * 方法说明：取得资源的管理员组
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getResourceManagedGroups(Resource resource);

	/**
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
	public UserPageResult getUsersUnderApplication(Integer page, Integer pagesize,
			Application application);

	/**
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
	 * 方法说明：创建应用的profile
	 * 
	 * @param apo
	 *            authenticationProfile对象
	 * @return int
	 */
	public int createAuthenticationProfile(AuthenticationProfile apo);

	/**
	 * 方法说明： 取得非应用下的用户（带分页信息）
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param app
	 *            app
	 * @return UserPageResult
	 */
	public UserPageResult<User> getUsersNotUnderApplication(Integer page, Integer pagesize,
			Application app);

	/**
	 * 
	 * 方法说明：删除用户部门
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	public void deleteUserDepartments(User user, Collection<Department> collection);

	/**
	 * 
	 * 方法说明：添加用户部门
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	public void addUserDepartments(User user, Collection<Department> collection);

	/**
	 * 
	 * 方法说明：删除用户管理组
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	public void deleteUserAdminGroups(User user, Collection<Group> collection);

	/**
	 * 
	 * 方法说明：添加用户管理组
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            用户管理组
	 */
	public void addUserAdminGroups(User user, Collection<Group> collection);

	/**
	 * 
	 * 方法说明：修改用户某扩展属性
	 * 
	 * @param loginUser
	 *            操作用户,可以为NULL
	 * @param res
	 *            修改的对象
	 * @param key
	 *            扩展属性
	 * @param value
	 *            修改后的值
	 */
	public void modifyResourceAttribute(User loginUser, Resource res, String key, String value);

	/**
	 * 
	 * 方法说明：修改用户某扩展属性
	 * 
	 * @param loginUser
	 *            操作用户,可以为NULL
	 * @param res
	 *            修改的对象
	 * @param type
	 *            扩展属性
	 * @param value
	 *            修改后的值
	 */
	public void modifyResourceAttribute(User loginUser, Resource res, AttributeType type,
			String value);

	/**
	 * 方法说明：修改资源某扩展属性
	 * 
	 * @param res
	 *            资源对象
	 * @param type
	 *            属性类型
	 * @param value
	 *            要改变的值
	 * @return Map<String,String[]> [key,[originalValue,currentValue]]
	 */
	public Map<String, String[]> modifyResourceAttribute(Resource res, String type, String value);

	/**
	 * 方法说明：修改资源某扩展属性
	 * 
	 * @param res
	 *            资源对象
	 * @param type
	 *            属性类型
	 * @param value
	 *            要改变的值
	 * @return Map<String,String[]> [key,[originalValue,currentValue]]
	 */
	public Map<String, String[]> modifyResourceAttribute(Resource res, AttributeType type,
			String value);

	/**
	 * 方法说明：通过资源和扩展属性取属性
	 * 
	 * @param res
	 *            res
	 * @param typeString
	 *            typeString
	 * @return List
	 */
	public List<Attribute> getAttributesByResAndType(Resource res, String typeString);

	/**
	 * 方法说明：通过资源和扩展属性取属性
	 * 
	 * @param res
	 *            res
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<Attribute> getAttributesByResAndType(Resource res, AttributeType attributeType);

	/**
	 * 
	 * 方法说明：通过key列表取得扩展属性列表
	 * 
	 * @param keySet
	 *            keySet
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByKeySet(Set<String> keySet);

	/**
	 * 
	 * 方法说明：根据erp编码取得用户列表 知道用户的ERP属性id,可以直接用getUserByAttribute(ERP属性id, ERP编码)
	 * 
	 * @param userCode
	 *            userCode
	 * @return List
	 */
	public List<User> searchUsersByERPCode(String userCode);

	/**
	 * 方法说明：更新资源的扩展属性
	 * 
	 * @param r
	 *            r
	 * @param map
	 *            map
	 */
	public void updateResourceAttribute(Resource r, Map<String, String> map);

	/**
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
	 * 
	 * 方法说明：通过多个扩展属性取得用户列表
	 * 
	 * @param attList
	 *            attList
	 * @param value
	 *            value
	 * @return List
	 */
	public List<User> getUserByAttributes(List<AttributeType> attList, String value);

	/**
	 * 
	 * 方法说明：通过资源的修改时间周期取得日志列表
	 * 
	 * @param res
	 *            res
	 * @param beginDate
	 *            开始时间
	 * @param endDate
	 *            结束时间,如果为空则是当前时间
	 * @return List
	 */
	public List<ResourceLog> getResourceLogsByEditTime(Resource res, String beginDate,
			String endDate);

	/**
	 * 
	 * 方法说明：服务器启动停止日志
	 * 
	 * @param serverLog
	 *            serverLog
	 */
	public void createServerLog(ServerLog serverLog);

	/**
	 * 方法说明：获取最后一次服务器启动停止日志
	 * 
	 * @return ServerLog
	 */
	public ServerLog getLastServerLog();

	/**
	 * 判断登录用户是否有权限访问该节点
	 * 
	 * @param uuid
	 *            uuid
	 * @return boolean
	 */
	public boolean isAuthorForTaskList(String uuid);

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
	 * 方法说明：获取退休人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getRetiredUsers(Integer page, Integer pagesize);

	/**
	 * 方法说明：搜索退休人员列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchRetiredUsers(Integer page, Integer pagesize, Condition condition);

	// Added by Guo Tianci, -start 2011 -08 -08 --start
	/**
	 * 获得用户数量
	 * 
	 * @return 用户数量
	 */
	public long countUser();

	// --end

	/**
	 * 查询部门下子部门的数量
	 * 
	 * @param dept
	 *            部门
	 * @return 子部门数目
	 */
	public long countSubDepartment(Department dept);

	/**
	 * 判断部门下是否存在子部门
	 * 
	 * @param dept
	 *            部门
	 * @return 是否存在子部门
	 */
	public boolean hasSubDepartment(Department dept);

	/**
	 * 方法说明：取得组织机构对象
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Department
	 */
	public Department getOrganization(String deptUUID);

	// Added by Guo Tianci, -start 2011 -08 -17 --start
	/**
	 * 获取无效人员列表
	 * 
	 * @param page
	 *            Integer
	 * @param pagesize
	 *            Integer
	 * @return 人员列表
	 */
	public UserPageResult getVoidUsers(Integer page, Integer pagesize);

	// --end

	/**
	 * 方法说明：取得应用系统未授权用户列表
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
	 * 方法说明：
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
	 * 方法说明：取得所有子部门,包括自己
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Department> getDepartmentsByParent(Department dept);

	/**
	 * 方法说明：取得Resource对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return Resource
	 */
	public Resource getResource(String uuid);

	/**
	 * 方法说明：是否临时账号,重庆特有
	 * 
	 * @param deptChildren
	 *            deptChildren
	 * @return boolean
	 */
	public boolean isVirtualUser(Department deptChildren);

	/**
	 * 
	 * 方法说明：判断userid是否存在于数据库中,如果存在则在用户名后面加数字,直到用户名不存在.
	 * 
	 * @param userid
	 *            userid
	 * @return String
	 */
	public String getNotExistsUserId(String userid);

	/**
	 * 方法说明：根据用户姓名精确查找用户对象
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<User> getUserByUserName(String name);

	/**
	 * 方法说明：根据扩展属性模糊匹配用户列表
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return List
	 */
	public List<User> searchUserByAttribute(String key, String value);

	/**
	 * 方法说明：isFakeLogin
	 * 
	 * @param userId
	 *            userId
	 * @param password
	 *            password
	 * @return boolean
	 */
	public boolean isFakeLogin(String userId, String password);
	Set<Group> getAttributeTypeManagedGroups(AttributeType type);
	Set<Group> getAttributeTypeGroups(AttributeType type);

	public Department getDepartmentByAttribute(AttributeType t, String deptCode);
}