package com.wt.uum2.web.wicket.panel.datatable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.components.web.wicket.link.AjaxWaitingLink;
import com.hirisun.components.web.wicket.modal.LazyLoadModalWindow;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.ConfirmationAnswer;
import com.wt.uum2.web.wicket.panel.YesNoPanel;
import com.wt.uum2.web.wicket.panel.dept.DeptListToolbarPanel;
import com.wt.uum2.web.wicket.panel.user.CreateUserPanel;

/**
 * <pre>
 * 业务名:  
 * 功能说明: 
 * 编写日期:	2011-11-3
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserlistToolbar extends BaseUUMPanel
{
	/**
	 * 
	 */
	private Department dept;
	/**
	 * 
	 */
	private UserListType currentType;
	/**
	 * 
	 */
	private Label caption;
	/**
	 * 
	 */
	private AjaxLink backdept;
	/**
	 * 
	 */
	private VisibleViewsubdeptPanel visibleViewsubdeptPanel;
	/**
	 * 
	 */
	private DeptListToolbarPanel visiblecurrentdeptpanel;
	/**
	 * 
	 */
	private WebMarkupContainer visiblehandleuser;
	/**
	 * 
	 */
	private WebMarkupContainer visibleaftersearchhandel;
	/**
	 * 
	 */
	private WebMarkupContainer visiblebackuserlist;
	/**
	 * 
	 */
	private WebMarkupContainer visiblerecoveruser;
	/**
	 * 
	 */
	private WebMarkupContainer visibleselecteduser;

	/**
	 * 
	 */
	private UserlistToolbarSearch search;
	/**
	 * 
	 */
	private AjaxLink adduser;
	/**
	 * 
	 */
	private AjaxLink moveuser;
	/**
	 * 用户页面的离职按钮
	 */
	private AjaxLink leaveoffice;
	/**
	 * 搜索页面的离职按钮
	 */
	private AjaxLink leftuser;
	/**
	 * 
	 */
	private AjaxLink addusertodept;
	/**
	 * 
	 */
	private AjaxLink deleteuserfromdept;
	/**
	 * 
	 */
	private AjaxLink clearselectedUser;
	/**
	 * 
	 */
	private List<User> addList;

	/**
	 * @param id
	 *            id
	 * @param dept
	 *            dept
	 * @param currentType
	 *            currentType
	 * @param isviewsubdept
	 *            isviewsubdept
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param list
	 *            list
	 */
	public UserlistToolbar(String id, final Department dept, final UserListType currentType,
			boolean isviewsubdept, String searchcontent, String searchtype, List<User> list)
	{
		super(id);
		setCurrentType(currentType);
		this.dept = dept;

		this.addList = list;

		final LazyLoadModalWindow EXPORTMODAL = new LazyLoadModalWindow("exportModal") {

			@Override
			public Component lazyCreateContent()
			{
				return new DownloadPanel(this.getContentId(), dept);
			}
		};
		EXPORTMODAL.setCookieName("exportModal");
		EXPORTMODAL.setMinimalWidth(200);
		EXPORTMODAL.setMinimalHeight(100);
		EXPORTMODAL.setInitialWidth(200);
		EXPORTMODAL.setInitialHeight(100);
		EXPORTMODAL.setResizable(false);
		add(EXPORTMODAL);
		final LazyLoadModalWindow COMFIRMMODAL = new LazyLoadModalWindow("comfirmModal") {

			@Override
			public Component lazyCreateContent()
			{
				return null;
			}
		};
		COMFIRMMODAL.setCookieName("comfirmModal");
		COMFIRMMODAL.setMinimalWidth(200);
		COMFIRMMODAL.setMinimalHeight(100);
		COMFIRMMODAL.setInitialWidth(200);
		COMFIRMMODAL.setInitialHeight(100);
		COMFIRMMODAL.setResizable(false);
		add(COMFIRMMODAL);
		boolean visible = false;

		if (InitParameters.isCqGroupAuthor()
				|| !getUUMService().isDepartmentManager(loginUser, dept)) {
			visible = false;
		} else {
			visible = true;
		}
		visiblecurrentdeptpanel = new DeptListToolbarPanel("visiblecurrentdeptpanel", dept, visible);
		add(visiblecurrentdeptpanel.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));

		visibleViewsubdeptPanel = new VisibleViewsubdeptPanel("visibleviewsubdept", isviewsubdept);
		add(visibleViewsubdeptPanel.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));

		search = new UserlistToolbarSearch("search", searchcontent, searchtype) {

			@Override
			public void onSubmit(AjaxRequestTarget target, Form<?> form, String content, String type)
			{
				setCurrentType(UserListType.search);
				setComponentVisibleByStyle(target);
				searchUserHandle(content, type, true, target);
			}

		};
		add(search.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));

		visiblehandleuser = new WebMarkupContainer("visiblehandleuser");
		add(visiblehandleuser.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));

		AjaxWaitingLink export = new AjaxWaitingLink("export") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				target.appendJavaScript("Wicket.Window.unloadConfirmation = false;");

				EXPORTMODAL.setContent(new DownloadPanel(EXPORTMODAL.getContentId(), dept));
				EXPORTMODAL.show(target);
			}

		};
		if (!InitParameters.isCqGroupAuthor()) {
			export.setVisible(false);
		}
		visiblehandleuser.add(export);
		adduser = new AjaxLink("adduser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");
				mainContainer.addOrReplace(new CreateUserPanel("mainPanel", new User(), dept));
				target.add(mainContainer);
			}

		};
		visiblehandleuser.add(adduser.setOutputMarkupPlaceholderTag(true));

		leaveoffice = new AjaxLink("leaveoffice") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "leaveoffice", addList,
							visibleViewsubdeptPanel.getViewsubdept().getModelObject(), null, null,
							target);
				}

			}

		};
		if (!"true".equalsIgnoreCase(getSetting().getVisibleLeaveofficeButton())) {
			leaveoffice.setVisible(false);
		}

		visiblehandleuser.add(leaveoffice.setOutputMarkupPlaceholderTag(true));

		AjaxLink deletuser = new AjaxLink("deletuser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "delete", addList, visibleViewsubdeptPanel
							.getViewsubdept().getModelObject(), null, null, target);
				}
			}

		};
		visiblehandleuser.add(deletuser);

		moveuser = new AjaxLink("moveuser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "move", addList, visibleViewsubdeptPanel
							.getViewsubdept().getModelObject(), null, null, target);
				}
			}

		};
		visiblehandleuser.add(moveuser.setOutputMarkupPlaceholderTag(true));
		if (!(isModifyGroup(loginUser) || getUUMService().isDepartmentManager(loginUser, dept))) {
			visiblehandleuser.setVisible(false);
		}

		visiblerecoveruser = new WebMarkupContainer("visiblerecoveruser");

		AjaxLink recoveruser = new AjaxLink("recoveruser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "recover", addList, false, null, null, target);
				}
			}

		};
		visiblerecoveruser.add(recoveruser);
		add(visiblerecoveruser.setVisible(false).setOutputMarkupId(true)
				.setOutputMarkupPlaceholderTag(true));
		visibleaftersearchhandel = new WebMarkupContainer("visibleaftersearchhandel");
		add(visibleaftersearchhandel.setVisible(false).setOutputMarkupId(true)
				.setOutputMarkupPlaceholderTag(true));
		addusertodept = new AjaxLink("addusertodept") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "addusertodept", addList,
							visibleViewsubdeptPanel.getViewsubdept().getModelObject(),
							search.getContent(), search.getType(), target);
				}
			}

		};
		
		visibleaftersearchhandel.add(addusertodept);
		deleteuserfromdept = new AjaxLink("deleteuserfromdept") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "deleteuserfromdept", addList,
							visibleViewsubdeptPanel.getViewsubdept().getModelObject(),
							search.getContent(), search.getType(), target);
				}

			}

		};
	
		visibleaftersearchhandel.add(deleteuserfromdept);
		leftuser = new AjaxLink("leftuser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (checkSelecedNull(addList, target)) {
					confirmUsersHandls(COMFIRMMODAL, "leaveoffice", addList,
							visibleViewsubdeptPanel.getViewsubdept().getModelObject(),
							search.getContent(), search.getType(), target);
				}

			}

		};
		

		visibleaftersearchhandel.add(leftuser.setOutputMarkupPlaceholderTag(true));

		visiblebackuserlist = new WebMarkupContainer("visiblebackuserlist");
		caption = new Label("caption", new Model());
		visiblebackuserlist.add(caption);
		backdept = new AjaxLink("backdept") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				UserlistToolbar.this.onClickBackDept(target);
			}
		};
		visiblebackuserlist.add(backdept);

		add(visiblebackuserlist.setVisible(false).setOutputMarkupId(true)
				.setOutputMarkupPlaceholderTag(true));

		visibleselecteduser = new WebMarkupContainer("visibleselecteduser");

		final Label SELECTEDUSERMSG = new Label("selectedUserMsg", new Model(addList.size()));
		visibleselecteduser.add(SELECTEDUSERMSG.setOutputMarkupId(true));

		clearselectedUser = new AjaxLink("clearselectedUser") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				UserlistToolbar.this.onClickClearSelected(target, visibleViewsubdeptPanel
						.getViewsubdept().getModelObject(), search.getContent(), search.getType());

			}

		};
		visibleselecteduser.add(clearselectedUser);

		add(visibleselecteduser.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true)
				.setVisible(false));
		setComponentVisibleByStyle(AjaxRequestTarget.get());

	}

	/**
	 * 方法说明：
	 * 
	 * @param list
	 *            list
	 * @param start
	 *            start
	 * @return String
	 */
	public String getConfirmMessage(List list, String start)
	{
		StringBuilder msg = new StringBuilder(start);
		for (int i = 0; i < addList.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			msg.append(((User) map.get("userObject")).getName());
			if (i > 10) {
				msg.append("……" + addList.size() + "位用户");
				break;
			} else if (i == addList.size() - 1) {
				msg.append("这" + addList.size() + "位用户");
			} else {
				msg.append("、");
			}
		}
		return msg.toString();
	}

	/**
	 * 方法说明：确认对用户的操作
	 * 
	 * @param modalWindow
	 *            modalWindow
	 * @param operateType
	 *            operateType
	 * @param list
	 *            list
	 * @param viewsubdept
	 *            viewsubdept
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param target
	 *            target
	 */
	public void confirmUsersHandls(ModalWindow modalWindow, final String operateType,
			final List list, final boolean viewsubdept, final String searchcontent,
			final String searchtype, AjaxRequestTarget target)
	{

		final ConfirmationAnswer ANSWER = new ConfirmationAnswer(false);
		String msg = getConfirmMessage(list, getString(operateType)) + "？";
		if ("addusertodept".equals(operateType)) {
			msg = "是否将" + getConfirmMessage(list, "") + "加入到当前部门(" + dept.getName() + ")？";
		} else if ("leaveoffice".equals(operateType)) {
			msg = "是否将" + getConfirmMessage(list, "") + "设置为离职状态？";
		}
		modalWindow
				.setContent(new YesNoPanel(modalWindow.getContentId(), msg, modalWindow, ANSWER));
		modalWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

			public void onClose(AjaxRequestTarget target)
			{
				if (ANSWER.isAnswer()) {
					usersHandls(operateType, list, viewsubdept, searchcontent, searchtype, target);
				}

			}
		});
		modalWindow.show(target);
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 */
	public void setComponentVisibleByStyle(AjaxRequestTarget target)
	{
		String deptname;
		if (dept.getName().length() > 14) {
			deptname = dept.getName().substring(0, 14) + "...";
		} else {
			deptname = dept.getName();
		}

		switch (currentType) {
		case search:
			addList.clear();
			caption.setDefaultModelObject("搜索结果");
			backdept.add(new AttributeModifier("value", "返回【" + deptname + "】列表"));
			visibleaftersearchhandel.setVisible(isModifyGroup(loginUser));
			
			if (isModifyGroup(loginUser) || getUUMService().isDepartmentManager(loginUser, dept)) {
				visibleaftersearchhandel.setVisible(true);
				addusertodept.setVisible(true);
				deleteuserfromdept.setVisible(true);
				if (StringUtils.equalsIgnoreCase("true",getSetting().getVisibleLeaveofficeButton())) {
					leftuser.setVisible(true);
				}else{
					leftuser.setVisible(false);
				}
			}
			
			if (target != null) {
				target.add(visiblebackuserlist.setVisible(true));
				target.add(visibleselecteduser.setVisible(false));
				target.add(visiblecurrentdeptpanel.setVisible(false));
				target.add(visibleViewsubdeptPanel.setVisible(false));
				target.add(visiblehandleuser.setVisible(false));
				target.add(visibleaftersearchhandel);

			}

			break;
		case recover:
			addList.clear();
			caption.setDefaultModelObject("【" + deptname + "】被删除成员列表");
			backdept.add(new AttributeModifier("value", "返回成员列表"));
			visiblerecoveruser.setVisible(true);
			search.setVisible(false);
			if (target != null) {
				target.add(visiblebackuserlist.setVisible(true));
				target.add(visibleselecteduser.setVisible(false));
				target.add(visiblecurrentdeptpanel.setVisible(false));
				target.add(visibleViewsubdeptPanel.setVisible(false));
				target.add(visiblehandleuser.setVisible(false));
				target.add(visiblerecoveruser);
				target.add(search);
			}
			break;
		default:
			if (target != null) {
				target.add(moveuser.setVisible(!visibleViewsubdeptPanel.getViewsubdept()
						.getModelObject()));
				if ("true".equalsIgnoreCase(getSetting().getVisibleLeaveofficeButton())) {
					target.add(leaveoffice.setVisible(!visibleViewsubdeptPanel.getViewsubdept()
							.getModelObject()));
				}
			}
			break;
		}
	}

	/**
	 * 方法说明：选择是否显示子部门人员复选框时操作
	 * 
	 * @param checked
	 *            checked
	 * @param target
	 *            target
	 */
	public void onCheckedviewSubDept(boolean checked, AjaxRequestTarget target)
	{
	}

	/**
	 * 方法说明：onClickBackDept
	 * 
	 * @param target
	 *            target
	 */
	public void onClickBackDept(AjaxRequestTarget target)
	{

	}

	/**
	 * 方法说明：onClickClearSelected
	 * 
	 * @param target
	 *            target
	 * @param isviewsubdept
	 *            isviewsubdept
	 * @param content
	 *            content
	 * @param type
	 *            type
	 */
	protected void onClickClearSelected(AjaxRequestTarget target, boolean isviewsubdept,
			String content, String type)
	{
	}

	/**
	 * 方法说明：查询用户
	 * 
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param isInit
	 *            isInit
	 * @param target
	 *            target
	 */
	public void searchUserHandle(String searchcontent, String searchtype, boolean isInit,
			AjaxRequestTarget target)
	{

	}

	/**
	 * 方法说明：查询已删除的人员列表
	 * 
	 * @param target
	 *            target
	 * @param isInit
	 *            isInit
	 */
	public void selectDeletedUserListHandle(AjaxRequestTarget target, boolean isInit)
	{
	};

	/**
	 * 方法说明：检查复选框是否有选中
	 * 
	 * @param list
	 *            list
	 * @param target
	 *            checkgroup
	 * @return boolean
	 */
	public boolean checkSelecedNull(List list, AjaxRequestTarget target)
	{
		if (CollectionUtils.isEmpty(list)) {
			target.appendJavaScript("alert('请选择要操作的用户！');");
			return false;
		}
		return true;
	}

	/**
	 * 方法说明：删除用户
	 * 
	 * @param deleteormove
	 *            deleteormove
	 * @param list
	 *            list
	 * @param viewsubdept
	 *            viewsubdept
	 * @param value
	 *            value
	 * @param type
	 *            type
	 * @param target
	 *            target
	 */
	public void usersHandls(String deleteormove, List list, boolean viewsubdept, String value,
			String type, AjaxRequestTarget target)
	{
		if (!checkSelecedNull(list, target))
			return;
		List<User> selectedUsers = new ArrayList<User>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = (Map<String, Object>) list.get(i);
			User u = (User) map.get("userObject");
			selectedUsers.add(u);
		}
		if ("delete".equals(deleteormove)) {
			deleteUserHandle(selectedUsers);
		} else if ("move".equals(deleteormove)) {
			moveUserHandle(selectedUsers);
		} else if ("recover".equals(deleteormove)) {
			recoverUserHandel(selectedUsers);
		} else if ("addusertodept".equals(deleteormove)) {
			addUserstoDept(selectedUsers);
		} else if ("deleteuserfromdept".equals(deleteormove)) {
			deleteUserHandle(selectedUsers);
		} else if ("leaveoffice".equals(deleteormove)) {
			leaveOfficeHandle(selectedUsers);
		}
		list.clear();
		target.add(visibleselecteduser.setVisible(false));

		switch (currentType) {
		case recover:
			selectDeletedUserListHandle(target, false);
			break;
		case search:
			searchUserHandle(value, type, false, target);
			break;
		default:
			setComponentVisibleByStyle(target);
			onCheckedviewSubDept(viewsubdept, target);

			break;
		}

	}

	/**
	 * 方法说明：
	 * 
	 * @param userList
	 *            userList
	 */
	public void deleteUserHandle(List<User> userList)
	{
		for (User user : userList) {
			// 修改所有应用帐号系统登录状态为FALSE
			List<String> keys = new ArrayList<String>();
			// keys.add("LoginEnable");
			keys.add("loginDisabled");
			List<Attribute> attList = getUUMService().getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("loginDisabled")) {
						attribute.setValue(Boolean.toString(true));
						getUUMService().updateAttribute(attribute);
					}
				}
			}
			String oldPwd = user.getPlainPassword();
			getUUMService().deleteUser(user);
			// /////////////////////////
			// ///同步 synchronise/////
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { oldPwd, user.getPassword() });
			map.put("loginDisabled",
					new String[] { Boolean.FALSE.toString(), Boolean.TRUE.toString() });
			Event event = getEventFactory().createEventUpdateUser(user.getUuid(), map);
			getEventListenerHandler().handle(event);
		}

	}

	/**
	 * 方法说明：
	 * 
	 * @param userList
	 *            userList
	 */
	public void moveUserHandle(List<User> userList)
	{
		for (User user : userList) {
			getUUMService().removeUserFromDepartment(dept, user);
			getEventListenerHandler().handle(
					getEventFactory().createUserEventRemoveDepartment(user.getUuid(),
							dept.getUuid()));
		}
	}

	/**
	 * 方法说明：恢复逻辑删除用户
	 * 
	 * @param userList
	 *            userList
	 */
	public void recoverUserHandel(List<User> userList)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		for (User user : userList) {
			// 设置可以登录门户状态
			List<String> keys = new ArrayList<String>();
			keys.add("loginDisabled");
			List<Attribute> attList = getUUMService().getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("loginDisabled")) {
						attribute.setValue(Boolean.FALSE.toString());
						map.put(attribute.getType().getId(), new String[] {
								Boolean.TRUE.toString(), attribute.getValue() });
						getUUMService().updateAttribute(attribute);
						// Iterator<AttributeValue> iterator = attribute.getAttValues().iterator();
						// while (iterator.hasNext()) {
						// StringValue av = (StringValue) iterator.next();
						// av.setValue(Boolean.FALSE.toString());
						// map.put("loginDisabled", new String[] { Boolean.TRUE.toString(),
						// Boolean.FALSE.toString() });
						// }
					}
				}
			}
			String oldPwd = user.getPlainPassword();
			getUUMService().restoreUser(user);
			if (InitParameters.isPlainPassword()) {
				map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
			}
			Event event = getEventFactory().createEventUpdateUser(user.getUuid(), map);
			getEventListenerHandler().handle(event);
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param userList
	 *            userList
	 */
	public void addUserstoDept(List<User> userList)
	{
		for (User user : userList) {
			if (getUUMService().getUserPrimaryDepartment(user).equals(dept)) {
				continue;
			}
			List<Event> events = new ArrayList<Event>();
			if (!InitParameters.isMultiDept()) {
				List<String> deptUUIDs = new ArrayList<String>();
				for (Department department : getUUMService().getUserDepartments(user)) {
					getUUMService().removeUserFromDepartment(department, user);
					deptUUIDs.add(department.getUuid());
				}
				events.add(getEventFactory().createUserEventRemoveDepartment(user.getUuid(),
						deptUUIDs));
			}
			events.add(getEventFactory().createUserEventAddDepartment(user.getUuid(),
					dept.getUuid()));
			getUUMService().addUserToDepartment(user, dept);
			user.setPrimaryDepartment(dept);
			getUUMService().updateUser(user);
			getEventListenerHandler().handle(events);
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param userList
	 *            userList
	 */
	public void leaveOfficeHandle(List<User> userList)
	{

		for (User user : userList) {
			// 修改所有应用帐号系统登录状态为FALSE
			List<String> keys = new ArrayList<String>();
			// keys.add("LoginEnable");
			keys.add("loginDisabled");
			List<Attribute> attList = getUUMService().getAttributesByAttributeTypeIdKey(user, keys);
			if (attList != null) {
				for (int j = 0; j < attList.size(); j++) {
					Attribute attribute = attList.get(j);
					if (attribute.getType().getId().contains("loginDisabled")) {
						attribute.setValue(Boolean.toString(true));
						getUUMService().updateAttribute(attribute);
					}
				}
			}
			String oldPwd = user.getPlainPassword();
			user.setPlainPassword(user.getPassword());
			user.setStatus(ResourceStatus.LEAVE.intValue());
//			if(StringUtils.equals(ProjectResolver.getId(), "hlx")){
//				Map<String,String> map = getUUMService().getAttributesMapByResourceAndTypes(user, "yuangonghao");
//				String yuangonghao = "left";
//				if(MapUtils.isNotEmpty(map)){
//					yuangonghao = StringUtils.defaultIfEmpty(map.get("yuangonghao"), "left");
//				}
//				user.setId(user.getId()+yuangonghao);
//			}
			getUUMService().updateUser(user);
			// /////////////////////////
			// ///同步 synchronise/////
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("userPassword", new String[] { oldPwd, user.getPlainPassword() });
			map.put("loginDisabled",
					new String[] { Boolean.FALSE.toString(), Boolean.TRUE.toString() });
			Event event = getEventFactory().createEventUpdateUser(user.getUuid(), map);
			getEventListenerHandler().handle(event);
		}
	}

	/**
	 * <pre>
	 * 业务名:
	 * 功能说明: 
	 * 编写日期:	2011-11-25
	 * 作者:	Administrator
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	private class VisibleViewsubdeptPanel extends Panel
	{
		/**
		 * 
		 */
		private AjaxCheckBox viewsubdept;

		public AjaxCheckBox getViewsubdept()
		{
			return viewsubdept;
		}

		/**
		 * @param id
		 *            id
		 * @param isviewsubdept
		 *            isviewsubdept
		 */
		public VisibleViewsubdeptPanel(String id, boolean isviewsubdept)
		{
			super(id);
			viewsubdept = new AjaxCheckBox("viewsubdept", Model.of(isviewsubdept)) {

				@Override
				protected void onUpdate(AjaxRequestTarget target)
				{
					setComponentVisibleByStyle(target);
					onCheckedviewSubDept(getModelObject(), target);
				}

			};

			add(viewsubdept);
			AjaxLink recoverUserList = new AjaxLink("recoverUserList") {

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					setCurrentType(UserListType.recover);
					setComponentVisibleByStyle(target);
					selectDeletedUserListHandle(target, true);

				}

			};
			add(recoverUserList);
			if (!(isModifyGroup(loginUser) || getUUMService().isDepartmentManager(loginUser, dept))) {
				recoverUserList.setVisible(false);
			}
		}

	}

	public void setCurrentType(UserListType currentType)
	{
		this.currentType = currentType;
	}

	public WebMarkupContainer getVisibleselecteduser()
	{
		return visibleselecteduser;
	}
}
