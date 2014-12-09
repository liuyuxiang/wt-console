package com.wt.hea.rbac.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA. User: noah Date: 6/28/12 Time: 10:21 PM
 * <p/>
 * 导航节点图标，小图标，中图标，大图标 onMouseOver时的图标，存放图标的相关内容
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "icon")
public class IndexIcon implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1708360255638740450L;
	private String smaPath;
	private String smaOnMovePath;

	@XmlElement(name = "midPath")
	private String midPath;
	@XmlElement(name = "midOnMovePath")
	private String midOnMovePath;

	private String bigPath;
	private String bigOnMovePath;

	public String getSmaPath()
	{
		return smaPath;
	}

	public void setSmaPath(String smaPath)
	{
		this.smaPath = smaPath;
	}

	public String getMidPath()
	{
		return midPath;
	}

	public void setMidPath(String midPath)
	{
		this.midPath = midPath;
	}

	public String getBigPath()
	{
		return bigPath;
	}

	public void setBigPath(String bigPath)
	{
		this.bigPath = bigPath;
	}

	public String getSmaOnMovePath()
	{
		return smaOnMovePath;
	}

	public void setSmaOnMovePath(String smaOnMovePath)
	{
		this.smaOnMovePath = smaOnMovePath;
	}

	public String getMidOnMovePath()
	{
		return midOnMovePath;
	}

	public void setMidOnMovePath(String midOnMovePath)
	{
		this.midOnMovePath = midOnMovePath;
	}

	public String getBigOnMovePath()
	{
		return bigOnMovePath;
	}

	public void setBigOnMovePath(String bigOnMovePath)
	{
		this.bigOnMovePath = bigOnMovePath;
	}

}
