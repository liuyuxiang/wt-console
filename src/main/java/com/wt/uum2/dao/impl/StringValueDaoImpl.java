package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.StringValueDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.StringValue;

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
public class StringValueDaoImpl extends BaseDaoSupport<StringValue> implements StringValueDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.StringValueDao#getStringValuesUnderAttribute(com.wt.uum2.domain.Attribute)
	 */
	/**
	 * 方法说明：getStringValuesUnderAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<StringValue> getStringValuesUnderAttribute(Attribute attribute)
	{
		return getSession().createCriteria(StringValue.class).createCriteria("attribute")
				.add(Restrictions.idEq(attribute.getUuid())).list();
	}

}
