package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.DepartmentTemp;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.UserTemp;
import com.wt.uum2.userlist.DeptTempType;
import com.wt.uum2.userlist.UserTempType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-5-12
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UUMTempDataService
{

	/**
	 * 方法说明：getModifyUserTemp
	 * 
	 * @return List
	 */
	public List<UserTemp> getModifyUserTemp();

	/**
	 * 方法说明：getModifyDeptTemp
	 * 
	 * @return List
	 */
	public List<DepartmentTemp> getModifyDeptTemp();

	/**
	 * 
	 * 方法说明：根据类型取得中间表部门列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize, DeptTempType type);

	/**
	 * 
	 * 方法说明：根据类型取得中间表用户列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getUserTempList(Integer page, Integer pagesize, UserTempType type);

	/**
	 * 方法说明：searchDepartmentTempList
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
			Condition condition, DeptTempType type);

	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTemp
	 */
	public DepartmentTemp getDepartmentTempByUuid(String deptUuid);

	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param l
	 *            L
	 * @return List
	 */
	public List<DepartmentTemp> getAllDepartmentTempListByStatus(Long l);

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
	public UserPageResult<UserTemp> searchUserTempList(Integer page, Integer pagesize,
			Condition condition, UserTempType type);

	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return UserTemp
	 */
	public UserTemp getUserTempByUuid(String deptUuid);

	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param l
	 *            L
	 * @return List
	 */
	public List<UserTemp> getAllUserTempListByStatus(Long l);

	/**
	 * 方法说明：添加资源映射
	 * 
	 * @param resource
	 *            resource
	 * @param userCode
	 *            userCode
	 */
	public void addResourceMapping(Resource resource, String userCode);

	/**
	 * 方法说明：removeResourceMapping
	 * 
	 * @param resource
	 *            resource
	 * @param string
	 *            string
	 */
	public void removeResourceMapping(Resource resource, String string);

}
