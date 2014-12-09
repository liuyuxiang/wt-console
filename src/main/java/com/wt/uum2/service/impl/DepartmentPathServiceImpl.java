package com.wt.uum2.service.impl;

import java.util.List;
import java.util.Stack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.dao.DepartmentDao;
import com.wt.uum2.dao.DepartmentPathDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentPath;
import com.wt.uum2.service.DepartmentPathService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class DepartmentPathServiceImpl implements DepartmentPathService
{

	/**
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 
	 */
	private DepartmentPathDao departmentPathDao;

	/**
	 * 
	 */
	private DepartmentDao departmentDao;

	/**
	 * @param departmentPathDao
	 *            the departmentPathDao to set
	 */
	public void setDepartmentPathDao(DepartmentPathDao departmentPathDao)
	{
		this.departmentPathDao = departmentPathDao;
	}

	/**
	 * @param departmentDao
	 *            the departmentDao to set
	 */
	public void setDepartmentDao(DepartmentDao departmentDao)
	{
		this.departmentDao = departmentDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#moveDepartmentPath(com.wt.uum2.domain.Department, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：moveDepartmentPath
	 * 
	 * @param currentDept
	 *            currentDept
	 * @param toDept
	 *            toDept
	 */
	@Transactional(readOnly = false)
	public void moveDepartmentPath(Department currentDept, Department toDept)
	{
		departmentDao.moveDeptUpdateLevel(currentDept, toDept);

		List<String> currentIdPath = departmentPathDao.readElderUUIDsOrderByLevel(currentDept
				.getUuid());

		List<String> toIdPath = departmentPathDao.readElderUUIDsOrderByLevel(toDept.getUuid());

		int max = Math.max(currentIdPath.size(), toIdPath.size());

		String latestCurrentId = currentIdPath.get(currentIdPath.size() - 1);
		String latestToid = toIdPath.get(toIdPath.size() - 1);

		boolean isCurrentIdEnd = false;
		boolean isToIdEnd = false;

		for (int c = 0; c < max; c++) {

			String currentId = null;
			String toId = null;

			if (!isCurrentIdEnd) {
				currentId = currentIdPath.get(c);
				if (c == currentIdPath.size() - 1) {
					isCurrentIdEnd = true;
				}
			}

			if (!isToIdEnd) {
				toId = toIdPath.get(c);
				if (c == toIdPath.size() - 1) {
					isToIdEnd = true;
				}
			}

			if (isCurrentIdEnd && isToIdEnd) {
				addDeptFamiliesToDeptPath(latestCurrentId, latestToid);
				// logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
				// latestToid+"{path");
			} else if (!isCurrentIdEnd && isToIdEnd) {
				departmentPathDao.moveDeptDeleteDeptPath(latestCurrentId, currentId);
				// logger.debug("delete " +latestCurrentId+
				// "&［childrens］ from "+currentId+"{path");
			} else if (isCurrentIdEnd && !isToIdEnd) {
				addDeptFamiliesToDeptPath(latestCurrentId, toId);
				// logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
				// toId+"{path");
			} else if (!isCurrentIdEnd && !isToIdEnd) {
				if (currentId.equals(toId)) {
					continue;
				} else {
					addDeptFamiliesToDeptPath(latestCurrentId, toId);
					departmentPathDao.moveDeptDeleteDeptPath(latestCurrentId, currentId);
					// logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
					// toId+"{path");
					// logger.debug("delete " +latestCurrentId+
					// "&［childrens］ from "+currentId+"{path");
				}
			}

		}

	}

	/**
	 * 方法说明：addDeptFamiliesToDeptPath
	 * 
	 * @param sourceDeptId
	 *            sourceDeptId
	 * @param targetDeptId
	 *            targetDeptId
	 */
	@Transactional(readOnly = false)
	private void addDeptFamiliesToDeptPath(String sourceDeptId, String targetDeptId)
	{
		List<String> deptIds = departmentPathDao.readJuniorUUIDs(sourceDeptId);
		for (String deptId : deptIds) {
			DepartmentPath newDepartmentPath = new DepartmentPath();
			newDepartmentPath.setElderUUID(targetDeptId);
			newDepartmentPath.setJuniorUUID(deptId);
			departmentPathDao.save(newDepartmentPath);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#updateAllLevelAndDeptPath()
	 */
	/**
	 * 方法说明：updateAllLevelAndDeptPath
	 * 
	 */
	@Transactional(readOnly = false)
	public void updateAllLevelAndDeptPath()
	{

		logger.info("updateDeptLevelPathAndDeptPath:start!");

		departmentPathDao.deleteAll("DepartmentPath");

		Department rootDept = departmentDao.readRoot();

		Stack<Department> updateStack = new Stack<Department>();
		updateStack.add(rootDept);

		long count = 0;

		while (!updateStack.isEmpty()) {

			logger.info("stack size:" + updateStack.size());

			Department currentDept = updateStack.pop();
			count++;

			logger.info("update:[" + count + "][" + currentDept.getUuid() + "]"
					+ currentDept.getName());

			updateLevelDeptPath(currentDept);

			List<Department> childs = departmentDao.readChildren(currentDept.getUuid());

			logger.info("stack add:" + childs.size());

			updateStack.addAll(childs);
		}

		logger.info("updateDeptLevelPathAndDeptPath:end!");
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#updateLevelDeptPath(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：updateLevelDeptPath
	 * 
	 * @param currentDept
	 *            currentDept
	 */
	@Transactional(readOnly = false)
	public void updateLevelDeptPath(Department currentDept)
	{
		Department parentDept = currentDept.getParent();
		if (parentDept != null) {
			currentDept.setLevel(parentDept.getLevel() + 1);

			createDeptPath(currentDept);
			currentDept.setPath(parentDept.getPath() + "→" + currentDept.getName());
		} else {
			currentDept.setPath(currentDept.getName());
			currentDept.setLevel(0);
			DepartmentPath deptPath = new DepartmentPath();
			deptPath.setElderUUID(currentDept.getUuid());
			deptPath.setJuniorUUID(currentDept.getUuid());
			departmentPathDao.save(deptPath);
		}
		departmentDao.update(currentDept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#createDeptPath(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：createDeptPath
	 * 
	 * @param currentDept
	 *            currentDept
	 */
	@Transactional(readOnly = false)
	public void createDeptPath(Department currentDept)
	{
		DepartmentPath departmentPath1 = new DepartmentPath();
		departmentPath1.setElderUUID(currentDept.getUuid());
		departmentPath1.setJuniorUUID(currentDept.getUuid());
		departmentPathDao.save(departmentPath1);

		Department currentD = currentDept;

		while (currentD.getParentUUID() != null) {

			Department currentP = departmentDao.read(currentD.getParentUUID());

			DepartmentPath departmentPath2 = new DepartmentPath();
			departmentPath2.setElderUUID(currentP.getUuid());
			departmentPath2.setJuniorUUID(currentDept.getUuid());

			departmentPathDao.save(departmentPath2);

			currentD = currentP;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#getJuniorDeptsWithLevel(java.lang.String, int, int)
	 */
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
	@Transactional(readOnly = true)
	public List<Department> getJuniorDeptsWithLevel(String elderUUID, int levelFrom, int levelTo)
	{
		return departmentPathDao.readJuniorDeptsWithLevel(elderUUID, levelFrom, levelTo);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DepartmentPathService#getAllElderDepts(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getAllElderDepts
	 * 
	 * @param juniorDept
	 *            juniorDept
	 * @return List
	 */
	@Transactional(readOnly = false)
	public List<Department> getAllElderDepts(Department juniorDept)
	{
		return departmentPathDao.readAllElderDepts(juniorDept);
	}

}
