package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.wt.hea.webbuilder.model.TemplateLayout;
/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 模板布局form
 * 编写日期:	2011-3-29
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class TemplateLayoutForm extends ActionForm {
	/**
	 * 模板布局bean
	 */
	private TemplateLayout templateLayout = new TemplateLayout();

	/**
	 * 模板文件
	 */
	private FormFile file ;
	/**
	 * 资源地址
	 */
	private String sourcePath ;
	
	/**
	 * 模板id
	 */
	private String templateId ;
	/**
	 * 站点id
	 */
	private String siteManageId ;
	
	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	
	public TemplateLayout getTemplateLayout() {
		return templateLayout;
	}

	public void setTemplateLayout(TemplateLayout templateLayout) {
		this.templateLayout = templateLayout;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public String getSiteManageId() {
		return siteManageId;
	}

	public void setSiteManageId(String siteManageId) {
		this.siteManageId = siteManageId;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
	
}
