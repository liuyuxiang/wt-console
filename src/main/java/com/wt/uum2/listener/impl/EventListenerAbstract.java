package com.wt.uum2.listener.impl;

import java.util.Collection;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.SyncLog;
import com.wt.uum2.listener.EventListener;
import com.wt.uum2.service.EventService;
import com.wt.uum2.service.SynchronismService;
import com.wt.uum2.service.UUMService;

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
public abstract class EventListenerAbstract implements EventListener
{

	/**
	 * 日志实例
	 */
	protected Log logger = LogFactory.getLog(getClass());

	public abstract String getAPPID();

	/**
	 * 
	 */
	protected SyncLog syncLog;
	/**
	 * 
	 */
	private boolean enable;

	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private SynchronismService synchronismService;

	/**
	 * 
	 */
	private EventService eventService;

	/**
	 * @return ssssssss
	 */
	public boolean isEnable()
	{
		return enable;
	}

	/**
	 * @param enable
	 *            the enable to set
	 */
	public void setEnable(boolean enable)
	{
		this.enable = enable;
	}

	/**
	 * @return ssssssss
	 */
	public UUMService getUumService()
	{
		return uumService;
	}

	/**
	 * @param uumService
	 *            the uumService to set
	 */
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * @return ssssssss
	 */
	public SynchronismService getSynchronismService()
	{
		return synchronismService;
	}

	/**
	 * @param synchronismService
	 *            the synchronismService to set
	 */
	public void setSynchronismService(SynchronismService synchronismService)
	{
		this.synchronismService = synchronismService;
	}

	/**
	 * @return ssssssss
	 */
	public EventService getEventService()
	{
		return eventService;
	}

	/**
	 * @param eventService
	 *            the eventService to set
	 */
	public void setEventService(EventService eventService)
	{
		this.eventService = eventService;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#fire(java.util.Collection)
	 */
	/**
	 * 方法说明：fire
	 * 
	 * @param events
	 *            events
	 */
	public void fire(Collection<Event> events)
	{
		for (Event e : events) {
			fire(e);
		}

	}

	/**
	 * 方法说明：getWarningMessage
	 * 
	 * @param string
	 *            string
	 * @return String
	 */
	protected String getWarningMessage(String string)
	{
		StackTraceElement[] stack = Thread.currentThread().getStackTrace();

		return stack[2].getClassName() + "[line number:" + stack[2].getLineNumber() + "]: "
				+ stack[2].getMethodName() + " : " + string;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#fire(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：事件处理主函数
	 * 
	 * @param event
	 *            event
	 */
	public synchronized void fire(Event event)
	{
		if (!isEnable()) {
			syncLog = new SyncLog(getAPPID());
			syncLog.setEventuuid(event.getUuid());
			getSynchronismService().createSyncLog(syncLog);
			return;
		}
		logger.debug(event.getEventType());
		event.setParams(eventService.getEventParams(event));
		syncLog = new SyncLog(getAPPID());
		syncLog.setEventuuid(event.getUuid());
		try {

			switch (event.getEventType()) {
			case CREATE_USER:
				createUser(event);
				break;
			case UPDATE_USER:
				updateUser(event);
				break;
			case DELETE_USER:
				deleteUser(event);
				break;
			case CREATE_GROUP:
				createGroup(event);
				break;
			case UPDATE_GROUP:
				updateGroup(event);
				break;
			case DELETE_GROUP:
				deleteGroup(event);
				break;
			case CREATE_DEPAREMENT:
				createDept(event);
				break;
			case UPDATE_DEPAREMENT:
				updateDept(event);
				break;
			case DELETE_DEPAREMENT:
				deleteDept(event);
				break;
			case REMOVE_USER_GROUP:
				removeUserFromGroup(event);
				break;
			case ADD_USER_GROUP:
				addUserToGroup(event);
				break;
			case REMOVE_USER_DEPAREMENT:
				removeUserFromDept(event);
				break;
			case ADD_USER_DEPAREMENT:
				addUserToDept(event);
				break;
			default:
				break;
			}
		} catch (Exception e) {
			event.setStatus(Event.FAILED);
			syncLog.addSyncMessage(SyncLog.FAILED, e);
			if (e instanceof SynchronismException) {
				SynchronismException e1 = (SynchronismException) e;
				if (e1.getType() == SynchronismExceptionType.Unknown) {
					logger.error(e1, e1);
				}
			} else {
				logger.error(e, e);
			}
		} finally {
			synchronismService.createSyncLog(syncLog);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#addUserToGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：addUserToGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean addUserToGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#removeUserFromGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：removeUserFromGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean removeUserFromGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#addGroupToGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：addGroupToGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean addGroupToGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#removeGroupFromGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：removeGroupFromGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean removeGroupFromGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#deleteGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：deleteGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean deleteGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#createGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：createGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean createGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#updateGroup(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：updateGroup
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean updateGroup(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#deleteUser(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：deleteUser
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean deleteUser(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#createUser(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：createUser
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean createUser(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#updateUser(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：updateUser
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean updateUser(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#deleteDept(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：deleteDept
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean deleteDept(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#createDept(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：createDept
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean createDept(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#updateDept(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：updateDept
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean updateDept(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#addUserToDept(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：addUserToDept
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean addUserToDept(Event event) throws SynchronismException
	{
		return false;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.listener.EventListener#removeUserFromDept(com.wt.uum2.domain.Event)
	 */
	/**
	 * 方法说明：removeUserFromDept
	 * 
	 * @param event
	 *            event
	 * @return boolean
	 * @throws SynchronismException
	 */
	public boolean removeUserFromDept(Event event) throws SynchronismException
	{
		return false;
	}

}
