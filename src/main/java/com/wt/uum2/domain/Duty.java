package com.wt.uum2.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "duty")
public class Duty extends Resource
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6406022576824672149L;

	@XmlElement
	private String id;
	
	/**
	 * 部门层次
	 */
	@XmlElement
	private int level;

	/**
	 * 机构名
	 */
	@XmlElement
	private String name;

public Duty(){
	super();
	setType(5);
}
	
	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
		setOrder((long)level);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

}
