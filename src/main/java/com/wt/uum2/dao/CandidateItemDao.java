package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;

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
public interface CandidateItemDao extends BaseDao<CandidateItem>
{

	/**
	 * 方法说明：通过扩展属性类型获得扩展类型下的候选项
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	public List<CandidateItem> getCandidateItembyAttributeType(AttributeType attributeType);

}
