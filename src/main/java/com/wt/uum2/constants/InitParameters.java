package com.wt.uum2.constants;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;

import com.hirisun.components.other.project.ProjectResolver;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-23
 * 作者:	Liu yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class InitParameters
{

	/**
	 * 方法说明：初始化参数
	 * 
	 * @param propName
	 *            propName
	 * @return String
	 */
	public static String para(String propName)
	{
		Properties prop = new Properties();
		String propertiesName = "init.properties";
		String projectId = String.valueOf(ProjectResolver.getId());
		if (!projectId.equals("null")) {
			propertiesName = "init_" + projectId + ".properties";
		}
		InputStream propIn = InitParameters.class.getResourceAsStream(propertiesName);
		String value = "";
		try {
			prop.load(propIn);
			value = prop.getProperty(propName);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (propIn != null) {
				try {
					propIn.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}

	/**
	 * 方法说明：获得根组名字
	 * 
	 * @return String
	 */
	public static String getRootGroupName()
	{
		return InitParameters.para("rootGroupName");
	}

	/**
	 * 方法说明：获得根组编码
	 * 
	 * @return String
	 */
	public static String getRootGroupCode()
	{
		return InitParameters.para("rootGroupCode");
	}

	/**
	 * 方法说明：获得超级管理员编码
	 * 
	 * @return String
	 */
	public static String getSuperGroupCode()
	{
		return InitParameters.para("superGroupCode");
	}

	/**
	 * 方法说明：获得根部门唯一标识
	 * 
	 * @return String
	 */
	public static String getRootDepartmentCode()
	{
		return InitParameters.para("rootDepartmentCode");
	}

	/**
	 * 方法说明：获得根部门名称
	 * 
	 * @return String
	 */
	public static String getRootDepartmentName()
	{
		return InitParameters.para("rootDepartmentName");
	}

	/**
	 * 方法说明：获得根节点应用组编码
	 * 
	 * @return String
	 */
	public static String getRootApplicationGroup()
	{
		return InitParameters.para("superApplicationGroup");
	}

	/**
	 * 方法说明：获得根节点应用组编码
	 * 
	 * @return String
	 */
	public static String getSGCCApplicationGroup()
	{
		return InitParameters.para("sgccApplicationGroup");
	}

	/**
	 * 方法说明：用户初始化密码
	 * 
	 * @return String
	 */
	public static String getUserPassword()
	{
		return InitParameters.para("defaultUserPassword");
	}

	/**
	 * 方法说明：是否开启IDS同步(主要是北供在用)
	 * 
	 * @return boolean
	 */
	public static boolean getIDSTurnOn()
	{
		return "true".equalsIgnoreCase(InitParameters.para("IDSTurnOn"));
	}

	/**
	 * 方法说明：是否显示应用系统密码显示功能
	 * 
	 * @return String
	 */
	public static String getShowAppPwdTurnOn()
	{
		return InitParameters.para("ShowAppPwdTurnOn");
	}

	/**
	 * 方法说明：是否加密密码
	 * 
	 * @return String
	 */
	public static String getEncodePassTurnOn()
	{
		return InitParameters.para("encodePassTurnOn");
	}

	/**
	 * 方法说明：是否MD5加密密码
	 * 
	 * @return String
	 */
	public static String getMD5EncodePassTurnOn()
	{
		return InitParameters.para("md5EncodePassTurnOn");
	}

	/**
	 * 方法说明：应用系统状态编码
	 * 
	 * @return String
	 */
	public static String getAppStatusCode()
	{
		return InitParameters.para("appStatusCode");
	}

	/**
	 * 方法说明：应用系统账户编码
	 * 
	 * @return String
	 */
	public static String getAppAccountLocal()
	{
		return InitParameters.para("appAcountLocal");
	}

	/**
	 * 方法说明：应用系统密码编码
	 * 
	 * @return String
	 */
	public static String getAppPwdLocal()
	{
		return InitParameters.para("appPwdLocal");
	}

	/**
	 * 方法说明：应用系统是否可用编码
	 * 
	 * @return String
	 */
	public static String getAppLoginEnableLocal()
	{
		return InitParameters.para("appLoginEnableLocal");
	}

	/**
	 * 方法说明：得到初始化超级管理员名字
	 * 
	 * @return String
	 */
	public static String getSuperUserName()
	{
		return InitParameters.para("superUserName");
	}

	/**
	 * 方法说明：得到初始化超级管理员编码
	 * 
	 * @return String
	 */
	public static String getSuperUserCode()
	{
		return InitParameters.para("superUserCode");
	}

	/**
	 * 方法说明：判断一个用户是否可存在于多个个部门
	 * 
	 * @return boolean
	 */
	public static boolean isMultiDept()
	{
		return "true".equalsIgnoreCase(InitParameters.para("isMultiDept"));
	}

	/**
	 * 方法说明：判断一个新增用户是否发送邮件给相关人员
	 * 
	 * @return Boolean
	 */
	public static Boolean isSendMailToMailManager()
	{
		return "true".equalsIgnoreCase(InitParameters.para("notifyManager"));
	}

	/**
	 * 方法说明：无组织机构人员列表
	 * 
	 * @return String
	 */
	public static String getNoDepartment()
	{
		return InitParameters.para("noDeptCode");
	}

	/**
	 * 方法说明：是否需要用户密码加密
	 * 
	 * @return String
	 */
	public static String getPlainPassword()
	{
		return InitParameters.para("plainPassword");
	}

	/**
	 * 方法说明：是否需要用户密码加密
	 * 
	 * @return boolean
	 */
	public static boolean isPlainPassword()
	{
		return "true".equalsIgnoreCase(InitParameters.para("plainPassword"));
	}

	/**
	 * 方法说明：是否需要统一展现用户用户列表
	 * 
	 * @return String
	 */
	public static String getMacroUserList()
	{
		return InitParameters.para("macroUserList");
	}

	/**
	 * 方法说明：是否需要在用户修改 密码的同时修改用户所有应用系统密码
	 * 
	 * @return String
	 */
	public static String getChangeAllAppPwd()
	{
		return InitParameters.para("changeAllAppPwd");
	}

	/**
	 * 方法说明：是否默认生成部门编码
	 * 
	 * @return String
	 */
	public static String getCreateDefaultDeptCode()
	{
		return InitParameters.para("createDefaultDeptCode");
	}

	/**
	 * 方法说明：是否是重庆组授权模式
	 * 
	 * @return boolean
	 */
	public static boolean isCqGroupAuthor()
	{
		return "true".equalsIgnoreCase(InitParameters.para("cqGroupAuthor"));
	}

	/**
	 * 方法说明：是否是重庆组授权模式
	 * 
	 * @return String
	 */
	public static String modifyUserGroupCode()
	{
		return InitParameters.para("modifyUserGroupCode");
	}

	/**
	 * 方法说明：是否在用户页签操作部门
	 * 
	 * @return String
	 */
	public static String getModifyDeptInUser()
	{
		return InitParameters.para("modifyDeptInUser");
	}

	/**
	 * 方法说明：是否在用户创建时产生管理组
	 * 
	 * @return boolean
	 */
	public static boolean isCreateDefaultAdminUser()
	{
		return "true".equalsIgnoreCase(InitParameters.para("createDefaultAdminUser"));
	}

	/**
	 * 方法说明：是否带权限搜索用户
	 * 
	 * @return String
	 */
	public static String getSeachUserByLogin()
	{
		return InitParameters.para("seachUserByLogin");
	}

	/**
	 * 方法说明：信通公司编码
	 * 
	 * @return String
	 */
	public static String getSgsDeptCode()
	{
		return InitParameters.para("sgsDeptCode");
	}

	/**
	 * 方法说明：获取可登陆系统并进行管理的组
	 * 
	 * @return String
	 */
	public static List<String> getManagerGroupCode()
	{
		String list = InitParameters.para("managerGroups");
		
		if(StringUtils.isBlank(list)){
			list = "";
		}
		
		return Arrays.asList(list.split(","));
	}

}
