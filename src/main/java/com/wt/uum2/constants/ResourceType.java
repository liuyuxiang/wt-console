package com.wt.uum2.constants;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public enum ResourceType
{

	/**
	 * 
	 */
	DEPARTMENT(2, "DEPT"), /**
	 * 
	 */
	APPLICATION(3, "APP"), /**
	 * 
	 */
	DUTY(5, "DUTY"), /**
	 * 
	 */
	EXTENDED_ATTRIBUTE(4, "ATTR"), /**
	 * 
	 */
	GROUP(1, "GROUP"), /**
	 * 
	 */
	UNKNOWN(-1, "UNKNOWN"), /**
	 * 
	 */
	USER(0, "USER");

	/**
	 * 方法说明：valueOf
	 * 
	 * @param intValue
	 *            intValue
	 * @return ResourceType
	 */
	public static ResourceType valueOf(int intValue)
	{
		switch (intValue) {
		case 0:
			return USER;// 用户
		case 1:
			return GROUP;// 角色
		case 2:
			return DEPARTMENT;// 部门
		case 3:
			return APPLICATION;// 应用系统
		case 4:
			return EXTENDED_ATTRIBUTE;// 扩展属性
		case 5:
			return DUTY;// 职务
		default:
			return UNKNOWN;
		}
	}

	/**
	 * 方法说明：valueOfString
	 * 
	 * @param stringValue
	 *            stringValue
	 * @return ResourceType
	 */
	public static ResourceType valueOfString(String stringValue)
	{
		if (StringUtils.equals(stringValue, "USER")) {
			return USER;
		} else if (StringUtils.equals(stringValue, "GROUP")) {
			return GROUP;
		} else if (StringUtils.equals(stringValue, "DEPT")) {
			return DEPARTMENT;
		} else if (StringUtils.equals(stringValue, "APP")) {
			return APPLICATION;
		} else if (StringUtils.equals(stringValue, "ATTR")) {
			return EXTENDED_ATTRIBUTE;
		} else if (StringUtils.equals(stringValue, "DUTY")) {
			return DUTY;
		} else {
			return UNKNOWN;
		}
	}

	/**
	 * 方法说明：getStringByType
	 * 
	 * @param intValue
	 *            intValue
	 * @return String
	 */
	public static String getStringByType(int intValue)
	{
		switch (intValue) {
		case 0:
			return "用户";// 用户
		case 1:
			return "角色";// 角色
		case 2:
			return "部门";// 部门
		case 3:
			return "应用系统";// 应用系统
		case 4:
			return "扩展属性";// 扩展属性
		case 5:
			return "职务";// 扩展属性
		default:
			return "无";
		}
	}

	/**
	 * 
	 */
	private final int intValue;

	/**
	 * 
	 */
	private final String stringValue;

	/**
	 * @param intValue
	 *            intValue
	 * @param key
	 *            key
	 */
	ResourceType(int intValue, String key)
	{
		this.intValue = intValue;
		this.stringValue = key;
	}

	/**
	 * 方法说明：intValue
	 * 
	 * @return int
	 */
	public int intValue()
	{
		return intValue;
	}

	/**
	 * 方法说明：stringValue
	 * 
	 * @return String
	 */
	public String stringValue()
	{
		return stringValue;
	}
}
