package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.PinyinUtil;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DutyDao;
import com.wt.uum2.dao.UserDutyDao;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDuty;
import com.wt.uum2.service.DutyService;

@Transactional
@Service
public class DutyServiceImpl implements DutyService
{

	@Resource
	private DutyDao dutyDao;
	
	@Resource
	private UserDutyDao userdutyDao;
	
	public Duty get(String uuid)
	{
		return dutyDao.read(uuid);
	}

	public Duty getByID(String dutyID)
	{
		return dutyDao.findUniqueByParam("id",dutyID);
	}

	public List<Duty> getByLevel(int level)
	{
		return dutyDao.findListByParam("level",level);
	}

	public List<Duty> getParent(Duty duty, Boolean include)
	{
		return dutyDao.getHigherDuty(duty,include);
	}

	public List<Duty> getChildren(Duty duty, Boolean include)
	{
		return dutyDao.getLowerDuty(duty,include);
	}

	public List<User> getUsers(Duty d)
	{
		return userdutyDao.getUser(d);
	}

	public List<Duty> getAll()
	{
		return dutyDao.findAll();
	}

	public void update(Duty duty)
	{
		if(duty.getOrder()==null){
			duty.setOrder((long)duty.getLevel());
		}
		if(duty.getStatus()==null){
			duty.setStatus(ResourceStatus.NORMAL.intValue());
		}
		duty.setModifiedTime(Calendar.getInstance().getTime());
		dutyDao.saveOrUpdate(duty);
	}

	public void delete(Duty duty)
	{
		userdutyDao.deleteByDuty(duty);
		dutyDao.delete(duty);
	}

	public void deleteByID(String dutyID)
	{
		Duty duty = getByID(dutyID);
		userdutyDao.deleteByDuty(duty);
		dutyDao.delete(duty);

	}

	public UserDuty addUser(Duty d, User u)
	{
		UserDuty ud = new UserDuty(d,u);
		userdutyDao.saveOrUpdate(ud);
		return ud;
	}

	public List<UserDuty> addUsers(Duty d, List<User> us)
	{
		List<UserDuty> list = new ArrayList<UserDuty>();
		for (User u : us) {
			list.add(addUser(d, u));
		}
		return list;
	}

	public boolean removeUser(Duty d, User u)
	{
		UserDuty ud = new UserDuty(d,u);
		userdutyDao.delete(ud);
		return true;
	}

	public boolean removeUsers(Duty d, List<User> us)
	{
		for (User u : us) {
			removeUser(d, u);
		}
		return true;
	}

	public void setDutyDao(DutyDao dutyDao)
	{
		this.dutyDao = dutyDao;
	}

	public void setUserdutyDao(UserDutyDao userdutyDao)
	{
		this.userdutyDao = userdutyDao;
	}

	public List<UserDuty> addUsers(List<Duty> ds, List<User> us)
	{
		List<UserDuty> list = new ArrayList<UserDuty>();
		for (Duty d : ds) {
			for (User u : us) {
				list.add(addUser(d, u));
			}
		}
		return list;
	}

	public boolean removeUsers(List<Duty> ds, List<User> us)
	{
		for (Duty d : ds) {
			for (User u : us) {
				removeUser(d, u);
			}
		}
		return true;
	}

	public List<Duty> getDuty(User user)
	{
		return userdutyDao.getDuty(user);
	}

	public UserPageResult<User> getUsersByDuty(Integer page, Integer pagesize, Duty duty)
	{
		return userdutyDao.getUsersPage( page, pagesize, duty);
	}

	public UserPageResult<UserDuty> searchUserByDuty(Integer page, Integer pagesize,
			Condition condition, Duty duty)
	{
		return userdutyDao.searchUserByDuty(page, pagesize,
				condition, duty);
	}

	public String getDutyId(String name)
	{
		String id = PinyinUtil.stringToHeadPinYin(name);
		String temp = id;
		String lib = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		for (int i = 0; (this.getByID(temp)!=null)&&(i < lib.length()); i++) {
			temp = id + lib.charAt(i);
		}
		return temp;
	}
	
	public static void main(String[] args)
	{
		DutyService d = new DutyServiceImpl();
		d.getDutyId("项目经理1");
	}

}
