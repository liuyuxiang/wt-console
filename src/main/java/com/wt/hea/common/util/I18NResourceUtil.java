package com.wt.hea.common.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import java.util.Locale; 
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * <pre>
 * 业务名: I18N资源操作工具类,提供应用框架资源文件初使化，获取资源文件的key和value
 * 功能说明: 提供应用框架资源文件初使化，获取资源文件的key和value
 * 编写日期:	2011-5-14
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-14
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class I18NResourceUtil {
	/**
	 * 私有构造函数
	 */
	private I18NResourceUtil(){};
	
	/**
	 * 资源文件单例对象
	 */
	private static I18NResourceUtil i18NResourceUtil=null;
	
	/**
	 * 资源文件键值对存储容器
	 */
	private static Map<String,Properties> map=null;
	
	//国际化资源文件初使
	static{
		try{
			map=new HashMap<String,Properties>();
			Properties props_zh_CN = PropertiesLoaderUtils.loadAllProperties("com/hirisun/hea/common/config/ApplicationResources_zh_CN.properties");
			Properties props_en = PropertiesLoaderUtils.loadAllProperties("com/hirisun/hea/common/config/ApplicationResources_en.properties");
			
			map.put(Locale.CHINESE.toString(), props_zh_CN);
			map.put(Locale.CHINA.toString(), props_zh_CN);
			
			
			map.put(Locale.ENGLISH.toString(), props_en);
			map.put(Locale.US.toString(), props_en);
			
			i18NResourceUtil=new I18NResourceUtil();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
	}
	
	/**
	 * 
	 * 方法说明：单例获取I18N资源文件对象
	 *
	 * @return 返回单例对象
	 */
	public static I18NResourceUtil getInstance(){
		return i18NResourceUtil;
	}
	
	/**
	 * 
	 * 方法说明：返回I18N值对象
	 *
	 * @param name 键名称
	 * @param locale 本地化对象
	 * @return 返回值
	 */
	public static String getValue(String name,Locale locale){
		return map.get(locale.toString()).getProperty(name);
	}
}
