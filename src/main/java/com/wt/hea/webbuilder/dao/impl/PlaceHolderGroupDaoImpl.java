package com.wt.hea.webbuilder.dao.impl;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.PlaceHolderGroupDao;
import com.wt.hea.webbuilder.model.PlaceHolderGroup;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板占位符dao实现
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class PlaceHolderGroupDaoImpl extends AbstractHibernateDaoSupport<PlaceHolderGroup>
		implements PlaceHolderGroupDao
{

	/**
	 * 
	 * @param groupId
	 *            groupId
	 * @param tmplId
	 *            tmplId
	 * @return List<PlaceHolderGroup>
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceHolderGroup> findByGroupAndTmpl(String groupId, String tmplId)
	{
		List<PlaceHolderGroup> phgList = this.getSession().createCriteria(PlaceHolderGroup.class)
				.add(Restrictions.eq("groupId", groupId)).add(Restrictions.eq("tmplId", tmplId))
				.list();
		return phgList;
	}

}
