package com.wt.uum2.dao.impl;

import java.util.Collection;
import java.util.List;

import nak.nsf.dao.support.BaseDaoSupport;

import org.hibernate.criterion.Restrictions;

import com.wt.uum2.dao.UserApplicationDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserApplication;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserApplicationDaoImpl extends BaseDaoSupport<UserApplication> implements
		UserApplicationDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserApplicationDao#delete(com.wt.uum2.domain.User, java.util.Collection)
	 */
	/**
	 * 方法说明：delete
	 * 
	 * @param user
	 *            user
	 * @param application
	 *            application
	 */
	public void delete(User user, Collection<Application> application)
	{
		String hql = "delete UserApplication ua where ua.user=:user and ua.application in (:applications)";
		getSession().createQuery(hql).setEntity("user", user)
				.setParameterList("applications", application).executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserApplicationDao#addUserApplication(com.wt.uum2.domain.UserApplication)
	 */
	/**
	 * 方法说明：addUserApplication
	 * 
	 * @param app
	 *            app
	 */
	public void addUserApplication(UserApplication app)
	{
		getSession().save(app);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserApplicationDao#removeUserApplication(com.wt.uum2.domain.UserApplication)
	 */
	/**
	 * 方法说明：removeUserApplication
	 * 
	 * @param app
	 *            app
	 */
	public void removeUserApplication(UserApplication app)
	{
		getSession().delete(app);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserApplicationDao#getUserApplication(com.wt.uum2.domain.Application, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getUserApplication
	 * 
	 * @param a
	 *            a
	 * @param u
	 *            u
	 * @return UserApplication
	 */
	public UserApplication getUserApplication(Application a, User u)
	{
		UserApplication ua = (UserApplication) getSession().createCriteria(UserApplication.class)
				.add(Restrictions.eq("applicationuuid", a.getUuid()))
				.add(Restrictions.eq("useruuid", u.getUuid())).uniqueResult();
		return ua;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.UserApplicationDao#getUserApplication(com.wt.uum2.domain.Application)
	 */
	/**
	 * 方法说明：getUserApplication
	 * 
	 * @param app
	 *            app
	 * @return List
	 */
	public List<UserApplication> getUserApplication(Application app)
	{
		return getSession().createCriteria(UserApplication.class)
				.add(Restrictions.eq("applicationuuid", app.getUuid())).list();
	}

}