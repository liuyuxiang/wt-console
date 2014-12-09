package com.wt.uum2.dao;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.DepartmentTemp;
import com.wt.uum2.userlist.DeptTempType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DepartmentTempDao extends BaseDao<DepartmentTemp>
{

	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTemp
	 */
	DepartmentTemp getDepartmentTempByUuid(String deptUuid);

	/**
	 * 
	 * 方法说明：取得部门列表,如果type为空则返回所有列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	UserPageResult getDepartmentTempList(Integer page, Integer pagesize, DeptTempType type);

	/**
	 * 方法说明：搜索符合条件的部门列表,如果type为空则返回所有列表
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
			DeptTempType type);

}
