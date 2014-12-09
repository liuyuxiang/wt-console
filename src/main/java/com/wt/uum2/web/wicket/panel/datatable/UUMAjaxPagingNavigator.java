package com.wt.uum2.web.wicket.panel.datatable;

import nak.nsf.pager.Pager;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.PropertyModel;

import com.wt.uum2.web.wicket.panel.BaseUUMPanel;

/**
 * <pre>
 * 业务名:UUM分页
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
public abstract class UUMAjaxPagingNavigator extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7461965555633613457L;

	
	/**
	 * @param id 
	 * @param pager pager
	 */
	public UUMAjaxPagingNavigator(String id, final Pager pager)
	{
		super(id);
		add(new Label("currentPage", new PropertyModel<Integer>(pager, "currentPage")));// Model.of(pageable.getCurrentPage())
		add(new Label("totalPage", new PropertyModel<Integer>(pager, "lastPage")));// Model.of(pageable.getPageCount()))
		AjaxLink firstLink=new AjaxLink("first") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pager.setCurrentPage(pager.getFirstPage());
				pager.compute();
				gotoPage(target,pager);
			}
		};
		add(firstLink);
		if(pager.getCurrentPage()==pager.getFirstPage()){
			firstLink.setEnabled(false);
		}
		AjaxLink prevLink=new AjaxLink("prev") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pager.setCurrentPage(pager.getPreviousPage());
				pager.compute();
				gotoPage(target,pager);
			}
		};
		
		add(prevLink);
		if(!pager.isHasPreviousPage()){
			prevLink.setEnabled(false);
		}
		
		AjaxLink nextLink=new AjaxLink("next") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pager.setCurrentPage(pager.getNextPage());
				pager.compute();
				gotoPage(target,pager);
			}
		};
		add(nextLink);
		if(!pager.isHasNextPage()){
			nextLink.setEnabled(false);
		}
		AjaxLink lastLink=new AjaxLink("last") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				pager.setCurrentPage(pager.getLastPage());
				pager.compute();
				gotoPage(target,pager);
			}
		};
		add(lastLink);
		if(pager.getCurrentPage()==pager.getLastPage()){
			lastLink.setEnabled(false);
		}
		/*add(newPagingNavigationLink("first", pager, 0));
		add(newPagingNavigationIncrementLink("prev", pager, -1));
		add(newPagingNavigationIncrementLink("next", pager, 1));
		add(newPagingNavigationLink("last", pageable, -1));*/
	}

	/**
	 * 方法说明：跳页方法
	 * 
	 * @param target
	 *            target
	 * @param pager
	 *            pager
	 */
	public abstract void gotoPage(AjaxRequestTarget target ,Pager pager);
	
	
}
