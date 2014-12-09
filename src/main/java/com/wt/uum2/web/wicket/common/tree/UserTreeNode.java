package com.wt.uum2.web.wicket.common.tree;

import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:用户节点
 * 功能说明: 
 * 编写日期:	2011-11-9
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserTreeNode extends ResourceMutableTreeNode<User>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2211776319559505471L;

	/**
	 * @param userObject
	 *            userObject
	 */
	public UserTreeNode(User userObject)
	{
		super(userObject, false);
		setUserObject(userObject.getName());
	}

}
