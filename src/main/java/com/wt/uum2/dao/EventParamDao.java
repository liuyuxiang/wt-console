package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-8-25
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface EventParamDao extends BaseDao<EventParam>
{

	/**
	 * 方法说明：save
	 * 
	 * @param params
	 *            params
	 */
	void save(List<EventParam> params);

	/**
	 * 方法说明：getEventParams
	 * 
	 * @param event
	 *            event
	 * @return List
	 */
	List<EventParam> getEventParams(Event event);

}
