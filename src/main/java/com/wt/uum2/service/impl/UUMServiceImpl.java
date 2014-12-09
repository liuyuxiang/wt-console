package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentDao;
import com.wt.uum2.dao.DepartmentPathDao;
import com.wt.uum2.dao.GroupDao;
import com.wt.uum2.dao.GroupRelationshipDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupRelationship;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;
import com.wt.uum2.domain.UserDepartment;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class UUMServiceImpl extends UUMServiceBaseImpl implements UUMService
{

	/**
	 * 
	 */
	private DepartmentPathService departmentPathService;

	/**
	 * 
	 */
	private DepartmentDao departmentDao;

	/**
	 * 
	 */
	private GroupDao groupDao;

	/**
	 * 
	 */
	private GroupRelationshipDao groupRelationshipDao;

	/**
	 * 
	 */
	private DepartmentPathDao departmentPathDao;

	public void setDepartmentPathDao(DepartmentPathDao departmentPathDao)
	{
		this.departmentPathDao = departmentPathDao;
	}

	/**
	 * @param departmentPathService
	 *            the departmentPathService to set
	 */
	public void setDepartmentPathService(DepartmentPathService departmentPathService)
	{
		this.departmentPathService = departmentPathService;
	}

	public void setGroupRelationshipDao(GroupRelationshipDao groupRelationshipDao)
	{
		this.groupRelationshipDao = groupRelationshipDao;
	}

	public void setGroupDao(GroupDao groupDao)
	{
		this.groupDao = groupDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentByUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentByUUID(String uuid)
	{
		return departmentDao.getDeapartmentByUUID(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentChildren(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getDepartmentChildren(String parentUUID)
	{
		return departmentDao.readChildren(parentUUID);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentChildren(java.lang.String, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getDepartmentChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getDepartmentChildren(String parentUUID, User loginUser)
	{
		return departmentDao.readChildren(parentUUID, loginUser);
	}

	/**
	 * 
	 */
	private Department rootDepartment;

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentRoot()
	 */
	/**
	 * 方法说明：getDepartmentRoot
	 * 
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentRoot()
	{
		if (rootDepartment == null) {
			rootDepartment = departmentDao.readRoot();
		}
		return rootDepartment;
	}

	public void setDepartmentDao(DepartmentDao departmentDao)
	{
		this.departmentDao = departmentDao;
	}

	@Transactional(readOnly = true)
	public List<Department> getAllDepartments()
	{
		return departmentDao.getAllDepartments();
	}

	@Transactional(readOnly = true)
	public List<Group> getAllGroups()
	{
		return groupDao.getAllGroups();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#createGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：createGroup
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createGroup(Group group)
	{
		if (StringUtils.isBlank(group.getAppGroupType())) {
			group.setAppGroupType("0");
		}
		if (StringUtils.isBlank(group.getGroupType())) {
			group.setGroupType("0");
		}
		if (group.getOrder() == null) {
			group.setOrder(0L);
		}
		group.setHasChildren(true);
		group.setModifiedTime(Calendar.getInstance().getTime());
		if (group.getStatus() == null) {
			group.setStatus(ResourceStatus.NORMAL.intValue());
		}
		groupDao.save(group);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#createDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：createDepartment
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createDepartment(Department department)
	{
		Set<Group> set = new HashSet<Group>();

		if (CollectionUtils.isNotEmpty(department.getAdminGroups())) {
			set.addAll(department.getAdminGroups());
		}
		Department parent = getParentDepartment(department);
		if (parent != null) {
			List<Group> sg = departmentDao.getGroupsByManagedDepartment(parent);
			// 得到父部门的管理组，实现继承父部门管理组
			if (CollectionUtils.isNotEmpty(sg)) {
				set.addAll(sg);
			}
			department.setAdminGroups(set);
			if (department.getPath() == null) {
				department.setPath(parent.getPath() + "→" + department.getName());
			}
		}
		if (department.getOrder() == null || department.getOrder() == 0) {
			department.setOrder((long) countDepartment() + 1);
		}
		if (department.getRtxCode() == null) {
			department.setRtxCode(countDepartmentForRtx());
		}
		if (department.getLastUpdateTime() == null) {
			department.setLastUpdateTime(Calendar.getInstance().getTime());
		}
		if (department.getModifiedTime() == null) {
			department.setModifiedTime(Calendar.getInstance().getTime());
		}
		if (department.getStatus() == null) {
			department.setStatus(ResourceStatus.NORMAL.intValue());
		}
		departmentDao.save(department);
		departmentPathService.updateLevelDeptPath(department);

		if (CollectionUtils.isNotEmpty(department.getAdminGroups())) {
			for (Group adminGroup : department.getAdminGroups()) {
				resourceAdminGroupDao.add(adminGroup, department);
			}
		}

		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#updateDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：updateDepartment
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateDepartment(Department department)
	{
		if (hasSubDepartment(department)) {
			department.setHasChildren(true);
		}
		departmentDao.update(department);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#updateDeptPath(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：updateDeptPath
	 * 
	 * @param newPath
	 *            newPath
	 * @param oldPath
	 *            oldPath
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateDeptPath(String newPath, String oldPath)
	{
		departmentDao.updateDeptPath(newPath, oldPath);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#updateGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：updateGroup
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateGroup(Group group)
	{
		groupDao.update(group);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：deleteDepartment
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteDepartment(Department department)
	{
		List<Department> children = departmentDao.getDepartmentsByParent(department);// 得到部门的所有子部门，实现级联删除
		if (CollectionUtils.isNotEmpty(children)
				&& userDao.getUserMembersByDepartments(1, 2, children).getList().isEmpty()) {
			List<User> listDeleteUser = userDao.getLogicDeleteUsersUnderDepartment(1, 999999,
					children).getList();
			List<User> listMoveUser = userDao.getMoveUsersUnderDepartment(1, 999999, children)
					.getList();
			List<User> listUser = new ArrayList<User>();
			listUser.addAll(listDeleteUser);
			listUser.addAll(listMoveUser);
			Department moveDepartment = null;
			if (this.existDepartmentCode(InitParameters.getNoDepartment())) {
				moveDepartment = this.getDepartmentByDeptCode(InitParameters.getNoDepartment());
			} else {
				moveDepartment = getParentDepartment(department);
			}
			Set<Department> moveDepartments = new HashSet<Department>();
			moveDepartments.add(moveDepartment);
			for (User user : listUser) {
				user.setPrimaryDepartment(moveDepartment);
				user.setDepartments(moveDepartments);
				this.updateUser(user);
			}

			for (Department child : children) {
				List<DepartmentAuthor> daList = getDepartmentAuthorsByDepartment(child);
				if (CollectionUtils.isNotEmpty(daList)) {
					for (DepartmentAuthor departmentAuthor : daList) {
						deleteDepartmentAuthor(departmentAuthor);
					}
				}
				deleteDepartmentByUuid(child.getUuid());
			}
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：deleteGroup
	 * 
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteGroup(Group group)
	{// 级联删除组
		// Set<Group> oldParentsGroups = group.getParents();
		// java.util.Iterator<Group> iterator = oldParentsGroups.iterator();
		// while (iterator.hasNext()) {
		// Group oldgroup = iterator.next();
		// if (groupDao.getGroupsByParentUUID(oldgroup.getUuid()).size() == 1) {
		// oldgroup.setHasChildren(false);
		// groupDao.update(oldgroup);
		// }
		// }
		userGroupDao.deleteUserGroup(group);
		resourceAdminGroupDao.removeByAdminGroup(group);
		resourceAdminGroupDao.removeByResource(group);
		deleteAttribute(group);
		userAuthorDao.deleteAuthorGroup(group);
		groupDao.delete(group);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteDepartmentByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：deleteDepartmentByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteDepartmentByUuid(String uuid)
	{
		Department department = departmentDao.getDeapartmentByUUID(uuid);
		if (department == null) {
			return 1;
		}
		// List<Department> children = departmentDao.getDepartmentsByParent(department);
		// if (CollectionUtils.isNotEmpty(children)) {
		// for (Department child : children) {
		// deleteAttribute(child);
		// departmentDao.delete(child);
		// departmentPathDao.deleteDepartmentPathByUUID(child.getUuid());
		// }
		// }
		deleteAttribute(department);
		resourceAdminGroupDao.removeByResource(department);
		departmentDao.delete(department);
		departmentPathDao.deleteDepartmentPathByUUID(department.getUuid());
		return 1;
	}

	/**
	 * 方法说明：deleteAttribute
	 * 
	 * @param resource
	 *            resource
	 */
	@Transactional(readOnly = false)
	private void deleteAttribute(Resource resource)
	{
		List<Attribute> list = getAttributesUnderResource(resource);
		for (Attribute att : list) {
			deleteAttribute(att);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteGroupByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：deleteGroupByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteGroupByUuid(String uuid)
	{
		Group group = groupDao.getGroupByUuid(uuid);
		Set<Group> oldParentsGroups = group.getParents();
		java.util.Iterator<Group> iterator = oldParentsGroups.iterator();
		while (iterator.hasNext()) {
			Group oldgroup = iterator.next();
			if (groupDao.getGroupsByParentUUID(oldgroup.getUuid()).size() == 1) {
				oldgroup.setHasChildren(false);
				List<Group> updateGroup = new ArrayList<Group>();
				updateGroup.add(oldgroup);
				groupDao.update(oldgroup);
			}
		}
		List<Group> groups = new ArrayList<Group>();
		groups.add(group);
		groups.addAll(groupDao.getAllGroupsByParent(group));
		if (CollectionUtils.isNotEmpty(groups)) {
			for (Group g : groups) {
				deleteAttribute(g);
				groupDao.delete(g);
			}
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroupByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getGroupByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Group
	 */
	@Transactional(readOnly = false)
	public Group getGroupByUuid(String uuid)
	{
		return groupDao.getGroupByUuid(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#removeUserFromDepartment(com.wt.uum2.domain.Department, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：removeUserFromDepartment
	 * 
	 * @param department
	 *            department
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeUserFromDepartment(Department department, User user)
	{

		userDepartmentDao.delete(new UserDepartment(department, user));
		List<Department> departments = getUserDepartments(user);
		// if(user.getPrimaryDepartment()!=null&&user.getPrimaryDepartment().equals(department)){
		// user.setPrimaryDepartment(departmentDao.readRoot());
		// }
		// departments.remove(department);
		if (departments.isEmpty()) {
			Department dept = departmentDao.getNoDepartment();
			if (dept == null) {
				dept = departmentDao.readRoot();
			}
			departments.add(dept);
		}
		// user.setDepartments(new HashSet<Department>(departments));
		user.setPrimaryDepartment(departments.get(0));
		userDao.update(user);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#moveDepartmentToNewParent(com.wt.uum2.domain.Department, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：moveDepartmentToNewParent
	 * 
	 * @param dept
	 *            dept
	 * @param parent
	 *            parent
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int moveDepartmentToNewParent(Department dept, Department parent)
	{
		dept.setParent(parent);
		dept.setParentUUID(parent.getUuid());
		String orgCode = "";
		if (StringUtils.isBlank(parent.getParentUUID())) {
			orgCode = dept.getDeptCode();
		} else {
			orgCode = parent.getOrgCode();
		}
		if (!StringUtils.equals(dept.getOrgCode(), orgCode)) {
			List<Department> children = departmentDao.getDepartmentsByParent(dept);
			for (Department child : children) {
				child.setOrgCode(orgCode);
				departmentDao.update(child);
			}
		}
		dept.setOrgCode(orgCode);
		departmentDao.update(dept);
		departmentPathService.moveDepartmentPath(dept, parent);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existDepartmentCode(com.wt.uum2.domain.Department, java.lang.String)
	 */
	/**
	 * 方法说明：existDepartmentCode
	 * 
	 * @param parentDepartment
	 *            parentDepartment
	 * @param departmentCode
	 *            departmentCode
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existDepartmentCode(Department parentDepartment, String departmentCode)
	{
		departmentCode = departmentCode == null ? departmentCode : departmentCode.trim();
		// List<Department> department = departmentDao
		// .readChildren(parentDepartment.getUuid());
		// if (InitParameters.isCqGroupAuthor()) {
		Department dept = this.getDepartmentByCode(departmentCode);
		if (dept != null) {
			return true;
		}
		// } else {
		// for (int i = 0; i < department.size(); i++) {
		// if (department.get(i).getCode()
		// .equalsIgnoreCase(departmentCode)) {
		// return true;
		// }
		// }
		// }
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isUserUnderDepartment(com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：isUserUnderDepartment
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isUserUnderDepartment(User user, Department department)
	{
		return getUserDepartments(user).contains(department);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroupsByParentGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupsByParentGroup
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getGroupsByParentGroup(Group group)
	{
		return groupDao.getGroupsByParent(group);
	}

	@Transactional(readOnly = true)
	public Group getRootGroup()
	{
		return groupDao.getRootGroup();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentManagedUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getDepartmentManagedUnderGroup
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
	public UserPageResult getDepartmentManagedUnderGroup(int page, int pagesize, Group group)
	{
		return departmentDao.getDepartmentByAdminGroup(page, pagesize, group);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroupManagedUnderGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupManagedUnderGroup
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
	public UserPageResult getGroupManagedUnderGroup(int page, int pagesize, Group group)
	{
		return groupDao.getGroupManagedUnderGroup(page, pagesize, group);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existUserUnderDepartment(com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：existUserUnderDepartment
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return boolean
	 */
	@Transactional(readOnly = false)
	public boolean existUserUnderDepartment(User user, Department department)
	{
		return isUserUnderDepartment(user, department);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentManagedGroups(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getDepartmentManagedGroups
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getDepartmentManagedGroups(Department department)
	{
		return departmentDao.getGroupsByManagedDepartment(department);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserManagedGroups(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserManagedGroups
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getUserManagedGroups(User user)
	{
		return groupDao.getGroupsByManagedUser(user);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentManagedByUserGroups(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchDepartmentManagedByUserGroups
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
	@Transactional(readOnly = true)
	public UserPageResult searchDepartmentManagedByUserGroups(int page, int pagesize,
			Condition condition, User user)
	{
		return departmentDao.searchDepartmentsByAdmGroups(page, pagesize, condition,
				getUserGroups(user), isUserinSuperGroup(user));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchGroupManagedByUserGroups(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchGroupManagedByUserGroups
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
	@Transactional(readOnly = true)
	public UserPageResult searchGroupManagedByUserGroups(int page, int pagesize,
			Condition condition, User user)
	{
		boolean isUserinSuperGroup = isUserinSuperGroup(user);
		if (isUserinSuperGroup) {
			return groupDao.getGroupsByCondition(page, pagesize, condition);
		}
		return groupDao.searchGroupsManagedByAdmGroups(page, pagesize, condition,
				getUserGroups(user), isUserinSuperGroup);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getParentsGroupsByGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getParentsGroupsByGroup
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getParentsGroupsByGroup(Group group)
	{
		return getParentsGroup(group);
		// return groupDao.getGroupsByChild(group);
		// List<Group> list = new ArrayList<Group>();
		// if(setGroup!=null){
		// list.addAll(setGroup);
		// }
		// return list;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#exitUserUnderGroupAndSubGroups(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：exitUserUnderGroupAndSubGroups
	 * 
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean exitUserUnderGroupAndSubGroups(Group group)
	{
		boolean flag = false;
		if (userDao.getUsersUnderGroup(1, 10, group).getList().size() > 0) {
			return true;
		} else if (groupDao.existsResourceUnderGroups(group)) {
			return true;
		}
		List<Group> groups = groupDao.getAllGroupsByParent(group);
		if (CollectionUtils.isNotEmpty(groups)) {
			flag = true;
			// for(int i=0;i<groups.size();i++){
			// if(userDao.getUsersUnderGroup(1, 10,
			// groups.get(i)).getList().size()>0){
			// flag = true;
			// break;
			// }else if(groupDao.existsResourceUnderGroups(groups.get(i))){
			// flag = true;
			// break;
			// }
			// }
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existGroupCode(java.lang.String)
	 */
	/**
	 * 方法说明：existGroupCode
	 * 
	 * @param code
	 *            code
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existGroupCode(String code)
	{
		Group group = groupDao.getGroupByCode(code);
		return (group != null);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroupManagedGroups(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getGroupManagedGroups
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getGroupManagedGroups(Group group)
	{
		List<Group> list = groupDao.getGroupsByManagedGroup(group);
		return list;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#updateGroup(com.wt.uum2.domain.Group, java.util.List)
	 */
	/**
	 * 方法说明：updateGroup
	 * 
	 * @param group
	 *            group
	 * @param newParentsGroups
	 *            newParentsGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateGroup(Group group, List<Group> newParentsGroups)
	{
		Set<Group> oldParentsGroups = group.getParents();
		java.util.Iterator<Group> iterator = oldParentsGroups.iterator();
		while (iterator.hasNext()) {
			Group oldgroup = iterator.next();
			if (groupDao.getGroupsByParentUUID(oldgroup.getUuid()).size() == 1) {
				oldgroup.setHasChildren(false);
				groupDao.update(oldgroup);
			}
		}
		java.util.Iterator<Group> iterator1 = newParentsGroups.iterator();
		while (iterator1.hasNext()) {
			Group newgroup = iterator1.next();
			if (groupDao.getGroupsByParentUUID(newgroup.getUuid()).size() >= 0) {
				newgroup.setHasChildren(true);
				groupDao.update(newgroup);
			}
		}
		Set<Group> setGroup = new HashSet<Group>();
		setGroup.addAll(newParentsGroups);
		group.setParents(setGroup);
		groupDao.update(group);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartments(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：getDepartments
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
	public UserPageResult getDepartments(int page, int pagesize, Condition condition)
	{
		return departmentDao.searchDepartmentsByCondition(page, pagesize, condition);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroups(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：getGroups
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
	public UserPageResult getGroups(int page, int pagesize, Condition condition)
	{
		return groupDao.getGroupsByCondition(page, pagesize, condition);
	}

	// 判断用户是否是该应用系统的超级管理员
	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isSuperAppGroupManager(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：isSuperAppGroupManager
	 * 
	 * @param application
	 *            application
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isSuperAppGroupManager(Group application, User user)
	{

		if (isUserinSuperGroup(user)) {
			return true;
		}
		List<Group> usergroups = getUserGroups(user);

		List<Group> admins = getGroupManagedGroups(application);

		final String flag = "SuperAdmin";

		for (Group ug : usergroups) {
			for (Group ag : admins) {
				if (ug.equals(ag) && ag.getCode().contains(flag)) {
					return true;
				}
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getNormalUsersUnderApplication
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
	@Transactional(readOnly = true)
	public UserPageResult getNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user)
	{
		if (isSuperAppGroupManager(group, user)) {
			return userDao.getNormalUsersUnderApplication(page, pagesize, group, user,
					new ArrayList<Group>());
		} else {
			Department org = getDepartmentByDeptCode(this.getUserPrimaryDepartment(user).getOrgCode());
			return userDao.getNormalUsersUnderApplication(page, pagesize, group, user, org);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getFilterUsersUnderApplication
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
	@Transactional(readOnly = true)
	public UserPageResult getFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user)
	{
		if (isSuperAppGroupManager(group, user)) {
			return userDao.getFilterUsersUnderApplication(page, pagesize, group, user,
					new ArrayList<Group>());
		} else {
			Department org = getDepartmentByDeptCode(this.getUserPrimaryDepartment(user).getOrgCode());
			return userDao.getFilterUsersUnderApplication(page, pagesize, group, user, org);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchNormalUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchNormalUsersUnderApplication
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
	@Transactional(readOnly = true)
	public UserPageResult searchNormalUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition)
	{
		if (isSuperAppGroupManager(group, user)) {
			return userDao
					.searchNormalUsersUnderApplication(page, pagesize, group, user, condition);
		} else {
			Department org = getDepartmentByDeptCode(this.getUserPrimaryDepartment(user).getOrgCode());
			return userDao.searchNormalUsersUnderApplication(page, pagesize, group, user,
					condition, org);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchFilterUsersUnderApplication(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchFilterUsersUnderApplication
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
	@Transactional(readOnly = true)
	public UserPageResult searchFilterUsersUnderApplication(int page, int pagesize, Group group,
			User user, Condition condition)
	{
		if (isSuperAppGroupManager(group, user)) {
			return userDao
					.searchFilterUsersUnderApplication(page, pagesize, group, user, condition);
		} else {
			Department org = getDepartmentByDeptCode(this.getUserPrimaryDepartment(user).getOrgCode());
			return userDao.searchFilterUsersUnderApplication(page, pagesize, group, user,
					condition, org);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#authorUserUnderApplication(com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：authorUserUnderApplication
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int authorUserUnderApplication(User user, Group group)
	{
		List<String> keys = new ArrayList<String>();
		String appKey = InitParameters.getAppStatusCode().replace("XXXX", group.getCode().trim());
		String status = String.valueOf(ResourceStatus.AUTHORIZE.intValue());
		keys.add(appKey);
		List<Attribute> att = getAttributesByAttributeTypeIdKey(user, keys);
		if (CollectionUtils.isNotEmpty(att)) {
			for (Attribute attribute : att) {
				if (attribute.getType().getId().equals(appKey)) {
					if (!StringUtils.defaultString(attribute.getValue()).equalsIgnoreCase(status)) {
						attribute.setValue(status);
						updateAttribute(attribute);
					}
				}
			}
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#filterUserUnderApplication(com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：filterUserUnderApplication
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int filterUserUnderApplication(User user, Group group)
	{
		List<String> keys = new ArrayList<String>();
		String appKey = InitParameters.getAppStatusCode().replace("XXXX", group.getCode().trim());
		String status = String.valueOf(ResourceStatus.FILTER.intValue());
		keys.add(appKey);
		List<Attribute> att = getAttributesByAttributeTypeIdKey(user, keys);
		if (CollectionUtils.isNotEmpty(att)) {
			for (Attribute attribute : att) {
				if (attribute.getType().getId().equals(appKey)) {
					if (!StringUtils.defaultString(attribute.getValue()).equalsIgnoreCase(status)) {
						attribute.setValue(status);
						updateAttribute(attribute);
					}
				}
			}
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#rollbackDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：rollbackDepartment
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int rollbackDepartment(Department department)
	{
		List<DepartmentAuthor> du = this.getDepartmentAuthorsByDepartment(department);
		if (du != null) {
			int length = du.size();
			if (department.getCurrentAuthorLevel() >= length) {
				if (department.getCurrentAuthorLevel() == length && length == 1) {
					departmentDao.delete(department);
				}
				return 1;
			} else {
				department.setCurrentAuthorLevel(department.getCurrentAuthorLevel() + 1);
			}
		}
		departmentDao.update(department);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isApplicationGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：isApplicationGroup
	 * 
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isApplicationGroup(Group group)
	{
		boolean flag = false;
		if (group.getParents().iterator().next().getCode()
				.equals(InitParameters.getRootApplicationGroup())) {
			flag = true;
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isDepartmentManager(com.wt.uum2.domain.User, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：isDepartmentManager
	 * 
	 * @param user
	 *            user
	 * @param department
	 *            department
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isDepartmentManager(User user, Department department)
	{
		boolean flag = false;
		Group adminGroup = this.getGroupByCode(InitParameters.getSuperGroupCode());
		List<Group> userGroups = getUserGroups(user);
		if (userGroups.contains(adminGroup)) {
			return true;
		}
		if (department != null) {
			if (CollectionUtils.isNotEmpty(userGroups)) {
				Set<Group> dg = new HashSet<Group>();
				while (department.getParentUUID() != null) {
					dg.addAll(this.getDepartmentManagedGroups(department));
					department = department.getParent();
				}
				if (CollectionUtils.containsAny(userGroups, dg)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isGroupManager(com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：isGroupManager
	 * 
	 * @param user
	 *            user
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isGroupManager(User user, Group group)
	{
		boolean flag = false;
		Group adminGroup = this.getGroupByCode(InitParameters.getSuperGroupCode());
		List<Group> userGroups = this.getUserGroups(user);
		if (userGroups.contains(adminGroup)) {
			return true;
		}
		if (group != null) {
			if (CollectionUtils.isNotEmpty(userGroups)) {
				List<Group> adminGroups = this.getGroupManagedGroups(group);
				if (CollectionUtils.isNotEmpty(adminGroups)
						&& CollectionUtils.containsAny(userGroups, adminGroups)) {
					return true;
				}
				List<Group> parents = this.getAllParentGroupsByGroup(null, adminGroup);
				for (Group parent : parents) {
					adminGroups = this.getGroupManagedGroups(parent);
					if (CollectionUtils.isNotEmpty(adminGroups)
							&& CollectionUtils.containsAny(userGroups, adminGroups)) {
						return true;
					}
				}
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isUserinSuperGroup(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：isUserinSuperGroup
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isUserinSuperGroup(User user)
	{
		if (StringUtils.equals(InitParameters.getSuperUserCode(), user.getId())) {
			return true;
		}
		Group g = getGroupByCode(InitParameters.getSuperGroupCode());
		return getUserGroups(user).contains(g);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getGroupByCode(java.lang.String)
	 */
	/**
	 * 方法说明：getGroupByCode
	 * 
	 * @param code
	 *            code
	 * @return Group
	 */
	@Transactional(readOnly = true)
	public Group getGroupByCode(String code)
	{
		return groupDao.getGroupByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#isAppAssessor(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：isAppAssessor
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isAppAssessor(User user)
	{
		return CollectionUtils.isNotEmpty(getAppGroupsManagedUnderUser(user));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getAppGroupsManagedUnderUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getAppGroupsManagedUnderUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAppGroupsManagedUnderUser(User user)
	{
		List<Group> setGroups = getUserGroups(user);
		List<Group> groups = new ArrayList<Group>();

		if (isUserinSuperGroup(user)) {
			return getAllAppGroup();
		}
		for (Group group : getAllAppGroup()) {
			if (CollectionUtils.containsAny(setGroups, resourceAdminGroupDao.getAdminGroups(group))) {
				groups.add(group);
			}
		}
		return groups;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existDepartmentName(com.wt.uum2.domain.Department, java.lang.String)
	 */
	/**
	 * 方法说明：existDepartmentName
	 * 
	 * @param department
	 *            department
	 * @param newDeptname
	 *            newDeptname
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existDepartmentName(Department department, String newDeptname)
	{
		newDeptname = newDeptname == null ? newDeptname : newDeptname.trim();
		boolean flag = false;
		// List<Department> list =
		// this.getDepartmentChildren(department.getUuid());
		List<Department> list = departmentDao.readChildren(department.getUuid());
		List<Department> list1 = departmentDao.getAbnormalDepartmentsByParent(department.getUuid());
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equalsIgnoreCase(newDeptname)) {
				flag = true;
			}
		}
		if (!flag && !list1.isEmpty()) {
			for (int i = 0; i < list1.size(); i++) {
				if (list1.get(i).getName().equalsIgnoreCase(newDeptname)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existGroupName(com.wt.uum2.domain.Group, java.lang.String)
	 */
	/**
	 * 方法说明：existGroupName
	 * 
	 * @param group
	 *            group
	 * @param newGroupName
	 *            newGroupName
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existGroupName(Group group, String newGroupName)
	{
		boolean flag = false;
		// List<Group> list = this.getGroupsByParentGroup(group);
		List<Group> list = groupDao.getGroupsByParent(group);
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).getName().equalsIgnoreCase(newGroupName)) {
				flag = true;
			}
		}
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getChildGroupByParentGroupAndUserCQ(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getChildGroupByParentGroupAndUserCQ
	 * 
	 * @param parentGroup
	 *            parentGroup
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getChildGroupByParentGroupAndUserCQ(Group parentGroup, User loginUser)
	{
		List<Group> allGroups = groupDao.getGroupsByParent(parentGroup);
		if (parentGroup.getCode().equalsIgnoreCase(InitParameters.getRootGroupCode())) {
			allGroups.remove(getGroupByCode(InitParameters.getRootApplicationGroup()));
		} else if (parentGroup.getCode().equalsIgnoreCase(InitParameters.getRootApplicationGroup())) {
			return getAppGroupsByLoginUser(
					getGroupByCode(InitParameters.getRootApplicationGroup()), loginUser);
		}
		return allGroups;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getAppGroupsByLoginUser(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getAppGroupsByLoginUser
	 * 
	 * @param parent
	 *            parent
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAppGroupsByLoginUser(Group parent, User user)
	{
		return getAppGroupsManagedUnderUser(user);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getChildGroupByParentGroupAndUser(com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getChildGroupByParentGroupAndUser
	 * 
	 * @param parent
	 *            parent
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getChildGroupByParentGroupAndUser(Group parent, User user)
	{
		List<Group> children = groupDao.getGroupsByParent(parent);
		if (this.isUserinSuperGroup(user)) {
			return children;
		}
		List<Group> userGroups = getUserGroups(user);
		List<Group> treeGroups = new ArrayList<Group>();

		if (CollectionUtils.isEmpty(userGroups)) {
			return treeGroups;
		}

		List<Group> adminList = new ArrayList<Group>();

		for (Group group : userGroups) {
			adminList.addAll(groupDao.getGroupManagedUnderGroup(1, -1, group).getList());
		}

		if (CollectionUtils.isEmpty(adminList)) {
			return treeGroups;
		}

		for (Group group : children) {
			List<Group> childs = this.getAllChildGroupByUuid(group.getUuid());
			childs.add(group);
			if (CollectionUtils.containsAny(adminList, childs)) {
				treeGroups.add(group);
			}
		}
		return treeGroups;
	}

	/**
	 * 方法说明：得到登陆用户管理的应用系统
	 * 
	 * @param superGroup
	 *            superGroup
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAppManagedByUser(Group superGroup, User loginUser)
	{
		Iterator<Group> setGroups = loginUser.getGroups().iterator();
		List<Group> allAppGroups = groupDao.getAllAppGroup();
		List<Group> returnGroups = new ArrayList<Group>();
		while (setGroups.hasNext()) {
			Group group = setGroups.next();
			List<Group> managedGroups = groupDao.getGroupManagedUnderGroup(1, 99999, group)
					.getList();
			for (int i = 0; i < managedGroups.size(); i++) {
				Group g = managedGroups.get(i);
				if (allAppGroups.contains(g)) {
					returnGroups.add(g);
				}
			}
		}
		return returnGroups;
	}

	/**
	 * 得到组的父组(递归)
	 * 
	 * @param listGroups
	 *            listGroups
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAllParentGroupsByGroup(List<Group> listGroups, Group group)
	{
		List<Group> groupList = groupDao.getAllGroupsByChildUUID(group.getUuid());
		if (CollectionUtils.isNotEmpty(groupList)) {
			return groupList;
		}
		if (listGroups == null) {
			listGroups = new ArrayList<Group>();
		}
		List<Group> parentGroups = this.getParentsGroupsByGroup(group);
		if (parentGroups != null && parentGroups.size() > 0) {
			listGroups.addAll(parentGroups);
			for (int i = 0; i < parentGroups.size(); i++) {
				listGroups.addAll(this.getAllParentGroupsByGroup(listGroups, parentGroups.get(i)));
			}
		}
		return listGroups;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getReAuthorUserMembersByLoginUser(int, int, com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getReAuthorUserMembersByLoginUser
	 * 
	 * @param size
	 *            size
	 * @param pagesize
	 *            pagesize
	 * @param loginUser
	 *            loginUser
	 * @param appGroup
	 *            appGroup
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getReAuthorUserMembersByLoginUser(int size, int pagesize, User loginUser,
			Group appGroup)
	{
		List<Group> userAppGroups = this.getAdminGroupByLoginUser(loginUser);
		return userDao.getReAuthorUserMembersByLoginUser(size, pagesize, loginUser, appGroup,
				userAppGroups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchReAuthorUserMembersByLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明： searchReAuthorUserMembersByLoginUser
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
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchReAuthorUserMembersByLoginUser(int size, int pagesize,
			Condition condition, User loginUser, Group appgroup)
	{
		List<Group> userAppGroups = this.getAdminGroupByLoginUser(loginUser);
		return userDao.searchReAuthorUserMembersByLoginUser(size, pagesize, condition, loginUser,
				appgroup, userAppGroups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUsersUnderGroupAndLoginUser(int, int, com.wt.uum2.domain.Group, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUsersUnderGroupAndLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pageSize
	 *            pageSize
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUsersUnderGroupAndLoginUser(int page, int pageSize, Group group,
			User user)
	{
		// List<Group> userAppGroups = this.getAdminGroupByLoginUser(user);
		return userDao.getUsersUnderGroupAndLoginUser(page, pageSize, group, user,
				new ArrayList<Group>());
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentByDeptCode(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByDeptCode
	 * 
	 * @param deptCode
	 *            deptCode
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentByDeptCode(String deptCode)
	{
		return departmentDao.getDepartmentByDeptCode(deptCode);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchUsersByConditionAndLoginUser(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndLoginUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param loginUser
	 *            loginUser
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchUsersByConditionAndLoginUser(int page, int pagesize,
			Condition condition, User loginUser)
	{
		List<Group> userAppGroups = this.getAdminGroupByLoginUser(loginUser);
		return userDao.searchUsersByConditionAndLoginUser(page, pagesize, condition, loginUser,
				userAppGroups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchUsersByConditionAndOrgCode(int, int, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUsersByConditionAndOrgCode
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
	public UserPageResult searchUsersByConditionAndOrgCode(int page, int pagesize,
			Condition condition, String orgCode)
	{
		return userDao.searchUsersByConditionAndOrgCode(page, pagesize, condition, orgCode);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUsersUnderApplicationByOrg(int, int, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getUsersUnderApplicationByOrg
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
	@Transactional(readOnly = true)
	public UserPageResult getUsersUnderApplicationByOrg(int page, int pagesize, Department dept,
			Group group)
	{
		if (dept == null) {
			return getUserUnderGroupOrderByDept(page, pagesize, group);
		}
		return userDao.getUsersUnderApplicationByOrg(page, pagesize, dept, group);
	}

	/**
	 * 得到登陆用户的应用系统组
	 * 
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAdminGroupByLoginUser(User loginUser)
	{
		List<Group> userAppGroups = this.getAppGroupsManagedUnderUser(loginUser);
		Iterator<Group> iterator = loginUser.getGroups().iterator();
		List<Group> appGroups = groupDao.getAllAppGroup();
		while (iterator.hasNext()) {
			Group userGroup = iterator.next();
			if (userGroup.getCode().contains("Admin")) {// 找到用户的管理组人员
				userAppGroups.add(userGroup);
			}
			for (int i = 0; i < appGroups.size(); i++) {
				Group appGroup = appGroups.get(i);
				if (userGroup.getCode().equals(appGroup.getCode())) {// 找到用户的应用组
					userAppGroups.add(appGroup);
				}
			}
		}
		return userAppGroups;
	}

	/**
	 * 判断是否是基层管理员
	 * 
	 * @param loginUser
	 *            loginUser
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isSubGroupManager(User loginUser)
	{
		boolean flag = false;
		Iterator<Group> iterator = loginUser.getGroups().iterator();
		while (iterator.hasNext()) {
			Group group = iterator.next();
			String code = group.getCode();
			code = code.toLowerCase();
			if (code.contains("mhadmin")) {// 判断门户管理员组的通用标识
				flag = true;
			}
		}
		return flag;
	}

	@Transactional(readOnly = true)
	public List<Group> getAllAppGroup()
	{
		return groupDao.getAllAppGroup();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existDepartmentCode(java.lang.String)
	 */
	/**
	 * 方法说明：existDepartmentCode
	 * 
	 * @param deptCode
	 *            deptCode
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existDepartmentCode(String deptCode)
	{
		deptCode = deptCode == null ? deptCode : deptCode.trim();
		Department department = departmentDao.getDepartmentByDeptCode(deptCode);
		if (department == null) {
			return false;
		}
		return true;
	}

	@Transactional(readOnly = true)
	public List<Group> getDefaultAddAppGroup()
	{
		return groupDao.getDefaultAddAppGroup();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentChildren(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getDepartmentChildren(String parentUUID, String loginUser)
	{
		User user = userDao.readById(loginUser);
		return this.getDepartmentChildren(parentUUID, user);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentNameAndOrgCode(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentNameAndOrgCode
	 * 
	 * @param deptName
	 *            deptName
	 * @param orgCode
	 *            orgCode
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentNameAndOrgCode(String deptName, String orgCode)
	{
		return departmentDao.getDepartmentByNameAndOrgCode(deptName, orgCode);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteUserWL(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：deleteUserWL
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteUserWL(User user)
	{
		List<UserAuthor> authorList = this.getUserAuthorsByUser(user);
		attributeDao.deleteAttributesByResource(user);
		for (int i = 0; i < authorList.size(); i++) {
			userAuthorDao.delete(authorList.get(i));
		}
		resourceMappingDao.deleteResourceMappingByResource(user);

		userDao.removePrimaryUser(user);

		userGroupDao.delete(user);

		userDepartmentDao.delete(user);

		resourceAdminGroupDao.removeByResource(user);

		userDao.delete(user);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getCreatedDepartmentsManagedUnderUser(int, int, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getCreatedDepartmentsManagedUnderUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getCreatedDepartmentsManagedUnderUser(int page, int pagesize, User user)
	{
		Set<Group> setGroup = user.getGroups();
		List<Group> groups = new ArrayList<Group>();
		groups.addAll(setGroup);
		return departmentDao.getCreatedDepartmentsManagedUnderGroups(page, pagesize, groups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#countDepartment()
	 */
	/**
	 * 方法说明：countDepartment
	 * 
	 * @return int
	 */
	@Transactional(readOnly = true)
	public int countDepartment()
	{
		return departmentDao.countDepartment();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#existDepartmentRTXCode(long)
	 */
	/**
	 * 方法说明：existDepartmentRTXCode
	 * 
	 * @param rtxCode
	 *            rtxCode
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existDepartmentRTXCode(long rtxCode)
	{
		return departmentDao.existDepartmentRTXCode(rtxCode);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getAllDepartmentByChildUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getAllDepartmentByChildUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getAllDepartmentByChildUuid(String uuid)
	{
		if (StringUtils.isBlank(uuid)) {
			return null;
		}
		return departmentPathDao.readAllElderDepts(departmentDao.read(uuid));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getAllDepartmentByChild(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getAllDepartmentByChild
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getAllDepartmentByChild(Department dept)
	{
		if (dept == null) {
			return null;
		}
		return departmentPathDao.readAllElderDepts(dept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getAllChildGroupByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getAllChildGroupByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getAllChildGroupByUuid(String uuid)
	{
		return groupDao.getAllGroupsByParent(groupDao.getGroupByUuid(uuid));
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#countDepartmentForRtx()
	 */
	/**
	 * 方法说明：countDepartmentForRtx
	 * 
	 * @return Long
	 */
	@Transactional(readOnly = true)
	public Long countDepartmentForRtx()
	{
		int num = departmentDao.countDepartmentForRtx();
		boolean flag = false;
		while (true) {
			flag = existDepartmentRTXCode(num);
			if (flag == true) {
				num++;
			} else {
				return Long.valueOf(num);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#authorDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：authorDepartment
	 * 
	 * @param department
	 *            department
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int authorDepartment(Department department)
	{
		if (department.getCurrentAuthorLevel() > 0) {
			Long currentLevel = department.getCurrentAuthorLevel();
			currentLevel = currentLevel - 1;
			department.setCurrentAuthorLevel(currentLevel);
			if (currentLevel < 1) {
				department.setStatus(ResourceStatus.NORMAL.intValue());
			}
		}
		departmentDao.update(department);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getParentGroupCodeOnGS(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getParentGroupCodeOnGS
	 * 
	 * @param group
	 *            group
	 * @return String
	 */
	@Transactional(readOnly = true)
	public String getParentGroupCodeOnGS(Group group)
	{
		String groupCode = String.valueOf("");
		if (group == null) {
			return groupCode;
		}
		List<Group> parentgroups = getParentsGroupsByGroup(group);
		Group cmsgroup = getGroupByCode("cmsManagerGroup");
		Group portgroup = getGroupByCode("portalGroup");
		if (parentgroups.contains(cmsgroup)) {
			groupCode = "cmsManagerGroup";
		} else if (parentgroups.contains(portgroup)) {
			groupCode = "portalGroup";
		} else {
			groupCode = "";
		}
		return groupCode;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentByCode(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByCode
	 * 
	 * @param code
	 *            code
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getDepartmentByCode(String code)
	{
		return departmentDao.getDepartmentByCode(code);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserManagedGroupsByHql(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserManagedGroupsByHql
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getUserManagedGroupsByHql(User user)
	{
		return groupDao.getGroupsByManagedUser(user);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentChildren(java.lang.String, com.wt.uum2.domain.User, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentChildren
	 * 
	 * @param uuid
	 *            uuid
	 * @param loginUser
	 *            loginUser
	 * @param flag
	 *            flag
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getDepartmentChildren(String uuid, User loginUser, String flag)
	{
		if (flag == null || !flag.equals("true")) {
			flag = "false";
			return departmentDao.readChildren(uuid, loginUser, flag);
		} else {
			return this.getDepartmentChildren(uuid, loginUser);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#addGroupParentGroups(com.wt.uum2.domain.Group, java.util.Collection)
	 */
	/**
	 * 方法说明：addGroupParentGroups
	 * 
	 * @param groupChildren
	 *            groupChildren
	 * @param groups
	 *            groups
	 */
	@Transactional(readOnly = false)
	public void addGroupParentGroups(Group groupChildren, Collection<Group> groups)
	{
		for (Group group : groups) {
			GroupRelationship gr = new GroupRelationship();
			gr.setChild(groupChildren);
			gr.setParent(group);
			groupRelationshipDao.save(gr);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#deleteGroupParentGroups(com.wt.uum2.domain.Group, java.util.Collection)
	 */
	/**
	 * 方法说明：deleteGroupParentGroups
	 * 
	 * @param groupChildren
	 *            groupChildren
	 * @param groups
	 *            groups
	 */
	@Transactional(readOnly = false)
	public void deleteGroupParentGroups(Group groupChildren, Collection<Group> groups)
	{
		groupRelationshipDao.delete(groupChildren, groups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserAdminGroups(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserAdminGroups
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getUserAdminGroups(User user)
	{
		List<Group> userGroups = getUserGroups(user);
		List<Group> treeGroups = new ArrayList<Group>();
		if (userGroups != null && userGroups.size() > 0) {
			for (Group group : userGroups) {
				List<Group> adminList = groupDao.getGroupManagedUnderGroup(1, -1, group).getList();
				treeGroups.addAll(adminList);
			}
		}
		return treeGroups;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserPrimaryDepartment(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserPrimaryDepartment
	 * 
	 * @param user
	 *            user
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getUserPrimaryDepartment(User user)
	{
		return departmentDao.getPrimaryDepartmentByUser(user);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getParentsGroup(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getParentsGroup
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getParentsGroup(Group group)
	{
		return groupDao.getGroupsByChild(group);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getParentDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getParentDepartment
	 * 
	 * @param dept
	 *            dept
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getParentDepartment(Department dept)
	{
		String parentUuid = dept.getParentUUID();
		return this.getDepartmentByUUID(parentUuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getSubDepartments(int, int, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getSubDepartments
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param parentDept
	 *            parentDept
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getSubDepartments(int page, int pagesize, Department parentDept)
	{
		return departmentDao.getDepartmentsByParent(page, pagesize, parentDept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getDepartmentsByParent(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getDepartmentsByParent
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getDepartmentsByParent(Department dept)
	{
		return departmentDao.getDepartmentsByParent(dept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserAccountInApplication(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getUserAccountInApplication
	 * 
	 * @param userId
	 *            userId
	 * @param groupUuid
	 *            groupUuid
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<String> getUserAccountInApplication(String userId, String groupUuid)
	{
		List<String> result = new ArrayList<String>();
		User user = this.getUserByUserId(userId);
		Group app = this.getGroupByUuid(groupUuid);
		List<String> keys = new ArrayList<String>();
		String account = InitParameters.getAppAccountLocal().replace("XXXX", app.getCode());
		keys.add(account);
		List<Attribute> atts = this.getAttributesByAttributeTypeIdKey(user, keys);
		if (atts != null && atts.size() > 0) {
			for (int i = 0; i < atts.size(); i++) {
				Attribute att = atts.get(i);
				if (att.getType().getId().equalsIgnoreCase(account)) {
					if (att.getAttValues().size() <= 0) {
						result.add("");
					} else {
						AttributeValue av = att.getAttValues().iterator().next();
						result.add((String) av.getValue());
					}
				}
			}
		} else {
			result.add("");
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentsByName(java.lang.String, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> searchDepartmentsByName(String name, User loginUser)
	{
		boolean isSuper = false;
		if (CollectionUtils.isNotEmpty(getUserGroups(loginUser))) {
			isSuper = isUserinSuperGroup(loginUser);
		}
		if (isSuper) {
			return departmentDao.searchDepartmentsByName(name, null);
		} else {
			String orgCode = this.getUserPrimaryDepartment(loginUser).getOrgCode();
			return departmentDao.searchDepartmentsByName(name, orgCode);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentsByName(java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> searchDepartmentsByName(String name)
	{
		return departmentDao.searchDepartmentsByName(name, null);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentsByCode(java.lang.String, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchDepartmentsByCode
	 * 
	 * @param code
	 *            code
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> searchDepartmentsByCode(String code, User loginUser)
	{
		boolean isSuper = false;
		if (CollectionUtils.isNotEmpty(getUserGroups(loginUser))) {
			isSuper = isUserinSuperGroup(loginUser);
		}
		if (isSuper) {
			return departmentDao.searchDepartmentsByCode(code, null);
		} else {
			String orgCode = this.getUserPrimaryDepartment(loginUser).getOrgCode();
			return departmentDao.searchDepartmentsByCode(code, orgCode);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getUserOrgDepartment(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserOrgDepartment
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Department> getUserOrgDepartment(User user)
	{
		List<Department> depts = getUserDepartments(user);
		List<Department> list = new ArrayList<Department>();
		if (CollectionUtils.isNotEmpty(depts)) {
			for (Department department : depts) {
				String orgCode = department.getOrgCode();
				Department orgDept = this.getDepartmentByDeptCode(orgCode);
				list.add(orgDept);
			}
			if (CollectionUtils.isEmpty(list)) {
				list.add(getDepartmentRoot());
			}
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getResourceManagedGroups(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getResourceManagedGroups
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Group> getResourceManagedGroups(Resource resource)
	{
		switch (resource.getType()) {
		case 2:
			return departmentDao.getGroupsByManagedDepartment((Department) resource);
		case 1:
			return groupDao.getGroupsByManagedGroup((Group) resource);
		default:
			return userDao.getUserManagedGroups((User) resource);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentsByNameCount(java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByNameCount
	 * 
	 * @param name
	 *            name
	 * @return long
	 */
	@Transactional(readOnly = true)
	public long searchDepartmentsByNameCount(String name)
	{
		return departmentDao.searchDepartmentsByNameCount(name, null);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchDepartmentsByName(java.lang.String, int, int)
	 */
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
	@Transactional(readOnly = true)
	public List<Department> searchDepartmentsByName(String name, int start, int max)
	{
		return departmentDao.searchDepartmentsByName(name, null, start, max);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchUsersByDepartmentCondition(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.DepartmentCondition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchUsersByDepartmentCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param dc
	 *            dc
	 * @param loginUser
	 *            loginUser
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult<User> searchUsersByDepartmentCondition(Integer page, Integer pagesize,
			DepartmentCondition dc, User loginUser)
	{
		if (isUserinSuperGroup(loginUser)) {
			return userDao.searchUsersByDepartmentCondition(page, pagesize, dc, null);
		} else {
			return userDao.searchUsersByDepartmentCondition(page, pagesize, dc, loginUser);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getOrganization(java.lang.String)
	 */
	/**
	 * 方法说明：getOrganization
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @return Department
	 */
	@Transactional(readOnly = true)
	public Department getOrganization(String deptUUID)
	{
		// 公司一级的level值为2
		return departmentPathDao.readElderDeptByLevel(deptUUID, 2);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getReAuthorUsersUnderApplicationByOrg(java.lang.Integer, java.lang.Integer, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getReAuthorUsersUnderApplicationByOrg
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
	@Transactional(readOnly = true)
	public UserPageResult getReAuthorUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Department dept, Group group)
	{
		if (dept == null) {
			return getReAuthorUserMembersByLoginUser(page, pagesize, getLoginUser(), group);
		}
		return userDao.getReAuthorUsersUnderApplicationByOrg(page, pagesize, dept, group);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchUsersUnderApplicationByOrg(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.Department, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：searchUsersUnderApplicationByOrg
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
	@Transactional(readOnly = true)
	public UserPageResult searchUsersUnderApplicationByOrg(Integer page, Integer pagesize,
			Condition condition, Department org, Group group)
	{
		if (org == null) {
			return searchUsersMembersByLoginUser(page, pagesize, condition, getLoginUser(), group);
		}
		return userDao.searchUsersUnderApplicationByOrg(page, pagesize, condition, org, group);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getNotExistsUserId(java.lang.String)
	 */
	/**
	 * 方法说明：getNotExistsUserId
	 * 
	 * @param userid
	 *            userid
	 * @return String
	 */
	public String getNotExistsUserId(String userid)
	{
		int i = 0;
		String temp = userid;
		while (existUserId(temp)) {
			i++;
			temp = userid + i;
		}
		List<Group> appgroup = getAllAppGroup();
		if (InitParameters.isCqGroupAuthor() && CollectionUtils.isNotEmpty(appgroup)) {
			Set<String> keySet = new HashSet<String>();
			for (Group appGroup : appgroup) {
				keySet.add(InitParameters.getAppAccountLocal().replace("XXXX", appGroup.getCode()));
			}
			List<AttributeType> attList = getAttributeTypeByKeySet(keySet);
			List<User> userList = getUserByAttributes(attList, temp);
			while (CollectionUtils.isNotEmpty(userList)) {
				i++;
				temp = userid + i;
				userList = getUserByAttributes(attList, temp);
			}
		}
		return temp;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#rollbackUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：rollbackUser
	 * 
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int rollbackUser(User user)
	{
		List<UserAuthor> au = this.getUserAuthorsByUser(user);
		if (au != null) {
			int length = au.size();
			if (user.getCurrentAuthorLevel() >= length) {
				user.setStatus(ResourceStatus.ROLLBACK.intValue());
			} else {
				user.setCurrentAuthorLevel(user.getCurrentAuthorLevel() + 1);
			}
		}
		if (user.getStatus() == ResourceStatus.ROLLBACK.intValue()) {
			this.deleteUserWL(user);
		} else {
			userDao.update(user);
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#getCreatedUsersManagedUnderUser(int, int, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getCreatedUsersManagedUnderUser
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getCreatedUsersManagedUnderUser(int page, int pagesize, User user)
	{
		if (this.isUserinSuperGroup(user)) {
			return userDao.getCreatedUsers(page, pagesize);
		}
		Set<Group> setGroup = user.getGroups();
		List<Group> groups = new ArrayList<Group>();
		groups.addAll(setGroup);
		return userDao.getCreatedUsersManagedUnderGroups(page, pagesize, groups);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMService#searchUsersManagedByUserGroups(int, int, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchUsersManagedByUserGroups
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
	@Transactional(readOnly = true)
	public UserPageResult searchUsersManagedByUserGroups(int page, int pagesize,
			Condition condition, User user)
	{
		return userDao.searchUsersManagedByAdmGroups(page, pagesize, condition,
				getUserGroups(user), isUserinSuperGroup(user));
	}

	public Department getDepartmentByAttribute(AttributeType t, String deptCode) {
		List<Resource> s = super.getResourceListByAttribute(t.getId(), deptCode);
		if(CollectionUtils.isEmpty(s)||s.get(0)==null){
			return null;
		}
		return (Department)s.get(0);
	}

}