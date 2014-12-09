package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.SyncEDDao;
import com.wt.uum2.domain.SyncED;

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
public class SyncEDDaoImpl extends BaseDaoSupport<SyncED> implements SyncEDDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.SyncEDDao#getSyncED(int, int)
	 */
	/**
	 * 方法说明：getSyncED
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getSyncED(int page, int pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(SyncED.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(SyncED.class)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	public List<SyncED> getSyncEDList()
	{
		return getSession().createCriteria(SyncED.class).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.SyncEDDao#getSyncEDByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getSyncEDByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncED
	 */
	public SyncED getSyncEDByUuid(String uuid)
	{
		return (SyncED) getSession().createCriteria(SyncED.class).add(Restrictions.idEq(uuid))
				.uniqueResult();
	}

}
