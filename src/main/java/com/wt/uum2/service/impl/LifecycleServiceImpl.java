package com.wt.uum2.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum.version.UumCoreVersionUtils;
import com.wt.uum2.constants.MACAddressUtils;
import com.wt.uum2.domain.ServerLog;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class LifecycleServiceImpl implements InitializingBean, DisposableBean
{

	private static ServerLog serverlog;

	public static ServerLog getServerlog()
	{
		return serverlog;
	}

	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * @param uumService
	 *            the uumService to set
	 */
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	/**
	 * 方法说明：afterPropertiesSet
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void afterPropertiesSet() throws Exception
	{
		uumService.createServerLog(getNewServerLog(ServerLog.STARTING));

	}

	/**
	 * 方法说明：getNewServerLog
	 * 
	 * @param status
	 *            status
	 * @return ServerLog
	 */
	public ServerLog getNewServerLog(String status)
	{
		setServerlog(new ServerLog(status));

		serverlog.setIp(StringUtils.join(getIps(), "/"));
		serverlog.setMac(getMACAddresses());
		serverlog.setFileEncoding(System.getProperty("file.encoding"));
		serverlog.setJavaHome(System.getProperty("java.home"));
		serverlog.setJavaVersion(System.getProperty("java.version"));
		serverlog.setOsArch(System.getProperty("os.arch"));
		serverlog.setOsName(System.getProperty("os.name"));
		serverlog.setOsVersion(System.getProperty("os.version"));
		serverlog.setUserDir(System.getProperty("user.dir"));
		serverlog.setUserHome(System.getProperty("user.home"));
		serverlog.setUserName(System.getProperty("user.name"));
		serverlog.setProjectVer(UumCoreVersionUtils.getVersion().getWholeVersion());
		serverlog.setProjectId(ProjectResolver.getId());

		return getServerlog();
	}

	private String getMACAddresses()
	{
		return MACAddressUtils.getMACAddresses();
	}

	/**
	 * 方法说明：getIps
	 * 
	 * @return List
	 */
	protected List<String> getIps()
	{
		List<String> ipResult = new LinkedList<String>();

		Enumeration<NetworkInterface> netInterfaces;
		try {
			netInterfaces = NetworkInterface.getNetworkInterfaces();

			while (netInterfaces.hasMoreElements()) {

				NetworkInterface networkInterface = netInterfaces.nextElement();

				Enumeration<InetAddress> address = networkInterface.getInetAddresses();

				while (address.hasMoreElements()) {
					ipResult.add(address.nextElement().getHostAddress());
				}

			}
		} catch (SocketException e) {
			e.printStackTrace();
		}

		Iterator<String> iterator = ipResult.iterator();
		while (iterator.hasNext()) {
			String ip = iterator.next();
			if (ip.contains(":")) {
				iterator.remove();
			} else if (ip.equals("127.0.0.1")) {
				iterator.remove();
			}
		}

		return ipResult;
	}

	/**
	 * 方法说明：getHostAddress
	 * 
	 * @return String
	 */
	protected String getHostAddress()
	{
		String ipResult = String.valueOf("");
		try {
			InetAddress address = InetAddress.getLocalHost();
			ipResult = address.getHostAddress();
		} catch (UnknownHostException e) {
			ipResult = "Unknown Host!";
		}
		return ipResult;
	}

	/**
	 * 方法说明：getMACAddress
	 * 
	 * @param ipAddress
	 *            ipAddress
	 * @return String
	 */
	public String getMACAddress(String ipAddress)
	{
		if (StringUtils.isNotBlank(ipAddress)) {
			ipAddress = getHostAddress();
		}
		String data = "", strMAC = "";
		Process pp = null;
		InputStreamReader ir = null;

		BufferedReader br = null;
		try {
			if (StringUtils.isNotBlank(ipAddress)) {
				if (!StringUtils.equals(ipAddress, "127.0.0.1")) {
					pp = Runtime.getRuntime().exec("nbtstat -a " + ipAddress);
					ir = new InputStreamReader(pp.getInputStream());

					br = new BufferedReader(ir);
					while ((data = br.readLine()) != null) {
						if (data.indexOf("MAC Address") > 1) {
							strMAC = StringUtils.substringAfterLast(data, " ");
							break;
						}
					}
				}
			} else {
				return "Can't Get IP Address!";
			}
		} catch (IOException ex) {
			return "Can't Get MAC Address!";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// Ignore
				}
			}
			if (ir != null) {
				try {
					ir.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pp != null) {
				try {
					pp.getErrorStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				try {
					pp.getOutputStream().close();
				} catch (IOException ex) {
					// Ignore it.
				}
				pp.destroy();

			}
		}
		if (strMAC.length() < 17) {
			return "Trespass on MAC!";
		}
		return strMAC;
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.DisposableBean#destroy()
	 */
	/**
	 * 方法说明：destroy
	 * 
	 * @throws Exception
	 */
	@Transactional(readOnly = false)
	public void destroy() throws Exception
	{
		uumService.createServerLog(getNewServerLog(ServerLog.STOPPING));

	}

	private void setServerlog(ServerLog serverlog)
	{
		LifecycleServiceImpl.serverlog = serverlog;
	}
}
