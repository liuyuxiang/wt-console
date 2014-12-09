package com.wt.uum2.web.wicket.page.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.PanelCachingTab;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.form.AjaxRefreshFeedbackForm;
import com.hirisun.components.web.wicket.tabs.AjaxKeepFormStatusTabbedPanel;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.user.ModifyInfoPanel;
import com.wt.uum2.web.wicket.panel.user.UserAttributePanel;

/**
 * <pre>
 * 业务名:创建用户页面
 * 功能说明: 创建用户页面
 * 编写日期:	2011-3-5
 * 作者:fanglei
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserInfoPage extends UUMBasePage {

	/**
	 * tab切换
	 */
	private AjaxKeepFormStatusTabbedPanel ajaxTabbedPanel;

	/**
	 * 用户通讯信息扩展属性面板
	 */
	private ModifyInfoPanel userAttrInfoPanel1;

	/**
	 * 
	 */
	private User user;
	
	/**
	 * 
	 */
	private Department dept;

	/**
	 * 表单
	 */
	private Form<Void> form;
	
	/**
	 * 
	 */
	private UserListPage gobackPage;
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -8853033422313124604L;

	/**
	 * 
	 */
	public UserInfoPage() {
		
		user = getUUMService().getLoginUser();
		if (isNew(user)) {
				throw new RuntimeException("");
		}else{
			this.dept=getUUMService().getUserPrimaryDepartment(user);
		}
		
		initForm();

	}

	/**
	 * @param u
	 *            用户
	 * @param d
	 *            部门
	 */
	public UserInfoPage(User u,Department d){
		
		this.user=u;
		
		if (isNew(user)) {
			if(d==null){
				throw new RuntimeException("");
			}
			dept =d;
		}else{
			dept=getUUMService().getUserPrimaryDepartment(user);
		}
		initForm();
	}

	/**
	 * 方法说明：初始化表单
	 * 
	 */
	public void initForm(){
		form=new AjaxRefreshFeedbackForm<Void>("form");

		add(form);
		List<ITab> tabs = new ArrayList<ITab>();

		tabs.add(new PanelCachingTab(new AbstractTab(
				new Model<String>("")) {
			@Override
			public Panel getPanel(String panelId) {
				userAttrInfoPanel1 = new ModifyInfoPanel(panelId, user, "1");
				revisabilityUserType(userAttrInfoPanel1,loginUser,user);
				return userAttrInfoPanel1;
			}
		}));
		ajaxTabbedPanel = new AjaxKeepFormStatusTabbedPanel("tabs", tabs, form){
			@Override
			public void renderHead(IHeaderResponse response)
			{
				super.renderHead(response);
				response.renderOnLoadJavaScript("fitheight(window)");
			}
		};

		form.add(ajaxTabbedPanel);

		AjaxWaitingButton ajaxButton = new AjaxWaitingButton("submit") {

			@Override
			protected void onSubmit(final AjaxRequestTarget target, Form<?> form) {
				target.add(this.setEnabled(true));

					Map<String, String[]> map = new HashMap<String, String[]>();

					List<Event> events = new ArrayList<Event>();

					if (userAttrInfoPanel1 != null) {
						saveAttributes(userAttrInfoPanel1
								.getUserAttrListPanel().getAttributePanels(),user);
						fillChangedAttrToMap(userAttrInfoPanel1
								.getUserAttrListPanel().getAttributePanels(),
								map);
					}

					if (!map.isEmpty()) {
						events.add(getEventFactory().createEventUpdateUser(
								user.getUuid(), map));
					}
					getEventListenerHandler().handle(events);
					
				setResponsePage(new UserInfoPage(user,dept));
			}

			@Override
			protected void onError(final AjaxRequestTarget target, Form<?> form) {
				target.add(this.setEnabled(true));
			}

		};

		ajaxButton.setDefaultFormProcessing(true);
		ajaxButton.setOutputMarkupId(true);
		form.add(ajaxButton);
		add(form);
		
	}

	/**
	 * 方法说明：新增用户时创建产生的扩展属性
	 * 
	 * @param panels
	 *            扩展属性面板
	 * @param user
	 *            用户
	 */
	private void saveAttributes(List<UserAttributePanel> panels, User user) {
		for (UserAttributePanel p : panels) {
			Attribute attribute = p.getAttribute();
			if (!attribute.getType().getHidden() && p.isChanged()) {
				if (p.isNew()) {
					attribute.setOwnerResource(user);
					getUUMService().saveAttribute(attribute);
				} else if (p.isDelete()) {
					getUUMService().deleteAttribute(attribute);
				} else {
					getUUMService().updateAttribute(attribute);
				}
			}
		}
	}

	/**
	 * 方法说明：判断扩展属性值是否改变，改变则添加到map
	 * 
	 * @param panels
	 *            扩展属性面板
	 * @param cMap
	 *            变化值对
	 */
	private void fillChangedAttrToMap(List<UserAttributePanel> panels,
			Map<String, String[]> cMap) {

		for (UserAttributePanel p : panels) {
			if (p.isChanged()) {
				cMap.put(p.getAttribute().getType().getId(),
						new String[] { p.getOldValue(),
								p.getAttribute().getValue() });
			}
		}
	}

}

