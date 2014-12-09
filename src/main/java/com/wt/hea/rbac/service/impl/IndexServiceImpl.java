package com.wt.hea.rbac.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.service.IndexService;
import com.wt.hea.rbac.service.RBACService;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:"指标"业务对象 Bean
 * 功能说明: 
 * 编写日期:	2011-4-7
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Service("heaIndexService")
public class IndexServiceImpl extends BaseService implements IndexService
{
	/**
	 * log
	 */
	private static final Logger LOG = LoggerService.getInstance(IndexServiceImpl.class);
	/**
	 * rbacService 对象
	 */
	private RBACService rbacService;

	/**
	 * 
	 * 方法说明：获取rbacService
	 * 
	 * @return RBACService
	 */
	public RBACService getRbacService()
	{
		return rbacService;
	}

	/**
	 * 
	 * 方法说明：设置rbacService
	 * 
	 * @param rbacService
	 *            rbacService
	 */
	public void setRbacService(RBACService rbacService)
	{
		this.rbacService = rbacService;
	}

	/**
 *   
 */

	/**
	 * 删除指标
	 * 
	 * @param index
	 *            index
	 */
	public void delete(Index index)
	{
		LOG.info("删除指标,UUID:" + index.getIndexuuid());
		this.indexDao.deleteById(index.getIndexuuid());
	}

	/**
	 * 删除指标,跟据id
	 * 
	 * @param id
	 *            id
	 * 
	 */
	public void deleteById(Serializable id)
	{
		LOG.info("删除指标,UUID:" + id);
		this.indexDao.deleteById(id);
	}

	/**
	 * 方法说明:
	 * 
	 * @return List<Index>
	 */
	public List<Index> findAll()
	{
		LOG.info("查找所有指标");
		return this.indexDao.findAll();
	}

	/**
	 * 查找所有指标
	 * 
	 * @param property
	 *            property
	 * @param isAsc
	 *            　
	 * @return List<Index>
	 */
	public List<Index> findAll(String property, Boolean isAsc)
	{
		LOG.info("查找所有指标");
		return this.indexDao.findAll(property, isAsc);
	}

	/**
	 * 查找指标
	 * 
	 * @param id
	 *            id
	 * @return Index
	 */
	public Index findById(Serializable id)
	{
		LOG.info("查找指标，UUID：" + id);
		return this.indexDao.findById(id);
	}

	/**
	 * 查找下级指标
	 * 
	 * @param uuid
	 *            uuid
	 * @return List<Index>
	 */
	public List<Index> findChildsById(String uuid)
	{
		LOG.info("查找下级指标");
		return this.indexDao.findByChildsById(uuid);
	}

	/**
	 * 保存指标
	 * 
	 * @param index
	 *            index
	 */
	public void save(Index index)
	{
		LOG.info("保存指标");
		this.indexDao.save(index);
	}

	/**
	 * 更新指标
	 * 
	 * @param index
	 *            指标
	 * @return 返回更新后的指标
	 */
	public Index update(Index index)
	{
		LOG.info("更新指标，UUID：" + index.getIndexuuid());
		return this.indexDao.update(index);
	}

	/**
	 * 通过属性查找指标
	 * 
	 * @param property
	 *            property
	 * @param value
	 *            属性值
	 * @return List<Index>
	 */
	public List<Index> findByProperty(String property, Object value)
	{
		LOG.info("通过属性查找指标");
		return this.indexDao.findByProperty(property, value);
	}

	/**
	 * 创建等级code
	 * 
	 * @param uuid
	 *            uuid
	 * @return 创建等级code 串
	 */
	public String createLevelCodeById(String uuid)
	{
		LOG.info("创建等级code");
		return this.indexDao.createLevelCodeById(uuid);
	}

	/**
	 * 查找多个组关联的指标
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            指标类型
	 * @return List<Index>
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type)
	{
		LOG.info("查找多个组关联的指标");
		return indexDao.findIndexByGroupID(groupIds, type);
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            指标类型
	 * @param appId
	 *            资源库AppId
	 * @return List<Index>
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds, int type, String appId)
	{
		return indexDao.findIndexByGroupID(groupIds, type, appId);
	}

	/**
	 * 查找多个组关联的指标
	 * 
	 * @param groupIds
	 *            组id集合
	 * @return List<Index>
	 */
	public List<Index> findIndexByGroupID(List<String> groupIds)
	{
		LOG.info("查找多个组关联的指标");
		return indexDao.findIndexByGroupID(groupIds);
	}

	/**
	 * 查找多个父级组关联的下组指标
	 * 
	 * @param groupIds
	 *            组集合
	 * @param type
	 *            资源类型
	 * @param parentIndexId
	 *            父级指标id
	 * @param state
	 *            指标禁用状态
	 * @return List<Index>
	 */
	public List<Index> findChildIndexByGroupID(List<String> groupIds, int type,
			String parentIndexId, int state)
	{
		LOG.info("查找多个父级组关联的下组指标");
		return indexDao.findChildIndexByGroupID(groupIds, type, parentIndexId, state);
	}

