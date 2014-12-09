package com.wt.uum2.dao;

import java.util.Date;
import java.util.List;

import nak.nsf.dao.support.BaseDao;

import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceLog;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	Faron
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public interface ResourceLogDao extends BaseDao<ResourceLog>
{

	/**
	 * 方法说明：通过资源对象获得该资源的日志分页
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resource
	 *            resource
	 * @return UserPageResult
	 */
	public UserPageResult getResourceLogsByResource(int page, int pagesize, Resource resource);

	/**
	 * 方法说明：通过资源类型获得该类型下已经物理删除的资源日志
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	public UserPageResult getDelResourceLogs(int page, int pagesize, Integer type);

	/**
	 * 通过资源对象获得该资源下的日志集合
	 * 
	 * @param resource
	 * @return
	 */
	/**
	 * 方法说明：getResourceLogsByResource
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	public List<ResourceLog> getResourceLogsByResource(Resource resource);

	/**
	 * 方法说明：searchResourceLogsByUserContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByUserContent(Integer page, Integer pagesize,
			String searchcontent);

	/**
	 * 方法说明：searchResourceLogsByDeptContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByDeptContent(Integer page, Integer pagesize,
			String searchcontent);

	/**
	 * 方法说明：searchResourceLogsByEditUserContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByEditUserContent(Integer page, Integer pagesize,
			String searchcontent);

	/**
	 * 方法说明：searchResourceLogsByGroupContent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param searchcontent
	 *            searchcontent
	 * @return UserPageResult
	 */
	public UserPageResult searchResourceLogsByGroupContent(Integer page, Integer pagesize,
			String searchcontent);

	/**
	 * 
	 * 方法说明：通过资源和日志周期取得日志列表
	 * 
	 * @param res
	 *            res
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            为空则是当前时间
	 * @return List
	 */
	public List<ResourceLog> searchResourceLogsByEditDate(Resource res, Date beginDate, Date endDate);

}
