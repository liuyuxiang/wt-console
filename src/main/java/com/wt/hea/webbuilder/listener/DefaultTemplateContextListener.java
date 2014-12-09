package com.wt.hea.webbuilder.listener;

import java.io.File;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 该类提供用户个性化定制功能时，服务器关启动时将保存默认模版配置信息到文本文件
 * 编写日期:	2011-3-25
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DefaultTemplateContextListener implements ServletContextListener {

	/**
	 * @param event event
	 */
	public void contextDestroyed(ServletContextEvent event) {
//		String filePath = event.getServletContext().getRealPath("");
		
	}

	/**
	 * @param event event
	 */
	public void contextInitialized(ServletContextEvent event) {
		
		
		String folderPath = event.getServletContext().getRealPath("");
		File folder = new File(folderPath + File.separator);
		if(folder.exists()){
			if(folder.isDirectory()) {
				File[] files = folder.listFiles();
				for (int i = 0; i < files.length; i++) {
				}
				
			}
		}
	}

}
