package com.wt.hea.rbac.service;

import java.util.List;

import com.wt.hea.rbac.model.Index;



/**
 * 
 * <pre>
 * 业务名:IndexPersonalizationService.java
 * 功能说明: 用户个性化定义培训，是否显示
 * 编写日期:	2013-9-11
 * 作者:	DexinWang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface IndexPersonalizationService
{
	/**
	 * 功能说明：根据节点和用户名更新排序和是否显示
	 * @param indexList
	 * @param userId
	 * @return
	 */
	public boolean update(List<Index> indexList,String userId);
	/**
	 * 功能说明:根据节点和用户名取得排序后的节点集合
	 * @param freightList
	 * @param userId
	 * @return
	 */
	public List<Index> getByUseridAndCategory(List<Index> freightList,String userId);
}
