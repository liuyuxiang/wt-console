package com.wt.uum.device;

/**
 * <pre>
 * 业务名: 设备相关异常
 * 功能说明: 设备相关异常
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceException extends RuntimeException
{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5046752998079119528L;

	/**
	 * @param msg
	 *            msg
	 * @param cause
	 *            cause
	 */
	public DeviceException(String msg, Throwable cause)
	{
		super(msg);
		super.initCause(cause);
	}

	/**
	 * @param msg
	 *            msg
	 */
	public DeviceException(String msg)
	{
		super(msg);
	}

	/**
	 * @param cause
	 *            cause
	 */
	public DeviceException(Throwable cause)
	{
		super();
		super.initCause(cause);
	}
}
