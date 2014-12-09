package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.PortletProperty;

/**
 * <pre>
 * 业务名:
 * 功能说明: portlet管理Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PortletPropertyService
{

	/**
	 * 方法说明： 删除portlet信息
	 * 
	 * @param e
	 *            PortletProperty对象
	 */
	public void delete(PortletProperty e);

	/**
	 * 方法说明： 查找所有的portlet信息
	 * 
	 * @return PortletProperty对象列表
	 */
	public List<PortletProperty> findAll();

	/**
	 * 方法说明： 根据id删除portlet信息
	 * 
	 * @param id
	 *            portletId
	 * @return boolean
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 方法说明： 查找所有的portlet信息并按portlet指定属性排序
	 * 
	 * @param property
	 *            portlet的属性
	 * @param isAsc
	 *            是否是升序
	 * @return PortletProperty对象列表
	 */
	public List<PortletProperty> findAll(String property, Boolean isAsc);

	/**
	 * 方法说明： 根据id查找portlet对象
	 * 
	 * @param id
	 *            portletId
	 * @return PortletProperty 对象
	 */
	public PortletProperty findById(Serializable id);

	/**
	 * 方法说明： 根据PortletProperty的属性查找PortletProperty信息
	 * 
	 * @param property
	 *            PortletProperty对象的属性
	 * @param value
	 *            PortletProperty对象的属性对应的值
	 * @return PortletProperty对象列表
	 */
	public List<PortletProperty> findByProperty(String property, Object value);

	/**
	 * 方法说明： 保存PortletProperty对象
	 * 
	 * @param e
	 *            PortletProperty对象
	 * @return boolean
	 */
	public boolean save(PortletProperty e);

	/**
	 * 方法说明： 更新PortletProperty对象
	 * 
	 * @param e
	 *            PortletProperty对象
	 * @return PortletProperty对象
	 */
	public PortletProperty update(PortletProperty e);

	/**
	 * 方法说明： 获取PortletProperty分页对象
	 * 
	 * @param page
	 *            分页对象
	 * @return 分页对象
	 */
	public Page<PortletProperty> loadPage(Page<PortletProperty> page);
}
