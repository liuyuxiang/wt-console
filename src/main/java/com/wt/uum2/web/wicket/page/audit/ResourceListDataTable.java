package com.wt.uum2.web.wicket.page.audit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.table.AjaxLinkPropertyColumn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.User;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;

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
public class ResourceListDataTable extends BaseUUMPanel
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2720591094450356736L;
	
	/**
	 * 
	 */
	private UUMAjaxFallbackDefaultDataTable datatable;
	/**
	 * 
	 */
	private List baseColumns;
	public List getBaseColumns()
	{
		return baseColumns;
	}

	/**
	 * 
	 */
	private List columns;
	/**
	 * 
	 */
	private GotoPageCallback callback;
	
	
	public GotoPageCallback getCallback()
	{
		return callback;
	}
	public List getColumns()
	{
		return columns;
	}

	/**
	 * @param id
	 *            id
	 */
	public ResourceListDataTable(String id){
		super(id);
	}
	
	/**
	 * @param id
	 *            id
	 * @param userPageResult
	 *            id
	 * @param callback1
	 *            id
	 */
	public ResourceListDataTable(String id,UserPageResult userPageResult,GotoPageCallback callback1)
	{
		super(id);
	
		columns=new ArrayList();
		
		this.callback=callback1;
		createBaseColumns();
		createOtherColumns(columns);
		createDataTable(columns,userPageResult,callback);
		
			
	}

	/**
	 * 方法说明：
	 * 
	 */
	public void createBaseColumns(){
		if(CollectionUtils.isEmpty(baseColumns)){
			baseColumns=new ArrayList();
		}
		Map<String,String> columnsMap=getUserListService().getColsAndHeaders();
		for(Entry<String,String> entry:columnsMap.entrySet()){
			if("userID".equals(entry.getKey())){
				
				baseColumns.add(new AjaxLinkPropertyColumn(Model.of(entry.getValue()), entry
						.getKey()) {

					@Override
					protected void onClick(IModel clicked, AjaxRequestTarget target)
					{
//						Map<String,Object> map=(Map<String,Object>)clicked.getObject();
//						User user=(User)map.get("userObject");
//						setResponsePage(CreateUserPage.class);
//						setResponsePage(new CreateUserPage(user,null));
						
					}
					
				});
			}else{
				baseColumns.add(new PropertyColumn<String>(Model.of(entry.getValue()), entry.getKey()));
			}
		}
		columns.addAll(baseColumns);
		
	}

	/**
	 * 方法说明：
	 * 
	 * @param callback
	 *            callback
	 */
	public void createOtherColumns(List callback)
	{
	}

	/**
	 * 方法说明：
	 * 
	 * @param columns
	 *            columns
	 * @param userPageResult
	 *            userPageResult
	 * @param callback
	 *            callback
	 */
	public void createDataTable(final List columns,UserPageResult<User> userPageResult,GotoPageCallback callback){
		
		Map<String, Map<String, Object>> maps=getUserListService().getDatas(userPageResult.getList(),userPageResult.getPager().getDataStart());
		List<Map<String, Object>> list = transitionList(maps);
		datatable=new UUMAjaxFallbackDefaultDataTable("userlisttable", columns, list,userPageResult.getPager(),callback);
		
		add(datatable);
	}

	/**
	 * 方法说明：
	 * 
	 * @param maps
	 *            maps
	 * @return List
	 */
	public List<Map<String, Object>> transitionList(Map<String, Map<String, Object>> maps){
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		for(Entry<String, Map<String, Object>> entry:maps.entrySet()){
			list.add(entry.getValue());
		}
		return list;
	}
	

}
