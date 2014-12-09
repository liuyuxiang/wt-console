package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.ResourceTempDetailsDao;
import com.wt.uum2.domain.ResourceTempDetails;

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
public class ResourceTempDetailsDaoImpl extends BaseDaoSupport<ResourceTempDetails> implements
		ResourceTempDetailsDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceTempDetailsDao#getResourceTempDetails(java.lang.String, java.lang.Integer, boolean)
	 */
	/**
	 * 方法说明：getResourceTempDetails
	 * 
	 * @param eventuuid
	 *            eventuuid
	 * @param type
	 *            type
	 * @param status
	 *            status
	 * @return List
	 */
	public List<ResourceTempDetails> getResourceTempDetails(String eventuuid, Integer type,
			boolean status)
	{
		return getSession().createCriteria(ResourceTempDetails.class)
				.add(Restrictions.eq("status", status)).add(Restrictions.eq("type", type))
				.add(Restrictions.eq("eventuuid", eventuuid)).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceTempDetailsDao#updateResourceTempDetails(java.lang.String, java.lang.Integer, boolean)
	 */
	/**
	 * 方法说明：updateResourceTempDetails
	 * 
	 * @param eventuuid
	 *            eventuuid
	 * @param type
	 *            type
	 * @param status
	 *            status
	 */
	public void updateResourceTempDetails(String eventuuid, Integer type, boolean status)
	{
		List<ResourceTempDetails> list = getSession().createCriteria(ResourceTempDetails.class)
				.add(Restrictions.eq("status", status)).add(Restrictions.eq("type", type))
				.add(Restrictions.eq("eventuuid", eventuuid)).list();
		for (ResourceTempDetails resourceTempDetails : list) {
			resourceTempDetails.setStatus(true);
			update(resourceTempDetails);
		}

	}

}
