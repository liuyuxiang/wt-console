package com.wt.uum2.dao;

import java.util.List;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDuty;

import nak.nsf.dao.support.BaseDao;

public interface UserDutyDao extends BaseDao<UserDuty>
{

	/**
	 * 方法说明：通过职务信息删除职务关系
	 *
	 * @param duty
	 */
	void deleteByDuty(Duty duty);

	/**
	 * 方法说明：通过用户信息删除职务关系
	 *
	 * @param user
	 */
	void deleteByUser(User user);

	/**
	 * 方法说明：获取用户列表
	 *
	 * @param d
	 * @return
	 */
	List<User> getUser(Duty d);

	/**
	 * 方法说明：获取职务列表
	 *
	 * @param user
	 * @return
	 */
	List<Duty> getDuty(User user);

	/**
	 * 方法说明：分页
	 *
	 * @param page
	 * @param pagesize
	 * @param duty
	 * @return
	 */
	UserPageResult<User> getUsersPage(Integer page, Integer pagesize, Duty duty);

	UserPageResult<UserDuty> searchUserByDuty(Integer page, Integer pagesize, Condition condition,
			Duty duty);

}
