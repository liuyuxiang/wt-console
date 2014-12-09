/**
 * 
 */
package com.wt.uum2.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentTempLogDao;
import com.wt.uum2.dao.ResourceTempDetailsDao;
import com.wt.uum2.dao.UserTempLogDao;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.ResourceTempDetails;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTempLog;
import com.wt.uum2.quartz.Constants;
import com.wt.uum2.service.UUMDataService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Alex
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class UUMDataServiceImpl implements UUMDataService
{

	/**
	 * 
	 */
	private UserTempLogDao userTempLogDao;

	/**
	 * 
	 */
	private DepartmentTempLogDao departmentTempLogDao;

	/**
	 * 
	 */
	private ResourceTempDetailsDao resourceTempDetailsDao;

	/**
	 * @param resourceTempDetailsDao
	 *            the resourceTempDetailsDao to set
	 */
	public void setResourceTempDetailsDao(ResourceTempDetailsDao resourceTempDetailsDao)
	{
		this.resourceTempDetailsDao = resourceTempDetailsDao;
	}

	/**
	 * @param userTempLogDao
	 *            the userTempLogDao to set
	 */
	public void setUserTempLogDao(UserTempLogDao userTempLogDao)
	{
		this.userTempLogDao = userTempLogDao;
	}

	public void setDepartmentTempLogDao(DepartmentTempLogDao departmentTempLogDao)
	{
		this.departmentTempLogDao = departmentTempLogDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#saveDepartmentTemp(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：saveDepartmentTemp
	 * 
	 * @param departmentTemp
	 *            departmentTemp
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveDepartmentTemp(DepartmentTempLog departmentTemp)
	{
		departmentTempLogDao.save(departmentTemp);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#saveUserTemp(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：saveUserTemp
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveUserTemp(UserTempLog userTemp)
	{
		userTempLogDao.save(userTemp);
		return 1;
	}

	@Transactional(readOnly = true)
	public List<UserTempLog> getModifyUserTemp()
	{
		return userTempLogDao.getModifyUserTemp();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#updateUserTemp(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：updateUserTemp
	 * 
	 * @param newUser
	 *            newUser
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateUserTemp(UserTempLog newUser)
	{
		if (newUser.getModifyStatus() == Constants.SUCCESSFUL) {
			newUser.setRemark("正常");
		} else if (newUser.getModifyStatus() == Constants.IGNORABLE) {
			newUser.setRemark("忽略");
		}
		if (StringUtils.isBlank(newUser.getUserCode())) {
			newUser.setUserCode(newUser.getUserId());
		}
		return userTempLogDao.updateUserTemp(newUser);

	}

	@Transactional(readOnly = true)
	public List<DepartmentTempLog> getModifyDeptTemp()
	{
		return departmentTempLogDao.getModifyDeptTemp();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#updateDeptTemp(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：updateDeptTemp
	 * 
	 * @param newDept
	 *            newDept
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateDeptTemp(DepartmentTempLog newDept)
	{
		if (StringUtils.isNotBlank(newDept.getRemark())) {
			newDept.setRemark(StringUtils.abbreviate(newDept.getRemark(), 255));
		}
		if (newDept.getModifyStatus() == Constants.SUCCESSFUL) {
			newDept.setRemark("正常");
		} else if (newDept.getModifyStatus() == Constants.IGNORABLE) {
			newDept.setRemark("忽略");
		}
		return departmentTempLogDao.updateDeptTemp(newDept);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize, User user)
	{
		Long i = 1L;
		return departmentTempLogDao.getDepartmentTempListByStatus(page, pagesize, i);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getUserTempListByStatus(java.lang.Integer, java.lang.Integer, java.lang.Long)
	 */
	/**
	 * 方法说明：getUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserTempListByStatus(Integer page, Integer pagesize, Long l)
	{
		return userTempLogDao.getUserTempListByStatus(page, pagesize, l);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#searchDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：searchDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param user
	 *            user
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition, User user)
	{
		Long l = 1L;
		return departmentTempLogDao.searchDepartmentTempListByStatus(page, pagesize, condition, l);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getDepartmentTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTempLog
	 */
	@Transactional(readOnly = true)
	public DepartmentTempLog getDepartmentTempByUuid(String deptUuid)
	{
		return departmentTempLogDao.getDepartmentTempByUuid(deptUuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getAllDepartmentTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<DepartmentTempLog> getAllDepartmentTempListByStatus(Long l)
	{
		return departmentTempLogDao.getDepartmentTempListByStatus(l);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getAllDepartmentTempListByStatus(java.lang.String)
	 */
	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	public List<DepartmentTempLog> getAllDepartmentTempListByStatus(String type)
	{
		return departmentTempLogDao.getDepartmentTempListByStatus(type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#searchUserTempListByStatus(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.Long)
	 */
	/**
	 * 方法说明：searchUserTempListByStatus
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param l
	 *            l
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchUserTempListByStatus(Integer page, Integer pagesize,
			Condition condition, Long l)
	{
		return userTempLogDao.searchUserTempListByStatus(page, pagesize, condition, l);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getUserTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return UserTempLog
	 */
	@Transactional(readOnly = true)
	public UserTempLog getUserTempByUuid(String deptUuid)
	{
		return userTempLogDao.getUserTempByUuid(deptUuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getAllUserTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UserTempLog> getAllUserTempListByStatus(Long l)
	{
		return userTempLogDao.getUserTempListByStatus(l);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getResourceTempDetails(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：getResourceTempDetails
	 * 
	 * @param userTempLog
	 *            userTempLog
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceTempDetails> getResourceTempDetails(UserTempLog userTempLog)
	{
		// 未处理的为false
		boolean status = false;
		// 用户类型为0
		int type = 0;
		return resourceTempDetailsDao.getResourceTempDetails(userTempLog.getUuid(), type, status);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getResourceTempDetails(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：getResourceTempDetails
	 * 
	 * @param departmentTempLog
	 *            departmentTempLog
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceTempDetails> getResourceTempDetails(DepartmentTempLog departmentTempLog)
	{
		// 未处理的为false
		boolean status = false;
		// 部门类型为2
		int type = 2;
		return resourceTempDetailsDao.getResourceTempDetails(departmentTempLog.getUuid(), type,
				status);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#updateResourceTempDetails(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：updateResourceTempDetails
	 * 
	 * @param userTempLog
	 *            userTempLog
	 */
	@Transactional(readOnly = false)
	public void updateResourceTempDetails(UserTempLog userTempLog)
	{
		// 未处理的为false
		boolean status = false;
		// 用户类型为0
		int type = 0;
		resourceTempDetailsDao.updateResourceTempDetails(userTempLog.getUuid(), type, status);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#updateResourceTempDetails(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：updateResourceTempDetails
	 * 
	 * @param departmentTempLog
	 *            departmentTempLog
	 */
	@Transactional(readOnly = false)
	public void updateResourceTempDetails(DepartmentTempLog departmentTempLog)
	{
		// 未处理的为false
		boolean status = false;
		// 部门类型为2
		int type = 2;
		resourceTempDetailsDao.updateResourceTempDetails(departmentTempLog.getUuid(), type, status);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getDepartmentTempLogList(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempLogList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getDepartmentTempLogList(Integer page, Integer pagesize, String type)
	{
		return departmentTempLogDao.getDepartmentTempListByStatus(page, pagesize, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#searchDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchDepartmentTempList(Integer page, Integer pagesize,
			Condition condition, String type)
	{
		return departmentTempLogDao.searchDepartmentTempList(page, pagesize, condition, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getUserTempList(java.lang.Integer, java.lang.Integer, java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getUserTempList(Integer page, Integer pagesize, String type)
	{
		return userTempLogDao.getUserTempListByStatus(page, pagesize, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#searchUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, java.lang.String)
	 */
	/**
	 * 方法说明：searchUserTempList
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchUserTempList(Integer page, Integer pagesize, Condition condition,
			String type)
	{
		return userTempLogDao.searchUserTempList(page, pagesize, condition, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMDataService#getAllUserTempListByStatus(java.lang.String)
	 */
	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param type
	 *            type
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UserTempLog> getAllUserTempListByStatus(String type)
	{
		return userTempLogDao.getUserTempListByStatus(type);
	}

}
