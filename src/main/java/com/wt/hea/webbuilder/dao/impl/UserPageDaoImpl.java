package com.wt.hea.webbuilder.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.UserPageDao;
import com.wt.hea.webbuilder.model.UserPage;

/**
 * 
 * @author xiaoqi
 * 
 */
public class UserPageDaoImpl extends AbstractHibernateDaoSupport<UserPage> implements UserPageDao {

	/**
	 * 跟据用户id查找页面
	 * 
	 * @param userId
	 *            用户id
	 * @return List<UserPage>
	 */
	@SuppressWarnings("unchecked")
	public List<UserPage> findByUserId(String userId) {
		List<UserPage> userPage = this.getSession().createCriteria(UserPage.class).add(Restrictions.eq("userId", userId)).addOrder(Order.asc("dispSn")).list();
		return userPage;
	}

	/**
	 * 
	 * @param userId
	 *            userId
	 * @param pageNos
	 *            pageNos
	 * @return List<UserPage>
	 */
	public List<UserPage> findByUserIdAndNos(String userId, String[] pageNos) {

		List<UserPage> userPageList = new ArrayList<UserPage>();
		for (String pageNo : pageNos) {
			UserPage userPage = (UserPage) this.getSession().createCriteria(UserPage.class).add(Restrictions.eq("userId", userId))
					.add(Restrictions.eq("pageNo", pageNo)).uniqueResult();
			userPageList.add(userPage);
		}
		return userPageList;
	}

	/**
	 * @param pageNo
	 *            pageNo
	 * @param userId
	 *            userId
	 * @return boolean
	 */
	public boolean isAddedTemplatePage(String pageNo, String userId) {
		UserPage userPage = (UserPage) this.getSession().createCriteria(UserPage.class).add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("pageNo", pageNo)).uniqueResult();

		if (userPage != null) {
			// 已添加当前模板页
			return false;
		} else {
			// 未添加当前模板页
			return true;
		}
	}

}
