package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-26
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface GroupDao extends BaseDao<Group>
{

	/**
	 * 方法说明：获得所有组集合
	 * 
	 * @return List
	 */
	public List<Group> getAllGroups();

	/**
	 * 方法说明：通过组的唯一标识获得组对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return Group
	 */
	public Group getGroupByUuid(String uuid);

	/**
	 * 方法说明：通过父组获得所有子组
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByParent(Group group);

	/**
	 * 方法说明：获得根组对象
	 * 
	 * @return Group
	 */
	public Group getRootGroup();

	/**
	 * 方法说明：通过组获得该组管理的分页组
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
	 * 方法说明：通过管理组搜索管理组集合管理的组集合
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
	 * @return UserPageResult
	 */
	public UserPageResult searchGroupsManagedByAdmGroups(int page, int pagesize,
			Condition condition, List<Group> admGroups, boolean isInSuperGroup);

	/**
	 * 方法说明：获得组下的所有组集合包括子组
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getAllGroupsByParent(Group group);

	/**
	 * 方法说明：通过父组的唯一标识获得子组集合
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	public List<Group> getGroupsByParentUUID(String uuid);

	/**
	 * 方法说明：搜索分页组
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult getGroupsByCondition(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：通过组的编码获得组对象
	 * 
	 * @param code
	 *            code
	 * @return Group
	 */
	public Group getGroupByCode(String code);

	/**
	 * 方法说明：获得所有的应用系统组
	 * 
	 * @return List
	 */
	public List<Group> getAllAppGroup();

	/**
	 * 方法说明：获得应用系统组集合
	 * 
	 * @return List
	 */
	public List<Group> getDefaultAddAppGroup();

	/**
	 * 方法说明：通过子组的uuid利用oracle的迭代方法取得其所有的父节点
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	public List<Group> getAllGroupsByChildUUID(String uuid);

	/**
	 * 
	 * 方法说明：获取可管理组的组
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getGroupsByManagedGroup(Group resource);

	/**
	 * 
	 * 方法说明：取得可管理用户的组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getGroupsByManagedUser(User user);

	/**
	 * 方法说明：获取用户所在组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getGroupsByUser(User user);

	/**
	 * 方法说明：获取用户的管理组
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Group> getAdminGroupsByUser(User user);

	/**
	 * 方法说明：getGroupsByChild
	 * 
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Group> getGroupsByChild(Group group);

	/**
	 * 方法说明：判断组下是否存在被管资源
	 * 
	 * @param group
	 *            group
	 * @return boolean
	 */
	public boolean existsResourceUnderGroups(Group group);

	/**
	 * 方法说明：取得资源的管理组
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getAdminGroups(Resource resource);

}