package com.wt.uum2.constants;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class TaskInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 待办名称（必填）
	 */
	private String taskName;
	
	/**
	 * 待办在应用系统中的标识
	 */
	private String appTaskID;
	
	/**
	 * 待办类型
	 */
	private String taskTypeID;
	
	/**
	 * 发送者id
	 */
	private String appSendUID;
	
	/**
	 * 待办的承接人id，可以为多个，以“；”隔开
	 */
	private String appReceiveUID;
	
	/**
	 * 发送时间，格式为“yyyy-mm-dd”(必填)
	 */
	private String sendTime;
	
	/**
	 * 待办处理截止时间
	 */
	private String endTime;
	
	/**
	 * 待办处理的url
	 */
	private String url;
	
	/**
	 * 待办描述
	 */
	private String taskDesc;

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getAppTaskID() {
		return appTaskID;
	}

	public void setAppTaskID(String appTaskID) {
		this.appTaskID = appTaskID;
	}

	public String getTaskTypeID() {
		return taskTypeID;
	}

	public void setTaskTypeID(String taskTypeID) {
		this.taskTypeID = taskTypeID;
	}

	public String getAppSendUID() {
		return appSendUID;
	}

	public void setAppSendUID(String appSendUID) {
		this.appSendUID = appSendUID;
	}

	public String getAppReceiveUID() {
		return appReceiveUID;
	}

	public void setAppReceiveUID(String appReceiveUID) {
		this.appReceiveUID = appReceiveUID;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}
	

}
