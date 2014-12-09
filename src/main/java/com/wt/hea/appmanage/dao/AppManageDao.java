package com.wt.hea.appmanage.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.appmanage.model.AppManage;
import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-26
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface AppManageDao extends EntityDao<AppManage>
{
	/**
	 * 
	 * 方法说明：@see com.wt.hea.common.dao.EntityDao#delete(java.lang.Object)
	 * 
	 * @param e
	 *            e
	 * 
	 */
	public void delete(AppManage e);

	/**
	 * 
	 * 方法说明： @see com.wt.hea.common.dao.EntityDao#deleteById(java.io.Serializable)
	 * 
	 * @return boolean
	 * 
	 * @param id
	 *            id
	 * 
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 
	 * 方法说明： @see com.wt.hea.common.dao.EntityDao#findById(java.io.Serializable)
	 * 
	 * @return AppManage
	 * 
	 * @param id
	 *            id
	 * 
	 */
	public AppManage findById(Serializable id);

	/**
	 * 
	 * 方法说明： @see
	 * com.wt.hea.common.dao.EntityDao#findByProperty(java.lang.Object,java.lang.String)
	 * 
	 * @return List<AppManage>
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * 
	 */
	public List<AppManage> findByProperty(String property, Object value);

	/**
	 * 
	 * 方法说明： @see com.wt.hea.common.dao.EntityDao#loadPage(com.wt.hea.common.model.Page)
	 * 
	 * @return Page<AppManage>
	 * @param pageModel
	 *            pageModel
	 * 
	 */
	public Page<AppManage> loadPage(Page<AppManage> pageModel);

	/**
	 * 
	 * 方法说明： @see com.wt.hea.common.dao.EntityDao#save(java.lang.Object)
	 * 
	 * @return boolean
	 * @param e
	 *            e
	 * 
	 */
	public boolean save(AppManage e);

	/**
	 * 
	 * 方法说明： @see com.wt.hea.common.dao.EntityDao#update(java.lang.Object)
	 * 
	 * @return AppManage
	 * @param e
	 *            e
	 * 
	 * 
	 */
	public AppManage update(AppManage e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param obj
	 *            obj
	 * @return List<AppManage>
	 */
	public List<AppManage> findByExample(AppManage obj);

}
