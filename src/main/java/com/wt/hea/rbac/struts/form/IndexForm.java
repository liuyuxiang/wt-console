package com.wt.hea.rbac.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.wt.hea.rbac.model.Index;

/**
 * 
 * <pre>
 * 业务名:WEB层 指标数据传输对象Bean
 * 功能说明: 
 * 编写日期:	2011-4-1
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class IndexForm extends ActionForm
{
	/**
	 * index
	 */
	private Index index = new Index();

	/**
	 * ID
	 */
	private String indexId;
	/**
	 * 组ID串
	 */
	private String groupIdsStr;
	/**
	 * 选中的ID串
	 */
	private String selectedGroupIdsStr;

	/**
	 * 指标ID串
	 */
	private String indexIdsStr;

	/**
	 * 选中的指标ID串
	 */
	private String selectedIndexIdsStr;

	/**
	 * 已出现的id
	 */
	private String bubs;

	/**
	 * 组ID
	 */
	private String groupId;
	
	/**
	 * 创建时间
	 */
	private String creatDate;

	public Index getIndex()
	{
		return index;
	}

	public void setIndex(Index index)
	{
		this.index = index;
	}

	/**
	 * file
	 */
	private FormFile file;

	public FormFile getFile()
	{
		return file;
	}

	public void setFile(FormFile file)
	{
		this.file = file;
	}

	public String getIndexId()
	{
		return indexId;
	}

	public void setIndexId(String indexId)
	{
		this.indexId = indexId;
	}

	public String getGroupIdsStr()
	{
		return groupIdsStr;
	}

	public void setGroupIdsStr(String groupIdsStr)
	{
		this.groupIdsStr = groupIdsStr;
	}

	public String getSelectedGroupIdsStr()
	{
		return selectedGroupIdsStr;
	}

	public void setSelectedGroupIdsStr(String selectedGroupIdsStr)
	{
		this.selectedGroupIdsStr = selectedGroupIdsStr;
	}

	public String getIndexIdsStr()
	{
		return indexIdsStr;
	}

	public void setIndexIdsStr(String indexIdsStr)
	{
		this.indexIdsStr = indexIdsStr;
	}

	public String getSelectedIndexIdsStr()
	{
		return selectedIndexIdsStr;
	}

	public void setSelectedIndexIdsStr(String selectedIndexIdsStr)
	{
		this.selectedIndexIdsStr = selectedIndexIdsStr;
	}

	public String getGroupId()
	{
		return groupId;
	}

	public void setGroupId(String groupId)
	{
		this.groupId = groupId;
	}

	public String getBubs()
	{
		return bubs;
	}

	public void setBubs(String bubs)
	{
		this.bubs = bubs;
	}

	public String getCreatDate()
	{
		return creatDate;
	}

	public void setCreatDate(String creatDate)
	{
		this.creatDate = creatDate;
	}

}
