package com.wt.uum2.web.wicket.panel.dept;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.validation.validator.MaximumValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator.MaximumLengthValidator;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.group.GroupLabelListPanel;

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
public class DeptPropertiesPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private DeptAttrListPanel attrListPanel;
	/**
	 * 
	 */
	private DeptLabelListPanel deptLabelList;
	/**
	 * 
	 */
	private GroupLabelListPanel adminGroupLabelList;
	/**
	 * 
	 */
	private boolean isNew;

	/**
	 * 
	 */
	private String createLevel;
	/**
	 * 
	 */
	private RequiredTextField<String> nameTextField;
	/**
	 * 
	 */
	private RequiredTextField<String> codeTextField;
	/**
	 * 
	 */
	private RequiredTextField<String> deptCodeTextField;
	/**
	 * 
	 */
	private Department parentDept;
	/**
	 * 
	 */
	private Department dept;

	/**
	 * @param id
	 *            id
	 * @param parentDept
	 *            parentDept
	 * @param dept
	 *            dept
	 * @param typeList
	 *            typeList
	 * @param attributeList
	 *            attributeList
	 * @param isNew
	 *            isNew
	 */
	public DeptPropertiesPanel(String id, Department parentDept, Department dept,
			List<AttributeType> typeList, List<Attribute> attributeList, boolean isNew)
	{
		this(id, parentDept, dept, typeList, attributeList, isNew, "sub");
	}

	/**
	 * @param id
	 *            id
	 * @param parentDept
	 *            parentDept
	 * @param dept
	 *            dept
	 * @param typeList
	 *            typeList
	 * @param attributeList
	 *            attributeList
	 * @param isNew
	 *            isNew
	 * @param createLevel
	 *            createLevel
	 */
	public DeptPropertiesPanel(String id, Department parentDept, Department dept,
			List<AttributeType> typeList, List<Attribute> attributeList, boolean isNew,
			String createLevel)
	{
		super(id);
		this.isNew = isNew;
		this.parentDept = parentDept;
		this.dept = dept;
		this.createLevel = createLevel;
		init(typeList, attributeList);

	}

	/**
	 * @param id
	 *            id
	 * @param parentDept
	 *            parentDept
	 * @param dept
	 *            dept
	 * @param createLevel
	 *            createLevel
	 */
	public DeptPropertiesPanel(String id, Department parentDept, Department dept, String createLevel)
	{
		this(id, parentDept, dept, null, null, true, createLevel);
	}

	/**
	 * 方法说明：
	 * 
	 * @param typeList
	 *            typeList
	 * @param attributeList
	 *            attributeList
	 */
	protected void init(List<AttributeType> typeList, List<Attribute> attributeList)
	{
		// name
		nameTextField = new RequiredTextField<String>("name", new PropertyModel<String>(dept,
				"name")) {

			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@Override
			public void validate()
			{
				super.validate();
				validateName();
			}

		};

		add(nameTextField.setLabel(new ResourceModel("name")).add(new MaximumLengthValidator(30))
				.setOutputMarkupId(true));
		final SimpleFeedbackPanel confirmationname = new SimpleFeedbackPanel("confirmationname",
				new ComponentFeedbackMessageFilter(nameTextField));
		add(confirmationname.setOutputMarkupId(true));

		nameTextField.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e)
			{
				super.onError(target, e);
				target.add(confirmationname);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(confirmationname);
			}

		});

		// order
		RequiredTextField<Integer> orderNumTextField = new RequiredTextField<Integer>("orderNum",
				new PropertyModel<Integer>(dept, "order"));
		add(orderNumTextField.setLabel(new ResourceModel("orderNum")).add(
				new MaximumValidator<Long>(99999l)));
		final SimpleFeedbackPanel confirmationorder = new SimpleFeedbackPanel("confirmationorder",
				new ComponentFeedbackMessageFilter(orderNumTextField));

		add(confirmationorder.setOutputMarkupId(true));

		// code
		Label codeLabel = new Label("codeLabel",
				InitParameters.isCqGroupAuthor() ? new ResourceModel("cqcodeLabel")
						: new ResourceModel("codeLabel"));
		add(codeLabel);
		codeTextField = new RequiredTextField<String>("code", new PropertyModel<String>(dept,
				"code")) {

			/**
					 * 
					 */
			private static final long serialVersionUID = 1L;

			@Override
			public void validate()
			{
				super.validate();
				validateCode();
			}

		};

		codeTextField.add(new PatternValidator("^([A-Z]|[a-z]|[\\d])*$"));
		add(codeTextField
				.setLabel(
						InitParameters.isCqGroupAuthor() ? new ResourceModel("cqcodeLabel")
								: new ResourceModel("codeLabel"))
				.add(new MaximumLengthValidator(20)).setOutputMarkupId(true));
		final SimpleFeedbackPanel confirmationcode = new SimpleFeedbackPanel("confirmationcode",
				new ComponentFeedbackMessageFilter(codeTextField));
		add(confirmationcode.setOutputMarkupId(true));

		codeTextField.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e)
			{
				super.onError(target, e);
				target.add(confirmationcode);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(confirmationcode);

			}
		});

		// deptcode
		Label deptCodeLabel = new Label("deptCodeLabel", new ResourceModel("deptCodeLabel"));
		add(deptCodeLabel);

		deptCodeTextField = new RequiredTextField<String>("deptCode", new PropertyModel<String>(
				dept, "deptCode")) {

			/**
			* 
			*/
			private static final long serialVersionUID = 1L;

			@Override
			public void validate()
			{
				super.validate();
				validateDeptCode();
			}
		};

		deptCodeTextField.add(new PatternValidator("^([A-Z]|[a-z]|[\\d])*$"));
		add(deptCodeTextField.setLabel(new ResourceModel("deptCodeLabel"))
				.add(new MaximumLengthValidator(15)).setOutputMarkupId(true));
		final SimpleFeedbackPanel confirmationdeptcode = new SimpleFeedbackPanel(
				"confirmationdeptcode", new ComponentFeedbackMessageFilter(deptCodeTextField));
		add(confirmationdeptcode.setOutputMarkupId(true));

		if (isNew && "true".equals(InitParameters.getCreateDefaultDeptCode())) {
			dept.setDeptCode(getCode(parentDept));
			deptCodeTextField.setEnabled(false);
		}
		if (!isNew) {
			deptCodeTextField.setEnabled(false);
			codeTextField.setEnabled(false);
		}

		deptCodeTextField.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e)
			{
				super.onError(target, e);
				target.add(confirmationdeptcode);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(confirmationdeptcode);
			}

		});

		List<Department> deptList = new ArrayList<Department>();
		if (isNew && "stair".equals(createLevel)) {
			parentDept = getUUMService().getDepartmentRoot();
		}
		deptList.add(parentDept);
		List<Department> disableList = new ArrayList<Department>();
		disableList.add(dept);
		deptLabelList = new DeptLabelListPanel("deptTree", deptList, disableList, false, false);

		add(deptLabelList.setLabel(new ResourceModel("parentDeptLabel")).setRequired(true));
		if (isNew) {
			deptLabelList.setVisible(false);
		}

		adminGroupLabelList = new GroupLabelListPanel("adminGroups", getUUMService()
				.getDepartmentManagedGroups(dept), false);
		add(adminGroupLabelList.setLabel(new ResourceModel("grouplabel")));

		if (CollectionUtils.isNotEmpty(typeList)) {
			attrListPanel = new DeptAttrListPanel("attr0", attributeList, typeList);
			add(attrListPanel);
		} else {
			attrListPanel = new DeptAttrListPanel("attr0", new ArrayList<Attribute>(),
					new ArrayList<AttributeType>());
			add(attrListPanel.setVisible(false));
		}
	}

	/**
	 * 方法说明：validateName
	 * 
	 */
	public void validateName()
	{
		if (CollectionUtils.isNotEmpty(deptLabelList.getList())) {
			Department selectedDept = deptLabelList.getList().get(0);
			if (!(parentDept.equals(selectedDept) && StringUtils.equals(dept.getName(),
					nameTextField.getValue())))
				if (getUUMService().existDepartmentName(deptLabelList.getList().get(0),
						nameTextField.getValue())) {
					nameTextField.error(nameTextField.getLabel().getObject() + "已经存在！");
				}
		}

	}

	/**
	 * 方法说明：validateCode
	 * 
	 */
	public void validateCode()
	{
		if (isNew && getUUMService().existDepartmentCode(parentDept, codeTextField.getValue())) {
			codeTextField.error(codeTextField.getLabel().getObject() + "已经存在！");
		}
	}

	/**
	 * 方法说明：validateDeptCode
	 * 
	 */
	public void validateDeptCode()
	{
		if (isNew && getUUMService().existDepartmentCode(deptCodeTextField.getValue())) {
			deptCodeTextField.error(deptCodeTextField.getLabel().getObject() + "已经存在！");
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param pDept
	 *            pDept
	 * @return String
	 */
	public String getCode(Department pDept)
	{
		for (int i = 1; i < 100; i++) {
			String code = pDept.getDeptCode();
			code = code.replaceFirst("06", "");
			code = code.replaceAll("0{2,8}", "");
			code += String.valueOf((100 + i)).substring(1);
			if (!getUUMService().existDepartmentCode(("06" + code + "000000").substring(0, 10))) {
				return ("06" + code + "000000").substring(0, 10);
			}
		}
		return null;
	}

	public DeptAttrListPanel getAttrListPanel()
	{
		return attrListPanel;
	}

	public void setAttrListPanel(DeptAttrListPanel attrListPanel)
	{
		this.attrListPanel = attrListPanel;
	}

	public DeptLabelListPanel getDeptLabelList()
	{
		return deptLabelList;
	}

	public void setDeptLabelList(DeptLabelListPanel deptLabelList)
	{
		this.deptLabelList = deptLabelList;
	}

	public GroupLabelListPanel getAdminGroupLabelList()
	{
		return adminGroupLabelList;
	}

	public void setAdminGroupLabelList(GroupLabelListPanel adminGroupLabelList)
	{
		this.adminGroupLabelList = adminGroupLabelList;
	}

}
