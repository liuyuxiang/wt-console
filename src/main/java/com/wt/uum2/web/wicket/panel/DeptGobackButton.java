package com.wt.uum2.web.wicket.panel;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.domain.Department;
import com.wt.uum2.service.UUMService;
import com.wt.uum2.web.wicket.panel.dept.DeptListPanel;
import com.wt.uum2.web.wicket.panel.index.DeptTreePanel;
import com.wt.uum2.web.wicket.panel.user.UserListPanel;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-12-23
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DeptGobackButton extends GobackButton
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	@SpringBean
	private UUMService uumService;
	private Department dept;

	/**
	 * @param id
	 *            id
	 * @param dept
	 *            dept
	 */
	public DeptGobackButton(String id, Department dept)
	{
		super(id);
		this.dept = dept;
	}

	/**
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @param dept
	 *            dept
	 */
	public DeptGobackButton(String id, IModel<String> model, Department dept)
	{
		super(id, model);
		this.dept = dept;
	}

	/**
	 * @param id
	 *            id
	 * @param form
	 *            form
	 * @param dept
	 *            dept
	 */
	public DeptGobackButton(String id, Form<?> form, Department dept)
	{
		super(id, form);
		this.dept = dept;
	}

	/**
	 * @param id
	 *            id
	 * @param model
	 *            model
	 * @param form
	 *            form
	 * @param dept
	 *            dept
	 */
	public DeptGobackButton(String id, IModel<String> model, Form<?> form, Department dept)
	{
		super(id, model, form);
		this.dept = dept;
	}

	@Override
	public void onClick(AjaxRequestTarget target, Form<?> form, DeptTreePanel deptTreePanel,
			WebMarkupContainer mainContainer, String panelId)
	{
		dept=uumService.getDepartmentByUUID(dept.getUuid());
		if (InitParameters.isCqGroupAuthor()) {
			mainContainer.addOrReplace(new DeptListPanel("mainPanel", dept));
		} else {
			mainContainer.addOrReplace(new UserListPanel("mainPanel", dept));
		}
		target.add(mainContainer);
	}

}
