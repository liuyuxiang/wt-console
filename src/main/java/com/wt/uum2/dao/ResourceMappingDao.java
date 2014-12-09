package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceMapping;

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
public interface ResourceMappingDao extends BaseDao<ResourceMapping>
{
	/**
	 * 
	 * 方法说明：取得资源映射对象
	 * 
	 * @param uuid
	 *            uuid
	 * @return ResourceMapping
	 */
	public ResourceMapping getResourceMapping(String uuid);

	/**
	 * 
	 * 方法说明：取得资源映射对象列表
	 * 
	 * @return List
	 */
	public List<ResourceMapping> getResourceMappingList();

	/**
	 * 方法说明：取得资源映射对象列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<ResourceMapping> getResourceMapping(Integer page, Integer pagesize);

	/**
	 * 方法说明：根据资源取得资源映射对象列表
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<String> getMappingIdByResouce(Resource resource);

	/**
	 * 
	 * 方法说明：根据映射对象取得资源映射对象列表
	 * 
	 * @param mappingid
	 *            mappingid
	 * @return List
	 */
	public List<Resource> getResouceByMappingId(String mappingid);

	/**
	 * 方法说明：根据资源和映射取得资源映射对象
	 * 
	 * @param resource
	 *            resource
	 * @param userCode
	 *            userCode
	 * @return ResourceMapping
	 */
	public ResourceMapping getResourceMapping(Resource resource, String userCode);

	/**
	 * 方法说明：删除用户相关的资源映射
	 * 
	 * @param resource
	 *            resource
	 */
	public void deleteResourceMappingByResource(Resource resource);
}
