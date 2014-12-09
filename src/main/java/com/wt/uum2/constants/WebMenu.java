package com.wt.uum2.constants;

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
public enum WebMenu
{

	/**
	 * 
	 */
	DEPARTMENT_PAGE(0, "menu.department_page"), /**
	 * 
	 */
	GROUP_PAGE(1, "menu.group_page"), /**
	 * 
	 */
	APP_PAGE(2, "menu.app_page"), /**
	 * 
	 */
	ATT_PAGE(3, "menu.att_page"), /**
	 * 
	 */
	TASK_PAGE(4, "menu.task_page"), /**
	 * 
	 */
	APPLICATION_PAGE(5, "menu.application_page"), /**
	 * 
	 */
	USER_PAGE(6, "menu.user_page"), /**
	 * 
	 */
	UNKNOWN_PAGE(-1, "menu.unknown_page"), /**
	 * 
	 */
	DUTY_PAGE(7, "menu.duty_page");

	/**
	 * 方法说明：valueOf
	 * 
	 * @param intValue
	 *            intValue
	 * @return WebMenu
	 */
	public static WebMenu valueOf(int intValue)
	{
		switch (intValue) {
		case 0:
			return DEPARTMENT_PAGE;
		case 1:
			return GROUP_PAGE;
		case 2:
			return APP_PAGE;
		case 3:
			return ATT_PAGE;
		case 4:
			return TASK_PAGE;
		case 5:
			return APPLICATION_PAGE;
		case 6:
			return USER_PAGE;
		case 7:
			return DUTY_PAGE;
		default:
			return UNKNOWN_PAGE;
		}
	}

	/**
	 * 
	 */
	private final int intValue;

	/**
	 * 
	 */
	private final String msgId;

	/**
	 * @param intValue
	 *            intValue
	 * @param msgId
	 *            msgId
	 */
	WebMenu(int intValue, String msgId)
	{
		this.intValue = intValue;
		this.msgId = msgId;
	}

	public int getIntValue()
	{
		return intValue;
	}

	public String getMsgId()
	{
		return msgId;
	}
}
