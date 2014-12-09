package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.TaskListDao;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.TaskList;

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
public class TaskListDaoImpl extends BaseDaoSupport<TaskList> implements TaskListDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.TaskListDao#getTaskList(int, int)
	 */
	/**
	 * 方法说明：getTaskList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getTaskList(int page, int pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(TaskList.class)
				.addOrder(Order.asc("linkOrder"))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(TaskList.class).addOrder(Order.asc("linkOrder"))
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.TaskListDao#getTaskListByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getTaskListByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return TaskList
	 */
	public TaskList getTaskListByUuid(String uuid)
	{
		return (TaskList) getSession().createCriteria(TaskList.class).add(Restrictions.idEq(uuid))
				.uniqueResult();
	}

	public List<TaskList> getTaskLists()
	{
		return getSession().createCriteria(TaskList.class).addOrder(Order.asc("linkOrder")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.TaskListDao#getAuditList(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getAuditList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getAuditList(Integer page, Integer pagesize)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Long count = (Long) getSession().createQuery("select count(*) from ResourceLog as logs")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult();
		pager.setQuantityOfData(count.intValue());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(ResourceLog.class)
				.addOrder(Order.desc("editDate")).setFirstResult(pager.getDataStart())
				.setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

}
