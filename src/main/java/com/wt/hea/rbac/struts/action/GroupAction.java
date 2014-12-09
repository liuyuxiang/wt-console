package com.wt.hea.rbac.struts.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * 业务名:组对象控制器 组选项卡所有内容操作
 * 功能说明: 
 * 编写日期:	2011-4-1
 * 作者:	LiYi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GroupAction extends BaseAction
{

	/**
	 * 获取日志实例
	 */
	private final Logger logger = LoggerService.getInstance(GroupAction.class);

	/**
	 * 
	 * 方法说明：组－资源权限分配 组树初始化
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
	public ActionForward initGroupTree(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		User user = WebUtil.getSessionUser(request);// 登录用户
		List<Group> managerGroupList = null;// 用户可管理的组
		boolean isAdmin = rbacService.isAdmin(user);// isAdmin
		if (isAdmin) {
			managerGroupList = rbacService.getAllGroups();
		} else {
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
			boolean hasChild = rbacService.hasChildGroup(rootGroup.getUuid());
			xml.append("<item  text=\"" + rootGroup.getName() + "\" id=\"" + rootGroup.getUuid()
					+ "\" child=\"" + (hasChild ? 1 : 0) + "\" >");
			xml.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + rootGroup.getUuid()
					+ "]]></userdata>");
			for (Group g : childGroupList) {
				hasChild = rbacService.hasChildGroup(g.getUuid());
				xml.append("<item  text=\"" + g.getName() + "\" id=\"" + g.getUuid()
						+ "\" child=\"" + (hasChild ? 1 : 0) + "\">");
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
			xml.append("<item  text=\"" + rootGroup.getName() + "\" id=\"" + rootGroup.getUuid()
					+ "\" child=\"0\" >");
			for (int i = 0; i < geList.size(); i++) {
				GroupExtends ge = geList.get(i);
				if (ge.parentId.equals(rootGroup.getUuid())) {
					StringBuilder childXML = new StringBuilder();
					childXML.append("<item  text=\"" + ge.group.getName() + "\" id=\""
							+ ge.group.getUuid() + "\" child=\"hasChild\" >");
					childXML.append("<userdata name=\"groupUuid\">" + "<![CDATA["
							+ ge.group.getUuid() + "]]>" + "</userdata>");
					String cXML = appendGroupXML(ge.group.getUuid(), geList);
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
	 * 方法说明：组－资源权限分配 组树节点加载
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
	public ActionForward loadNextNodesGroup(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String groupId = request.getParameter("id");
		User user = WebUtil.getSessionUser(request);
		List<Group> childGroupList = null;// 子级组

		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin)
			childGroupList = rbacService.getGroupsByParentGroup(groupId);
		else {
			request.setAttribute("xml", "<tree id=\"0\"></tree>");
			return mapping.findForward("tree_xmlData");
		}
		boolean hasChild = rbacService.hasChildGroup(groupId);
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + groupId + "\">");
		for (Group n : childGroupList) {
			hasChild = rbacService.hasChildGroup(n.getUuid());
			xml.append("<item  text=\"" + n.getName() + "\" id=\"" + n.getUuid() + "\" child=\""
					+ (hasChild ? 1 : 0) + "\" >");
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
	 * 方法说明：追加组树XML片段
	 * 
	 * @param pid
	 *            父ID
	 * @param geList
	 *            组扩展对象List
	 * @return xml
	 */
	private String appendGroupXML(String pid, List<GroupExtends> geList)
	{
		StringBuilder sb = new StringBuilder();
		for (GroupExtends ge : geList) {
			if (ge.parentId.equals(pid)) {
				sb.append("<item  text=\"" + ge.group.getName() + "\" id=\"" + ge.group.getUuid()
						+ "\" child=\"hasChild\" ");
				sb.append(">");
				sb.append("<userdata name=\"groupUuid\">" + "<![CDATA[" + ge.group.getUuid()
						+ "]]>" + "</userdata>");
				String childXML = appendGroupXML(ge.group.getUuid(), geList);
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
		String groupId = request.getParameter("groupId");
		User user = WebUtil.getSessionUser(request);
		StringBuffer indexIdsStr = new StringBuffer("");// 已关联的指标ID
		List<Index> managerIndexList = null;// 用户可管理的指标
		List<String> groupIdList = new ArrayList<String>();
		groupIdList.add(groupId);
		List<Index> indexMapingGroupList = indexService.findIndexByGroupID(groupIdList);// 组关联的资源
		boolean isAdmin = rbacService.isAdmin(user);

		if (isAdmin) {
			managerIndexList = indexService.findAll();
		} else {
			List<String> groupIds = new ArrayList<String>();
			String[] groupIdsArray = WebUtil.getSessionLoginGroupId(request);
			for (String gid : groupIdsArray) {
				groupIds.add(gid);
			}
			managerIndexList = indexService.findIndexByGroupID(groupIds);
		}
		for (Index mg : managerIndexList) {
			for (Index ig : indexMapingGroupList) {
				if (mg.getIndexuuid().equals(ig.getIndexuuid())) {
					indexIdsStr.append(mg.getIndexuuid()).append(",");
					break;
				}
			}
		}
		if ((indexIdsStr.length() > 0)
				&& (indexIdsStr.lastIndexOf(",") == indexIdsStr.length() - 1)) {
			indexIdsStr.deleteCharAt(indexIdsStr.length() - 1);
		}
		request.setAttribute("groupId", groupId);
		request.setAttribute("indexIdsStr", indexIdsStr.toString());
		return mapping.findForward("groupIndexTree");
	}

	/**
	 * 更新组与资源的关联
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
	public ActionForward updateIG(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		IndexForm indexForm = (IndexForm) form;
		String[] indexIdsStr = indexForm.getIndexIdsStr().split(",");
		String[] selectedIds = indexForm.getSelectedIndexIdsStr().split(",");
		String[] bubs = indexForm.getBubs().split(",");
		String groupId = indexForm.getGroupId();

		List<String> addIds = new ArrayList<String>();
		List<String> deleteIds = new ArrayList<String>();
		for (String j : selectedIds) {
			boolean jisAdd = true;
			for (String i : indexIdsStr) {
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
		for (String i : indexIdsStr) {
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
					}
				}
			}
		}
		String[] deleteIdst = new String[deleteIds.size()];
		String[] addIdst = new String[addIds.size()];
		indexService.deleteIndexGroup(deleteIds.toArray(deleteIdst), groupId);
		indexService.addIndexGroup(addIds.toArray(addIdst), groupId);
		super.saveUserAccessLog(request, logger, "组[" + groupId + "]指标关联");
		request.setAttribute("message", "修改成功!");
		return mapping.findForward("message");
	}

	/**
	 * 
	 * 方法说明：组－资源权限分配 资源树初始化
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
		int indexType = Integer.valueOf(request.getParameter("type"));// 资源类型
		String groupId = request.getParameter("groupId");
		List<String> groupIds = new ArrayList<String>();
		groupIds.add(groupId);
		List<Index> groupIndexList = indexService.findIndexByGroupID(groupIds, indexType);// 选中组关联的资源
		User user = WebUtil.getSessionUser(request);
		List<Index> indexList = null;
		boolean isAdmin = rbacService.isAdmin(user);
		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);
		if (isAdmin) {
			indexList = indexService.findAll(indexType);
		} else {
			// indexList = indexService.findIndexByUser(user, indexType);
			List<String> groupIdList = new ArrayList<String>();
			for (String gid : loginGroupIds) {
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

		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"0\">");

		String hasChild = null;
		if (isAdmin) {
			hasChild = indexService.hasChild(rootIndex.getIndexuuid(), indexType) == true ? "1"
					: "0";
		} else {
			hasChild = indexService.hasManagerChild(rootIndex.getIndexuuid(), loginGroupIds,
					indexType) == true ? "1" : "0";
		}
		String isSelect = isSelectIndex(rootIndex.getIndexuuid(), groupIndexList) == true ? "checked='1'"
				: "";
		xml.append("<item  text=\"" + rootIndex.getIndexname() + "\" id=\""
				+ rootIndex.getIndexuuid() + "\" child=\"" + hasChild + "\" " + isSelect + ">");
		for (Index idx : childIndexList) {
			if (isAdmin) {
				hasChild = indexService.hasChild(idx.getIndexuuid(), indexType) == true ? "1" : "0";
			} else {
				hasChild = indexService.hasManagerChild(idx.getIndexuuid(), loginGroupIds,
						indexType) == true ? "1" : "0";
			}
			isSelect = isSelectIndex(idx.getIndexuuid(), groupIndexList) == true ? "checked='1'"
					: "";
			xml.append("<item  text=\"" + idx.getIndexname() + "\" id=\"" + idx.getIndexuuid()
					+ "\" child=\"" + hasChild + "\" " + isSelect + ">");
			xml.append("</item>");
		}
		xml.append("</item>");
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());

		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		request.getSession().setAttribute("groupId", groupId);
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 
	 * 方法说明：组－资源权限分配 资源树节点加载
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
		// Index rootIndex=this.indexService.findById(this.rootIndex);
		String indexuuid = request.getParameter("id");
		String groupId = request.getSession().getAttribute("groupId").toString();
		User user = WebUtil.getSessionUser(request);
		Index rootIndex = this.indexService.findById(indexuuid);
		int indexType = rootIndex.getIndextype();
		List<String> groupIds = new ArrayList<String>();
		groupIds.add(groupId);
		List<Index> groupIndexList = indexService.findIndexByGroupID(groupIds, indexType);// 选中组关联的资源
		boolean isAdmin = rbacService.isAdmin(user);

		String[] loginGroupIds = WebUtil.getSessionLoginGroupId(request);
		List<Index> childIndexList = null;
		// 是管理员
		if (isAdmin) {
			childIndexList = indexService.findChildByParentId(indexuuid, indexType);
		} else {
			List<String> groupIdList = new ArrayList<String>();
			for (String gid : loginGroupIds) {
				groupIdList.add(gid);
			}
			childIndexList = indexService.findChildIndexByGroupID(groupIdList, indexType,
					indexuuid, -1);
		}
		StringBuffer xml = new StringBuffer("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		xml.append("<tree id=\"" + rootIndex.getIndexuuid() + "\">");
		String hasChild = null;
		for (Index n : childIndexList) {
			if (isAdmin) {
				hasChild = indexService.hasChild(n.getIndexuuid(), indexType) == true ? "1" : "0";
			} else {
				hasChild = indexService.hasManagerChild(n.getIndexuuid(), loginGroupIds, indexType) == true ? "1"
						: "0";
			}
			String isSelect = isSelectIndex(n.getIndexuuid(), groupIndexList) == true ? "checked='1'"
					: "";
			xml.append("<item  text=\"" + n.getIndexname() + "\" id=\"" + n.getIndexuuid()
					+ "\" child=\"" + hasChild + "\" " + isSelect + ">");
			xml.append("</item>");
		}
		xml.append("</tree>");
		request.setAttribute("xml", xml.toString().trim());

		response.setContentType("text/xml; charset=UTF-8");
		response.addHeader("Content-Type", "text/xml;charset=UTF-8");
		return mapping.findForward("tree_xmlData");
	}

	/**
	 * 方法说明：判断指标是否选中
	 * 
	 * @param indexId
	 *            indexId
	 * @param indexList
	 *            indexList
	 * @return boolean
	 */
	private boolean isSelectIndex(String indexId, List<Index> indexList)
	{
		for (Index i : indexList) {
			if (i.getIndexuuid().equals(indexId))
				return true;
		}
		return false;
	}

}
