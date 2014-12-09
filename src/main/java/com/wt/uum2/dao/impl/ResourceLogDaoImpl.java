package com.wt.uum2.dao.impl;

import java.util.Date;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.ResourceLogDao;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceLog;

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
public class ResourceLogDaoImpl extends BaseDaoSupport<ResourceLog> implements ResourceLogDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#getResourceLogsByResource(int, int, com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getResourceLogsByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resource
	 *            resource
	 * @return UserPageResult
	 */
	public UserPageResult getResourceLogsByResource(int page, int pagesize, Resource resource)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession()
				.createQuery(
						"select count(resourceLog.uuid) from ResourceLog as resourceLog where resourceLog.resourceuuid='"
								+ resource.getUuid() + "' order by editDate desc")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult());
		pager.compute();
		result.setPager(pager);
		result.setList(getSession()
				.createQuery(
						"select resourceLog from ResourceLog as resourceLog where resourceLog.resourceuuid='"
								+ resource.getUuid() + "' order by editDate desc")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#getDelResourceLogs(int, int, java.lang.Integer)
	 */
	/**
	 * 方法说明：getDelResourceLogs
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDelResourceLogs(int page, int pagesize, Integer type)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		if (type.intValue() == 0) {
			pager.setQuantityOfData((Long) getSession()
					.createQuery(
							"select count(resourceLog.uuid) from ResourceLog as resourceLog where resourceLog.remark='永久删除用户' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.uniqueResult());
			pager.compute();
			result.setPager(pager);
			result.setList(getSession()
					.createQuery(
							"select resourceLog from ResourceLog as resourceLog where resourceLog.remark='永久删除用户' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		} else if (type.intValue() == 1) {
			pager.setQuantityOfData((Long) getSession()
					.createQuery(
							"select count(resourceLog.uuid) from ResourceLog as resourceLog where resourceLog.remark='删除角色' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.uniqueResult());
			pager.compute();
			result.setPager(pager);
			result.setList(getSession()
					.createQuery(
							"select resourceLog from ResourceLog as resourceLog where resourceLog.remark='删除角色' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		} else {
			pager.setQuantityOfData((Long) getSession()
					.createQuery(
							"select count(resourceLog.uuid) from ResourceLog as resourceLog where resourceLog.remark='删除部门' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.uniqueResult());
			pager.compute();
			result.setPager(pager);
			result.setList(getSession()
					.createQuery(
							"select resourceLog from ResourceLog as resourceLog where resourceLog.remark='删除部门' order by editDate desc")
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize()).list());
		}

		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#getResourceLogsByResource(com.wt.uum2.domain.Resource)
	 */
	/**
	 * 方法说明：getResourceLogsByResource
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<ResourceLog> getResourceLogsByResource(Resource resource)
	{
		return getSession().createCriteria(ResourceLog.class)
				.add(Restrictions.eq("resourceuuid", resource.getUuid())).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#searchResourceLogsByDeptContent(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：searchResourceLogsByDeptContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByDeptContent(Integer page, Integer pagesize,
			String searchcontent)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Query q = getSession()
				.createQuery(
						"select resourceLog from ResourceLog as resourceLog,Department as dept where resourceLog.resourceuuid=dept.uuid and (dept.code like '%"
								+ searchcontent.trim()
								+ "%' or dept.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		Query q1 = getSession()
				.createQuery(
						"select count(resourceLog.uuid) from ResourceLog as resourceLog,Department as dept where resourceLog.resourceuuid=dept.uuid and (dept.code like '%"
								+ searchcontent.trim()
								+ "%' or dept.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#searchResourceLogsByEditUserContent(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：searchResourceLogsByEditUserContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByEditUserContent(Integer page, Integer pagesize,
			String searchcontent)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Query q = getSession()
				.createQuery(
						"select resourceLog from ResourceLog as resourceLog,User as u where resourceLog.editPerson=u.name and (u.id like '%"
								+ searchcontent.trim()
								+ "%' or u.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		Query q1 = getSession()
				.createQuery(
						"select count(resourceLog.uuid) from ResourceLog as resourceLog,User as u where resourceLog.editPerson=u.name and (u.id like '%"
								+ searchcontent.trim()
								+ "%' or u.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#searchResourceLogsByGroupContent(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：searchResourceLogsByGroupContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByGroupContent(Integer page, Integer pagesize,
			String searchcontent)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Query q = getSession()
				.createQuery(
						"select resourceLog from ResourceLog as resourceLog,Group as g where resourceLog.resourceuuid=g.uuid and (g.code like '%"
								+ searchcontent.trim()
								+ "%' or g.name like '%"
								+ searchcontent.trim() + "%') order by resourceLog.editDate desc");
		Query q1 = getSession()
				.createQuery(
						"select count(resourceLog.uuid) from ResourceLog as resourceLog,Group as g where resourceLog.resourceuuid=g.uuid and (g.code like '%"
								+ searchcontent.trim()
								+ "%' or g.name like '%"
								+ searchcontent.trim() + "%') order by resourceLog.editDate desc");
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#searchResourceLogsByUserContent(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：searchResourceLogsByUserContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByUserContent(Integer page, Integer pagesize,
			String searchcontent)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Query q = getSession()
				.createQuery(
						"select resourceLog from ResourceLog as resourceLog,User as u where resourceLog.resourceuuid=u.uuid and (u.id like '%"
								+ searchcontent.trim()
								+ "%' or u.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		Query q1 = getSession()
				.createQuery(
						"select count(resourceLog.uuid) from ResourceLog as resourceLog,User as u where resourceLog.resourceuuid=u.uuid and (u.id like '%"
								+ searchcontent.trim()
								+ "%' or u.name like '%"
								+ searchcontent.trim() + "%') order by editDate desc");
		pager.setQuantityOfData(Integer.valueOf(q1.list().get(0).toString()));
		pager.compute();
		result.setPager(pager);
		result.setList(q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.ResourceLogDao#searchResourceLogsByEditDate(com.wt.uum2.domain.Resource, java.util.Date, java.util.Date)
	 */
	/**
	 * 方法说明：searchResourceLogsByEditDate
	 * 
	 * @param res
	 *            res
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return List
	 */
	public List<ResourceLog> searchResourceLogsByEditDate(Resource res, Date beginDate, Date endDate)
	{
		return getSession().createCriteria(ResourceLog.class)
				.add(Restrictions.eq("resourceuuid", res.getUuid()))
				.add(Restrictions.between("editDate", beginDate, endDate))
				.addOrder(Order.desc("editDate")).list();

	}

}
