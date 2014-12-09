package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentPath;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	noah
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DepartmentPathDao extends BaseDao<DepartmentPath>
{

	/**
	 * 方法说明：readElderUUIDsOrderByLevel
	 * 
	 * @param juniorUUID
	 *            juniorUUID
	 * @return List
	 */
	public List<String> readElderUUIDsOrderByLevel(String juniorUUID);

	/**
	 * 方法说明：readJuniorUUIDs
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @return List
	 */
	public List<String> readJuniorUUIDs(String elderUUID);

	/**
	 * 方法说明：moveDeptDeleteDeptPath
	 * 
	 * @param sourceDeptId
	 *            sourceDeptId
	 * @param targetDeptId
	 *            targetDeptId
	 */
	public void moveDeptDeleteDeptPath(String sourceDeptId, String targetDeptId);

	/**
	 * 用长机构UUID查询子机构
	 * 
	 * @param elderUUID
	 *            长机构UUID
	 * @param levelFrom
	 *            查询level的起始深度
	 * @param levelTo
	 *            查询level的终止深度
	 * @return 幼机构列表
	 */
	public List<Department> readJuniorDeptsWithLevel(String elderUUID, int levelFrom, int levelTo);

	/**
	 * 方法说明：readAllElderDepts
	 * 
	 * @param juniorDept
	 *            juniorDept
	 * @return List
	 */
	public List<Department> readAllElderDepts(Department juniorDept);

	/**
	 * 方法说明：删除部门相关的departmentpath信息,包括elder和junior
	 * 
	 * @param uuid
	 *            uuid
	 */
	public void deleteDepartmentPathByUUID(String uuid);

	/**
	 * 方法说明：用机构uuid和机构深度取得对应机构实体
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @param i
	 *            i
	 * @return Department
	 */
	public Department readElderDeptByLevel(String deptUUID, int i);

}
