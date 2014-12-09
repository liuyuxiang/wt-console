package com.wt.uum2.web.wicket.common.tree;

import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.commons.lang.StringUtils;

import com.wt.uum2.domain.Resource;


/**
 * <pre>
 * 业务名:树数据结构中的uum的节点
 * 功能说明: 
 * 编写日期:	2012-5-16
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 * 
 * @param <T>
 *            t
 */
public class ResourceMutableTreeNode<T extends Resource> extends DefaultMutableTreeNode
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4220072482658628189L;
	/**
	 * 
	 */
	private T resource;

	public ResourceMutableTreeNode(T resource)
	{
		this(resource, true);
	}
	public ResourceMutableTreeNode(String objectname){
		super(objectname,false);
	}
	public ResourceMutableTreeNode(T resource, boolean allowsChildren)
	{
		super(allowsChildren);
		this.resource = resource;
	}

	public T getResource()
	{
		return this.resource;
	}

	@Override
	public boolean isLeaf()
	{
		return !getAllowsChildren();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ResourceMutableTreeNode)) {
			return false;
		}
		ResourceMutableTreeNode node = (ResourceMutableTreeNode) obj;
		if (this.getResource() == null || node.getResource() == null) {
			return false;
		}
		return StringUtils.equals(this.getResource().getUuid(), node.getResource().getUuid());
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((resource == null) ? 0 : resource.hashCode());
		return result;
	}
}
