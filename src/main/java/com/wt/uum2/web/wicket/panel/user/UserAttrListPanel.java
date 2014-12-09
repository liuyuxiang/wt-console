package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:用户扩展属性类型列表面板
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
public class UserAttrListPanel extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7215498705925707305L;
	/**
	 * 扩展属性类型列表
	 */
	private final List<AttributeType> attributeTypeList;
	/**
	 * 扩展属性列表
	 */
	private final List<Attribute> attributeList;
	/**
	 * 扩展属性面板
	 */
	private final List<UserAttributePanel> attributePanels;
	/**
	 * 创建用户时数据来源
	 */
	private boolean isCreateDataCameFromAttr = false;
	/**
	 * 
	 */
	private boolean isLoginDisabled;
	/**
	 * 
	 */
	private boolean loginDisabledAttr = false;
	public List<UserAttributePanel> getAttributePanels()
	{
		return attributePanels;
	}

	/**
	 * @param id
	 *            id
	 * @param attributes
	 *            attributes
	 * @param catagory
	 *            catagory
	 */
	public UserAttrListPanel(String id, List<Attribute> attributes, String catagory)
	{
		super(id);

		if (StringUtils.isBlank(catagory) || StringUtils.isEmpty(catagory)) {
			catagory = "0";
		}
		attributePanels = new ArrayList<UserAttributePanel>();

		attributeTypeList = getUUMService().getAttributeTypeByResourceOnPage(null, 0, catagory);
		attributeList = attributes;

		initComponents();

	}

	/**
	 * @param id
	 *            id
	 * @param attributes
	 *            id
	 * @param attributeTypes
	 *            id
	 */
	public UserAttrListPanel(String id, List<Attribute> attributes,
			List<AttributeType> attributeTypes)
	{
		super(id);

		attributePanels = new ArrayList<UserAttributePanel>();

		this.attributeTypeList = attributeTypes;
		this.attributeList = attributes;

		initComponents();

	}

	/**
	 * 方法说明：
	 * 
	 * @param type
	 *            type
	 * @return Attribute
	 */
	public Attribute getAttributeByType(AttributeType type)
	{
		Attribute attr = null;
		for (Attribute attribute : attributeList) {
			if (attribute.getTypeUUID().equals(type.getUuid())) {
				attr = attribute;
				break;
			}
		}
		if (attr == null) {
			attr = new Attribute();
			attr.setTypeUUID(type.getUuid());
			if (type.getId().equals("dataCameFrom")) {
				attr.setValue("1");
				isCreateDataCameFromAttr = true;
			}
			if ("loginDisabled".equals(type.getId())) {
				if (getUUMService().isUserinSuperGroup(loginUser)) {
					attr.setValue("false");
				} else {
					attr.setValue("true");
					loginDisabledAttr = true;
				}
				isLoginDisabled = true;
			}
		}
		attr.setType(type);
		return attr;
	}

	/**
	 * 方法说明：
	 * 
	 */
	protected void initComponents()
	{

		IModel<List<AttributeType>> attributeTypeModel = new LoadableDetachableModel<List<AttributeType>>() {

			@Override
			protected List<AttributeType> load()
			{
				return attributeTypeList;
			}

		};
		ListView<AttributeType> attributeListView = new ListView<AttributeType>("attribute0",
				attributeTypeModel) {

			@Override
			protected void populateItem(ListItem<AttributeType> item)
			{
				if (!item.getModelObject().getHidden()) {
					item.add(new Label("tdName", new PropertyModel<String>(item.getModelObject(),
							"name")));
					Attribute newAttribute = getAttributeByType(item.getModelObject());
					
					UserAttributePanel panel2 = new UserAttributePanel("tdContent", newAttribute);
					if (isLoginDisabled) {
						panel2.setTouch(true);
					}
					attributePanels.add(panel2);

					item.add(panel2.setOutputMarkupId(true));
				} else {
					item.add(new Label("tdName", ""));
					item.add(new Label("tdContent", "").setOutputMarkupId(true));
					item.setVisible(false);
				}

				if (isCreateDataCameFromAttr) {
					item.setVisible(false);
				}
				if (loginDisabledAttr) {
					item.setEnabled(false);
				}
			}

		};
		attributeListView.setReuseItems(true);
		add(attributeListView);
	}

}
