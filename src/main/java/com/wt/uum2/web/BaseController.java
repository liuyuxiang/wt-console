package com.wt.uum2.web;

import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * <pre>
 * 业务名: controller
 * 功能说明: Controller基类
 * 编写日期:	2011-7-13
 * 作者:	郭天赐
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class BaseController {
	
	/**
	 * log
	 */
	protected Logger logger = LogManager.getLogger(getClass().getName());
	
	protected static final String ERROR_URL = "/err/exception";
	
	protected final static String NOTLOGIN = "/tasklist/nofound";
	/**
	 * Exception attribute name
	 */
	protected static final String EXCEPTION_ATTRIBUTE_NAME = "exception";
	
	/**
	 * 数据库异常信息
	 */
	protected static final String SQL_EXCEPTION = "数据库异常";
	
	/**
	 * 一般(非数据库)异常信息
	 */
	protected static final String EXCEPTION = "异常";

	/**
	 * Constructor
	 */
	public BaseController() {
		super();
	}
	
	
	/**
	 * 有异常抛出时将其捕获，若为数据库异常，将其放入ModelAndView中，以便在页面显示。
	 * @param ex 捕获的异常
	 * @return ModelAndView
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception ex) {
		logger.error(ex, ex);
		ModelAndView mv = new ModelAndView(ERROR_URL);

		String msg = getCauseMsg(ex);
		String stackTrace=formatMsg(ExceptionUtils.getFullStackTrace(ex));
		mv.addObject(EXCEPTION_ATTRIBUTE_NAME, new ErrorMessage(msg, stackTrace));
		
		return mv;
	}

	/**
	 * Format, replaces \n
	 * @param stackTrace the string
	 * @return Formated string
	 */
	private String formatMsg(String stackTrace) {
		if (stackTrace != null) {
			return StringUtils.replace(stackTrace, "\n", "<br/>");
		}
		return null;
	}


	/**
	 * @param e
	 *            Exception
	 * @return The cause SQLException or null in case of no SQLException in the
	 *         cause chain
	 */
	protected String getCauseMsg(Throwable e) {
		if (e != null) {
			if (e instanceof SQLException) {
				return SQL_EXCEPTION;
			} else{
				return getCauseMsg(e.getCause());
			}
		}
		return EXCEPTION;
	}
	
	/**
	 * <pre>
	 * 业务名: 以诚信息封装
	 * 功能说明: 以诚信息封装
	 * 编写日期:	2011-7-13
	 * 作者:	郭天赐
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	public static class ErrorMessage{
		/**
		 * 异常信息
		 */
		private String message;
		/**
		 * 异常堆栈
		 */
		private String stackTrace;
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		public String getStackTrace() {
			return stackTrace;
		}
		public void setStackTrace(String stackTrace) {
			this.stackTrace = stackTrace;
		}
		/**
		 * Constructor
		 * @param message 异常信息
		 * @param stackTrace 异常堆栈
		 */
		public ErrorMessage(String message, String stackTrace) {
			super();
			this.message = message;
			this.stackTrace = stackTrace;
		}
		
	}
}
