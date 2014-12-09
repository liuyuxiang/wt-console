package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:用户扩展属性信息面板
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
public class ModifyInfoPanel extends BaseUUMPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120833043617049477L;

	/**
	 * 用户扩展属性列表面板
	 */
	private ModifyInfoAttrPanel userAttrListPanel;

	public ModifyInfoAttrPanel getUserAttrListPanel()
	{
		return userAttrListPanel;
	}

	/**
	 * @param id
	 *            id
	 * @param user
	 *            用户
	 * @param catagory
	 *            资源类别
	 */
	public ModifyInfoPanel(String id, User user, String catagory)
	{
		this(id);
		boolean isNew = StringUtils.isBlank(user.getUuid());
		List<Attribute> attrList = null;		
		if (!isNew) {
			attrList = getUUMService().getAttributesUnderResourceOnPage(user, catagory);
		} else {
			attrList = new ArrayList<Attribute>();
		}
		userAttrListPanel = new ModifyInfoAttrPanel("attr1", attrList, catagory);
		
		Department dept = getUUMService().getUserPrimaryDepartment(user);
		
		Department org = getUUMService().getOrganization(dept.getUuid());
		
		if(org==null){
			org = getUUMService().getDepartmentRoot();
		}
		
		add(new Label("name", user.getName()));
		add(new Label("loginName", user.getId()));
		add(new Label("comp", org.getName()));
		add(new Label("dept", dept.getName()));
		add(new Label("duty", user.getPlainPassword()));
		add(userAttrListPanel);
		
	}

	/**
	 * @param id id
	 */
	public ModifyInfoPanel(String id)
	{
		super(id);
		
	}

}
