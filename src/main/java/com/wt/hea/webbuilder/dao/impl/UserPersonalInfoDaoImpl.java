package com.wt.hea.webbuilder.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.UserPersonalInfoDao;
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
public class UserPersonalInfoDaoImpl extends AbstractHibernateDaoSupport<UserPersonalInfo>
		implements UserPersonalInfoDao
{

	/**
	 * @param userId
	 *            userId
	 * @param portletId
	 *            portletId
	 * @param pageId
	 *            pageId
	 * @return boolean
	 */
	@SuppressWarnings("unchecked")
	public boolean isAdded(String userId, String portletId, String pageId)
	{

		List<UserPersonalInfo> upiList = this.getSession().createCriteria(UserPersonalInfo.class)
				.add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("portletId", portletId))
				.add(Restrictions.eq("pageId", pageId)).list();
		if (upiList != null && upiList.size() > 0) {
			return true;
		} else {
			return false;
		}
		// return false;
	}

	/**
	 * 根据列号查出用户所属portlet
	 * 
	 * @param sortNo
	 *            sortNo
	 * @param userId
	 *            userId
	 * @return ist<UserPersonalInfo>
	 */
	@SuppressWarnings("unchecked")
	public List<UserPersonalInfo> findBySortNo(int sortNo, String userId)
	{
		List<UserPersonalInfo> upiList = this.getSession().createCriteria(UserPersonalInfo.class)
				.add(Restrictions.eq("sortNo", sortNo)).add(Restrictions.eq("userId", userId))
				.list();
		return upiList;
	}

}
