package com.wt.hea.common.util;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:WEB 工具类
 * 功能说明: 
 * 编写日期:	2011-3-31
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public final class WebUtil
{
	/**
	 * 
	 * 方法说明：获取Session中用户
	 * 
	 * @param request
	 *            request
	 * @return User实体
	 */
	public static User getSessionUser(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute("user");
		if (obj != null) {
			return (User) obj;
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：获取Session登录组Id
	 * 
	 * @param request
	 *            request
	 * @return String groupId
	 */
	public static String[] getSessionLoginGroupId(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute("loginGroup");
		if (obj != null) {
			return (String[]) obj;
		}
		return null;
	}

	/**
	 * 
	 * 方法说明：获取Session 前端登录组Id
	 * 
	 * @param request
	 *            request
	 * @return String groupId
	 */
	public static String[] getSessionFrontGroupId(HttpServletRequest request)
	{
		Object obj = request.getSession().getAttribute("louverGroup");
		if (obj != null) {
			return (String[]) obj;
		}
		return null;
	}

	/**
	 * 获取Web应用名
	 * 
	 * @param request
	 *            request
	 * @return String应用名称
	 */
	public static String getAppName(HttpServletRequest request)
	{
		String path = request.getContextPath();
		String appName = path.replace("/", "");
		return appName;
	}

	/**
	 * 获取Web应用IP:端口
	 * 
	 * @param request
	 *            request
	 * @return String
	 */
	public static String getHeaderHost(HttpServletRequest request)
	{
		return request.getHeader("Host");
	}

	/**
	 * 获取Web端口
	 * 
	 * @param request
	 *            request
	 * @return port
	 */
	public static int getPost(HttpServletRequest request)
	{
		return request.getLocalPort();
	}

	/**
	 * 获取Web应用基础路径
	 * 
	 * @param request
	 *            request
	 * @return String 如:"http://localhost:8080/app/"
	 */
	public static String getAppBasePath(HttpServletRequest request)
	{
		return "http://" + WebUtil.getHeaderHost(request) + "/" + WebUtil.getAppName(request) + "/";
	}

	/**
	 * 
	 * 方法说明：获取Web应用基础路径，根据当前运行环境获取。
	 * 
	 * @param request
	 *            request
	 * @return String
	 */
	public static String getAppBasePathByLocal(HttpServletRequest request)
	{
		return "http://" + WebUtil.getServerIP() + ":" + WebUtil.getPost(request) + "/"
				+ WebUtil.getAppName(request) + "/";
	}

	/**
	 * 获取服务器IP
	 * 
	 * @return String IP
	 */
	public static String getServerIP()
	{
		try {
			return InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 处理get请求 参数编码
	 * 
	 * @param request
	 *            request
	 * @param param
	 *            get参数
	 * @return 处理后的字符串
	 */
	public static String encodeParamUTF8String(HttpServletRequest request, String param)
	{
		Object obj = request.getParameter(param);
		String returnValue = null;
		if (obj != null) {
			try {
				returnValue = new String(obj.toString().getBytes("ISO-8859-1"), "UTF-8");
				if (returnValue.length() == obj.toString().length()) {
					returnValue = new String(obj.toString().getBytes("UTF-8"), "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return returnValue;
	}

	/**
	 * 
	 * 方法说明： 获取客户端ip地址
	 * 
	 * @param request
	 *            request
	 * @return 客户端ip地址
	 */
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

}
