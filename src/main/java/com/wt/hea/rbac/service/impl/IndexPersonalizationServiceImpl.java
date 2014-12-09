package com.wt.hea.rbac.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.model.IndexPersonalization;
import com.wt.hea.rbac.service.IndexPersonalizationService;

@Service("heaIndexPersonalizationService")
@Transactional
public class IndexPersonalizationServiceImpl extends BaseService implements
		IndexPersonalizationService {
	
	public boolean update(List<Index> indexList, String userId) {
		boolean flag = true;
		if(null!=indexList && indexList.size()>0){
			for (Index index : indexList) {
				IndexPersonalization indexPersonalization = null;
				int orderNum = index.getIndexorder();
				int showT = Integer.parseInt(null==index.getWay()?"1":index.getWay());
				String nodeId = index.getIndexuuid();
				indexPersonalization = this.indexPersonalizationDao.getIndexPersonalizationByUserIdAndNodeId(userId, nodeId);
				if(null != indexPersonalization){
					indexPersonalization.setShowT(showT);
					indexPersonalization.setOrderNum(orderNum);
				}else{
					indexPersonalization = new IndexPersonalization();
					indexPersonalization.setShowT(showT);
					indexPersonalization.setOrderNum(orderNum);
					indexPersonalization.setUserId(userId);
					indexPersonalization.setNodeId(nodeId);
				}
				IndexPersonalization result = indexPersonalizationDao.update(indexPersonalization);
				if(null == result){
					flag = false;
				}
			}
		}
		return flag;
	}

	public List<Index> getByUseridAndCategory(List<Index> freightList,
			String userId) {
		List<Index> result = null;
		if(null!=freightList && freightList.size()>0){
			result = new ArrayList<Index>();
			for (Index index : freightList) {
				String nodeId = index.getIndexuuid();
				IndexPersonalization indexPersonalization = this.indexPersonalizationDao.getIndexPersonalizationByUserIdAndNodeId(userId, nodeId);
				if(null != indexPersonalization){
					index.setWay(indexPersonalization.getShowT().toString());
					index.setIndexorder(indexPersonalization.getOrderNum());
					result.add(index);
				}else{
					result.add(index);
				}
			}
			 ComparatorIndex comparator = new ComparatorIndex();
			 Collections.sort(result, comparator);
		}
		return result;
	}
	/**
	 * 
	 * <pre>
	 * 业务名:ComparatorIndex.java
	 * 功能说明: 排序功能的内部类
	 * 编写日期:	2013-9-11
	 * 作者:	DexinWang
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	public class ComparatorIndex implements Comparator{

		public int compare(Object arg0, Object arg1) {
			Index index0 = (Index) arg0;
			Index index1 = (Index) arg1;
			int flag = index0.getIndexorder().compareTo(index1.getIndexorder());
			if (flag == 0) {
				return index0.getCreateTime().compareTo(index1.getCreateTime());
			} else {
				return flag;
			}
		}
	}
}
