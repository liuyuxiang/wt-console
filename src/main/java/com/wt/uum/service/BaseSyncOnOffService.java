package com.wt.uum.service;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.hirisun.components.other.runtime.RuntimeResolver;
import com.wt.uum2.bean.Setting;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-22
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class BaseSyncOnOffService implements SyncOnOffService
{
	/**
	 * 
	 */
	private Cache syncOnOffCache;
	/**
	 * 
	 */
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 
	 */
	private static final String CACHEKEY = "syncOnOff";
	/**
	 * 是否已经初始化
	 */
	private boolean initialized = false;

	/* (non-Javadoc)
	 * @see com.wt.uum.service.SyncOnOffService#init(com.wt.uum2.bean.Setting)
	 */
	/**
	 * 方法说明：init
	 * 
	 * @param setting
	 *            setting
	 */
	public void init(Setting setting)
	{
		if (!initialized) {
			if (RuntimeResolver.isDepMode() && "on".equalsIgnoreCase(setting.getSyncOnOff())) {
				syncOnOffCache.put(new Element(CACHEKEY, true));
				logger.info("The sync is on!");
			} else {
				syncOnOffCache.put(new Element(CACHEKEY, false));
				logger.warn("Please make sure that the sync is off!");
			}
			initialized = true;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum.service.SyncOnOffService#isSyncOn()
	 */
	/**
	 * 方法说明：isSyncOn
	 * 
	 * @return boolean
	 */
	public boolean isSyncOn()
	{
		boolean isSyncOn = false;
		Element element = syncOnOffCache.get(CACHEKEY);
		if (element != null && StringUtils.isNotBlank(element.getValue().toString())) {
			return Boolean.valueOf(element.getValue().toString());
		}
		return isSyncOn;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum.service.SyncOnOffService#syncOff()
	 */
	/**
	 * 方法说明：syncOff
	 * 
	 */
	public void syncOff()
	{
		syncOnOffCache.put(new Element(CACHEKEY, false));
		logger.info("The sync is off!");
	}

	/* (non-Javadoc)
	 * @see com.wt.uum.service.SyncOnOffService#syncOn()
	 */
	/**
	 * 方法说明：syncOn
	 * 
	 */
	public void syncOn()
	{
		syncOnOffCache.put(new Element(CACHEKEY, true));
		logger.info("The sync is on!");
	}

	public void setSyncOnOffCache(Cache syncOnOffCache)
	{
		this.syncOnOffCache = syncOnOffCache;
	}

}
