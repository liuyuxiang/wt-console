package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.TextValueDao;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.TextValue;

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
public class TextValueDaoImpl extends BaseDaoSupport<TextValue> implements TextValueDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.TextValueDao#getTextValuesUnderAttribute(com.wt.uum2.domain.Attribute)
	 */
	/**
	 * 方法说明：getTextValuesUnderAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<TextValue> getTextValuesUnderAttribute(Attribute attribute)
	{
		return getSession().createCriteria(TextValue.class).createCriteria("attribute")
				.add(Restrictions.idEq(attribute.getUuid())).list();
	}

}
