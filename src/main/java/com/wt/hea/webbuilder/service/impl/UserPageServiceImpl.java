package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.webbuilder.model.UserPage;
import com.wt.hea.webbuilder.service.UserPageService;

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
public class UserPageServiceImpl extends BaseService implements UserPageService
{

	/**
	 * @param e
	 *            e
	 */
	public void delete(UserPage e)
	{
		this.userPageDao.delete(e);
	}

	/**
	 * @param id
	 *            id
	 */
	public void deleteById(Serializable id)
	{
		this.userPageDao.deleteById(id);
	}

	/**
	 * @return List<UserPage>
	 */
	public List<UserPage> findAll()
	{

		return this.userPageDao.findAll();
	}

	/**
	 * @param id
	 *            id
	 * @return UserPage
	 */
	public UserPage findById(Serializable id)
	{
		return this.userPageDao.findById(id);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<UserPage>
	 */
	public List<UserPage> findByProperty(String property, Object value)
	{
		return this.userPageDao.findByProperty(property, value);
	}

	/**
	 * @param e
	 *            e
	 */
	public void save(UserPage e)
	{
		this.userPageDao.save(e);
	}

	/**
	 * @param e
	 *            e
	 * @return UserPage
	 */
	public UserPage update(UserPage e)
	{

		return this.userPageDao.update(e);
	}

	/**
	 * @param userId
	 *            userId
	 * @return List<UserPage>
	 */
	public List<UserPage> findByUserId(String userId)
	{
		//
		return this.userPageDao.findByUserId(userId);
	}

	/**
	 * @param userId
	 *            userId
	 * @param pageNo
	 *            pageNo
	 * @return boolean
	 */
	public boolean isAddedTemplatePage(String pageNo, String userId)
	{

		return this.userPageDao.isAddedTemplatePage(pageNo, userId);
	}

	/**
	 * @param userId
	 *            userId
	 * @param pageNos
	 *            pageNo
	 * @return List<UserPage>
	 */
	public List<UserPage> findByUserIdAndNos(String userId, String[] pageNos)
	{
		return this.userPageDao.findByUserIdAndNos(userId, pageNos);
	}

}
