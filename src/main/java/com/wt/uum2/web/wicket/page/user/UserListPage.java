package com.wt.uum2.web.wicket.page.user;

import java.util.LinkedList;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.string.StringValue;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.DepartmentCondition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.datatable.IsExistCurrentDeptColumn;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;
import com.wt.uum2.web.wicket.panel.datatable.UserListType;
import com.wt.uum2.web.wicket.panel.datatable.UserlistCheckBoxPropertyColumn;
import com.wt.uum2.web.wicket.panel.datatable.UserlistToolbar;
import com.wt.uum2.web.wicket.panel.user.UserListDataTable;

/**
 * <pre>
 * 业务名:用户列表
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
@Deprecated
public class UserListPage extends UUMBasePage
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3125653616402844020L;
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
	public UserlistToolbar getToolbar()
	{
		return (UserlistToolbar)toolbar;
	}
	/**
	 * 
	 */
	
	public UserListPage(){
		 pagesize=15;
		StringValue deptuuid=getRequest().getRequestParameters().getParameterValue("id");
		if(deptuuid.isEmpty()){
			throw new RuntimeException("");
		}else{
			this.dept=getUUMService().getDepartmentByUUID(deptuuid.toString());
			initForm();
		}
	}

	/**
	 * @param d
	 *            部门
	 */
	public UserListPage(Department d)
	{
		pagesize=15;
		
		this.dept=d;
		initForm();
	}

	/**
	 * 方法说明：初始化表单
	 * 
	 */
	public void initForm()
	{
		currentType=UserListType.valueOfString("normal");
		form=new Form<Void>("form");
		add(form);
		addlist=new LinkedList<User>();

		toolbar=new UserlistToolbar("toolbar",dept,currentType,false,null,null,addlist){
			@Override
			public void onCheckedviewSubDept(final boolean checked,AjaxRequestTarget target)
			{
						
				if(checked){
					userPageResult = getUUMService().getUserMembersUnderDepartment(userListDataTable.getPager().getCurrentPage(), userListDataTable.getPager().getPageSize(), dept);
				}else{
					userPageResult = getUUMService().getUserMembersByDepartment(userListDataTable.getPager().getCurrentPage(), userListDataTable.getPager().getPageSize(), dept);
				}
				
				
				GotoPageCallback callback=new GotoPageCallback() {

					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						if(checked){
							userPageResult = getUUMService().getUserMembersUnderDepartment(pager.getCurrentPage(), pager.getPageSize(), dept);
						}else{
							userPageResult = getUUMService().getUserMembersByDepartment(pager.getCurrentPage(), pager.getPageSize(), dept);
						}
					
						replaceWithDataTable(target,userPageResult,userListDataTable.getCallback());

					}					
				};
				
				replaceWithDataTable(target,userPageResult,callback);
				
			}

			@Override
			public void searchUserHandle(final String searchcontent, final String searchtype,boolean isInit,
					AjaxRequestTarget target)
			{
				currentType=UserListType.valueOfString("search");
				if(isInit){
					userPageResult=UserListPage.this.searchUser(searchcontent,searchtype,1,pagesize);
				}else{
					userPageResult=UserListPage.this.searchUser(searchcontent,searchtype,userListDataTable.getPager().getCurrentPage(), userListDataTable.getPager().getPageSize());
				}
				GotoPageCallback callback=new GotoPageCallback() {

					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						userPageResult=UserListPage.this.searchUser(searchcontent,searchtype,pager.getCurrentPage(),pager.getPageSize());					
						replaceWithDataTable(target,userPageResult,userListDataTable.getCallback());

					}					
				};
				replaceWithDataTable(target,userPageResult,callback);
							
			}

			@Override
			public void selectDeletedUserListHandle(AjaxRequestTarget target,boolean isInit)
			{
				currentType=UserListType.valueOfString("recover");
				if(isInit){
					userPageResult = getUUMService().getLogicDelUsersUnderDeptAndSubDept(1,pagesize, dept);
				}else{
					userPageResult = getUUMService().getLogicDelUsersUnderDeptAndSubDept(userListDataTable.getPager().getCurrentPage(), userListDataTable.getPager().getPageSize(), dept);
				}
				
				GotoPageCallback callback=new GotoPageCallback() {
					
					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						userPageResult = getUUMService().getLogicDelUsersUnderDeptAndSubDept(pager.getCurrentPage(),pager.getPageSize(), dept);
						replaceWithDataTable(target,userPageResult,userListDataTable.getCallback());
						
					}
				};
				replaceWithDataTable(target,userPageResult,callback);
			}

			

			
		};
		form.add(toolbar.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));
		userPageResult = getUUMService().getUserMembersByDepartment(1, pagesize, dept);
		userListDataTable = new UserListDataTable("userListDataTable", userPageResult,
				new GotoPageCallback() {

					public void gotoPage(AjaxRequestTarget target, Pager pager)
					{
						userPageResult=getUUMService().getUserMembersByDepartment(pager.getCurrentPage(),pager.getPageSize(), dept);
						replaceWithDataTable(target,userPageResult,userListDataTable.getCallback());

					}					
		}){

			@Override
			public void createOtherColumns(List columns)
			{
				createDataTableOtherColumns(columns,currentType);
			}

			
		};
		baseColumns=userListDataTable.getBaseColumns();
		form.add(userListDataTable.setOutputMarkupId(true));
		
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 * @param newResult
	 *            newResult
	 * @param callback
	 *            callback
	 */
	public void replaceWithDataTable(AjaxRequestTarget target,UserPageResult<User> newResult,final GotoPageCallback callback){
		UserListDataTable newUserListDataTable=new UserListDataTable(userListDataTable.getId(),newResult,callback){
			@Override
			public void createOtherColumns(List columns)
			{
					createDataTableOtherColumns(columns,currentType);
			}
		};
		userListDataTable.replaceWith(newUserListDataTable);
		userListDataTable=newUserListDataTable;
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
	public void createDataTableOtherColumns(List columns,UserListType type){
		
		switch (type) {
		case search:
			columns.add(new IsExistCurrentDeptColumn<User>(Model.of("属当前部门"), null, dept));
			//if(InitParameters.isCqGroupAuthor() && getUUMService().isUserinSuperGroup(loginUser)){
				columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist,toolbar.getVisibleselecteduser()));
			//}
			break;
		case recover:
			columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist,toolbar.getVisibleselecteduser()));
			break;

		default:
			if(getUUMService().isDepartmentManager(loginUser, dept)||(isModifyGroup(loginUser))){
				columns.add(new UserlistCheckBoxPropertyColumn<User>(addlist,toolbar.getVisibleselecteduser()));
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
	public UserPageResult searchUser(String searchcontent,String searchtype,int page,int pagesize){
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
			if (isSuper||(InitParameters.isCqGroupAuthor()&&isModifyGroup(loginUser))){
				upr = getUUMService().searchERPUsersByCondition(page, pagesize, condition);
			} else {
				upr = getUUMService().searchERPUsersByConditionAndOrgCode(page, pagesize, condition,
						orgCode);
			}
		} else if (searchtype.indexOf("dept") != -1) {
			DepartmentCondition dc = new DepartmentCondition();
			if(searchtype.indexOf("deptid") != -1){
				dc.setId(searchcontent);
			}else{
				dc.setName(searchcontent);
			}
			if (isSuper||(InitParameters.isCqGroupAuthor()&&isModifyGroup(loginUser))){
				upr =getUUMService().searchUsersByDepartmentCondition(page, pagesize,dc,loginUser);
			} else {
				Department org =getUUMService().getDepartmentByDeptCode(orgCode);
				upr =getUUMService().searchUsersByDepartmentConditionAndOrgCode(page, pagesize,dc,org);
			}
		}else {
			if (isSuper||(InitParameters.isCqGroupAuthor()&&isModifyGroup(loginUser))){
				upr = getUUMService().searchUsersByCondition(page, pagesize, condition);
			} else {
				upr = getUUMService().searchUsersByConditionAndOrgCode(page, pagesize, condition,
						orgCode);
			}
		}
		return upr;
	}
	
	
	
}
