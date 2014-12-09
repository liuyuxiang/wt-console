package com.wt.hea.webbuilder.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.webbuilder.dao.TemplatePortletInfoDao;
import com.wt.hea.webbuilder.model.TemplatePortletInfo;

/**
 * <pre>
 * 业务名:
 * 功能说明: 模板portlet信息Dao实现
 * 编写日期:	2011-5-12
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TemplatePortletInfoDaoImpl extends AbstractHibernateDaoSupport<TemplatePortletInfo> implements
		TemplatePortletInfoDao {
	@Override
	public void delete(TemplatePortletInfo e) {
		super.delete(e);
	}
	@Override
	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}
	
	@Override
	public List<TemplatePortletInfo> findAll() {
		return super.findAll();
	}
	
	@Override
	public TemplatePortletInfo findById(Serializable id) {
		return super.findById(id);
	}
	
	@Override
	public List<TemplatePortletInfo> findByProperty(String property,
			Object value) {
		return super.findByProperty(property, value);
	}
	
	@Override
	public TemplatePortletInfo findByPropertyUnique(String property,
			Object value) {
		return super.findByPropertyUnique(property, value);
	}
	
	@Override
	public boolean save(TemplatePortletInfo obj) {
		return super.save(obj);
	}
	
	@Override
	public TemplatePortletInfo update(TemplatePortletInfo obj) {
		return super.update(obj);
	}
	

}
