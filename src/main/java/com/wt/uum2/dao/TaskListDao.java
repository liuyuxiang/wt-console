package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.TaskList;

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
public interface TaskListDao extends BaseDao<TaskList>
{

	/**
	 * 方法说明：getTaskLists
	 * 
	 * @return List
	 */
	public List<TaskList> getTaskLists();

	/**
	 * 方法说明：getTaskList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getTaskList(int page, int pagesize);

	/**
	 * 方法说明：getTaskListByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return TaskList
	 */
	public TaskList getTaskListByUuid(String uuid);

	/**
	 * 方法说明：getAuditList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getAuditList(Integer page, Integer pagesize);
}
