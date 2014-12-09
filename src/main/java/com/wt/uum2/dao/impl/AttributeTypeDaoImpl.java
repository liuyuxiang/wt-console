package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.AttributeTypeDao;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-9-5
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AttributeTypeDaoImpl extends BaseDaoSupport<AttributeType> implements AttributeTypeDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeByResource(com.wt.uum2.domain.Resource, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeByResource
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
			String catagory)
	{

		if (type == null) {
			type = resource.getType();
		}

		Criteria c = getSession().createCriteria(AttributeType.class);

		c.add(Restrictions.eq("resourceType", type.intValue()));

		if (StringUtils.isNotBlank(catagory)) {
			c.add(Restrictions.eq("catagory", catagory));
		}

		return c.addOrder(Order.asc("order")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeAllByResource(com.wt.uum2.domain.Resource, java.lang.Integer)
	 */
	/**
	 * 方法说明：getAttributeTypeAllByResource
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeAllByResource(Resource resource, Integer type)
	{
		return getAttributeTypeByResource(resource, type, null);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeByResourceAndAppGroup(com.wt.uum2.domain.Resource, java.lang.Integer, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getAttributeTypeByResourceAndAppGroup
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param appGroup
	 *            appGroup
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByResourceAndAppGroup(Resource resource,
			Integer type, Group appGroup)
	{
		if (type == null) {
			return getSession().createCriteria(AttributeType.class)
					.add(Restrictions.eq("resourceType", resource.getType()))
					.add(Restrictions.like("id", "%" + appGroup.getCode() + "%"))
					.addOrder(Order.asc("order")).list();
		} else {
			return getSession().createCriteria(AttributeType.class)
					.add(Restrictions.eq("resourceType", type.intValue()))
					.add(Restrictions.like("id", "%" + appGroup.getCode() + "%"))
					.addOrder(Order.asc("order")).list();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeById(java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeById
	 * 
	 * @param id
	 *            id
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeById(String id)
	{
		return getSession().createCriteria(AttributeType.class).add(Restrictions.eq("id", id))
				.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return AttributeType
	 */
	public AttributeType getAttributeTypeByUuid(String uuid)
	{
		return (AttributeType) getSession().createCriteria(AttributeType.class)
				.add(Restrictions.idEq(uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#searchAttributeTypesByResource(int, int, java.lang.Integer, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchAttributeTypesByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchAttributeTypesByResource(int page, int pagesize,
			Integer resourceType, Condition condition)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(AttributeType.class)
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.add(Restrictions.eq("resourceType", resourceType))
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(AttributeType.class)
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.add(Restrictions.eq("resourceType", resourceType))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(AttributeType.class)
					.add(Restrictions.like("id", "%" + condition.getUserId() + "%").ignoreCase())
					.add(Restrictions.eq("resourceType", resourceType))
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(AttributeType.class)
					.add(Restrictions.like("id", "%" + condition.getUserId() + "%").ignoreCase())
					.add(Restrictions.eq("resourceType", resourceType))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		} else {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList());

		}
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypesByResource(int, int, java.lang.Integer)
	 */
	/**
	 * 方法说明：getAttributeTypesByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	public UserPageResult getAttributeTypesByResource(int page, int pagesize, Integer resourceType)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData((Long) getSession().createCriteria(AttributeType.class)
				.add(Restrictions.eq("resourceType", resourceType))
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setList(getSession().createCriteria(AttributeType.class)
				.add(Restrictions.eq("resourceType", resourceType)).addOrder(Order.asc("order"))
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());

		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeByResourceOnPage(com.wt.uum2.domain.Resource, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeByResourceOnPage
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByResourceOnPage(Resource resource, Integer type,
			String catagory)
	{
		if (type == null) {
			type = resource.getType();
		}
		Criteria c = getSession().createCriteria(AttributeType.class).addOrder(Order.asc("order"));

		c.add(Restrictions.eq("resourceType", type.intValue()));

		if (StringUtils.isNotBlank(catagory)) {
			c.add(Restrictions.eq("catagory", catagory));
		}
		return c.add(Restrictions.eq("hidden", false)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeByName(java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeByName
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByName(String name)
	{
		return getSession().createCriteria(AttributeType.class)
				.add(Restrictions.like("name", name)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypesLikeIdKey(java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypesLikeIdKey
	 * 
	 * @param idKey
	 *            idKey
	 * @return List
	 */
	public List<AttributeType> getAttributeTypesLikeIdKey(String idKey)
	{
		return getSession().createQuery(
				"select attributeType from AttributeType as attributeType where attributeType.id like '%"
						+ idKey + "%' order by attributeType.id").list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeTypeDao#getAttributeTypeById(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeTypeById
	 * 
	 * @param resource
	 *            resource
	 * @param key
	 *            key
	 * @return AttributeType
	 */
	public AttributeType getAttributeTypeById(Resource resource, String key)
	{
		return (AttributeType) getSession().createCriteria(AttributeType.class)
				.add(Restrictions.eq("resourceType", resource.getType()))
				.add(Restrictions.eq("id", key)).uniqueResult();
	}

	public Set<Group> getGroupsByUuid(String uuid)
	{
		return getAttributeTypeByUuid(uuid).getGroups();
		/*return getSession().createQuery(
				"from AttributeType where uuid ='" + uuid + "'").list();*/
	}

	public Set<Group> getAdminGroupsByUuid(String uuid)
	{
		return getAttributeTypeByUuid(uuid).getAdminGroups();
	}

}
