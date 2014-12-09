package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.domain.Group;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface GroupPathService
{

	/**
	 * 
	 * 方法说明：移动组的上下级关系
	 * 
	 * @param currentGroup
	 *            当前要操作组
	 * @param toGroup
	 *            目标组(父级组)
	 */
	public void moveGroupPath(Group currentGroup, Group toGroup);

	/**
	 * 
	 * 方法说明：更新所有的组的path
	 * 
	 */
	public void updateAllGroupPath();

	/**
	 * 方法说明：更新组的path
	 * 
	 * @param currentGroup
	 *            currentGroup
	 */
	public void updateGroupPath(Group currentGroup);

	/**
	 * 
	 * 方法说明：创建组的path
	 * 
	 * @param currentGroup
	 *            currentGroup
	 */
	public void createGroupPath(Group currentGroup);

	/**
	 * 
	 * 方法说明：取得子组
	 * 
	 * @param elderUUID
	 *            elderUUID
	 * @return List
	 */
	public List<Group> getJuniorGroups(String elderUUID);

}
