package com.wt.uum2.service.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum.device.AuthCodeGenerator;
import com.wt.uum.device.AuthStatus;
import com.wt.uum.device.DeviceStatus;
import com.wt.uum2.dao.DeviceDao;
import com.wt.uum2.domain.DeviceInfo;
import com.wt.uum2.service.DeviceService;

/**
 * <pre>
 * 业务名: DeviceService 实现
 * 功能说明: 
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeviceServiceImpl implements DeviceService
{

	private static final Log LOGGER = LogFactory.getLog(DeviceServiceImpl.class);

	/**
	 * DeviceDao
	 */
	private DeviceDao deviceDao;

	/**
	 * AuthCodeGenerator
	 */
	private AuthCodeGenerator authCodeGenerator;

	public void setDeviceDao(DeviceDao deviceDao)
	{
		this.deviceDao = deviceDao;
	}

	/**
	 * 方法说明：create
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	public void create(DeviceInfo deviceInfo)
	{
		deviceDao.create(deviceInfo);

	}

	/**
	 * 方法说明：update
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	public void update(DeviceInfo deviceInfo)
	{
		deviceDao.update(deviceInfo);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DeviceService#auth(java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：auth
	 * 
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param authCode
	 *            authCode
	 * @return AuthStatus
	 */
	@Transactional
	public AuthStatus auth(String userId, String deviceId, String authCode)
	{

		DeviceInfo deviceInfo = deviceDao.getByDeviceIdAndUserId(deviceId, userId);

		if (deviceInfo != null && StringUtils.equals(authCode, deviceInfo.getAuthCode())) {
			LOGGER.debug("Validation successfull");

			deviceInfo.addCount();

			update(deviceInfo);

			return AuthStatus.OK;
		}

		LOGGER.debug("Validation failed");

		return AuthStatus.FAILER;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DeviceService#saveOrUpdate(com.wt.uum2.domain.DeviceInfo)
	 */
	/**
	 * 方法说明：saveOrUpdate
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	@Transactional
	public void saveOrUpdate(DeviceInfo deviceInfo)
	{
		deviceDao.saveOrUpdate(deviceInfo);

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DeviceService#generateAuthCode(com.wt.uum2.domain.DeviceInfo)
	 */
	/**
	 * 方法说明：generateAuthCode
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	@Transactional
	public void generateAuthCode(DeviceInfo deviceInfo)
	{

		if (deviceInfo != null) {

			deviceInfo.setAuthCode(getAuthCodeGenerator().genCode(deviceInfo));
		}

	}

	public AuthCodeGenerator getAuthCodeGenerator()
	{
		return authCodeGenerator;
	}

	public void setAuthCodeGenerator(AuthCodeGenerator authCodeGenerator)
	{
		this.authCodeGenerator = authCodeGenerator;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DeviceService#getByDeviceIdAndUserId(java.lang.String, java.lang.String)
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
		return deviceDao.getByDeviceIdAndUserId(deviceId, userId);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.DeviceService#getAuthCode(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getAuthCode
	 * 
	 * @param userId
	 *            userId
	 * @param name
	 *            name
	 * @param version
	 *            version
	 * @param deviceId
	 *            deviceId
	 * @return String
	 */
	@Transactional
	public String getAuthCode(String userId, String name, String version, String deviceId)
	{

		DeviceInfo deviceInfo = getByDeviceIdAndUserId(deviceId, userId);

		if (deviceInfo == null) {
			deviceInfo = new DeviceInfo(userId, deviceId, name, version);
			deviceInfo.setStatus(DeviceStatus.REGISTERED);
		}

		deviceInfo.setModified(new Date());

		generateAuthCode(deviceInfo);

		deviceInfo.addCount();

		saveOrUpdate(deviceInfo);

		return deviceInfo.getAuthCode();
	}

}
