package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.UserMappingDao;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UseridMapping;

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
public class UserMappingDaoImpl extends BaseDaoSupport<UseridMapping> implements UserMappingDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserMappingDao#getUserMappingByUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserMappingByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<UseridMapping> getUserMappingByUser(User user)
	{
		return getSession().createCriteria(UseridMapping.class).createCriteria("user")
				.add(Restrictions.idEq(user.getUuid())).list();
	}

}
