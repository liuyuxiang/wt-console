package com.wt.uum2.web.wicket.panel.system;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.form.AjaxRefreshFeedbackForm;
import com.wt.uum2.domain.ServerLog;
import com.wt.uum2.service.UUMService;
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
public class AppStatusPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4120166213071001681L;

	private ServerLog serverLog;

	/**
	 * @param id
	 *            id
	 */
	public AppStatusPanel(String id)
	{
		super(id);
		initForm();
	}

	/**
	 * 方法说明：initForm
	 * 
	 */
	private void initForm()
	{
		UUMService uumService = getUUMService();

		serverLog = uumService.getLastServerLog();

		Form<ServerLog> appStatusForm = new AjaxRefreshFeedbackForm<ServerLog>("serverLog",
				new CompoundPropertyModel<ServerLog>(serverLog));
		add(appStatusForm);

		Label projectId = new Label("projectId");
		Label projectVer = new Label("projectVer");
		Label handleTime = new Label("handleTime");
		Label ip = new Label("ip");
		Label osMsg = new Label("osMsg", new Model<String>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public String getObject()
			{
				String osMessage = serverLog.getOsName() + " " + serverLog.getOsVersion() + " "
						+ serverLog.getOsArch();
				return osMessage;
			}

		});
		Label userDir = new Label("userDir");
		Label javaHome = new Label("javaHome");
		Label javaVersion = new Label("javaVersion");

		appStatusForm.add(projectId);

		appStatusForm.add(projectVer);

		appStatusForm.add(handleTime);

		appStatusForm.add(ip);

		appStatusForm.add(osMsg);

		appStatusForm.add(userDir);

		appStatusForm.add(javaHome);

		appStatusForm.add(javaVersion);

	}

}
