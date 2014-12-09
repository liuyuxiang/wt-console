package com.wt.uum2.dao;

import java.util.List;
import java.util.Set;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface AttributeTypeDao extends BaseDao<AttributeType>
{

	/**
	 * 方法说明：通过资源，类型和类别获得扩展属性类型
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
			String catagory);

	/**
	 * 方法说明：通过资源和类型获得扩展属性类型
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeAllByResource(Resource resource, Integer type);

	/**
	 * 方法说明：通过资源，类型和应用组获得扩展属性类型
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
			Integer type, Group appGroup);

	/**
	 * 方法说明：通过扩展属性类型的ID获得扩展属性类型
	 * 
	 * @param id
	 *            id
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeById(String id);

	/**
	 * 方法说明：通过扩展属性唯一标识获得扩展属性类型
	 * 
	 * @param uuid
	 *            uuid
	 * @return AttributeType
	 */
	public AttributeType getAttributeTypeByUuid(String uuid);

	/**
	 * 方法说明：通过扩展属性名字获得扩展属性类型
	 * 
	 * @param name
	 *            name
	 * @return List
	 */
	public List<AttributeType> getAttributeTypeByName(String name);

	/**
	 * 方法说明：通过资源类型和条件查询分页扩展属性类型
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
			Integer resourceType, Condition condition);

	/**
	 * 方法说明：通过资源类型获得分页扩展属性类型
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	public UserPageResult getAttributeTypesByResource(int page, int pagesize, Integer resourceType);

	/**
	 * 方法说明：通过资源，类型和类别获得在页面上展现的扩展类型
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
			String catagory);

	/**
	 * 方法说明：通过类型ID模糊搜索
	 * 
	 * @param idKey
	 *            idKey
	 * @return List
	 */
	public List<AttributeType> getAttributeTypesLikeIdKey(String idKey);

	/**
	 * 
	 * 方法说明：通过资源类型和属性id取扩展属性资源
	 * 
	 * @param res
	 *            res
	 * @param key
	 *            key
	 * @return AttributeType
	 */
	public AttributeType getAttributeTypeById(Resource res, String key);
	Set<Group> getGroupsByUuid(String uuid);
	Set<Group> getAdminGroupsByUuid(String uuid);
}
