package com.wt.hea.rbac.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.wt.hea.common.dao.impl.AbstractHibernateDaoSupport;
import com.wt.hea.rbac.dao.IndexPersonalizationDao;
import com.wt.hea.rbac.model.IndexPersonalization;

public class IndexPersonalizationDaoImpl extends AbstractHibernateDaoSupport<IndexPersonalization>
		implements IndexPersonalizationDao {

	public boolean deleteById(Serializable id) {
		return super.deleteById(id);
	}

	public IndexPersonalization findById(Serializable id) {
		return super.findById(id);
	}

	public boolean save(IndexPersonalization e) {
		return super.save(e);
	}
	

	public IndexPersonalization update(IndexPersonalization indexPersonalization) {
		return super.update(indexPersonalization);
	}

	public IndexPersonalization getIndexPersonalizationByUserIdAndNodeId(
			String userId, String nodeId) {
		IndexPersonalization result = null;
		IndexPersonalization indexPersonalization = new IndexPersonalization();
		indexPersonalization.setUserId(userId);
		indexPersonalization.setNodeId(nodeId);
		List<IndexPersonalization> indexPersonalizationList = super.findByExample(indexPersonalization);
		if(null!=indexPersonalizationList && indexPersonalizationList.size()>0){
			result = indexPersonalizationList.get(0);
		}
		return result;
	}

}
