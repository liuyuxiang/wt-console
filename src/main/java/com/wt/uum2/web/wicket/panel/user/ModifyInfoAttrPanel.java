package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.repeater.RepeatingView;
import org.apache.wicket.model.Model;

import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:用户应用系统信息扩展属性面板
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
public class ModifyInfoAttrPanel extends UserAttrInfoPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 扩展属性类型列表
	 */
	private List<AttributeType> attributeTypeList;
	/**
	 * 扩展属性列表
	 */
	List<Attribute> attributeList;
	/**
	 * 扩展属性面板
	 */
	private List<UserAttributePanel> attributePanels;
	/**
	 * 用户应用系统扩展属性列表面板
	 */
	
	private ModifyInfoAttrPanel userAppAccountPanel;
	public ModifyInfoAttrPanel getUserAppAccountPanel()
	{
		return this.userAppAccountPanel;
	}
	
	public void setUserAppAccountPanel(ModifyInfoAttrPanel userAppAccountPanel)
	{
		this.userAppAccountPanel = userAppAccountPanel;
	}

	public List<UserAttributePanel> getAttributePanels() {
		return attributePanels;
	}

	/**
	 * @param id
	 *            id
	 * @param attrList
	 *            扩展属性列表
	 * @param catagory
	 *            资源类别
	 */
	public ModifyInfoAttrPanel(String id,List<Attribute> attrList,String catagory) {
		super(id);
		init( id,attrList, catagory);

	}
	
	/**
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @param attrList
	 *            id
	 * @param catagory
	 *            id
	 */
	public void init(String id,List<Attribute> attrList,String catagory) {
		attributeTypeList = getUUMService().getAttributeTypeByResourceOnPage(null, 0, catagory);
		attributeList=attrList;
		
		RepeatingView repeatingView = new RepeatingView("trs");
		if(CollectionUtils.isNotEmpty(attributeTypeList)){
			attributePanels=new ArrayList<UserAttributePanel>();
			Iterator<AttributeType> iterator = attributeTypeList.iterator();
				while(iterator.hasNext()){
				
				AttributeType attributeType = (AttributeType)iterator.next();
				Attribute newAttribute = getAttributeByType(attributeType);
				
				WebMarkupContainer item = new WebMarkupContainer(repeatingView.newChildId());
				repeatingView.add(item);
				item.add(new Label("tdName1", Model.of(attributeType.getName()+"：")));
				UserAttributePanel panel1 = new UserAttributePanel("tdContent1",newAttribute);
				attributePanels.add(panel1);
				item.add(panel1);
				if(iterator.hasNext()){
					AttributeType attributeType2 = (AttributeType)iterator.next();
					Attribute newAttribute2 = getAttributeByType(attributeType2);
					item.add(new Label("tdName2", Model.of(attributeType2.getName()+"：")));
					UserAttributePanel panel2 = new UserAttributePanel("tdContent2",newAttribute2);
					attributePanels.add(panel2);
					item.add(panel2);
				}else{
					item.add(new Label("tdName2"));
					item.add(new Label("tdContent2"));
				} 
			}
		}
		
		add(repeatingView);

	}

	/**
	 * @param id
	 *            id
	 * @param user
	 *            user
	 * @param catagory
	 *            资源类别
	 */
	public ModifyInfoAttrPanel(String id, User user, String catagory) {
		super(id);
		boolean isNew = StringUtils.isBlank(user.getUuid());
		List<Attribute> attrList = null;		
		if (!isNew) {
			attrList = getUUMService().getAttributesUnderResourceOnPage(user, catagory);
		} else {
			attrList = new ArrayList<Attribute>();
		}
		init(id,attrList, catagory);
		/*userAppAccountPanel = new ModifyInfoAttrPanel("attr1", attrList,
				catagory);
		add(userAppAccountPanel);*/
		
	}

	/**
	 * 方法说明：得到attribute
	 * 
	 * @param type
	 *            扩展属性类型
	 * @return attribute
	 */
	public Attribute getAttributeByType(AttributeType type){
		Attribute attr=null;
		for(Attribute attribute :attributeList){
			if(attribute.getTypeUUID().equals(type.getUuid())){
				attr=attribute;
				break;
			}
		}
		if(attr==null){
			attr=new Attribute();			
			attr.setTypeUUID(type.getUuid());
		}
		attr.setType(type);
		return attr;
	}
}
