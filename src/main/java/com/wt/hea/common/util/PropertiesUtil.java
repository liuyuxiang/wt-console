package com.wt.hea.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Properties;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-11-9
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PropertiesUtil
{

	/**
	 * 方法说明： 获取在classpath下的properties文件
	 * 
	 * @param propPath
	 *            properties文件路径
	 * @return properties
	 */
	public static Properties getProp(String propPath)
	{
		Properties props = new Properties();
		InputStream inStream = null;
		InputStreamReader isr = null;
		Reader reader = null;
		try {
			// String cp = Object.class.getResource(propPath).getFile();
			inStream = PropertiesUtil.class.getResourceAsStream(propPath);
			isr = new InputStreamReader(inStream, "utf-8");
			reader = new BufferedReader(isr);
			props.load(inStream);
			// props.load(reader);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (isr != null) {
				try {
					isr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return props;
	}

	// public static void main(String[] args) {
	// Properties props =
	// PropertiesUtil.getProp("/com/hirisun/senddata/context/cityCode.properties");
	// for(Object key : props.keySet()){
	// System.out.println(props.get(key));
	// }
	// }
}
