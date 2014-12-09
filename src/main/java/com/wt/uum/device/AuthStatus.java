package com.wt.uum.device;

/**
 * <pre>
 * 业务名: 验证返回的状态码
 * 功能说明: 目前只有成功和失败两种，以后可能根据失败的原因返回不同的验证码
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public enum AuthStatus {
	
	/**
	 * 成功
	 */
	OK,
	
	/**
	 * 失败
	 */
	FAILER;
	
	
	
}
