package com.wt.hea.webbuilder.struts.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.web.util.HtmlUtils;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.rbac.model.Index;

/**
 * 二级页面左侧树
 * 
 * @author xiaoqi
 */
public class SecondClassPageAction extends DispatchAction
{

	/**
	 * 
	 * 方法说明： 根据父id查找所有的子节点
	 * 
	 * @param mapping
	 *            mapping
	 * @param form
	 *            form
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return ActionForward
	 * @throws Exception
	 */
	public ActionForward findByParentId(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		String currPath = "";
		String parentIndexId = request.getParameter("indexId");
		String indexLevel = request.getParameter("indexLevel");
		String iframeurl = request.getParameter("url");
		if (!("3".equals(indexLevel))) {
			List<Index> indexList = new ArrayList<Index>();
			Index index = this.indexService.findById(parentIndexId);
			indexList.add(index);
			ArrayList<Index> indexes = new ArrayList<Index>(index.getSubIndexes());
			Collections.sort(indexes);
			for (Index i : indexes) {// 替换子菜单的URL
				String url = i.getIndexurl();
				String tempPath = getCurrentPath(i.getIndexuuid(), currPath);
				i.setDescription(HtmlUtils.htmlEscape(tempPath.replaceAll("\\$", "＞")));
				if (url != null) {
					if (url.indexOf("url") > 0) {
						String str = url.substring(url.indexOf("url") + 4);
						i.setIndexurl(str);
					}
				}
			}
			request.setAttribute("currPath",
					getCurrentPath(indexes.get(0).getIndexuuid(), currPath).replaceAll("\\$", "＞"));
			request.setAttribute("indexList", indexList);
		} else {
			List<Index> indexList = this.indexService.findByProperty("parentindexuuid",
					parentIndexId);
			ArrayList<Index> indexes = null;
			for (Index i : indexList) {
				indexes = new ArrayList<Index>(i.getSubIndexes());
				Collections.sort(indexes);
				for (Index in : indexes) {// 替换子菜单的URL
					String url = in.getIndexurl();
					String tempPath = getCurrentPath(in.getIndexuuid(), currPath);
					if (url.indexOf("url") > 0) {
						String str = url.substring(url.indexOf("url") + 4);
						in.setIndexurl(str);
					}

					in.setDescription(HtmlUtils.htmlEscape(tempPath.replaceAll("\\$", "＞")));
				}
			}
			request.setAttribute("currPath",
					getCurrentPath(indexes.get(0).getIndexuuid(), currPath).replaceAll("\\$", "＞"));
			request.setAttribute("indexList", indexList);
		}
		request.setAttribute("iframeurl", iframeurl);
		return mapping.findForward("childPage");
	}

	/**
	 * 得到当前位置
	 * 
	 * @param indexId
	 *            指标id
	 * @param currPath
	 *            当前路径
	 * @return 当前位置的串
	 */
	private String getCurrentPath(String indexId, String currPath)
	{
		Index index = this.indexService.findById(indexId);
		Index rootMenu = this.indexService.findById(this.rootmenu);

		String aTag = "";
		Index parentIndex = null;
		do {
			parentIndex = index.getParentIndex();
			aTag = "<a class=\"currPath\" target=\"" + index.getTarget() + "\" href=\""
					+ index.getIndexurl() + "\">" + index.getIndexname() + "</a>$";
			if (index.getSubIndexes() != null && index.getSubIndexes().size() == 0) {
				aTag = index.getIndexname();
			}
			currPath = aTag + currPath;
			index = parentIndex;
		} while (parentIndex != null && !(rootMenu.equals(parentIndex)));
		if (currPath != null && currPath.split("$").length > 0) {
			currPath = currPath.substring(currPath.split("\\$")[0].length() + 1);
		}
		return currPath;
	}
}
