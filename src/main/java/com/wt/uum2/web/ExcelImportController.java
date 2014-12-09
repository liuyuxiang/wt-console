package com.wt.uum2.web;

import org.springframework.stereotype.Controller;

import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2011-9-27
 * 作者:	Liu Yuxiang
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Controller
public class ExcelImportController {

	
	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private EventFactory eventFactory;
	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;
	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

}