	/**
	 * 查找父级组关联的指标
	 * 
	 * @param user
	 *            用户对象
	 * @param type
	 *            指标类型
	 * @param parentIndexId
	 *            父级指标id
	 * @return List<Index>返回查询结果
	 */
	public List<Index> findChildIndexByUser(User user, int type, String parentIndexId)
	{
		LOG.info("查找父级组关联的指标");
		List<Group> groupList = this.rbacService.getUserGroups(user);
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groupList) {
			groupIds.add(group.getUuid());
		}
		List<Index> indexList = this.findChildIndexByGroupID(groupIds, type, parentIndexId, -1);
		return indexList;
	}

	/**
	 * 查找用户关联的指标
	 * 
	 * @param user
	 *            用户
	 * @param type
	 *            资源类型
	 * @return List<Index> 资源集合
	 */
	public List<Index> findIndexByUser(User user, int type)
	{
		LOG.info("查找用户关联的指标，用户UUID：" + user.getUuid());
		List<Group> groupList = this.rbacService.getUserGroups(user);
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groupList) {
			groupIds.add(group.getUuid());
		}
		List<Index> indexList = this.findIndexByGroupID(groupIds, type);
		return indexList;
	}

	/**
	 * 查找用户关联的指标
	 * 
	 * @param user
	 *            用户
	 * @return List<Index>
	 */
	public List<Index> findIndexByUser(User user)
	{
		LOG.info("查找用户关联的指标，用户UUID：" + user.getUuid());
		List<Group> groupList = this.rbacService.getUserGroups(user);
		List<String> groupIds = new ArrayList<String>();
		for (Group group : groupList) {
			groupIds.add(group.getUuid());
		}
		List<Index> indexList = this.findIndexByGroupID(groupIds);
		return indexList;
	}

	/**
	 * 添加指标与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组的集合
	 */
	public void addIndexGroup(String indexId, String[] groupIds)
	{
		LOG.info("添加指标与组的关联");
		this.indexDao.addIndexGroup(indexId, groupIds);
	}

	/**
	 * 添加指标与组的关联
	 * 
	 * @param indexIds
	 *            指标id集
	 * @param groupId
	 *            组
	 * 
	 */
	public void addIndexGroup(String[] indexIds, String groupId)
	{
		LOG.info("添加指标与组的关联");
		this.indexDao.addIndexGroup(indexIds, groupId);
	}

	/**
	 * 删除指标与组的关联
	 * 
	 * @param indexIds
	 *            指标集
	 * @param groupId
	 *            组id
	 */
	public void deleteIndexGroup(String[] indexIds, String groupId)
	{
		LOG.info("删除指标与组的关联");
		this.indexDao.deleteIndexGroup(indexIds, groupId);
	}

	/**
	 * 删除指标与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组
	 */
	public void deleteIndexGroup(String indexId, String[] groupIds)
	{
		LOG.info("删除指标与组的关联");
		this.indexDao.deleteIndexGroup(indexId, groupIds);
	}

	/**
	 * 通过指标查找关联组
	 * 
	 * @param indexId
	 *            指标id
	 * @return List<Group>
	 */
	public List<Group> findGroupByIndexID(String indexId)
	{
		LOG.info("通过指标查找关联组,指标UUID：" + indexId);
		List<String> groupIds = this.indexDao.findGroupIdByIndexID(indexId);
		List<Group> groupList = new ArrayList<Group>();
		for (String groupId : groupIds) {
			Group group = this.rbacService.getGroupByUuid(groupId);
			if (group != null) {
				groupList.add(group);
			}
		}
		return groupList;
	}

	/**
	 * 更新指标与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groupIds
	 *            组集合id
	 */
	public void updateIndexGroup(String indexId, String[] groupIds)
	{
		LOG.info("更新指标与组的关联");
		this.indexDao.updateIndexGroup(indexId, groupIds);
	}

	/**
	 * 更新指标与组的关联
	 * 
	 * @param indexIds
	 *            指标id集
	 * @param groupId
	 *            组id
	 */
	public void updateIndexGroup(String[] indexIds, String groupId)
	{
		LOG.info("更新指标与组的关联");
		this.indexDao.updateIndexGroup(indexIds, groupId);
	}

	/**
	 * 更新指标与组的关联　
	 * 
	 * @param indexes
	 *            指标集
	 * @param groupId
	 *            组id
	 */
	public void updateIndexGroup(List<Index> indexes, String groupId)
	{
		LOG.info("更新指标与组的关联");
		if (indexes != null) {
			String[] indexIds = new String[indexes.size()];
			for (int i = 0; i < indexes.size(); i++) {
				indexIds[i] = indexes.get(i).getIndexuuid();
			}
			this.indexDao.updateIndexGroup(indexIds, groupId);
		}
	}

	/**
	 * 更新指标与组的关联
	 * 
	 * @param indexId
	 *            指标id
	 * @param groups
	 *            组id集
	 */
	public void updateIndexGroup(String indexId, List<Group> groups)
	{
		LOG.info("更新指标与组的关联");
		if (groups != null) {
			String[] groupIds = new String[groups.size()];
			for (int i = 0; i < groups.size(); i++) {
				groupIds[i] = groups.get(i).getUuid();
			}
			this.indexDao.updateIndexGroup(indexId, groupIds);
		}
	}

	/**
	 * 查找所有
	 * 
	 * @param type
	 *            指标类型
	 * @return List<Index>
	 */
	public List<Index> findAll(int type)
	{
		LOG.info("查找所有,type:" + type);
		return this.indexDao.findAll(type);
	}

	/**
	 * 查找所有
	 * 
	 * @param type
	 *            指标类型
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	public List<Index> findAll(int type, String appId)
	{
		return this.indexDao.findAll(type, appId);
	}

	/**
	 * 查找下级指标
	 * 
	 * @param parentId
	 *            父级指标
	 * @param type
	 *            　资源类型
	 * @return List<Index>
	 */
	public List<Index> findChildByParentId(String parentId, int type)
	{
		LOG.info("查找下级指标");
		return this.indexDao.findChildByParentId(parentId, type);
	}

	/**
	 * 查找根级指标
	 * 
	 * @param type
	 *            资源类型
	 * @return Index
	 */
	public Index findRootIndex(int type)
	{
		LOG.info("查找根级指标");
		return indexDao.findRootIndex(type);
	}

	/* (non-Javadoc)
	 * @see com.wt.hea.rbac.service.IndexService#findRootIndex(int, java.lang.String)
	 */
	/** {@inheritDoc} */
	public Index findRootIndex(int type, String appId)
	{
		LOG.info("查找根级指标");
		return indexDao.findRootIndex(type, appId);
	}

	/**
	 * 删除指标和它所有下级指标
	 * 
	 * @param indexUuid
	 *            指标id
	 */
	public void deleteIndexAndAllChild(String indexUuid)
	{
		List<String> deleteIndexId = new ArrayList<String>();
		Index index = this.indexDao.findById(indexUuid);
		indexChildIdList(index, deleteIndexId);
		for (String id : deleteIndexId) {
			this.deleteById(id);
		}
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param index
	 *            　index
	 * @param indexIdList
	 *            　indexIdList
	 */
	private void indexChildIdList(Index index, List<String> indexIdList)
	{
		List<Index> childIndexList = this.findChildByParentId(index.getIndexuuid(),
				index.getIndextype());
		for (Index i : childIndexList) {
			indexChildIdList(i, indexIdList);
			indexIdList.add(i.getIndexuuid());
		}
		indexIdList.add(index.getIndexuuid());
	}

	/**
	 * 是否有下级
	 * 
	 * @param parentId
	 *            父级id
	 * @param type
	 *            资源类型
	 * @return boolean
	 * 
	 */
	public boolean hasChild(String parentId, int type)
	{
		return indexDao.hasChild(parentId, type);
	}

	/**
	 * 
	 * @param parentId
	 *            parentId
	 * @param groupIds
	 *            groupId[]
	 * @param type
	 *            type
	 * @return boolean
	 */
	public boolean hasManagerChild(String parentId, String groupIds[], int type)
	{
		if (parentId == null)
			return true;
		List<String> list = new ArrayList<String>();
		for (String gid : groupIds) {
			list.add(gid);
		}
		List<Index> indexList = this.findChildIndexByGroupID(list, type, parentId, -1);
		if (indexList.size() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * @param rootIndex
	 *            rootIndex
	 * @param level
	 *            level
	 * @return int
	 */
	public int updateIndexLevelCascade(Index rootIndex, int level)
	{
		int count = 1;
		rootIndex.setIndexlevel(level);
		List<Index> childIndexList = this.findChildsById(rootIndex.getIndexuuid());
		for (Index index : childIndexList) {
			count += updateIndexLevelCascade(index, level + 1);
		}
		return count;
	}

	public List<Index> findChildByParentId(String parentId, int type, String appId)
	{

		return this.indexDao.findChildByParentId(parentId, type, appId);
	}

	public List<Index> getTreeIndex(List<Index> rootList, List<Index> allIndex) {
		return getResultList(rootList, allIndex);
	}
	
	/**
	 * 获取子集集合
	 * 方法说明：
	 *
	 * @param pId
	 * @param list
	 * @return
	 */
	private List<Index> getResultList(List<Index> listMenu,List<Index> allIndex){
		List<Index>  result = new ArrayList<Index>();
		for (Index index : listMenu) {
			if(index.getHasChild() == 1){
				index.setSubIndexes(findChildren(index.getIndexuuid(), allIndex));
			}
			result.add(index);
		}
		return result;
	}
	/**
	 * 获得子集
	 * 方法说明：
	 *
	 * @param pId
	 * @param allIndex
	 * @return
	 */
	private LinkedHashSet<Index> findChildren(String pId,List<Index> allIndex){
		LinkedHashSet<Index> result = new LinkedHashSet<Index>();
		for (Index index : allIndex) {
			if(index.getParentindexuuid().equals(pId)){
				index.setSubIndexes(findChildren(index.getIndexuuid(),allIndex));
				result.add(index);
			}
		}
		return result;
	}	
}
