package com.wt.uum2.web;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hirisun.up.service.AuthService;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Duty;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserDuty;
import com.wt.uum2.service.DutyService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名: 职务处理的action
 * 功能说明: 
 * 编写日期:	2011-3-29
 * 作者:	刘宇翔
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
@RequestMapping("/duty")
public class DutyController extends BaseController
{

	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private DutyService dutyService;
	
	private AuthService authService;

	/**
	 * 方法说明：应用系统用户列表
	 * 
	 * @param type
	 *            type
	 * @param uuid
	 *            uuid
	 * @param model
	 *            model
	 * @return String
	 */
	@RequestMapping("/content")
	public String dutyContext(@RequestParam(value = "id") String uuid, ModelMap model)
	{
		if (uuid.equals("create")) {
			return "/duty/duty";
		}
		Duty duty = dutyService.get(uuid);
		model.addAttribute("isSuper", uumService.isUserinSuperGroup(uumService.getLoginUser()));
		model.addAttribute("duty", duty);
		return "/duty/content";
	}

	@RequestMapping("/userList")
	public String userdutyList(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "pagesize", defaultValue = "15") Integer pagesize, ModelMap model)
	{
		if (uuid.equals("create")) {
			return "/duty/duty";
		}
		Duty duty = dutyService.get(uuid);
		model.addAttribute("duty", duty);
		UserPageResult<User> upr = new UserPageResult<User>();
		if (duty != null) {
			upr = dutyService.getUsersByDuty(page, pagesize, duty);
		}
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("list", upr.getList());
		return "/duty/userList";
	}

	@RequestMapping("/userSearch")
	public void userdutySearch(@RequestParam(value = "id") String uuid,
			@RequestParam(value = "page", defaultValue = "1") Integer page,
			@RequestParam(value = "pagesize", defaultValue = "15") Integer pagesize,
			@RequestParam("searchvalue") String searchvalue,
			@RequestParam("searchcontent") String searchcontent, ModelMap model)
	{
		searchcontent = searchcontent.trim();
		Condition condition = new Condition();
		if (searchvalue.equals("userid")) {
			condition.setUserId(searchcontent);
		}
		if (searchvalue.equals("username")) {
			condition.setUserName(searchcontent);
		}
		Duty duty = dutyService.get(uuid);
		UserPageResult<UserDuty> upr = new UserPageResult<UserDuty>();
		if (duty != null) {
			upr = dutyService.searchUserByDuty(page, pagesize, condition, duty);
		}
		model.addAttribute("userlist", upr.getList());
		model.addAttribute("javapage", upr.getPager());
		model.addAttribute("duty", duty);
		model.addAttribute("searchvalue", searchvalue);
		model.addAttribute("searchcontent", searchcontent);
	}

	/**
	 * 方法说明：职务
	 * 
	 */
	@RequestMapping("/duty")
	public void dutyHandle(ModelMap model, @RequestParam(value = "uuid",required=false) String uuid)
	{
		Duty duty = dutyService.get(uuid);
		model.addAttribute("duty", duty);
	}

	/**
	 * 方法说明：职务
	 * 
	 */
	@RequestMapping("/publictree")
	public void dutyListHandle(ModelMap model)
	{
		List<Duty> upr = dutyService.getAll();
		model.addAttribute("dutyList", upr);
	}
	
	@RequestMapping("/simpletree/publicajax")
	public void dutyUserListHandle(ModelMap model,@RequestParam(value = "uuid",required=false) String uuid)
	{
		Duty duty = dutyService.get(uuid);
		UserPageResult<User> upr = new UserPageResult<User>();
		if (duty != null) {
			upr = dutyService.getUsersByDuty(1, -1, duty);
		}
		model.addAttribute("users", upr.getList());
	}
	
	@RequestMapping("/rulebuilder")
	public void rulebuilderHandle(ModelMap model)
	{
		System.out.println(authService.toString());
	}
	
	@RequestMapping("/resolveRule")
	public String resolveRuleHandle(ModelMap model,@RequestParam(value = "rule",required=false) String rule)
	{
		model.addAttribute("Finally", authService.getUserByAuthItemRule(rule));
		return "/confirmation";
	}
	
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setDutyService(DutyService dutyService)
	{
		this.dutyService = dutyService;
	}

	public void setAuthService(AuthService authService)
	{
		this.authService = authService;
	}

}