package com.wt.uum2.web.wicket.panel.datatable;

import nak.nsf.pager.Pager;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.IClusterable;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractToolbar;
import org.apache.wicket.extensions.markup.html.repeater.data.table.DataTable;
import org.apache.wicket.markup.html.WebMarkupContainer;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-11-3
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UUMAjaxNavigationToolbar extends AbstractToolbar
{

	
	/**
	 * <pre>
	 * 业务名:
	 * 功能说明: 
	 * 编写日期:	2011-11-3
	 * 作者:	Administrator
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	public interface GotoPageCallback extends IClusterable {

		/**
		 * 方法说明：
		 *
		 * @param target target
		 * @param pager pager
		 */
		void gotoPage(AjaxRequestTarget target,Pager pager);

	}
	
	/**
	 * 
	 */
	private GotoPageCallback callback;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private Pager pager;
	/**
	 * @param table table
	 * @param colspan colspan
	 * @param pager pager
	 * @param callback callback
	 */
	public UUMAjaxNavigationToolbar(final DataTable<?> table,int colspan,Pager pager,GotoPageCallback callback)
	{
		super(table);
		this.callback=callback;
		this.pager=pager;
		WebMarkupContainer span = new WebMarkupContainer("span");
		add(span);
		span.add(AttributeModifier.replace("colspan", String.valueOf(colspan)));

		span.add(newPagingNavigator("navigator", pager));
		span.add(newNavigatorLabel("navigatorLabel", pager));
	}
	
	/**
	 * 方法说明：
	 *
	 * @param navigatorId navigatorId
	 * @param pager pager
	 * @return Component
	 */
	protected Component newPagingNavigator(final String navigatorId, final Pager pager)
	{
		return new UUMAjaxPagingNavigator(navigatorId, pager){

			@Override
			public void gotoPage(AjaxRequestTarget target, Pager pager)
			{
				callback.gotoPage(target, pager);				
			}

			
			
		};
		
	}
	/**
	 * 方法说明：
	 *
	 * @param navigatorId navigatorId
	 * @param pager pager
	 * @return Component
	 */
	protected Component newNavigatorLabel(final String navigatorId, final Pager pager)
	{
		return new UUMAjaxPagingNavigatorLeft(navigatorId, pager){

			@Override
			public void gotoPage(AjaxRequestTarget target, Pager pager)
			{
				callback.gotoPage(target, pager);
				
			}};
		
	}
	@Override
	protected void onConfigure()
	{
		super.onConfigure();
		setVisible(pager.getQuantityOfData()>0);
	}

}
