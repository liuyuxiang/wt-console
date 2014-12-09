package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.ServerLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ServerLogDao extends BaseDao<ServerLog>
{

	/**
	 * 方法说明：getServerLogList
	 * 
	 * @return List
	 */
	public List<ServerLog> getServerLogList();

}
