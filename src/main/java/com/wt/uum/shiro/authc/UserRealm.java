package com.wt.uum.shiro.authc;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.AuthenticatingRealm;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务�? 用户名密码验证的realm
 * 功能说明: 用户名密码验证的realm
 * 编写日期:	2011-11-25
 * 作�?	Guo Tianci
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容�
 * </pre>
 */
public class UserRealm extends AuthenticatingRealm
{

	/**
	 * UUMService
	 */
	private UUMService uumService;

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public String getName()
	{
		return "Simple-Realm";
	}

	/* (non-Javadoc)
	 * @see org.apache.shiro.realm.AuthenticatingRealm#supports(org.apache.shiro.authc.AuthenticationToken)
	 */
	/**
	 * 方法说明：supports
	 * 
	 * @param token
	 *            token
	 * @return boolean
	 */
	public boolean supports(AuthenticationToken token)
	{
		if (token != null && token instanceof UsernamePasswordToken) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是否是可管理�
	 * @param userid
	 * @return
	 */
	private boolean isManagerGroupMember(User user) {
		if(user==null){
			return false;
		}
		
		if(StringUtils.equals(user.getId(), InitParameters.getSuperUserCode())){
			return true;
		}
		
		List<Group> managerGroup = new ArrayList<Group>();
		for (String str : InitParameters.getManagerGroupCode()) {
			if(StringUtils.isNotBlank(str))
				managerGroup.add(uumService.getGroupByCode(str));
		}
		if(CollectionUtils.isEmpty(managerGroup)){
			return true;
		}
		return CollectionUtils.containsAny(uumService.getUserGroups(user), managerGroup);
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException
	{

		if (token != null) {
			UsernamePasswordToken upToken = (UsernamePasswordToken) token;
			User user = uumService.getUserByUserId(upToken.getUsername());

			//如果不存在管理组,则不让其验证通过
			if (!isManagerGroupMember(user)){
				return null;
			}
			
			//如果用户状态不正常,则不让其验证通过
			if (user.getStatus()!=ResourceStatus.NORMAL.intValue()){
				return null;
			}
			
			if (user != null) {
				return new SimpleAuthenticationInfo(user.getId(), user.getPlainPassword(),
						getName());
			}

		}

		return null;

	}

}
