package com.wt.hea.rbac.model;

import java.io.Serializable;

import com.hirisun.hea.api.domain.Group;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: hea组扩展类
 * 编写日期:	2010-5-11
 * 作者:LiYi
 * 
 * </pre>
 */
public class GroupExtends implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -61188443627756778L;
	/**
	 * 父组ID
	 */
	public String parentId;
	/**
	 * 组
	 */
	public Group group;
}