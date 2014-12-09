package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.ResourceTempDetails;

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
public interface ResourceTempDetailsDao extends BaseDao<ResourceTempDetails>
{

	/**
	 * 方法说明：获取详细相关修改信息
	 * 
	 * @param eventuuid
	 *            eventuuid
	 * @param type
	 *            type
	 * @param status
	 *            status
	 * @return List
	 */
	List<ResourceTempDetails> getResourceTempDetails(String eventuuid, Integer type, boolean status);

	/**
	 * 
	 * 方法说明：更新详细相关修改信息
	 * 
	 * @param eventuuid
	 *            eventuuid
	 * @param type
	 *            type
	 * @param status
	 *            status
	 */
	void updateResourceTempDetails(String eventuuid, Integer type, boolean status);

}
