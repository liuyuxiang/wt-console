package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.SyncED;

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
public interface SyncEDDao extends BaseDao<SyncED>
{

	/**
	 * 方法说明：getSyncEDList
	 * 
	 * @return List
	 */
	public List<SyncED> getSyncEDList();

	/**
	 * 方法说明：getSyncED
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult getSyncED(int page, int pagesize);

	/**
	 * 方法说明：getSyncEDByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncED
	 */
	public SyncED getSyncEDByUuid(String uuid);
}
