package com.wt.hea.common.dao.support;

/**
 *  数据源配置异常,如：没有显示配置默认数据源
 * @author 袁明敏
 *
 */
public class DataSourceConfigException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2209875865736590281L;

	/**
	 * 
	 */
	public DataSourceConfigException() {
		  
	 }
	/**
	 * 方法说明：覆盖父类方法
	 * @param message 异常信息
	 */
	public DataSourceConfigException(String message) {
		  super(message);
	 }
	
	/**
	 * 方法说明：覆盖父类方法
	 * @param cause 异常对象
	 */
	 public DataSourceConfigException(Throwable cause) {
		 super(cause);
	 }
	 
	 /**
	  * 方法说明：覆盖父类方法
	  * @param message 异常信息
	  * @param cause 异常对象
	  */
	 public DataSourceConfigException(String message,Throwable cause){
		 super(message,cause);
	 }
	 
}
