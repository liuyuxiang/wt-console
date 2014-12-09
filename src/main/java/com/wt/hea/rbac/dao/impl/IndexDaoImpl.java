package com.wt.hea.rbac.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SQLQuery;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.rbac.dao.IndexDao;
import com.wt.hea.rbac.model.Index;

/**
 * "指标"数据访问对象
 * 
 * @author 袁明敏
 * @see BaseDao<T>
 * @since 1.0
 * 
 */

public class IndexDaoImpl extends AbstractHibernateDaoSupport<Index> implements IndexDao
{
	/**
	 * 
	 */
	private static final Log LOG = LogFactory.getLog(IndexDaoImpl.class);

	/**
	 * 返回当前结点的所有下级结点(多层)
	 */
	/*@SuppressWarnings("unchecked")
	public List<Index> findByChildsById(Serializable id)
	{
		Index index = super.findById(id);
		List<Index> list = super.getSession().createCriteria(Index.class).add(
				Expression.gt("indexlevel", index.getIndexlevel())).add(
				Expression.like("levelCode", index.getLevelCode(), MatchMode.START)).list();

		return list;
	}*/

	/**
	 * @param uuid
	 *            uuid
	 * @return List<Index>
	 */
	public List<Index> findByChildsById(Serializable uuid)
	{
		LOG.debug("finding findByChildById instance with id: " + uuid);
		try {
			Set<Index> set = new HashSet<Index>();
			List<Index> list = new ArrayList<Index>();
			Index instance = (Index) getSession().get(Index.class, uuid);
			if (instance.getSubIndexes().size() > 0) {
				Iterator<Index> it = instance.getSubIndexes().iterator();
				while (it.hasNext()) {
					Index temp = it.next();
					list.add(temp);
				}
			} else {
				// 返回之前过滤相同对象
				for (Index i : list) {
					set.add(i);
				}
				List<Index> reList = new ArrayList<Index>();
				Iterator<Index> it = set.iterator();
				while (it.hasNext()) {
					reList.add(it.next());
				}
				list = null;
				set = null;
				return reList;
			}
			for (int i = 0; i < list.size(); i++) {
				Index temp = list.get(i);
				list.addAll(findByChildsById(temp.getIndexuuid()));
			}

			// 返回之前过滤相同对象
			for (Index i : list) {
				set.add(i);
			}
			List<Index> reList = new ArrayList<Index>(0);
			Iterator<Index> it = set.iterator();
			while (it.hasNext()) {
				reList.add(it.next());
			}
			list = null;
			set = null;

			return reList;
		} catch (RuntimeException re) {
			LOG.error("get failed", re);
			throw re;
		}

	}

	/**
	 * @param index
	 *            index
	 */
	public void delete(Index index)
	{
		LOG.debug("deleting Index instance");
		try {
			super.getSession().clear();
			super.delete(index);
			LOG.debug("delete successful");
		} catch (RuntimeException re) {
			LOG.error("delete failed", re);
			throw re;
		}
		this.deleteIndexIG(index.getIndexuuid());
	}

	/**
	 * @param id
	 *            id
	 * @return boolean
	 */
	public boolean deleteById(Serializable id)
	{
		try {
			this.deleteIndexIG(id.toString());
			return super.deleteById(id);
		} catch (RuntimeException ex) {
			ex.printStackTrace();
		}
		return false;
	}

	/**
	 * @param id
	 *            id
	 * @return Index
	 */
	public Index findById(Serializable id)
	{
		LOG.debug("getting Index instance with id: " + id);
		try {
			return super.findById(id);
		} catch (RuntimeException re) {
			LOG.error("get failed", re);
			throw re;
		}
	}

