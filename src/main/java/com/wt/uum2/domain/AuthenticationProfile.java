package com.wt.uum2.domain;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author LiuYX
 *
 */
@XmlAccessorType(XmlAccessType.NONE)
public class AuthenticationProfile implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7701116059501434604L;

	/**
	 * 
	 */
	@XmlElement
	private String uuid;

	/**
	 * 名称
	 */
	private String profileName;

	/**
	 * 描述
	 */
	private String profileDescription;

	/**
	 * 模拟表单名称
	 */
	private String formName;

	/**
	 * 验证失败检查关键字
	 */
	@XmlElement
	private String errorKeyword;

	/**
	 * 目标,弹出,新开窗口
	 */
	private String target;

	/**
	 * 服务器端提前访问地址
	 */
	private String preAccessUrlServerSide;

	/**
	 * 服务器端登录地址
	 */
	@XmlElement
	private String actionUrlServerSide;

	/**
	 * 客户器端提前访问地址
	 */
	private String preAccessUrl;

	/**
	 * 客户端登录地址
	 */
	@XmlElement
	private String actionUrl;

	/**
	 * 提交方法
	 */
	@XmlElement
	private String submitMethod;

	/**
	 * 登录名输入框
	 */
	@XmlElement
	private String inputNameUserid;

	/**
	 * 密码输入框
	 */
	@XmlElement
	private String inputNamePassword;

	/**
	 * 页面编码
	 */
	@XmlElement
	private String charset;

	/**
	 * 应用系统
	 */
	private Application application;

	/**
	 * 应用系统uuid
	 */
	@XmlElement
	private String applicationUUID;

	/**
	 * @return ssssssss
	 */
	public String getApplicationUUID()
	{
		return applicationUUID;
	}

	/**
	 * @param applicationUUID the applicationUUID to set
	 */
	public void setApplicationUUID(String applicationUUID)
	{
		this.applicationUUID = applicationUUID;
	}

	/**
	 * @return ssssssss
	 */
	public String getErrorKeyword()
	{
		return errorKeyword;
	}

	/**
	 * @param errorKeyword the errorKeyword to set
	 */
	public void setErrorKeyword(String errorKeyword)
	{
		this.errorKeyword = errorKeyword;
	}

	/**
	 * @return the actionUrl
	 */
	public String getActionUrl()
	{
		return actionUrl;
	}

	/**
	 * @return the charset
	 */
	public String getCharset()
	{
		return charset;
	}

	/**
	 * @return the formName
	 */
	public String getFormName()
	{
		return formName;
	}

	/**
	 * @return the inputNamePassword
	 */
	public String getInputNamePassword()
	{
		return inputNamePassword;
	}

	/**
	 * @return the inputNameUserid
	 */
	public String getInputNameUserid()
	{
		return inputNameUserid;
	}

	/**
	 * @return the profileDescription
	 */
	public String getProfileDescription()
	{
		return profileDescription;
	}

	/**
	 * @return the profileName
	 */
	public String getProfileName()
	{
		return profileName;
	}

	/**
	 * @return the submitMethod
	 */
	public String getSubmitMethod()
	{
		return submitMethod;
	}

	/**
	 * @return the target
	 */
	public String getTarget()
	{
		return target;
	}

	/**
	 * @return the uuid
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param actionUrl
	 *            the actionUrl to set
	 */
	public void setActionUrl(String actionUrl)
	{
		this.actionUrl = actionUrl;
	}

	/**
	 * @param charset
	 *            the charset to set
	 */
	public void setCharset(String charset)
	{
		this.charset = charset;
	}

	/**
	 * @param formName
	 *            the formName to set
	 */
	public void setFormName(String formName)
	{
		this.formName = formName;
	}

	/**
	 * @param inputNamePassword
	 *            the inputNamePassword to set
	 */
	public void setInputNamePassword(String inputNamePassword)
	{
		this.inputNamePassword = inputNamePassword;
	}

	/**
	 * @param inputNameUserid
	 *            the inputNameUserid to set
	 */
	public void setInputNameUserid(String inputNameUserid)
	{
		this.inputNameUserid = inputNameUserid;
	}

	/**
	 * @param profileDescription
	 *            the profileDescription to set
	 */
	public void setProfileDescription(String profileDescription)
	{
		this.profileDescription = profileDescription;
	}

	/**
	 * @param profileName
	 *            the profileName to set
	 */
	public void setProfileName(String profileName)
	{
		this.profileName = profileName;
	}

	/**
	 * @param submitMethod
	 *            the submitMethod to set
	 */
	public void setSubmitMethod(String submitMethod)
	{
		this.submitMethod = submitMethod;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(String target)
	{
		this.target = target;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	public String getPreAccessUrl()
	{
		return preAccessUrl;
	}

	public void setPreAccessUrl(String preAccessUrl)
	{
		this.preAccessUrl = preAccessUrl;
	}

	public String getPreAccessUrlServerSide()
	{
		return preAccessUrlServerSide;
	}

	public void setPreAccessUrlServerSide(String preAccessUrlServerSide)
	{
		this.preAccessUrlServerSide = preAccessUrlServerSide;
	}

	public String getActionUrlServerSide()
	{
		return actionUrlServerSide;
	}

	public void setActionUrlServerSide(String actionUrlServerSide)
	{
		this.actionUrlServerSide = actionUrlServerSide;
	}

	public Application getApplication()
	{
		return application;
	}

	public void setApplication(Application application)
	{
		this.application = application;
	}

}
