package com.wt.hea.common.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hirisun.components.data.BeanHelper;
import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.model.Condition;
import com.wt.hea.common.model.Page;
import com.wt.hea.common.util.HibernateTypeHelper;

/**
 * 所有数据库持久层dao抽象基类
 * 
 * @author 袁明敏
 * @see org.springframework.orm.hibernate3.support.HibernateDaoSupport
 * 
 * @version 1.1
 * @since 2010-07-01
 * 
 * 
 * 
 * @param <T>
 * 
 */

@SuppressWarnings({ "unchecked", "rawtypes" })
public abstract class AbstractHibernateDaoSupport<T> extends HibernateDaoSupport implements
		EntityDao<T>
{
	/**
	 * 日志对象
	 */
	Logger logger = LoggerService.getInstance(AbstractHibernateDaoSupport.class);
	/**
	 * 
	 */
	private Class currClass;
	{
		Type type = this.getClass().getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType pType = (ParameterizedType) type;
			Type[] types = pType.getActualTypeArguments();
			currClass = (Class) types[0];
			LogFactory.getLog(currClass);
		}
	}

	/**
	 * 
	 * 方法说明：按类型删除所有对象
	 * 
	 */
	public void deleteAll()
	{
		try {
			this.getSession().createQuery("delete " + currClass.getName()).executeUpdate();
		} catch (Exception ex) {
			logger.error("delete " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("delete All " + currClass.getName() + "  is success!");
	}

	/**
	 * 跟据多个id批量删除对象
	 * 
	 * @param ids
	 *            一系列对象的id标识号
	 */
	public void deleteByIds(Serializable... ids)
	{
		try {
			Session session = this.getSession();
			int cnt = 0;
			for (Serializable i : ids) {
				Object temp = session.get(currClass, i);
				session.delete(temp);
				cnt++;
				if (cnt > 500) {
					session.flush();
					cnt = 0;
				}
			}
		} catch (Exception ex) {
			logger.error("deleteByIds " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("deleteByIds " + currClass.getName() + " success!");
	}

	/**
	 * 方法说明:
	 * 
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		try {
			Session session = this.getSession();
			Object persistence = session.get(currClass, id);
			if (persistence != null) {
				session.delete(persistence);
			}
		} catch (RuntimeException ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 查询有的对象
	 * 
	 * @return 返回对象列表
	 */
	public List<T> findAll()
	{
		List<T> list = null;
		try {
			list = this.getSession().createCriteria(currClass).list();
		} catch (Exception ex) {
			logger.error("findAll " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("findAll " + currClass.getName() + " success!");
		return list;
	}

	/**
	 * 查询所有对象，按照片 property 属性排序
	 * 
	 * @param property
	 *            对象属性名称
	 * @param isAsc
	 *            是否为升序
	 * @return 返回对象列表
	 */
	public List<T> findAll(String property, Boolean isAsc)
	{
		try {
			Criteria criteria = this.getSession().createCriteria(currClass);
			if (isAsc) {
				List<T> list = criteria.addOrder(Order.asc(property)).list();
				if (list != null && list.size() > 0) {
					logger.info("findAll " + currClass.getName() + " success!");
				} else {
					if (list == null) {
						logger.warn("findAll " + currClass.getName()
								+ " success,but returned list is null !");
					} else {
						logger.warn("findAll " + currClass.getName()
								+ " success,but returned list size==0 !");
					}
				}
				return list;
			} else {
				return criteria.addOrder(Order.desc(property)).list();
			}

		} catch (Exception ex) {
			logger.error("findAll " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}

	}

	/**
	 * 跟据id查询对象
	 * 
	 * @param id
	 *            对象标识
	 * @return 返回查询的对象值
	 */
	public T findById(Serializable id)
	{
		T model = null;
		try {
			model = (T) this.getSession().get(currClass, id);
		} catch (Exception ex) {
			logger.error("findById " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("findById " + currClass.getName() + " success ,object id is {" + id + "}!");
		return model;
	}

	/**
	 * 方法说明：跟据对象id号，批量查询对象
	 * 
	 * @param ids
	 *            对象id集合
	 * @return 返回查询的结果列表
	 */
	public List<T> findByIds(Serializable... ids)
	{
		List<T> list = null;
		try {
			Session session = this.getSession();
			list = new ArrayList<T>();
			for (Serializable i : ids) {
				T temp = (T) session.get(currClass, i);
				list.add(temp);
			}
		} catch (Exception ex) {
			logger.error("findByIds " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}

		logger.info("findByIds " + currClass.getName() + " success!");
		return list;
	}

	/**
	 * 保存一个对象
	 * 
	 * @param e
	 *            要保存的实体对象
	 * @return boolean
	 */
	public boolean save(T e)
	{
		Serializable identifier = this.getSession().save(e);
		if (identifier != null) {
			return true;
		}
		return false;
	}

	/**
	 * 删除一个对象
	 * 
	 * @param obj
	 *            要删除的实体对象
	 */
	public void delete(T obj)
	{
		try {
			this.getSession().delete(obj);
		} catch (Exception ex) {
			logger.error("delete " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("delete " + currClass.getName() + " success!");
	}

	/**
	 * 更新一个实体对象
	 * 
	 * @param obj
	 *            要更新的实体对象
	 * @return 返回更新后的实体对象
	 */
	public T update(T obj)
	{
		try {
			obj = (T) this.getSession().merge(obj);
		} catch (Exception ex) {
			logger.error("update " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("update " + currClass.getName() + " success!");
		return obj;
	}

	/**
	 * 按照对象的属性名称查找实体对象列表
	 * 
	 * @param property
	 *            实体对象的属性字符串
	 * @param value
	 *            属性值
	 * @return 返回按属性值匹配的结果集
	 */
	public List<T> findByProperty(String property, Object value)
	{
		List<T> list = null;
		try {
			list = this.getSession().createCriteria(currClass)
					.add(Restrictions.eq(property, value)).list();
		} catch (Exception ex) {
			logger.error("findByProperty " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		logger.info("findByProperty " + currClass.getName() + " success!");
		return list;
	}

	/**
	 * 方法说明:按照对象的属性名称查找实体对象列表
	 * 
	 * @param property
	 *            实体对象的属性字符串
	 * @param value
	 *            属性值
	 * @return 返回按属性值匹配的唯一值
	 */
	public T findByPropertyUnique(String property, Object value)
	{
		T t = null;
		try {
			t = (T) this.getSession().createCriteria(currClass)
					.add(Restrictions.eq(property, value)).uniqueResult();
		} catch (Exception ex) {
			logger.error("findByProperty " + currClass.getName() + " error: " + ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		}
		return t;
	}

	/**
	 * 
	 * 方法说明：按照片对象查询查询示例，按属性模糊查询
	 * 
	 * @param obj
	 *            实体对象
	 * @return 返回匹配的结果集
	 */
	public List<T> findByExample(T obj)
	{
		List results = this.getSession().createCriteria(currClass)
				.add(Example.create(obj).ignoreCase().enableLike(MatchMode.ANYWHERE)).list();
		return results;
	}

	/**
	 * 加载分页对象
	 * 
	 * @param pageModel
	 *            初使化后不带数据的分页对象
	 * @return 返回带数据的分页对象
	 */
	public Page<T> loadPage(Page<T> pageModel)
	{
		int firstResult = (pageModel.getCurrPageNum() - 1) * pageModel.getPerPageRecord();
		int maxResult = pageModel.getPerPageRecord();

		Criteria criteria = getSession().createCriteria(currClass);
		Criteria criteria2 = getSession().createCriteria(currClass);

		try {
			// 按属性条件查询
			Condition condition = pageModel.getConditions();
			if (condition != null) {
				List<Criterion> list = (List<Criterion>) BeanHelper.forceGetProperty(condition,
						"expressions");
				for (Criterion i : list) {
					criteria = criteria.add(i);
					criteria2 = criteria2.add(i);
				}
			}
			Object totalRecordObj = criteria2.setProjection(Projections.rowCount()).uniqueResult();
			int totalRecord = 0;
			if (totalRecordObj != null) {
				totalRecord = Integer.valueOf(totalRecordObj.toString());
				// System.out.println(totalRecord);
			}
			criteria.setFirstResult(firstResult);
			criteria.setMaxResults(maxResult);

			criteria = processPageBeanOrders(pageModel, criteria);

			List list = criteria.list();

			double temp = Double.valueOf(totalRecord)
					/ Double.valueOf(pageModel.getPerPageRecord());

			BeanHelper.forceSetProperty(pageModel, "totalRecord", totalRecord);
			BeanHelper.forceSetProperty(pageModel, "totalPage", Double.valueOf(Math.ceil(temp))
					.intValue());
			BeanHelper.forceSetProperty(pageModel, "data", list);

			processPageBeanProperties(pageModel);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
		logger.debug("load successful");
		return pageModel;
	}

	/***
	 * 处理分页对象的排序字段
	 * 
	 * @param pageModel
	 *            分页模型对象
	 * @param criteria
	 *            hibernate查询对象
	 * @return 返回查询对象
	 */
	private Criteria processPageBeanOrders(Page<T> pageModel, Criteria criteria)
	{
		// 排序
		boolean isEmpty = pageModel.getOrderProperties().isEmpty();
		if (isEmpty == false) {
			List list = pageModel.getOrderProperties();
			for (int i = 0; i < list.size(); i++) {
				List temp = (List) list.get(i);
				if (temp.size() == 2) {
					String key = (String) temp.get(0);
					Boolean value = (Boolean) temp.get(1);
					if (value == true) {
						criteria = criteria.addOrder(Order.asc(key));
					} else {
						criteria = criteria.addOrder(Order.desc(key));
					}
				}
			}
		}
		return criteria;
	}

	/**
	 * 底层处理分页对象内部属性值，如:"当前页值" 大于 "总页数值",将重设 "当前页值" 为"总页数值"
	 * 
	 * @param pageModel
	 *            分页模型
	 * @throws NoSuchFieldException
	 *             找不到实体的当前属性
	 */
	private void processPageBeanProperties(Page pageModel) throws NoSuchFieldException
	{
		if (pageModel.getCurrPageNum() >= pageModel.getTotalPage()) {
			BeanHelper.forceSetProperty(pageModel, "currPageNum", pageModel.getTotalPage());// 页面上展现加了1，所以这要减1
			BeanHelper.forceSetProperty(pageModel, "hasNextPage", false);
		} else {
			BeanHelper.forceSetProperty(pageModel, "hasNextPage", true);
		}

		if (pageModel.getCurrPageNum() <= 1) {
			BeanHelper.forceSetProperty(pageModel, "currPageNum", 1);
			BeanHelper.forceSetProperty(pageModel, "hasPreviouPage", false);
		} else {
			BeanHelper.forceSetProperty(pageModel, "hasPreviouPage", true);
		}

	}

	// ------------------------------------------------------- HQL: background
	// -----------------------------------
	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @return boolean
	 */
	public boolean executeHql(String hql)
	{
		return this.getSession().createQuery(hql).executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return boolean
	 */
	public boolean executeHql(String hql, Object paramValues[])
	{
		Query query = this.getSession().createQuery(hql);
		return processParamValues(paramValues, query).executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @return List<T>
	 */
	public List<T> executeHqlQuery(String hql)
	{
		return this.getSession().createQuery(hql).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return List<T>
	 */
	public List<T> executeHqlQuery(String hql, Object paramValues[])
	{
		Query query = this.getSession().createQuery(hql);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @return Object
	 */
	public Object uniqueResultByHql(String hql)
	{
		return this.getSession().createQuery(hql).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @return Object
	 */
	public Object uniqueResultByHql(String hql, Object[] paramValues)
	{
		Query query = this.getSession().createQuery(hql);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @return Object
	 */
	public Object uniqueResultBySql(String sql)
	{
		Query query = this.getSession().createSQLQuery(sql);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @return Object
	 */
	public Object uniqueResultBySql(String sql, Object[] paramValues)
	{
		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	// -------------------------------------------------------- SQL: background
	// ------------------------------------//

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @return boolean
	 */
	public boolean executeSql(String sql)
	{
		int cnt = this.getSession().createSQLQuery(sql).executeUpdate();
		return cnt > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValue
	 *            paramValue
	 * @return boolean
	 */
	public boolean executeSql(final String sql, final Object[] paramValue)
	{
		/*
		final Map<String ,Boolean> map=new HashMap<String,Boolean>();
		map.put("isSuccess", Boolean.valueOf(false));
		this.getSession().doWork(new Work() {
			public void execute(Connection con) throws SQLException {
				PreparedStatement pstmt = null;
				//try{
				pstmt=con.prepareStatement(sql);
				for(int i=0;i< paramValue.length-1;i++){
					pstmt.setObject(i+1, paramValue[i]);
				}
				Boolean isSuccess = Boolean.valueOf(pstmt.execute());
				map.put("isSuccess", isSuccess);
				//}catch(Exception e){
				//	e.printStackTrace();
				//}finally{
				//	DbHelper.destroy(con, pstmt, null);
				//}
			}
		});
		
		return map.get("isSuccess").booleanValue();
		*/

		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValue, query);
		return query.executeUpdate() > 0;
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @return List
	 */
	public List executeSqlQuery(String sql)
	{
		return this.getSession().createSQLQuery(sql).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @return List
	 */
	public List executeSqlQuery(String sql, Object[] paramValues)
	{
		Query query = this.getSession().createSQLQuery(sql);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	// -----------------------------------------------NamedQuery background
	// -----------------------------------------
	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @return List<T>
	 */
	public List<T> executeHqlNamedQuery(String hqlQueryName)
	{
		return this.getSession().getNamedQuery(hqlQueryName).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return List<T>
	 */
	public List<T> executeHqlNamedQuery(String hqlQueryName, Object paramValues[])
	{
		Query query = this.getSession().getNamedQuery(hqlQueryName);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @return List
	 */
	public List executeSqlNamedQuery(String sqlQueryName)
	{
		return this.getSession().getNamedQuery(sqlQueryName).list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return List
	 */
	public List executeSqlNamedQuery(String sqlQueryName, Object paramValues[])
	{
		Query query = this.getSession().getNamedQuery(sqlQueryName);
		query = processParamValues(paramValues, query);
		return query.list();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @return Object
	 */
	public Object uniqueResultByNamedHql(String hqlQueryName)
	{
		return this.getSession().getNamedQuery(hqlQueryName).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param hqlQueryName
	 *            hqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return Object
	 */
	public Object uniqueResultByNamedHql(String hqlQueryName, Object paramValues[])
	{
		Query query = this.getSession().getNamedQuery(hqlQueryName);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @return Object
	 */
	public Object uniqueResultByNamedSql(String sqlQueryName)
	{
		return this.getSession().getNamedQuery(sqlQueryName).uniqueResult();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sqlQueryName
	 *            sqlQueryName
	 * @param paramValues
	 *            paramValues
	 * @return Object
	 */
	public Object uniqueResultByNamedSql(String sqlQueryName, Object paramValues[])
	{
		Query query = this.getSession().getNamedQuery(sqlQueryName);
		query = processParamValues(paramValues, query);
		return query.uniqueResult();
	}

	/**
	 * 
	 * 方法说明：私有方法，处理参数值
	 * 
	 * @param paramValues
	 *            paramValues
	 * @param query
	 *            query
	 * @return Query
	 */
	private Query processParamValues(Object[] paramValues, Query query)
	{
		for (int i = 0; i < paramValues.length; i++) {
			query = query.setParameter(i, paramValues[i],
					HibernateTypeHelper.filter(paramValues[i]));
		}
		return query;
	}

	// ----------------------------------------------- background
	// -----------------------------------------
	/**
	 * 
	 * 方法说明：
	 * 
	 * @param sql
	 *            sql
	 * @param paramValues
	 *            paramValues
	 * @param page
	 *            page
	 * @return Page<Object []>
	 */
	public Page<Object[]> loadPageBySql(String sql, Object[] paramValues, Page<Object[]> page)
	{
		int firstResult = (page.getCurrPageNum() - 1) * page.getPerPageRecord();
		int maxResult = page.getPerPageRecord();

		Query query = super.getSession().createSQLQuery(sql);
		for (int i = 0; i < paramValues.length; i++) {
			query.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
		}
		query.setFirstResult(firstResult);
		query.setMaxResults(maxResult);

		List list = query.list();

		double temp = Double.valueOf(list.size()) / Double.valueOf(page.getPerPageRecord());

		try {
			BeanHelper.forceSetProperty(page, "totalRecord", list.size());
			BeanHelper.forceSetProperty(page, "totalPage", Double.valueOf(Math.ceil(temp))
					.intValue());
			BeanHelper.forceSetProperty(page, "data", list);
			processPageBeanProperties(page);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
		return page;
	}

	/**
	 * 
	 * 方法说明：hql分页查询
	 * 
	 * @param hql
	 *            hql
	 * @param paramValues
	 *            paramValues
	 * @param pageModel
	 *            pageModel
	 * @return Page<T>
	 */
	public Page<T> loadPageByHql(String hql, Object[] paramValues, Page<T> pageModel)
	{
		int firstResult = (pageModel.getCurrPageNum() - 1) * pageModel.getPerPageRecord();
		int maxResult = pageModel.getPerPageRecord();

		// 构造查旬对象，并带查询条件
		Query query = getSession().createQuery(hql);
		for (int i = 0; i < paramValues.length; i++) {
			query.setParameter(i, paramValues[i], HibernateTypeHelper.filter(paramValues[i]));
		}
		List<T> list = query.list();

		query.setMaxResults(maxResult);
		query.setFirstResult(firstResult);
		List<T> pagedData = query.list();

		try {
			double temp = Double.valueOf(list.size())
					/ Double.valueOf(pageModel.getPerPageRecord());

			BeanHelper.forceSetProperty(pageModel, "totalRecord", list.size());
			BeanHelper.forceSetProperty(pageModel, "data", pagedData);
			BeanHelper.forceSetProperty(pageModel, "totalPage", Double.valueOf(Math.ceil(temp))
					.intValue());

			processPageBeanProperties(pageModel);

			processPageBeanProperties(pageModel);
		} catch (NoSuchFieldException e) {

			e.printStackTrace();
		}
		return pageModel;
	}
}