package com.wt.hea.rbac.struts.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.wt.hea.common.infrastructure.logger.Logger;
import com.wt.hea.common.infrastructure.logger.impl.LoggerService;
import com.wt.hea.common.util.WebUtil;
import com.wt.hea.rbac.model.GroupExtends;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.struts.form.IndexForm;
import com.wt.hea.rbac.util.RbacUtil;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:WEB层，指标对象控制器
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class IndexAction extends BaseAction
{
	/**
	 * 获取日志实例
	 */
	private final Logger logger = LoggerService.getInstance(IndexAction.class);

	/**
	 * 
	 * 方法说明：初使化树结构第一层结点,管理页面左侧系统树初始化
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward initTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		List<Index> indexList = null;
		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);
		int indexType = Integer.parseInt(request.getParameter("indexType").toString());
		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin) {
			indexList = indexService.findAll(indexType);
		} else {
			List<String> groupIdList = new ArrayList<String>();
			String[] groupIds = WebUtil.getSessionLoginGroupId(request);
			for (String gid : groupIds) {
				groupIdList.add(gid);
			}
			indexList = indexService.findIndexByGroupID(groupIdList, indexType);
		}
		// 用户没有可管理的指标
		if (indexList.size() == 0) {
			request.setAttribute("xml", "<tree id=\"0\"></tree>");
			return mapping.findForward("tree_xmlData");
		}
		Index rootIndex = null;
		List<Index> childIndexList = new ArrayList<Index>();
		if (isAdmin) {
			rootIndex = indexService.findRootIndex(indexType);
			childIndexList = indexService.findChildByParentId(rootIndex.getIndexuuid(), indexType);
		} else {
			List<String> rootIndexIdList = RbacUtil.getRootIndexId(indexList);
			if (rootIndexIdList.size() > 1) {// 用户可管理的指标不是一棵完整树结构
				rootIndex = new Index();
				rootIndex.setIndexname("根节点");
				rootIndex.setHasChild(1);
				for (String rootId : rootIndexIdList) {
					for (Index index : indexList) {
						if (rootId.equals(index.getIndexuuid())) {
							childIndexList.add(index);
							break;
						}
					}

				}
			} else {// 用户可管理的指标是完整树结构
				String rootIndexId = rootIndexIdList.get(0);
				rootIndex = indexService.findById(rootIndexId);
				childIndexList = indexService.findChildIndexByUser(user, indexType, rootIndexId);
			}
		}

		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"0\">");
		String hasChild = null;
		if (isAdmin) {
			hasChild = indexService.hasChild(rootIndex.getIndexuuid(), indexType) == true ? "1"
					: "0";
		} else {
			if (null == rootIndex.getIndexuuid())
				hasChild = indexService.hasManagerChild(rootIndex.getIndexuuid(), loginGroupIds,
						indexType) == true ? "1" : "0";
		}
		xml.append("<item open=\"1\" text=\"" + this.xmlEnCode(rootIndex.getIndexname())
				+ "\" id=\"" + rootIndex.getIndexuuid() + "\" child=\"" + hasChild + "\">");
		xml.append("<userdata name=\"url\">"
				+ "<![CDATA["
				+ (rootIndex.getIndexurl() != null ? ((rootIndex.getIndexurl().contains("?") == true ? rootIndex
						.getIndexurl() : rootIndex.getIndexurl() + "?")
						+ "&treeNodeId=" + rootIndex.getIndexuuid())
						: "") + "]]>" + "</userdata>");
		for (Index idx : childIndexList) {
			if (isAdmin) {
				hasChild = indexService.hasChild(idx.getIndexuuid(), indexType) == true ? "1" : "0";
			} else {
				hasChild = indexService.hasManagerChild(idx.getIndexuuid(), loginGroupIds,
						indexType) == true ? "1" : "0";
			}
			xml.append("<item  text=\"" + this.xmlEnCode(idx.getIndexname()) + "\" id=\""
					+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\">");
			xml.append("<userdata name=\"url\">" + "<![CDATA["
					+ (idx.getIndexurl() != null ? (processUrl(idx)) : "") + "]]>" + "</userdata>");
			xml.append("</item>");
		}
		xml.append("</item>");
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		// System.out.println(xml);
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：管理页面左侧系统树节点加载
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward loadNextNodes(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String indexuuid = request.getParameter("id");
		User user = WebUtil.getSessionUser(request);
		Index node = this.indexService.findById(indexuuid);
		boolean isAdmin = rbacService.isAdmin(user);
		List<Index> childIndexList = null;
		int indexType = Integer.parseInt(request.getParameter("indexType").toString());
		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);
		// 是管理员
		if (isAdmin) {
			childIndexList = indexService.findChildByParentId(indexuuid, indexType);
			if (childIndexList != null && childIndexList.size() > 0) {
				Collections.sort(childIndexList);
			}
		} else {
			List<String> groupIdList = new ArrayList<String>();
			String[] groupIds = WebUtil.getSessionLoginGroupId(request);
			for (String gid : groupIds) {
				groupIdList.add(gid);
			}
			childIndexList = indexService.findChildIndexByGroupID(groupIdList, indexType,
					indexuuid, -1);
		}
		if (childIndexList != null && childIndexList.size() > 0) {
			Collections.sort(childIndexList);
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + node.getIndexuuid() + "\">");
		String hasChild = null;
		if (childIndexList != null) {
			for (Index n : childIndexList) {
				if (n.getWay() != null && n.getWay().equals("1")) {
					if (isAdmin) {
						hasChild = indexService.hasChild(n.getIndexuuid(), indexType) == true ? "1"
								: "0";
					} else {
						hasChild = indexService.hasManagerChild(n.getIndexuuid(), loginGroupIds,
								indexType) == true ? "1" : "0";
					}
					xml.append("<item  text=\"" + xmlEnCode(n.getIndexname()) + "\" id=\""
							+ n.getIndexuuid() + "\" child=\"" + hasChild + "\">");
					xml.append("<userdata name=\"url\">");
					xml.append("<![CDATA[");
					xml.append(processUrl(n));
					xml.append("]]>");
					xml.append("</userdata>");

					xml.append("</item>");
				}

			}
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：系统资源/应用资源页面 树初始化
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward initTreeIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);
		List<Index> indexList = null;
		String appId = request.getParameter("appId");
		int indexType = Integer.parseInt(request.getParameter("indexType").toString());
		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);
		// 判断用户是不是管理员
		boolean isAdmin = rbacService.isAdmin(user);
		if (isAdmin) {
			if (indexType == 0) {
				indexList = indexService.findAll(indexType, appId);
			} else if (indexType == 5) {
				indexList = indexService.findAll(indexType);
			}
		} else {
			List<String> groupIdList = new ArrayList<String>();
			String[] groupIds = WebUtil.getSessionLoginGroupId(request);
			for (String gid : groupIds) {
				groupIdList.add(gid);
			}
			indexList = indexService.findIndexByGroupID(groupIdList, indexType, appId);
		}
		// 用户没有可管理的指标
		if (indexList.size() == 0) {
			Index rootIndex = new Index();
			rootIndex.setIndexname("根节点");
			rootIndex.setIndexuuid("hea_indexroot_0");
			rootIndex.setHasChild(0);
			request.setAttribute("xml",
					"<tree id=\"0\"><item text=\"根节点\" id=\"hea_indexroot_0\"></item></tree>");
			return mapping.findForward("tree_xmlData");
		}
		Index rootIndex = null;
		List<Index> childIndexList = new ArrayList<Index>();
		if (isAdmin) {
			rootIndex = indexService.findRootIndex(indexType, appId);
			childIndexList = indexService.findChildByParentId(rootIndex.getIndexuuid(), indexType,
					appId);
		} else {
			List<String> rootIndexIdList = RbacUtil.getRootIndexId(indexList);
			if (rootIndexIdList.size() > 1) {// 用户可管理的指标不是一棵完整树结构
				rootIndex = new Index();
				rootIndex.setIndexname("根节点");
				rootIndex.setIndexuuid("__Y__");
				rootIndex.setHasChild(1);
				for (String rootId : rootIndexIdList) {
					for (Index index : indexList) {
						if (rootId.equals(index.getIndexuuid())) {
							childIndexList.add(index);
							break;
						}
					}
				}
			} else {// 用户可管理的指标是完整树结构
				String rootIndexId = rootIndexIdList.get(0);
				rootIndex = indexService.findById(rootIndexId);
				childIndexList = indexService.findChildIndexByUser(user, indexType, rootIndexId);
			}
		}
		String hasChild = null;
		if (isAdmin) {
			hasChild = indexService.hasChild(rootIndex.getIndexuuid(), indexType) == true ? "1"
					: "0";
		} else {
			hasChild = indexService.hasManagerChild(rootIndex.getIndexuuid(), loginGroupIds,
					indexType) == true ? "1" : "0";
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"0\">");
		xml.append("<item  text=\"" + xmlEnCode(rootIndex.getIndexname()) + "\" id=\""
				+ rootIndex.getIndexuuid() + "\" child=\"" + hasChild + "\">");
		for (Index idx : childIndexList) {
			if (appId.equals(idx.getAppId())) {
				if (isAdmin) {
					hasChild = indexService.hasChild(idx.getIndexuuid(), indexType) == true ? "1"
							: "0";
				} else {
					hasChild = indexService.hasManagerChild(idx.getIndexuuid(), loginGroupIds,
							indexType) == true ? "1" : "0";
				}
				xml.append("<item  text=\"" + xmlEnCode(idx.getIndexname()) + "\" id=\""
						+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\">");
				xml.append("</item>");
			}
		}
		xml.append("</item>");
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());

		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：系统资源/应用资源页面 树节点加载
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward loadNextNodesIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// Index rootIndex=this.indexService.findById(this.rootIndex);
		String indexuuid = request.getParameter("id");
		String appId = request.getParameter("appId");
		User user = WebUtil.getSessionUser(request);
		Index node = this.indexService.findById(indexuuid);
		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);

		boolean isAdmin = rbacService.isAdmin(user);

		List<Index> childIndexList = null;
		int indexType = Integer.parseInt(request.getParameter("indexType").toString());
		// 是管理员
		if (isAdmin) {
			childIndexList = indexService.findChildByParentId(indexuuid, indexType, appId);
		} else {
			List<String> groupIdList = new ArrayList<String>();
			String[] groupIds = WebUtil.getSessionLoginGroupId(request);
			for (String gid : groupIds) {
				groupIdList.add(gid);
			}
			childIndexList = indexService.findChildIndexByGroupID(groupIdList, indexType,
					indexuuid, -1);
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + node.getIndexuuid() + "\">");
		for (Index n : childIndexList) {
			String hasChild = null;
			if (isAdmin) {
				hasChild = indexService.hasChild(n.getIndexuuid(), indexType) == true ? "1" : "0";
			} else {
				hasChild = indexService.hasManagerChild(n.getIndexuuid(), loginGroupIds, indexType) == true ? "1"
						: "0";
			}
			xml.append("<item  text=\"" + xmlEnCode(n.getIndexname()) + "\" id=\""
					+ n.getIndexuuid() + "\" child=\"" + hasChild + "\">");
			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：URL拼接
	 * 
	 * @param n
	 *            指标
	 * @return 地址
	 */
	private static String processUrl(Index n)
	{
		String url = "";
		if (n.getIndexurl() != null) {
			if (n.getIndexurl().contains("?") == true) {
				url = n.getIndexurl() + "&";
			} else {
				url = n.getIndexurl() + "?";
			}
			url = url + "treeNodeId=" + n.getIndexuuid();
		}
		return url;
	}

	/**
	 * 
	 * 方法说明：to初始化资源关联的权限组树
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward toIndexGroupTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String indexId = request.getParameter("indexId");
		User user = WebUtil.getSessionUser(request);
		StringBuffer groupIdsStr = new StringBuffer("");// 已关联的groupID
		List<Group> managerGroupList = null;// 用户可管理的组
		List<Group> indexMapingGroupList = indexService.findGroupByIndexID(indexId);
		;// 资源映射的组
		boolean isAdmin = rbacService.isAdmin(user);
		String[] loginGroups = WebUtil.getSessionLoginGroupId(request);// 用户登录使用的组
		if (isAdmin) {
			managerGroupList = rbacService.getAllGroups();
		} else {
			managerGroupList = rbacService.getManagerGroups(loginGroups);
		}
		for (Group mg : managerGroupList) {
			for (Group ig : indexMapingGroupList) {
				if (mg.getUuid().equals(ig.getUuid())) {
					groupIdsStr.append(mg.getUuid()).append(",");
					break;
				}
			}
		}
		if ((groupIdsStr.length() > 0)
				&& (groupIdsStr.lastIndexOf(",") == groupIdsStr.length() - 1)) {
			groupIdsStr.deleteCharAt(groupIdsStr.length() - 1);
		}
		request.setAttribute("indexId", indexId);
		request.setAttribute("groupIdsStr", groupIdsStr.toString());
		request.setAttribute("isAdmin", isAdmin);
		return mapping.findForward("indexGroupTree");
	}

	/**
	 * 
	 * 方法说明：资源－组 权限分配，组树初始化
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward initIndexGroupTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{

		String indexId = request.getParameter("indexId");
		List<Group> indexGroupList = indexService.findGroupByIndexID(indexId);
		User user = WebUtil.getSessionUser(request);
		List<Group> managerGroupList = null;// 用户可管理的组

		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin) {
			managerGroupList = rbacService.getAllGroups();
		} else {
			// managerGroupList = rbacService.getAllGroups();
			managerGroupList = rbacService
					.getManagerGroups(WebUtil.getSessionLoginGroupId(request));
		}
		List<Group> childGroupList = null;// 子级组
		Group rootGroup = null;// 根级组
		if (isAdmin) {
			rootGroup = rbacService.getRootGroup();
			childGroupList = rbacService.getGroupsByParentGroup(rootGroup.getUuid());

			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<tree id=\"0\">");
			String selected = this.dhtmlTreeNodeSelect(indexGroupList, rootGroup.getUuid());
			boolean hasChild = rbacService.hasChildGroup(rootGroup.getUuid());

			xml.append("<item  text=\"" + xmlEnCode(rootGroup.getName()) + "\" id=\""
					+ rootGroup.getUuid() + "\" child=\"" + (hasChild ? 1 : 0) + "\" " + selected
					+ ">");
			xml.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + rootGroup.getUuid()
					+ "]]></userdata>");
			for (Group g : childGroupList) {
				selected = this.dhtmlTreeNodeSelect(indexGroupList, g.getUuid());
				hasChild = rbacService.hasChildGroup(g.getUuid());
				xml.append("<item  text=\"" + xmlEnCode(g.getName()) + "\" id=\"" + g.getUuid()
						+ "\" child=\"" + (hasChild ? 1 : 0) + "\" " + selected + ">");
				xml.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + g.getUuid() + "]]>"
						+ "</userdata>");
				xml.append("</item>");
			}
			xml.append("</item></tree>");
			request.setAttribute("xml", xml.toString().trim());
			response.setContentType("text/xml; charset=UTF-8");
			response.addHeader("Content-Type", "text/xml;charset=UTF-8");
			return mapping.findForward("tree_xmlData");
		} else {
			rootGroup = new Group();
			rootGroup.setUuid("__Y__");
			rootGroup.setName("根节点");
			List<GroupExtends> geList = new ArrayList<GroupExtends>();
			for (int i = 0; i < managerGroupList.size(); i++) {
				Group group_i = managerGroupList.get(i);
				List<Group> parentGroup = rbacService.getParentGroup(group_i.getUuid());
				GroupExtends ge = new GroupExtends();
				ge.group = group_i;
				ge.parentId = rootGroup.getUuid();
				for (int p = 0; p < parentGroup.size(); p++) {
					Group group_p = parentGroup.get(p);
					for (int k = 0; k < managerGroupList.size(); k++) {
						Group group_k = managerGroupList.get(k);
						if (group_p.getUuid().equals(group_k.getUuid())) {
							ge.parentId = group_p.getUuid();
							break;
						}
					}
				}
				geList.add(ge);
			}

			StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<tree id=\"0\">");
			xml.append("<item  text=\"" + xmlEnCode(rootGroup.getName()) + "\" id=\""
					+ rootGroup.getUuid() + "\" child=\"0\" >");
			for (int i = 0; i < geList.size(); i++) {
				GroupExtends ge = geList.get(i);
				if (ge.parentId.equals(rootGroup.getUuid())) {
					String selected = this.dhtmlTreeNodeSelect(indexGroupList, ge.group.getUuid());
					StringBuilder childXML = new StringBuilder();
					childXML.append("<item  text=\"" + xmlEnCode(ge.group.getName()) + "\" id=\""
							+ ge.group.getUuid() + "\" child=\"hasChild\" " + selected + ">");
					childXML.append("<userdata name=\"groupUuid\">" + "<![CDATA["
							+ ge.group.getUuid() + "]]>" + "</userdata>");
					String cXML = appendGroupXML(ge.group.getUuid(), geList, indexGroupList);
					if (cXML.length() > 10) {
						xml.append(childXML.toString().replace("hasChild", "1"));
					} else {
						xml.append(childXML.toString().replace("hasChild", "0"));
					}
					xml.append(cXML);
					xml.append("</item>");
				}
			}

			xml.append("</item></tree>");
			request.setAttribute("xml", xml.toString().trim());
			response.setContentType("text/xml; charset=UTF-8");
			response.addHeader("Content-Type", "text/xml;charset=UTF-8");
			return mapping.findForward("tree_xmlData");
		}

	}

	/**
	 * 
	 * 方法说明：资源－组 权限分配，组树节点加载
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward indexGroupTreeNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String groupId = request.getParameter("id");
		String indexId = request.getParameter("indexId");
		List<Group> indexGroupList = indexService.findGroupByIndexID(indexId);
		User user = WebUtil.getSessionUser(request);
		List<Group> childGroupList = null;// 子级组

		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin)
			childGroupList = rbacService.getGroupsByParentGroup(groupId);
		else {
			request.setAttribute("xml", "<tree id=\"0\"></tree>");
			return mapping.findForward("tree_xmlData");
		}
		String selected = "no";
		boolean hasChild = rbacService.hasChildGroup(groupId);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + groupId + "\">");
		for (Group n : childGroupList) {
			hasChild = rbacService.hasChildGroup(n.getUuid());
			selected = this.dhtmlTreeNodeSelect(indexGroupList, n.getUuid());
			xml.append("<item  text=\"" + xmlEnCode(n.getName()) + "\" id=\"" + n.getUuid()
					+ "\" child=\"" + (hasChild ? 1 : 0) + "\" " + selected + ">");
			xml.append("<userdata name=\"groupUuid\">");
			xml.append("<![CDATA[" + n.getUuid() + "]]>");
			xml.append("</userdata>");

			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());

		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：判断树结点是否为选中状态
	 * 
	 * @param baseGroup
	 *            基础组
	 * @param groupId
	 *            组ID
	 * @return 页面转向
	 */
	private String dhtmlTreeNodeSelect(List<Group> baseGroup, String groupId)
	{
		for (Group group : baseGroup) {
			if (group.getUuid().equals(groupId))
				return "checked=\"1\"";
		}
		return "";
	}

	/**
	 * 更新指标与组的关联关系
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 * @throws Exception
	 *             系统可以抛出的异常
	 */
	public ActionForward updateIG(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IndexForm indexForm = (IndexForm) form;
		String[] groupIdsStr = indexForm.getGroupIdsStr().split(",");
		String[] selectedIds = indexForm.getSelectedGroupIdsStr().split(",");
		String[] bubs = indexForm.getBubs().split(",");
		String indexId = indexForm.getIndexId();

		List<String> addIds = new ArrayList<String>();
		List<String> deleteIds = new ArrayList<String>();
		for (String j : selectedIds) {
			boolean jisAdd = true;
			for (String i : groupIdsStr) {
				if (j.equals(i)) {
					jisAdd = false;
					break;
				}
			}
			if (jisAdd) {
				if (j != null && !"".equals(j)) {
					addIds.add(j);
				}
			}
		}
		for (String i : groupIdsStr) {
			boolean jisDelete = true;
			for (String j : selectedIds) {
				if (j.equals(i)) {
					jisDelete = false;
					break;
				}
			}
			if (jisDelete) {
				for (String b : bubs) {
					if (null != b && !"".equals(b) && i.equals(b)) {
						deleteIds.add(i);
						break;
					}
				}
			}
		}
		String[] deleteIdst = new String[deleteIds.size()];
		String[] addIdst = new String[addIds.size()];
		indexService.deleteIndexGroup(indexId, deleteIds.toArray(deleteIdst));
		indexService.addIndexGroup(indexId, addIds.toArray(addIdst));
		super.saveUserAccessLog(request, logger, "指标[" + indexId + "]组关联");
		request.setAttribute("message", "修改成功!");
		return mapping.findForward("message");
	}

	/**
	 * 方法说明： 删除子标
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 * @throws Exception
	 *             系统可以抛出的异常
	 * 
	 * 
	 */
	public ActionForward deleteIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String indexuuid = request.getParameter("indexuuid");
		Index index = indexService.findById(indexuuid);
		try {
			String indexName = null;
			if (index != null) {
				indexName = index.getIndexname();
				this.indexService.deleteIndexAndAllChild(indexuuid);
				super.saveUserAccessLog(request, logger, "指标[" + indexuuid + "][" + indexName
						+ "]删除（包含下级指标）");
			} else {
				request.setAttribute("message", "删除资源失败，未选中资源或资源被删除，请偿试重新登陆或与管理员联系");
				return mapping.findForward("message");
			}
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("message", "删除子标失败，请偿试重新登陆或与管理员联系");
			return mapping.findForward("message");
		}
		if (index.getIndextype().equals(5)) {
			request.setAttribute(
					"message",
					"删除指标成功!<script>top.refreshTree(\"" + index.getParentindexuuid()
							+ "\");</script><script>parent.parent.refreshTree('"
							+ index.getParentindexuuid() + "')</script>");
		} else {
			request.setAttribute("message",
					"删除指标成功!<script>parent.parent.refreshTree('" + index.getParentindexuuid()
							+ "')</script>");
		}
		return mapping.findForward("message");
	}

	/**
	 * to 更新指标
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward toUpdateIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String indexuuid = request.getParameter("indexuuid");
		Index index = this.indexService.findById(indexuuid);
		IndexForm indexForm = (IndexForm) form;
		indexForm.setIndex(index);
		request.setAttribute("index", index);
		return mapping.findForward("updateIndex");
	}

	/**
	 * to 增加指标
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward toAddIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		String parentUuid = request.getParameter("parentUuid");
		request.setAttribute("parentUuid", parentUuid);
		request.setAttribute("add", true);
		String[] groupUuids = WebUtil.getSessionLoginGroupId(request);
		List<Group> groupList = new ArrayList<Group>();
		if (groupUuids != null) {
			for (String gid : groupUuids) {
				Group group = rbacService.getGroupByUuid(gid);
				groupList.add(group);
			}
		}
		request.setAttribute("groupList", groupList);
		return mapping.findForward("updateIndex");
	}

	/**
	 * 更新指标
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward updateIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{
		IndexForm indexForm = (IndexForm) form;
		Index index = indexForm.getIndex();
		Index parentIndex = this.indexService.findById(index.getParentindexuuid());
		index.setParentIndex(parentIndex);
		// if (StringUtils.isNotEmpty(index.getIndexuuid())) {
		// index.setCreateTime(DateUtil.getYmdhmsDate(indexForm.getCreatDate()));
		// indexService.update(index);
		// }
		if (StringUtils.isNotEmpty(index.getIndexuuid())) {
			if (!"hea_indexroot_0".equals(index.getIndexuuid())) {
				indexService.update(index);
				super.saveUserAccessLog(request, logger, "指标[" + index.getIndexuuid() + "]更新");
				if (index.getIndextype() == 5 && "1".equals(index.getParentIndex().getWay())) {
					request.setAttribute(
							"message",
							"修改成功!<script>top.refreshTree(\"" + index.getParentindexuuid()
									+ "\");</script><script>parent.parent.refreshTree('"
									+ index.getParentindexuuid() + "')</script>");
				} else {
					request.setAttribute("message", "修改成功!<script>parent.parent.refreshTree('"
							+ index.getParentindexuuid() + "')</script>");
				}
			} else {
				request.setAttribute("message", "根节点不允许修改");
			}
			return mapping.findForward("message");
		} else {
			// 添加
			index.setCreateTime(new Date());
			if (parentIndex != null) {
				index.setIndextype(parentIndex.getIndextype());
				index.setParentIndex(parentIndex);
				index.setIndexlevel(parentIndex.getIndexlevel() + 1);
				// index.setAppId(appId);
				parentIndex.setHasChild(1);
				indexService.update(parentIndex);
				indexService.save(index);
				User user = WebUtil.getSessionUser(request);
				if (!rbacService.isAdmin(user)) {
					String selectGroup = request.getParameter("igGroupUuid");
					indexService.addIndexGroup(index.getIndexuuid(), new String[] { selectGroup });
				}
				super.saveUserAccessLog(request, logger, "指标[" + index.getIndexname() + "]添加");
			} else {
				request.setAttribute("message", "保存失败！父节点已删除！<script>parent.parent.refreshTree('"
						+ index.getParentindexuuid() + "')</script>");
				return mapping.findForward("message");
			}
		}
		if (index.getIndextype().intValue() == 5 && "1".equals(index.getParentIndex().getWay())) {

			request.setAttribute(
					"message",
					"<script>top.refreshTree(\"" + index.getParentindexuuid() + "\");</script>"
							+ "保存成功!<script>parent.parent.refreshTree('"
							+ index.getParentindexuuid() + "')</script>");
		} else {
			request.setAttribute("message",
					"保存成功!<script>parent.parent.refreshTree('" + index.getParentindexuuid()
							+ "')</script>");
		}

		return mapping.findForward("message");
	}

	/**
	 * 追加组XML
	 * 
	 * @param pid
	 *            父级组ID
	 * @param geList
	 *            组扩展对象List
	 * @param indexGroupList
	 *            指标关联的组ID
	 * @return xml数据片断
	 */
	private String appendGroupXML(String pid, List<GroupExtends> geList, List<Group> indexGroupList)
	{
		StringBuilder sb = new StringBuilder();
		for (GroupExtends ge : geList) {
			if (ge.parentId.equals(pid)) {
				String selected = this.dhtmlTreeNodeSelect(indexGroupList, ge.group.getUuid());
				sb.append("<item  text=\"" + xmlEnCode(ge.group.getName()) + "\" id=\""
						+ ge.group.getUuid() + "\" child=\"hasChild\" ");
				sb.append(selected);
				sb.append(">");
				sb.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + ge.group.getUuid()
						+ "]]>" + "</userdata>");
				String childXML = appendGroupXML(ge.group.getUuid(), geList, indexGroupList);
				String xml = null;
				if (childXML.length() > 10) {
					xml = sb.toString().replace("hasChild", "1");
				} else {
					xml = sb.toString().replace("hasChild", "0");
				}
				sb = new StringBuilder(xml);
				sb.append("</item>");
			}
		}
		return sb.toString();
	}

	/**
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向 方法说明：初使化前端树
	 */
	public ActionForward initLouver(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 根级ID
		String rootIndexId = request.getParameter("rootId");
		String[] loginGroupIds = WebUtil.getSessionFrontGroupId(request);
		int indexType = 0;
		List<String> groupIds = new ArrayList<String>();
		for (String id : loginGroupIds) {
			groupIds.add(id);
		}
		Index rootIndex = indexService.findById(rootIndexId);
		List<Index> childIndexList = indexService.findChildIndexByGroupID(groupIds, indexType,
				rootIndexId, 1);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"0\">");
		String hasChild = indexService.hasManagerChild(rootIndex.getIndexuuid(), loginGroupIds,
				indexType) == true ? "1" : "0";
		xml.append("<item  text=\"" + xmlEnCode(rootIndex.getIndexname()) + "\" id=\""
				+ rootIndex.getIndexuuid() + "\" child=\"" + hasChild + "\">");
		for (Index idx : childIndexList) {
			hasChild = indexService.hasManagerChild(idx.getIndexuuid(), loginGroupIds, indexType) == true ? "1"
					: "0";
			xml.append("<item  text=\"" + xmlEnCode(idx.getIndexname()) + "\" id=\""
					+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\">");
			xml.append("<userdata name=\"url\">" + "<![CDATA["
					+ (idx.getIndexurl() != null ? idx.getIndexurl() : "") + "]]>" + "</userdata>");
			xml.append("</item>");
		}
		xml.append("</item>");
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向 方法说明：加载前端树节点
	 */
	public ActionForward loadLouverTreeNode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 根级ID
		String rootIndexId = request.getParameter("id");
		String[] loginGroupIds = WebUtil.getSessionFrontGroupId(request);
		int indexType = 0;
		List<String> groupIds = new ArrayList<String>();
		for (String id : loginGroupIds) {
			groupIds.add(id);
		}
		List<Index> childIndexList = indexService.findChildIndexByGroupID(groupIds, indexType,
				rootIndexId, 1);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + rootIndexId + "\">");
		for (Index idx : childIndexList) {
			String hasChild = indexService.hasManagerChild(idx.getIndexuuid(), loginGroupIds,
					indexType) == true ? "1" : "0";
			xml.append("<item  text=\"" + xmlEnCode(idx.getIndexname()) + "\" id=\""
					+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\">");
			xml.append("<userdata name=\"url\">" + "<![CDATA["
					+ (idx.getIndexurl() != null ? idx.getIndexurl() : "") + "]]>" + "</userdata>");
			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向 方法说明：初使化前端树
	 */
	public ActionForward initTreeChooseParentIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		// 被选择的指标
		String indexId = request.getParameter("indexId");
		String appId = request.getParameter("appId");
		Index index = indexService.findById(indexId);
		String parentIndexId = index.getParentindexuuid();
		int indexType = index.getIndextype();
		Index rootIndex = indexService.findRootIndex(indexType);
		if (indexId.equals(rootIndex.getIndexuuid())) {
			request.setAttribute("xml", "<tree id=\"0\"></tree>");
			return mapping.findForward("tree_xmlData");
		}

		List<Index> childIndexList = indexService.findChildByParentId(rootIndex.getIndexuuid(),
				indexType);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"0\">");
		String hasChild = indexService.hasChild(rootIndex.getIndexuuid(), indexType) == true ? "1"
				: "0";
		xml.append("<item  text=\"" + xmlEnCode(rootIndex.getIndexname()) + "\" id=\""
				+ rootIndex.getIndexuuid() + "\" child=\"" + hasChild + "\">");
		for (Index idx : childIndexList) {
			if (idx.getIndexuuid().equals(indexId)) {
				continue;
			}
			if (appId.equals(idx.getAppId())) {
				String checked = idx.getIndexuuid().equals(parentIndexId) ? " checked=\"1\"" : "";
				hasChild = indexService.hasChild(idx.getIndexuuid(), indexType) == true ? "1" : "0";
				xml.append("<item  text=\"" + xmlEnCode(idx.getIndexname()) + "\" id=\""
						+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\"" + checked + ">");
				xml.append("</item>");
			}
		}
		xml.append("</item>");
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		request.getSession().setAttribute("updateParentIndexFromId", indexId);
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：加载前端树节点
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 */
	public ActionForward loadTreeNodeChooseParentIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		int indexType = 0;
		// 被选择的指标
		String indexId = request.getParameter("id");
		String fromId = request.getSession().getAttribute("updateParentIndexFromId").toString();
		Index index = indexService.findById(fromId);
		indexType = index.getIndextype();
		List<Index> childIndexList = indexService.findChildByParentId(indexId, indexType);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + indexId + "\">");
		for (Index idx : childIndexList) {
			if (idx.getIndexuuid().equals(fromId))
				continue;
			String checked = idx.getIndexuuid().equals(index.getParentindexuuid()) ? " checked=\"1\" "
					: "";
			String hasChild = indexService.hasChild(idx.getIndexuuid(), indexType) == true ? "1"
					: "0";
			xml.append("<item  text=\"" + xmlEnCode(idx.getIndexname()) + "\" id=\""
					+ idx.getIndexuuid() + "\" child=\"" + hasChild + "\"" + checked + ">");
			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());
		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 更新指标父节点
	 * 
	 * @param mapping
	 *            映射对象
	 * @param form
	 *            表单封装对象
	 * @param request
	 *            http请求对象
	 * @param response
	 *            http响应对象
	 * @return 页面转向
	 * @return
	 */
	public ActionForward updateIndexPid(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
	{

		String indexId = request.getParameter("indexId");
		String indexPid = request.getParameter("indexPId");
		if (indexPid != null && !"".equals(indexPid)) {
			Index index = indexService.findById(indexId);
			Index indexP = indexService.findById(indexPid);
			String srcIndexParentId = index.getParentindexuuid();
			index.setParentIndex(indexP);
			index.setParentindexuuid(indexPid);
			indexService.update(index);
			indexService.updateIndexLevelCascade(index, indexP.getIndexlevel() + 1);
			// User user = WebUtil.getSessionUser(request);
			super.saveUserAccessLog(request, logger, "指标[" + index.getIndexuuid() + "]修改父级指标ID为："
					+ index.getParentindexuuid());
			if (index.getIndextype() == 5) {
				request.setAttribute("message", "保存成功!<script>top.refreshTree(\""
						+ srcIndexParentId + "\");</script>"
						+ "<script>parent.parent.refreshTree('" + srcIndexParentId + "')</script>"
						+ "<script>top.refreshTree(\"" + indexPid + "\");</script>"
						+ "<script>parent.parent.refreshTree('" + indexPid + "')</script>");
			} else {
				if (indexPid != "hea_indexroot_0") {
					request.setAttribute(
							"message",
							"保存成功!<script type=\"text/javascript\">function reloadWindow(){window.parent.parent.location.reload();} setTimeout('reloadWindow()',2000);</script>");
				} else {
					request.setAttribute("message", "保存成功!<script>parent.parent.refreshTree('"
							+ "hea_indexroot_0" + "')</script>");
				}
			}
		}
		return mapping.findForward("message");
	}
}
