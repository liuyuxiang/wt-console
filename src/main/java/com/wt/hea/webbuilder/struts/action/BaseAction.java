package com.wt.hea.webbuilder.struts.action;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.wt.hea.common.action.DispatchAction;
import com.wt.hea.webbuilder.model.BaseInfo;
import com.wt.hea.webbuilder.model.PopWindow;
import com.wt.hea.webbuilder.util.FileIOUtil;

/**
 * 
 * <pre>
 * 业务名: 自助建站的基础action
 * 功能说明: 
 * 编写日期:	2011-4-7
 * 作者:	xiaoqi
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class BaseAction extends DispatchAction
{
	/**
	 * banner资源类型
	 */
	protected final static String BANNER = "07";
	/**
	 * logo资源类型
	 */
	protected final static String LOGO = "05";
	/**
	 * 飘窗资源类型
	 */
	protected final static String FLOATWINDOW = "04";
	/**
	 * 右侧帘资源类型
	 */
	protected final static String RIGHTSIDESHADE = "03";
	/**
	 * 左侧帘资源类型
	 */
	protected final static String LEFTSIDESHADE = "02";
	/**
	 * 弹出窗口资源类型
	 */
	protected final static String POPWINDOW = "08";

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param siteId
	 *            siteId
	 * @param topType
	 *            topType
	 * @param request
	 *            request
	 */
	@SuppressWarnings("deprecation")
	protected void generatePopWindow(String siteId, String topType, HttpServletRequest request)
	{
		String tempWindow = "function append0(obj){ if(obj<10){ return \"0\" + obj; }else {return obj}; }"
				+ "var date = new Date();var dateFormat = date.getFullYear() + \"-\" + append0((date.getMonth() + 1))+\"-\" + append0(date.getDate()) + "
				+ "\" \"+ append0(date.getHours()) + \":\"+append0(date.getMinutes()) +\":\"+ append0(date.getSeconds());if(dateFormat > \"_startTime\" && dateFormat < \"_endTime\"){"
				+ "window.open(\"_url\",\"_title\","
				+ "\"toolbar=no,resizable=no,menubar=no,scrollbars=no,location,status=no,"
				+ "top=_top,left=_left,width=_width,height=_height\");}\r\n";
		String popTemplate = request.getRealPath("heaconsole/web/webbuild/template/pop.html");
		String content = FileIOUtil.readFile(popTemplate);
		StringBuffer window = new StringBuffer("");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("siteId", siteId);
		map.put("topType", topType);
		map.put("suspenStatus", "01");
		List<BaseInfo> bis = this.baseInfoService.findByProperty(map);

		String popPath = this.rootPath + this.siteManageService.findById(siteId).getSiteAddr()
				+ "pop.html";
		if (bis != null && !bis.isEmpty()) {
			PopWindow pw = null;
			for (BaseInfo bi : bis) {
				pw = this.popWindowService.findByTopId(bi.getTopId());
				bi.setPopWindow(pw);
				String[] tempAddr = bi.getSuspenAddr().split(":");
				String top = tempAddr[0];
				String left = tempAddr[1];
				if (bi.getResId() != null && "".equals(bi.getResId())) {
					window.append(tempWindow.replace("_url", bi.getSuspenUrl())
							.replace("_title", pw.getPopTitle()).replace("_top", top)
							.replace("_left", left).replace("_width", pw.getWidth() + "")
							.replace("_height", pw.getHeight() + "")
							.replace("_startTime", pw.getStartTime())
							.replace("_endTime", pw.getEndTime()));
				} else {
					String popPicPath = generatePopPicHtml(siteId, bi, request);
					window.append(tempWindow.replace("_url", "/" + popPicPath)
							.replace("_title", pw.getPopTitle()).replace("_top", top)
							.replace("_left", left).replace("_width", pw.getWidth() + "")
							.replace("_height", pw.getHeight() + "")
							.replace("_startTime", pw.getStartTime())
							.replace("_endTime", pw.getEndTime()));
				}
			}
			content = content.replace("_window", window.toString());
			// FileUtil.fileWrite(popPath, content);
			FileIOUtil.writeFile(new StringBuffer(content), new File(popPath));
		} else {
			File file = new File(popPath);
			if (file.exists()) {
				boolean flag = file.delete();
				if (flag) {
					log.info(flag + "delete file successfully");
				}
			}
		}

	}

	// private void generatePopHtml(String siteId)
	// {
	// }

	/**
	 * 
	 * 方法说明：文件名字为图片的文件名
	 * 
	 * @param siteId
	 *            siteId
	 * @param bi
	 *            bi
	 * @param request
	 *            request
	 * @return String
	 */
	@SuppressWarnings("deprecation")
	private String generatePopPicHtml(String siteId, BaseInfo bi, HttpServletRequest request)
	{
		String picPath = bi.getSuspenPath();
		String fileName = picPath.substring(picPath.indexOf("res"), picPath.length() - 3);
		String popPicPath = this.rootPath + this.siteManageService.findById(siteId).getSiteAddr()
				+ fileName + "html";
		String popPicTemplate = request.getRealPath("heaconsole/web/webbuild/template/poppic.html");
		String content = FileIOUtil.readFile(popPicTemplate);
		content = content.replace("_url", bi.getSuspenUrl()).replace("_width", bi.getWidth() + "")
				.replace("_height", bi.getHeight() + "").replace("_src", "/" + bi.getSuspenPath());
		// FileUtil.fileWrite(popPicPath, content);
		FileIOUtil.writeFile(new StringBuffer(content), new File(popPicPath));
		return this.siteManageService.findById(siteId).getSiteAddr() + fileName + "html";
	}
}
