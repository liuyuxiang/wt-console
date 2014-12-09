package com.wt.hea.common.action;

import org.springframework.web.struts.DispatchActionSupport;

import com.wt.hea.common.infrastructure.logger.Logger;
/**
 * Action抽象基类
 * Web层所有控制器都继承此抽象类
 * @see org.springframework.web.struts.DispatchActionSupport
 * @author 袁明敏
 * 
 */
@SuppressWarnings("deprecation")
public abstract class BaseDispatchAction extends DispatchActionSupport{
	/**
	 * action层内的日志对象
	 */
	protected Logger loggerService ;

	public void setLoggerService(Logger loggerService) {
		this.loggerService = loggerService;
	}
	
	
}
