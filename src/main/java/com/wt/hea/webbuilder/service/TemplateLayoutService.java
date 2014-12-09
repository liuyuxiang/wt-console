package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.TemplateLayout;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板布局Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TemplateLayoutService {

	/**
	 * 方法说明：
	 *删除TemplateLayout信息
	 * @param e TemplateLayout对象
	 */
	public void delete(TemplateLayout e);
	
	/**
	 * 方法说明：
	 *根据id删除TemplateLayout信息
	 * @param id TemplateLayout的Id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有TemplateLayout信息
	 * @return TemplateLayout对象列表
	 */
	public List<TemplateLayout> findAll();
	
	/**
	 * 方法说明：
	 *根据TemplateLayout属性查找TemplateLayout信息
	 * @param property TemplateLayout的属性
	 * @param value TemplateLayout属性对应的值
	 * @return TemplateLayout对象列表
	 */
	public List<TemplateLayout> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *根据TemplateLayout的属性查找TemplateLayout信息
	 * @param property TemplateLayout的而熟悉
	 * @param value TemplateLayout属性对应的值
	 * @return TemplateLayout对象
	 */
	public TemplateLayout findByPropertyUnique(String property, Object value);
	
	/**
	 * 方法说明：
	 *根据id查找TemplateLayout信息
	 * @param id TemplateLayout的Id
	 * @return TemplateLayout对象
	 */
	public TemplateLayout findById(Serializable id);
	
	/**
	 * 方法说明：
	 *保存TemplateLayout信息
	 * @param e TemplateLayout对象
	 */
	public void save(TemplateLayout e);
	
	/**
	 * 方法说明：
	 *更新TemplateLayout信息
	 * @param e TemplateLayout对象
	 * @return TemplateLayout对象
	 */
	public TemplateLayout update(TemplateLayout e);
	
	/**
	 * 方法说明：
	 *获取TemplateLayout的分页信息
	 * @param pageModel 分页对象
	 * @return TemplateLayout的分页信息
	 */
	public Page<TemplateLayout> loadPage(Page<TemplateLayout> pageModel);
}
