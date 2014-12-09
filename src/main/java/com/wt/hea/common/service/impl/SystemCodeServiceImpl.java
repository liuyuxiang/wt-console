package com.wt.hea.common.service.impl;

import java.util.List;

import com.wt.hea.common.model.Page;
import com.wt.hea.common.model.SystemCode;
import com.wt.hea.common.service.SystemCodeService;

/**
 * 
 * <pre>
 * 业务名:系统全局常量服务接口
 * 功能说明: 系统全局常量服务接口
 * 编写日期:	2011-5-13
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-5-13
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public class SystemCodeServiceImpl extends BaseService implements SystemCodeService
{
	/**
	 * 方法说明: 添加一条常量表记录
	 * 
	 * @param systemCode
	 *            常量实体
	 */
	public void save(SystemCode systemCode)
	{
		this.systemCodeDao.save(systemCode);
	}

	/**
	 * 方法说明：删除系统常量模型
	 * 
	 * @param systemCode
	 *            常量实体
	 */
	public void delete(SystemCode systemCode)
	{
		this.systemCodeDao.delete(systemCode);
	}

	/**
	 * 方法说明: 根据id查找常量表记录
	 * 
	 * @param id
	 *            常量实体id
	 * @return 常量模型
	 */
	public SystemCode findById(String id)
	{
		return this.systemCodeDao.findById(id);
	}

	/**
	 * 方法说明：查找所有常量表记录
	 * 
	 * @return 返回list集合
	 */
	public List<SystemCode> findAll()
	{
		return this.systemCodeDao.findAll();
	}

	/**
	 * 方法说明：更新常量表一条记录
	 * 
	 * @param systemCode
	 *            系统编码模型
	 * @return 返回更新后的常量对象
	 */
	public SystemCode update(SystemCode systemCode)
	{
		return this.systemCodeDao.update(systemCode);
	}

	/**
	 * 
	 * 方法说明：根据系统常量范例查询记录
	 * 
	 * @param obj
	 *            常量实体范例对象
	 * @return 返回结果集
	 */
	public List<SystemCode> findByExample(SystemCode obj)
	{
		return this.systemCodeDao.findByExample(obj);

	}

	/**
	 * 
	 * 方法说明：分页加载全局常量
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<SystemCode>
	 */
	public Page<SystemCode> loadPage(Page<SystemCode> pageModel)
	{
		return this.systemCodeDao.loadPage(pageModel);
	}
}
