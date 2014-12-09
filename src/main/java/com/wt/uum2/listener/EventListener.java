package com.wt.uum2.listener;

import java.util.Collection;

import com.wt.uum2.domain.Event;
import com.wt.uum2.listener.impl.SynchronismException;

/**
 * <pre>
 * 业务名:事件listener接口
 * 功能说明: 
 * 编写日期:	2011-3-23
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface EventListener
{

	/**
	 * 方法说明：执行主体程序
	 * 
	 * @param event
	 *            事件实体
	 */
	public void fire(Event event);

	/**
	 * 方法说明：fire
	 * 
	 * @param events
	 *            events
	 */
	public void fire(Collection<Event> events);

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean addUserToGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean removeUserFromGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean addGroupToGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean removeGroupFromGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean deleteGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean createGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean updateGroup(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean deleteUser(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean createUser(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean updateUser(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean deleteDept(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean createDept(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean updateDept(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean addUserToDept(Event event) throws SynchronismException;

	/**
	 * 方法说明：
	 * 
	 * @param event
	 *            事件实体
	 * @return 操作状态
	 */
	public boolean removeUserFromDept(Event event) throws SynchronismException;

}
