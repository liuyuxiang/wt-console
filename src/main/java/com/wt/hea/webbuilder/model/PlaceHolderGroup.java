package com.wt.hea.webbuilder.model;

/**
 * 占位符跟组的权限设计
 * @author xiaoqi
 *
 */
public class PlaceHolderGroup {

	/**
	 * 
	 */
	private String pgId ;

	/**
	 * 占位符ID
	 */
	private String placeHolderId ;
	
	/**
	 * 组ID
	 */
	private String groupId ;
	
	/**
	 * 操作，分别用0、1标识
	 * 0不可操作
	 * 1可操作
	 */
	private String tmplId ;

	public String getPlaceHolderId() {
		return placeHolderId;
	}

	public void setPlaceHolderId(String placeHolderId) {
		this.placeHolderId = placeHolderId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	public String getTmplId() {
		return tmplId;
	}

	public void setTmplId(String tmplId) {
		this.tmplId = tmplId;
	}

	public String getPgId() {
		return pgId;
	}
	
	public void setPgId(String pgId) {
		this.pgId = pgId;
	}
}
