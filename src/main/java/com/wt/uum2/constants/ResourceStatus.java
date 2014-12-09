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
public enum ResourceStatus
{

	/**
	*
	*/
	DELETE_LOGIC(-1),
	/**
	*
	*/
	DELETE_PHYS(-2),
	/**
	*
	*/
	NORMAL(1),
	/**
	*
	*/
	UNKNOWN(-3),
	/**
	*
	*/
	FILTER(-4),
	/**
	*
	*/
	AUTHORIZE(-5),
	/**
	*
	*/
	UNDEAL(-6),
	/**
	*
	*/
	CREATED(-7),
	/**
	*
	*/
	ROLLBACK(-8),
	/**
	*
	*/
	MOVEDEPT(-9),
	/**
	*
	*/
	CHANGEPOSITION(-10),
	/**
	*
	*/
	LEAVE(-11),
	/**
	*
	*/
	RETIRED(-12);

	/**
	 * 方法说明：valueOf
	 * 
	 * @param intValue
	 *            intValue
	 * @return ResourceStatus
	 */
	public static ResourceStatus valueOf(int intValue)
	{
		switch (intValue) {
		case 1:
			return NORMAL;// 正常
		case -1:
			return DELETE_LOGIC;// 逻辑删除
		case -2:
			return DELETE_PHYS;// 物理删除
		case -4:
			return FILTER;// 应用系统过滤
		case -5:
			return AUTHORIZE;// 应用系统预授权
		case -6:
			return UNDEAL;// 应用系统为处理状态
		case -7:
			return CREATED;// 刚创建，待审批
		case -8:
			return ROLLBACK;// 不同意，回退
		case -9:
			return MOVEDEPT;// 移动部门审核
		case -10:
			return CHANGEPOSITION;// 改变职务
		case -11:
			return LEAVE;// 离职
		case -12:
			return RETIRED;// 退休
		default:
			return UNKNOWN;
		}
	}

	/**
	 * 
	 */
	private final int intValue;

	/**
	 * @param intValue
	 *            intValue
	 */
	ResourceStatus(int intValue)
	{
		this.intValue = intValue;
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
}
