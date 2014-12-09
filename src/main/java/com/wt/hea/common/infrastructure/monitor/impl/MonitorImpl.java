package com.wt.hea.common.infrastructure.monitor.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.util.Iterator;
import java.util.Map.Entry;

import com.wt.hea.common.infrastructure.monitor.Monitor;

/**
 * 
 * <pre>
 * 业务名: 监控服务接口实现类
 * 功能说明: 实现监控接口实现
 * 编写日期:	2011-3-29
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-3-29
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class MonitorImpl implements Monitor
{

	/**
	 * 
	 * 方法说明：调用操作系统命令返回服务器监控信息
	 * 
	 * @param cmds
	 *            系统可识别的命令,如:　String[] cmd = new String[]{ "cmd.exe", "/C", "ipconfig" };
	 * @return 返回系统信息
	 */
	public String excuteCmd(String... cmds)
	{
		InputStream ins = null;
		BufferedReader reader = null;

		StringBuffer info = new StringBuffer();
		try {
			Process process = Runtime.getRuntime().exec(cmds);
			ins = process.getInputStream();

			reader = new BufferedReader(new InputStreamReader(ins, "GBK"));
			String line = null;
			while ((line = reader.readLine()) != null) {
				info.append(line);
			}
			// int exitValue = process.waitFor();
			process.getOutputStream().close();
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {

					e.printStackTrace();
				} finally {

				}
			}
		}
		return info.toString();
	}

	/**
	 * 
	 * 方法说明：获取系统的安装环境，如：java环境变量,jkd版本等
	 * 
	 * @return 返回系统环境信息 (xml格式)
	 */
	public String getEnvInfo()
	{
		StringBuffer info = new StringBuffer();
		info.append("<infos>");

		Iterator<Entry<String, String>> iter = System.getenv().entrySet().iterator();

		while (iter.hasNext()) {
			Entry<String, String> entry = iter.next();
			info.append("<info>");
			info.append("<key>");
			info.append("<![CDATA[");
			info.append(entry.getKey());
			info.append("]]>");
			info.append("</key>");

			info.append("<value>");
			info.append("<![CDATA[");
			info.append(entry.getValue().replace("]]>", ""));
			info.append("]]>");
			info.append("</value>");
			info.append("</info>");
		}

		info.append("</infos>");
		return info.toString();
	}

	/**
	 * 监控数据库服务器
	 * 
	 * @param args
	 *            服务器命令参数
	 * @param type
	 *            监控类型
	 * 
	 * @return 返回服务器返回的字符串值
	 */
	public String monitDbServer(String[] args, MonitorType type)
	{
		return monitInvoke(args, type);
	}

	/**
	 * 监控web服务器
	 * 
	 * @param args
	 *            服务器命令参数
	 * @param type
	 *            监控类型
	 * @return 返回服务器返回的字符串信息
	 */
	public String monitWebServer(String[] args, MonitorType type)
	{
		return monitInvoke(args, type);
	}

	/**
	 * 
	 * 方法说明：监控通用方法
	 * 
	 * @param args
	 *            监控命令或数据库sql命令
	 * @param type
	 *            监控类型
	 * @return 返回服务器执行命令后的信息
	 */
	private String monitInvoke(String[] args, MonitorType type)
	{
		String infos = null;
		switch (type) {
		case CMD:
			infos = excuteCmd(args);
			break;
		case SQL:

			break;
		case URL:
			StringBuffer myinfos = new StringBuffer();
			for (String url : args) {
				myinfos.append(getUrlContent(url));
			}
			infos = myinfos.toString();
			break;
		default:
			break;
		}

		return infos;
	}

	/**
	 * 
	 * 方法说明：跟据url返回服务器页面内容的字符串
	 * 
	 * @param httpUrlStr
	 *            url链接
	 * @return 返回服务器页面内容的字符串
	 */
	@SuppressWarnings("unused")
	private String getUrlContent(String httpUrlStr)
	{
		InputStream in = null;
		OutputStream out = null;
		try {
			URL url = new URL(httpUrlStr);
			in = url.openStream();

			byte[] buffer = new byte[in.available()];
			int read;
			while ((read = in.read(buffer)) != -1) {
			}
			String info = new String(buffer, "UTF-8");
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroy(in, out);
		}
		return null;
	}

	/*	public static void main(String args[]){
			MonitorImpl m=new MonitorImpl();
		}
		*/
	/**
	 * 
	 * 方法说明：回收系统输入、输出资源
	 * 
	 * @param in
	 *            输入流对象
	 * @param out
	 *            输出流对象
	 */
	private void destroy(InputStream in, OutputStream out)
	{
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
