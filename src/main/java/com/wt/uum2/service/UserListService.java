package com.wt.uum2.service;

import java.util.List;
import java.util.Map;

import org.springframework.ui.ModelMap;

import com.wt.uum2.domain.User;
import com.wt.uum2.userlist.UserCol;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface UserListService
{

	/**
	 * 方法说明：getCols
	 * 
	 * @return List
	 */
	public abstract List<UserCol> getCols();

	/**
	 * 方法说明：getDatas
	 * 
	 * @param users
	 *            users
	 * @param nowNum
	 *            nowNum
	 * @return Map
	 */
	public abstract Map<String, Map<String, Object>> getDatas(List<User> users, int nowNum);

	/**
	 * 方法说明： getHeaders
	 * 
	 * @return List
	 */
	public abstract List<String> getHeaders();

	/**
	 * 方法说明：putAll2ModelMap
	 * 
	 * @param userlist
	 *            userlist
	 * @param nowNum
	 *            nowNum
	 * @param modelMap
	 *            modelMap
	 */
	public abstract void putAll2ModelMap(List<User> userlist, int nowNum, ModelMap modelMap);

	/**
	 * 方法说明：getColsAndHeaders
	 * 
	 * @return Map
	 */
	public abstract Map<String, String> getColsAndHeaders();
}