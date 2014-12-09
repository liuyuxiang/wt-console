package com.wt.uum2.domain;

import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Richard
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class SyncErrorLog implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4372152829058221199L;

	/**
	 * 
	 */
	private String uuid;
	/**
	 * 
	 */
	private String errorMessage;

	/**
	 * 
	 */
	private SyncLog syncLog;

	/**
	 * 
	 */
	public SyncErrorLog()
	{
		super();
	}

	/**
	 * @param syncLog
	 *            syncLog
	 * @param string
	 *            string
	 */
	public SyncErrorLog(SyncLog syncLog, String string)
	{
		this();
		setSyncLog(syncLog);
		addErrorMessage(string);
	}

	/**
	 * @param syncLog
	 *            syncLog
	 * @param e
	 *            e
	 */
	public SyncErrorLog(SyncLog syncLog, Exception e)
	{
		this();
		final Writer result = new StringWriter();
		e.printStackTrace(new PrintWriter(result));

		setSyncLog(syncLog);
		addErrorMessage(result.toString());
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
	public void setUuid(String uuid)
	{
		this.uuid = uuid;
	}

	/**
	 * @return ssssssss
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}

	/**
	 * 方法说明：setErrorMessage
	 * 
	 * @param errorMessage
	 *            errorMessage
	 */
	public void setErrorMessage(String errorMessage)
	{
		addErrorMessage(errorMessage);
	}

	/**
	 * @param errorMessage
	 *            the errorMessage to set
	 */
	public void addErrorMessage(String errorMessage)
	{
		if (StringUtils.isNotBlank(this.errorMessage)) {
			this.errorMessage = this.errorMessage
					+ "\n>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>\n" + errorMessage;
		} else {
			this.errorMessage = errorMessage;
		}
	}

	/**
	 * @return ssssssss
	 */
	public SyncLog getSyncLog()
	{
		return syncLog;
	}

	/**
	 * @param syncLog
	 *            the syncLog to set
	 */
	public void setSyncLog(SyncLog syncLog)
	{
		this.syncLog = syncLog;
	}

}
