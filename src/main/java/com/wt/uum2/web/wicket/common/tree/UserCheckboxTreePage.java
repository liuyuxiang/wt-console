package com.wt.uum2.web.wicket.common.tree;

import java.util.ArrayList;
import java.util.List;

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
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:选择用户树
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
public class UserCheckboxTreePage extends WebPage
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
	private UUMCheckboxTreePanel<UserTreeNode> panel;
	/**
	 * 
	 */
	private List<UserTreeNode> expandNodes = new ArrayList<UserTreeNode>();
	/**
	 * 
	 */
	private List<UserTreeNode> selectNodes = new ArrayList<UserTreeNode>();

	public UserCheckboxTreePage(PageParameters parameters)
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
					} else if (StringUtils.equalsIgnoreCase("u", data[0])) {
						User u = uumService.getUserByUserId(data[1]);
						if (u == null) {
							continue;
						}
						UserTreeNode ut = new UserTreeNode(u);
						if (!selectNodes.contains(ut)) {
							selectNodes.add(ut);
						}
					}
				}
			}
		}
		selectedTextField = new TextField<String>("selectedstr");
		add(selectedTextField.setMarkupId("selectedstr").setOutputMarkupId(true));
		panel = new UUMCheckboxTreePanel<UserTreeNode>("panel", selectNodes, null, expandNodes,
				multiple, true) {

			@Override
			public Class getTClass()
			{
				return UserTreeNode.class;
			}

		};
		add(panel);
		add(new AjaxLink<Void>("ok") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				submittree(panel.getList(), null, target);
			}

		});
		add(new AjaxLink<Void>("cancel") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				submittree(panel.getOriginalList(), panel.getUserlistgroup(), target);
			}
		});

	}

	private void submittree(List<UserTreeNode> list, List<User> selectedUsers,
			AjaxRequestTarget target)
	{
		StringBuilder str = new StringBuilder();
		List<User> users = new ArrayList<User>();
		if (CollectionUtils.isNotEmpty(list)) {
			for (int i = 0; i < list.size(); i++) {
				User u = list.get(i).getResource();
				users.add(u);
			}

		}
		List<User> all = (List<User>) CollectionUtils.union(users, selectedUsers);
		if (CollectionUtils.isNotEmpty(all)) {
			for (int i = 0; i < all.size(); i++) {
				User u = all.get(i);
				str.append(u.getUuid()).append(i == all.size() - 1 ? "" : ",");
			}
			selectedTextField.add(new SimpleAttributeModifier("value", str.toString()));
		} else {
			selectedTextField.add(new SimpleAttributeModifier("value", ""));
		}
		target.add(selectedTextField);
		target.appendJavaScript("a();");
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		response.renderCSSReference("style/hirisun/style.css");
		response.renderCSSReference("style/hirisun/wstyle.css");
	}
}
