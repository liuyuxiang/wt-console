package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.wt.uum2.domain.Attribute;
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
public class UserAttrInfoPanel extends BaseUUMPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2120833043617049477L;

	/**
	 * 用户扩展属性列表面板
	 */
	private UserAttrListPanel userAttrListPanel;

	public UserAttrListPanel getUserAttrListPanel()
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
	public UserAttrInfoPanel(String id, User user, String catagory)
	{
		this(id);
		boolean isNew = StringUtils.isBlank(user.getUuid());
		List<Attribute> attrList = null;		
		if (!isNew) {
			attrList = getUUMService().getAttributesUnderResourceOnPage(user, catagory);
		} else {
			attrList = new ArrayList<Attribute>();
		}
		userAttrListPanel = new UserAttrListPanel("attr1", attrList, catagory);
		add(userAttrListPanel);
		
	}

	/**
	 * @param id id
	 */
	public UserAttrInfoPanel(String id)
	{
		super(id);
		
	}

}
