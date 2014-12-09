package com.wt.uum.shiro.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.hirisun.components.security.lcta.LctaContextHolder;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

public class AuthcFilter extends UserFilter {

	/**
	 * 匿名用户名
	 */
	private static final String ANONYMOUS_USER_NAME = "anonymous";
	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory.getLog(AuthcFilter.class);

	private boolean isManagerGroupMember(String userid) {
		if (StringUtils.equals(InitParameters.getSuperUserCode(), userid)) {
			return true;
		}
		User user = getUUMService().getUserByUserId(userid);
		if (user == null) {
			return false;
		}
		return CollectionUtils.containsAny(getUUMService().getUserGroups(user),
				getManagerGroup());
	}

	private static UUMService uumService;

	private static List<Group> managerGroup;

	private List<Group> getManagerGroup() {
		if (CollectionUtils.isEmpty(managerGroup)) {
			managerGroup = new ArrayList<Group>();
			managerGroup.add(getUUMService().getGroupByCode("wpsadmins"));
			managerGroup.add(getUUMService().getGroupByCode("xmspuumadmin"));
		}
		return managerGroup;
	}

	private UUMService getUUMService() {
		if (uumService == null) {
			uumService = (UUMService) ContextLoader
					.getCurrentWebApplicationContext().getBean("uumService");
		}
		return uumService;
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
        if (isLoginRequest(request, response)) {
            return true;
        } else {
            Subject subject = getSubject(request, response);
            return subject.getPrincipal() != null&&isManagerGroupMember(subject.getPrincipal().toString());
        }
	}

}
