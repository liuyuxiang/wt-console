package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UseridMapping;

/**
 * <pre>
 * 业务名: UserMappingDao
 * 功能说明: UserMappingDao
 * 编写日期:	2011-11-9
 * 作者:	LIu 
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserMappingDao extends BaseDao<UseridMapping>
{

	/**
	 * 方法说明： UseridMapping
	 * 
	 * @param user
	 *            user
	 * @return UseridMapping UseridMapping
	 */
	public List<UseridMapping> getUserMappingByUser(User user);

}
