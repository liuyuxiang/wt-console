package com.wt.hea.rbac.model;

import java.io.Serializable;

public class IndexPersonalization implements Serializable, Comparable<IndexPersonalization>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String personalizationuuid;
	
	private String userId;
	
	private String nodeId;
	
	private String viewNodeId;
	
	private Integer orderNum;
	
	private Integer showT;

	public String getPersonalizationuuid() {
		return personalizationuuid;
	}

	public void setPersonalizationuuid(String personalizationuuid) {
		this.personalizationuuid = personalizationuuid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getViewNodeId() {
		return viewNodeId;
	}

	public void setViewNodeId(String viewNodeId) {
		this.viewNodeId = viewNodeId;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}

	public Integer getShowT() {
		return showT;
	}

	public void setShowT(Integer showT) {
		this.showT = showT;
	}

	public int compareTo(IndexPersonalization o) {
		if (o != null) {
			if (this.getOrderNum() == null) {
				this.setOrderNum(0);
			}
			if (o.getOrderNum() == null) {
				o.setOrderNum(0);
			}
			return this.getOrderNum() - o.getOrderNum();
		}
		return 0;
	}
}
