package com.wt.hea.webbuilder.struts.form;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;

import com.wt.hea.webbuilder.model.ThemeDefinition;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 主题定义传输form
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class ThemeDefinitionForm extends ActionForm {
	
	/**
	 * 主题定义
	 */
	private ThemeDefinition themeDefinition = new ThemeDefinition() ;

	/**
	 * 上传文件
	 */
	private FormFile file ;
	/**
	 * 资源地址
	 */
	private String sourcePath ;
	
	public String getSourcePath() {
		return sourcePath;
	}

	public void setSourcePath(String sourcePath) {
		this.sourcePath = sourcePath;
	}

	public ThemeDefinition getThemeDefinition() {
		return themeDefinition;
	}

	public void setThemeDefinition(ThemeDefinition themeDefinition) {
		this.themeDefinition = themeDefinition;
	}

	public FormFile getFile() {
		return file;
	}

	public void setFile(FormFile file) {
		this.file = file;
	}
}
