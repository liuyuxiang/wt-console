package com.wt.uum2.web.wicket.panel.group;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import nak.nsf.pager.Pager;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.extensions.markup.html.repeater.util.SortableDataProvider;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import com.hirisun.components.web.wicket.modal.LazyLoadModalWindow;
import com.hirisun.components.web.wicket.table.AjaxLinkPropertyColumn;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.GroupList;
import com.wt.uum2.domain.ResourceSync;
import com.wt.uum2.web.wicket.panel.BaseUUMPanel;
import com.wt.uum2.web.wicket.panel.ConfirmationAnswer;
import com.wt.uum2.web.wicket.panel.YesNoPanel;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxFallbackDefaultDataTable;
import com.wt.uum2.web.wicket.panel.datatable.UUMAjaxNavigationToolbar.GotoPageCallback;
import com.wt.uum2.web.wicket.panel.datatable.UserlistToolbarSearch;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-2-24
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupContentPanel extends BaseUUMPanel
{
	/**
	 * 
	 */
	private Group currentGroup;
	/**
	 * 
	 */
	private UserPageResult<GroupList> result;
	/**
	 * 
	 */
	private int currentPage;
	/**
	 * 
	 */
	private int pagesize;
	/**
	 * 
	 */
	private UUMAjaxFallbackDefaultDataTable datatable;
	/**
	 * 
	 */
	private LazyLoadModalWindow comfirmModal;

	/**
	 * @param id
	 *            id
	 */
	public GroupContentPanel(String id)
	{
		this(id, null, 1, 15);
	}

	/**
	 * @param id
	 *            id
	 * @param group
	 *            group
	 * @param currentPage
	 *            currentPage
	 * @param pagesize
	 *            pagesize
	 */
	public GroupContentPanel(String id, Group group, int currentPage, int pagesize)
	{
		this(id, group, null, null, currentPage, pagesize);
	}

	/**
	 * @param id
	 *            id
	 * @param group
	 *            group
	 * @param searchcontent
	 *            searchcontent
	 * @param searchtype
	 *            searchtype
	 * @param currentPage
	 *            currentPage
	 * @param pagesize
	 *            pagesize
	 */
	public GroupContentPanel(String id, Group group, String searchcontent, String searchtype,
			int currentPage, int pagesize)
	{
		super(id);
		this.currentGroup = group;
		this.currentPage = currentPage;
		this.pagesize = pagesize;
		Form<Void> contentForm = new Form<Void>("contentForm");
		add(contentForm);

		final Label separatorLabel = new Label("separator", new ResourceModel("menmberSeparator"));
		if (currentGroup == null) {
			currentGroup = getUUMService().getGroupByUuid("8a394ecd1c4b443c011c4b4459a00040");
			separatorLabel.setVisible(false);
		}
		contentForm.add(separatorLabel.setOutputMarkupId(true));
		final Label groupName = new Label("groupName", new PropertyModel<String>(currentGroup,
				"name"));
		contentForm.add(groupName);
		contentForm.add(new AjaxLink("allMembers") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{
			}
		});
		UserlistToolbarSearch searchPanel = new UserlistToolbarSearch("searchPanel");
		contentForm.add(searchPanel);
		AjaxLink addGroup = new AjaxLink("addGroup") {

			@Override
			public void onClick(AjaxRequestTarget target)
			{

			}
		};
		contentForm.add(addGroup);
		datatable = getDataTable();
		contentForm.add(datatable.setOutputMarkupId(true));

		comfirmModal = new LazyLoadModalWindow("comfirmModal") {

			@Override
			public Component lazyCreateContent()
			{
				return null;
			}
		};
		comfirmModal.setCookieName("comfirmModal");
		comfirmModal.setMinimalWidth(200);
		comfirmModal.setMinimalHeight(100);
		comfirmModal.setInitialWidth(200);
		comfirmModal.setInitialHeight(100);
		comfirmModal.setResizable(false);
		contentForm.add(comfirmModal);

	}

	/**
	 * 方法说明：getDataTable
	 * 
	 * @return UUMAjaxFallbackDefaultDataTable
	 */
	public UUMAjaxFallbackDefaultDataTable getDataTable()
	{
		List columns = new ArrayList();
		columns.add(new PropertyColumn(Model.of("序号"), "num"));
		columns.add(new PropertyColumn(Model.of("ID"), "ID"));
		columns.add(new PropertyColumn(Model.of("类型"), "type"));
		columns.add(new AjaxLinkPropertyColumn<GroupList>(Model.of("名称"), "name") {

			@Override
			public boolean getLinkEnabled(IModel<GroupList> rowModel)
			{
				if (rowModel.getObject().isGroup()) {
					return true;
				}
				return false;
			}

			@Override
			protected void onClick(IModel<GroupList> clicked, AjaxRequestTarget target)
			{
				if (clicked.getObject().isGroup()) {
					Group selectedGroup = getUUMService().getGroupByUuid(
							clicked.getObject().getUuid());
					currentPage = 1;
					GroupContentPanel newPanel = new GroupContentPanel(GroupContentPanel.this
							.getId(), selectedGroup, currentPage, pagesize);
					GroupContentPanel.this.replaceWith(newPanel);
					target.add(newPanel);
				}

			}

		});
		columns.add(new PropertyColumn(Model.of("所在部门"), "department"));
		columns.add(new PropertyColumn(Model.of("描述"), "description"));
		columns.add(new GroupHandleColumn(Model.of("操作"), null) {

			@Override
			protected void onClickEditButton(IModel<GroupList> model, AjaxRequestTarget target)
			{

			}

			@Override
			protected void onClickDeleteButton(final IModel<GroupList> model,
					AjaxRequestTarget target)
			{
				final ConfirmationAnswer ANSWER = new ConfirmationAnswer(false);
				comfirmModal.setContent(new YesNoPanel(comfirmModal.getContentId(), "是否删除此角色？",
						comfirmModal, ANSWER));
				comfirmModal.setWindowClosedCallback(new ModalWindow.WindowClosedCallback() {

					public void onClose(AjaxRequestTarget target)
					{
						if (ANSWER.isAnswer()) {
							Group selectedGroup = getUUMService().getGroupByUuid(
									model.getObject().getUuid());
							if (getUUMService().exitUserUnderGroupAndSubGroups(selectedGroup)) {
								target.appendJavaScript("alert('该组内存在用户或其他被管资源，不能被删除！')");
							} else {
								Event event = getEventFactory().createEventDeleteGroup(
										model.getObject().getUuid());
								if (selectedGroup.getGroupType().equals("1")) {
									deleteAttributeTypeHandler(selectedGroup.getCode());
								}
								selectedGroup = getUUMService().getGroupByUuid(
										model.getObject().getUuid());
								getUUMService().deleteGroup(selectedGroup);
								getEventListenerHandler().handle(event);
							}
						}

					}
				});
				comfirmModal.show(target);
			}

			@Override
			protected void onClickMoveButton(IModel<GroupList> model, AjaxRequestTarget target)
			{
				// TODO Auto-generated method stub

			}

		});

		result = getGroupService().getUsersGroupsByGroup(currentPage, pagesize, currentGroup);
		SortableDataProvider<GroupList> provider = new SortableDataProvider<GroupList>() {

			/**
			 * 
			 */
			private static final long serialVersionUID = -7943633469570488403L;

			public Iterator<? extends GroupList> iterator(int first, int count)
			{
				return result.getList().subList(first, first + count).iterator();
			}

			public int size()
			{
				return result.getList().size();
			}

			public IModel<GroupList> model(GroupList object)
			{
				return Model.of(object);
			}

		};

		GotoPageCallback gotoCallback = new GotoPageCallback() {

			public void gotoPage(AjaxRequestTarget target, Pager pager)
			{
				GroupContentPanel newPanel = new GroupContentPanel(GroupContentPanel.this.getId(),
						currentGroup, pager.getCurrentPage(), pager.getPageSize());
				GroupContentPanel.this.replaceWith(newPanel);
				target.add(newPanel);
			}
		};

		return new UUMAjaxFallbackDefaultDataTable("grouplisttable", columns, provider,
				result.getPager(), gotoCallback);
	}

	/**
	 * 方法说明：deleteAttributeTypeHandler
	 * 
	 * @param code
	 *            code
	 */
	private void deleteAttributeTypeHandler(String code)
	{
		String newcode = "";
		String[] attrlist = { "Account", "Pwd", "LoginEnable", "Status" };
		for (String attr : attrlist) {
			if (attr.equals(attrlist[0])) {
				newcode = InitParameters.getAppAccountLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[1])) {
				newcode = InitParameters.getAppPwdLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[2])) {
				newcode = InitParameters.getAppLoginEnableLocal().replace("XXXX", code);
			} else if (attr.equals(attrlist[3])) {
				newcode = InitParameters.getAppStatusCode().replace("XXXX", code);
			}
			if (getUUMService().existAttributeTypeId(newcode)) {
				AttributeType app = getUUMService().getAttributeTypeById(newcode).get(0);
				List<CandidateItem> aaa = getUUMService().getCandidateItemsByAttributeType(app);
				List<Attribute> att = getUUMService().getAttributesByAttributeType(app);
				List<ResourceSync> sync = getUUMService().getResourceSyncByPorpName(app.getId());
				for (int i = 0; i < aaa.size(); i++) {
					getUUMService().deleteCandidateItem(aaa.get(i));
				}
				for (int i = 0; i < att.size(); i++) {
					getUUMService().deleteAttribute(att.get(i));
				}
				for (int i = 0; i < sync.size(); i++) {
					getUUMService().deleteResourceSync(sync.get(i));
				}
				getUUMService().deleteAttributeType(app);
			}
		}
	}
}
