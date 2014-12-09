package com.wt.uum2.domain;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Richard
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TextValue extends AttributeValue
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128792L;

	private String value;

	/**
	 * 
	 */
	public TextValue()
	{
		super();
	}

	@Override
	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
