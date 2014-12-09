/*******************************************************************************
 * Copyright (c) 2011 by Hirisun Corporation all right reserved.
 * 2011-7-18 
 * 
 *******************************************************************************/
package com.wt.uum2.web;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wt.uum2.service.DBInfoService;

/**
 * <pre>
 * 业务名:数据库信息的action
 * 功能说明: 取数据库信息的action
 * 编写日期:	2011-7-18
 * 作者:	李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class DBInfoController
{
	/**
	 * 
	 */
	private DBInfoService dbInfoService;

	/**
	 * @param dbInfoService
	 *            the dbInfoService to set
	 */
	public void setDbInfoService(DBInfoService dbInfoService)
	{
		this.dbInfoService = dbInfoService;
	}

	/**
	 * @return ssssssss
	 */
	public DBInfoService getDbInfoService()
	{
		return dbInfoService;
	}

	/**
	 * 方法说明：dbinfo
	 * 
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/dbinfo")
	public String dbinfo(ModelMap model)
	{
		Map<String, String> map = dbInfoService.getDBinfo();
		Set<Entry<String, String>> entries = map.entrySet();
		for (Entry<String, String> e : entries) {
			model.addAttribute(e.getKey(), e.getValue());
		}

		boolean dba = false;
		for (String role : map.get("roles").split(",")) {
			if (role.equalsIgnoreCase("dba")) {
				dba = true;
				break;
			}
		}

		model.addAttribute("dbaWarning", dba);

		return "/dbinfo";
	}
}
