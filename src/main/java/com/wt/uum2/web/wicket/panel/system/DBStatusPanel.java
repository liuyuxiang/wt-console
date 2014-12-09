package com.wt.uum2.web.wicket.panel.system;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.wt.uum2.service.DBInfoService;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DBStatusPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4120166213071001681L;

	@SpringBean
	private DBInfoService dbInfoService;

	/**
	 * @param id
	 *            id
	 */
	public DBStatusPanel(String id)
	{
		super(id);
		initForm();
	}

	/**
	 * 方法说明：defaultIfEmpty
	 * 
	 * @param str
	 *            str
	 * @return String
	 */
	private String defaultIfEmpty(String str)
	{
		return StringUtils.defaultIfEmpty(str, "NULL");
	}

	/**
	 * 方法说明：initForm
	 * 
	 */
	private void initForm()
	{
		Map<String, String> map = dbInfoService.getDBinfo();

		Label serverName = new Label("serverName", defaultIfEmpty(map.get("serverHost")) + "("
				+ map.get("serverIp") + ")");
		Label instanceName = new Label("instanceName", defaultIfEmpty(map.get("instanceName")));
		Label user = new Label("user", defaultIfEmpty(map.get("user")));
		Label roles = new Label("roles", defaultIfEmpty(map.get("roles")));
		Label tablespace = new Label("tablespace", defaultIfEmpty(map.get("tablespace")));
		Label clientIp = new Label("clientIp", defaultIfEmpty(map.get("clientIp")));

		Label dbaWarning = new Label("dbaWarning", "当前用户有DBA角色的权限，请撤消此角色！");

		boolean dba = false;
		for (String role : map.get("roles").split(",")) {
			if (role.equalsIgnoreCase("dba")) {
				dba = true;
				break;
			}
		}

		dbaWarning.setVisible(dba);

		add(serverName);

		add(instanceName);

		add(user);

		add(roles);

		add(tablespace);

		add(clientIp);

		add(dbaWarning);

	}

}
