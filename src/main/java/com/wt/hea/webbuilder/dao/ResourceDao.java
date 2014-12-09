package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ResourceSite;

/**
 * (站点)资源操作持久层操作接口定义
 * 
 * @author 袁明敏
 * 
 */
public interface ResourceDao extends EntityDao<ResourceSite>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(ResourceSite e);

	/**
	 * 查询所有资源
	 * 
	 * @return 资源列表
	 */
	public List<ResourceSite> findAll();

	/**
	 * 根据resource中的某些属性查询资源列表
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return 资源列表
	 */
	public List<ResourceSite> findByProperty(String property, Object value);

	/**
	 * 更新资源对象
	 * 
	 * @param e
	 *            资源对象
	 * @return 资源实例
	 */
	public ResourceSite update(ResourceSite e);

	/**
	 * 根据id查找一个资源
	 * 
	 * @param id
	 *            resourceId
	 * @return 资源对象
	 */
	public ResourceSite findById(Serializable id);

	/**
	 * 查询根据property排序后的所有资源
	 * 
	 * @param property
	 *            排序属性
	 * @param isAsc
	 *            是否升序
	 * @return 返回排序后的资源对象
	 */
	public List<ResourceSite> findAll(String property, Boolean isAsc);

	/**
	 * 获取资源分页对象
	 * 
	 * @param pageModel
	 *            分页对象
	 * @return 分页后的资源对象
	 */
	public Page<ResourceSite> loadPage(Page<ResourceSite> pageModel);

	/**
	 * 跟据实体对象多个属性的值，查找对象
	 * 
	 * @param map
	 *            key为实体对象属性名，value为对应的属性值
	 * @return 返回符合条件的资源列表
	 */
	public List<ResourceSite> findByProperty(Map<String, Object> map);
}
