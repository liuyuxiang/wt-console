package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.LongValueDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.LongValue;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class LongValueDaoImpl extends BaseDaoSupport<LongValue> implements LongValueDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.LongValueDao#getLongValuesUnderAttribute(com.wt.uum2.domain.Attribute)
	 */
	/**
	 * 方法说明：getLongValuesUnderAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<LongValue> getLongValuesUnderAttribute(Attribute attribute)
	{
		return getSession().createCriteria(LongValue.class).createCriteria("attribute")
				.add(Restrictions.idEq(attribute.getUuid())).list();
	}

}
