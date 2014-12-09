package com.wt.uum2.dao;

import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Duty;

public interface DutyDao extends BaseDao<Duty>
{

	List<Duty> findAll();

	List<Duty> getLowerDuty(Duty duty, Boolean include);

	List<Duty> getHigherDuty(Duty duty, Boolean include);

	public List<Duty> findListByParam(String key, Object value);

	public Duty findUniqueByParam(String key, Object value);

}
