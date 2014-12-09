package com.wt.uum2.domain;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SyncLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1554455254874360847L;

	/**
	 * 成功
	 */
	public static final int SUCCESSFUL = 1;

	/**
	 * 失败
	 */
	public static final int FAILED = 0;

	/**
	 * 警告
	 */
	public static final int WARING = 2;

	/**
	 * @param appId
	 *            appId
	 */
	public SyncLog(String appId)
	{
		this();
		setAppId(appId);
	}

	/**
	 * 
	 */
	public SyncLog()
	{
		super();
		setHandleTime(Calendar.getInstance().getTime());
		this.status = SyncLog.SUCCESSFUL;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SyncLog other = (SyncLog) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}

	/**
	 * 主键
	 */
	private String uuid;
	/**
	 * 
	 */
	private String eventuuid;
	/**
	 * 
	 */
	private String appId;
	/**
	 * 
	 */
	private Date handleTime;
	/**
	 * 0正常,1异常,2警告
	 */
	private int status;

	/**
	 * 
	 */
	private SyncErrorLog syncErrorLog;

	/**
	 * @return ssssssss
	 */
	public SyncErrorLog getSyncErrorLog()
	{
		return syncErrorLog;
	}

	/**
	 * @param syncErrorLog
	 *            the syncErrorLog to set
	 */
	public void setSyncErrorLog(SyncErrorLog syncErrorLog)
	{
		this.syncErrorLog = syncErrorLog;
	}

	/**
	 * 方法说明：addSyncMessage
	 * 
	 * @param message
	 *            message
	 */
	public void addSyncMessage(String message)
	{
		if (this.syncErrorLog == null) {
			this.syncErrorLog = new SyncErrorLog(this, message);
		} else {
			SyncErrorLog temp = this.syncErrorLog;
			temp.addErrorMessage(message);
			this.syncErrorLog = temp;
		}
	}

	/**
	 * 方法说明：addSyncMessage
	 * 
	 * @param status
	 *            status
	 * @param message
	 *            message
	 */
	public void addSyncMessage(int status, String message)
	{
		setStatus(status);
		addSyncMessage(message);
	}

	/**
	 * 方法说明：addSyncMessage
	 * 
	 * @param status
	 *            status
	 * @param e
	 *            e
	 */
	public void addSyncMessage(int status, Exception e)
	{
		final Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));

		addSyncMessage(status, result.toString());

	}

	/**
	 * @return ssssssss
	 */
	public String getUuid()
	{
		return uuid;
	}

	/**
	 * @param uuid
	 *            the uuid to set
	 */
	protected void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getEventuuid()
	{
		return eventuuid;
	}

	/**
	 * @param eventuuid
	 *            the eventuuid to set
	 */
	public void setEventuuid(String eventuuid)
	{
		this.eventuuid = eventuuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getAppId()
	{
		return appId;
	}

	/**
	 * @param appId
	 *            the appId to set
	 */
	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	/**
	 * @return ssssssss
	 */
	public Date getHandleTime()
	{
		return handleTime;
	}

	/**
	 * @param handleTime
	 *            the handleTime to set
	 */
	public void setHandleTime(Date handleTime)
	{
		this.handleTime = handleTime;
	}

	public int getStatus()
	{
		return status;
	}

	/**
	 * 方法说明：setStatus
	 * 
	 * @param status
	 *            status
	 */
	public void setStatus(int status)
	{
		switch (status) {
		case FAILED:
		{
			if(this.status!=SyncLog.FAILED){
				this.status = status;
			}
		}
			break;

		case WARING:
		{
			if(this.status==SyncLog.SUCCESSFUL){
				this.status = status;
			}
		}
			break;

		default:
			this.status = status;
			break;
		}
	}

}
