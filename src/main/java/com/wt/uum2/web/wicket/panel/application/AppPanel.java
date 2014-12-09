package com.wt.uum2.web.wicket.panel.application;

import java.util.List;
import java.util.Map;

import org.apache.wicket.ajax.AjaxRequestTarget;

import com.wt.uum2.domain.Application;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
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
public abstract class AppPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 *            id
	 * @param app
	 *            app
	 */
	public AppPanel(String id, Application app)
	{
		super(id);
		init(app);

	}

	/**
	 * 方法说明：init
	 * 
	 * @param app
	 *            app
	 */
	protected void init(final Application app)
	{

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
	public abstract void goTo(AjaxRequestTarget target, Application newDept, Application parentDept);

	/**
	 * 方法说明：添加用户修改信息到MAP
	 * 
	 * @param cMap
	 *            cMap
	 * @param currentDept
	 *            currentDept
	 */
	public void fillChangedInfoToMap(Map<String, String[]> cMap, Application currentDept)
	{
		// if (!StringUtils.equals(oldName, currentDept.getName())) {
		// cMap.put("name", new String[] { oldName, currentDept.getName() });
		// }
		// if (oldOrder.longValue() != currentDept.getOrder().longValue()) {
		// cMap.put(
		// "orderNum",
		// new String[] { String.valueOf(oldOrder), String.valueOf(currentDept.getOrder()) });
		// }
		// if (!StringUtils.equals(oldCode, currentDept.getCode())) {
		// cMap.put("code", new String[] { oldCode, currentDept.getCode() });
		// }

	}

	/**
	 * 方法说明：
	 * 
	 * @param cMap
	 *            cMap
	 * @param currentDept
	 *            currentDept
	 */
	public void updateParentAndManagedGroups(Map<String, String[]> cMap, Application currentDept)
	{

		// if (CollectionUtils.isNotEmpty(deptPropertiesPanel.getDeptLabelList().getList())) {
		// // 更新上级部门信息
		// Department parent = deptPropertiesPanel.getDeptLabelList().getList().get(0);
		// if (!StringUtils.equals(parent.getUuid(), currentDept.getParentUUID())) {
		// if (!parent.isHasChildren()) {
		// parent.setHasChildren(true);
		// getUUMService().updateDepartment(parent);
		// }
		// cMap.put("parentUUID",
		// new String[] { currentDept.getParentUUID(), parent.getUuid() });
		// getUUMService().moveDepartmentToNewParent(currentDept, parent);
		// currentDept.setParent(parent);
		// currentDept.setParentUUID(parent.getUuid());
		// currentDept.setDeptParentCode(parent.getDeptCode());
		// }
		// // 更新部门的path信息
		//
		// String newPath = parent.getPath() + "→" + currentDept.getName();
		// if (!StringUtils.equals(oldPath, newPath)) {
		// if (parent.getPath().equals(oldPath)) {
		// newPath = currentDept.getName();
		// }
		// getUUMService().updateDeptPath(newPath, oldPath);
		// currentDept.setPath(newPath);
		// }
		// getUUMService().updateDepartment(currentDept);
		//
		// }
		//
		// // 部门的管理组
		// List<Group> grouplist = new ArrayList<Group>();
		// List<Group> adminList = getUUMService().getDepartmentManagedGroups(currentDept);
		//
		// if (CollectionUtils.isNotEmpty(deptPropertiesPanel.getAdminGroupLabelList().getList())) {
		// grouplist = deptPropertiesPanel.getAdminGroupLabelList().getList();
		// }
		//
		// if (!CollectionUtils.isEqualCollection(adminList, grouplist)) {
		// getUUMService().updateDeartmentManagerGroups(currentDept, grouplist);
		// // 部门和组的关系欠缺
		// }
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
