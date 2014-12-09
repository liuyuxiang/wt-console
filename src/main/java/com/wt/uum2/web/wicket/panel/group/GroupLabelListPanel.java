package com.wt.uum2.web.wicket.panel.group;

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
import com.wt.uum2.domain.Group;
import com.wt.uum2.web.wicket.panel.ChangeListPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Xiao Guoying
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupLabelListPanel extends ChangeListPanel<Group> {

	/**
	 * 
	 */
	protected ModalWindow groupTreeModal;

	/**
	 * 
	 */
	private WebMarkupContainer groupsContainer;
	
	/**
	 * 
	 */
	private FeedbackPanel groupsFeedback;

	/**
	 * 
	 */
	private static final long serialVersionUID = 6304789731058205298L;
	/**
	 * 
	 */
	private boolean haveAppGroup;

	/**
	 * @param id
	 *            id
	 * @param groups
	 *            groups
	 */
	public GroupLabelListPanel(String id, List<Group> groups)
	{

		super(id,groups);
		this.haveAppGroup = false;
		setOutputMarkupId(true);

		initComponents();
	}

	/**
	 * @param id
	 *            id
	 * @param groups
	 *            groups
	 * @param haveAppGroup
	 *            haveAppGroup
	 */
	public GroupLabelListPanel(String id, List<Group> groups, boolean haveAppGroup)
	{
		this(id, groups);
		this.haveAppGroup = haveAppGroup;

	}

	@Override
	public void validate() {
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
	 */
	protected void initComponents() {

		IModel<List<Group>> model = new LoadableDetachableModel<List<Group>>() {

			@Override
			protected List<Group> load()
			{
				return getList();
			}

		};

		groupsContainer = new WebMarkupContainer("groupsContainer");
		groupsContainer.setOutputMarkupId(true);

		groupsContainer.add(new ListView<Group>("groupListView", model) {

			@Override
			protected void populateItem(ListItem<Group> item)
			{

				item.add(new Label("groupName", new PropertyModel<String>(item
						.getModel(), "name")));
				item.add(new Label("separator", ",")
				.setVisible(getModelObject().size() - 1 != item.getIndex()));// 如果为最后一项则不显示分隔符

			}

		});
		add(groupsContainer);
		add(new AjaxWaitingLink("groupEditButton") {
			private boolean isNotRender = true;
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				groupTreeModal.show(target);
				target.add(setEnabled(true));
			}

			@Override
			protected void onAfterRender()
			{
				super.onAfterRender();
				if (GroupLabelListPanel.this.isRequired() && isNotRender) {
					this.getResponse().write("&nbsp;<span style='color:red;'>*</span>");
					isNotRender = false;
				}

			}

		});

		groupsFeedback=new SimpleFeedbackPanel("groupsFeedback",
				new ComponentFeedbackMessageFilter(this));
		add(groupsFeedback.setOutputMarkupId(true));

		groupTreeModal = createGroupTreeModal("groupTreeModal", getList());
		add(groupTreeModal);
	}

	/**
	 * 方法说明：
	 * 
	 * @param id
	 *            id
	 * @param groups
	 *            groups
	 * @return ModalWindow
	 */
	protected ModalWindow createGroupTreeModal(String id, final List<Group> groups)
	{

		ModalWindow modalWindow = new LazyLoadModalWindow("modal"){

			@Override
			public Component lazyCreateContent() {
				return new GroupCheckBoxTree(this.getContentId(), groups, haveAppGroup) {
					@Override
					public void onGroupNodeLinkClicked(GroupTreeNode node,
							BaseTree tree, AjaxRequestTarget target) {

						if (tree.getTreeState().isNodeSelected(node)) {
							GroupLabelListPanel.this.add(node.getGroup());
						} else {
							GroupLabelListPanel.this.remove(node.getGroup());
						}

						target.add(groupsContainer);

					}

				};
			}

			@Override
			public void close(AjaxRequestTarget target) {
				super.close(target);
				GroupLabelListPanel.this.validate();
				target.add(groupsFeedback);
			}
			
		};
		modalWindow.setCookieName(id);

		return modalWindow;
	}
}
