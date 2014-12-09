package com.wt.hea.rbac.service;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.rbac.model.Index;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:"资源(资源)"业务对象操作     接口定义
 * 功能说明: 
 * 编写日期:	2011-3-31
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface IndexService
{

	/**
	 * 按id查找当前资源下的所有资源(多层)
	 * 
	 * @param uuid
	 *            父级资源uuid
	 * @return 资源列表
	 */
	public List<Index> findChildsById(String uuid);

	/**
	 * 创建一个新资源
	 * 
	 * @param index
	 *            资源对象
	 */
	public void save(Index index);

	/**
	 * 删除一个资源
	 * 
	 * @param index
	 *            index
	 */
	public void delete(Index index);

	/**
	 * 按资源ID删除一个资源
	 * 
	 * @param id
	 *            资源id
	 */
	public void deleteById(Serializable id);

	/**
	 * 按资源id查找某资源
	 * 
	 * @param id
	 *            资源id
	 * @return 资源对象
	 */
	public Index findById(Serializable id);

	/**
	 * 查询所有资源对象
	 * 
	 * @return 资源信息列表
	 */
	public List<Index> findAll();

	/**
	 * 按照资源某个属性排序查找所有资源对象
	 * 
	 * @param property
	 *            资源对象的property
	 * @param isAsc
	 *            是否升序
	 * @return 排序后的资源对象集合
	 */
	public List<Index> findAll(String property, Boolean isAsc);

	/**
	 * 更新资源对象
	 * 
	 * @param index
	 *            资源对象
	 * @return 更新后的资源对象
	 */
	public Index update(Index index);

	/**
	 * 跟据实体属性值查询对象
	 * 
	 * @param property
	 *            资源对象的属性
	 * @param value
	 *            查询property对应的值
	 * @return 符合条件的资源对象集合
	 */
	public List<Index> findByProperty(String property, Object value);

	/**
	 * 据当前资源结点的id生产下级结点的级别编码
	 * 
	 * @param uuid
	 *            资源id
	 * @return 资源层级
	 */
	public String createLevelCodeById(String uuid);

	/**
	 * 
	 * 方法说明：根据组ID，资源类型获取关联的资源。
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            ［0是资源 1是控件 2是站点 3是菜单 04文件模板 5 控制台节点］
	 * @return 资源列表
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type);

	/**
	 * 
	 * 方法说明：获取组关联的资源
	 * 
	 * @param groupIds
	 *            groupIds
	 * @return 资源列表
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds);

	/**
	 * 
	 * 方法说明：根据组ID，资源类型，父级资源，获取关联的下级资源。
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            ［0是资源 1是控件 2是站点 3是菜单 04文件模板 5 控制台节点］
	 * @param parentIndexId
	 *            资源父级id
	 * @param state
	 *            状态 ［1是启用 0是禁用 -1无限制］
	 * @return 资源列表
	 */
	public List<Index> findChildIndexByGroupID(List<String> groupIds, int type,
			String parentIndexId, int state);

	/**
	 * 
	 * 方法说明：根据用户，资源类型获取关联的资源。
	 * 
	 * @param user
	 *            用户对象
	 * @param type
	 *            资源类型
	 * @return 资源列表
	 */
	public List<Index> findIndexByUser(User user, int type);

	/**
	 * 
	 * 方法说明：根据用户获取关联的资源。
	 * 
	 * @param user
	 *            用户对象
	 * @return 资源列表
	 */
	public List<Index> findIndexByUser(User user);

	/**
	 * 
	 * 方法说明：根据用户，父级资源，资源类型获取关联的下级资源。
	 * 
	 * @param user
	 *            用户对象
	 * @param type
	 *            资源类型
	 * @param parentIndexId
	 *            资源父级id
	 * @return 资源列表
	 */
	public List<Index> findChildIndexByUser(User user, int type, String parentIndexId);

	/**
	 * 
	 * 方法说明：根据资源ID获取关联的组。
	 * 
	 * @param indexId
	 *            资源id
	 * @return 关联的组的集合
	 */
	public List<Group> findGroupByIndexID(String indexId);

	/**
	 * 
	 * 方法说明：添加资源与组的关联
	 * 
	 * @param indexId
	 *            资源id
	 * @param groupIds
	 *            组ids
	 */
	public void addIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：删除资源与组的关联
	 * 
	 * @param indexIds
	 *            资源ids
	 * @param groupId
	 *            组id
	 */
	public void deleteIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：删除资源与组的关联
	 * 
	 * @param indexId
	 *            资源id
	 * @param groupIds
	 *            组ids
	 */
	public void deleteIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：添加资源与组的关联
	 * 
	 * @param indexIds
	 *            资源id
	 * @param groupId
	 *            组id
	 */
	public void addIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexId
	 *            资源id
	 * @param groupIds
	 *            组ids
	 */
	public void updateIndexGroup(String indexId, String[] groupIds);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexId
	 *            资源id
	 * @param groups
	 *            组对象集合
	 */
	public void updateIndexGroup(String indexId, List<Group> groups);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexIds
	 *            资源id
	 * @param groupId
	 *            组id
	 */
	public void updateIndexGroup(String[] indexIds, String groupId);

	/**
	 * 
	 * 方法说明：更新资源与组的关联
	 * 
	 * @param indexes
	 *            资源集合
	 * @param groupId
	 *            组id
	 */
	public void updateIndexGroup(List<Index> indexes, String groupId);

	/**
	 * 
	 * 方法说明：根据父级获取子级
	 * 
	 * @param parentId
	 *            父级资源id
	 * @param type
	 *            资源类型
	 * @return 资源列表
	 */
	public List<Index> findChildByParentId(String parentId, int type);

	/**
	 * 
	 * 方法说明：根据父级获取子级
	 * 
	 * @param parentId
	 *            父级资源id
	 * @param type
	 *            资源类型
	 * @return 资源列表
	 */
	public List<Index> findChildByParentId(String parentId, int type, String appId);

	/**
	 * 方法说明：根据appId获取资源
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            type
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type, String appId);

	/**
	 * 
	 * 方法说明：根据资源类型获取所有
	 * 
	 * @param type
	 *            资源类型
	 * @return 资源列表
	 */
	public List<Index> findAll(int type);

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
	 * 
	 * 方法说明：获取根级资源
	 * 
	 * @param type
	 *            资源类型
	 * @return 根级资源
	 */
	public Index findRootIndex(int type);

	/**
	 * 方法说明：删除资源及所有下级资源
	 * 
	 * @param indexUuid
	 *            资源id
	 */
	public void deleteIndexAndAllChild(String indexUuid);

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
	 * 方法说明 ：是否有下一级可管理的资源
	 * 
	 * @param parentId
	 *            父级资源ID
	 * @param groupId
	 *            组ID
	 * @param type
	 *            类型
	 * @return 是否可关联下级节点
	 */
	public boolean hasManagerChild(String parentId, String groupId[], int type);

	/**
	 * 级联修改资源等级编码
	 * 
	 * @param rootIndex
	 *            根
	 * @param level
	 *            等级编码
	 * @return 更新记录数
	 */
	public int updateIndexLevelCascade(Index rootIndex, int level);

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
	
	List<Index> getTreeIndex(List<Index> rootList,List<Index> allIndex);
}
