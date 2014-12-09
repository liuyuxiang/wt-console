package com.wt.hea.rbac.dao;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.EntityDao;
import com.wt.hea.rbac.model.Index;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-4-1
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface IndexDao extends EntityDao<Index>
{

	/**
	 * 
	 * 方法说明：通过ID删除
	 * 
	 * @param id
	 *            ID号
	 */
	void deleteByIds(Serializable... id);

	/**
	 * 
	 * 方法说明：删除所有
	 * 
	 */
	void deleteAll();

	/**
	 * 查找所有
	 * 
	 * @return 列表
	 */
	List<Index> findAll();

	/**
	 * 
	 * 方法说明：通过类型查找所有
	 * 
	 * @param type
	 *            报表类型
	 * @return 列表
	 */
	List<Index> findAll(int type);

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	public List<Index> findAll(int type, String appId);

	/**
	 * 方法说明：查找所有
	 * 
	 * @param property
	 *            属性名
	 * @param isAsc
	 *            排序
	 * @return 列表
	 */
	List<Index> findAll(String property, Boolean isAsc);

	/**
	 * 
	 * 方法说明：查找多个
	 * 
	 * @param id
	 *            ID号
	 * @return 列表
	 */
	List<Index> findByIds(Serializable... id);

	/**
	 * 方法说明：更新
	 * 
	 * @param e
	 *            资源
	 * @return Index对象
	 */
	Index update(Index e);

	/**
	 * 方法说明：通过属性查找
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return list
	 */
	List<Index> findByProperty(String property, Object value);

	/**
	 * 
	 * 方法说明：查找下级
	 * 
	 * @param id
	 *            指标id
	 * @return 指标信息
	 */
	List<Index> findByChildsById(Serializable id);

	/**
	 * 跟据当前结点的id生产下级结点的级别编码
	 * 
	 * @param id
	 *            指标id
	 * @return 指标层级
	 */
	String createLevelCodeById(Serializable id);

	/**
	 * 
	 * 方法说明：获取组关联的指标
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            指标类型
	 * @return 指标列表
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type);

	/**
	 * 
	 * 方法说明：获取组关联的指标
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            指标类型
	 * @param appId
	 *            资源库AppId
	 * @return 指标列表
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type, String appId);

	/**
	 * 
	 * 方法说明：获取组关联的指标
	 * 
	 * @param groupIds
	 *            groupIds
	 * @return 指标列表
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds);

	/**
	 * 
	 * 方法说明：根据组ID，资源类型，父级资源，获取关联的下级资源。
	 * 
	 * @param groupIds
	 *            组id
	 * @param type
	 *            ［0是指标 1是控件 2是站点 3是菜单 04文件模板 5 控制台节点］
	 * @param parentIndexId
	 *            指标父级id
	 * @param state
	 *            状态［1启用 0禁用 -1无限制］
	 * @return 指标列表
	 */
	public List<Index> findChildIndexByGroupID(List<String> groupIds, int type,
			String parentIndexId, int state);

	/**
	 * 
	 * 方法说明：根据父级获取子级
	 * 
	 * @param parentId
	 *            父级id
	 * @param type
	 *            指标类型
	 * @return 指标列表
	 */
	public List<Index> findChildByParentId(String parentId, int type);

	/**
	 * 
	 * 方法说明：添加资源与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组id
	 */
	public void addIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：添加资源与组的关联
	 * 
	 * @param indexIds
	 *            指标id
	 * @param groupId
	 *            组id
	 */
	public void addIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：删除资源与组的关联
	 * 
	 * @param indexIds
	 *            指标id
	 * @param groupId
	 *            组id
	 */
	public void deleteIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：删除资源与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组id
	 */
	public void deleteIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组id
	 */
	public void updateIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexIds
	 *            指标id
	 * @param groupId
	 *            组id
	 */
	public void updateIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：获取资源关联的组ID
	 * 
	 * @param indexId
	 *            指标id
	 * @return 组id列表
	 */
	public List<String> findGroupIdByIndexID(String indexId);

	/**
	 * 
	 * 方法说明：获取根级资源
	 * 
	 * @param type
	 *            指标类型
	 * @return 根级指标
	 */
	public Index findRootIndex(int type);

	/**
	 * 
	 * 方法说明：删除指标与组的关系
	 * 
	 * @param indexUuid
	 *            indexUuid
	 * @return
	 */
	public void deleteIndexIG(String indexUuid);

	/**
	 * 方法说明：是否有下一级
	 * 
	 * @param parentId
	 *            父级ID
	 * @param type
	 *            类型
	 * @return 是否有子节点
	 */
	public boolean hasChild(String parentId, int type);

	/**
	 * 方法说明： 获取应用的根级资源
	 * 
	 * @param type
	 *            资源类型
	 * @param appId
	 *            应用id
	 * @return 资源
	 */
	Index findRootIndex(int type, String appId);

	/**
	 * 方法说明： 获取应用下级节点
	 * 
	 * @param parentId
	 *            父节点id
	 * @param type
	 *            类型
	 * @param appId
	 *            应用id
	 * @return 应用资源
	 */
	List<Index> findChildByParentId(String parentId, int type, String appId);

}
