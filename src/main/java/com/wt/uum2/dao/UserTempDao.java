package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.UserTemp;
import com.wt.uum2.userlist.UserTempType;

/**
 * <pre>
 * 业务名: UserTempDao
 * 功能说明: UserTempDao
 * 编写日期:	2011-11-9
 * 作者:	Liu 
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserTempDao extends BaseDao<UserTemp>
{

	/**
	 * 方法说明：getUserTempList
	 * 
	 * @return List
	 */
	List<UserTemp> getUserTempList();

	/**
	 * 方法说明：getUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	UserPageResult<UserTemp> getUserTempList(Integer page, Integer pagesize);

	/**
	 * 方法说明：getUserTempMappingList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	UserPageResult getUserTempMappingList(Integer page, Integer pagesize);

	/**
	 * 方法说明：searchUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Deprecated
	UserPageResult<UserTemp> searchUserTempList(Integer page, Integer pagesize, Condition condition);

	/**
	 * 方法说明：searchUserTempMappingList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Deprecated
	UserPageResult searchUserTempMappingList(Integer page, Integer pagesize, Condition condition);

	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param userUuid
	 *            userUuid
	 * @return UserTemp
	 */
	UserTemp getUserTempByUuid(String userUuid);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	List<UserTemp> getUserTempListByStatus(Long l);

	/**
	 * 
	 * 方法说明：根据条件取得erp用户列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	UserPageResult getUserTempConditionList(Integer page, Integer pagesize, UserTempType type);

	/**
	 * 
	 * 方法说明：根据搜索类型和用户过滤条件搜索所有符合条件的用户
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition,
			UserTempType type);

}
