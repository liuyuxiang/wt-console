package com.wt.uum2.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentTempDao;
import com.wt.uum2.dao.ResourceMappingDao;
import com.wt.uum2.dao.UserTempDao;
import com.wt.uum2.domain.DepartmentTemp;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceMapping;
import com.wt.uum2.domain.UserTemp;
import com.wt.uum2.service.UUMTempDataService;
import com.wt.uum2.userlist.DeptTempType;
import com.wt.uum2.userlist.UserTempType;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class UUMTempDataServiceImpl implements UUMTempDataService
{

	/**
	 * 
	 */
	private UserTempDao userTempDao;

	/**
	 * 
	 */
	private DepartmentTempDao departmentTempDao;

	/**
	 * 
	 */
	private ResourceMappingDao resourceMappingDao;

	/**
	 * @param resourceMappingDao
	 *            the resourceMappingDao to set
	 */
	public void setResourceMappingDao(ResourceMappingDao resourceMappingDao)
	{
		this.resourceMappingDao = resourceMappingDao;
	}

	/**
	 * @param userTempDao
	 *            the userTempDao to set
	 */
	public void setUserTempDao(UserTempDao userTempDao)
	{
		this.userTempDao = userTempDao;
	}

	/**
	 * @param departmentTempDao
	 *            the departmentTempDao to set
	 */
	public void setDepartmentTempDao(DepartmentTempDao departmentTempDao)
	{
		this.departmentTempDao = departmentTempDao;
	}

	public List<UserTemp> getModifyUserTemp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	public List<DepartmentTemp> getModifyDeptTemp()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.userlist.DeptTempType)
	 */
	/**
	 * 方法说明：getDepartmentTempList
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
	public UserPageResult getDepartmentTempList(Integer page, Integer pagesize, DeptTempType type)
	{
		return departmentTempDao.getDepartmentTempList(page, pagesize, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.userlist.UserTempType)
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
	public UserPageResult getUserTempList(Integer page, Integer pagesize, UserTempType type)
	{
		if (type != null) {
			return userTempDao.getUserTempConditionList(page, pagesize, type);
		} else {
			return userTempDao.getUserTempMappingList(page, pagesize);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#searchDepartmentTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.userlist.DeptTempType)
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
			Condition condition, DeptTempType type)
	{
		return departmentTempDao.searchDepartmentTempList(page, pagesize, condition, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getDepartmentTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentTempByUuid
	 * 
	 * @param deptUuid
	 *            deptUuid
	 * @return DepartmentTemp
	 */
	@Transactional(readOnly = true)
	public DepartmentTemp getDepartmentTempByUuid(String deptUuid)
	{
		return departmentTempDao.getDepartmentTempByUuid(deptUuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getAllDepartmentTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getAllDepartmentTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<DepartmentTemp> getAllDepartmentTempListByStatus(Long l)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#searchUserTempList(java.lang.Integer, java.lang.Integer, com.wt.uum2.constants.Condition, com.wt.uum2.userlist.UserTempType)
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
			UserTempType type)
	{
		return userTempDao.searchUserTempList(page, pagesize, condition, type);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getUserTempByUuid(java.lang.String)
	 */
	/**
	 * 方法说明：getUserTempByUuid
	 * 
	 * @param userUuid
	 *            userUuid
	 * @return UserTemp
	 */
	@Transactional(readOnly = true)
	public UserTemp getUserTempByUuid(String userUuid)
	{
		return userTempDao.getUserTempByUuid(userUuid);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#getAllUserTempListByStatus(java.lang.Long)
	 */
	/**
	 * 方法说明：getAllUserTempListByStatus
	 * 
	 * @param l
	 *            l
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UserTemp> getAllUserTempListByStatus(Long l)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#addResourceMapping(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：addResourceMapping
	 * 
	 * @param resource
	 *            resource
	 * @param userCode
	 *            userCode
	 */
	@Transactional(readOnly = false)
	public void addResourceMapping(Resource resource, String userCode)
	{
		if (StringUtils.isNotBlank(userCode)) {
			ResourceMapping rm = resourceMappingDao.getResourceMapping(resource, userCode);
			if (rm == null) {
				rm = new ResourceMapping();
				rm.setMappingid(userCode);
				rm.setResourceuuid(resource.getUuid());
				resourceMappingDao.save(rm);
			}
		}

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UUMTempDataService#removeResourceMapping(com.wt.uum2.domain.Resource, java.lang.String)
	 */
	/**
	 * 方法说明：removeResourceMapping
	 * 
	 * @param resource
	 *            resource
	 * @param userCode
	 *            userCode
	 */
	@Transactional(readOnly = false)
	public void removeResourceMapping(Resource resource, String userCode)
	{

		ResourceMapping rm = resourceMappingDao.getResourceMapping(resource, userCode);
		if (rm != null) {
			resourceMappingDao.delete(rm);
		}
	}

}
