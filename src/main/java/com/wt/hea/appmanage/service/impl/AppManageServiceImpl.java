package com.wt.hea.appmanage.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.appmanage.model.AppManage;
import com.wt.hea.appmanage.service.AppManageService;
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
public class AppManageServiceImpl extends BaseService implements AppManageService
{

	/**
	 * 
	 * 方法说明：@see
	 * com.wt.hea.appmanage.service.AppManageService#delete(com.wt.hea.appmanage.
	 * model.AppManage)
	 * 
	 * @param e
	 *            e
	 * 
	 */
	public void delete(AppManage e)
	{
		this.appManageDao.delete(e);
	}

	/**
	 * 
	 * 方法说明：@see com.wt.hea.appmanage.service.AppManageService#deleteById(java.io.Serializable)
	 * 
	 * @param id
	 *            id
	 * 
	 */
	public void deleteById(Serializable id)
	{
		this.appManageDao.deleteById(id);
	}

	/**
	 * 
	 * 方法说明：@see
	 * com.wt.hea.appmanage.service.AppManageService#findByExample(com.wt.hea.appmanage
	 * .model.AppManage)
	 * 
	 * @return List<AppManage>
	 * @param obj
	 *            obj
	 * 
	 */
	public List<AppManage> findByExample(AppManage obj)
	{
		return this.appManageDao.findByExample(obj);
	}

	/**
	 * 
	 * 方法说明：@see com.wt.hea.appmanage.service.AppManageService#findById(java.io.Serializable)
	 * 
	 * @return AppManage
	 * @param id
	 *            id
	 * 
	 */
	public AppManage findById(Serializable id)
	{
		return this.appManageDao.findById(id);
	}

	/**
	 * 
	 * 方法说明：@see com.wt.hea.appmanage.service.AppManageService#findByProperty(java.lang.String,
	 * java.lang.Object)
	 * 
	 * @return List<AppManage>
	 * @param property
	 *            property
	 * 
	 * @param value
	 *            value
	 * 
	 */
	public List<AppManage> findByProperty(String property, Object value)
	{
		return this.appManageDao.findByProperty(property, value);
	}

	/**
	 * 
	 * 方法说明：@see
	 * com.wt.hea.appmanage.service.AppManageService#loadPage(com.wt.hea.common.model
	 * .Page)
	 * 
	 * @return List<AppManage>
	 * @param pageModel
	 *            pageModel
	 * 
	 */
	public Page<AppManage> loadPage(Page<AppManage> pageModel)
	{
		return this.appManageDao.loadPage(pageModel);
	}

	/**
	 * 
	 * 方法说明：@see
	 * com.wt.hea.appmanage.service.AppManageService#save(com.wt.hea.appmanage.model
	 * .AppManage)
	 * 
	 * @param e
	 *            e
	 * 
	 */
	public void save(AppManage e)
	{
		this.appManageDao.save(e);
	}

	/**
	 * 
	 * 方法说明：@see
	 * com.wt.hea.appmanage.service.AppManageService#update(com.wt.hea.appmanage.
	 * model.AppManage)
	 * 
	 * @return AppManage
	 * @param e
	 *            e
	 */
	public AppManage update(AppManage e)
	{
		return this.appManageDao.update(e);
	}

}
