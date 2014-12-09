package com.wt.uum2.quartz;

/**
 * <pre>
 * 业务名:反向同步处理常量
 * 功能说明: 
 * 编写日期:	2012-7-25
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class Constants
{
	/**
	 * 处理成功
	 */
	public static final long SUCCESSFUL = 2l;

	/**
	 * 处理失败
	 */
	public static final long FAILED = 1l;

	/**
	 * 忽略处理
	 */
	public static final long IGNORABLE = 3l;
	
	/**
	 * 待处理
	 */
	public static final long PENDING = 0l;

}
