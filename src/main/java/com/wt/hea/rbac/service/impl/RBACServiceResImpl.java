package com.wt.hea.rbac.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.wt.hea.common.infrastructure.configuration.impl.Configurate;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.rbac.service.RBACService;
import com.hirisun.hea.api.domain.Department;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.StringParse;
import com.wt.uum2.service.UUMService;

/**
 * @author liuyx
 *
 */
public class RBACServiceResImpl implements RBACService
{
	/**
	 * 
	 */
	protected static String USERNAMESUPPER = null;
	/**
	 * 
	 */
	protected static String PASSWORDSUPPER = null;
	
	@Resource
	private UUMService uumService;
	
	static {
		String propPath = "com/hirisun/hea/common/config/hea_constant.properties";
		USERNAMESUPPER = Configurate.getInstance().getPropValue("username", propPath);
		PASSWORDSUPPER = Configurate.getInstance().getPropValue("password", propPath);

	}

	/**
	 * log
	 */
	private static final Logger LOG = LoggerService.getInstance(RBACServiceResImpl.class);

	/**
	 * @param userId
	 *            userId
	 * @return boolean
	 */
	public boolean existUserId(String userId)
	{
		LOG.info("判断用户是否存在 userId" + userId);
		return uumService.existUserId(userId);
	}

	/**
	 * @param uuid
	 *            uuid
	 * @return Department
	 */
	public Department getDepartmentByUUID(String uuid)
	{
		LOG.info("获取部门 UUID：" + uuid);
		com.wt.uum2.domain.Department dept = uumService.getDepartmentByUUID(uuid);
		return buildSimpleDepartment(dept);
	}

	private Department buildSimpleDepartment(
			com.wt.uum2.domain.Department dept) {
		Department d = new Department();
		if(StringUtils.isNotBlank(dept.getUuid())){
			d.setCode(dept.getDeptCode());
			d.setName(dept.getName());
			d.setOrder(dept.getOrder());
			d.setParentUUID(dept.getParentUUID());
			d.setPath(dept.getPath());
			d.setUuid(dept.getUuid());
		}
		return d;
	}

	/**
	 * @param parentUUID
	 *            parentUUID
	 * @return List<Department>
	 */
	public List<Department> getDepartmentChildren(String parentUUID)
	{
		LOG.info("获取下级部门 parentUUID:" + parentUUID);
		
		List<Department> depts = new ArrayList<Department>();
		
		for (com.wt.uum2.domain.Department dept : uumService.getDepartmentChildren(parentUUID)) {
			depts.add(buildSimpleDepartment(dept));
		}
		
		return depts;
	}

	/**
	 * @param department
	 *            department
	 * @return List<Group>
	 */
	public List<Group> getDepartmentManagedGroups(Department department)
	{
		LOG.info("获取部门的管理组");
		// return uum.department().
		return null;
	}

	/**
	 * @param uuid
	 *            uuid
	 * @return Group
	 */
	public Group getGroupByUuid(String uuid)
	{
		LOG.info("获取组 UUID:" + uuid);
		Group group = null;
		try {
			group = buildSimpleGroup(uumService.getGroupByUuid(uuid));
		} catch (Exception e) {
			LOG.error("找不到组ID" + uuid);
		}
		return group;
	}

	private Group buildSimpleGroup(com.wt.uum2.domain.Group group) {
		Group g = new Group();
		if(StringUtils.isNotBlank(group.getUuid())){
			g.setCode(group.getCode());
			g.setName(group.getName());
			g.setOrder(group.getOrder());
			g.setUuid(group.getUuid());
		}
		return g;
	}

	/**
	 * @param uuid
	 *            uuid
	 * @return List<Group>
	 */
	public List<Group> getGroupsByParentGroup(String uuid)
	{
		LOG.info("获取下级组 parentUUID:" + uuid);
		List<Group> gs = new ArrayList<Group>();
		for (com.wt.uum2.domain.Group group : uumService.getGroupsByParentGroup(uumService.getGroupByUuid(uuid))) {
			gs.add(buildSimpleGroup(group));
		}
		return gs;
	}

	/**
	 * @param id
	 *            id
	 * @return User
	 */
	public User getUserByUserId(String id)
	{
		LOG.info("获取用户 ID:" + id);
		return buildSimpleUser(uumService.getUserByUserId(id));
	}

	private User buildSimpleUser(com.wt.uum2.domain.User user) {
		User u = new User();
		if(StringUtils.isNotBlank(user.getUuid())){
			u.setId(user.getId());
			u.setName(user.getName());
			u.setOrder(user.getOrder());
			u.setPassword(user.getPassword());
			u.setUuid(user.getUuid());
		}
		return u;
	}

	/**
	 * @param uuid
	 *            uuid
	 * @return User
	 */
	public User getUserByUuid(String uuid)
	{
		LOG.info("获取用户UUID：" + uuid);
		return buildSimpleUser(uumService.getUserByUuid(uuid));
	}

	/**
	 * @param user
	 *            user
	 * @return Department
	 */
	public Department getUserDepartment(User user)
	{
		LOG.info("获取用户所在部门");
		com.wt.uum2.domain.User u = uumService.getUserByUuid(user.getUuid());
		
		return buildSimpleDepartment(uumService.getUserPrimaryDepartment(u));
	}

