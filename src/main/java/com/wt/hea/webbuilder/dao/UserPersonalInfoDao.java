package com.wt.hea.webbuilder.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.webbuilder.model.UserPersonalInfo;

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
public interface UserPersonalInfoDao extends EntityDao<UserPersonalInfo>
{

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param e
	 *            e
	 */
	public void delete(UserPersonalInfo e);

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id);

	/**
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findAll();

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findAll(String property, Boolean isAsc);

	/**
	 * @param id
	 *            id
	 * @return UserPersonalInfo
	 */
	public UserPersonalInfo findById(Serializable id);

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<UserPersonalInfo>
	 */
	public List<UserPersonalInfo> findByProperty(String property, Object value);

	/**
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(UserPersonalInfo e);

	/**
	 * @param e
	 *            e
	 * @return UserPersonalInfo
	 */
	public UserPersonalInfo update(UserPersonalInfo e);

	/**
	 * 方法说明： 判断portletId的portlet是否呗添加
	 * 
	 * @param userId
	 *            用户id
	 * @param portletId
	 *            portletid
	 * @param pageId
	 *            页面id
	 * @return 添加情况
	 */
	public boolean isAdded(String userId, String portletId, String pageId);

	/**
	 * 方法说明： 根据列号查询出用户的portlet
	 * 
	 * @param sortNo
	 *            排序号
	 * @param userId
	 *            用户id
	 * @return 返回用户portlet信息
	 */
	public List<UserPersonalInfo> findBySortNo(int sortNo, String userId);
}
