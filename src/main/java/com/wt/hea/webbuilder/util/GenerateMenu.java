package com.wt.hea.webbuilder.util;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.wt.hea.common.util.FileUtil;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.service.IndexService;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 跟据某地市站点生成无限制级导航菜单
 * 编写日期:	2011-3-29
 * 作者:	yuanmingmin
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class GenerateMenu
{

	/**
	 * 系统默认密码
	 */
	private static String OSEncoding = FileUtil.getEncoding();

	/**
	 * 
	 * 方法说明： 根据系统获取系统默认编码
	 * 
	 * @return 系统所能识别的编码
	 */
	public static String getOSEncoding()
	{
		if (System.getProperty("os.name").indexOf("Windows") >= 0) {
			OSEncoding = "GBK";
		}
		return OSEncoding;
	}

	/***
	 * 辅助方法: 是否为第1层结点
	 * 
	 * @param set
	 *            待检测指标集合
	 * @param i
	 *            被检测的指标
	 * @return true or false
	 */
	private static boolean isContain(Set<Index> set, Index i)
	{
		boolean flag = false;
		for (Index t : set) {
			if (t.getIndexuuid().equals(i.getIndexuuid()) && "1".equals(i.getWay())) {
				flag = true;
				break;
			}
		}
		return flag;
	}

	/***
	 * 生成横向菜单,xml文件里存的是html代码片断
	 * 
	 * @param rootIndexuuid
	 *            根节点id
	 * @param indexService
	 *            指标接口
	 * @param path
	 *            生成菜单文件的路径
	 * @param styleFlag
	 *            0代表平板菜单，1代表含有间隔的菜单
	 * @return 生成的banner数据的xml文件
	 * @throws Exception
	 */
	public static File createBannersMenu(String rootIndexuuid, IndexService indexService,
			String path, String styleFlag) throws Exception
	{
		// getOSEncoding();
		List<Index> fullTree = new ArrayList<Index>(); // 整棵树的结点

		Index root = indexService.findById(rootIndexuuid);
		fullTree.add(root);
		fullTree.addAll(indexService.findChildsById(rootIndexuuid));

		Collections.sort(fullTree);
		// 过滤禁用的指标
		List<Index> validedIndex = new ArrayList<Index>();
		for (Index i : fullTree) {
			if ("1".equals(i.getWay())) {
				validedIndex.add(i);
			}
		}
		fullTree = validedIndex;

		// 使用DOM生成XML文件
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document document = builder.newDocument();
		// 创建根结点
		Element xmlRoot = document.createElement("ul");
		xmlRoot.setAttribute("id", "nav");
		Set<Index> firstIndexs = root.getSubIndexes();
		Set<Index> tempSet = new HashSet<Index>();
		for (Index i : firstIndexs) { // 过滤禁用的指标
			if ("1".equals(i.getWay())) {
				tempSet.add(i);
			}
		}
		firstIndexs = tempSet;
		Map<Index, List<Element>> temp = new HashMap<Index, List<Element>>();
		for (Index i : fullTree) {
			// 一次就创建li和ul结点，
			Element li = document.createElement("li");
			Element ul = document.createElement("ul");

			// 给第一层添加附属标签

			if (isContain(firstIndexs, i)) {
				li.setAttribute("class", "top");
				Element a = document.createElement("a");
				a.setAttribute("class", "top_link");
				a.setAttribute("href", i.getIndexurl());
				a.setAttribute("target", i.getTarget());
				Element span = document.createElement("span");
				span.setAttribute("class", "down");
				Text text = document.createTextNode(i.getIndexname());
				// Text text = document.createTextNode(new String(i.getIndexname().getBytes(),
				// OSEncoding));
				// Text text=document.createTextNode(new
				// String(i.getIndexname().getBytes(),"UTF-8"));
				span.appendChild(text);
				a.appendChild(span);
				li.appendChild(a);
				ul.setAttribute("class", "sub");
			} else {
				Element a = document.createElement("a");
				a.setAttribute("class", "down");
				a.setAttribute("href", i.getIndexurl());
				a.setAttribute("target", i.getTarget());
				Element span = document.createElement("span");
				Text text = document.createTextNode(i.getIndexname());
				// Text text=document.createTextNode(new
				// String(i.getIndexname().getBytes(),"UTF-8"));
				span.appendChild(text);
				a.appendChild(span);
				li.appendChild(a);
			}
			List<Element> list = new ArrayList<Element>();
			list.add(li);
			if (i.getSubIndexes().size() > 0 && "1".equals(i.getWay())) {
				Set<Index> ll = i.getSubIndexes();
				int s = 0;
				for (Index dd : ll) {
					if (!"1".equals(dd.getWay())) {
						s += 1;
					}
				}
				if (s < ll.size()) {
					list.add(ul);
					li.appendChild(ul);
				}
			}
			temp.put(i, list);
		}
		Set<Index> set = temp.keySet();
		List<Index> sortedSet = new ArrayList<Index>(set);
		Collections.sort(sortedSet);
		for (Index i : sortedSet) {
			if ("1".equals(i.getWay())) {
				if (i.getIndexuuid() != root.getIndexuuid()) {
					Index parent = i.getParentIndex();
					if (set.contains(parent)) {
						List<Element> par = (List<Element>) temp.get(parent);
						if (par.size() == 2) {
							par.get(0).appendChild(par.get(1));
						}

						List<Element> sub = (List<Element>) temp.get(i);
						if (sub.size() == 2) {
							sub.get(0).appendChild(sub.get(1));
						} else {
							par.get(1).appendChild(sub.get(0));
						}

						par.get(1).appendChild(sub.get(0));
					}
					if (isContain(firstIndexs, i)) {
						xmlRoot.appendChild(temp.get(i).get(0));
						if ("1".equals(styleFlag)) { // 添加分隔符
							Element li2 = document.createElement("li");
							li2.setAttribute("style", "width:2px;float:left;");
							// Element imgTag=document.createElement("img");
							Element divTag = document.createElement("div");
							divTag.setAttribute("class", "separator");
							divTag.setNodeValue("&nbsp;");
							// imgTag.setAttribute("src",
							// "/hea/view_ref/personal/nav_menu/nav_line.gif");
							// imgTag.setAttribute("border", "0");
							li2.appendChild(divTag);
							// li2.appendChild(imgTag);
							xmlRoot.appendChild(li2);
						}
					}
				}
			}
		}
		document.appendChild(xmlRoot);

		TransformerFactory transFactory = TransformerFactory.newInstance();
		Transformer transformer = transFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes"); // 换行没有缩进
		transformer.setOutputProperty(OutputKeys.ENCODING, OSEncoding); //
		// transformer.setOutputProperty(OutputKeys.ENCODING,"UTF-8"); //
		DOMSource domSource = new DOMSource(document);

		FileOutputStream out = null;
		File file = new File(path);
		out = new FileOutputStream(file);
		StreamResult xmlResult = new StreamResult(out);

		transformer.transform(domSource, xmlResult);
		return file;

	}

	/**
	 * 生成树状菜单
	 * 
	 * @param rootIndexuuid
	 *            菜单跟结点id
	 * @param indexService
	 *            linkserver业务对象
	 * @param openedLevel
	 *            指定树状菜单初使化后打开的层级数
	 * @param createdTreeName
	 *            生成树状菜单的名字
	 * @return 树状导航的数据
	 */
	public static StringBuffer createTreeMenu(String rootIndexuuid, IndexService indexService,
			Integer openedLevel, String createdTreeName)
	{
		List<Index> list = new ArrayList<Index>();
		Index rootIndex = indexService.findById(rootIndexuuid);

		// 整棵树加载到list
		list.add(rootIndex);
		list.addAll(indexService.findChildsById(rootIndexuuid));

		StringBuffer html = new StringBuffer();
		html.append("<script type=\"text/javascript\">\r\n");
		html.append("	var data={};" + "\r\n");
		html.append("	data[\"-1_" + rootIndex.getIndexuuid() + "\"] = \"text:"
				+ rootIndex.getIndexname() + ";\"" + "\r\n");
		html.append("	var rootid=\"" + rootIndex.getIndexuuid() + "\";" + "\r\n");
		for (Index i : list.subList(1, list.size())) {
			html.append("	data['" + i.getParentindexuuid() + "_" + i.getIndexuuid()
					+ "'] = \"text:" + i.getIndexname());
			if (i.getIndexmappedurl() != null) {
				html.append(";url:" + i.getIndexmappedurl());
			}
			if (i.getTarget() != null) {
				html.append(";target:" + i.getTarget());
			}
			html.append(";\"\r\n");

		}

		html.append("	Using(\"System.Web.UI.WebControls.MzTreeView\");" + "\r\n");
		html.append("	var a_" + createdTreeName + " = new MzTreeView();" + "\r\n");
		html.append("	a_" + createdTreeName + ".dataSource = data;" + "\r\n");
		html.append("	a_" + createdTreeName + ".autoSort=false;" + "\r\n");
		html.append("	a_" + createdTreeName + ".canOperate=true;" + "\r\n");
		html.append("	document.write(a_" + createdTreeName + ".render());" + "\r\n");
		html.append("	a_" + createdTreeName + ".expandLevel(" + openedLevel + ");" + "\r\n");

		html.append("</script>");

		return html;

	}
}
