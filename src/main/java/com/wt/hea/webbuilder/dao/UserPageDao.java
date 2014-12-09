package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.UserPage;

/**
 * 
 * @author xiaoqi
 * 
 */
public interface UserPageDao extends EntityDao<UserPage>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(UserPage e);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @return List<UserPage>
	 */
	public List<UserPage> findAll();

	/**
	 * @param id
	 *            id
	 * @return UserPage
	 */
	public UserPage findById(Serializable id);

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<UserPage>
	 */
	public List<UserPage> findByProperty(String property, Object value);

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id);

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(UserPage e);

	/**
	 * @param e
	 *            e
	 * @return UserPage
	 */
	public UserPage update(UserPage e);

	/**
	 * 根据用户id查询出用户所属的页面
	 * 
	 * @param userId
	 *            用户id
	 * @return 用户个性化页面
	 */
	public List<UserPage> findByUserId(String userId);

	/**
	 * 判断是否添加过当前模板页面
	 * 
	 * @param pageNo
	 *            页面编码
	 * @param userId
	 *            用户id
	 * @return 是否添加过当前模板页面
	 */
	public boolean isAddedTemplatePage(String pageNo, String userId);

	/**
	 * 根据页面用户id和页面编号返回页面信息
	 * 
	 * @param userId
	 *            用户id
	 * @param pageNos
	 *            页面编码
	 * @return 用户个性化页面列表
	 */
	public List<UserPage> findByUserIdAndNos(String userId, String[] pageNos);

}
