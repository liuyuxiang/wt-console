package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.Query;

import com.wt.uum2.dao.DepartmentPathDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentPath;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	noah
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DepartmentPathDaoImpl extends BaseDaoSupport<DepartmentPath> implements
		DepartmentPathDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#readElderUUIDsOrderByLevel(java.lang.String)
	 */
	/**
	 * 方法说明：readElderUUIDsOrderByLevel
	 * 
	 * @param juniorUUID
	 *            juniorUUID
	 * @return List
	 */
	public List<String> readElderUUIDsOrderByLevel(String juniorUUID)
	{
		return getSession()
				.createQuery(
						"select p.elderUUID from DepartmentPath p,Department d where d.uuid=p.elderUUID and p.juniorUUID=:juniorUUID order by d.level")
				.setString("juniorUUID", juniorUUID).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#readJuniorUUIDs(java.lang.String)
	 */
	/**
	 * 方法说明：readJuniorUUIDs
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @return List
	 */
	public List<String> readJuniorUUIDs(String elderUUID)
	{
		return getSession()
				.createQuery(
						"select p.juniorUUID from DepartmentPath p where p.elderUUID=:elderUUID")
				.setString("elderUUID", elderUUID).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#moveDeptDeleteDeptPath(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：moveDeptDeleteDeptPath
	 * 
	 * @param sourceDeptId
	 *            sourceDeptId
	 * @param targetDeptId
	 *            targetDeptId
	 */
	public void moveDeptDeleteDeptPath(String sourceDeptId, String targetDeptId)
	{
		getSession()
				.createQuery(
						"delete from DepartmentPath tp where tp.juniorUUID in (select p.juniorUUID from DepartmentPath p where p.elderUUID=:sourceDeptId) and tp.elderUUID=:targetDeptId")
				.setString("sourceDeptId", sourceDeptId).setString("targetDeptId", targetDeptId)
				.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#readJuniorDeptsWithLevel(java.lang.String, int, int)
	 */
	/**
	 * 方法说明：readJuniorDeptsWithLevel
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @param levelFrom
	 *            levelFrom
	 * @param levelTo
	 *            levelTo
	 * @return List
	 */
	public List<Department> readJuniorDeptsWithLevel(String elderUUID, int levelFrom, int levelTo)
	{
		String sql = "select d from DepartmentPath p,Department d where p.juniorUUID=d.uuid and p.elderUUID=:elderUUID ";

		boolean isLevelFrom = false;
		if (levelFrom > 0) {
			sql += " and d.level>=:levelFrom ";
			isLevelFrom = true;
		}

		boolean isLevelTo = false;
		if (levelTo > 0) {
			sql += " and d.level<=:levelTo ";
			isLevelTo = true;
		}

		Query query = getSession().createQuery(sql).setString("elderUUID", elderUUID);
		if (isLevelFrom) {
			query = query.setInteger("levelFrom", levelFrom);
		}
		if (isLevelTo) {
			query = query.setInteger("levelTo", levelTo);
		}

		return query.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#deleteDepartmentPathByUUID(java.lang.String)
	 */
	/**
	 * 方法说明：deleteDepartmentPathByUUID
	 * 
	 * @param uuid
	 *            uuid
	 */
	public void deleteDepartmentPathByUUID(String uuid)
	{
		String sql = "delete from DepartmentPath tp where tp.juniorUUID =:uuid or tp.elderUUID=:uuid";
		getSession().createQuery(sql).setString("uuid", uuid).executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#readElderDeptByLevel(java.lang.String, int)
	 */
	/**
	 * 方法说明：readElderDeptByLevel
	 * 
	 * @param deptUUID
	 *            deptUUID
	 * @param level
	 *            level
	 * @return Department
	 */
	public Department readElderDeptByLevel(String deptUUID, int level)
	{
		String sql = "select d from DepartmentPath p,Department d where p.juniorUUID=:deptuuid and d.uuid=p.elderUUID and d.level=:level ";

		Query query = getSession().createQuery(sql).setString("deptuuid", deptUUID);
		query = query.setInteger("level", level);

		return (Department) query.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentPathDao#readAllElderDepts(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：readAllElderDepts
	 * 
	 * @param juniorDept
	 *            juniorDept
	 * @return List
	 */
	public List<Department> readAllElderDepts(Department juniorDept)
	{
		return getSession()
				.createQuery(
						"select d from DepartmentPath p,Department d where d=p.elder and p.junior=:juniorDept order by d.level")
				.setEntity("juniorDept", juniorDept).list();
	}

}
