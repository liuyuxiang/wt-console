package com.wt.uum2.dao.impl;

import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.UserAuthorDao;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-12-26
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserAuthorDaoImpl extends BaseDaoSupport<UserAuthor> implements UserAuthorDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserAuthorDao#getUserAuthorsByUser(com.wt.uum2.domain.User)
	 */
	/** {@inheritDoc} */
	public List<UserAuthor> getUserAuthorsByUser(User user)
	{
		return getSession().createCriteria(UserAuthor.class).add(Restrictions.eq("user", user))
				.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserAuthorDao#deleteAuthorGroup(com.wt.uum2.domain.Group)
	 */
	/** {@inheritDoc} */
	public void deleteAuthorGroup(Group group)
	{
		getSession().createQuery("delete UserAuthor ua where ua.group=:group")
				.setEntity("group", group).executeUpdate();
	}

}
