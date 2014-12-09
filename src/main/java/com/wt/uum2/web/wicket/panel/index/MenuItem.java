package com.wt.uum2.web.wicket.panel.index;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxEventBehavior;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.image.NonCachingImage;
import org.apache.wicket.request.resource.IResource;

import com.wt.uum2.constants.MenuItemType;

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
public class MenuItem extends NonCachingImage
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private String imageUrl;
	/**
	 * 
	 */
	private String selectedImageUrl;
	/**
	 * 
	 */
	private MenuItemType menuItemType;

	/**
	 * @param id
	 *            id
	 * @param type
	 *            type
	 * @param imageResource
	 *            imageResource
	 * @param imageUrl
	 *            imageUrl
	 * @param selectedImageUrl
	 *            selectedImageUrl
	 */
	public MenuItem(String id, MenuItemType type, IResource imageResource, String imageUrl,
			String selectedImageUrl)
	{
		super(id, imageResource);
		setImageUrl(imageUrl);
		setSelectedImageUrl(selectedImageUrl);
		setMenuItemType(type);
		add(AttributeModifier.append("style", "cursor:pointer;"));
		add(new AjaxEventBehavior("onclick") {

			@Override
			protected void onEvent(AjaxRequestTarget target)
			{
				MenuItem.this.onClick(target);
			}

		});
	}

	/**
	 * 方法说明：
	 * 
	 * @param target
	 *            target
	 */
	public void onClick(AjaxRequestTarget target)
	{

	}
	public String getImageUrl()
	{
		return imageUrl;
	}

	public void setImageUrl(String imageUrl)
	{
		this.imageUrl = imageUrl;
	}

	public String getSelectedImageUrl()
	{
		return selectedImageUrl;
	}

	public void setSelectedImageUrl(String selectedImageUrl)
	{
		this.selectedImageUrl = selectedImageUrl;
	}

	public MenuItemType getMenuItemType()
	{
		return menuItemType;
	}

	public void setMenuItemType(MenuItemType menuItemType)
	{
		this.menuItemType = menuItemType;
	}




}
