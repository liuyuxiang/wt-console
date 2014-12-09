package com.wt.uum2.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum2.dao.CandidateItemDao;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;

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
public class CandidateItemDaoImpl extends BaseDaoSupport<CandidateItem> implements CandidateItemDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.CandidateItemDao#getCandidateItembyAttributeType(com.wt.uum2.domain.AttributeType)
	 */
	/**
	 * 方法说明：getCandidateItembyAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<CandidateItem> getCandidateItembyAttributeType(AttributeType attributeType)
	{
		return getSession().createCriteria(CandidateItem.class).createCriteria("attributeType")
				.add(Restrictions.idEq(attributeType.getUuid())).list();
	}

}
