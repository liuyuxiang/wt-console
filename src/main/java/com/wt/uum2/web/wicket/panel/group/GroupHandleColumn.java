package com.wt.uum2.web.wicket.panel.group;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;

import com.wt.uum2.domain.GroupList;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class GroupHandleColumn extends AbstractColumn<GroupList>
{

	/**
	 * @param displayModel
	 *            displayModel
	 */
	public GroupHandleColumn(IModel<String> displayModel)
	{
		super(displayModel);
	}

	/**
	 * @param displayModel
	 *            displayModel
	 * @param sortProperty
	 *            sortProperty
	 */
	public GroupHandleColumn(IModel<String> displayModel, String sortProperty)
	{
		super(displayModel, sortProperty);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -7080228454148807874L;

	/* (non-Javadoc)
	 * @see org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator#populateItem(org.apache.wicket.markup.repeater.Item, java.lang.String, org.apache.wicket.model.IModel)
	 */
	/**
	 * 方法说明：populateItem
	 * 
	 * @param cellItem
	 *            cellItem
	 * @param componentId
	 *            componentId
	 * @param rowModel
	 *            rowModel
	 */
	public void populateItem(Item<ICellPopulator<GroupList>> cellItem, String componentId,
			IModel<GroupList> rowModel)
	{
		cellItem.add(new ButtonPanel(componentId, rowModel));
	}

	/**
	 * <pre>
	 * 业务名:
	 * 功能说明: 
	 * 编写日期:	2012-2-24
	 * 作者:	Administrator
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	private class ButtonPanel extends Panel
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -7430430315418095176L;

		/**
		 * @param id
		 *            id
		 * @param model
		 *            model
		 */
		public ButtonPanel(String id, final IModel<GroupList> model)
		{
			super(id, model);
			AjaxLink<GroupList> editButton = new AjaxLink<GroupList>("editButton", model) {

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					GroupHandleColumn.this.onClickEditButton(model, target);
				}

			};
			add(editButton.setVisible(model.getObject().isGroup()));
			AjaxLink<GroupList> deleteButton = new AjaxLink<GroupList>("deleteButton", model) {

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					GroupHandleColumn.this.onClickDeleteButton(model, target);
				}

			};
			add(deleteButton.setVisible(model.getObject().isGroup()));

			AjaxLink<GroupList> moveButton = new AjaxLink<GroupList>("moveButton", model) {

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					GroupHandleColumn.this.onClickMoveButton(model, target);
				}

			};
			add(moveButton);
		}

	}

	/**
	 * 方法说明：onClickEditButton
	 * 
	 * @param model
	 *            model
	 * @param target
	 *            target
	 */
	protected abstract void onClickEditButton(IModel<GroupList> model, AjaxRequestTarget target);

	/**
	 * 方法说明：onClickDeleteButton
	 * 
	 * @param model
	 *            model
	 * @param target
	 *            target
	 */
	protected abstract void onClickDeleteButton(IModel<GroupList> model, AjaxRequestTarget target);

	/**
	 * 方法说明：onClickMoveButton
	 * 
	 * @param model
	 *            model
	 * @param target
	 *            target
	 */
	protected abstract void onClickMoveButton(IModel<GroupList> model, AjaxRequestTarget target);
}
