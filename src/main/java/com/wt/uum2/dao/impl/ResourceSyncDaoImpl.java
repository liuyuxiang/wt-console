package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.ResourceSyncDao;
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
public class ResourceSyncDaoImpl extends BaseDaoSupport<ResourceSync> implements ResourceSyncDao
{
	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceSyncDao#getResourceSyncByResource(int, int, java.lang.Integer)
	 */
	/**
	 * 方法说明：getResourceSyncByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	public UserPageResult getResourceSyncByResource(int page, int pagesize, Integer resourceType)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData((Long) getSession().createCriteria(ResourceSync.class)
				.add(Restrictions.eq("type", resourceType))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setList(getSession().createCriteria(ResourceSync.class)
				.add(Restrictions.eq("type", resourceType))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		result.setPager(pager);
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceSyncDao#getResourceSyncByResource(java.lang.Integer)
	 */
	/**
	 * 方法说明：getResourceSyncByResource
	 * 
	 * @param resourceType
	 *            resourceType
	 * @return List
	 */
	public List<ResourceSync> getResourceSyncByResource(Integer resourceType)
	{
		return getSession().createCriteria(ResourceSync.class)
				.add(Restrictions.eq("type", resourceType)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceSyncDao#getResourceSyncByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getResourceSyncByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return ResourceSync
	 */
	public ResourceSync getResourceSyncByUuid(String uuid)
	{
		return (ResourceSync) getSession().createCriteria(ResourceSync.class)
				.add(Restrictions.idEq(uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceSyncDao#getResourceSyncByPropName(java.lang.String)
	 */
	/**
	 * 方法说明：getResourceSyncByPropName
	 * 
	 * @param propName
	 *            propName
	 * @return List
	 */
	public List<ResourceSync> getResourceSyncByPropName(String propName)
	{
		return getSession().createCriteria(ResourceSync.class)
				.add(Restrictions.eq("propName", propName)).list();
	}
}
