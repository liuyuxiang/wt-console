package com.wt.uum2.web.wicket.panel.user;

import java.util.LinkedList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.datatable.IsExistCurrentDeptColumn;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;
import com.wt.uum2.web.wicket.panel.datatable.UserListType;
import com.wt.uum2.web.wicket.panel.datatable.UserlistCheckBoxPropertyColumn;
import com.wt.uum2.web.wicket.panel.datatable.UserlistToolbar;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-16
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserListPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private Form<Void> form;
	/**
	 * userPageResult
	 */
	private UserPageResult userPageResult;
	/**
	 * 
	 */
	private UserListDataTable userListDataTable;
	/**
	 * 
	 */
	private Department dept;

	/**
	 * 
	 */
	private List baseColumns;

	/**
	 * 
	 */
	private int currentPage;
	/**
	 * 
	 */
	private int pagesize;
	/**
	 * 
	 */
	private LinkedList<User> addlist;
	/**
	 * 
	 */
	private UserlistToolbar toolbar;

	/**
	 * 
	 */
	private UserListType currentType;

	/**
	 * 
	 */
	private String searchcontent;
	/**
	 * 
	 */
	private String searchtype;

	/**
	 * 
	 */
	private boolean isviewsubdept;

	/**
	 * @param id
	 *            id
	 * @param d
	 *            d
	 * @param userListType
	 *            userListType
	 * @param currentPage
	 *            currentPage
	 * @param pagesize
	 *            pagesize
	 * @param isviewsubdept
	 *            isviewsubdept
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param list
	 *            list
	 */
	public UserListPanel(String id, Department d, UserListType userListType, int currentPage,
			int pagesize, boolean isviewsubdept, String searchcontent, String searchtype,
			LinkedList<User> list)
	{
		super(id);
		this.dept = d;
		this.currentPage = currentPage;
		this.pagesize = pagesize;
		this.currentType = userListType;
		this.searchcontent = searchcontent;
		this.searchtype = searchtype;
		this.isviewsubdept = isviewsubdept;
		this.addlist = CollectionUtils.isEmpty(list) ? new LinkedList<User>() : list;
		initForm();
	}

	/**
	 * @param id
	 *            id
	 * @param d
	 *            d
	 * @param userListType
	 *            userListType
	 * @param currentPage
	 *            currentPage
	 * @param pagesize
	 *            pagesize
	 * @param isviewsubdept
	 *            isviewsubdept
	 * @param list
	 *            list
	 */
	public UserListPanel(String id, Department d, UserListType userListType, int currentPage,
			int pagesize, boolean isviewsubdept, LinkedList<User> list)
	{
		this(id, d, userListType, currentPage, pagesize, isviewsubdept, null, null, list);
	}

	/**
	 * @param id
	 *            id
	 * @param d
	 *            d
	 * @param currentPage
	 *            currentPage
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param list
	 *            list
	 */
	public UserListPanel(String id, Department d, int currentPage, int pagesize,
			String searchcontent, String searchtype, LinkedList<User> list)
	{
		this(id, d, UserListType.valueOfString("search"), currentPage, pagesize, false,
				searchcontent, searchtype, list);
	}

	/**
	 * @param id
	 *            id
	 * @param d
	 *            d
	 */
	public UserListPanel(String id, Department d)
	{
		this(id, d, UserListType.valueOfString("normal"), 1, 15, false, null, null, null);
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void initForm()
	{

		form = new Form<Void>("form");
		add(form);
		toolbar = new UserlistToolbar("toolbar", dept, currentType, isviewsubdept, searchcontent,
				searchtype, addlist) {
			@Override
			public void onCheckedviewSubDept(final boolean checked, AjaxRequestTarget target)
			{

				isviewsubdept = checked;
				currentPage = 1;
				UserListDataTable newDataTable = createUserListDataTable();
				userListDataTable.replaceWith(newDataTable);
				userListDataTable = newDataTable;
				target.add(newDataTable);

			}

			@Override
			public void onClickBackDept(AjaxRequestTarget target)
			{
				currentPage = 1;
				WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");
				mainContainer.addOrReplace(new UserListPanel("mainPanel", dept,
						UserListType.normal, currentPage, pagesize, false, null));
				target.add(mainContainer);
			}

			@Override
			public void searchUserHandle(String searchcontent, String searchtype, boolean isInit,
					AjaxRequestTarget target)
			{
				currentPage = 1;
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");
				mainContainer.addOrReplace(new UserListPanel("mainPanel", dept, currentPage,
						pagesize, searchcontent, searchtype, null));
				if (target != null) {
					target.add(mainContainer);
				}

			}

			@Override
			public void selectDeletedUserListHandle(AjaxRequestTarget target, boolean isInit)
			{
				currentType = UserListType.valueOfString("recover");
				currentPage = 1;
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");

				mainContainer.addOrReplace(new UserListPanel("mainPanel", dept, currentType,
						currentPage, pagesize, false, null));
				if (target != null) {
					target.add(mainContainer);
				}

			}

			@Override
			protected void onClickClearSelected(AjaxRequestTarget target, boolean isviewsubdept,
					String content, String type)
			{
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");

				mainContainer.addOrReplace(new UserListPanel("mainPanel", dept, currentType,
						currentPage, pagesize, isviewsubdept, content, type, null));
				if (target != null) {
					target.add(mainContainer);
				}
			}

		};
		form.add(toolbar.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));
		userListDataTable = createUserListDataTable();

		form.add(userListDataTable.setOutputMarkupId(true));
		baseColumns = userListDataTable.getBaseColumns();

	}

	/**
	 * 方法说明：replaceWithRecoverDataTable
	 * 
	 * @param target
	 *            target
	 * @param newResult
	 *            newResult
	 * @param callback
	 *            callback
	 */
	@Deprecated
	public void replaceWithRecoverDataTable(AjaxRequestTarget target,
			UserPageResult<User> newResult, final GotoPageCallback callback)
	{
		UserListDataTable newUserListDataTable = new UserListDataTable(userListDataTable.getId(),
				newResult, callback) {

			@Override
			public void createOtherColumns(List columns)
			{
				createDataTableOtherColumns(columns, currentType);
			}

			@Override
			protected void onClickLink(User user, AjaxRequestTarget target)
			{
				// TODO Auto-generated method stub

			}
		};
		userListDataTable.replaceWith(newUserListDataTable);
		userListDataTable = newUserListDataTable;
		target.add(newUserListDataTable);
	}

	/**
	 * 方法说明：
	 * 
	 * @param columns
	 *            columns
	 * @param type
	 *            type
	 */
	public void createDataTableOtherColumns(List columns, UserListType type)
	{

		switch (type) {
		case search:
			columns.add(new IsExistCurrentDeptColumn<User>(Model.of("属当前部门"), null, dept));
			// if(InitParameters.isCqGroupAuthor() &&
			// getUUMService().isUserinSuperGroup(loginUser)){
			columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist, toolbar
					.getVisibleselecteduser()));
			// }
			break;
		case recover:
			columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist, toolbar
					.getVisibleselecteduser()));
			break;

		default:
			if (getUUMService().isDepartmentManager(loginUser, dept) || (isModifyGroup(loginUser))) {
				columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist, toolbar
						.getVisibleselecteduser()));
			}
			break;
		}

	}

	/**
	 * 方法说明：
	 * 
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	public UserPageResult searchUser(String searchcontent, String searchtype, int page, int pagesize)
	{
		Condition condition = new Condition();
		UserPageResult<User> upr = new UserPageResult<User>();
		String orgCode = getUUMService().getUserPrimaryDepartment(loginUser).getOrgCode();
		if (searchtype.indexOf("username") != -1) {
			condition.setUserName(searchcontent);
		} else if (searchtype.indexOf("userid") != -1) {
			condition.setUserId(searchcontent);
		}

		boolean isSuper = getUUMService().isUserinSuperGroup(loginUser);

		if (searchtype.indexOf("ERP") != -1) {
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser))) {
				upr = getUUMService().searchERPUsersByCondition(page, pagesize, condition);
			} else {
				upr = getUUMService().searchERPUsersByConditionAndOrgCode(page, pagesize,
						condition, orgCode);
			}
		} else if (searchtype.indexOf("dept") != -1) {
			DepartmentCondition dc = new DepartmentCondition();
			if (searchtype.indexOf("deptid") != -1) {
				dc.setId(searchcontent);
			} else {
				dc.setName(searchcontent);
			}
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser))) {
				upr = getUUMService().searchUsersByDepartmentCondition(page, pagesize, dc,
						loginUser);
			} else {
				Department org = getUUMService().getDepartmentByDeptCode(orgCode);
				upr = getUUMService().searchUsersByDepartmentConditionAndOrgCode(page, pagesize,
						dc, org);
			}
		} else {
			if (isSuper || (InitParameters.isCqGroupAuthor() && isModifyGroup(loginUser))) {
				upr = getUUMService().searchUsersByCondition(page, pagesize, condition);
			} else {
				upr = getUUMService().searchUsersByConditionAndOrgCode(page, pagesize, condition,
						orgCode);
			}
		}
		return upr;
	}

	/**
	 * 方法说明：createUserListDataTable
	 * 
	 * @return UserListDataTable
	 */
	private UserListDataTable createUserListDataTable()
	{
		GotoPageCallback gotoCallback = null;
		switch (currentType) {
		case search:
			userPageResult = searchUser(searchcontent, searchtype, currentPage, pagesize);

			gotoCallback = new GotoPageCallback() {

				public void gotoPage(AjaxRequestTarget target, Pager pager)
				{
					currentPage = pager.getCurrentPage();
					pagesize = pager.getPageSize();
					userPageResult = searchUser(searchcontent, searchtype, currentPage, pagesize);
					UserListDataTable datatable = getUserListDataTable(this);
					userListDataTable.replaceWith(datatable);
					userListDataTable = datatable;
					target.add(datatable);
				}
			};
			break;
		case recover:
			userPageResult = getUUMService().getLogicDelUsersUnderDeptAndSubDept(currentPage,
					pagesize, dept);

			gotoCallback = new GotoPageCallback() {

				public void gotoPage(AjaxRequestTarget target, Pager pager)
				{
					currentPage = pager.getCurrentPage();
					pagesize = pager.getPageSize();
					userPageResult = getUUMService().getLogicDelUsersUnderDeptAndSubDept(
							currentPage, pagesize, dept);
					UserListDataTable datatable = getUserListDataTable(this);
					userListDataTable.replaceWith(datatable);
					userListDataTable = datatable;
					target.add(datatable);
				}
			};
			break;
		default:
			if (isviewsubdept) {
				userPageResult = getUUMService().getUserMembersUnderDepartment(currentPage,
						pagesize, dept);
			} else {
				userPageResult = getUUMService().getUserMembersByDepartment(currentPage, pagesize,
						dept);
			}

			gotoCallback = new GotoPageCallback() {

				public void gotoPage(AjaxRequestTarget target, Pager pager)
				{
					currentPage = pager.getCurrentPage();
					pagesize = pager.getPageSize();
					if (isviewsubdept) {
						userPageResult = getUUMService().getUserMembersUnderDepartment(currentPage,
								pagesize, dept);
					} else {
						userPageResult = getUUMService().getUserMembersByDepartment(currentPage,
								pagesize, dept);
					}
					UserListDataTable datatable = getUserListDataTable(this);
					userListDataTable.replaceWith(datatable);
					userListDataTable = datatable;
					target.add(datatable);

				}
			};

		}
		return getUserListDataTable(gotoCallback);
	}

	/**
	 * 方法说明：getUserListDataTable
	 * 
	 * @param gotoCallback
	 *            gotoCallback
	 * @return UserListDataTable
	 */
	public UserListDataTable getUserListDataTable(GotoPageCallback gotoCallback)
	{
		return new UserListDataTable("userListDataTable", userPageResult, gotoCallback,
				!currentType.equals(UserListType.recover)) {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public void createOtherColumns(List columns)
			{
				createDataTableOtherColumns(columns, currentType);
			}

			@Override
			protected void onClickLink(User user, AjaxRequestTarget target)
			{
				// TODO Auto-generated method stub
				final WebMarkupContainer mainContainer = (WebMarkupContainer) getPage().get(
						"contentPanel:contentForm:mainContainer");

				mainContainer.addOrReplace(new CreateUserPanel("mainPanel", user, null) {

					@Override
					public void gobackOther(AjaxRequestTarget target)
					{
						// TODO Auto-generated method stub

						mainContainer.addOrReplace(new UserListPanel("mainPanel", dept,
								currentType, currentPage, pagesize, isviewsubdept, searchcontent,
								searchtype, null));
						target.add(mainContainer);
					}

				});
				if (target != null) {
					target.add(mainContainer);
				}
			}

		};
	}

}
