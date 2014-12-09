package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.UserDutyDao;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDuty;

public class UserDutyDaoImpl extends BaseDaoSupport<UserDuty> implements UserDutyDao
{

	public void deleteByDuty(Duty duty)
	{
		getSession().createQuery("delete from UserDuty where duty=:duty").setEntity("duty", duty).executeUpdate();
	}

	public void deleteByUser(User user)
	{
		getSession().createQuery("delete from UserDuty where user=:duty").setEntity("user", user).executeUpdate();
	}

	public List<User> getUser(Duty d)
	{
		return getSession().createQuery("select ud.user from UserDuty ud where ud.duty=:duty").setEntity("duty", d).list();
	}

	public List<Duty> getDuty(User user)
	{
		return getSession().createQuery("select ud.duty from UserDuty ud where ud.user=:user").setEntity("user", user).list();
	}

	public UserPageResult<User> getUsersPage(Integer page, Integer pagesize, Duty duty)
	{
		Query q = getSession()
				.createQuery(
						"select user from UserDuty as ud where ud.duty=:duty").setEntity("duty", duty).setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Query q1 = getSession()
				.createQuery(
						"select count(user) from UserDuty as ud where ud.duty=:duty").setEntity("duty", duty).setResultTransformer(
						CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		UserPageResult<User> result = new UserPageResult<User>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		pager.setQuantityOfData(Integer.valueOf(q1.uniqueResult().toString()));
		pager.compute();
		result.setPager(pager);
		q.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());
		result.setList(q.list());
		return result;
		
}

	public UserPageResult<UserDuty> searchUserByDuty(Integer page, Integer pagesize,
			Condition condition, Duty duty)
	{
		UserPageResult<UserDuty> result = new UserPageResult<UserDuty>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition.isEmpty()) {
			pager.setQuantityOfData(0);
			pager.compute();
			result.setList(ListUtils.EMPTY_LIST);
			result.setPager(pager);
			return result;
		}

		Criteria list = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		Criteria num = getSession().createCriteria(User.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.setProjection(Projections.rowCount());

		if (StringUtils.isNotBlank(condition.getUserName())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.name) like ? escape'/'",
					vagueSqlValueHandle(condition.getUserName()), Hibernate.STRING);
			list.add(c);
			num.add(c);
		} else if (StringUtils.isNotBlank(condition.getUserId())) {
			Criterion c = Restrictions.sqlRestriction("lower(this_1_.id) like lower(?) escape'/'",
					vagueSqlValueHandle(condition.getUserId()), Hibernate.STRING);
			list.add(c);
			num.add(c);
		}

		pager.setQuantityOfData((Long) num.uniqueResult());

		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		} else {
			pager.setPageSize(pager.getQuantityOfData());
		}

		pager.compute();

		list.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());
		List<UserDuty> l = new ArrayList<UserDuty>();
		
		List<User> ul = list.list();
		if(CollectionUtils.isNotEmpty(ul)){
			List<User> users = getUser(duty);
			for (User user : ul) {
				if(users.contains(user)){
					l.add(new UserDuty(duty, user));
				}else{
					l.add(new UserDuty(null, user));
				}
			}
		}
		result.setList(l);
		result.setPager(pager);
		return result;
	}

}
