package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.ThemeDefinition;

/**
 * <pre>
 * 业务名:
 * 功能说明: 主题定义管理Service
 * 编写日期:	2011-5-13
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ThemeDefinitionService {

	/**
	 * 方法说明：
	 *删除ThemeDefinition信息
	 * @param e ThemeDefinition对象
	 */
	public void delete(ThemeDefinition e);
	
	/**
	 * 方法说明：
	 *根据id删除ThemeDefinition信息
	 * @param id ThemeDefinition的id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 * 查找所有ThemeDefinition信息
	 * @return ThemeDefinition对象列表
	 */
	public List<ThemeDefinition> findAll();
	
	/**
	 * 方法说明：
	 *根据ThemeDefinition的属性排序后的所有ThemeDefinition信息
	 * @param property ThemeDefinition属性
	 * @param isAsc 是否按升序排序
	 * @return ThemeDefinition对象列表
	 */
	public List<ThemeDefinition> findAll(String property, Boolean isAsc);
	
	/**
	 * 方法说明：
	 * 根据id查找ThemeDefinition对象
	 * @param id ThemeDefinition的id
	 * @return ThemeDefinition对象
	 */
	public ThemeDefinition findById(Serializable id);
	
	/**
	 * 方法说明：
	 * 根据ThemeDefinition的属性获取ThemeDefinition信息
	 * @param property ThemeDefinition对象的属性
	 * @param value ThemeDefinition属性的值
	 * @return ThemeDefinition对象列表
	 */
	public List<ThemeDefinition> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *保存ThemeDefinition对象
	 * @param e ThemeDefinition对象
	 */
	public void save(ThemeDefinition e);
	
	/**
	 * 方法说明：
	 *更新ThemeDefinition信息
	 * @param e ThemeDefinition对象
	 * @return ThemeDefinition对象
	 */
	public ThemeDefinition update(ThemeDefinition e);

	/**
	 * 方法说明：
	 *获取ThemeDefinition的分页信息
	 * @param pageModel 分页对象
	 * @return ThemeDefinition的分页信息
	 */
	public Page<ThemeDefinition> loadPage(Page<ThemeDefinition> pageModel);
}
