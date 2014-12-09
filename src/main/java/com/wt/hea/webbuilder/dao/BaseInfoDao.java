package com.wt.hea.webbuilder.dao;

import java.util.List;
import java.util.Map;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.BaseInfo;

/***
 * 某站点下的首页资源操作(持久层)接口定义 BaseInfo封装 侧帘、飘窗、LOGO、版权等资源信息
 * 
 * @author 袁明敏
 * 
 */
public interface BaseInfoDao extends EntityDao<BaseInfo>
{

	/**
	 * 删除首页信息
	 * 
	 * @param e
	 *            首页信息对象
	 */
	public void delete(BaseInfo e);

	/**
	 * 查询所有的首页资源
	 * 
	 * @return 所有BaseInfo 对象列表
	 */
	public List<BaseInfo> findAll();

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<BaseInfo>
	 */
	public List<BaseInfo> findAll(String property, Boolean isAsc);

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
	 * 根据多个属性查询首页信息列表
	 * 
	 * @param map
	 *            Map<String,Object>多个查询条件
	 * @return baseInfo对象列表
	 */
	public List<BaseInfo> findByProperty(Map<String, Object> map);

	/**
	 * 分页加载
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<BaseInfo>
	 */
	public Page<BaseInfo> loadPage(Page<BaseInfo> pageModel);
}
