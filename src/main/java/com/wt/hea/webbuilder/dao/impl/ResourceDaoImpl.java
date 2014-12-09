package com.wt.hea.webbuilder.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.ResourceDao;
import com.wt.hea.webbuilder.model.ResourceSite;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 资源管理Dao
 * 编写日期:	2011-3-24
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class ResourceDaoImpl extends AbstractHibernateDaoSupport<ResourceSite> implements ResourceDao
{

	/**
	 * 方法说明： 根据map存放的多个查询条件查询资源列表
	 * 
	 * @param map
	 *            map
	 * @return List<Resource>
	 */
	@SuppressWarnings("unchecked")
	public List<ResourceSite> findByProperty(Map<String, Object> map)
	{
		Criteria criteria = super.getSession().createCriteria(ResourceSite.class);
		Set<Map.Entry<String, Object>> entries = map.entrySet();
		for (Entry<String, Object> entry : entries) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return criteria.list();
	}
}
