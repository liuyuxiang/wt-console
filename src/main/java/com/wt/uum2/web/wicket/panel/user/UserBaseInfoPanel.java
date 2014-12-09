package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.IAjaxIndicatorAware;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.dept.DeptLabelListPanel;
import com.wt.uum2.web.wicket.panel.group.GroupLabelListPanel;

/**
 * <pre>
 * 业务名:用户基本信息面板
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserBaseInfoPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private List<Department> deptList;

	/**
	 * 
	 */
	private List<Group> groupList;

	/**
	 * 
	 */
	private List<Group> adminGroupList;

	/**
	 * 
	 */
	private final DeptLabelListPanel deptLabelList;

	/**
	 * 
	 */
	private final GroupLabelListPanel groupLabelList;

	/**
	 * 
	 */
	private final GroupLabelListPanel adminGroupLabelList;

	/**
	 * 
	 */
	private final Department dept;

	/**
	 * 
	 */
	private final UserAttrListPanel userAttrListPanel;

	/**
	 * 
	 */
	private final String oldName;

	/**
	 * 
	 */
	private final Long oldOrder;

	/**
	 * 
	 */
	private final User user;

	/**
	 * 
	 */
	private UseridTextFieldPanel useridTextFieldPanel;

	/**
	 * 
	 */
	private FeedbackPanel nameFeedback;
	/**
	 * 
	 */
	private User loginUser;

	/**
	 * 
	 */
	private AjaxIndicatorAppender indicatorAppender;

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120833043617049477L;

	protected boolean isNew()
	{
		return StringUtils.isBlank(user.getUuid());
	}

	/**
	 * <pre>
	 * 业务名:用户名文本框
	 * 功能说明: 
	 * 编写日期:	2011-9-27
	 * 作者:	Administrator
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	private abstract class NameTextField extends TextField<String> implements IAjaxIndicatorAware
	{

		/**
		 * @param id
		 *            id
		 * @param type
		 *            type
		 */
		public NameTextField(String id, Class<String> type)
		{
			super(id, type);
		}

		/**
		 * @param id
		 *            id
		 * @param model
		 *            model
		 * @param type
		 *            type
		 */
		public NameTextField(String id, IModel<String> model, Class<String> type)
		{
			super(id, model, type);
		}

		/**
		 * @param id
		 *            id
		 * @param model
		 *            model
		 */
		public NameTextField(String id, IModel<String> model)
		{
			super(id, model);
		}

		/**
		 * @param id
		 *            id
		 */
		public NameTextField(String id)
		{
			super(id);
		}

	}

	/**
	 * @param id
	 *            id
	 * @param user
	 *            用户
	 * @param department
	 *            部门
	 */
	public UserBaseInfoPanel(String id, final User user, Department department)
	{
		super(id);

		dept = department;

		oldName = user.getName();
		oldOrder = user.getOrder();

		this.user = user;
		groupList = new ArrayList<Group>();
		adminGroupList = new ArrayList<Group>();
		deptList = new ArrayList<Department>();
		loginUser = getUUMService().getLoginUser();

		if (isNew()) {
			deptList.add(dept);
		} else {
			deptList = getUUMService().getUserDepartments(user);
			groupList = getUUMService().getUserGroups(user);
			adminGroupList = getUUMService().getUserAdminGroup(user);
		}

		indicatorAppender = new AjaxIndicatorAppender();
		NameTextField nameTextField = new NameTextField("name", new PropertyModel<String>(user,
				"name")) {
			public String getAjaxIndicatorMarkupId()
			{
				return indicatorAppender.getMarkupId();
			}
		};

		add(nameTextField.setRequired(true));

		nameTextField.add(new AjaxFormComponentUpdatingBehavior("onblur") {

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e)
			{
				super.onError(target, e);
				target.add(nameFeedback);
			}

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				target.add(nameFeedback);
			}

		});
		nameFeedback = new SimpleFeedbackPanel("nameFeedback", new ComponentFeedbackMessageFilter(
				nameTextField));
		add(nameFeedback.setOutputMarkupId(true));

		useridTextFieldPanel = new UseridTextFieldPanel("userid", new PropertyModel<String>(user,
				"id"));
		useridTextFieldPanel.getUseridTextField().add(indicatorAppender);
		add(useridTextFieldPanel);
		/*useridTextField.add(new PatternValidator("[a-zA-Z0-9_\u4e00-\u9fa5]"));*/

		AjaxFormComponentUpdatingBehavior ajaxFormComponentUpdatingBehavior = new AjaxFormComponentUpdatingBehavior(
				"onchange") {

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				useridTextFieldPanel.genUserId(user.getName(), dept, target);
				if (StringUtils.isNotBlank(getSetting().getAutoCompleMailSuffix())
						&& CollectionUtils.isNotEmpty(getUUMService().getAttributeTypeById("mail"))) {

					for (UserAttributePanel p : userAttrListPanel.getAttributePanels()) {
						if ("mail".equals(p.getAttribute().getType().getId())) {
							p.getAttribute().setValue(
									useridTextFieldPanel.getUseridTextField().getModelObject()
											+ getSetting().getAutoCompleMailSuffix());
							target.add(p);
							break;
						}
					}
				}

			}

		};
		if (isNew()) {
			nameTextField.add(ajaxFormComponentUpdatingBehavior);
		}

		add(new TextField<String>("primaryUserUUID", new PropertyModel<String>(user,
				"primaryUserUUID")));

		deptLabelList = new DeptLabelListPanel("depts", deptList, InitParameters.isMultiDept());
		if (isNew()) {
			deptLabelList.setEnabled(false);
		}
		add(deptLabelList.setLabel(new ResourceModel("depts")).setRequired(true));

		groupLabelList = new GroupLabelListPanel("groups", groupList);
		add(groupLabelList.setLabel(new ResourceModel("groups")).setRequired(true));

		boolean isCreateDefaultAdminGroup = InitParameters.isCreateDefaultAdminUser() && isNew();
		if (isCreateDefaultAdminGroup) {
			adminGroupList.add(getUUMService().getGroupByCode(InitParameters.getSuperGroupCode()));
		}

		adminGroupLabelList = new GroupLabelListPanel("adminGroups", adminGroupList);
		add(adminGroupLabelList.setLabel(new ResourceModel("adminGroups")));

		// if (isCreateDefaultAdminGroup) {
		// adminGroupLabelList.setEnabled(false);
		// }
		TextField<Long> orderNum;
		add(orderNum = new TextField<Long>("orderNum", new PropertyModel<Long>(user, "order")));
		orderNum.setRequired(true);
		add(new SimpleFeedbackPanel("orderFeedback", new ComponentFeedbackMessageFilter(orderNum))
				.setOutputMarkupId(true).setMarkupId("orderFeedback"));

		// add(new Image("leftImg", new
		// ContextRelativeResource("/style/default/images/tag11.gif")));
		add(new TextField<Long>("currentAuthorLevel", new PropertyModel<Long>(user,
				"currentAuthorLevel")));
		List<Attribute> attrList = null;
		if (!isNew()) {
			attrList = getUUMService().getAttributesUnderResourceOnPage(user, "0");
		} else {
			attrList = new ArrayList<Attribute>();
		}

		userAttrListPanel = new UserAttrListPanel("attr0", attrList, "0");
		add(userAttrListPanel.setOutputMarkupId(true));

		// TODO 一样的逻辑是否需要删除if
		if (!isNew()) {
			useridTextFieldPanel.getUseridTextField().setEnabled(false);
		} else if (getUUMService().isVirtualUser(dept)) {
			useridTextFieldPanel.getUseridTextField().setEnabled(false);
		}
	}

	/**
	 * @return the deptLabelList
	 */
	public DeptLabelListPanel getDeptLabelList()
	{
		return deptLabelList;
	}

	/**
	 * @return the groupLabelList
	 */
	public GroupLabelListPanel getGroupLabelList()
	{
		return groupLabelList;
	}

	/**
	 * @return the adminGroupLabelList
	 */
	public GroupLabelListPanel getAdminGroupLabelList()
	{
		return adminGroupLabelList;
	}

	/**
	 * @return the userAttrListPanel
	 */
	public UserAttrListPanel getUserAttrListPanel()
	{
		return userAttrListPanel;
	}

	/**
	 * @return the user
	 */
	public User getUser()
	{
		return user;
	}

	/**
	 * 方法说明：添加用户修改信息到MAP
	 * 
	 * @param cMap
	 *            map
	 */
	public void fillChangedUserBaseInfoToMap(Map<String, String[]> cMap)
	{
		if (!StringUtils.equals(useridTextFieldPanel.getOldId(), getUser().getId())) {
			cMap.put("id", new String[] { useridTextFieldPanel.getOldId(), getUser().getId() });
		}
		if (!StringUtils.equals(oldName, getUser().getName())) {
			cMap.put("name", new String[] { oldName, getUser().getName() });
		}
		if (oldOrder.longValue() != getUser().getOrder().longValue()) {
			cMap.put("order",
					new String[] { String.valueOf(oldOrder), String.valueOf(getUser().getOrder()) });
		}
	}

}