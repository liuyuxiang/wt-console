package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.ResourceSync;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ResourceSyncDao extends BaseDao<ResourceSync>
{

	/**
	 * 通过资源类型获得该类型下所有需要同步的资源分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	public UserPageResult getResourceSyncByResource(int page, int pagesize, Integer resourceType);

	/**
	 * 通过资源类型得到该类型下所有需要同步的资源集合
	 * 
	 * @param resourceType
	 *            resourceType
	 * @return List
	 */
	public List<ResourceSync> getResourceSyncByResource(Integer resourceType);

	/**
	 * 通过唯一标识获得需要同步的资源
	 * 
	 * @param uuid
	 *            uuid
	 * @return ResourceSync
	 */
	public ResourceSync getResourceSyncByUuid(String uuid);

	/**
	 * 通过同步属性名称获得同步属性集合
	 * 
	 * @param propName
	 *            propName
	 * @return List
	 */
	public List<ResourceSync> getResourceSyncByPropName(String propName);

}
