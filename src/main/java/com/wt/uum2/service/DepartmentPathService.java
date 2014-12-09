package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.domain.Department;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-08-30
 * 作者:	Xiao Guoying
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DepartmentPathService
{

	/**
	 * 方法说明：moveDepartmentPath
	 * 
	 * @param currentDept
	 *            currentDept
	 * @param toDept
	 *            toDept
	 */
	public void moveDepartmentPath(Department currentDept, Department toDept);

	/**
	 * 方法说明：updateAllLevelAndDeptPath
	 * 
	 */
	public void updateAllLevelAndDeptPath();

	/**
	 * 方法说明：updateLevelDeptPath
	 * 
	 * @param currentDept
	 *            currentDept
	 */
	public void updateLevelDeptPath(Department currentDept);

	/**
	 * 方法说明：createDeptPath
	 * 
	 * @param currentDept
	 *            currentDept
	 */
	public void createDeptPath(Department currentDept);

	/**
	 * 方法说明：getJuniorDeptsWithLevel
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @param levelFrom
	 *            levelFrom
	 * @param levelTo
	 *            levelTo
	 * @return List
	 */
	public List<Department> getJuniorDeptsWithLevel(String elderUUID, int levelFrom, int levelTo);

	/**
	 * 方法说明：getAllElderDepts
	 * 
	 * @param juniorDept
	 *            juniorDept
	 * @return List
	 */
	public List<Department> getAllElderDepts(Department juniorDept);
}
