package com.wt.uum2.service;

import com.wt.uum.device.AuthStatus;
import com.wt.uum2.domain.DeviceInfo;

/**
 * <pre>
 * 业务名: 设备验证
 * 功能说明: 设备验证的服务接口
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DeviceService
{

	/**
	 * 方法说明： 保存活更新
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	void saveOrUpdate(DeviceInfo deviceInfo);

	/**
	 * 方法说明：根据设备ID和用户ID查询设备信息
	 * 
	 * @param deviceId
	 *            deviceId
	 * @param userId
	 *            用户ID
	 * @return DeviceInfo
	 */
	DeviceInfo getByDeviceIdAndUserId(String deviceId, String userId);

	/**
	 * 方法说明：根据设备的相关验证信息进行验证，返回状态码
	 * 
	 * @param userId
	 *            userId
	 * @param deviceId
	 *            deviceId
	 * @param authCode
	 *            authCode
	 * @return AuthStatus
	 */
	AuthStatus auth(String userId, String deviceId, String authCode);

	/**
	 * 方法说明：获取 验证码,保存到 DeviceInfo
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	void generateAuthCode(DeviceInfo deviceInfo);

	/**
	 * 方法说明： 根据信息生成验证码
	 * 
	 * @param userId
	 *            userId
	 * @param name
	 *            name
	 * @param version
	 *            version
	 * @param deviceId
	 *            deviceId
	 * @return 验证码
	 */
	String getAuthCode(String userId, String name, String version, String deviceId);

	// /**
	// * 方法说明： 创建一个新的设备信息，持久化
	// *
	// * @param deviceInfo 设备信息 DeviceInfo
	// */
	// void create(DeviceInfo deviceInfo);
	//
	// /**
	// * 方法说明： 删除此设备信息
	// *
	// * @param deviceInfo DeviceInfo
	// */
	// void delete(DeviceInfo deviceInfo);
	//
	// /**
	// * 方法说明： 更新
	// *
	// * @param deviceInfo
	// */
	// void update(DeviceInfo deviceInfo);

}
