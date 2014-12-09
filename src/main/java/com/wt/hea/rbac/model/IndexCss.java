package com.wt.hea.rbac.model;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created with IntelliJ IDEA. User: ROCFLY ZHANGE Date: 5/2/13 Time: 02:50 PM
 * <p/>
 * 导航节点样式表 onMouseOver时样式表
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "css")
public class IndexCss implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 244450016297282431L;
	private String style;
	private String styleonMove;
	@XmlElement(name = "styleClass")
	private String styleClass;
	@XmlElement(name = "styleClassOnMver")
	private String styleClassOnMver;

	public String getStyle()
	{
		return style;
	}

	public void setStyle(String style)
	{
		this.style = style;
	}

	public String getStyleonMove()
	{
		return styleonMove;
	}

	public void setStyleonMove(String styleonMove)
	{
		this.styleonMove = styleonMove;
	}

	public String getStyleClass()
	{
		return styleClass;
	}

	public void setStyleClass(String styleClass)
	{
		this.styleClass = styleClass;
	}

	public String getStyleClassOnMver()
	{
		return styleClassOnMver;
	}

	public void setStyleClassOnMver(String styleClassOnMver)
	{
		this.styleClassOnMver = styleClassOnMver;
	}

}
