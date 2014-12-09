package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.PlaceHolderGroup;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板展位符权限控制Service
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface PlaceHolderGroupService {

	/**
	 * 方法说明：
	 *删除一个占位符权限信息
	 * @param e 占位符权限对象
	 */
	public void delete(PlaceHolderGroup e);
	
	/**
	 * 方法说明：
	 *根据id删除占位符权限信息
	 * @param id 占位符权限id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有的占位符权限信息
	 * @return PlaceHolderGroup对象列表
	 */
	public List<PlaceHolderGroup> findAll();
	
	/**
	 * 方法说明：
	 *根据id查找PlaceHolderGroup对象
	 * @param id PlaceHolderGroup的ID
	 * @return PlaceHolderGroup对象
	 */
	public PlaceHolderGroup findById(Serializable id);
	
	/**
	 * 方法说明：
	 *根据属性查找PlaceHolderGroup信息
	 * @param property PlaceHolderGroup的属性
	 * @param value PlaceHolderGroup的属性对应的值
	 * @return PlaceHolderGroup对象列表
	 */
	public List<PlaceHolderGroup> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *保存PlaceHolderGroup对象
	 * @param e PlaceHolderGroup对象
	 */
	public void save(PlaceHolderGroup e);
	
	/**
	 * 方法说明：
	 *更新PlaceHolderGroup对象
	 * @param e PlaceHolderGroup对象
	 * @return 更新后的PlaceHolderGroup对象
	 */
	public PlaceHolderGroup update(PlaceHolderGroup e);
	
	/**
	 * 方法说明：
	 *根据组id和模板id查找PlaceHolderGroup信息
	 * @param groupId groupId
	 * @param tmplId tmplId
	 * @return PlaceHolderGroup对象列表
	 */
	public List<PlaceHolderGroup> findByGroupAndTmpl(String groupId,String tmplId);
}
