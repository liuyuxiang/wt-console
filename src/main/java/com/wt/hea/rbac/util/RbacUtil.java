package com.wt.hea.rbac.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.service.RBACService;
import com.hirisun.hea.api.domain.Department;
import com.hirisun.hea.api.domain.Group;

/**
 * 
 * <pre>
 * 业务名:权限工具类
 * 功能说明: 
 * 编写日期:	2011-3-31
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class RbacUtil
{
	/**
	 * 
	 * 方法说明：在一组资源中获取根级ID
	 * 
	 * @param indexList 指标列表
	 * @return 根级指标id
	 */
	public static List<String> getRootIndexId(List<Index> indexList)
	{
		Set<String> rootIdSet = new HashSet<String>();
		List<String> rootIdList = new ArrayList<String>();
		boolean isRoot = false;
		for (Index i : indexList) {
			isRoot = true;
			for (Index j : indexList) {
				if (i.getParentindexuuid() != null) {
					if (i.getParentindexuuid().equals(j.getIndexuuid())) {
						isRoot = false;
						break;
					}
				}
			}
			if (isRoot) {
				rootIdSet.add(i.getIndexuuid());
			}
		}
		for (String id : rootIdSet) {
			rootIdList.add(id);
		}
		return rootIdList;
	}

	/**
	 * 
	 * 方法说明：在一组部门获取根级ID
	 * 
	 * @param departmentList 部门列表
	 * @return 部门根级id
	 */
	public static List<String> getRootDepartmentId(List<Department> departmentList)
	{
		Set<String> rootIdSet = new HashSet<String>();
		List<String> rootIdList = new ArrayList<String>();
		boolean isRoot = false;
		for (Department i : departmentList) {
			isRoot = true;
			for (Department j : departmentList) {
				if (i.getParentUUID() != null) {
					if (i.getParentUUID().equals(j.getUuid())) {
						isRoot = false;
						break;
					}
				}
			}
			if (isRoot) {
				rootIdSet.add(i.getUuid());
			}
		}
		for (String id : rootIdSet) {
			rootIdList.add(id);
		}
		return rootIdList;
	}
	/**
	 * 
	 * 方法说明：在组中获取根级ID
	 * 
	 * @param groupList 组列表
	 * @param rbacService 权限管理Service
	 * @return 根级组id
	 */
	public static List<String> getRootGroupId(List<Group> groupList, RBACService rbacService)
	{
		Set<String> rootIdSet = new HashSet<String>();
		List<String> rootIdList = new ArrayList<String>();
		boolean isRoot = false;
		Set<String> parentGroupIds = new HashSet<String>();
		Map<String, List<Group>> map = new HashMap<String, List<Group>>();
		// 汇总所有父级组
		for (Group i : groupList) {
			List<Group> parentGroupList = rbacService.getParentGroup(i.getUuid());
			map.put(i.getUuid(), parentGroupList);
			for (Group p : parentGroupList) {
				parentGroupIds.add(p.getUuid());
			}
		}
		for (Group i : groupList) {
			isRoot = true;
			for (String pid : parentGroupIds) {
				for (Group ipGroup : map.get(i.getUuid())) {
					if (ipGroup.getUuid().equals(pid)) {
						isRoot = false;
						break;
					}
				}
			}
			if (isRoot) {
				rootIdSet.add(i.getUuid());
			}
		}
		for (String id : rootIdSet) {
			rootIdList.add(id);
		}
		return rootIdList;
	}

}
