package com.wt.hea.common.model;

import javax.servlet.http.HttpServletRequest;

import com.hirisun.components.data.DateUtils;
import com.hirisun.hea.api.domain.User;
import com.wt.hea.common.util.WebUtil;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 系统日志实体对象,登陆日志，用户点击指标的日志都通过此对象存取
 * 编写日期:	2011-4-6
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */

public class BehaveLog
{
	/**
	 * 日志id(为流水id不需自行维护)
	 */
	private String logid;
	/**
	 * 用户id
	 */
	private String useruuid;
	/**
	 * 用户名称
	 */
	private String username;
	/**
	 * 指标id
	 */
	private String indexuuid;
	/**
	 * 指标名称
	 */
	private String indexname;
	/**
	 * 访问的ip
	 */
	private String accessip;
	/**
	 * 访问时间
	 */
	private String accesstime;
	/**
	 * 日志访问类型
	 */
	private String accesstype;
	/**
	 * 用户行为备注（请必须填写）
	 */
	private String remark;

	/**
	 * 应用id号
	 */
	private String appId;

	/**
	 * 应用名称
	 */
	private String appName;

	public String getUseruuid()
	{
		return useruuid;
	}

	public void setUseruuid(String useruuid)
	{
		this.useruuid = useruuid;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String username)
	{
		this.username = username;
	}

	public String getIndexname()
	{
		return indexname;
	}

	public void setIndexname(String indexname)
	{
		this.indexname = indexname;
	}

	public String getAccessip()
	{
		return accessip;
	}

	public void setAccessip(String accessip)
	{
		this.accessip = accessip;
	}

	public String getAccesstime()
	{
		return accesstime;
	}

	public void setAccesstime(String accesstime)
	{
		this.accesstime = accesstime;
	}

	public String getAccesstype()
	{
		return accesstype;
	}

	public void setAccesstype(String accesstype)
	{
		this.accesstype = accesstype;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

	public String getLogid()
	{
		return logid;
	}

	public void setLogid(String logid)
	{
		this.logid = logid;
	}

	public String getIndexuuid()
	{
		return indexuuid;
	}

	public void setIndexuuid(String indexuuid)
	{
		this.indexuuid = indexuuid;
	}

	/**
	 * 默认构造
	 */
	public BehaveLog()
	{
	}

	/**
	 * 重载构造方便操作日志属性
	 * 
	 * @param useruuid
	 *            用户id
	 * @param userName
	 *            用户名
	 * @param accessIp
	 *            客户端ip
	 * @param accessTime
	 *            访问时间
	 * @param reMark
	 *            备注
	 * @param logType
	 *            日志类型
	 * @param indexuuid
	 *            指标id
	 * @param indexName
	 *            指标名称
	 */
	public BehaveLog(String useruuid, String userName, String accessIp, String accessTime,
			String reMark, String logType, String indexuuid, String indexName)
	{
		this.username = userName;
		this.useruuid = useruuid;
		this.accessip = accessIp;
		this.accesstime = accessTime;
		this.indexname = indexName;
		this.indexuuid = indexuuid;
		this.remark = reMark;
		this.accesstype = logType;
	}

	/**
	 * 关注日志的构造
	 * 
	 * @param reMark
	 *            用户操作的事项
	 * @param request
	 *            request 用于获得用户及客户ip地址
	 */
	public BehaveLog(String reMark, HttpServletRequest request)
	{
		User user = WebUtil.getSessionUser(request);
		this.accessip = WebUtil.getIpAddr(request);
		this.accesstime = DateUtils.getCurrDate("yyyy-MM-dd HH:dd:ss");
		this.remark = reMark;
		this.username = user.getName();
		this.useruuid = user.getUuid();
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

}
