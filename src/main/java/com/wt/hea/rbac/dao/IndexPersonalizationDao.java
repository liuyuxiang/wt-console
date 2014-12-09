package com.wt.hea.rbac.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.model.IndexPersonalization;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-4-1
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface IndexPersonalizationDao extends EntityDao<IndexPersonalization>
{

	public IndexPersonalization update(IndexPersonalization indexPersonalization);
	/**
	 * 功能说明:根据用户Id和节点Id查询个性化信息
	 * @param userId
	 * @param nodeId
	 * @return
	 */
	public IndexPersonalization getIndexPersonalizationByUserIdAndNodeId(String userId,String nodeId);
}
