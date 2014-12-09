package com.wt.uum2.dao.impl;

import org.hibernate.criterion.Restrictions;

import nak.nsf.dao.support.BaseDaoSupport;

import com.wt.uum.device.DeviceStatus;
import com.wt.uum2.dao.DeviceDao;
import com.wt.uum2.domain.DeviceInfo;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceDaoImpl extends BaseDaoSupport<DeviceInfo> implements DeviceDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DeviceDao#create(com.wt.uum2.domain.DeviceInfo)
	 */
	/**
	 * 方法说明：create
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	public void create(DeviceInfo deviceInfo)
	{
		save(deviceInfo);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DeviceDao#getByDeviceIdAndUserId(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getByDeviceIdAndUserId
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param userId
	 *            userId
	 * @return DeviceInfo
	 */
	public DeviceInfo getByDeviceIdAndUserId(String deviceId, String userId)
	{
		return (DeviceInfo) getSession().createCriteria(DeviceInfo.class)
				.add(Restrictions.eq("deviceId", deviceId)).add(Restrictions.eq("userId", userId))
				.add(Restrictions.eq("status", DeviceStatus.REGISTERED)).uniqueResult();
	}

}
