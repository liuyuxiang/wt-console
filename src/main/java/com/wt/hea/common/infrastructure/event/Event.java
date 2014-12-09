package com.wt.hea.common.infrastructure.event;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 业务名:事件响应接口类
 * 功能说明: 事件响应接口类
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public interface Event {
	/**
	 * 
	 * 方法说明：ajax锁屏接口实现
	 *
	 * @param response http响应对象
	 * @param bindControlById 点击按钮时绑定的事件，这个是html页面按钮的id号
	 * @param screenHtmlContent 锁屏中间的文字或html
	 * @return 弹出锁屏时，中间出现的内容
	 */
	public StringBuffer eventLockScreen(HttpServletResponse response,String bindControlById, String screenHtmlContent) ;
	
	

	
	/**
	 * 
	 * 方法说明：(用户点击查询按钮后的)弹出窗口事件
	 *@return 返回一个对象话框提示
	 */
	public String eventPopBox();
	
	/**
	 * 方法说明：(用户点击查询按钮后的)弹出窗口事件响应
	 * @param message 弹出的消息文本
	 * @return 返回一个javascript的对象话框提示
	 */
	public String eventSimplePopBox(String message) ;
	
	
	/**
	 * 
	 * 方法说明：(用户点击查询按钮后的)滚动条事件，出现等待的滚动栏
	 *
	 */
	public void eventScollBar();
}
