package com.wt.uum2.web.wicket.page.audit;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.Model;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Event;
import com.wt.uum2.web.wicket.page.UUMBasePage;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UserListType;
import com.wt.uum2.web.wicket.panel.datatable.UserlistToolbarSearch;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-6
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AuditPage extends UUMBasePage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3487538782400965847L;

	/**
	 * 
	 */
	private Form<Void> form;

	/**
	 * 
	 */
	private UserListType currentType;

	/**
	 * 
	 */
	private UserlistToolbarSearch searchbar;

	/**
	 * 
	 */
	private static List<Event> events = new ArrayList<Event>();

	/**
	 * 
	 */
	private ResourceListDataTable eventListDataTable;
	
	/**
	 * 
	 */
	private int pagesize;

	/**
	 * 
	 */
	private int page;

	/**
	 * 
	 */
	public AuditPage() {
		super();
		pagesize = 15;
		page = 1;
		currentType = UserListType.valueOfString("normal");
		initForm();
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void initForm() {
		form = new Form<Void>("form");
		add(form);

		UserPageResult<Event> eventlist = getTaskListService().getEventList(
				page, pagesize);
//		events = eventlist.getList();
//
//		final DataView<Event> list = new DataView<Event>("events",
//				new ListDataProvider<Event>(events), pagesize) {
//
//			/**
//			 * 
//			 */
//			private static final long serialVersionUID = -7208319431054147676L;
//
//			@Override
//			protected void populateItem(Item<Event> item) {
//				final Event event = item.getModelObject();
//				item.add(new Label("num", Integer.toString(item.getIndex()+1)));
//				item.add(new Label("status", Integer
//						.toString(event.getStatus())));
//				item.add(new Label("type", event.getType()));
//				item.add(new Label("resourceType", event.getResourceType()));
//				item.add(new Label("resourceId", event.getResourceId()));
//				item.add(new Label("resourceName", event.getResourceName()));
//				item.add(new Label("operatorName", event.getOperatorName()));
//				item.add(new Label("operatorIpAdderss", event
//						.getOperatorIpAdderss()));
//				item.add(new Label("date", event.getDate().toString()));
//
//				item.add(new ExternalLink("showmore", getRequest()
//						.getContextPath()
//						+ "/audit/syncLog.nsf?uuid=" + event.getUuid())
//						.setPopupSettings(new PopupSettings().setHeight(400)
		// .setWidth(800).setWindowName("事件日志")));
//
//			}
//		};
//
//		form.add(list);
		
		List columns=new ArrayList();
		
		// columns.add(new PropertyColumn<String>(Model.of("序号"), "num"));
		columns.add(new PropertyColumn<String>(Model.of("操作状态"), "status"));
		columns.add(new PropertyColumn<String>(Model.of("操作类型"), "type"));
		columns.add(new PropertyColumn<String>(Model.of("资源类型"), "resourceType"));
		columns.add(new PropertyColumn<String>(Model.of("资源ID"), "resourceId"));
		columns.add(new PropertyColumn<String>(Model.of("资源名称"), "resourceName"));
		columns.add(new PropertyColumn<String>(Model.of("操作者"), "operatorName"));
		columns.add(new PropertyColumn<String>(Model.of("操作者IP地址"), "operatorIpAdderss"));
		columns.add(new PropertyColumn<String>(Model.of("操作时间"), "date"));
		// columns.add(new LinkPropertyColumn(Model.of("查看详细"), "showmore"){
//
//			@Override
//			protected void onClick(IModel clicked) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//		});

		form.add(new UUMAjaxFallbackDefaultDataTable("events", columns, eventlist.getList(),eventlist.getPager(),null));
		
	}
	
}
