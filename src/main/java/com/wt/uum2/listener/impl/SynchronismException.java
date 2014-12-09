package com.wt.uum2.listener.impl;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SynchronismException extends RuntimeException
{
	/**
	 * 
	 */
	private SynchronismExceptionType type;

	/**
	 * @param ex
	 *            ex
	 */
	public SynchronismException(Exception ex)
	{
		super(ex);
		type = SynchronismExceptionType.Unknown;
	}

	/**
	 * @param s
	 *            s
	 */
	public SynchronismException(String s)
	{
		super(s);
		type = SynchronismExceptionType.Known;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 4808090572114821918L;

	public SynchronismExceptionType getType()
	{
		return type;
	}

}
