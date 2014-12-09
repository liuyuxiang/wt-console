package com.wt.uum.device;

import java.util.UUID;

import com.wt.uum2.domain.DeviceInfo;

/**
 * <pre>
 * 业务名: AuthCodeGenerator 简易实现
 * 功能说明: 生成验证码
 * 编写日期:	2011-11-1
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UUIDCodeGenerator implements AuthCodeGenerator
{

	/**
	 * 
	 */
	private UUIDCodeGenerator()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see com.wt.uum.device.AuthCodeGenerator#genCode(com.wt.uum2.domain.DeviceInfo)
	 */
	/**
	 * 方法说明：genCode
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 * @return String
	 */
	public String genCode(DeviceInfo deviceInfo)
	{

		return UUID.randomUUID().toString();
	}

}
