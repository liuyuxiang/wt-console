package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Order;

import com.wt.uum2.dao.ServerLogDao;
import com.wt.uum2.domain.ServerLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-6-14
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ServerLogDaoImpl extends BaseDaoSupport<ServerLog> implements ServerLogDao
{

	@SuppressWarnings("unchecked")
	public List<ServerLog> getServerLogList()
	{
		return getSession().createCriteria(ServerLog.class).addOrder(Order.desc("handleTime"))
				.list();
	}

}
