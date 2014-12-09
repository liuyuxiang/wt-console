package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.ResourceMappingDao;
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
public class ResourceMappingDaoImpl extends BaseDaoSupport<ResourceMapping> implements
		ResourceMappingDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#getResourceMapping(java.lang.String)
	 */
	/**
	 * 方法说明：getResourceMapping
	 * 
	 * @param uuid
	 *            uuid
	 * @return ResourceMapping
	 */
	public ResourceMapping getResourceMapping(String uuid)
	{
		return (ResourceMapping) getSession().createCriteria(ResourceMapping.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}

	public List<ResourceMapping> getResourceMappingList()
	{
		return getSession().createCriteria(ResourceMapping.class).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#getResourceMapping(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getResourceMapping
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<ResourceMapping> getResourceMapping(Integer page, Integer pagesize)
	{
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#getMappingIdByResouce(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getMappingIdByResouce
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<String> getMappingIdByResouce(Resource resource)
	{
		return getSession().createCriteria(ResourceMapping.class)
				.add(Restrictions.eq("resourceuuid", resource.getUuid())).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#getResouceByMappingId(java.lang.String)
	 */
	/**
	 * 方法说明：getResouceByMappingId
	 * 
	 * @param mappingid
	 *            mappingid
	 * @return List
	 */
	public List<Resource> getResouceByMappingId(String mappingid)
	{
		return getSession().createCriteria(ResourceMapping.class)
				.add(Restrictions.eq("mappingid", mappingid)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#getResourceMapping(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：getResourceMapping
	 * 
	 * @param resource
	 *            resource
	 * @param mappingid
	 *            mappingid
	 * @return ResourceMapping
	 */
	public ResourceMapping getResourceMapping(Resource resource, String mappingid)
	{
		return (ResourceMapping) getSession().createCriteria(ResourceMapping.class)
				.add(Restrictions.eq("resourceuuid", resource.getUuid()))
				.add(Restrictions.eq("mappingid", mappingid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceMappingDao#deleteResourceMappingByResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：deleteResourceMappingByResource
	 * 
	 * @param resource
	 *            resource
	 */
	public void deleteResourceMappingByResource(Resource resource)
	{
		getSession().createQuery("delete ResourceMapping where resourceuuid=:resourceuuid")
				.setString("resourceuuid", resource.getUuid()).executeUpdate();

	}

}
