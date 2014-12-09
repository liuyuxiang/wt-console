package com.wt.hea.common.infrastructure.event.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wt.hea.common.infrastructure.event.Event;

/**
 * 
 * <pre>
 * 业务名: 应用框架action层的事件响应调用
 * 功能说明: 提供锁屏，弹窗等控件
 * 编写日期:	2011-4-15
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-4-15
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public final class EventResponse implements Event
{
	/**
	 * 单例对象声明
	 */
	private static EventResponse EVENT_RESPONSE = null;

	/**
	 * 构造函数
	 */
	private EventResponse()
	{
	}

	/**
	 * 
	 * 方法说明：获取事件实例
	 * 
	 * @return 返回事件响应单例对象
	 */
	public static EventResponse getInstance()
	{
		if (EVENT_RESPONSE == null) {
			EVENT_RESPONSE = new EventResponse();
		}
		return EVENT_RESPONSE;
	}

	/**
	 * 
	 * 方法说明：ajax锁屏接口实现
	 * 
	 * @param response
	 *            http响应对象
	 * @param bindControlById
	 *            点击按钮时绑定的事件，这个是html页面按钮的id号
	 * @param screenHtmlContent
	 *            锁屏中间的文字或html
	 * @return 弹出锁屏时，中间出现的内容
	 */
	public StringBuffer eventLockScreen(HttpServletResponse response, String bindControlById,
			String screenHtmlContent)
	{
		StringBuffer divHtml = new StringBuffer();

		// 添加弹出框必需的样式
		divHtml.append("<style>");
		divHtml.append(".my_black_overlay{  display: none;  position: absolute;   top: 0%;  left: 0%;  width: 100%;  height: 100%;  background-color:#EEEEEE;   z-index:1001;   -moz-opacity: 0.8;  opacity:.80;   filter: alpha(opacity=80);  }   .my_white_content {   display: none;    position: absolute;  top: 25%;   left: 25%;   width: 50%;   height: 50%;   padding: 16px;   border: 16px solid orange;   background-color: white;   z-index:1002;  overflow: auto;  } ");
		divHtml.append("</style>");

		// 添加Html项
		divHtml.append("<a id='my_event' href = \"javascript:void(0)\" onclick = \"document.getElementById('my_light').style.display='block';document.getElementById('my_fade').style.display='block'\";></a>");

		divHtml.append("<div id=\"my_light\" class=\"my_white_content\">");
		divHtml.append(screenHtmlContent);
		divHtml.append("    <a href = \"javascript:void(0)\" onclick =\"document.getElementById('my_light').style.display='none';document.getElementById('my_fade').style.display='none'\">Close</a></div>");
		divHtml.append("<div id=\"my_fade\" class=\"my_black_overlay\"></div>");

		// 添加角本
		divHtml.append("<script>");
		divHtml.append("var _divOpenStatu='close';");
		divHtml.append("var _my_event = document.getElementById(\"my_event\");");
		divHtml.append("var _control=document.getElementById(\"" + bindControlById + "\");");
		divHtml.append("_my_event.appendChild(_control);");
		divHtml.append("function _closeScreen(){");
		divHtml.append("        document.getElementById('my_light').style.display='none';");
		divHtml.append("        document.getElementById('my_fade').style.display='none';");
		divHtml.append("}");
		divHtml.append("setInterval('_closeScreen()',3000);");
		divHtml.append("</script>");

		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		response.addHeader("Accept-Charset", "utf-8");
		response.addHeader("Content-Type", "text/html");
		try {
			response.getWriter().println(divHtml);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return divHtml;
	}

	/**
	 * 
	 * 方法说明：弹出框事件响应
	 * 
	 * @param response
	 *            响应对象
	 * @param screenHtmlContent
	 *            弹出窗口的自定义内容，可以是一段文本或一图片等
	 * @return 返回html代码片断
	 */
	public StringBuffer eventPopBox(HttpServletResponse response, String screenHtmlContent)
	{
		StringBuffer box = new StringBuffer();
		return box;
	}

	/**
	 * 弹出消息窗口
	 * 
	 * @return 返回弹出消息框
	 */
	public String eventPopBox()
	{

		return "";
	}

	/**
	 * 弹出消息窗口
	 * 
	 * @param message
	 *            对话框消息文本
	 * @return 返回一个javascript对象框
	 */
	public String eventSimplePopBox(String message)
	{
		String js = "<script>$(function(){alert('" + message + "');});</script>";
		return js;
	}

	/**
	 * 弹出消息窗口
	 * 
	 * @param message
	 *            对话框消息文本
	 * @param request
	 *            request
	 * @return
	 */
	public void eventSimplePopBox(String message, HttpServletRequest request)
	{
		String js = "<script>$(function(){alert('" + message + "');});</script>";
		request.setAttribute("message", js);
	}

	/**
	 * 显示进度条
	 */
	public void eventScollBar()
	{

	}
}