	/**
	 * @return List<Index>
	 */
	public List<Index> findAll()
	{
		LOG.debug("finding all Index instances");
		try {
			return super.findAll();
		} catch (RuntimeException re) {
			LOG.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * @param property
	 *            property
	 * @param isAsc
	 *            isAsc
	 * @return List<Index>
	 */
	public List<Index> findAll(String property, Boolean isAsc)
	{
		return super.findAll(property, isAsc);
	}

	/**
	 * @param property
	 *            property
	 * @param value
	 *            value
	 * @return List<Index>
	 */
	public List<Index> findByProperty(String property, Object value)
	{
		return super.findByProperty(property, value);
	}

	/**
	 * 
	 */
	public void deleteAll()
	{
		super.deleteAll();

	}

	/**
	 * @param ids
	 *            ids
	 */
	public void deleteByIds(Serializable... ids)
	{
		super.deleteByIds(ids);
		for (Serializable i : ids) {
			this.deleteIndexIG(i.toString());
		}
	}

	/**
	 * @param ids
	 *            ids
	 * @return List<Index>
	 */
	public List<Index> findByIds(Serializable... ids)
	{
		return super.findByIds(ids);
	}

	/**
	 * @param uuid
	 *            uuid
	 * @return String
	 */
	public String createLevelCodeById(Serializable uuid)
	{
		Index index = super.findById(uuid);
		index.getIndexlevel();
		List<Index> list = super
				.executeHqlQuery("select new Index(levelCode) from Index as i where indexlevel > "
						+ index.getIndexlevel() + " and levelCode like '" + index.getLevelCode()
						+ "%'");
		List<Double> levels = new ArrayList<Double>();
		for (Index i : list) {
			levels.add(Double.valueOf(i.getLevelCode().replace(index.getLevelCode(), "")));
		}
		if (list.size() == 0) {
			return index.getLevelCode() + "10000";
		} else {
			Double[] temp = new Double[levels.size()];
			Arrays.sort(levels.toArray(temp));
			return index.getLevelCode() + (temp[temp.length - 1].intValue() + 1) + "";

		}
	}

	/**
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            type
	 * @return List<Index>
	 */
	@SuppressWarnings("unchecked")
	public List<Index> findIndexByGroupID(List<String> groupIds, int type)
	{
		String sql = "SELECT distinct I.* FROM LFS_INDEX I , LFS_IG IG WHERE IG.GROUPUUID in (:groupIds) AND I.INDEXTYPE ="
				+ type + " AND I.INDEXUUID = IG.INDEXUUID ORDER BY I.INDEXORDER";
		return this.getSession().createSQLQuery(sql).addEntity(Index.class)
				.setParameterList("groupIds", groupIds).list();
	}

	/**
	 * @param groupIds
	 *            groupIds
	 * @return List<Index>
	 */
	@SuppressWarnings("unchecked")
	public List<Index> findIndexByGroupID(List<String> groupIds)
	{
		String sql = "SELECT distinct I.* FROM LFS_INDEX I , LFS_IG IG WHERE IG.GROUPUUID in (:groupIds)  AND I.INDEXUUID = IG.INDEXUUID ORDER BY I.INDEXORDER";
		return this.getSession().createSQLQuery(sql).addEntity(Index.class)
				.setParameterList("groupIds", groupIds).list();
	}

	/**
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            type
	 * @param parentIndexId
	 *            parentIndexId
	 * @param state
	 *            state
	 * @return List<Index>
	 */
	@SuppressWarnings("unchecked")
	public List<Index> findChildIndexByGroupID(List<String> groupIds, int type,
			String parentIndexId, int state)
	{
		String sql = "SELECT distinct I.* FROM LFS_INDEX I , LFS_IG IG WHERE IG.GROUPUUID in (:groupIds) AND I.INDEXTYPE ="
				+ type
				+ " AND I.INDEXUUID = IG.INDEXUUID AND I.PARENTINDEXUUID='"
				+ parentIndexId
				+ "'" + (state == -1 ? "" : (" AND WAY=" + state)) + " ORDER BY I.INDEXORDER";
		return this.getSession().createSQLQuery(sql).addEntity(Index.class)
				.setParameterList("groupIds", groupIds).list();
	}

	/**
	 * @param parentId
	 *            parentId
	 * @param type
	 *            type
	 * @return List<Index>
	 */
	public List<Index> findChildByParentId(String parentId, int type)
	{
		String hql = "from com.wt.hea.rbac.model.Index i where i.parentindexuuid='" + parentId
				+ "' and i.indextype=" + type + " order by i.indexorder";
		return this.executeHqlQuery(hql);
	}

	/**
	 * @param groupIds
	 *            groupIds
	 * @param type
	 *            type
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	@SuppressWarnings("unchecked")
	public List<Index> findIndexByGroupID(List<String> groupIds, int type, String appId)
	{
		String sql = "SELECT distinct I.* FROM LFS_INDEX I , LFS_IG IG WHERE IG.GROUPUUID in (:groupIds) AND I.INDEXTYPE ="
				+ type
				+ " AND I.APP_ID=:appId AND I.INDEXUUID = IG.INDEXUUID ORDER BY I.INDEXORDER";

		List<Index> indexes = this.getSession().createSQLQuery(sql).addEntity(Index.class)
				.setParameterList("groupIds", groupIds).setString("appId", appId).list();

		return indexes;
	}

	/**
	 * @param type
	 *            type
	 * @return List<Index>
	 */
	public List<Index> findAll(int type)
	{
		String hql = "from com.wt.hea.rbac.model.Index i where way = '1' and i.indextype="
				+ type + "  order by i.indexorder";
		return this.executeHqlQuery(hql);
	}

	/**
	 * @param type
	 *            type
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	public List<Index> findAll(int type, String appId)
	{
		String hql = "from com.wt.hea.rbac.model.Index i where i.indextype=" + type
				+ " and i.appId='" + appId + "'  order by i.indexorder";
		return this.executeHqlQuery(hql);
	}

	/**
	 * @param indexId
	 *            indexId
	 * @param groupIds
	 *            groupIds
	 */
	public void addIndexGroup(String indexId, String[] groupIds)
	{
		String sql = null;
		for (String groupId : groupIds) {
			sql = "INSERT INTO LFS_IG(INDEXUUID,GROUPUUID) VALUES('" + indexId + "','" + groupId
					+ "')";
			this.executeSql(sql);
		}

	}

	/**
	 * @param indexIds
	 *            indexIds
	 * @param groupId
	 *            groupId
	 */
	public void addIndexGroup(String[] indexIds, String groupId)
	{
		String sql = null;
		for (String indexId : indexIds) {
			sql = "INSERT INTO LFS_IG(INDEXUUID,GROUPUUID) VALUES('" + indexId + "','" + groupId
					+ "')";
			this.executeSql(sql);
		}

	}

	/**
	 * @param indexId
	 *            indexId
	 * @param groupIds
	 *            groupIds
	 */
	public void deleteIndexGroup(String indexId, String[] groupIds)
	{
		if (groupIds == null || groupIds.length == 0)
			return;
		StringBuffer sql = new StringBuffer("DELETE FROM IndexGroups WHERE indexuuid='");
		sql.append(indexId);
		sql.append("' AND groupuuid IN(");
		;
		for (String groupId : groupIds) {
			sql.append("'").append(groupId).append("',");
		}
		if (groupIds != null && groupIds.length > 0) {
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(")");
		this.executeHql(sql.toString());
		// this.executeSql(sql.toString());
	}

	/**
	 * @param indexIds
	 *            indexIds
	 * @param groupId
	 *            groupId
	 */
	public void deleteIndexGroup(String[] indexIds, String groupId)
	{
		if (indexIds == null || indexIds.length == 0)
			return;
		StringBuffer sql = new StringBuffer("DELETE FROM IndexGroups WHERE groupuuid='");
		sql.append(groupId);
		sql.append("' AND indexuuid IN(");
		;
		for (String indexId : indexIds) {
			sql.append("'").append(indexId).append("',");
		}
		if (indexIds != null && indexIds.length > 0) {
			sql.deleteCharAt(sql.length() - 1);
		}
		sql.append(")");
		this.executeHql(sql.toString());
	}

	/**
	 * @param indexId
	 *            indexId
	 * @param groupIds
	 *            groupIds
	 */
	public void updateIndexGroup(String indexId, String[] groupIds)
	{
		String sql = null;
		sql = "DELETE FROM LFS_IG IG WHERE IG.indexuuid='" + indexId + "'";
		this.executeSql(sql);
		for (String groupId : groupIds) {
			sql = "INSERT INTO LFS_IG(INDEXUUID,GROUPUUID) VALUES('" + indexId + "','" + groupId
					+ "')";
			this.executeSql(sql);
		}
	}

	/**
	 * @param indexIds
	 *            indexIds
	 * @param groupId
	 *            groupId
	 */
	public void updateIndexGroup(String[] indexIds, String groupId)
	{
		String sql = null;
		sql = "DELETE FROM IndexGroups IG WHERE IG.groupuuid='" + groupId + "'";
		this.executeHql(sql);
		for (String indexId : indexIds) {
			sql = "INSERT INTO LFS_IG(INDEXUUID,GROUPUUID) VALUES('" + indexId + "','" + groupId
					+ "')";
			this.executeSql(sql);
		}
	}

	/**
	 * @param indexId
	 *            indexId
	 * @return List<String>
	 */
	@SuppressWarnings("rawtypes")
	public List<String> findGroupIdByIndexID(String indexId)
	{
		String sql = "SELECT GROUPUUID FROM LFS_IG WHERE INDEXUUID = '" + indexId + "'";
		List ids = this.executeSqlQuery(sql);
		List<String> idList = new ArrayList<String>();
		for (Object id : ids) {
			idList.add(id.toString());
		}
		return idList;
	}

	/**
	 * @param type
	 *            type
	 * @return Index
	 */
	public Index findRootIndex(int type)
	{
		String hql = "from com.wt.hea.rbac.model.Index t where t.parentindexuuid='index_root' and t.indextype="
				+ type;
		List<Index> indexList = this.executeHqlQuery(hql);
		if (indexList != null && indexList.size() == 1) {
			return indexList.get(0);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.wt.hea.rbac.dao.IndexDao#findRootIndex(int, java.lang.String)
	 */
	/** {@inheritDoc} */
	public Index findRootIndex(int type, String appId)
	{
		String hql = "from com.wt.hea.rbac.model.Index t where t.parentindexuuid='index_root' and t.indextype="
				+ type;
		List<Index> indexList = this.executeHqlQuery(hql);
		if (indexList != null && indexList.size() == 1) {
			return indexList.get(0);
		}
		return null;
	}

	/**
	 * @param indexUuid
	 *            indexUuid
	 */
	public void deleteIndexIG(String indexUuid)
	{
		String hql = "delete IndexGroups ig where ig.indexuuid='" + indexUuid + "'";
		this.executeHql(hql);
	}

	/**
	 * @param parentId
	 *            parentId
	 * @param type
	 *            type
	 * @return boolean
	 */
	public boolean hasChild(String parentId, int type)
	{
		String sql = "select count(*) from LFS_INDEX ig where ig.parentindexuuid='" + parentId
				+ "' and ig.way = '1' and ig.indextype=" + type + "";
		SQLQuery query = this.getSession().createSQLQuery(sql);
		Long count = Long.valueOf(query.uniqueResult().toString());
		return count > 0 ? true : false;
	}

	public List<Index> findChildByParentId(String parentId, int type, String appId)
	{
		String hql = "from com.wt.hea.rbac.model.Index i where i.parentindexuuid='" + parentId
				+ "' and i.indextype=" + type + " and i.appId='" + appId
				+ "' order by i.indexorder";
		return this.executeHqlQuery(hql);
	}

}
