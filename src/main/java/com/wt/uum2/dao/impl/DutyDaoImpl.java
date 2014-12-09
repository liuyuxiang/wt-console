package com.wt.uum2.dao.impl;

import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum2.dao.DutyDao;
import com.wt.uum2.domain.Duty;

public class DutyDaoImpl extends BaseDaoSupport<Duty> implements DutyDao
{

	public List<Duty> findAll()
	{
		return getSession().createCriteria(Duty.class).list();
	}

	public List<Duty> getLowerDuty(Duty duty, Boolean include)
	{
		List<Duty> list = getSession().createCriteria(Duty.class).add(Restrictions.idEq(duty.getUuid())).add(Restrictions.gt("level", duty.getLevel())).list();
		return list;
	}

	public List<Duty> getHigherDuty(Duty duty, Boolean include)
	{
		List<Duty> list = getSession().createCriteria(Duty.class).addOrder(Order.desc("level")).add(Restrictions.lt("level", duty.getLevel())).list();
		return list;
	}

	public List<Duty> findListByParam(String key, Object value)
	{
		return getSession().createCriteria(Duty.class).add(Restrictions.eq(key, value)).list();
	}

	public Duty findUniqueByParam(String key, Object value)
	{
		return (Duty)getSession().createCriteria(Duty.class).add(Restrictions.eq(key, value)).uniqueResult();
	}

}
