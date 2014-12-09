package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceAdminGroup;

/**
 * <pre>
 * 业务名: zu 
 * 功能说明: zu 
 * 编写日期:	2011-11-9
 * 作者:	LIu
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ResourceAdminGroupDao extends BaseDao<ResourceAdminGroup> {

	/**
	 * 添加某资源的管理组
	 * 
	 * @param group
	 *            管理组
	 * @param resource
	 *            资源
	 */
	public void add(Group group, Resource resource);

	/**
	 * 取得某资源的管理组
	 * 
	 * @param resource
	 *            资源
	 * @return 管理组的集合
	 */
	public List<Group> getAdminGroups(Resource resource);

	/**
	 * 移除某资源的管理组
	 * 
	 * @param group
	 *            管理组
	 * @param resource
	 *            资源
	 */
	public void remove(Group group, Resource resource);

	/**
	 * 移除某资源的所有管理组
	 * 
	 * @param resource
	 *            资源
	 */
	public void removeByResource(Resource resource);

	/**
	 * 方法说明：移除某管理组相关管理权限
	 *
	 * @param adminGroup 管理组
	 */
	public void removeByAdminGroup(Group adminGroup);

}
