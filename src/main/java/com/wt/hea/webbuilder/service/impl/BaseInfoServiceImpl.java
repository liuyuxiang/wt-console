package com.wt.hea.webbuilder.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.wt.hea.common.model.Page;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.service.BaseInfoService;

/***
 * 站点的首页资源业务操作接口定义的实现
 * 
 * @author 袁明敏
 * 
 */
public class BaseInfoServiceImpl extends BaseService implements BaseInfoService {
	/**
	 * 站点的首页资源删除
	 * 
	 * @param e
	 *            e
	 */
	public void delete(BaseInfo e) {
		this.baseInfoDao.delete(e);
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param id
	 *            资源id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id) {
		return this.baseInfoDao.deleteById(id);
	}

	/**
	 * 查找所有
	 * 
	 * @return List<BaseInfo>
	 */
	public List<BaseInfo> findAll() {
		return this.baseInfoDao.findAll();
	}

	/**
	 * 根据属性查找
	 * 
	 * @param property
	 *            　属性名称
	 * @param value
	 *            属性值
	 * @return List<BaseInfo>
	 */
	public List<BaseInfo> findByProperty(String property, Object value) {
		return this.baseInfoDao.findByProperty(property, value);
	}

	/**
	 * 保存
	 * 
	 * @param e
	 *            e
	 * @return boolean
	 */
	public boolean save(BaseInfo e) {
		return this.baseInfoDao.save(e);
	}

	/**
	 * 更新
	 * 
	 * @param e
	 *            e
	 * @return 返回更新后的对象
	 */
	public BaseInfo update(BaseInfo e) {
		return this.baseInfoDao.update(e);
	}

	/**
	 * 查找所有
	 * 
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<BaseInfo>
	 */
	public List<BaseInfo> findAll(String property, Boolean isAsc) {
		return this.baseInfoDao.findAll(property, isAsc);
	}

	/**
	 * 跟据id查找对象
	 * 
	 * @param id
	 *            id
	 * @return BaseInfo
	 */
	public BaseInfo findById(String id) {
		return this.baseInfoDao.findById(id);

	}

	/**
	 * 加载分页对象
	 * 
	 * @param pageModel
	 *            pageModel
	 * @return Page<BaseInfo>
	 */
	public Page<BaseInfo> loadPage(Page<BaseInfo> pageModel) {
		return this.baseInfoDao.loadPage(pageModel);
	}

	/**
	 * 跟据属性查找对象
	 * 
	 * @param map
	 *            map
	 * @return List<BaseInfo>
	 */
	public List<BaseInfo> findByProperty(Map<String, Object> map) {
		return this.baseInfoDao.findByProperty(map);
	}
}
