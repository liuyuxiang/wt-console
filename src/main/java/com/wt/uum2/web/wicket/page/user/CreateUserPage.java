package com.wt.uum2.web.wicket.page.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.tabs.AbstractTab;
import org.apache.wicket.extensions.markup.html.tabs.ITab;
import org.apache.wicket.extensions.markup.html.tabs.PanelCachingTab;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValue;

import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.form.AjaxRefreshFeedbackForm;
import com.hirisun.components.web.wicket.tabs.AjaxKeepFormStatusTabbedPanel;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UUMDateFormat;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;
import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.dept.DeptLabelListPanel;
import com.wt.uum2.web.wicket.panel.group.GroupLabelListPanel;
import com.wt.uum2.web.wicket.panel.user.UserAppAccountPanel;
import com.wt.uum2.web.wicket.panel.user.UserAttrInfoPanel;
import com.wt.uum2.web.wicket.panel.user.UserAttributePanel;
import com.wt.uum2.web.wicket.panel.user.UserBaseInfoPanel;
import com.wt.uum2.web.wicket.panel.user.UserUpdateButtonsPanel;

/**
 * <pre>
 * 业务名:创建用户页面
 * 功能说明: 创建用户页面
 * 编写日期:	2011-9-27
 * 作者:李趁月
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class CreateUserPage extends UUMBasePage {

	/**
	 * tab切换
	 */
	private AjaxKeepFormStatusTabbedPanel ajaxTabbedPanel;

	/**
	 * 用户基本信息面板
	 */
	private UserBaseInfoPanel userBaseInfoPanel;

	/**
	 * 用户通讯信息扩展属性面板
	 */
	private UserAttrInfoPanel userAttrInfoPanel1;

	/**
	 * 用户职务信息扩展属性面板
	 */
	private UserAttrInfoPanel userAttrInfoPanel2;

	/**
	 * 应用系统授权信息扩展属性面板
	 */
	private UserAttrInfoPanel userAttrInfoPanel3;

	
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
	public CreateUserPage() {
		
		StringValue userUuid = getRequest()
				.getRequestParameters().getParameterValue("userid");
		if (userUuid.isEmpty()) {
			this.user = new User();
		} else {
			this.user = getUUMService().getUserByUuid(userUuid.toString());
		}
		StringValue deptUuid = getRequest().getRequestParameters().getParameterValue("id");
		if (isNew(user)) {
			if(deptUuid.isEmpty()){
				throw new RuntimeException("");
			}
			this.dept = getUUMService().getDepartmentByUUID(
					deptUuid.toString());
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
	public CreateUserPage(User u,Department d){
		
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
	 * @param u
	 *            用户
	 * @param d
	 *            部门
	 * @param page
	 *            返回页面
	 */
	public CreateUserPage(User u,Department d,UserListPage page){
		
		this.user=u;
		this.gobackPage=page;
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
new Model<String>("用户基本信息")) {

			@Override
			public Panel getPanel(String panelId) {
				userBaseInfoPanel = new UserBaseInfoPanel(panelId, user, dept);
				revisabilityUserType(userBaseInfoPanel,loginUser,user);
				return userBaseInfoPanel;
			}

		}));

		tabs.add(new PanelCachingTab(new AbstractTab(
new Model<String>("用户通讯信息")) {
			@Override
			public Panel getPanel(String panelId) {
				userAttrInfoPanel1 = new UserAttrInfoPanel(panelId, user, "1");
				revisabilityUserType(userAttrInfoPanel1,loginUser,user);
				return userAttrInfoPanel1;
			}
		}));
		tabs.add(new PanelCachingTab(new AbstractTab(new Model<String>("用户职务信息")) {
			@Override
			public Panel getPanel(String panelId) {
				userAttrInfoPanel2 = new UserAttrInfoPanel(panelId, user,"2");
				revisabilityUserType(userAttrInfoPanel2,loginUser,user);
				return userAttrInfoPanel2;
			}
		}));
		if (!isNew(user) && (isModifyGroup(loginUser) || isAdmin(loginUser, user))) {

			tabs.add(new PanelCachingTab(new AbstractTab(new Model<String>("应用系统信息")) {
				@Override
				public Panel getPanel(String panelId) {
					userAttrInfoPanel3 = new UserAppAccountPanel(panelId,	user, "3");
					userAttrInfoPanel3.setEnabled(false);
					return userAttrInfoPanel3;
				}
			}));
		}
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

				if (isNew(user)) {
					user.setPrimaryDepartmentUUID(dept.getUuid());
					user.setPrimaryDepartment(dept);

					if (isModifyGroup(loginUser)
							|| CollectionUtils.isEmpty(userBaseInfoPanel
									.getAdminGroupLabelList().getList())) {
						user.setStatus(Integer.valueOf(ResourceStatus.NORMAL
								.intValue()));
						user.setCurrentAuthorLevel(Long.valueOf(0));
					} else {
						user.setCurrentAuthorLevel(1l);
						user.setStatus(Integer.valueOf(ResourceStatus.CREATED
								.intValue()));
					}
					if (user.getPrimaryUserUUID() != null
							&& user.getPrimaryUserUUID().length() > 0) {
						user.setPrimaryUser(getUUMService().getUserByUuid(
								user.getPrimaryUserUUID()));
					}
					Set<Department> deptSet = new HashSet<Department>();
					deptSet.addAll(userBaseInfoPanel.getDeptLabelList()
							.getList());
					user.setDepartments(deptSet);
					getUUMService().createUser(user);
					getUUMService().addUserGroups(user,
							userBaseInfoPanel.getGroupLabelList().getList());

					if (!isModifyGroup(loginUser)
							&& CollectionUtils.isNotEmpty(userBaseInfoPanel
									.getAdminGroupLabelList().getList())) {
						UserAuthor author = new UserAuthor();
						author.setLevels("1");
						author.setUser(user);
						author.setGroup(userBaseInfoPanel
								.getAdminGroupLabelList().getList().get(0));
						getUUMService().saveUserAuthor(author);
					}

					// 添加可管理该用户的组
					getUUMService().addUserManagerGroups(
							user,
							userBaseInfoPanel.getAdminGroupLabelList()
									.getList());

					// 处理默认应用系统设置
					List<Group> appGroups = getUUMService()
							.getDefaultAddAppGroup();
					if (appGroups != null && appGroups.size() > 0) {
						getUUMService().addUserGroups(user, appGroups);
					}

					// 创建新增用户时产生的扩展属性
					saveAttributes(userBaseInfoPanel.getUserAttrListPanel()
							.getAttributePanels(), user);
					if (userAttrInfoPanel1 != null) {
						saveAttributes(userAttrInfoPanel1
								.getUserAttrListPanel().getAttributePanels(),
								user);
					}
					if (userAttrInfoPanel2 != null) {
						saveAttributes(userAttrInfoPanel2
								.getUserAttrListPanel().getAttributePanels(),
								user);
					}
					saveAttributes3(user);

					Event event = getEventFactory().createEventCreateUser(
							user.getUuid());
					// 如果是重庆,需要将用户的erp编码映射到映射表中
					if (InitParameters.isCqGroupAuthor()
							&& StringUtils.isNotBlank(event.getParamValuesMap()
									.getSingle("sgccEmployeeCode"))) {
						getUumTempDataService().addResourceMapping(
								user,
								event.getParamValuesMap().getSingle(
										"sgccEmployeeCode"));
					}
					getEventListenerHandler().handle(event);

				} else {
					Map<String, String[]> map = new HashMap<String, String[]>();

					List<Event> events = new ArrayList<Event>();

					handleUserDepartments(userBaseInfoPanel.getDeptLabelList(),
							user, events);
					handleUserGroups(userBaseInfoPanel.getGroupLabelList(),
							user, events);
					handleAdminGroups(
							userBaseInfoPanel.getAdminGroupLabelList(), user,
							events);

					user.setStatus(Integer.valueOf(ResourceStatus.NORMAL
							.intValue()));
					getUUMService().updateUser(user);

					// 保存扩展属性值
					saveAttributes(userBaseInfoPanel
							.getUserAttrListPanel().getAttributePanels(),user);
					userBaseInfoPanel.fillChangedUserBaseInfoToMap(map);
					fillChangedAttrToMap(userBaseInfoPanel
							.getUserAttrListPanel().getAttributePanels(), map);

					if (userAttrInfoPanel1 != null) {
						saveAttributes(userAttrInfoPanel1
								.getUserAttrListPanel().getAttributePanels(),user);
						fillChangedAttrToMap(userAttrInfoPanel1
								.getUserAttrListPanel().getAttributePanels(),
								map);
					}
					if (userAttrInfoPanel2 != null) {
						saveAttributes(userAttrInfoPanel2
								.getUserAttrListPanel().getAttributePanels(),user);
						fillChangedAttrToMap(userAttrInfoPanel2
								.getUserAttrListPanel().getAttributePanels(),
								map);
					}

					// userBaseInfoPanel.fillChangedMap(map);
					if (InitParameters.isCqGroupAuthor()) {
						if (!ArrayUtils.isEmpty(map.get("sgccEmployeeCode"))) {
							if (StringUtils.isNotBlank(map
									.get("sgccEmployeeCode")[0])) {
								getUumTempDataService().removeResourceMapping(
										user, map.get("sgccEmployeeCode")[0]);
							}
							if (StringUtils.isNotBlank(map
									.get("sgccEmployeeCode")[1])) {
								getUumTempDataService().addResourceMapping(
										user, map.get("sgccEmployeeCode")[1]);
							}
						}
					}
					if (!map.isEmpty()) {
						events.add(getEventFactory().createEventUpdateUser(
								user.getUuid(), map));
					}
					getEventListenerHandler().handle(events);
					

				}
				if(gobackPage==null){
					setResponsePage(new UserListPage(dept));
				}else{
					setResponsePage(gobackPage);
				}
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

		form.add(new AjaxLink("goBack") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if(gobackPage==null){
					setResponsePage(new UserListPage(dept));
				}else{
					setResponsePage(gobackPage);
				}
			}
		});
		
		UserUpdateButtonsPanel updateUserbottons = new UserUpdateButtonsPanel(
"userUpdateButtons",
				user) {

			@Override
			public void reset(AjaxRequestTarget target)
			{
				// TODO Auto-generated method stub

			}

		};
		if (!isNew(user)) {
			updateUserbottons.setVisible(true);
		} else {
			updateUserbottons.setVisible(false);
		}
		form.add(updateUserbottons);
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
	 * 方法说明：保存用户应用系统扩展属性
	 * 
	 * @param user
	 *            用户
	 */
	private void saveAttributes3(User user) {
		List<AttributeType> attributeTypeList = getUUMService()
				.getAttributeTypeByResource(user, null, "3");
		String appStatusCode = InitParameters.getAppStatusCode().split("XXXX")[1];
		String appAccount = InitParameters.getAppAccountLocal().split("XXXX")[1];
		String appLoginEnable = InitParameters.getAppLoginEnableLocal().split(
				"XXXX")[1];
		List<Group> appGroups = getUUMService().getDefaultAddAppGroup();
		for (AttributeType attrType : attributeTypeList) {
			Attribute av = new Attribute();
			av.setOwnerResource(user);
			av.setType(attrType);
			if (attrType.getId().equals("loginDisabled")) {
				if (user.getCurrentAuthorLevel() == 0) {
					av.setValue(Boolean.FALSE.toString());
				} else {
					av.setValue(Boolean.TRUE.toString());
				}
			} else if (attrType.getId().equalsIgnoreCase("cqPORTALAccount")) {
				av.setValue(user.getId());
			} else if (attrType.getId().equalsIgnoreCase("cqPORTALPwd")) {
				if (InitParameters.getPlainPassword() != null
						&& InitParameters.getPlainPassword().equalsIgnoreCase(
								"true")) {
					av.setValue(user.getPlainPassword());
				} else {
					av.setValue(user.getPassword());
				}
			} else {

				if (attrType.getId().endsWith(appStatusCode)) {
					if (isDefaultAttWithApp(appGroups, attrType.getId())) {
						av.setValue(String.valueOf(ResourceStatus.AUTHORIZE
								.intValue()));
					} else {
						av.setValue(String.valueOf(ResourceStatus.UNDEAL
								.intValue()));
					}
				} else if (attrType.getId().endsWith(appAccount)) {
					if (isDefaultAttWithApp(appGroups, attrType.getId())) {
						av.setValue(user.getId());
					} else {
						av.setValue("");
					}
				} else if (attrType.getId().endsWith(appLoginEnable)) {
					if (isDefaultAttWithApp(appGroups, attrType.getId())) {
						av.setValue(Boolean.TRUE.toString());
					} else {
						av.setValue(Boolean.FALSE.toString());
					}
				} else if (attrType.getId().contains("Pwd")
						|| attrType.getId().contains("Password")) {
					if (InitParameters.getPlainPassword() != null
							&& InitParameters.getPlainPassword()
									.equalsIgnoreCase("true")) {
						av.setValue(user.getPlainPassword());
					} else {
						av.setValue(user.getPassword());
					}
				} else {
					av.setValue("");
				}
			}
			getUUMService().saveAttribute(av);
		}

		String[] attrTypeIds = { "pwdChangeTime", "sgccUserId" };

		for (String attrTypeId : attrTypeIds) {
			List<AttributeType> attrTypeList = getUUMService()
					.getAttributeTypeById(attrTypeId);
			if (CollectionUtils.isNotEmpty(attrTypeList)) {
				Attribute attribute = new Attribute();
				attribute.setOwnerResource(user);
				attribute.setType(attrTypeList.get(0));
				if (attrTypeId.contains("pwdChangeTime")) {
					UUMDateFormat df = new UUMDateFormat();
					attribute.setValue(df.switchLongToDateFormat(System
							.currentTimeMillis()));
				} else if (attrTypeId.equalsIgnoreCase("sgccUserId")) {
					Integer num = Integer.valueOf(getUUMService()
							.getLastUserId("sgccUserId")) + 1;
					attribute.setValue(String.valueOf(num).length() == 10 ? String
							.valueOf(num) : "0" + String.valueOf(num));
				}
				getUUMService().saveAttribute(attribute);
			}
		}
	}

	/**
	 * 方法说明：操作用户部门关联关系并添加到event
	 * 
	 * @param deptListPanel
	 *            部门树面板
	 * @param user
	 *            用户
	 * @param events
	 *            事件
	 */
	private void handleUserDepartments(DeptLabelListPanel deptListPanel,
			User user, List<Event> events) {
		if (CollectionUtils.isNotEmpty(deptListPanel.getAddList())) {
			List<String> list = new ArrayList<String>();
			for (Department d : deptListPanel.getAddList()) {
				list.add(d.getUuid());
			}
			getUUMService()
					.addUserDepartments(user, deptListPanel.getAddList());
			events.add(getEventFactory().createUserEventAddDepartment(
					user.getUuid(), list));
		}
		if (CollectionUtils.isNotEmpty(deptListPanel.getRemoveList())) {
			List<String> list = new ArrayList<String>();
			for (Department d : deptListPanel.getRemoveList()) {
				list.add(d.getUuid());
			}
			getUUMService().deleteUserDepartments(user,
					deptListPanel.getRemoveList());
			events.add(getEventFactory().createUserEventRemoveDepartment(
					user.getUuid(), list));
		}
	}

	/**
	 * 方法说明：操作用户权限组并添加到event
	 * 
	 * @param groupListPanel
	 *            组树面板
	 * @param user
	 *            用户
	 * @param events
	 *            事件
	 */
	private void handleUserGroups(GroupLabelListPanel groupListPanel,
			User user, List<Event> events) {
		if (CollectionUtils.isNotEmpty(groupListPanel.getAddList())) {
			List<String> list = new ArrayList<String>();
			for (Group g : groupListPanel.getAddList()) {
				list.add(g.getUuid());
			}
			getUUMService().addUserGroups(user, groupListPanel.getAddList());
			events.add(getEventFactory().createUserEventAddGroup(
					user.getUuid(), list));
		}
		if (CollectionUtils.isNotEmpty(groupListPanel.getRemoveList())) {
			List<String> list = new ArrayList<String>();
			for (Group g : groupListPanel.getRemoveList()) {
				list.add(g.getUuid());
			}
			getUUMService().deleteUserGroups(user,
					groupListPanel.getRemoveList());
			events.add(getEventFactory().createUserEventRemoveGroup(
					user.getUuid(), list));
		}
	}

	/**
	 * 方法说明：操作用户的可管理该用户的组并添加到event
	 * 
	 * @param adminGroupListPanel
	 *            组树面板
	 * @param user
	 *            用户
	 * @param events
	 *            事件
	 */
	private void handleAdminGroups(GroupLabelListPanel adminGroupListPanel,
			User user, List<Event> events) {
		if (CollectionUtils.isNotEmpty(adminGroupListPanel.getAddList())) {
			List<String> list = new ArrayList<String>();
			for (Group g : adminGroupListPanel.getAddList()) {
				list.add(g.getUuid());
			}
			events.add(getEventFactory().createUserEventAddDepartment(
					user.getUuid(), list));
			getUUMService().addUserAdminGroups(user,
					adminGroupListPanel.getAddList());
		}
		if (CollectionUtils.isNotEmpty(adminGroupListPanel.getRemoveList())) {
			List<String> list = new ArrayList<String>();
			for (Group g : adminGroupListPanel.getRemoveList()) {
				list.add(g.getUuid());
			}
			events.add(getEventFactory().createUserEventRemoveDepartment(
					user.getUuid(), list));
			getUUMService().deleteUserAdminGroups(user,
					adminGroupListPanel.getRemoveList());
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

	/**
	 * 方法说明：判断扩展属性是否是默认加载的应用系统组的相关属性,如果是则返回true
	 * 
	 * @param apps
	 *            应用系统组列表
	 * @param typeid
	 *            扩展属性id
	 * @return boolean
	 */
	private boolean isDefaultAttWithApp(List<Group> apps, String typeid) {
		if (apps != null && !apps.isEmpty()) {
			for (Group group : apps) {
				if (typeid.contains(group.getCode())) {
					return true;
				}
			}
		}
		return false;
	}
	
	
}

