package com.wt.uum.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hirisun.components.maven.version.Version;
import com.hirisun.components.maven.version.VersionUtils;

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
public class UumCoreVersionUtils {

	/**
	 * 
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(UumCoreVersionUtils.class);

	/**
	 * 
	 */
	private static Version version;

	/**
	 * 
	 */
	private static final String CORE_VERSION = "uum.version";

	/**
	 * 方法说明：
	 * 
	 * @return Version
	 */
	public static Version getVersion() {
		if (version == null) {
			init();
		}
		return version;
	}

	public static void setVersion(Version version) {
		UumCoreVersionUtils.version = version;
	}

	/**
	 * 方法说明：
	 * 
	 */
	public static void init() {
		//osgi 环境下此处要改为VersionUtils.read(UumCoreVersionUtils.class,"uum-core");
		version = VersionUtils.read(UumCoreVersionUtils.class);
		setVersion(version);
		System.setProperty(CORE_VERSION, version.getWholeVersion());
		
		LOGGER.info("UUM Core Verion:" + version.getWholeVersion());
		LOGGER.info("Init System Environment " + CORE_VERSION + "="
				+ version.getWholeVersion());
	}

}
