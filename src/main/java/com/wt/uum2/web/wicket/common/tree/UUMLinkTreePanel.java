package com.wt.uum2.web.wicket.common.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.markup.html.tree.LinkTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.hirisun.components.web.wicket.table.AjaxFallbackSortDataTable;
import com.hirisun.components.web.wicket.table.CheckBoxPropertyColumn;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.web.wicket.panel.UUMSortableListDataProvider;

/**
 * <pre>
 * 业务名:用户部门树panel
 * 功能说明: 
 * 编写日期:	2012-4-20
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 */
public class UUMLinkTreePanel extends ChangeListPanel<UserTreeNode>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 665265053533593035L;
	/**
	 * 
	 */
	private LinkTree tree;
	/**
	 * 
	 */
	private DefaultTreeModel model;
	/**
	 * 
	 */
	private Department rootDepartment;
	/**
	 * 
	 */
	private DepartmentTreeNode rootNode;
	/**
	 * 
	 */
	private boolean treemultiple = false;
	/**
	 * 
	 */
	private List<DepartmentTreeNode> deptpaths;
	/**
	 * 
	 */
	private List<UserTreeNode> unSelectedNodes;
	/**
	 * 
	 */
	private List<UserTreeNode> searchList;

	private List<UserTreeNode> expandNodes;
	private Label selectedValue2;
	private List<User> userlistgroup = new ArrayList<User>();
	private AjaxFallbackSortDataTable<User> searchcontent;
	/**
	 * 
	 */
	@SpringBean
	private UUMService uumService;

	public UUMLinkTreePanel(String id, List<UserTreeNode> list, List<UserTreeNode> unSelectedNodes,
			List<UserTreeNode> expandNodes, final boolean multiple, boolean visibleSearch)
	{
		super(id, list);
		for (Iterator<UserTreeNode> iterator = list.iterator(); iterator.hasNext();) {
			User u = iterator.next().getResource();
			userlistgroup.add(u);
		}
		this.treemultiple = multiple;
		this.expandNodes = expandNodes;
		// 设置根节点
		rootDepartment = getRootDepartment() == null ? uumService.getDepartmentRoot()
				: getRootDepartment();

		this.unSelectedNodes = unSelectedNodes;
		List<User> provider = new ArrayList<User>();
		if (CollectionUtils.isNotEmpty(expandNodes)) {
			for (UserTreeNode userTreeNode : expandNodes) {
				Department d = uumService.getUserPrimaryDepartment(userTreeNode.getResource());
				List<User> users = uumService.getUsersUnderDepartment(d);
				provider.addAll(users);
			}
		} else {
			for (UserTreeNode userTreeNode : list) {
				provider.add(userTreeNode.getResource());

			}
		}

		// List<UserTreeNode> all = (List<UserTreeNode>) CollectionUtils.union(list, expandNodes);
		initDepartmentPath(expandNodes);// 初使化部门和查询他的父部门
		initTree();

		add(new SearchUserPanel("searchpanel").setVisible(visibleSearch));
		StringBuilder str = new StringBuilder();
		for (User u : userlistgroup) {
			str.append(u.getName()).append(",");
		}
		add(selectedValue2 = new Label("selectedValue2", str.toString()));
		selectedValue2.setOutputMarkupId(true);

		add(searchcontent = new AjaxFallbackSortDataTable<User>("searchcontent", getColumns(),
				new UUMSortableListDataProvider<User>(provider), 10));
		searchcontent.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
	}

	public Department getRootDepartment()
	{
		return null;
	}

	private List<IColumn<User>> getColumns()
	{
		List<IColumn<User>> columns = new ArrayList<IColumn<User>>();
		columns.add(new CheckBoxPropertyColumn<User>(userlistgroup) {
			@Override
			protected void onUpdateHearder(AjaxRequestTarget target)
			{
				super.onUpdateHearder(target);
				StringBuilder str = new StringBuilder();
				for (User u : userlistgroup) {
					str.append(u.getName()).append(",");
				}
				selectedValue2.setDefaultModelObject(str.toString());
				target.add(selectedValue2);
			}

			@Override
			protected void onUpdateCheckBoxItem(AjaxRequestTarget target, AjaxCheckBox checkbox,
					IModel<User> rowModel)
			{
				super.onUpdateCheckBoxItem(target, checkbox, rowModel);
				StringBuilder str = new StringBuilder();
				for (User u : userlistgroup) {
					str.append(u.getName()).append(",");
				}
				selectedValue2.setDefaultModelObject(str.toString());
				target.add(selectedValue2);
			}

		});
		columns.add(new PropertyColumn<User>(new Model<String>("姓名"), "name"));
		columns.add(new PropertyColumn<User>(new Model<String>("部门"), "primaryDepartment.path") {
			@Override
			public String getCssClass()
			{
				return "headerwidth";
			}
		});
		return columns;
	}

	private void initDepartmentPath(List<UserTreeNode> selectedlist)
	{
		deptpaths = new ArrayList<DepartmentTreeNode>();
		if (CollectionUtils.isNotEmpty(selectedlist)) {
			for (UserTreeNode t : selectedlist) {

				if (t.getResource() instanceof User) {
					User u = (User) t.getResource();

					List<Department> deptList = uumService.getUserDepartments(u);
					for (Iterator<Department> iterator = deptList.iterator(); iterator.hasNext();) {
						Department department = (Department) iterator.next();
						DepartmentTreeNode deptnode = new DepartmentTreeNode(department);
						deptpaths.add(deptnode);
						deptpaths.addAll(getParentsByDepartment(rootDepartment, department));
					}
				}
			}
		}
	}

	private void initTree()
	{
		tree = new LinkTree("tree", createTreeModel()) {

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node)
			{
				super.onJunctionLinkClicked(target, node);
				addNodes((DefaultMutableTreeNode) node, false, target);
			}

			@Override
			protected void onNodeLinkClicked(Object node, BaseTree tree, AjaxRequestTarget target)
			{
				super.onNodeLinkClicked(node, tree, target);
				Department d = ((DepartmentTreeNode) node).getResource();
				List<User> searchUserList = uumService.getUsersUnderDepartment(d);
				AjaxFallbackSortDataTable<User> newtable = new AjaxFallbackSortDataTable<User>(
						"searchcontent", getColumns(), new UUMSortableListDataProvider<User>(
								searchUserList), 10);
				searchcontent.replaceWith(newtable);
				searchcontent = newtable;
				target.add(searchcontent);

			}

		};

		add(tree);
		tree.getTreeState().setAllowSelectMultiple(getTreemultiple());// 多选or单选

		addNodes(rootNode, false, null);
		// 如果没有根节点没有子则显示，如果根节点有子节点则直接显示子节点不显示根
		if (rootNode.getChildCount() != 0) {
			tree.setRootLess(true);
		}
	}

	private List<DepartmentTreeNode> getParentsByDepartment(Department rootDepartment,
			Department department)
	{
		List<DepartmentTreeNode> deptpaths = new ArrayList<DepartmentTreeNode>();
		while (!StringUtils.equals(rootDepartment.getUuid(), department.getUuid())) {

			Department parent = uumService.getParentDepartment(department);
			DepartmentTreeNode deptnode1 = new DepartmentTreeNode(parent);
			deptpaths.add(deptnode1);
			department = parent;
		}
		return deptpaths;
	}

	protected TreeModel createTreeModel()
	{
		rootNode = new DepartmentTreeNode(rootDepartment);
		model = new DefaultTreeModel(rootNode);
		return model;
	}

	/**
	 * 方法说明：添加部门节点
	 * 
	 * @param parent
	 * @param target
	 */
	private void addNodes(DefaultMutableTreeNode parent, boolean isSearch, AjaxRequestTarget target)
	{
		if (parent.getChildCount() == 0) {
			DepartmentTreeNode current = (DepartmentTreeNode) parent;

			List<Department> depts = uumService.getDepartmentChildren(current.getResource()
					.getUuid());

			for (Department d : depts) {
				DepartmentTreeNode subNode = new DepartmentTreeNode(d);
				current.add(subNode);
				if (CollectionUtils.isNotEmpty(expandNodes)) {
					for (Iterator<UserTreeNode> iterator = expandNodes.iterator(); iterator
							.hasNext();) {
						User u = iterator.next().getResource();
						if (u.getPrimaryDepartmentUUID().equals(d.getUuid())) {
							tree.getTreeState().selectNode(subNode, true);
						}
					}
				}

				if (CollectionUtils.isNotEmpty(deptpaths) && deptpaths.contains(subNode)) {
					addNodes(subNode, isSearch, target);
					tree.getTreeState().expandNode(subNode);
				}
			}
			if (isSearch) {
				addSearchLeaf(current, target);
			}

			if (target != null) {
				tree.updateTree(target);
			}
		}
	}

	/**
	 * 方法说明：添加叶子（用户）节点
	 * 
	 * @param parent
	 * @param target
	 */
	private void addLeaf(DepartmentTreeNode parent, AjaxRequestTarget target)
	{

		List<User> userlist = uumService.getUsersUnderDepartment(parent.getResource());
		if (CollectionUtils.isNotEmpty(userlist)) {
			for (User user : userlist) {
				UserTreeNode userSubNode = new UserTreeNode(user);
				userSubNode.setAllowsChildren(false);
				parent.add(userSubNode);

				if (getList().contains(userSubNode)) {
					tree.getTreeState().selectNode(userSubNode, true);
				}
			}
		}
	}

	private void addSearchLeaf(DepartmentTreeNode parent, AjaxRequestTarget target)
	{
		List<User> userlist = uumService.getUsersUnderDepartment(parent.getResource());
		if (CollectionUtils.isNotEmpty(userlist)) {
			for (User user : userlist) {
				UserTreeNode userSubNode = new UserTreeNode(user);

				if (searchList.contains(userSubNode)) {
					userSubNode.setAllowsChildren(false);
					parent.add(userSubNode);
					if (getList().contains(userSubNode)) {
						tree.getTreeState().selectNode(userSubNode, true);
					}
				}
			}
		}
	}

	/**
	 * <pre>
	 * 业务名:搜索条
	 * 功能说明: 
	 * 编写日期:	2012-4-20
	 * 作者:	Administrator
	 * 
	 * 历史记录
	 * 1、修改日期：
	 *    修改人：
	 *    修改内容：
	 * </pre>
	 */
	private class SearchUserPanel extends Panel
	{
		/**
		 * 
		 */
		private static final long serialVersionUID = -6003650615085300965L;

		public SearchUserPanel(String id)
		{
			super(id);

			Form<Void> form = new Form<Void>("form2");
			add(form);
			final TextField<String> TEXT = new TextField<String>("text", new Model<String>()) {

				@Override
				protected void onComponentTag(ComponentTag tag)
				{
					super.onComponentTag(tag);
					tag.put("onkeypress", "if (event.keyCode == 13) {return false;}");
				}

			};
			form.add(TEXT.setRequired(true).setOutputMarkupId(true));
			final SimpleFeedbackPanel feedback = new SimpleFeedbackPanel("feedback");
			form.add(feedback.setOutputMarkupId(true));
			final AjaxWaitingButton SEARCH = new AjaxWaitingButton("search") {

				protected void onError(AjaxRequestTarget target, Form<?> form)
				{
					target.add(feedback);
				}

				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{

					// searchcontent
					AjaxFallbackSortDataTable<User> newtable = new AjaxFallbackSortDataTable<User>(
							"searchcontent", getColumns(), new UserSortableDataProvider(
									TEXT.getInput()), 10);
					searchcontent.replaceWith(newtable);
					searchcontent = newtable;
					target.add(searchcontent);
					target.add(feedback);

				}
			};
			form.add(SEARCH);
			/*final IndicatingAjaxLink SHOWTREE = new IndicatingAjaxLink("showtree") {

				@Override
				public void onClick(AjaxRequestTarget target)
				{
					TEXT.clearInput();
					TEXT.setModelObject("");
					target.add(TEXT);
					tree.setModelObject(createTreeModel());// 返回普通树
					List<T> all = (List<T>) CollectionUtils.union(getList(), expandNodes);
					initDepartmentPath(all);// 重新取已选的path
					addNodes(rootNode, false, target);
					tree.updateTree(target);
				}

			};
			form.add(SHOWTREE);*/

		}
	}

	private class UserSortableDataProvider<User> extends SortableDataProvider
	{
		private Condition condition;
		private int size = 0;

		public UserSortableDataProvider(String username)
		{
			Condition condition = new Condition();
			condition.nameLike(username);
			this.condition = condition;
			size = uumService.searchUsersByCondition(condition).size();

		}

		public Iterator iterator(int first, int count)
		{
			return uumService.searchUsersByCondition((first / count) + 1, count, condition)
					.getList().iterator();
		}

		public int size()
		{
			return size;
		}

		public IModel model(final Object object)
		{
			return new LoadableDetachableModel<User>() {

				@Override
				protected User load()
				{
					return (User) object;
				}
			};
		}

	}

	public LinkTree getTree()
	{
		return tree;
	}

	public boolean getTreemultiple()
	{
		return this.treemultiple;
	}

	public void onSubmit(AjaxRequestTarget target, List<UserTreeNode> selectedList,
			List<UserTreeNode> removeList)
	{
	}

	public void visibleCheckboxIconPanel(IModel<Object> model, CheckBox checkbox)
	{

	}

	public List<User> getUserlistgroup()
	{
		return userlistgroup;
	}

	/*private Class getTClass()
	{
		ParameterizedType p = ((ParameterizedType) UserTreePanel.this.getClass()
				.getGenericSuperclass());
		return (Class) p.getActualTypeArguments()[0];

	}*/

}
