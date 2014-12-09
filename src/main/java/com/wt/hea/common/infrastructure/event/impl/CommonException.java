package com.wt.hea.common.infrastructure.event.impl;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 应用框架异常基类
 * 编写日期:	2011-4-6
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-4-6
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class CommonException extends Exception
{
	/**
	 * 单例对象
	 */
	private static CommonException exception = null;

	/**
	 * 
	 */
	private CommonException()
	{

	}

	/**
	 * 
	 * 方法说明：异常单实例方法
	 * 
	 * @return 返回异常实例
	 */
	public static CommonException getInstance()
	{
		if (exception == null) {
			exception = new CommonException();
		}
		return exception;
	}

	/**
	 * 
	 * 方法说明： 异常单实例方法
	 * 
	 * @param message
	 *            message
	 * @return CommonException
	 */
	public static CommonException getInstance(String message)
	{
		if (exception == null) {
			exception = new CommonException(message);
		}
		return exception;
	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 *            异常信息
	 */
	public CommonException(String message)
	{
		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param cause
	 *            异常对象
	 */
	public CommonException(Throwable cause)
	{
		super(cause);
	}

	/**
	 * 构造方法
	 * 
	 * @param message
	 *            异常信息
	 * @param cause
	 *            异常对象
	 */
	public CommonException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
