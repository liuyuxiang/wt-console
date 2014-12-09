package com.wt.uum2.web.wicket.panel.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-11-25
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public enum AppUserSearchType
{
	/**
	 * 
	 */
	username, userid;

	/**
	 * 方法说明：
	 * 
	 * @param string
	 *            string
	 * @return UserListType
	 */
	public static AppUserSearchType valueOfString(String string)
	{
		if (StringUtils.isBlank(string)) {
			return username;
		} else {
			return AppUserSearchType.valueOf(string);
		}
	}

	/**
	 * 方法说明：getSearchTypeMap
	 * 
	 * @return Map
	 */
	public static Map<String, String> getSearchTypeMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("username", "姓名");
		map.put("userid", "用户ID");
		return map;
	}

	/**
	 * 方法说明：getSearchTypeList
	 * 
	 * @return List
	 */
	public static List<String> getSearchTypeList()
	{
		List<String> types = new ArrayList<String>();

		for (AppUserSearchType type : values()) {
			types.add(type.toString());
		}
		return types;
	}

}
