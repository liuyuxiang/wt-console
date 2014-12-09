package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ResourceSite;
import com.wt.hea.webbuilder.service.ResourceService;

/***
 * 站点的首页资源业务操作接口定义的实现
 * 
 * @author 袁明敏
 * 
 */
public class ResourceServiceImpl extends BaseService implements ResourceService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(ResourceSite e)
	{
		this.resourceDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		return this.resourceDao.deleteById(id);
	}

	/**
	 * @return List<Resource>
	 */
	public List<ResourceSite> findAll()
	{
		return this.resourceDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<Resource>
	 */
	public List<ResourceSite> findByProperty(String property, Object value)
	{
		return this.resourceDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(ResourceSite e)
	{
		return this.resourceDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return Resource
	 */
	public ResourceSite update(ResourceSite e)
	{
		return this.resourceDao.update(e);
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<Resource>
	 */
	public List<ResourceSite> findAll(String property, Boolean isAsc)
	{
		return this.resourceDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return Resource
	 */
	public ResourceSite findById(Serializable id)
	{
		return this.resourceDao.findById(id);

	}

	/**
	 * @param pageModel
	 *            pageModel
	 * @return Page<Resource>
	 */
	public Page<ResourceSite> loadPage(Page<ResourceSite> pageModel)
	{
		return this.resourceDao.loadPage(pageModel);
	}

	/**
	 * @param map
	 *            map
	 * @return List<Resource>
	 */
	public List<ResourceSite> findByProperty(Map<String, Object> map)
	{
		return this.resourceDao.findByProperty(map);
	}
}
