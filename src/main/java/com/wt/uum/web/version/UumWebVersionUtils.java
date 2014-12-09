package com.wt.uum.web.version;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hirisun.components.maven.version.Version;
import com.hirisun.components.maven.version.web.WebVersionUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-12
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UumWebVersionUtils {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UumWebVersionUtils.class);
	/**
	 * 
	 */
	private static Version version;

	/**
	 * 
	 */
	private static final String WEB_VERSION = "uum.version";

	public static Version getVersion() {
		return version;
	}

	public static void setVersion(Version version) {
		UumWebVersionUtils.version = version;
	}

	/**
	 * 方法说明：
	 * 
	 * @param servletContext
	 *            servletContext
	 */
	public static void init(ServletContext servletContext) {
		version = WebVersionUtils.read(servletContext);
		setVersion(version);
		System.setProperty(WEB_VERSION, version.getWholeVersion());

		LOGGER.info("UUM Web Verion:" + version.getWholeVersion());
		LOGGER.info("Init System Environment " + WEB_VERSION + "="
				+ version.getWholeVersion());
	}

}
