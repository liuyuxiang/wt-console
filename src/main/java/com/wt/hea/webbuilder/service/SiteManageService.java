package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wt.hea.webbuilder.model.SiteManage;

/**
 * <pre>
 * 业务名:
 * 功能说明: 站点管理Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface SiteManageService {
	
	/**
	 * 方法说明：
	 *删除站点信息
	 * @param e SiteManage对象
	 */
	public void delete(SiteManage e);
	
	/**
	 * 方法说明：
	 *根据id删除站点信息
	 * @param id siteId
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有站点信息
	 * @return SiteManage对象列表
	 */
	public List<SiteManage> findAll();
	
	/**
	 * 方法说明：
	 *根据SiteManage的属性排序后的所有SiteManage信息
	 * @param property SiteManage的属性
	 * @param isAsc 是否是升序
	 * @return SiteManage对象列表
	 */
	public List<SiteManage> findAll(String property, Boolean isAsc);
	
	/**
	 * 方法说明：
	 *根据SiteManage的属性查找SiteManage信息
	 * @param property SiteManage的属性
	 * @param value SiteManage属性对应的值
	 * @return SiteManage对象列表
	 */
	public List<SiteManage> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *保存SiteManage对象
	 * @param e SiteManage对象
	 */
	public void save(SiteManage e);
	
	/**
	 * 方法说明：
	 *更新SiteManage对象
	 * @param e SiteManage对象
	 * @return SiteManage对象
	 */
	public SiteManage update(SiteManage e);
	
	/**
	 * 方法说明：
	 *根据id查找SiteManage对象
	 * @param id siteId
	 * @return SiteManage对象
	 */
	public SiteManage findById(Serializable id);
	
	/**
	 * 方法说明：
	 * 根据map里放置的查询条件查找SiteManage对象信息
	 * @param map SiteManage对应的属性键值对
	 * @return SiteManage对象列表
	 */
	public List<SiteManage> findByProperty(Map<String,Object> map) ;
}
