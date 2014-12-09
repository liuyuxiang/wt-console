package com.wt.uum2.dao;


import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserAuthorDao extends BaseDao<UserAuthor> {
	
	/**
	 * 得到用户的审核权限集合
	 * @param user user
	 * @return List
	 */
	public List<UserAuthor> getUserAuthorsByUser(User user);

	/**
	 * 方法说明：删除所有该角色需要验证的信息
	 *
	 * @param group 权限组
	 */
	public void deleteAuthorGroup(Group group);
}
