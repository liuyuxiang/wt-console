package com.wt.hea.rest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hirisun.hea.api.domain.NavNodeCondition;
import com.wt.hea.rbac.model.Index;
import com.wt.hea.rbac.service.IndexPersonalizationService;
import com.wt.hea.rbac.service.IndexService;
import com.wt.hea.rbac.service.RBACService;
import com.hirisun.hea.api.domain.Group;
import com.hirisun.hea.api.domain.User;

/**
 * 
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-12-17
 * 作者:	Administrator
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人： 
 *    修改内容：
 * </pre>
 */
@Path("/index")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Service
public class NavigationResource
{
	/**
	 * 
	 */
	@Autowired()
	@Qualifier("heaIndexService")
	private IndexService indexService;
	
	
	@Autowired()
	@Qualifier("heaIndexPersonalizationService")
	private IndexPersonalizationService indexPersonalizationService;
	/**
	 * 
	 */
	@Autowired
	protected RBACService rbacService;

	@Path("/all")
	@GET
	public List<Index> getAll()
	{
		return indexService.findAll();
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param uuid
	 *            uuid
	 * @return Index
	 */
	@Path("/id/{uuid}")
	@GET
	public Index get(@PathParam("uuid") String uuid)
	{
		return indexService.findById(uuid);
	}
	/**
	 * 获得所有的app
	 * @param parentUuid
	 * @param userUUID
	 * @return
	 */
	@Path("/apps/{parentUuid}")
	@GET
	public List<Index> getApps(@PathParam("parentUuid") String parentUuid){
		return indexService.findByProperty("parentindexuuid", parentUuid);
	}
	/**
	 * 
	 * 方法说明：
	 * 
	 * @return String
	 */
	@Path("/foo")
	@GET
	public String foo()
	{
		return "hello world";
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param uuid
	 *            uuid
	 * @return Index
	 */
	public Index getParent(String uuid)
	{
		return indexService.findById(uuid);
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param uuid
	 *            uuid
	 * @param userUUID
	 *            userUUID
	 * @return List<Index>
	 */
	@Path("/children/jsonp/{uuid}")
	@GET
	public Object getChildrenJsonp(@PathParam("uuid") String uuid, @QueryParam("userId") String userId,@QueryParam("callback") String callback)
	{
		
		List<String> groupIds = new ArrayList<String>();
		for (Group group : rbacService.getUserGroups(rbacService.getUserByUserId(userId))){
			groupIds.add(group.getUuid());
		}
		List<Index> indexList = indexService.findChildIndexByGroupID(groupIds, 0, uuid, 1);
		return buildObject(indexList, callback);
	}
	
	/**
	 * 
	 * 方法说明：
	 * 
	 * @param uuid
	 *            uuid
	 * @param userUUID
	 *            userUUID
	 * @return List<Index>
	 */
	@Path("/children/{uuid}")
	@GET
	public List<Index> getChildren(@PathParam("uuid") String uuid, @QueryParam("userUUID") String userUUID)
	{
		List<String> groupIds = new ArrayList<String>();
		for (Group group : rbacService.getUserGroups(rbacService.getUserByUuid((userUUID)))){
			groupIds.add(group.getUuid());
		}
		return indexService.findChildIndexByGroupID(groupIds, 0, uuid, 1);
	}

	/**
	 * 
	 * 方法说明：
	 * 
	 * @param uuid
	 *            uuid
	 * @param navNodeCondition
	 *            navNodeCondition
	 * @return List<Index>
	 */
	@Path("/children")
	@GET
	public List<Index> getChildren(@PathParam("uuid") String uuid, NavNodeCondition navNodeCondition)
	{
		return indexService.findChildByParentId(uuid, 0);
	}

	/**
	 * 
	 * 方法说明：根节点
	 * 
	 * @param uuid
	 *            uuid
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	@Path("/app/root/{uuid}")
	@GET
	public List<Index> getRoot(@PathParam("uuid") String uuid, @QueryParam("appId") String appId)
	{
		User ssoUser = rbacService.getUserByUuid(uuid);

		List<String> groupIds = new ArrayList<String>();
		List<Index> userResources = new ArrayList<Index>();

		List<Integer> listlevel = new ArrayList<Integer>();
		List<Index> listMenu = new ArrayList<Index>();

		if (null == ssoUser) {
			return listMenu;
		}
		for (Group group : rbacService.getUserGroups(ssoUser)) {
			groupIds.add(group.getUuid());
		}
		if (!groupIds.isEmpty()) {
			userResources = indexService.findIndexByGroupID(groupIds, 0, appId);
		}
		for (Index index : userResources) {
			listlevel.add(index.getIndexlevel());
		}
		for (Index index : userResources) {
			if (index.getIndexlevel().compareTo(Collections.min(listlevel)) == 0) {
				index.setSubIndexes(null);
				listMenu.add(index);
			}
		}
		return listMenu;
	}
	/**
	 * 
	 * 方法说明：树节点
	 * 
	 * @param userId
	 *            userId
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	@Path("/app/user/node/{userId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Index> getAppUserNode(@PathParam("userId") String userId, @QueryParam("appId") String appId)
	{
		User ssoUser = rbacService.getUserByUserId(userId);

		List<String> groupIds = new ArrayList<String>();
		List<Index> userResources = new ArrayList<Index>();

		List<Integer> listlevel = new ArrayList<Integer>();
		List<Index> listMenu = new ArrayList<Index>();

		if (null == ssoUser) {
			return listMenu;
		}
		for (Group group : rbacService.getUserGroups(ssoUser)) {
			groupIds.add(group.getUuid());
		}
		if (!groupIds.isEmpty()) {
			userResources = indexService.findIndexByGroupID(groupIds, 0, appId);
		}
		for (Index index : userResources) {
			listlevel.add(index.getIndexlevel());
		}
		for (Index index : userResources) {
			if (index.getIndexlevel().compareTo(Collections.min(listlevel)) == 0) {
				listMenu.add(index);
			}
		}
		return listMenu;
	}
	
	/**
	 * 
	 * 方法说明：树节点
	 * 
	 * @param userId
	 *            userId
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	@Path("/app/user/node/jsonp/{userId}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Object getAppUserNodeJsonp(@PathParam("userId") String userId, @QueryParam("appId") String appId,@QueryParam("callback") String callback)
	{	
		Object  resultObj = new Object();
		User ssoUser = rbacService.getUserByUserId(userId);

		List<String> groupIds = new ArrayList<String>();
		List<Index> userResources = new ArrayList<Index>();

		List<Integer> listlevel = new ArrayList<Integer>();
		List<Index> listMenu = new ArrayList<Index>();
		if (null == ssoUser) {
			return resultObj;
		}
		for (Group group : rbacService.getUserGroups(ssoUser)) {
			groupIds.add(group.getUuid());
		}
		if (!groupIds.isEmpty()) {
			userResources = indexService.findIndexByGroupID(groupIds, 0, appId);
		}
		for (Index index : userResources) {
			listlevel.add(index.getIndexlevel());
		}
		for (Index index : userResources) {
			if (index.getIndexlevel().compareTo(Collections.min(listlevel)) == 0) {
				listMenu.add(index);
			}
		}
		List<Index> result = indexService.getTreeIndex(listMenu, userResources);
		resultObj = buildObject(result, callback);
		return resultObj;
	}
	
	/**
	 * 
	 * 方法说明：树节点
	 * 
	 * @param uuid
	 *            uuid
	 * @param appId
	 *            appId
	 * @return List<Index>
	 */
	@Path("/app/node/{uuid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Index> getAppNode(@PathParam("uuid") String uuid, @QueryParam("appId") String appId)
	{
		User ssoUser = rbacService.getUserByUuid(uuid);

		List<String> groupIds = new ArrayList<String>();
		List<Index> userResources = new ArrayList<Index>();

		List<Integer> listlevel = new ArrayList<Integer>();
		List<Index> listMenu = new ArrayList<Index>();

		if (null == ssoUser) {
			return listMenu;
		}
		for (Group group : rbacService.getUserGroups(ssoUser)) {
			groupIds.add(group.getUuid());
		}
		if (!groupIds.isEmpty()) {
			userResources = indexService.findIndexByGroupID(groupIds, 0, appId);
		}
		for (Index index : userResources) {
			listlevel.add(index.getIndexlevel());
		}
		for (Index index : userResources) {
			if (index.getIndexlevel().compareTo(Collections.min(listlevel)) == 0) {
				listMenu.add(index);
			}
		}
		return listMenu;
	}

	/**
	 * 方法说明：所有节点
	 * 
	 * @param uuid
	 * @param appId
	 * @return
	 */
	@Path("/app/nodes/{uuid}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Index> getAppNodes(@PathParam("uuid") String uuid, @QueryParam("appId") String appId)
	{
		User ssoUser = rbacService.getUserByUuid(uuid);

		List<String> groupIds = new ArrayList<String>();
		List<Index> userResources = new ArrayList<Index>();

		List<Index> listMenu = new ArrayList<Index>();

		if (null == ssoUser) {
			return listMenu;
		}
		for (Group group : rbacService.getUserGroups(ssoUser)) {
			groupIds.add(group.getUuid());
		}
		if (!groupIds.isEmpty()) {
			userResources = indexService.findIndexByGroupID(groupIds, 0, appId);
		}
		for (Index index : userResources) {
			index.setSubIndexes(null);
			listMenu.add(index);
		}
		return listMenu;
	}
	@Path("/update/{userid}/{category}")
	@POST
	public boolean update(List<Index> indexList,@PathParam("userid")String userId,@PathParam("category")String category){
		return this.indexPersonalizationService.update(indexList,userId);
	}
	
	@Path("/getByUseridAndCategory/{userid}/{category}")
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public List<Index> getByUseridAndCategory(@QueryParam("parentId")String parentId,@PathParam("userid")String userid,@PathParam("category")String category){
		List<Index> result = null;
		List<String> groupIds = new ArrayList<String>();
		for (Group group : rbacService.getUserGroups(rbacService.getUserByUserId(userid))) {
			groupIds.add(group.getUuid());
		}
		List<Index> freightList =  indexService.findChildIndexByGroupID(groupIds, 0, parentId, 1);
		result = indexPersonalizationService.getByUseridAndCategory(freightList, userid);
		return result;
	}
	
	/**
	 * 方法说明：根据callback参数返回内容
	 * @param 应该返回的对象, callback
	 * @return Object
	 */
	public Object buildObject(Object obj,String callback){
		if(StringUtils.isBlank(callback)){
			return obj;
		}
		ObjectMapper mapper = new ObjectMapper();
	    AnnotationIntrospector primary = new JaxbAnnotationIntrospector();
	    AnnotationIntrospector secondary = new JacksonAnnotationIntrospector();
	    AnnotationIntrospector pair = new AnnotationIntrospector.Pair(primary, secondary);
	    mapper.getDeserializationConfig().setAnnotationIntrospector(pair);
	    mapper.getSerializationConfig().setAnnotationIntrospector(pair);
	    
	    // Set up the provider
	    JacksonJaxbJsonProvider jaxbProvider = new JacksonJaxbJsonProvider();
	    jaxbProvider.setMapper(mapper);
		
	    try {
	    	StringBuilder str=new StringBuilder(callback);
			str.append("(").append(mapper.writeValueAsString(obj)).append(");");
			return str.toString();
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}
}
