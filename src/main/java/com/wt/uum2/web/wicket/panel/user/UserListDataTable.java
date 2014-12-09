package com.wt.uum2.web.wicket.panel.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import nak.nsf.pager.Pager;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

import com.hirisun.components.web.wicket.table.AjaxLinkPropertyColumn;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.User;
import com.wt.uum2.userlist.UserCol;
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
public class UserListDataTable extends BaseUUMPanel
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
	/**
	 * 
	 */
	private Pager pager;

	public List getBaseColumns()
	{
		return baseColumns;
	}

	/**
	 * 
	 */
	private boolean isLinkEnable;
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

	public Pager getPager()
	{
		return pager;
	}

	/**
	 * @param id
	 *            id
	 */
	public UserListDataTable(String id)
	{
		super(id);
	}

	/**
	 * @param id
	 *            id
	 * @param userPageResult
	 *            userPageResult
	 * @param callback1
	 *            callback1
	 * @param isLinkEnable
	 *            isLinkEnable
	 */
	public UserListDataTable(String id, UserPageResult<User> userPageResult,
			GotoPageCallback callback1, boolean isLinkEnable)
	{
		super(id);

		columns = new ArrayList();

		this.callback = callback1;
		this.pager = userPageResult.getPager();
		this.isLinkEnable = isLinkEnable;
		createBaseColumns(columns);
		createOtherColumns(columns);
		createDataTable(columns, userPageResult, callback);

	}

	/**
	 * @param id
	 *            id
	 * @param userPageResult
	 *            userPageResult
	 * @param callback1
	 *            callback1
	 */
	public UserListDataTable(String id, UserPageResult<User> userPageResult,
			GotoPageCallback callback1)
	{
		this(id, userPageResult, callback1, true);

	}

	/**
	 * 方法说明：createBaseColumns
	 * 
	 * @param columns
	 *            columns
	 */
	public void createBaseColumns(List columns)
	{
		if (CollectionUtils.isEmpty(baseColumns)) {
			baseColumns = new ArrayList();
		}
		List<String> headers = getUserListService().getHeaders();
		List<UserCol> cols = getUserListService().getCols();

		for (int i = 0; i < headers.size(); i++) {
			switch (cols.get(i)) {
			case userName:
				if (isLinkEnable) {
					baseColumns.add(new AjaxLinkPropertyColumn(Model.of(headers.get(i)), cols
							.get(i).toString()) {

						@Override
						protected void onClick(IModel clicked, AjaxRequestTarget target)
						{
							final Map<String, Object> map = (Map<String, Object>) clicked
									.getObject();
							UserListDataTable.this.onClickLink((User) map.get("userObject"), target);

						}
					});
				} else {
					baseColumns.add(new PropertyColumn<String>(Model.of(headers.get(i)), cols
							.get(i).toString()));
				}
				break;
			case userDept:
				baseColumns.add(new PropertyColumn<String>(Model.of(headers.get(i)), cols.get(i)
						.toString()) {
					@Override
					public String getCssClass()
					{
						return "headerwidth";
					}
				});
				break;
			default:
				baseColumns.add(new PropertyColumn<String>(Model.of(headers.get(i)), cols.get(i)
						.toString()));
				break;
			}

		}

		columns.addAll(baseColumns);

	}

	/**
	 * 方法说明：
	 * 
	 * @param columns
	 *            columns
	 */
	public void createOtherColumns(List columns)
	{
	}

	/**
	 * 方法说明：
	 * 
	 * @param columns
	 *            columns
	 * @param userPageResult
	 *            columns
	 * @param callback
	 *            columns
	 */
	public void createDataTable(final List columns, UserPageResult<User> userPageResult,
			GotoPageCallback callback)
	{

		Map<String, Map<String, Object>> maps = getUserListService().getDatas(
				userPageResult.getList(), userPageResult.getPager().getDataStart());
		List<Map<String, Object>> list = transitionList(maps);
		datatable = new UUMAjaxFallbackDefaultDataTable("userlisttable", columns, list,
				userPageResult.getPager(), callback);

		add(datatable);
	}

	/**
	 * 方法说明：
	 * 
	 * @param maps
	 *            maps
	 * @return List
	 */
	public List<Map<String, Object>> transitionList(Map<String, Map<String, Object>> maps)
	{
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Entry<String, Map<String, Object>> entry : maps.entrySet()) {
			list.add(entry.getValue());
		}
		return list;
	}

	/**
	 * 方法说明：onClickLink
	 * 
	 * @param user
	 *            user
	 * @param target
	 *            target
	 */
	protected void onClickLink(User user, AjaxRequestTarget target)
	{

	};
}
