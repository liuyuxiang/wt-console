package com.wt.hea.webbuilder.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.UserPersonalInfo;

/**
 * 用户个性化信息的业务方法
 * @author xiaoqi
 *
 */
public interface UserPersonalInfoService {

	/**
	 * 方法说明：
	 *删除UserPersonalInfo信息
	 * @param e UserPersonalInfo对象
	 */
	public void delete(UserPersonalInfo e);
	
	/**
	 * 方法说明：
	 *根据id删除UserPersonalInfo信息
	 * @param id UserPersonalInfo对象Id
	 */
	public void deleteById(Serializable id);
	
	/**
	 * 方法说明：
	 *查找所有UserPersonalInfo信息
	 * @return UserPersonalInfo对象列表
	 */
	public List<UserPersonalInfo> findAll();
	
	/**
	 * 方法说明：
	 * 获取根据property指定的排序方式的所有UserPersonalInfo信息
	 * @param property UserPersonalInfo对象的属性
	 * @param isAsc 是否升序
	 * @return UserPersonalInfo列表
	 */
	public List<UserPersonalInfo> findAll(String property, Boolean isAsc);
	
	/**
	 * 方法说明：
	 * 根据id查找UserPersonalInfo信息
	 * @param id UserPersonalInfo对象的id
	 * @return UserPersonalInfo对象
	 */
	public UserPersonalInfo findById(Serializable id);
	
	/**
	 * 方法说明：
	 *根据属性获取UserPersonalInfo信息
	 * @param property UserPersonalInfo的property
	 * @param value UserPersonalInfo对象的属性值
	 * @return UserPersonalInfo对象列表
	 */
	public List<UserPersonalInfo> findByProperty(String property, Object value);
	
	/**
	 * 方法说明：
	 *保存UserPersonalInfo信息
	 * @param e UserPersonalInfo对象
	 */
	public void save(UserPersonalInfo e);
	
	/**
	 * 更新页面portlet
	 * @param e UserPersonalInfo对象
	 * @return UserPersonalInfo对象
	 */
	public UserPersonalInfo update(UserPersonalInfo e);
	
	/**
	 * 判断是否已添加
	 * @param userId 用户id
	 * @param pageId pageId
	 * @param portletId portletId
	 * @return 是否已添加
	 */
	public boolean isAdded(String userId , String portletId,String pageId) ;
	
	/**
	 * 根据portlet所在列查出该列所有的portlet
	 * @param sortNo 列编号
	 * @param userId 用户id
	 * @return UserPersonalInfo对象列表
	 */
	public List<UserPersonalInfo> findBySortNo(int sortNo , String userId);
	
}
