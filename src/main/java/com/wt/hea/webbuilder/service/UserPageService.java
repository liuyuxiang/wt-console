package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.UserPage;

/**
 * <pre>
 * 业务名:
 * 功能说明: 用户个性化信息列表
 * 编写日期:	2011-5-13
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserPageService  {
	
	/**
	 * 方法说明：
	 * 删除UserPage信息
	 * @param e UserPage对象
	 */
	public void delete(UserPage e);
	
	/**
	 * 方法说明：
	 *查找所有的个性化信息
	 * @return UserPage对象列表
	 */
	public List<UserPage> findAll();
	
	/**
	 * 方法说明：
	 * 根据id查找个性化信息页面
	 * @param id 个性化信息id
	 * @return UserPage对象
	 */
	public UserPage findById(Serializable id);
	
	/**
	 * 方法说明：
	 * 根据UserPage的属性查找个性化信息
	 * @param property UserPage的属性
	 * @param value UserPage属性的值
	 * @return UserPage对象列表
	 */
	public List<UserPage> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 * 根据id删除个性化页面
	 * @param id UserPage的id
	 */
	public void deleteById(Serializable id);
	
	
	/**
	 * 方法说明：
	 * 保存UserPage对象
	 * @param e UserPage对象
	 */
	public void save(UserPage e);
	
	/**
	 * 方法说明：
	 * 更新UserPage对象
	 * @param e UserPage对象
	 * @return UserPage对象
	 */
	public UserPage update(UserPage e);
	
	/**
	 * 方法说明：
	 * 根据用户id查询所有个性化页面
	 * @param userId 用户id
	 * @return UserPage对象
	 */
	public List<UserPage> findByUserId(String userId);
	
	/**
	 * 方法说明：
	 * 判断页面是否已被添加
	 * @param pageNo 页面编号
	 * @param userId 用户id
	 * @return 是否已添加到页面
	 */
	public boolean isAddedTemplatePage(String pageNo, String userId);
	
	/**
	 * 方法说明：
	 *
	 * @param userId 用户id
	 * @param pageNos 页面编号
	 * @return UserPage信息
	 */
	public List<UserPage> findByUserIdAndNos(String userId , String[] pageNos);
}
