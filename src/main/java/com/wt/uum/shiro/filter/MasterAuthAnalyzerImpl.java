package com.wt.uum.shiro.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;

import com.hirisun.components.security.lcta.LctaContextHolder;
import com.hirisun.components.security.lcta.MasterAuthAnalyzer;

public class MasterAuthAnalyzerImpl implements MasterAuthAnalyzer{

	public String analyze(HttpServletRequest req) {
		
		String userId = null;//LctaContextHolder.getContext().getUserid();
		Object principal = null;
		
		try {
			principal = SecurityUtils.getSubject().getPrincipal();
		} catch (org.apache.shiro.UnavailableSecurityManagerException e) {
			e.printStackTrace();
		}
		if (principal != null) {
			userId = principal.toString();
		}
		
		return userId;
	}

}
