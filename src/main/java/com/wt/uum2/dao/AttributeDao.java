package com.wt.uum2.dao;

import java.util.List;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;

import nak.nsf.dao.support.BaseDao;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface AttributeDao extends BaseDao<Attribute>
{

	/**
	 * 方法说明：获得资源下的扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource);

	/**
	 * 方法说明：通过资源和组获得扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param group
	 *            group
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceAndGroup(Resource resource, Group group);

	/**
	 * 方法说明：通过扩展属性关键字获得扩展属性
	 * 
	 * @param uuid
	 *            uuid
	 * @return Attribute
	 */
	public Attribute getAttributeByUuid(String uuid);

	/**
	 * 方法说明：通过资源和类别获得扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, String catagory);

	/**
	 * 方法说明：通过资源和类别集合获得扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResource(Resource resource, List<String> catagory);

	/**
	 * 方法说明：通过资源和类别获得在页面上展现的扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	public List<Attribute> getAttributesUnderResourceOnPage(Resource resource, String catagory);

	/**
	 * 方法说明：通过扩展属性类型获得扩展属性
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<Attribute> getAttributesByAttributeType(AttributeType attributeType);

	/**
	 * 方法说明：通过扩展属性类型的编码获得扩展属性值
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return List
	 */
	@Deprecated
	public List<Attribute> getAttributesByAttributeTypeIdKey(Resource resource, List<String> keys);

	/**
	 * 方法说明：通过资源删除该资源下的所有扩展属性
	 * 
	 * @param resource
	 *            resource
	 * @return int
	 */
	public int deleteAttributesByResource(Resource resource);

	/**
	 * 方法说明：通过属性类型删除该属性下的所有扩展属性
	 * 
	 * @param type
	 *            type
	 * @return 1成功,0失败
	 */
	public int deleteAttributesByAttributeType(AttributeType type);

	/**
	 * 方法说明：通过资源和属性类型取得属性列表
	 * 
	 * @param res
	 *            res
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<Attribute> getAttributesByResAndType(Resource res, AttributeType attributeType);

	/**
	 * 方法说明：通过扩展属性值取得资源列表
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<Resource> getResourceByAttribute(String attrName, String attrValue);

	/**
	 * 方法说明：通过扩展属性值取得资源列表
	 * 
	 * @param type
	 *            type
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	public List<Resource> getResourceByAttribute(AttributeType type, String attrValue);

	/**
	 * 方法说明：通过扩展属性key和值,模糊查找资源列表
	 * 
	 * @param type
	 *            type
	 * @param value
	 *            value
	 * @return List
	 */
	public List<User> searchUserByAttribute(AttributeType type, String value);

}
