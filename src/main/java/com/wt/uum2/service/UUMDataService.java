/**
 * 
 */
package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.ResourceTempDetails;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTempLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UUMDataService
{

	/**
	 * 方法说明：saveUserTemp
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return int
	 */
	public int saveUserTemp(UserTempLog userTemp);

	/**
	 * 方法说明：saveDepartmentTemp
	 * 
	 * @param departmentTemp
	 *            departmentTemp
	 * @return int
	 */
	public int saveDepartmentTemp(DepartmentTempLog departmentTemp);

	/**
	 * 方法说明：getModifyUserTemp
	 * 
	 * @return List
	 */
	public List<UserTempLog> getModifyUserTemp();

	/**
	 * 方法说明：updateUserTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	public int updateUserTemp(UserTempLog newUser);

	/**
	 * 方法说明：getModifyDeptTemp
	 * 
	 * @return List
	 */
	public List<DepartmentTempLog> getModifyDeptTemp();

	/**
	 * 方法说明：updateDeptTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	public int updateDeptTemp(DepartmentTempLog newUser);

	/**
	 * 方法说明：getDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize, User user);

	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	public UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, Long l);

	/**
	 * 方法说明：searchDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition, User user);

	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTempLog
	 */
	public DepartmentTempLog getDepartmentTempByUuid(String deptUuid);

	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	public List<DepartmentTempLog> getAllDepartmentTempListByStatus(Long l);

	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	public List<DepartmentTempLog> getAllDepartmentTempListByStatus(String type);

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
	public UserPageResult searchUserTempListByStatus(Integer page, Integer pagesize,
			Condition condition, Long l);

	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return UserTempLog
	 */
	public UserTempLog getUserTempByUuid(String deptUuid);

	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	public List<UserTempLog> getAllUserTempListByStatus(Long l);

	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	public List<UserTempLog> getAllUserTempListByStatus(String type);

	/**
	 * 方法说明：获取详细相关修改信息
	 * 
	 * @param userTempLog
	 *            userTempLog
	 * @return List
	 */
	public List<ResourceTempDetails> getResourceTempDetails(UserTempLog userTempLog);

	/**
	 * 方法说明：获取详细相关修改信息
	 * 
	 * @param departmentTempLog
	 *            departmentTempLog
	 * @return List
	 */
	public List<ResourceTempDetails> getResourceTempDetails(DepartmentTempLog departmentTempLog);

	/**
	 * 方法说明：更新详细相关修改信息
	 * 
	 * @param userTempLog
	 *            userTempLog
	 */
	public void updateResourceTempDetails(UserTempLog userTempLog);

	/**
	 * 方法说明：更新详细相关修改信息
	 * 
	 * @param departmentTempLog
	 *            departmentTempLog
	 */
	public void updateResourceTempDetails(DepartmentTempLog departmentTempLog);

	/**
	 * 方法说明：取得部门日志列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempLogList(Integer page, Integer pagesize, String type);

	/**
	 * 方法说明：搜索部门日志列表
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
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition, String type);

	/**
	 * 方法说明：取得用户日志列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getUserTempList(Integer page, Integer pagesize, String type);

	/**
	 * 方法说明：搜索用户 日志列表
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
	public UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition,
			String type);

}
