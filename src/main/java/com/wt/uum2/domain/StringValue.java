package com.wt.uum2.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Richard
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@XmlRootElement(name = "attr")
@XmlAccessorType(XmlAccessType.FIELD)
public class StringValue extends AttributeValue
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128792L;

	@XmlAttribute
	private String value;

	/**
	 * 
	 */
	public StringValue()
	{
		super();
	}

	/**
	 * @param value
	 *            value
	 */
	public StringValue(String value)
	{
		super();
		this.value = value;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
