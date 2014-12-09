package com.wt.uum2.domain;

import java.util.Set;

import javax.xml.bind.annotation.XmlElement;


/**
 * @author Alex
 * 
 *         应用
 */
public class Application extends Resource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128793L;

	/**
	 * 应用名
	 */
	@XmlElement
	private String name;

	/**
	 * 排序号
	 */
	private Long order;

	/**
	 * 系统标识
	 */
	@XmlElement
	private String code;
	/**
	 * 系统简要概述
	 */
	private String remark;
	/**
	 * 扩展
	 */
	private Set<AuthenticationProfile> authenticationProfile;

	/**
	 * 构造函数
	 */
	public Application()
	{
		super();
		setType(3);
		order = 0L;
	}

	/**
	 * @return ssssssss
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ssssssss
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return ssssssss
	 */
	public String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public Long getOrder()
	{
		return order;
	}

	public void setOrder(Long order)
	{
		this.order = order;
	}

	public Set<AuthenticationProfile> getAuthenticationProfile()
	{
		return authenticationProfile;
	}

	public void setAuthenticationProfile(Set<AuthenticationProfile> authenticationProfile)
	{
		this.authenticationProfile = authenticationProfile;
	}

}