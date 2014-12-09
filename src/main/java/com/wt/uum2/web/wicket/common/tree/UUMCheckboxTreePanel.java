package com.wt.uum2.web.wicket.common.tree;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreeNode;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.visit.IVisit;
import org.apache.wicket.util.visit.IVisitor;

import com.hirisun.components.web.wicket.button.AjaxWaitingButton;
import com.hirisun.components.web.wicket.table.AjaxFallbackSortDataTable;
import com.hirisun.components.web.wicket.table.CheckBoxPropertyColumn;
import com.hirisun.components.web.wicket.tree.CheckBoxTree;
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
public abstract class UUMCheckboxTreePanel<T extends ResourceMutableTreeNode> extends
		ChangeListPanel<T>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 665265053533593035L;
	/**
	 * 
	 */
	private CheckBoxTree tree;
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
	private List<T> unSelectedNodes;
	/**
	 * 
	 */
	private List<T> searchList;

	private List<T> expandNodes;
	private Label selectedValue2;
	private Label selectedValue1;
	private List<User> userlistgroup = new ArrayList<User>();
	private AjaxFallbackSortDataTable<User> searchcontent;
	/**
	 * 
	 */
	@SpringBean
	private UUMService uumService;

	public UUMCheckboxTreePanel(String id, List<T> list, List<T> unSelectedNodes,
			List<T> expandNodes, final boolean multiple, boolean visibleSearch)
	{
		super(id, list);
		this.treemultiple = multiple;
		this.expandNodes = expandNodes;
		rootDepartment = uumService.getDepartmentRoot();
		this.unSelectedNodes = unSelectedNodes;
		List<T> all = (List<T>) CollectionUtils.union(list, expandNodes);
		initDepartmentPath(all);// 初使化部门和查询他的父部门
		initTree();

		if (getTClass() == DepartmentTreeNode.class) {// 如果当前只显示部门节点，则不显示查询用户
			visibleSearch = false;
		}

		add(new SearchUserPanel("searchpanel").setVisible(visibleSearch));
		add(selectedValue2 = new Label("selectedValue2", ""));
		selectedValue2.setOutputMarkupId(true);
		add(selectedValue1 = new Label("selectedValue1", ""));
		selectedValue1.setOutputMarkupId(true);
		add(searchcontent = new AjaxFallbackSortDataTable<User>("searchcontent", getColumns(),
				new UUMSortableListDataProvider<User>(null), 15));
		searchcontent.setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true);
	}

	private List<IColumn<User>> getColumns()
	{
		List<IColumn<User>> columns = new ArrayList<IColumn<User>>();
		columns.add(new CheckBoxPropertyColumn<User>(userlistgroup) {
			@Override
			protected void onUpdateCheckBoxItem(AjaxRequestTarget target, AjaxCheckBox checkbox,
					IModel<User> rowModel)
			{
				super.onUpdateCheckBoxItem(target, checkbox, rowModel);
				/*if (CollectionUtils.isNotEmpty(userlistgroup)) {
					for (Iterator<User> iterator = userlistgroup.iterator(); iterator.hasNext();) {
						User u = iterator.next();
						UUMCheckboxTreePanel.this.add(u);
					}
				}*/
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

	private void initDepartmentPath(List<T> selectedlist)
	{
		deptpaths = new ArrayList<DepartmentTreeNode>();
		if (CollectionUtils.isNotEmpty(selectedlist)) {
			for (T t : selectedlist) {

				if (t.getResource() instanceof User) {
					User u = (User) t.getResource();

					List<Department> deptList = uumService.getUserDepartments(u);
					for (Iterator<Department> iterator = deptList.iterator(); iterator.hasNext();) {
						Department department = (Department) iterator.next();
						DepartmentTreeNode deptnode = new DepartmentTreeNode(department);
						deptpaths.add(deptnode);
						deptpaths.addAll(getParentsByDepartment(rootDepartment, department));
					}
				} else if (t.getResource() instanceof Department) {
					Department d = (Department) t.getResource();
					deptpaths.addAll(getParentsByDepartment(rootDepartment, d));
				}
			}
		}
	}

	private void initTree()
	{
		tree = new CheckBoxTree("tree", createTreeModel()) {
			/**
			 * 
			 */
			private static final long serialVersionUID = -7962230746064854275L;

			@Override
			protected void onJunctionLinkClicked(AjaxRequestTarget target, Object node)
			{
				addNodes((DefaultMutableTreeNode) node, false, target);
			}

			@SuppressWarnings("unchecked")
			@Override
			protected void onNodeCheckUpdated(TreeNode node, final BaseTree tree,
					final AjaxRequestTarget target)
			{

				super.onNodeCheckUpdated(node, tree, target);
				final boolean checked = tree.getTreeState().isNodeSelected(node);
				if (checked) {// 节点是否选中
					if (getTreemultiple()) {
						UUMCheckboxTreePanel.this.add((T) node);
					} else {
						UUMCheckboxTreePanel.this.clear();
						UUMCheckboxTreePanel.this.add((T) node);
						UUMCheckboxTreePanel.this.onSubmit(target, getList(), getRemoveList());
					}

				} else {
					UUMCheckboxTreePanel.this.remove((T) node);
				}
				StringBuilder str = new StringBuilder();
				for (Iterator<ResourceMutableTreeNode> iterator = (Iterator<ResourceMutableTreeNode>) UUMCheckboxTreePanel.this
						.getList().iterator(); iterator.hasNext();) {
					ResourceMutableTreeNode type = iterator.next();
					if (type instanceof UserTreeNode) {
						User u = (User) type.getResource();
						str.append(u.getName()).append(",");
					} else if (type instanceof DepartmentTreeNode) {
						Department d = (Department) type.getResource();
						str.append(d.getName()).append(",");
					}

				}
				selectedValue1.setDefaultModelObject(str.toString());
				target.add(selectedValue1);
				visitChildrenCheckbox(node, checked, target);

			}

			@Override
			protected void visibleCheckboxIconPanel(IModel<Object> model, final CheckBox checkbox)
			{

				if (getTClass() == UserTreeNode.class
						&& model.getObject() instanceof DepartmentTreeNode) {
					checkbox.setVisible(false);
				} else if (getTClass() == DepartmentTreeNode.class
						&& model.getObject() instanceof UserTreeNode) {
					checkbox.setVisible(false);
				}
				if (CollectionUtils.isNotEmpty(unSelectedNodes)) {
					if (unSelectedNodes.contains(model.getObject())) {
						checkbox.setVisible(false);
					}
				}
				TreeNode parentNode = ((ResourceMutableTreeNode) model.getObject()).getParent();
				if (tree.getTreeState().isNodeSelected(parentNode)) {
					checkbox.setEnabled(false);
				}
				((MarkupContainer) tree.getNodeComponent(parentNode)).visitChildren(CheckBox.class,
						new IVisitor<CheckBox, Void>() {

							public void component(CheckBox object, IVisit<Void> visit)
							{
								if (!object.isEnabled()) {
									checkbox.setEnabled(false);
								}
							}
						});

				UUMCheckboxTreePanel.this.visibleCheckboxIconPanel(model, checkbox);

			}
		};
		add(tree);
		tree.getTreeState().setAllowSelectMultiple(getTreemultiple());// 多选or单选
		tree.setRootLess(true);
		addNodes(rootNode, false, null);

	}

	private void visitChildrenCheckbox(TreeNode node, final boolean checked,
			final AjaxRequestTarget target)
	{

		Enumeration<TreeNode> e = node.children();
		while (e.hasMoreElements()) {
			TreeNode n = e.nextElement();

			((MarkupContainer) tree.getNodeComponent(n)).visitChildren(CheckBox.class,
					new IVisitor<CheckBox, Void>() {

						public void component(CheckBox object, IVisit<Void> visit)
						{
							object.setEnabled(!checked);
							if (object.getModelObject()) {
								object.modelChanging();
								object.setDefaultModel(new Model<Boolean>(false));
								// object.onSelectionChanged();

								object.modelChanged();
								// object.add(AttributeAppender.remove("checked"));
							}

							target.add(object);
						}
					});
			if (tree.getTreeState().isNodeSelected(n)) {
				tree.getTreeState().selectNode(n, false);
			}
			UUMCheckboxTreePanel.this.remove((T) n);
			/*target.add(tree.getNodeComponent(node));
			tree.updateTree(target);*/
			visitChildrenCheckbox(n, checked, target);
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

	@SuppressWarnings("unchecked")
	protected TreeModel createSearchTreeModel(String name, AjaxRequestTarget target)
	{
		searchList = new ArrayList<T>();
		rootNode = new DepartmentTreeNode(rootDepartment);
		Condition condition = new Condition();
		condition.nameLike(name);
		List<User> searchUserList = uumService.searchUsersByCondition(condition);
		if (CollectionUtils.isNotEmpty(searchUserList)) {
			for (User u : searchUserList) {
				searchList.add((T) new UserTreeNode(u));
			}
			initDepartmentPath(searchList);
			addNodes(rootNode, true, target);
		} else {

			ResourceMutableTreeNode newNode = new ResourceMutableTreeNode("没有记录");
			rootNode.add(newNode);

		}

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
				if (CollectionUtils.isNotEmpty(getList()) && getList().contains(subNode)) {
					tree.getTreeState().selectNode(subNode, true);
				}

				if (CollectionUtils.isNotEmpty(deptpaths) && deptpaths.contains(subNode)) {
					addNodes(subNode, isSearch, target);
					tree.getTreeState().expandNode(subNode);
				}
			}
			if (isSearch) {
				addSearchLeaf(current, target);
			} else if (getTClass() != DepartmentTreeNode.class) {
				addLeaf(current, target);
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
			form.add(TEXT.setOutputMarkupId(true));
			final AjaxWaitingButton SEARCH = new AjaxWaitingButton("search") {

				protected void onError(AjaxRequestTarget target, Form<?> form)
				{
				}

				protected void onSubmit(AjaxRequestTarget target, Form<?> form)
				{
					tree.getTreeState().collapseAll();
					// tree.setModelObject(createSearchTreeModel(TEXT.getInput(), target));
					tree.updateTree(target);

					// searchcontent
					Condition condition = new Condition();
					condition.nameLike(TEXT.getInput());
					List<User> searchUserList = uumService.searchUsersByCondition(condition);
					if (CollectionUtils.isNotEmpty(searchUserList)) {
						AjaxFallbackSortDataTable<User> newtable = new AjaxFallbackSortDataTable<User>(
								"searchcontent", getColumns(),
								new UUMSortableListDataProvider<User>(searchUserList), 15);
						searchcontent.replaceWith(newtable);
						searchcontent = newtable;
						searchcontent.setVisible(true);
						target.add(searchcontent);
					} else {
						searchcontent.setVisible(true);
						target.add(searchcontent);
					}

				}

			};
			form.add(SEARCH.setDefaultFormProcessing(false));
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

	public CheckBoxTree getTree()
	{
		return this.tree;
	}

	public boolean getTreemultiple()
	{
		return this.treemultiple;
	}

	public void onSubmit(AjaxRequestTarget target, List<T> selectedList, List<T> removeList)
	{
	}

	public void visibleCheckboxIconPanel(IModel<Object> model, CheckBox checkbox)
	{

	}

	public List<User> getUserlistgroup()
	{
		return userlistgroup;
	}

	public abstract Class getTClass();
	/*private Class getTClass()
	{
		ParameterizedType p = ((ParameterizedType) UserTreePanel.this.getClass()
				.getGenericSuperclass());
		return (Class) p.getActualTypeArguments()[0];

	}*/

}
