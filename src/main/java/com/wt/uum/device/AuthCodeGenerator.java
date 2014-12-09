package com.wt.uum.device;

import com.wt.uum2.domain.DeviceInfo;

/**
 * <pre>
 * 业务名: 验证码生成器
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
public interface AuthCodeGenerator
{

	/**
	 * 方法说明：生成一个验证码
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 * @return 验证码
	 */
	String genCode(DeviceInfo deviceInfo);

}
