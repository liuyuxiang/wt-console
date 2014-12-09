package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.DepartmentTempLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-08-22
 * 作者:	Li Chenyue
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DepartmentTempLogDao extends BaseDao<DepartmentTempLog>
{

	/**
	 * 方法说明：getModifyDeptTemp
	 * 
	 * @return List
	 */
	List<DepartmentTempLog> getModifyDeptTemp();

	/**
	 * 方法说明：UserPageResult
	 * 
	 * @param newDept
	 *            newDept
	 * @return int
	 */
	int updateDeptTemp(DepartmentTempLog newDept);

	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	UserPageResult getDepartmentTempListByStatus(Integer page, Integer pagesize, Long l);

	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	List<DepartmentTempLog> getDepartmentTempListByStatus(String type);

	/**
	 * 方法说明：searchDepartmentTempListByStatus
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
	UserPageResult searchDepartmentTempListByStatus(Integer page, Integer pagesize,
			Condition condition, Long l);

	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTempLog
	 */
	DepartmentTempLog getDepartmentTempByUuid(String deptUuid);

	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	List<DepartmentTempLog> getDepartmentTempListByStatus(Long l);

	/**
	 * 方法说明：getDepartmentTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	UserPageResult getDepartmentTempListByStatus(Integer page, Integer pagesize, String type);

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
	UserPageResult searchDepartmentTempList(Integer page, Integer pagesize, Condition condition,
			String type);

}
