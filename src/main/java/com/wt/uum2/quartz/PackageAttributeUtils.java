package com.wt.uum2.quartz;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import com.wt.uum2.domain.DepartmentTempLog;

/**
 * <pre>
 * 业务名: 封装扩展属性工具类
 * 功能说明: 
 * 编写日期:	2012-12-8
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PackageAttributeUtils
{

	/**
	 * Properties
	 */
	private static Properties prop = new Properties();

	/**
	 * 方法说明：packageAttribute
	 * 
	 * @param obj
	 *            invoke object
	 * @param prop
	 *            prop
	 * @return Map
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> packageAttribute(Object obj, Properties prop)
			throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException
	{
		Map<String, Object> map = new HashMap<String, Object>();

		for (Entry<String, Object> entry : reflect(obj, prop).entrySet()) {
			map.put(entry.getKey(), entry.getValue());

		}

		return map;
	}

	/**
	 * 方法说明：reflect
	 * 
	 * @param clazz
	 *            clazz
	 * @param prop
	 *            prop
	 * @return Map
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	public static Map<String, Object> reflect(Object obj, Properties prop)
			throws NoSuchMethodException, SecurityException
	{
		Map<String, Object> map = new HashMap<String, Object>();

		for (Entry<Object, Object> entry : prop.entrySet()) {
			String key = (String) entry.getKey();
			String value = (String) entry.getValue();
			
			Method method = obj.getClass().getDeclaredMethod("get" + key);
			if (value != null && value.length() > 0) {
				try {
					if (method.getReturnType().getName().equals("java.util.Date")) {
						Date date = (Date)method.invoke(obj);
						if (date!=null) {
						   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					       map.put(value, sdf.format(date));
						}
					}else{
						map.put(value, method.invoke(obj));
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return map;
	}

	/**
	 * 方法说明：packageAttribute
	 * 
	 * @param obj
	 *            obj
	 * @param fileName
	 *            fileName
	 * @return Map
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws NoSuchMethodException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static Map<String, Object> packageAttribute(Object obj, String fileName)
			throws SecurityException, IllegalArgumentException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException
	{
		return packageAttribute(obj, getProperties(fileName));
	}

	/**
	 * 方法说明：getProperties
	 * 
	 * @param propertiesName
	 *            properties file name
	 * @return Properties
	 */
	private static Properties getProperties(String propertiesName)
	{
		if (prop.isEmpty()) {
			if (propertiesName == null || propertiesName.length() == 0) {
				propertiesName = "syncFrom.properties";

			}
			InputStream propIn = PackageAttributeUtils.class.getResourceAsStream(propertiesName);
			try {
				prop.load(propIn);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (propIn != null) {
					try {
						propIn.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return prop;
	}

	/**
	 * 方法说明：main
	 * 
	 * @param args
	 *            args
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	public static void main(String[] args) throws SecurityException, NoSuchMethodException,
			IllegalArgumentException, IllegalAccessException, InvocationTargetException
	{
		DepartmentTempLog dl = new DepartmentTempLog();

		dl.setUuid("aaa");

		Map<String, Object> map = packageAttribute(dl, "");
		for (Entry<String, Object> entry : map.entrySet()) {
			// System.out.println(entry.getKey() + "\t" + entry.getValue());
		}

	}

}