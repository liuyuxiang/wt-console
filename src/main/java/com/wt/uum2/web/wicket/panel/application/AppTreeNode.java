package com.wt.uum2.web.wicket.panel.application;

import javax.swing.tree.DefaultMutableTreeNode;

import com.wt.uum2.domain.Application;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-10-31
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class AppTreeNode extends DefaultMutableTreeNode
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3104946662360151607L;

	/**
	 * 
	 */
	private Application application;

	/**
	 * 
	 */
	private boolean junctionClicked;

	/**
	 * 
	 */
	public AppTreeNode()
	{
		super();
		setAllowsChildren(true);
		junctionClicked = false;
	}

	/**
	 * @param application
	 *            application
	 */
	public AppTreeNode(Application application)
	{
		super(application.getName());
		setAllowsChildren(true);
		this.application = application;
		junctionClicked = false;
	}

	/**
	 * @return the application
	 */
	public Application getApplication()
	{
		return application;
	}

	@Override
	public boolean isLeaf()
	{
		return !getAllowsChildren();
	}

	public boolean isJunctionClicked()
	{
		return junctionClicked;
	}

	public void setJunctionClicked(boolean junctionClicked)
	{
		this.junctionClicked = junctionClicked;
	}

}
