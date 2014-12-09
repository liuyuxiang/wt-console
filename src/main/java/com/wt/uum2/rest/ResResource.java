package com.wt.uum2.rest;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.jaxrs.JacksonJaxbJsonProvider;
import org.codehaus.jackson.map.AnnotationIntrospector;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.introspect.JacksonAnnotationIntrospector;
import org.codehaus.jackson.xc.JaxbAnnotationIntrospector;

import com.hirisun.up.service.AuthService;
import com.wt.uum2.constants.HbUtils;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceType;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.DepartmentPathService;
import com.wt.uum2.service.DutyService;
import com.wt.uum2.service.UUMAppService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-10
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class ResResource
{
	public final static String CALLBACK="callback";
	@Context
	HttpHeaders requestHeaders;

	@Context
	HttpServletRequest request;
	
	public String getParameter(String key){
		return request.getParameter(key);
	}
	
	/**
	 * 
	 */
	private UUMService uumService;

	/**
	 * 
	 */
	private DutyService dutyService;

	/**
	 * 
	 */
	private UUMAppService uumAppService;
	
	/**
	 * 
	 */
	private AuthService authService;

	/**
	 * 
	 */
	private EventListenerHandler eventListenerHandler;

	/**
	 * 
	 */
	private EventFactory eventFactory;

	/**
	 * @param eventListenerHandler
	 *            the eventListenerHandler to set
	 */
	public DepartmentPathService getDepartmentPathService()
	{
		return departmentPathService;
	}

	public void setDepartmentPathService(DepartmentPathService departmentPathService)
	{
		this.departmentPathService = departmentPathService;
	}

	private DepartmentPathService departmentPathService;

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	/**
	 * @param eventFactory
	 *            the eventFactory to set
	 */
	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public UUMService getUumService()
	{
		return uumService;
	}

	public EventListenerHandler getEventListenerHandler()
	{
		return eventListenerHandler;
	}

	public EventFactory getEventFactory()
	{
		return eventFactory;
	}

	/**
	 * 方法说明：getAttributeValue
	 * 
	 * @param uuid
	 *            uuid
	 * @param attrName
	 *            attrName
	 * @return String
	 */
	@Path("/attr")
	@GET
	public String getAttributeValue(@QueryParam("uuid") String uuid,
			@QueryParam("attrName") String attrName)
	{
		if (StringUtils.isBlank(attrName)) {
			return null;
		}
		Map<String, String> m = getAttributeValues(uuid, new String[] { attrName });
		if (m == null || m.get(attrName) == null) {
			return null;
		}
		return m.get(attrName).toString();
	}

	/**
	 * 方法说明：foo
	 * 
	 * @return Response
	 */
	@Path("/foo")
	@GET
	public Response foo()
	{

		String msg = "This rets interface is normal";
		return Response.status(200).entity(msg).build();
	}

	/**
	 * 方法说明：modifyAttributeValue
	 * 
	 * @param uuid
	 *            uuid
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return boolean
	 */
	@Path("/attr")
	@PUT
	public boolean modifyAttributeValue(@QueryParam("uuid") String uuid,
			@QueryParam("attrName") String attrName, @QueryParam("attrValue") String attrValue)
	{
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put(attrName, attrValue);
		return modifyAttributeValues(uuid, paraMap);
	}

	/**
	 * 方法说明：getAttributeValues
	 * 
	 * @param uuid
	 *            uuid
	 * @param attrNames
	 *            attrNames
	 * @return Map
	 */
	@Path("/attrs")
	@GET
	public Map<String, String> getAttributeValues(@QueryParam("uuid") String uuid,
			@QueryParam("attrNames") String[] attrNames)
	{
		Resource r = uumService.getResource(uuid);
		if (r == null) {
			return null;
		}
		if (ArrayUtils.isEmpty(attrNames)) {
			return uumService.getAttributesMapByResource(r);
		} else {
			return uumService.getAttributesMapByResourceAndTypes(r, attrNames);
		}
	}

	/**
	 * 方法说明：modifyAttributeValues
	 * 
	 * @param uuid
	 *            uuid
	 * @param paraMap
	 *            paraMap
	 * @return boolean
	 */
	@Path("/attrs")
	@PUT
	public boolean modifyAttributeValues(@QueryParam("uuid") String uuid,
			Map<String, String> paraMap)
	{
		boolean flag = true;
		Resource r = uumService.getResource(uuid);
		// event
		Map<String, String[]> attrs = new HashMap<String, String[]>();
		// stringvalue
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> resMap = uumService.getAttributesMapByResource(r);
		for (Map.Entry<String, String> entry : paraMap.entrySet()) {
			if (!StringUtils.equals(resMap.get(entry.getKey()), entry.getValue())) {
				map.put(entry.getKey(), entry.getValue());
				attrs.put(entry.getKey(),
						new String[] { resMap.get(entry.getKey()), entry.getValue() });
			}
		}
		// update db
		uumService.updateResourceAttribute(r, map);
		// insert event
		if (MapUtils.isNotEmpty(attrs)) {
			Event event = null;
			switch (ResourceType.valueOf(r.getType())) {
			case DEPARTMENT:
				event = eventFactory.createEventUpdateDept(uuid, attrs);
				break;

			case GROUP:
				event = eventFactory.createEventUpdateGroup(uuid, attrs);
				break;

			case USER:
				event = eventFactory.createEventUpdateUser(uuid, attrs);
				break;

			default:
				break;
			}
			if (event != null) {
				eventListenerHandler.handle(event);
			}
		}
		return flag;
	}

	/**
	 * 方法说明：getResourceByAttribute
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	@Path("/list")
	@GET
	public List<Resource> getResourceByAttribute(@QueryParam("attrName") String attrName,
			@QueryParam("attrValue") String attrValue)
	{
		if (StringUtils.isBlank(attrName)) {
			return null;
		}
		return uumService.getResourceListByAttribute(attrName, attrValue);
	}
	/**
	 * 方法说明：扩展属性的可管理组(哪些组可编辑某属性)
	 * @param attrName attrName
	 * @return list<group>
	 */
	@Path("/attr/{attrName}/admingroups")
	@GET
	public Response getAttributeAdminGroupsById(@PathParam("attrName") String attrName){
		if (StringUtils.isBlank(attrName)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<AttributeType> types=uumService.getAttributeTypeById(attrName);
		if (CollectionUtils.isEmpty(types)) {
			return Response.status(Status.NO_CONTENT).build();
		}
		Set<Group> adminGroupSet = uumService.getAttributeTypeManagedGroups(types.get(0));
		if(CollectionUtils.isEmpty(adminGroupSet)){
			adminGroupSet.add(	getUumService().getGroupByCode(InitParameters.getSuperGroupCode()));
		}
		return Response.status(Status.OK).entity(adminGroupSet).build();
	}
	/**
	 * 方法说明：扩展属性的所属组(哪些组可查看某属性)
	 * @param attrName attrName
	 * @return list<group>
	 */
	@Path("/attr/{attrName}/groups")
	@GET
	public Response getAttributeGroupsById(@PathParam("attrName") String attrName){
		if (StringUtils.isBlank(attrName)) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		List<AttributeType> types=uumService.getAttributeTypeById(attrName);
		if (CollectionUtils.isEmpty(types)) {
			return Response.status(Status.NO_CONTENT).build();
		}
		Set<Group> adminGroupSet =types.get(0).getGroups();
		if(CollectionUtils.isEmpty(adminGroupSet)){
			adminGroupSet.addAll(uumService.getAllGroups());
		}
		return Response.status(Status.OK).entity(adminGroupSet).build();
	}
	/**
	 * 方法说明：根据callback参数返回内容
	 * @param groups
	 * @return Object
	 */
    public Object buildObject(Object obj){
		String callback=getParameter(CALLBACK);
		if(StringUtils.isBlank(callback)){
			return HbUtils.deproxy(obj);
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
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return obj;
	}

	public DutyService getDutyService()
	{
		return dutyService;
	}

	public void setDutyService(DutyService dutyService)
	{
		this.dutyService = dutyService;
	}

	public UUMAppService getUumAppService()
	{
		return uumAppService;
	}

	public void setUumAppService(UUMAppService uumAppService)
	{
		this.uumAppService = uumAppService;
	}

	public AuthService getAuthService()
	{
		return authService;
	}

	public void setAuthService(AuthService authService)
	{
		this.authService = authService;
	}
}
