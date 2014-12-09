package com.wt.hea.rbac.model;

import java.io.Serializable;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-6-18
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class IndexGroups implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1510858731620938431L;

	/**
	 * 
	 */
	private String indexuuid;

	/**
	 * 
	 */
	private String groupuuid;

	public String getIndexuuid()
	{
		return indexuuid;
	}

	public void setIndexuuid(String indexuuid)
	{
		this.indexuuid = indexuuid;
	}

	public String getGroupuuid()
	{
		return groupuuid;
	}

	public void setGroupuuid(String groupuuid)
	{
		this.groupuuid = groupuuid;
	}
}
