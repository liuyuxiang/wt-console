package com.wt.hea.common.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

/**
 * 
 * <pre>
 * 业务名:ajax输出工具类
 * 功能说明: 
 * 编写日期:	2011-4-2
 * 作者:	李毅
 * 
 * 历史记录
 * 1、修改日期：2011-5-14
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class AjaxOutputUtil {
	/**
	 * AJAX请求 文本输出
	 * @param obj  xml字符串对象
	 * @param response servlet响应对象,用于输出到客户端
	 */
	public static void printWriterObject(Object obj,HttpServletResponse response){
		try {
			response.setContentType("text/html;chartset=UTF-8");
			//response.addHeader("Accept-Charset", "utf-8");
			response.addHeader("Content-Type","text/html;charset=UTF-8");
			response.reset();
			PrintWriter pw=response.getWriter();
			
			pw.write(obj.toString());
			pw.flush();
			pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
