package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDuty;

/**
 * <pre>
 * 业务名:职务服务接口
 * 功能说明: 
 * 编写日期:	2014-9-22
 * 作者:	liuyx
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DutyService
{

	/**
	 * 方法说明：获取职责信息
	 *
	 * @param uuid 主键
	 * @return duty
	 */
	Duty get(String uuid);

	/**
	 * 方法说明：获取职责信息
	 *
	 * @param dutyID id
	 * @return duty
	 */
	Duty getByID(String dutyID);

	/**
	 * 方法说明：通过级别获取职务信息
	 *
	 * @param level 级别1最大，数字越大级别越小
	 * @return dutylist
	 */
	List<Duty> getByLevel(int level);

	/**
	 * 方法说明：获取上级职务
	 *
	 * @param duty 职务节点
	 * @param include 是否包含到根
	 * @return
	 */
	List<Duty> getParent(Duty duty, Boolean include);

	/**
	 * 方法说明：获取下级职务
	 *
	 * @param duty 职务节点
	 * @param include 是否包含到底
	 * @return
	 */
	List<Duty> getChildren(Duty duty, Boolean include);

	/**
	 * 方法说明：获取职务下用户
	 *
	 * @param d
	 * @return list
	 */
	List<User> getUsers(Duty d);

	/**
	 * 方法说明：获取职务列表
	 *
	 * @return list
	 */
	List<Duty> getAll();
	
	/**
	 * 方法说明：更新
	 *
	 * @param duty
	 */
	void update(Duty duty);
	
	/**
	 * 方法说明：删除
	 *
	 * @param duty
	 */
	void delete(Duty duty);
	
	/**
	 * 方法说明：
	 *
	 * @param dutyID
	 */
	void deleteByID(String dutyID);
	
	/**
	 * 方法说明：添加职务
	 *
	 * @param d
	 * @param u
	 * @return
	 */
	UserDuty addUser(Duty d,User u);
	
	/**
	 * 方法说明：添加职务
	 *
	 * @param d
	 * @param us
	 * @return
	 */
	List<UserDuty> addUsers(Duty d,List<User> us);
	
	/**
	 * 方法说明：添加职务
	 *
	 * @param d
	 * @param us
	 * @return
	 */
	List<UserDuty> addUsers(List<Duty> d,List<User> us);
	
	/**
	 * 方法说明：删除职务
	 *
	 * @param d
	 * @param u
	 * @return
	 */
	boolean removeUser(Duty d,User u);
	
	/**
	 * 方法说明：删除职务
	 *
	 * @param d
	 * @param us
	 * @return
	 */
	boolean removeUsers(Duty d,List<User> us);

	/**
	 * 方法说明：删除职务
	 *
	 * @param d
	 * @param us
	 * @return
	 */
	boolean removeUsers(List<Duty> d,List<User> us);

	/**
	 * 方法说明：获取职务
	 *
	 * @param user
	 * @return
	 */
	List<Duty> getDuty(User user);

	/**
	 * 方法说明：获取职务下用户分页
	 *
	 * @param page
	 * @param pagesize
	 * @param duty
	 * @return
	 */
	UserPageResult<User> getUsersByDuty(Integer page, Integer pagesize, Duty duty);

	UserPageResult<UserDuty> searchUserByDuty(Integer page, Integer pagesize, Condition condition,
			Duty duty);

	/**
	 * 方法说明：根据名称计算id
	 *
	 * @param name
	 * @return
	 */
	String getDutyId(String name);

}
