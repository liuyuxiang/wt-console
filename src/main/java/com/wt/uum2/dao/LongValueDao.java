package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.LongValue;

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
public interface LongValueDao extends BaseDao<LongValue>
{

	/**
	 * 方法说明：通过扩展属性得到扩展属性下的Long值集合
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	public List<LongValue> getLongValuesUnderAttribute(Attribute attribute);

}
