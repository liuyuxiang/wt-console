package com.wt.uum2.userlist;

import org.apache.commons.lang.StringUtils;

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
public enum DeptTempType
{
	erpDept, noMapping, mapping;

	/**
	 * 方法说明：valueOfString
	 * 
	 * @param type
	 *            type
	 * @return DeptTempType
	 */
	public static DeptTempType valueOfString(String type)
	{
		if (StringUtils.equalsIgnoreCase(type, noMapping.toString())) {
			return noMapping;
		} else if (StringUtils.equalsIgnoreCase(type, mapping.toString())) {
			return mapping;
		}
		return erpDept;
	}
}
