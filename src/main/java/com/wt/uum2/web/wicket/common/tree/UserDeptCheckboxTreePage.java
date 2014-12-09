package com.wt.uum2.web.wicket.common.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.behavior.SimpleAttributeModifier;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:选择用户部门树
 * 功能说明: 
 * 编写日期:	2014年4月8日
 * 作者:	lcy
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class UserDeptCheckboxTreePage extends WebPage
{
	/**
	 * 
	 */
	@SpringBean
	private UUMService uumService;

	/**
	 * 
	 */
	private TextField<String> selectedTextField;
	/**
	 * 
	 */
	private UUMCheckboxTreePanel<ResourceMutableTreeNode> panel;
	/**
	 * 
	 */
	private List<ResourceMutableTreeNode> expandNodes = new ArrayList<ResourceMutableTreeNode>();
	/**
	 * 
	 */
	private List<ResourceMutableTreeNode> selectNodes = new ArrayList<ResourceMutableTreeNode>();

	public UserDeptCheckboxTreePage(PageParameters parameters)
	{
		super(parameters);
		String type = parameters.get("type").toString("checkbox");
		boolean multiple = StringUtils.equalsIgnoreCase(type, "checkbox");

		String expand = parameters.get("expand").toString();
		String[] expands = StringUtils.split(expand, ',');

		if (expands != null && expands.length > 0) {

			for (int i = 0; i < expands.length; i++) {
				User u = uumService.getUserByUserId(expands[i]);
				if (u == null) {
					continue;
				}
				UserTreeNode ut = new UserTreeNode(u);
				if (!expandNodes.contains(ut)) {
					expandNodes.add(ut);
				}
			}
		}

		String selected = parameters.get("selected").toString();

		String[] selectedDeptUser = StringUtils.split(selected, ',');
		if (selectedDeptUser != null && selectedDeptUser.length > 0) {
			for (int i = 0; i < selectedDeptUser.length; i++) {
				String[] data = StringUtils.split(selectedDeptUser[i], "|");
				if (data != null && data.length > 0) {
					if (StringUtils.equalsIgnoreCase("d", data[0])) {
						Department d = uumService.getDepartmentByDeptCode(data[1]);
						if (d == null) {
							continue;
						}
						ResourceMutableTreeNode ut = new ResourceMutableTreeNode(d);
						ut.setUserObject(d.getName());
						if (!selectNodes.contains(ut)) {
							selectNodes.add(ut);
						}
					} else if (StringUtils.equalsIgnoreCase("u", data[0])) {
						User u = uumService.getUserByUserId(data[1]);
						if (u == null) {
							continue;
						}
						ResourceMutableTreeNode ut = new ResourceMutableTreeNode(u);
						ut.setUserObject(u.getName());
						if (!selectNodes.contains(ut)) {
							selectNodes.add(ut);
						}
					}
				}
			}
		}
		selectedTextField = new TextField<String>("selectedstr");
		add(selectedTextField.setMarkupId("selectedstr").setOutputMarkupId(true));
		panel = new UUMCheckboxTreePanel<ResourceMutableTreeNode>("panel", selectNodes, null,
				expandNodes, multiple, true) {

			@Override
			public Class getTClass()
			{
				return ResourceMutableTreeNode.class;
			}

		};
		add(panel);
		add(new AjaxLink<Void>("ok") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{

				submittree(panel.getList(), panel.getUserlistgroup(), target);
			}

		});
		add(new AjaxLink<Void>("cancel") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				submittree(panel.getOriginalList(), null, target);
			}
		});
	}

	private void submittree(List<ResourceMutableTreeNode> list, List<User> selectedUsers,
			AjaxRequestTarget target)
	{
		JSONArray array = new JSONArray();
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				Resource r = list.get(i).getResource();
				if (r instanceof User) {
					User u = (User) r;
					JSONObject jo = new JSONObject();
					jo.put("uuid", u.getUuid());
					jo.put("id", u.getId());
					jo.put("name", u.getName());
					jo.put("type", "u");
					array.add(jo);
				} else if (r instanceof Department) {
					Department d = (Department) r;
					JSONObject jo = new JSONObject();
					jo.put("uuid", d.getUuid());
					jo.put("id", d.getDeptCode());
					jo.put("name", d.getName());
					jo.put("type", "d");
					array.add(jo);
				}
				selectedTextField.add(new SimpleAttributeModifier("value", array.toString()));
			}

		}
		if (CollectionUtils.isNotEmpty(selectedUsers)) {
			for (Iterator<User> iterator = selectedUsers.iterator(); iterator.hasNext();) {
				User u = iterator.next();
				JSONObject jo = new JSONObject();
				jo.put("uuid", u.getUuid());
				jo.put("id", u.getId());
				jo.put("name", u.getName());
				jo.put("type", "u");
				array.add(jo);
			}
		}
		if (array.size() == 0) {
			selectedTextField.add(new SimpleAttributeModifier("value", ""));
		}
		target.add(selectedTextField);
		target.appendJavaScript("a();");
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference("style/hirisun/style.css");
		response.renderCSSReference("style/hirisun/tree.css");
	}
}
