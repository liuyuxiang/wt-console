package com.wt.uum2.service;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupList;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface GroupService
{
	/**
	 * 方法说明：getUsersGroupsByGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	UserPageResult<GroupList> getUsersGroupsByGroup(int page, int pagesize, Group group);

}
