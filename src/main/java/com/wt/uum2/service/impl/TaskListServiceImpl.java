package com.wt.uum2.service.impl;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.EventDao;
import com.wt.uum2.dao.ResourceLogDao;
import com.wt.uum2.dao.TaskListDao;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
import com.wt.uum2.domain.TaskList;
import com.wt.uum2.service.TaskListService;

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
@Transactional
public class TaskListServiceImpl implements TaskListService
{

	/**
	 * 
	 */
	private TaskListDao taskListDao;

	/**
	 * 
	 */
	private ResourceLogDao resourceLogDao;

	/**
	 * 
	 */
	private EventDao eventDao;

	/**
	 * @param eventDao
	 *            the eventDao to set
	 */
	public void setEventDao(EventDao eventDao)
	{
		this.eventDao = eventDao;
	}

	public void setResourceLogDao(ResourceLogDao resourceLogDao)
	{
		this.resourceLogDao = resourceLogDao;
	}

	public void setTaskListDao(TaskListDao taskListDao)
	{
		this.taskListDao = taskListDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#deleteTaskList(com.wt.uum2.domain.TaskList)
	 */
	/**
	 * 方法说明：deleteTaskList
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteTaskList(TaskList taskList)
	{
		taskListDao.delete(taskList);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getTaskList(int, int)
	 */
	/**
	 * 方法说明：getTaskList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getTaskList(int page, int pagesize)
	{
		return taskListDao.getTaskList(page, pagesize);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getTaskListByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getTaskListByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return TaskList
	 */
	@Transactional(readOnly = true)
	public TaskList getTaskListByUuid(String uuid)
	{
		return taskListDao.getTaskListByUuid(uuid);
	}

	@Transactional(readOnly = true)
	public List<TaskList> getTaskLists()
	{
		return taskListDao.getTaskLists();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#saveTaskList(com.wt.uum2.domain.TaskList)
	 */
	/**
	 * 方法说明：saveTaskList
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveTaskList(TaskList taskList)
	{
		taskListDao.save(taskList);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#updateTaskList(com.wt.uum2.domain.TaskList)
	 */
	/**
	 * 方法说明：updateTaskList
	 * 
	 * @param taskList
	 *            taskList
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateTaskList(TaskList taskList)
	{
		taskListDao.update(taskList);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getAuditList(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getAuditList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getAuditList(Integer page, Integer pagesize)
	{
		return taskListDao.getAuditList(page, pagesize);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#searchAuditList(java.lang.Integer, java.lang.Integer, java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：searchAuditList
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
	@Transactional(readOnly = true)
	public UserPageResult searchAuditList(Integer page, Integer pagesize, String searchcontent,
			String searchvalue)
	{
		if (searchvalue.equals("user")) {
			return resourceLogDao.searchResourceLogsByUserContent(page, pagesize, searchcontent);
		} else if (searchvalue.equals("dept")) {
			return resourceLogDao.searchResourceLogsByDeptContent(page, pagesize, searchcontent);
		} else if (searchvalue.equals("group")) {
			return resourceLogDao.searchResourceLogsByGroupContent(page, pagesize, searchcontent);
		} else if (searchvalue.equals("consumer")) {
			return resourceLogDao
					.searchResourceLogsByEditUserContent(page, pagesize, searchcontent);
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getEventList(java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getEventList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getEventList(Integer page, Integer pagesize)
	{
		return getEventListByResouce(null, page, pagesize);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getEvent(java.lang.String)
	 */
	/**
	 * 方法说明：getEvent
	 * 
	 * @param uuid
	 *            uuid
	 * @return Event
	 */
	@Transactional(readOnly = true)
	public Event getEvent(String uuid)
	{
		return eventDao.getEvent(uuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getEventParamByEvent(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：getEventParamByEvent
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<EventParam> getEventParamByEvent(Event event)
	{
		return eventDao.getEventParam(event);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.TaskListService#getEventListByResouce(java.lang.String, java.lang.Integer, java.lang.Integer)
	 */
	/**
	 * 方法说明：getEventListByResouce
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getEventListByResouce(String uuid, Integer page, Integer pagesize)
	{
		return eventDao.getEventList(uuid, page, pagesize);
	}

}
