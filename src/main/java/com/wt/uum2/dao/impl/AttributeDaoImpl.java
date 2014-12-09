package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.AttributeDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

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
public class AttributeDaoImpl extends BaseDaoSupport<Attribute> implements AttributeDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesUnderResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource)
	{
		return getSession()
				.createQuery(
						"select attribute from Attribute as attribute, AttributeType as attributeType where attribute.type=attributeType and attribute.ownerResource=:resource order by attributeType.order")
				.setEntity("resource", resource).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesUnderResourceAndGroup(com.wt.uum2.domain.Resource, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getAttributesUnderResourceAndGroup
	 * 
	 * @param resource
	 *            resource
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceAndGroup(Resource resource, Group group)
	{
		return getSession()
				.createQuery(
						"select attribute from Attribute as attribute, AttributeType as attributeType where attribute.type=attributeType and attributeType.id like '%"
								+ group.getCode()
								+ "%' and attribute.ownerResource=:resource order by attributeType.order")
				.setEntity("resource", resource).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributeByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getAttributeByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Attribute
	 */
	public Attribute getAttributeByUuid(String uuid)
	{
		return (Attribute) getSession().createCriteria(Attribute.class)
				.add(Restrictions.idEq(uuid)).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesUnderResource(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, String catagory)
	{
		return getSession()
				.createQuery(
						"select attribute from Attribute as attribute, AttributeType as attributeType where attributeType.catagory=:catagory and attribute.type=attributeType and attribute.ownerResource=:resource order by attributeType.order")
				.setString("catagory", catagory).setEntity("resource", resource).list();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesUnderResourceOnPage(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：getAttributesUnderResourceOnPage
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceOnPage(Resource resource, String catagory)
	{
		return getSession()
				.createQuery(
						"select attribute from Attribute as attribute, AttributeType as attributeType where attributeType.catagory=:catagory and attribute.type=attributeType and attribute.ownerResource=:resource and attribute.type.hidden=false order by attributeType.order")
				.setString("catagory", catagory).setEntity("resource", resource).list();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesByAttributeType(com.wt.uum2.domain.AttributeType)
	 */
	/**
	 * 方法说明：getAttributesByAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<Attribute> getAttributesByAttributeType(AttributeType attributeType)
	{
		return getSession().createCriteria(Attribute.class)
				.add(Restrictions.eq("type", attributeType)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesByAttributeTypeIdKey(com.wt.uum2.domain.Resource, java.util.List)
	 */
	/**
	 * 方法说明：getAttributesByAttributeTypeIdKey
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return List
	 */
	@Deprecated
	public List<Attribute> getAttributesByAttributeTypeIdKey(Resource resource, List<String> keys)
	{
		String sql = "select attribute from Attribute as attribute, AttributeType as attributeType where attribute.type=attributeType and attribute.ownerResource=:resource and (";
		for (int i = 0; i < keys.size(); i++) {
			sql += "attributeType.id like '%" + keys.get(i) + "%' ";
			if (i < keys.size() - 1) {
				sql += "or ";
			} else {
				sql += ") ";
			}
		}
		return getSession().createQuery(sql + " order by attributeType.order")
				.setEntity("resource", resource).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesUnderResource(com.wt.uum2.domain.Resource, java.util.List)
	 */
	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, List<String> catagory)
	{
		String ud = "'";
		for (int k = 0; k < catagory.size(); k++) {
			if (k == catagory.size() - 1) {
				ud = ud + catagory.get(k) + "'";
			} else {
				ud = ud + catagory.get(k) + "','";
			}
		}
		return getSession()
				.createQuery(
						"select attribute from Attribute as attribute, AttributeType as attributeType where attributeType.catagory in("
								+ ud
								+ ") and attribute.type=attributeType and attribute.ownerResource=:resource order by attributeType.order")
				.setEntity("resource", resource).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#deleteAttributesByResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：deleteAttributesByResource
	 * 
	 * @param resource
	 *            resource
	 * @return int
	 */
	public int deleteAttributesByResource(Resource resource)
	{
		String hql = "delete Attribute as attribute where attribute.ownerResource=:resource";
		Query query = getSession().createQuery(hql).setEntity("resource", resource);
		int status = query.executeUpdate();
		return status;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#deleteAttributesByAttributeType(com.wt.uum2.domain.AttributeType)
	 */
	/**
	 * 方法说明：deleteAttributesByAttributeType
	 * 
	 * @param type
	 *            type
	 * @return int
	 */
	public int deleteAttributesByAttributeType(AttributeType type)
	{
		String hql = "delete Attribute as attribute where attribute.type=:type";
		Query query = getSession().createQuery(hql).setEntity("type", type);
		int status = query.executeUpdate();
		return status;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getAttributesByResAndType(com.wt.uum2.domain.Resource, com.wt.uum2.domain.AttributeType)
	 */
	/**
	 * 方法说明：getAttributesByResAndType
	 * 
	 * @param resource
	 *            resource
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<Attribute> getAttributesByResAndType(Resource resource, AttributeType attributeType)
	{
		String sql = "select attribute from Attribute as attribute where attribute.type=:attributeType and attribute.ownerResource=:resource ) ";
		return getSession().createQuery(sql).setEntity("resource", resource)
				.setEntity("attributeType", attributeType).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getResourceByAttribute(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getResourceByAttribute
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<Resource> getResourceByAttribute(String attrName, String attrValue)
	{
		AttributeType type = (AttributeType) getSession()
				.createQuery("select type from AttributeType type where type.id=:id")
				.setString("id", attrName).uniqueResult();
		if (type == null) {
			return null;
		}
		return this.getResourceByAttribute(type, attrValue);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#getResourceByAttribute(com.wt.uum2.domain.AttributeType, java.lang.String)
	 */
	/**
	 * 方法说明：getResourceByAttribute
	 * 
	 * @param type
	 *            type
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<Resource> getResourceByAttribute(AttributeType type, String attrValue)
	{
		if (type == null) {
			return null;
		}
		String hql = "select att.ownerResource from Attribute att where att.type=:type ";
		if (StringUtils.isBlank(attrValue)) {
			hql += " and att.value is null";
		} else {
			hql += " and att.value=:attrValue";
		}
		Query q = getSession().createQuery(hql).setEntity("type", type);
		if (StringUtils.isNotBlank(attrValue)) {
			q.setString("attrValue", attrValue);
		}
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeDao#searchUserByAttribute(com.wt.uum2.domain.AttributeType, java.lang.String)
	 */
	/**
	 * 方法说明：searchUserByAttribute
	 * 
	 * @param type
	 *            type
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<User> searchUserByAttribute(AttributeType type, String attrValue)
	{
		if (type == null) {
			return null;
		}
		String hql = "select att.ownerResource from Attribute att where att.type=:type ";
		if (StringUtils.isBlank(attrValue)) {
			hql += " and att.value is null";
		} else {
			hql += " and att.value like :attrValue";
		}
		Query q = getSession().createQuery(hql).setEntity("type", type);
		if (StringUtils.isNotBlank(attrValue)) {
			q.setString("attrValue", "%" + attrValue + "%");
		}
		return q.list();
	}

}
