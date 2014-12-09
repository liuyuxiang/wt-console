package com.wt.hea.common.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.model.BehaveLog;
import com.wt.hea.common.model.Page;

/**
 * 系统行为日志持久层接口
 * 
 * @author 袁明敏
 * 
 */
public interface BehaveLogDao extends EntityDao<BehaveLog>
{
	/**
	 * 方法说明：删除一条日志记录
	 * 
	 * @param e
	 *            日志对象实体
	 */
	public void delete(BehaveLog e);

	/**
	 * 方法说明：根据id删除日志记录
	 * 
	 * @param id
	 *            日志对象id标识
	 * @return boolean
	 * 
	 */
	public boolean deleteById(Serializable id);

	/**
	 * 方法说明：查找所有日志对象
	 * 
	 * @return 返回所有日志对象
	 */
	public List<BehaveLog> findAll();

	/**
	 * 方法说明：根据属性值匹配日志对象
	 * 
	 * @param property
	 *            属性名称
	 * @param isAsc
	 *            升序或降序标识
	 * @return 返回日志对象列表
	 */
	public List<BehaveLog> findAll(String property, Boolean isAsc);

	/**
	 * 方法说明：跟据id查找日志对象
	 * 
	 * @param id
	 *            对象标识id
	 * @return 返回日志对象模型
	 */
	public BehaveLog findById(Serializable id);

	/**
	 * 方法说明: 根据属性和属性值查找匹配的日志对象
	 * 
	 * @param property
	 *            模型属性名称
	 * @param value
	 *            属性值
	 * @return 返回匹配的日志对象
	 */
	public List<BehaveLog> findByProperty(String property, Object value);

	/**
	 * 方法说明：日志对象分页加载
	 * 
	 * @param pageModel
	 *            初使化的分页对象
	 * @return 返回带数据的分页对象
	 */
	public Page<BehaveLog> loadPage(Page<BehaveLog> pageModel);

	/**
	 * 方法说明：保存一条日志
	 * 
	 * @param e
	 *            日志对象实体
	 * @return boolean
	 */
	public boolean save(BehaveLog e);

	/**
	 * 方法说明：更新一条日志
	 * 
	 * @param e
	 *            需要更新的日志对象模型
	 * @return 返回更新后的对象
	 */
	public BehaveLog update(BehaveLog e);

}