	/**
	 * @param user
	 *            user
	 * @return String
	 */
	public String getUserOrgCode(User user)
	{
		LOG.info("获取用户所在单位");
		Department dept = getUserDepartment(user);
		if(dept==null){
			return getRootDepartment().getCode();
		}
		String deptUuid = dept.getUuid();
		com.wt.uum2.domain.Department org = uumService.getOrganization(deptUuid);
		if(org==null){
			return getRootDepartment().getCode();
		}
		return buildSimpleDepartment(org).getCode();
	}

	/**
	 * @param user
	 *            user
	 * @return List<Group>
	 */
	public List<Group> getUserGroups(User user)
	{
		LOG.info("获取用户的关联组");
		List<Group> gs = new ArrayList<Group>();
		com.wt.uum2.domain.User u = uumService.getUserByUuid(user.getUuid());
		for (com.wt.uum2.domain.Group group : uumService.getUserGroups(u)) {
			gs.add(buildSimpleGroup(group));
		}
		return gs;
	}

	/**
	 * @param id
	 *            id
	 * @param pwd
	 *            pwd
	 * @return boolean
	 */
	public boolean userValidate(String id, String pwd)
	{
		LOG.info("验证用户");
		com.wt.uum2.domain.User u = uumService.getUserByUserId(id);
		if (u == null) {
			return false;
		}
		if (InitParameters.isPlainPassword()) {
			return StringUtils.equals(u.getPlainPassword(), pwd);
		} else {
			if (StringUtils.equals("true", InitParameters.getMD5EncodePassTurnOn())) {
				pwd = StringParse.md5(pwd);
			}
			return StringUtils.equals(u.getPlainPassword(), pwd);
		}
	}

	/**
	 * @return List<Group>
	 */
	public List<Group> getAllGroups()
	{
		LOG.info("获取所有组");
		List<Group> gs = new ArrayList<Group>();
		for (com.wt.uum2.domain.Group group : uumService.getAllGroups()) {
			gs.add(buildSimpleGroup(group));
		}
		return gs;
	}

	/**
	 * 访方法只验证存储数据 库内的密码字符串
	 * 
	 * @param user
	 *            user
	 * @return boolean
	 */
	public boolean isAdmin(User user)
	{
		LOG.info("判断用户是不是管理员");
		// if (USERNAMESUPPER.equals(user.getId()) && PASSWORDSUPPER.equals(user.getPassword())) {
		// return true;
		// }

		List<String> groupCodes = new ArrayList<String>();
		groupCodes.add("wpsadmins");
		return isInGroups(user.getUuid(), groupCodes);
	}

	/**
	 * 方法说明：
	 * 
	 * @param userUUID
	 * @param groupCodes
	 * @return
	 */
	private boolean isInGroups(String userUUID, List<String> groupCodes)
	{
		boolean success = false;
		com.wt.uum2.domain.User user = uumService.getUserByUuid(userUUID);
		List<com.wt.uum2.domain.Group> groupList = uumService.getUserGroups(user);
		for (com.wt.uum2.domain.Group group : groupList) {
			if(groupCodes.contains(group.getCode())){
				return true;
			}
		}
		return success;
	}

	/**
	 * @param groupId
	 *            groupId
	 * @return List<Group>
	 */
	public List<Group> getManagerGroup(String groupId)
	{
		LOG.info("获取可管理的组");
		List<Group> gs = new ArrayList<Group>();
		com.wt.uum2.domain.Group group = uumService.getGroupByUuid(groupId);
		if (group == null) {
			return gs;
		}
		List<com.wt.uum2.domain.Group> groups = uumService.getGroupManagedUnderGroup(0, 999, group)
						.getList();
		
		for (com.wt.uum2.domain.Group group2 : groups) {
			gs.add(buildSimpleGroup(group2));
		}

		return gs;
	}

	/**
	 * @param groupIds
	 *            groupIds
	 * @return List<Group>
	 */
	public List<Group> getManagerGroups(String[] groupIds)
	{
		LOG.info("获取用管理的组");
		
		List<Group> groupList = new ArrayList<Group>();
		for (String gid : groupIds) {
			groupList.addAll(getManagerGroup(gid));
		}
		return groupList;
	}

	/**
	 * @param groupId
	 *            groupId
	 * @return List<Group>
	 * 
	 */
	public List<Group> getParentGroup(String groupId)
	{
		LOG.info("获取父级组");
		List<Group> gs = new ArrayList<Group>();
		com.wt.uum2.domain.Group group = uumService.getGroupByUuid(groupId);
		if(group!=null){
			for (com.wt.uum2.domain.Group g : uumService.getParentsGroupsByGroup(group)) {
				gs.add(buildSimpleGroup(g));
			}
		}
		return gs;
		
		
	}

	/**
	 * @return Department
	 * 
	 */
	public Department getRootDepartment()
	{
		LOG.info("获取根级部门");
		return buildSimpleDepartment(uumService.getDepartmentRoot());
	}

	/**
	 * @return Group
	 * 
	 */
	public Group getRootGroup()
	{
		LOG.info("获取根级组");
		return buildSimpleGroup(uumService.getRootGroup());
	}

	/**
	 * 
	 * @param groupUuid
	 *            groupUuid
	 * @return boolean
	 */
	public boolean hasChildGroup(String groupUuid)
	{
		com.wt.uum2.domain.Group g = uumService.getGroupByUuid(groupUuid);
		if(g==null){
			return false;
		}
		return CollectionUtils.isNotEmpty(uumService.getGroupsByParentGroup(g));
	}

}
