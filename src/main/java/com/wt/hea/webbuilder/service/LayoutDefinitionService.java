package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.LayoutDefinition;

/**
 * <pre>
 * 业务名:
 * 功能说明: 布局信息Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface LayoutDefinitionService {

	/**
	 * 方法说明：
	 *删除布局信息
	 * @param e 布局信息
	 */
	public void delete(LayoutDefinition e);
	
	/**
	 * 方法说明：
	 *根据id删除布局信息
	 * @param id 布局信息id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有的布局信息
	 * @return 布局信息列表
	 */
	public List<LayoutDefinition> findAll();
	
	/**
	 * 方法说明：
	 * 查找所有的布局信息并根据property字段排序
	 * @param property 布局信息property
	 * @param isAsc 是否升序
	 * @return 布局信息列表
	 */
	public List<LayoutDefinition> findAll(String property, Boolean isAsc);
	
	/**
	 * 方法说明：
	 * 根据id查找布局信息
	 * @param id 布局信息id
	 * @return 布局信息对象
	 */
	public LayoutDefinition findById(Serializable id);
	
	/**
	 * 方法说明：
	 * 保存布局信息
	 * @param e 要保存的布局信息对象
	 */
	public void save(LayoutDefinition e);
	
	/**
	 * 方法说明：
	 * 更新布局信息
	 * @param e 要更新的布局对象
	 * @return 更新后的布局信息对象
	 */
	public LayoutDefinition update(LayoutDefinition e);
	
	/**
	 * 方法说明：
	 * 根据属性查找布局信息
	 * @param property LayoutDefinition的属性
	 * @param value LayoutDefinition的property对应的属性值
	 * @return 布局信息列表
	 */
	public List<LayoutDefinition> findByProperty(String property, Object value);
}
