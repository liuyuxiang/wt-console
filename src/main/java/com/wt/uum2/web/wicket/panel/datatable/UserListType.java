package com.wt.uum2.web.wicket.panel.datatable;

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
public enum UserListType
{
	/**
	 * 
	 */
	normal,search,recover;
	
	/**
	 * 方法说明：
	 * 
	 * @param string
	 *            string
	 * @return UserListType
	 */
	public static UserListType valueOfString(String string) {
		if(StringUtils.isBlank(string)){
			return normal;
		}else{
			return UserListType.valueOf(string);
		}
    }
}
