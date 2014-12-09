package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.TemplatePortletInfo;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板页面的portlet管理
 * 编写日期:	2011-5-13
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TemplatePortletInfoService {

	/**
	 * 方法说明：
	 *删除模板portlet信息
	 * @param e TemplatePortletInfo对象
	 */
	public void delete(TemplatePortletInfo e);
	
	/**
	 * 方法说明：
	 *根据id删除TemplatePortletInfo信息
	 * @param id TemplatePortletInfo的Id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有的模板portlet管理
	 * @return TemplatePortletInfo对象列表
	 */
	public List<TemplatePortletInfo> findAll();
	
	/**
	 * 方法说明：
	 *根据id查找TemplatePortletInfo信息
	 * @param id TemplatePortletInfo的id
	 * @return TemplatePortletInfo对象
	 */
	public TemplatePortletInfo findById(Serializable id);
	
	/**
	 * 方法说明：
	 *根据TemplatePortletInfo的属性查找TemplatePortletInfo信息
	 * @param property TemplatePortletInfo对象的属性
	 * @param value TemplatePortletInfo属性的值
	 * @return TemplatePortletInfo对象列表
	 */
	public List<TemplatePortletInfo> findByProperty(String property,
			Object value);
	
	/**
	 * 方法说明：
	 *根据TemplatePortletInfo的属性查找TemplatePortletInfo信息
	 * @param property property TemplatePortletInfo对象的属性
	 * @param value TemplatePortletInfo属性的值
	 * @return TemplatePortletInfo对象
	 */
	public TemplatePortletInfo findByPropertyUnique(String property,
			Object value);
	
	/**
	 * 方法说明：
	 *保存TemplatePortletInfo信息
	 * @param e TemplatePortletInfo对象
	 */
	public void save(TemplatePortletInfo e);
	
	/**
	 * 方法说明：
	 *更新TemplatePortletInfo信息
	 * @param e TemplatePortletInfo对象
	 * @return TemplatePortletInfo对象
	 */
	public TemplatePortletInfo update(TemplatePortletInfo e);
}
