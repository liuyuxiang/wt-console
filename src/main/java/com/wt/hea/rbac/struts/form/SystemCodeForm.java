package com.wt.hea.rbac.struts.form;

import org.apache.struts.action.ActionForm;

import com.wt.hea.common.model.Page;
import com.wt.hea.common.model.SystemCode;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-7-28
 * 作者:	mazhaohui
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@SuppressWarnings("serial")
public class SystemCodeForm extends ActionForm{
	/**
	 * 系统常量bean
	 */
	private SystemCode systemCode = new SystemCode();
	/**
	 * 分页组件Page
	 */
	Page<SystemCode> page =  new Page<SystemCode>(5,1);

	public SystemCode getSystemCode(){
		return systemCode;
	}

	public void setSystemCode(SystemCode systemCode){
		this.systemCode = systemCode;
	}
	
	public Page<SystemCode> getPage() {
		return page;
	}

	public void setPage(Page<SystemCode> page) {
		this.page = page;
	}

}
