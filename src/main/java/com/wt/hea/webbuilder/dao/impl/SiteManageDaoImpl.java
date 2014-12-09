package com.wt.hea.webbuilder.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.SiteManageDao;
import com.wt.hea.webbuilder.model.SiteManage;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 站点管理接口实现类
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SiteManageDaoImpl extends AbstractHibernateDaoSupport<SiteManage> implements
		SiteManageDao
{

	/**
	 * @param map
	 *            map
	 * @return List<SiteManage>
	 */
	@SuppressWarnings("unchecked")
	public List<SiteManage> findByProperty(Map<String, Object> map)
	{
		Criteria criteria = super.getSession().createCriteria(SiteManage.class);
		Set<Entry<String, Object>> entries = map.entrySet();
		for (Map.Entry<String, Object> entry : entries) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return criteria.list();
	}
}
