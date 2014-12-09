package com.wt.hea.webbuilder.dao;

import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.LayoutDefinition;

/**
 * <pre>
 * 业务名:
 * 功能说明: 布局定义Dao
 * 编写日期:	2011-3-25
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface LayoutDefinitionDao extends EntityDao<LayoutDefinition>{

	/**
	 * 删除布局信息
	 * @param e 布局定义对象
	 */
	public void delete(LayoutDefinition e);
	
	/**
	 * 查询所有布局信息
	 * @return LayoutDefinition列表
	 */
	public List<LayoutDefinition> findAll();
	
	/**
	 * 查询所有布局信息并根据property排序
	 * @param property 排序字段
	 * @param isAsc boolean值是否升序
	 * @return LayoutDefinition列表
	 */
	public List<LayoutDefinition> findAll(String property, Boolean isAsc);

	
	/**
	 * 根据属性查找布局
	 * @param property 做为查询条件的属性
	 * @param value 属性值
	 * @return LayoutDefinition列表
	 */
	public List<LayoutDefinition> findByProperty(String property, Object value);
	
}
