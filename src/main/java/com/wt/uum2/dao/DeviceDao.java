package com.wt.uum2.dao;

import com.wt.uum2.domain.DeviceInfo;

/**
 * <pre>
 * 业务名: 设备
 * 功能说明: 设备crud
 * 编写日期:	2011-10-31
 * 作者:	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface DeviceDao
{

	/**
	 * 方法说明： 创建一个新的设备信息，持久化
	 * 
	 * @param deviceInfo
	 *            设备信息 DeviceInfo
	 */
	void create(DeviceInfo deviceInfo);

	/**
	 * 方法说明： 删除此设备信息
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	void delete(DeviceInfo deviceInfo);

	/**
	 * 方法说明： 更新
	 * 
	 * @param deviceInfo
	 *            deviceInfo
	 */
	void update(DeviceInfo deviceInfo);

	// /**
	// * 方法说明：根据UUID查找设备信息
	// *
	// * @param uuid UUID
	// * @return DeviceInfo
	// */
	// DeviceInfo get(String uuid);

	/**
	 * 方法说明： 根据设备id和用户id查询设备信息
	 * 
	 * @param deviceId
	 *            设备id
	 * @param userId
	 *            用户id
	 * @return DeviceInfo
	 */
	DeviceInfo getByDeviceIdAndUserId(String deviceId, String userId);

	/**
	 * 方法说明：保存或更新
	 * 
	 * @param deviceInfo
	 *            DeviceInfo
	 */
	void saveOrUpdate(DeviceInfo deviceInfo);

}
