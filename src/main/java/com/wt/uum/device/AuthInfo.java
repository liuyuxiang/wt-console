package com.wt.uum.device;

/**
 * <pre>
 * 业务名: 设备验证
 * 功能说明: 对设备验证所需要的信息
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface AuthInfo {
	
	/**
	 * 方法说明： 获取设备key
	 *
	 * @return 设备key
	 */
	String getDeviceId();
	
	/**
	 * 方法说明： 获取 user ID
	 *
	 * @return user ID
	 */
	String getUserId();
	
	/**
	 * 方法说明： 获取验证码
	 * 
	 * @return 验证码
	 */
	String getAuthCode();
//	
//	/**
//	 * 方法说明：对信息进行验证，
//	 *
//	 * @return true false
//	 */
//	boolean validate();
	
}
