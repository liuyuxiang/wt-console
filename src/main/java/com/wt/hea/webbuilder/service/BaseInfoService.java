package com.wt.hea.webbuilder.service;

import java.util.List;
import java.util.Map;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.BaseInfo;

/***
 * 站点的首页资源业务操作接口定义
 * 
 * @author 袁明敏
 * 
 */
public interface BaseInfoService {
	/**
	 * 
	 * 方法说明：保存
	 * 
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(BaseInfo e);

	/**
	 * 删除首页信息
	 * 
	 * @param e
	 *            baseInfo 对象
	 */
	public void delete(BaseInfo e);

	/**
	 * 查询所有的首页资源
	 * 
	 * @return 所有BaseInfo 对象列表
	 */
	public List<BaseInfo> findAll();

	/**
	 * 根据属性查询首页信息
	 * 
	 * @param property
	 *            BaseInfo的属性
	 * @param value
	 *            BaseInfo的属性值
	 * @return BaseInfo列表
	 */
	public List<BaseInfo> findByProperty(String property, Object value);

	/**
	 * 更新首页信息
	 * 
	 * @param e
	 *            首页信息对象BaseInfo
	 * @return 更新后的BaseInfo
	 */
	public BaseInfo update(BaseInfo e);

	/**
	 * 根据id查找baseInfo信息
	 * 
	 * @param id
	 *            baseInfo id
	 * @return 返回baseInfo对象
	 */
	public BaseInfo findById(String id);

	/**
	 * 查询所有首页信息并根据property排序
	 * 
	 * @param property
	 *            排序字段
	 * @param isAsc
	 *            boolean值是否升序
	 * @return baseInfo列表
	 */
	public List<BaseInfo> findAll(String property, Boolean isAsc);

	/**
	 * 首页信息的分页信息
	 * 
	 * @param pageModel
	 *            分页对象
	 * @return 首页信息的分页信息
	 */
	public Page<BaseInfo> loadPage(Page<BaseInfo> pageModel);

	/**
	 * 根据多个属性查询首页信息列表
	 * 
	 * @param map
	 *            Map<String,Object>多个查询条件
	 * @return baseInfo对象列表
	 */
	public List<BaseInfo> findByProperty(Map<String, Object> map);

}
