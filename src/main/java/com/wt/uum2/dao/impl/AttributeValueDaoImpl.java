package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.AttributeValueDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeValue;

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
public class AttributeValueDaoImpl extends BaseDaoSupport<AttributeValue> implements
		AttributeValueDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.AttributeValueDao#getAttributeValuesUnderAttribute(com.wt.uum2.domain.Attribute)
	 */
	/**
	 * 方法说明：getAttributeValuesUnderAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<AttributeValue> getAttributeValuesUnderAttribute(Attribute attribute)
	{
		return getSession().createCriteria(AttributeValue.class).createCriteria("attribute")
				.add(Restrictions.idEq(attribute.getUuid())).list();
	}

}
