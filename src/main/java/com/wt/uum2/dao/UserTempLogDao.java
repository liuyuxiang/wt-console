package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.UserTempLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserTempLogDao extends BaseDao<UserTempLog>
{

	/**
	 * 方法说明：getModifyUserTemp
	 * 
	 * @return List
	 */
	List<UserTempLog> getModifyUserTemp();

	/**
	 * 方法说明：updateUserTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	int updateUserTemp(UserTempLog newUser);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param i
	 *            i
	 * @return UserPageResult
	 */
	UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, Long i);

	/**
	 * 方法说明：searchUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	UserPageResult searchUserTempListByStatus(Integer page, Integer pagesize, Condition condition,
			Long l);

	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return UserTempLog
	 */
	UserTempLog getUserTempByUuid(String deptUuid);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	List<UserTempLog> getUserTempListByStatus(Long l);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	List<UserTempLog> getUserTempListByStatus(String type);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, String type);

	/**
	 * 方法说明：searchUserTempList
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
			String type);

}
