package com.wt.hea.common.dao.support;

/**
 * 用于设置和获取上下文,切换数据源
 * <p>
 *	调用范列:ContextHolder.set("dataSource1")
 * </p>
 * @author 袁明敏
 *
 */
public class ContextHolder {
	/**
	 * 线程安全上下文对象contextHolder
	 */
	private static final ThreadLocal<String>  CONTEXTHOLDER = new ThreadLocal<String>();

	/**
	 * 
	 * 方法说明：设置数据源标识
	 *
	 * @param type 数据源标识
	 */
	public static void setType(String type) {
		   CONTEXTHOLDER.set(type);
	}
	
	/**
	 * 方法说明：获取数源标识
	 *
	 * @return 数据源标识字符串
	 */
	 public static String getType() {
	   return (String) CONTEXTHOLDER.get();
	 }
		 
	 /**
	  * 
	  * 方法说明：清除数据源标识
	  *
	  */
	 public static void clearType() {
	   CONTEXTHOLDER.remove();
	 }
}
