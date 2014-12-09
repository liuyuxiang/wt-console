package com.wt.uum2.web.wicket.panel.dept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.link.InlineFrame;
import org.apache.wicket.markup.html.pages.RedirectPage;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.form.AjaxRefreshFeedbackForm;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.DeptGobackButton;
import com.wt.uum2.web.wicket.panel.user.UserAttributePanel;

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
public abstract class ModifyDeptPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private DeptPropertiesPanel deptPropertiesPanel;
	/**
	 * 
	 */
	private String oldName;
	/**
	 * 
	 */
	private Long oldOrder;
	/**
	 * 
	 */
	private String oldCode;
	/**
	 * 
	 */
	private String oldPath;

	/**
	 * @param id
	 *            id
	 * @param currentDept
	 *            currentDept
	 */
	public ModifyDeptPanel(String id, Department currentDept)
	{
		super(id);
		init(currentDept);

	}

	/**
	 * 方法说明：
	 * 
	 * @param currentDept
	 *            currentDept
	 */
	protected void init(final Department currentDept)
	{
		oldName = currentDept.getName();
		oldOrder = currentDept.getOrder();
		oldCode = currentDept.getCode();
		oldPath = currentDept.getPath();
		final AjaxRefreshFeedbackForm<Void> UPDATEDEPTFORM = new AjaxRefreshFeedbackForm<Void>(
				"updateDeptForm");
		add(UPDATEDEPTFORM);
		DeptGobackButton deptGobackButton = new DeptGobackButton("goBackButton", new Model(),
				currentDept);

		UPDATEDEPTFORM.add(new Label("titletext", Model.of("修改【" + currentDept.getName() + "】属性")));
		final List<Attribute> attlist = getUUMService().getAttributesUnderResource(currentDept);
		final List<AttributeType> attributeTypeList = getUUMService()
				.getAttributeTypeByResourceOnPage(currentDept, null, null);

		deptPropertiesPanel = new DeptPropertiesPanel("deptPropertiesPanel",
				currentDept.getParent(), currentDept, attributeTypeList, attlist, false);
		UPDATEDEPTFORM.add(deptPropertiesPanel.setOutputMarkupId(true));
		UPDATEDEPTFORM.add(new AjaxWaitingButton("submit", new Model(), UPDATEDEPTFORM) {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				Map<String, String[]> cMap = new HashMap<String, String[]>();
				fillChangedInfoToMap(cMap, currentDept);
				updateParentAndManagedGroups(cMap, currentDept);
				// 添加扩展属性的变化
				fillChangedAttrToMap(deptPropertiesPanel.getAttrListPanel().getAttributePanels(),
						cMap);

				saveAttributes(deptPropertiesPanel.getAttrListPanel().getAttributePanels(),
						currentDept);

				// 生成事件
				if (!cMap.isEmpty()) {
					getEventListenerHandler().handle(
							getEventFactory().createEventUpdateDept(currentDept.getUuid(), cMap));
				}

				goTo(target, currentDept, deptPropertiesPanel.getDeptLabelList().getList().get(0));

			}

			@Override
			protected void onError(final AjaxRequestTarget target, Form<?> form)
			{
			}

		});
		/*UPDATEDEPTFORM.add(new Button("resetButton", new Model()) {

			@Override
			public void onSubmit()
			{
				getForm().modelChanging();
				getForm().modelChanged();
				getForm().clearInput();

			}
		}.setDefaultFormProcessing(false));*/
		UPDATEDEPTFORM.add(new AjaxButton("resetButton") {

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form)
			{
				Department department = getUUMService().getDepartmentByUUID(currentDept.getUuid());
				DeptPropertiesPanel newpanel = new DeptPropertiesPanel("deptPropertiesPanel",
						department.getParent(), department, attributeTypeList, attlist, false);
				deptPropertiesPanel.replaceWith(newpanel);
				deptPropertiesPanel = newpanel;
				target.add(newpanel);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form)
			{
			}

		}.setDefaultFormProcessing(false));
		UPDATEDEPTFORM.add(deptGobackButton);

		RedirectPage page = new RedirectPage(getRequest().getContextPath()
				+ "/audit/resourceLog.nsf?uuid=" + currentDept.getUuid());
		final InlineFrame INLINEFRAME = new InlineFrame("logs", page);
		UPDATEDEPTFORM.add(INLINEFRAME.setVisible(false).setOutputMarkupId(true)
				.setOutputMarkupPlaceholderTag(true));
		UPDATEDEPTFORM.add(new AjaxLink("deptlog") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (!INLINEFRAME.isVisible()) {
					target.add(INLINEFRAME.setVisible(true));
				}
			}
		}).setOutputMarkupId(true);
		UPDATEDEPTFORM.add(new AjaxLink("deptlogclose") {
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				if (INLINEFRAME.isVisible()) {
					target.add(INLINEFRAME.setVisible(false));
				}
			}
		}).setOutputMarkupId(true);

	}

	/**
	 * 方法说明：goTo
	 * 
	 * @param target
	 *            target
	 * @param newDept
	 *            newDept
	 * @param parentDept
	 *            parentDept
	 */
	public abstract void goTo(AjaxRequestTarget target, Department newDept, Department parentDept);

	/**
	 * 方法说明：添加用户修改信息到MAP
	 * 
	 * @param cMap
	 *            cMap
	 * @param currentDept
	 *            currentDept
	 */
	public void fillChangedInfoToMap(Map<String, String[]> cMap, Department currentDept)
	{
		if (!StringUtils.equals(oldName, currentDept.getName())) {
			cMap.put("name", new String[] { oldName, currentDept.getName() });
		}
		if (oldOrder.longValue() != currentDept.getOrder().longValue()) {
			cMap.put(
					"orderNum",
					new String[] { String.valueOf(oldOrder), String.valueOf(currentDept.getOrder()) });
		}
		if (!StringUtils.equals(oldCode, currentDept.getCode())) {
			cMap.put("code", new String[] { oldCode, currentDept.getCode() });
		}

	}

	/**
	 * 方法说明：
	 * 
	 * @param cMap
	 *            cMap
	 * @param currentDept
	 *            currentDept
	 */
	public void updateParentAndManagedGroups(Map<String, String[]> cMap, Department currentDept)
	{

		if (CollectionUtils.isNotEmpty(deptPropertiesPanel.getDeptLabelList().getList())) {
			// 更新上级部门信息
			Department parent = deptPropertiesPanel.getDeptLabelList().getList().get(0);
			if (!StringUtils.equals(parent.getUuid(), currentDept.getParentUUID())) {
				if (!parent.isHasChildren()) {
					parent.setHasChildren(true);
					getUUMService().updateDepartment(parent);
				}
				cMap.put("parentUUID",
						new String[] { currentDept.getParentUUID(), parent.getUuid() });
				getUUMService().moveDepartmentToNewParent(currentDept, parent);
				currentDept.setParent(parent);
				currentDept.setParentUUID(parent.getUuid());
				currentDept.setDeptParentCode(parent.getDeptCode());
			}
			// 更新部门的path信息

			String newPath = parent.getPath() + "→" + currentDept.getName();
			if (!StringUtils.equals(oldPath, newPath)) {
				if (parent.getPath().equals(oldPath)) {
					newPath = currentDept.getName();
				}
				getUUMService().updateDeptPath(newPath, oldPath);
				currentDept.setPath(newPath);
			}
			getUUMService().updateDepartment(currentDept);

		}

		// 部门的管理组
		List<Group> grouplist = new ArrayList<Group>();
		List<Group> adminList = getUUMService().getDepartmentManagedGroups(currentDept);

		if (CollectionUtils.isNotEmpty(deptPropertiesPanel.getAdminGroupLabelList().getList())) {
			grouplist = deptPropertiesPanel.getAdminGroupLabelList().getList();
		}

		if (!CollectionUtils.isEqualCollection(adminList, grouplist)) {
			getUUMService().updateDeartmentManagerGroups(currentDept, grouplist);
			// 部门和组的关系欠缺
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
	private void fillChangedAttrToMap(List<UserAttributePanel> panels, Map<String, String[]> cMap)
	{

		for (UserAttributePanel p : panels) {
			if (p.isChanged()) {
				cMap.put(p.getAttribute().getType().getId(), new String[] { p.getOldValue(),
						p.getAttribute().getValue() });
			}
		}
	}

}
