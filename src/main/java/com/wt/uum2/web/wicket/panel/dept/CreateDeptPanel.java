package com.wt.uum2.web.wicket.panel.dept;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.hirisun.components.other.project.ProjectResolver;
import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.form.AjaxRefreshFeedbackForm;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.ResourceType;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.DeptGobackButton;
import com.wt.uum2.web.wicket.panel.user.UserAttributePanel;

/**
 * <pre>
 * 业务名:创建部门
 * 功能说明: 
 * 编写日期:	2011-12-12
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class CreateDeptPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6262759151517197017L;

	/**
	 * 
	 */
	private Department currentDept;

	/**
	 * 
	 */
	private DeptPropertiesPanel deptPropertiesPanel;
	/**
	 * 
	 */
	private DeptPropertiesPanel stairDeptPropertiesPanel;

	final List<AttributeType> typeList = getUUMService().getAttributeTypeAllByResource(
			new Department(), ResourceType.DEPARTMENT.intValue());

	/**
	 * @param id
	 *            id
	 * @param currentDept
	 *            当前部门
	 */
	public CreateDeptPanel(String id, Department currentDept)
	{
		super(id);
		this.currentDept = currentDept;
		createSubDept();
		createStairDept();
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void createSubDept()
	{
		final Department NEWDEPT = new Department();
		if(StringUtils.equals(currentDept.getOrgCode(), "customer")){
			NEWDEPT.setDeptCode("customer"+getUUMService().countDepartment()+1);
		}
		final AjaxRefreshFeedbackForm<Void> SUBFORM = new AjaxRefreshFeedbackForm<Void>("subForm");
		add(SUBFORM);

		SUBFORM.add(new Label("titletext", Model.of("【" + currentDept.getName() + "】")));
		deptPropertiesPanel = new DeptPropertiesPanel("deptPropertiesPanel", currentDept, NEWDEPT,
				typeList, new ArrayList<Attribute>(), true);
		SUBFORM.add(deptPropertiesPanel.setOutputMarkupId(true));

		SUBFORM.add(new AjaxWaitingButton("submit", new Model(), SUBFORM) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{

				createDeptHandle(currentDept, NEWDEPT, deptPropertiesPanel.getAdminGroupLabelList()
						.getList(), deptPropertiesPanel.getAttrListPanel().getAttributePanels());
				goTo(target, NEWDEPT, false);
			}

			@Override
			protected void onError(final AjaxRequestTarget target, Form<?> form)
			{
			}

		});
		/*SUBFORM.add(new Button("resetButton", new Model()) {

			@Override
			public void onSubmit()
			{
				getForm().modelChanging();
				getForm().modelChanged();
				getForm().clearInput();

			}
		}.setDefaultFormProcessing(false));*/
		SUBFORM.add(new AjaxButton("resetButton") {

			/**
			 * 
			 */
			private static final long serialVersionUID = -5819182924340317669L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				DeptPropertiesPanel newPanel = new DeptPropertiesPanel("deptPropertiesPanel",
						currentDept, new Department(), typeList, new ArrayList<Attribute>(), true);
				deptPropertiesPanel.replaceWith(newPanel);
				deptPropertiesPanel = newPanel;
				target.add(newPanel);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
			}
		}.setDefaultFormProcessing(false));

		SUBFORM.add(new DeptGobackButton("goBackButton", new Model(), currentDept));
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void createStairDept()
	{
		final Department NEWDEPT = new Department();
		final AjaxRefreshFeedbackForm<Void> STAIRFORM = new AjaxRefreshFeedbackForm<Void>(
				"stairForm");
		add(STAIRFORM);
		if (!getUUMService().isUserinSuperGroup(loginUser)) {// 设置只有超级管理员才可以创建一级部门
			STAIRFORM.setVisible(false);
		}
		final Department PARENTDEPT = getUUMService().getDepartmentRoot();
		STAIRFORM.add(new Label("parentDeptName", new Model(PARENTDEPT.getName())));

		stairDeptPropertiesPanel = new DeptPropertiesPanel("stairDeptPropertiesPanel", PARENTDEPT,
				NEWDEPT, typeList, new ArrayList<Attribute>(), true, "stair");

		STAIRFORM.add(stairDeptPropertiesPanel.setOutputMarkupId(true));

		STAIRFORM.add(new AjaxWaitingButton("submit", new Model(), STAIRFORM) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{

				createDeptHandle(PARENTDEPT, NEWDEPT, stairDeptPropertiesPanel
						.getAdminGroupLabelList().getList(), stairDeptPropertiesPanel
						.getAttrListPanel().getAttributePanels());
				goTo(target, NEWDEPT, true);

			}

			@Override
			protected void onError(final AjaxRequestTarget target, Form<?> form)
			{
			}

		});

		/*STAIRFORM.add(new Button("resetButton", new Model()) {

			@Override
			public void onSubmit()
			{
				getForm().modelChanging();
				getForm().modelChanged();
				getForm().clearInput();

			}
		}.setDefaultFormProcessing(false));*/

		STAIRFORM.add(new AjaxButton("resetButton") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				DeptPropertiesPanel newPanel = new DeptPropertiesPanel("stairDeptPropertiesPanel",
						PARENTDEPT, new Department(), "stair");
				stairDeptPropertiesPanel.replaceWith(newPanel);
				stairDeptPropertiesPanel = newPanel;
				target.add(newPanel);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
				// TODO Auto-generated method stub

			}
		}.setDefaultFormProcessing(false));
	}

	/**
	 * 方法说明：goTo
	 * 
	 * @param target
	 *            target
	 * @param newDept
	 *            newDept
	 * @param parentisroot
	 *            parentisroot
	 */
	public abstract void goTo(AjaxRequestTarget target, Department newDept, boolean parentisroot);

	/**
	 * 方法说明：createDeptHandle
	 * 
	 * @param parent
	 *            parent
	 * @param dept
	 *            dept
	 * @param adminGroupLabelList
	 *            adminGroupLabelList
	 * @param panels
	 *            panels
	 */
	public void createDeptHandle(Department parent, Department dept,
			List<Group> adminGroupLabelList, List<UserAttributePanel> panels)
	{
		dept.setParent(parent);
		dept.setParentUUID(parent.getUuid());
		if (parent.getUuid().equalsIgnoreCase(getUUMService().getDepartmentRoot().getUuid())) {
			dept.setOrgCode(dept.getDeptCode());
			dept.setStatus(ResourceStatus.NORMAL.intValue());
		} else {
			dept.setOrgCode(parent.getOrgCode());
			dept.setStatus(ResourceStatus.NORMAL.intValue());
		}
		dept.setDeptParentCode(parent.getDeptCode());
		dept.setHasChildren(false);
		dept.setPath(parent.getPath() + "→" + dept.getName());
		DepartmentAuthor author = new DepartmentAuthor();

		if (CollectionUtils.isNotEmpty(adminGroupLabelList)) {
			dept.setAdminGroups(new HashSet<Group>(adminGroupLabelList));
		}
		dept.setRtxCode(getUUMService().countDepartmentForRtx());
		if (!parent.isHasChildren()) {
			parent.setHasChildren(true);
			getUUMService().updateDepartment(parent);
		}
		getUUMService().createDepartment(dept);

		if (StringUtils.equals(ProjectResolver.getId(), "bj")) {
			// 北供创建ids_dn属性
			createDeptSyncToIDS(parent, dept);
		}
		saveAttributes(panels, dept);
		// 保存扩展属性
		if (author.getLevels() != null) {
			dept = getUUMService().getDepartmentByDeptCode(dept.getDeptCode());
			author.setDepartment(dept);
			getUUMService().saveDepartmentAuthor(author);
		}

		Event event = getEventFactory().createEventCreateDept(dept.getUuid());
		getEventListenerHandler().handle(event);
	}

	/**
	 * 方法说明：
	 * 
	 * @param parent
	 *            parent
	 * @param current
	 *            current
	 */
	private void createDeptSyncToIDS(Department parent, Department current)
	{
		List<AttributeType> attributeTypeList = getUUMService().getAttributeTypeById("dept_ids_dn");
		for (int i = 0; i < attributeTypeList.size(); i++) {
			String parentDN = getDeptDNWithIBM(parent);
			AttributeType attributeType = attributeTypeList.get(i);
			Attribute att = new Attribute();
			StringValue sv = new StringValue();
			Set<AttributeValue> av = new HashSet<AttributeValue>();
			att.setType(attributeType);
			att.setOwnerResource(current);
			av.add(sv);
			att.setAttValues(av);
			sv.setAttribute(att);
			sv.setValue(parentDN.equals("") ? "" : ("ou=" + current.getDeptCode() + "," + parentDN));
			getUUMService().saveAttribute(att);
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param dept
	 *            dept
	 * @return String
	 */
	private String getDeptDNWithIBM(Department dept)
	{
		String dn = "";
		List<String> keys = new ArrayList<String>();
		keys.add("dept_ids_dn");
		List<com.wt.uum2.domain.Attribute> listatt = getUUMService()
				.getAttributesByAttributeTypeIdKey(dept, keys);
		if (!listatt.isEmpty() && !listatt.get(0).getAttValues().isEmpty()
				&& listatt.get(0).getAttValues().iterator().hasNext()) {
			dn = ((StringValue) listatt.get(0).getAttValues().iterator().next()).getValue();
		}
		return dn == null ? "" : dn;
	}

}