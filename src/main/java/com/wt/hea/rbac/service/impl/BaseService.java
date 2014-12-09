package com.wt.hea.rbac.service.impl;

import org.springframework.stereotype.Service;

import com.wt.hea.rbac.dao.IndexDao;
import com.wt.hea.rbac.dao.IndexPersonalizationDao;

/**
 * 所有业务类须继承的基类 所有数据访问对象将注入到的业务基类
 * 
 * @author 袁明敏
 * 
 */
@Service("heaRbacBaseService")
public class BaseService
{
	/**
	 * indexDao
	 */
	protected IndexDao indexDao;
	
	protected IndexPersonalizationDao indexPersonalizationDao;
	/**
	 * 注入indexDaoImpl数据访问对象
	 * 
	 * @param indexDaoImpl
	 *            indexDaoImpl
	 */
	public void setIndexDao(IndexDao indexDaoImpl)
	{
		this.indexDao = indexDaoImpl;
	}
	public void setIndexPersonalizationDao(
			IndexPersonalizationDao indexPersonalizationDao) {
		this.indexPersonalizationDao = indexPersonalizationDao;
	}
	
	
}
