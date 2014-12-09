package com.wt.uum2.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.EventParam;
import com.wt.uum2.domain.SyncLog;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.SynchronismService;
import com.wt.uum2.service.TaskListService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	fanglei
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class AuditController extends BaseController
{
	/**
	 * 
	 */
	private UUMService uumService;
	/**
	 * 
	 */
	private TaskListService taskListService;
	/**
	 * 
	 */
	private SynchronismService synchronismService;

	/**
	 * @param synchronismService
	 *            the synchronismService to set
	 */
	public void setSynchronismService(SynchronismService synchronismService)
	{
		this.synchronismService = synchronismService;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	/**
	 * @param taskListService
	 *            the taskListService to set
	 */
	public void setTaskListService(TaskListService taskListService)
	{
		this.taskListService = taskListService;
	}

	// /////////////////////////////////用户操作审计处理开始/////////////////////

	/**
	 * 方法说明：auditListHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/audit/auditList")
	public String auditListHandler(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = taskListService.getAuditList(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		return "/audit/auditList";
	}

	/**
	 * 方法说明：auditSearchListHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchvalue
	 *            searchvalue
	 * @param searchcontent
	 *            searchcontent
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/audit/auditSearchList")
	public String auditSearchListHandler(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize,
			@RequestParam("searchType") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = taskListService.searchAuditList(page, pagesize, searchcontent,
				searchvalue);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("user", user);
		model.addAttribute("uumService", uumService);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
		return "/audit/auditsearch";
	}

	/**
	 * 方法说明：auditHandler
	 * 
	 * @param model
	 *            model
	 */
	@RequestMapping("/audit/auditMain")
	public void auditHandler(ModelMap model)
	{
		model.addAttribute("loginuser", uumService.getLoginUser());
	}

	// /////////////////////////////////用户操作审计处理结束/////////////////////

	// /////////////////////////////////event 同步日志开始/////////////////////

	/**
	 * 方法说明：syncLogListHandler
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/audit/syncLogList")
	public String syncLogListHandler(@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 15;
		}
		UserPageResult upr = taskListService.getEventList(page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		return "/audit/syncLogList";
	}

	/**
	 * 方法说明：syncLogHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/audit/syncLog")
	public String syncLogHandler(@RequestParam(value = "uuid", required = false) String uuid,
			ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		Event event = taskListService.getEvent(uuid);
		List<SyncLog> synclogList = synchronismService.getSyncLogByEvent(event);
		List<EventParam> eventParamList = taskListService.getEventParamByEvent(event);

		for (EventParam eventParam : eventParamList) {
			if (eventParam.getKey().indexOf("Password") > 0
					|| eventParam.getKey().indexOf("Pwd") > 0) {
				eventParam.setOriginalValue("******");
				eventParam.setValue("******");
			}
		}

		model.addAttribute("event", event);
		model.addAttribute("synclogList", synclogList);
		model.addAttribute("eventParamList", eventParamList);

		return "/audit/syncLog";
	}

	/**
	 * 方法说明：resourceLogHandler
	 * 
	 * @param uuid
	 *            uuid
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/audit/resourceLog")
	public String resourceLogHandler(@RequestParam(value = "uuid", required = false) String uuid,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "pagesize", required = false) Integer pagesize, ModelMap model)
	{
		User user = uumService.getLoginUser();
		if (user == null) {
			return NOTLOGIN;
		}
		if (page == null) {
			page = 1;
		}
		if (pagesize == null) {
			pagesize = 3;
		}
		UserPageResult upr = taskListService.getEventListByResouce(uuid, page, pagesize);
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("tasklist", upr.getList());
		model.addAttribute("uuid", uuid);
		return "/audit/resourceLog";
	}

}