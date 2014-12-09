package com.wt.uum2.quartz;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.UserTempLog;

/**
 * <pre>
 * 业务名: 反向同步实现
 * 功能说明: 默认版本的反向同步实现
 * 编写日期:	2012-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SynJobDetail extends SyncListenerAbstract
{

	/**
	 * 方法说明：入口
	 * 
	 */
	public synchronized void handle()
	{
		DepartmentSyncFrom();
		UserSyncFrom();
	}

	/**
	 * 方法说明：获取公司编码
	 * 
	 * @param deptTemp deptTemp
	 * @return String
	 */
	protected String getDeptOrgCode(DepartmentTempLog deptTemp)
	{
		return StringUtils.defaultIfEmpty(deptTemp.getDeptOrgCode(), deptTemp.getDeptCode());
	}

	/**
	 * 方法说明：获取部门信息，如果获取失败则将根节点作为部门返回
	 * 
	 * @param deptCode deptCode
	 * @return Department
	 */
	protected Department getPrimaryDepartment(String deptCode)
	{
		Department dept = getUumService().getDepartmentByDeptCode(deptCode);
		if (dept == null) {
			dept = getUumService().getDepartmentRoot();
		}
		return dept;
	}

	/**
	 * 方法说明：获取用户状态
	 * 
	 * @param userTemp userTemp
	 * @return ResourceStatus
	 */
	protected ResourceStatus getUserStatus(UserTempLog userTemp)
	{
		String code = userTemp.getReducingCode();
		if (StringUtils.equals("1", code)) {
			return ResourceStatus.DELETE_LOGIC;
		}
		return ResourceStatus.NORMAL;
	}

	/**
	 * 方法说明：获取用户密码，如果password为空则返回默认密码
	 * 
	 * @param userTemp userTemp
	 * @return String
	 */
	public String getPlainPassword(UserTempLog userTemp)
	{
		if (StringUtils.isNotBlank(userTemp.getPassword())) {
			return userTemp.getPassword();//new String(Base64.decodeBase64(userTemp.getPassword().getBytes()));
		}
		return InitParameters.getUserPassword();
	}

	@Override
	protected String getPropertieFileName()
	{
		return "syncFrom-" + ProjectResolver.getId() + ".properties";
	}

}
