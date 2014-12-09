/**
 * 
 */
package com.wt.uum2.service;

import java.util.List;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
import com.wt.uum2.domain.TaskList;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface TaskListService
{

	/**
	 * 保存待办列表
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	public int saveTaskList(TaskList taskList);

	/**
	 * 删除待办列表
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	public int deleteTaskList(TaskList taskList);

	/**
	 * 更新待办列表
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	public int updateTaskList(TaskList taskList);

	/**
	 * 获得待办列表
	 * 
	 * @return List
	 */
	public List<TaskList> getTaskLists();

	/**
	 * 获得待办分页列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getTaskList(int page, int pagesize);

	/**
	 * 通过UUid获得待办项
	 * 
	 * @param uuid
	 *            uuid
	 * @return TaskList
	 */
	public TaskList getTaskListByUuid(String uuid);

	// /**
	// * 判断登录用户是否有权限访问该节点
	// * @param uuid
	// * @return
	// */
	// public boolean isAuthorForTaskList(String uuid);

	/**
	 * 取得所有的审计资源
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getAuditList(Integer page, Integer pagesize);

	/**
	 * 搜索符合条件的日志
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @param searchvalue
	 *            searchvalue
	 * @return UserPageResult
	 */
	public UserPageResult searchAuditList(Integer page, Integer pagesize, String searchcontent,
			String searchvalue);

	/**
	 * 
	 * 方法说明：取得事件列表
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult<Event> getEventList(Integer page, Integer pagesize);

	/**
	 * 
	 * 方法说明：取得事件对象
	 * 
	 * @param uuid
	 *            事件对象主键
	 * @return Event
	 */
	public Event getEvent(String uuid);

	/**
	 * 
	 * 方法说明：取得事件详细列表
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<EventParam> getEventParamByEvent(Event event);

	/**
	 * 
	 * 方法说明：取得某资源的日志
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getEventListByResouce(String uuid, Integer page, Integer pagesize);

}
