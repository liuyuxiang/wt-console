package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-25
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface EventDao extends BaseDao<Event>
{

	/**
	 * 方法说明：getEventList
	 * 
	 * @return List
	 */
	public List<Event> getEventList();

	/**
	 * 方法说明：getEvent
	 * 
	 * @param uuid
	 *            uuid
	 * @return Event
	 */
	public Event getEvent(String uuid);

	/**
	 * 方法说明：取event的序列
	 * 
	 * @return long
	 */
	public long getEventSequence();

	/**
	 * 方法说明：getEventList
	 * 
	 * @param resourceuuid
	 *            resourceuuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getEventList(String resourceuuid, Integer page, Integer pagesize);

	/**
	 * 方法说明：getEventParam
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	public List<EventParam> getEventParam(Event event);

	/**
	 * 方法说明：updateEvent
	 * 
	 * @param event
	 *            event
	 */
	public void updateEvent(Event event);

	/**
	 * 方法说明：取得event列表的分页信息,带状态
	 * 
	 * @param page
	 *            当前页码
	 * @param pagesize
	 *            每页最大
	 * @param status
	 *            如果为空则取所以状态
	 * @param order
	 *            true为顺序,false为倒序,默认为倒序
	 * @return UserPageResult
	 */
	public UserPageResult<Event> getEventList(int page, int pagesize, Integer status, Boolean order);

}
