package com.wt.uum.shiro.filter;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.hirisun.components.security.lcta.LctaContextHolder;
import com.hirisun.components.security.shiro.filter.sso.LctaSsoFilter;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

public class LCTASSOFilterXMSP extends LctaSsoFilter {

	/**
	 * 匿名用户名
	 */
	private static final String ANONYMOUS_USER_NAME = "anonymous";
	/**
	 * LOGGER
	 */
	private static final Log LOGGER = LogFactory
			.getLog(LCTASSOFilterXMSP.class);

	@Override
	protected boolean isAccessAllowed(ServletRequest request,
			ServletResponse response, Object mappedValue) {
		String lctaUserid = LctaContextHolder.getContext().getUserid();
		Subject shiroSubject = SecurityUtils.getSubject();
		Subject subject = null;
		
		if (isManagerGroupMember(lctaUserid)) {

			if (!isAnonymous(shiroSubject)
					&& !lctaUserid.equals(shiroSubject.getPrincipal()
							.toString())) {
				// 如果两个用户不相等生成单点Subject(LCTA)
				LOGGER.debug("lctaUserid != shiroSubject =>true");
				subject = createSubject(lctaUserid, true, "LCTA_REALM",
						request, response);
			} else if (isAnonymous(shiroSubject)) {
				subject = createSubject(lctaUserid, true, "LCTA_REALM",
						request, response);
			}
		} else { // 如果LCTA是匿名登录
			LOGGER.debug("lctaUserid-anonymous=true");
//			subject = createAnonymousSubject(request, response);
//		} else { // 如果LCTA是匿名登录
//			LOGGER.debug("lctaUserid-anonymous=true");
			subject = createSubject(lctaUserid, false, "LCTA_REALM",
					request, response);
		}
		if (subject != null) {
			//LOGGER.debug("sso======subject" + subject.getPrincipal());
			saveSubject(subject);
		}

		return super.isAccessAllowed(request, response, mappedValue);
	}

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

}
