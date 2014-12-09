package com.wt.uum2.web.wicket.panel.user.password;

import java.util.List;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.web.wicket.panel.user.UserAttrListPanel;

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
public class PasswordAttrListPanel extends UserAttrListPanel
{

	/**
	 * @param id
	 *            attributeTypes
	 * @param attributes
	 *            attributes
	 * @param attributeTypes
	 *            attributeTypes
	 */
	public PasswordAttrListPanel(String id, List<Attribute> attributes,
			List<AttributeType> attributeTypes)
	{
		super(id, attributes, attributeTypes);
	}

}
