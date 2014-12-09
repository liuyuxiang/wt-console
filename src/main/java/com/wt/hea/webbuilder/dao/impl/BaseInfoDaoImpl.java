package com.wt.hea.webbuilder.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.BaseInfoDao;
import com.wt.hea.webbuilder.model.BaseInfo;

/***
 * 某站点下的首页资源操作持久层实现类 BaseInfo封装 侧帘、飘窗、LOGO、版权等资源信息
 * 
 * @author 袁明敏
 * 
 */
public class BaseInfoDaoImpl extends AbstractHibernateDaoSupport<BaseInfo>
		implements BaseInfoDao {
	/**
	 * 根据map存储的多个属性查取资源信息
	 * 
	 * @param map
	 *            Map<String,Object>多个查询条件
	 * @return baseInfo对象列表
	 */
	@SuppressWarnings("unchecked")
	public List<BaseInfo> findByProperty(Map<String, Object> map) {
		Criteria criteria = super.getSession().createCriteria(BaseInfo.class);
		Set<Entry<String, Object>> entrys = map.entrySet();
		for (Entry<String, Object> entry : entrys) {
			criteria.add(Restrictions.eq(entry.getKey(), entry.getValue()));
		}
		return criteria.list();
	}
}
