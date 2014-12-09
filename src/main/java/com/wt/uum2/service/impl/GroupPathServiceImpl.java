package com.wt.uum2.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.dao.GroupDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.service.GroupPathService;

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
@Transactional
public class GroupPathServiceImpl implements GroupPathService
{

	protected final Log logger = LogFactory.getLog(getClass());

	// private GroupPathDao groupPathDao;

	private GroupDao groupDao;

	/**
	 * @param groupPathDao
	 *            the groupPathDao to set
	 */
	// public void setGroupPathDao(GroupPathDao groupPathDao) {
	// this.groupPathDao = groupPathDao;
	// }

	/**
	 * @param groupDao
	 *            the groupDao to set
	 */
	public void setGroupDao(GroupDao groupDao)
	{
		this.groupDao = groupDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupPathService#moveGroupPath(com.wt.uum2.domain.Group, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：moveGroupPath
	 * 
	 * @param currentGroup
	 *            currentGroup
	 * @param toGroup
	 *            toGroup
	 */
	public void moveGroupPath(Group currentGroup, Group toGroup)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupPathService#updateAllGroupPath()
	 */
	/**
	 * 方法说明：updateAllGroupPath
	 * 
	 */
	public void updateAllGroupPath()
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupPathService#updateGroupPath(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：updateGroupPath
	 * 
	 * @param currentGroup
	 *            currentGroup
	 */
	public void updateGroupPath(Group currentGroup)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupPathService#createGroupPath(com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：createGroupPath
	 * 
	 * @param currentGroup
	 *            currentGroup
	 */
	public void createGroupPath(Group currentGroup)
	{
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.GroupPathService#getJuniorGroups(java.lang.String)
	 */
	/**
	 * 方法说明：getJuniorGroups
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @return List
	 */
	public List<Group> getJuniorGroups(String elderUUID)
	{
		// TODO Auto-generated method stub
		return null;
	}

	// public void moveGroupPath(Group currentGroup, Group toGroup) {
	// groupDao.moveGroupUpdateLevel(currentGroup, toGroup);
	//
	// List<String> currentIdPath = groupPathDao
	// .readElderUUIDsOrderByLevel(currentGroup.getUuid());
	//
	// List<String> toIdPath = groupPathDao
	// .readElderUUIDsOrderByLevel(toGroup.getUuid());
	//
	// int max = Math.max(currentIdPath.size(), toIdPath.size());
	//
	// String latestCurrentId = currentIdPath.get(currentIdPath.size() - 1);
	// String latestToid = toIdPath.get(toIdPath.size() - 1);
	//
	// boolean isCurrentIdEnd = false;
	// boolean isToIdEnd = false;
	//
	// for (int c = 0; c < max; c++) {
	//
	// String currentId = null;
	// String toId = null;
	//
	// if (!isCurrentIdEnd) {
	// currentId = currentIdPath.get(c);
	// if (c == currentIdPath.size() - 1) {
	// isCurrentIdEnd = true;
	// }
	// }
	//
	// if (!isToIdEnd) {
	// toId = toIdPath.get(c);
	// if (c == toIdPath.size() - 1) {
	// isToIdEnd = true;
	// }
	// }
	//
	// if (isCurrentIdEnd && isToIdEnd) {
	// addGroupFamiliesToGroupPath(latestCurrentId, latestToid);
	// // logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
	// // latestToid+"{path");
	// } else if (!isCurrentIdEnd && isToIdEnd) {
	// groupPathDao.moveGroupDeleteGroupPath(latestCurrentId,
	// currentId);
	// // logger.debug("delete " +latestCurrentId+
	// // "&［childrens］ from "+currentId+"{path");
	// } else if (isCurrentIdEnd && !isToIdEnd) {
	// addGroupFamiliesToGroupPath(latestCurrentId, toId);
	// // logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
	// // toId+"{path");
	// } else if (!isCurrentIdEnd && !isToIdEnd) {
	// if (currentId.equals(toId)) {
	// continue;
	// } else {
	// addGroupFamiliesToGroupPath(latestCurrentId, toId);
	// groupPathDao.moveGroupDeleteGroupPath(latestCurrentId,
	// currentId);
	// // logger.debug("add " +latestCurrentId+ "&［childrens］ to "+
	// // toId+"{path");
	// // logger.debug("delete " +latestCurrentId+
	// // "&［childrens］ from "+currentId+"{path");
	// }
	// }
	//
	// }
	//
	// }
	//
	// private void addGroupFamiliesToGroupPath(String sourceGroupId,
	// String targetGroupId) {
	// List<String> deptIds = groupPathDao.readJuniorUUIDs(sourceGroupId);
	// for (String deptId : deptIds) {
	// GroupPath newGroupPath = new GroupPath();
	// newGroupPath.setElderUUID(targetGroupId);
	// newGroupPath.setJuniorUUID(deptId);
	// groupPathDao.save(newGroupPath);
	// }
	// }
	//
	// public void updateAllGroupPath() {
	//
	// logger.info("updateGroupLevelPathAndGroupPath:start!");
	//
	// groupPathDao.deleteAll("GroupPath");
	//
	// Group rootGroup = groupDao.readRoot();
	//
	// Stack<Group> updateStack = new Stack<Group>();
	// updateStack.add(rootGroup);
	//
	// long count = 0;
	//
	// while (!updateStack.isEmpty()) {
	//
	// logger.info("stack size:" + updateStack.size());
	//
	// Group currentGroup = updateStack.pop();
	// count++;
	//
	// logger.info("update:[" + count + "][" + currentGroup.getUuid() + "]"
	// + currentGroup.getName());
	//
	// updateLevelGroupPath(currentGroup);
	//
	// List<Group> childs = groupDao.readChildren(currentGroup
	// .getUuid());
	//
	// logger.info("stack add:" + childs.size());
	//
	// updateStack.addAll(childs);
	// }
	//
	// logger.info("updateGroupLevelPathAndGroupPath:end!");
	// }
	//
	// public void updateGroupPath(Group currentGroup) {
	// Group parentGroup = currentGroup.getParent();
	// if (parentGroup != null) {
	// currentGroup.setLevel(parentGroup.getLevel() + 1);
	//
	// createGroupPath(currentGroup);
	//
	// } else {
	// currentGroup.setPath(currentGroup.getUuid());
	// currentGroup.setLevel(0);
	// GroupPath deptPath = new GroupPath();
	// deptPath.setElderUUID(currentGroup.getUuid());
	// deptPath.setJuniorUUID(currentGroup.getUuid());
	// groupPathDao.save(deptPath);
	//
	// }
	// groupDao.update(currentGroup);
	// }
	//
	// public void createGroupPath(Group currentGroup) {
	// GroupPath groupPath1 = new GroupPath();
	// groupPath1.setElderUUID(currentGroup.getUuid());
	// groupPath1.setJuniorUUID(currentGroup.getUuid());
	// groupPathDao.save(groupPath1);
	//
	// Group currentD = currentGroup;
	//
	// while (currentD.getParentUUID() != null) {
	//
	// Group currentP = currentD.getParent();
	//
	// GroupPath groupPath2 = new GroupPath();
	// groupPath2.setElderUUID(currentP.getUuid());
	// groupPath2.setJuniorUUID(currentGroup.getUuid());
	//
	// groupPathDao.save(groupPath2);
	//
	// currentD = currentP;
	// }
	// }
	//
	// public List<Group> getJuniorGroups(String elderUUID) {
	// return groupPathDao.readJuniorGroupsWithLevel(elderUUID, levelFrom, levelTo);
	// }

}
