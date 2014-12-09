package com.wt.uum2.web.wicket.page.user.password;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:忘记密码功能的类型
 * 功能说明: 
 * 编写日期:	2011-11-3
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public enum ModifyPasswordType
{
	/**
	 * 类型
	 */
	portal,system,reset;
	
	/**
	 * 方法说明：将参数转换为枚举类型
	 *
	 * @param string 参数
	 * @return 类型
	 */
	public static ModifyPasswordType valueOfString(String string) {
		if(StringUtils.isBlank(string)){
			return portal;
		}else{
			return ModifyPasswordType.valueOf(string);
		}
    }

}
