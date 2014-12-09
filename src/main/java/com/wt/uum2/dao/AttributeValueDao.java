package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeValue;

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
public interface AttributeValueDao extends BaseDao<AttributeValue>
{

	/**
	 * 方法说明：通过扩展属性获得扩展属性值
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<AttributeValue> getAttributeValuesUnderAttribute(Attribute attribute);

}
