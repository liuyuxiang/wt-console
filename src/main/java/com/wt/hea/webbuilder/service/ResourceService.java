package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ResourceSite;

/***
 * 站点的所有资源业务操作接口定义
 * 
 * @author 袁明敏
 * 
 */
public interface ResourceService
{

	/**
	 * 方法说明： 删除Resource信息
	 * 
	 * @param e
	 *            Resource对象
	 */
	public void delete(ResourceSite e);

	/**
	 * 方法说明： 根据id删除Resource信息
	 * 
	 * @param id
	 *            ResourceId
	 * @return boolean
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 方法说明： 查找所有的Resource对象
	 * 
	 * @return Resource对象列表
	 */
	public List<ResourceSite> findAll();

	/**
	 * 方法说明： 根据Resource的属性查找Resource对象
	 * 
	 * @param property
	 *            Resource的属性
	 * @param value
	 *            Resource属性对应值
	 * @return Resource对象列表
	 */
	public List<ResourceSite> findByProperty(String property, Object value);

	/**
	 * 方法说明： 根据map所放置的条件查询Resource信息
	 * 
	 * @param map
	 *            Resource条件
	 * @return Resource对象列表
	 */
	public List<ResourceSite> findByProperty(Map<String, Object> map);

	/**
	 * 方法说明： 保存Resource对象
	 * 
	 * @param e
	 *            Resource对象
	 * @return boolean
	 */
	public boolean save(ResourceSite e);

	/**
	 * 方法说明： 更新Resource对象
	 * 
	 * @param e
	 *            Resource对象
	 * @return 更新后的Resource对象
	 */
	public ResourceSite update(ResourceSite e);

	/**
	 * 方法说明： 根据id查找Resource对象
	 * 
	 * @param id
	 *            ResourceId
	 * @return Resource对象
	 */
	public ResourceSite findById(Serializable id);

	/**
	 * 方法说明： 查询Resource对象信息并以Resource的属性排序
	 * 
	 * @param property
	 *            Resource对象的属性
	 * @param isAsc
	 *            是否为升序
	 * @return 排序后的Resource对象列表
	 */
	public List<ResourceSite> findAll(String property, Boolean isAsc);

	/**
	 * 方法说明： 获取Resource的分页信息
	 * 
	 * @param pageModel
	 *            分页对象
	 * @return 返回Resource的分页信息
	 */
	public Page<ResourceSite> loadPage(Page<ResourceSite> pageModel);
}
