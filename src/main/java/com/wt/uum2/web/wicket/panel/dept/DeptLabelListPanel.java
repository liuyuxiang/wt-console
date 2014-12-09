package com.wt.uum2.web.wicket.panel.dept;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.ComponentFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.tree.BaseTree;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;

import com.hirisun.components.web.wicket.feedback.SimpleFeedbackPanel;
import com.hirisun.components.web.wicket.link.AjaxWaitingLink;
import com.hirisun.components.web.wicket.modal.LazyLoadModalWindow;
import com.wt.uum2.domain.Department;
import com.wt.uum2.web.wicket.panel.ChangeListPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-26
 * 作者:	Xiao Guoying
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeptLabelListPanel extends ChangeListPanel<Department>
{

	/**
	 * 
	 */
	protected ModalWindow deptTreeModal;

	/**
	 * 
	 */
	private WebMarkupContainer deptsContainer;

	/**
	 * 
	 */
	private FeedbackPanel deptsFeedback;

	/**
	 * 
	 */
	private boolean selectMultiple;

	/**
	 * 
	 */
	private List<Department> disableDepts;

	/**
	 * 无根的
	 */
	private boolean rootLess;
	/**
	 * 
	 */
	private static final long serialVersionUID = 6304789731058205298L;

	/**
	 * @param id
	 *            id
	 * @param depts
	 *            depts
	 */
	public DeptLabelListPanel(String id, List<Department> depts)
	{

		this(id, depts, null, true, true);
	}

	/**
	 * @param id
	 *            id
	 * @param depts
	 *            depts
	 * @param disableDepts
	 *            disableDepts
	 */
	public DeptLabelListPanel(String id, List<Department> depts, List<Department> disableDepts)
	{

		this(id, depts, disableDepts, true, true);
	}

	/**
	 * @param id
	 *            id
	 * @param depts
	 *            depts
	 * @param selectMultiple
	 *            selectMultiple
	 */
	public DeptLabelListPanel(String id, List<Department> depts, boolean selectMultiple)
	{
		this(id, depts, null, selectMultiple, true);
	}

	/**
	 * @param id
	 *            id
	 * @param depts
	 *            默认选中列表
	 * @param disableDepts
	 *            不可选部门列表
	 * @param selectMultiple
	 *            多选的
	 * @param rootLess
	 *            无根的
	 */
	public DeptLabelListPanel(String id, List<Department> depts, List<Department> disableDepts,
			boolean selectMultiple, boolean rootLess)
	{

		super(id, depts);

		setOutputMarkupId(true);
		this.selectMultiple = selectMultiple;
		this.disableDepts = disableDepts;
		this.rootLess = rootLess;
		initComponents();
	}

	/**
	 * 方法说明：
	 * 
	 */
	protected void initComponents()
	{

		IModel<List<Department>> model = new LoadableDetachableModel<List<Department>>() {

			@Override
			protected List<Department> load()
			{
				return getList();
			}

		};

		deptsContainer = new WebMarkupContainer("deptsContainer");
		deptsContainer.setOutputMarkupId(true);

		deptsContainer.add(new ListView<Department>("deptListView", model) {

			@Override
			protected void populateItem(ListItem<Department> item)
			{

				item.add(new Label("deptName", new PropertyModel<String>(item.getModel(), "name")));
				item.add(new Label("separator", ",")
				.setVisible(getModelObject().size() - 1 != item.getIndex()));// 如果为最后一项则不显示分隔符
			}

		});
		add(deptsContainer);

		add(new AjaxWaitingLink("deptEditButton") {
			private boolean isNotRender = true;

			@Override
			public void onClick(AjaxRequestTarget target)
			{
				deptTreeModal.show(target);
			}

			@Override
			protected void onAfterRender()
			{
				super.onAfterRender();
				if (DeptLabelListPanel.this.isRequired() && isNotRender) {
					this.getResponse().write("&nbsp;<span style='color:red;'>*</span>");
					isNotRender = false;
				}

			}

		});

		deptsFeedback = new SimpleFeedbackPanel("deptsFeedback",
				new ComponentFeedbackMessageFilter(this));
		add(deptsFeedback.setOutputMarkupId(true));

		deptTreeModal = createDeptTreeModal("deptTreeModal", getList());
		add(deptTreeModal);
	}

	@Override
	public void validate()
	{
		if (isRequired()) {
			if (getList().isEmpty()) {
				if (getLabel() == null) {
					error("此项不能为空，请选择！");
				} else {
					error(getLabel().getObject() + "不能为空，请选择！");
				}

			}
		}
	}

	/**
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @param depts
	 *            depts
	 * @return ModalWindow
	 */
	protected ModalWindow createDeptTreeModal(String id, final List<Department> depts)
	{

		ModalWindow modalWindow = new LazyLoadModalWindow("modal") {

			@Override
			public Component lazyCreateContent()
			{

				return new DeptTreePanel(this.getContentId(), depts, disableDepts, selectMultiple,
						rootLess) {

					@Override
					public void onDeptNodeLinkClicked(DepartmentTreeNode node, BaseTree tree,
							AjaxRequestTarget target)
					{

						if (tree.getTreeState().isNodeSelected(node)) {
							if (!selectMultiple) {
								DeptLabelListPanel.this.clear();
							}
							DeptLabelListPanel.this.add(node.getDepartment());
						} else {
							DeptLabelListPanel.this.remove(node.getDepartment());
						}

						target.add(deptsContainer);

					}

				};
			}

			@Override
			public void close(AjaxRequestTarget target)
			{
				super.close(target);
				DeptLabelListPanel.this.validate();
				target.add(deptsFeedback);
			}

		};
		modalWindow.setCookieName(id);

		return modalWindow;
	}

}
