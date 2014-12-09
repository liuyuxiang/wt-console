package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.UserPersonalInfo;
import com.wt.hea.webbuilder.service.UserPersonalInfoService;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-20
 * 作者:	Mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserPersonalInfoServiceImpl extends BaseService implements UserPersonalInfoService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(UserPersonalInfo e)
	{

		this.userPersonalInfoDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.userPersonalInfoDao.deleteById(id);
	}

	/**
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findAll()
	{
		return this.userPersonalInfoDao.findAll();
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findAll(String property, Boolean isAsc)
	{
		return this.userPersonalInfoDao.findAll(property, isAsc);
	}

	/**
	 * @param id
	 *            id
	 * @return UserPersonalInfo
	 */
	public UserPersonalInfo findById(Serializable id)
	{
		return this.userPersonalInfoDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findByProperty(String property, Object value)
	{
		return this.userPersonalInfoDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(UserPersonalInfo e)
	{
		this.userPersonalInfoDao.save(e);
	}

	/**
	 * 更新页面portlet
	 * 
	 * @param e
	 *            e
	 * @return UserPersonalInfo
	 */

	public UserPersonalInfo update(UserPersonalInfo e)
	{
		return this.userPersonalInfoDao.update(e);
	}

	/**
	 * 判断是否已添加
	 * 
	 * @param userId
	 *            userId
	 * @param portletId
	 *            portletId
	 * @param pageId
	 *            pageId
	 * @return boolean
	 */

	public boolean isAdded(String userId, String portletId, String pageId)
	{
		return this.userPersonalInfoDao.isAdded(userId, portletId, pageId);
	}

	/**
	 * 根据portlet所在列查出该列所有的portlet
	 * 
	 * @param sortNo
	 *            sortNo
	 * @param userId
	 *            userId
	 * @return List<UserPersonalInfo>
	 */

	public List<UserPersonalInfo> findBySortNo(int sortNo, String userId)
	{
		return this.userPersonalInfoDao.findBySortNo(sortNo, userId);
	}

}
