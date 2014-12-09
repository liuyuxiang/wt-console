package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;

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
public interface DepartmentDao extends BaseDao<Department>
{

	/**
	 * 方法说明：通过部门名称获得部门对象
	 * 
	 * @param deptName
	 *            deptName
	 * @return Department
	 */
	public Department readByName(String deptName);

	/**
	 * 方法说明：通过父部门的唯一标识获得子部门集合
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @return List
	 */
	public List<Department> readChildren(String parentUUID);

	/**
	 * 方法说明：通过父部门的唯一标识和登录用户获得带权限的子部门集合
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	public List<Department> readChildren(String parentUUID, User loginUser);

	/**
	 * 方法说明：获得根部门
	 * 
	 * @return Department
	 */
	public Department readRoot();

	/**
	 * 方法说明：得到所有部门的集合
	 * 
	 * @return List
	 */
	public List<Department> getAllDepartments();

	/**
	 * 方法说明：跟新部门的排序号
	 * 
	 * @param key
	 *            key
	 */
	@Deprecated
	public void updateDeptOrder(String key);

	/**
	 * 方法说明：通过部门名称模糊搜索部门
	 * 
	 * @param key
	 *            key
	 * @return List
	 */
	@Deprecated
	public List<Department> searchDepartmentsByKey(String key);

	/**
	 * 方法说明：通过部门的唯一标识获得唯一部门
	 * 
	 * @param uuid
	 *            uuid
	 * @return Department
	 */
	public Department getDeapartmentByUUID(String uuid);

	/**
	 * 方法说明：通过部门的唯一标识删除部门
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	@Deprecated
	public int deleteDepartmentByUUID(String uuid);

	/**
	 * 方法说明：通过部门的唯一编码获得部门对象
	 * 
	 * @param deptCode
	 *            deptCode
	 * @return Department
	 */
	public Department getDepartmentByDeptCode(String deptCode);

	/**
	 * 方法说明：通过部门的唯一编码获得部门对象
	 * 
	 * @param code
	 *            code
	 * @return Department
	 */
	public Department getDepartmentByCode(String code);

	/**
	 * 方法说明：通过父部门获得该部门下的所有部门集合（包括自己）
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<Department> getDepartmentsByParent(Department department);

	/**
	 * 方法说明：获得组所管理的分页部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentByAdminGroup(int page, int pagesize, Group group);

	/**
	 * 方法说明：通过管理组集合查询部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param admGroups
	 *            admGroups
	 * @param isInSuoerGroup
	 *            isInSuoerGroup
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentsByAdmGroups(int page, int pagesize, Condition condition,
			List<Group> admGroups, boolean isInSuoerGroup);

	/**
	 * 方法说明：通过条件查询分页部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentsByCondition(int page, int pagesize, Condition condition);

	/**
	 * 方法说明：通过部门名称和单位编码获得部门
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return Department
	 */
	public Department getDepartmentByNameAndOrgCode(String name, String orgCode);

	/**
	 * 方法说明：获得组集合下需要审核的分页部门
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param groups
	 *            groups
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedDepartmentsManagedUnderGroups(int page, int pagesize,
			List<Group> groups);

	/**
	 * 方法说明：计算总部门数量
	 * 
	 * @return int
	 */
	public int countDepartment();

	/**
	 * 方法说明：查询部门的rtxCode是否存在
	 * 
	 * @param rtxCode
	 *            rtxCode
	 * @return boolean
	 */
	public boolean existDepartmentRTXCode(long rtxCode);

	/**
	 * 方法说明：为RTX计算部门的数量
	 * 
	 * @return int
	 */
	public int countDepartmentForRtx();

	/**
	 * 方法说明：修改部门path
	 * 
	 * @param newPath
	 *            newPath
	 * @param oldPath
	 *            oldPath
	 */
	public void updateDeptPath(String newPath, String oldPath);

	/**
	 * 方法说明：取得无组织机构人员的虚拟部门
	 * 
	 * @return Department
	 */
	public Department getNoDepartment();

	/**
	 * 方法说明：取得部门下不正常的子部门
	 * 
	 * @param uuid
	 *            uuid
	 * @return List
	 */
	public List<Department> getAbnormalDepartmentsByParent(String uuid);

	/**
	 * 方法说明：带权限取部门列表
	 * 
	 * @param uuid
	 *            uuid
	 * @param loginUser
	 *            loginUser
	 * @param flag
	 *            flag
	 * @return List
	 */
	public List<Department> readChildren(String uuid, User loginUser, String flag);

	/**
	 * 方法说明：获取用户所在部门
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getDepartmentsByUser(User user);

	/**
	 * 方法说明：获用户主属部门
	 * 
	 * @param user
	 *            user
	 * @return Department
	 */
	public Department getPrimaryDepartmentByUser(User user);

	/**
	 * 方法说明：获取子部门分页信息
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param parentDept
	 *            parentDept
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentsByParent(int page, int pagesize, Department parentDept);

	/**
	 * 方法说明：根据部门名称模糊搜索部门
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, String orgCode);

	/**
	 * 方法说明：searchDepartmentsByNameCount
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return long
	 */
	public long searchDepartmentsByNameCount(String name, String orgCode);

	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @param start
	 *            start
	 * @param max
	 *            max
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, String orgCode, int start, int max);

	/**
	 * 方法说明：根据部门名称模糊搜索部门
	 * 
	 * @param code
	 *            code
	 * @param orgCode
	 *            orgCode
	 * @return List
	 */
	public List<Department> searchDepartmentsByCode(String code, String orgCode);

	/**
	 * 
	 * 方法说明：取得部门的管理组
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<Group> getGroupsByManagedDepartment(Department resource);

	/**
	 * 方法说明：移除部门等级字段
	 * 
	 * @param currentDept
	 *            currentDept
	 * @param toDept
	 *            toDept
	 */
	public void moveDeptUpdateLevel(Department currentDept, Department toDept);

}