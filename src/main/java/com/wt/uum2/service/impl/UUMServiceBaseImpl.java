package com.wt.uum2.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.AttributeDao;
import com.wt.uum2.dao.AttributeTypeDao;
import com.wt.uum2.dao.AttributeValueDao;
import com.wt.uum2.dao.CandidateItemDao;
import com.wt.uum2.dao.DepartmentAuthorDao;
import com.wt.uum2.dao.ResourceLogDao;
import com.wt.uum2.dao.ResourceMappingDao;
import com.wt.uum2.dao.ResourceSyncDao;
import com.wt.uum2.dao.ServerLogDao;
import com.wt.uum2.dao.StringValueDao;
import com.wt.uum2.dao.SyncEDDao;
import com.wt.uum2.dao.TaskListDao;
import com.wt.uum2.dao.UserAuthorDao;
import com.wt.uum2.dao.UserMappingDao;
import com.wt.uum2.domain.Application;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.AttributeType;
import com.wt.uum2.domain.AttributeValue;
import com.wt.uum2.domain.CandidateItem;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentAuthor;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.Resource;
import com.wt.uum2.domain.ResourceAdminGroup;
import com.wt.uum2.domain.ResourceLog;
import com.wt.uum2.domain.ResourceSync;
import com.wt.uum2.domain.ServerLog;
import com.wt.uum2.domain.StringValue;
import com.wt.uum2.domain.SyncED;
import com.wt.uum2.domain.TaskList;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserAuthor;
import com.wt.uum2.domain.UseridMapping;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2012-3-22
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public abstract class UUMServiceBaseImpl extends UserServiceImpl
{

	/**
	 * 
	 */
	private ResourceLogDao resourceLogDao;

	/**
	 * 
	 */
	private UserMappingDao userMappingDao;

	/**
	 * 
	 */
	private ServerLogDao serverLogDao;

	/**
	 * 
	 */
	protected TaskListDao taskListDao;

	/**
	 * 
	 */
	protected ResourceMappingDao resourceMappingDao;

	/**
	 * 
	 */
	protected UserAuthorDao userAuthorDao;

	/**
	 * 
	 */
	private DepartmentAuthorDao departmentAuthorDao;

	/**
	 * 
	 */
	private AttributeTypeDao attributeTypeDao;

	/**
	 * 
	 */
	private CandidateItemDao candidateItemDao;

	/**
	 * 
	 */
	private ResourceSyncDao resourceSyncDao;

	/**
	 * 
	 */
	protected AttributeDao attributeDao;

	/**
	 * 
	 */
	private StringValueDao stringValueDao;

	/**
	 * 
	 */
	private AttributeValueDao attributeValueDao;

	/**
	 * 
	 */
	private SyncEDDao syncEDDao;

	public void setAttributeDao(AttributeDao attributeDao)
	{
		this.attributeDao = attributeDao;
	}

	public void setSyncEDDao(SyncEDDao syncEDDao)
	{
		this.syncEDDao = syncEDDao;
	}

	public void setAttributeValueDao(AttributeValueDao attributeValueDao)
	{
		this.attributeValueDao = attributeValueDao;
	}

	public void setStringValueDao(StringValueDao stringValueDao)
	{
		this.stringValueDao = stringValueDao;
	}

	public void setResourceSyncDao(ResourceSyncDao resourceSyncDao)
	{
		this.resourceSyncDao = resourceSyncDao;
	}

	public void setCandidateItemDao(CandidateItemDao candidateItemDao)
	{
		this.candidateItemDao = candidateItemDao;
	}

	public void setAttributeTypeDao(AttributeTypeDao attributeTypeDao)
	{
		this.attributeTypeDao = attributeTypeDao;
	}

	public void setUserAuthorDao(UserAuthorDao userAuthorDao)
	{
		this.userAuthorDao = userAuthorDao;
	}

	public void setDepartmentAuthorDao(DepartmentAuthorDao departmentAuthorDao)
	{
		this.departmentAuthorDao = departmentAuthorDao;
	}

	public void setResourceMappingDao(ResourceMappingDao resourceMappingDao)
	{
		this.resourceMappingDao = resourceMappingDao;
	}

	public void setTaskListDao(TaskListDao taskListDao)
	{
		this.taskListDao = taskListDao;
	}

	/**
	 * @param serverLogDao
	 *            the serverLogDao to set
	 */
	public void setServerLogDao(ServerLogDao serverLogDao)
	{
		this.serverLogDao = serverLogDao;
	}

	public void setUserMappingDao(UserMappingDao userMappingDao)
	{
		this.userMappingDao = userMappingDao;
	}

	public void setResourceLogDao(ResourceLogDao resourceLogDao)
	{
		this.resourceLogDao = resourceLogDao;
	}

	/**
	 * 方法说明：saveResourceLog
	 * 
	 * @param resourceLog
	 *            resourceLog
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveResourceLog(ResourceLog resourceLog)
	{
		// resourceLog.setOperatorIpAdderss(getOperatorIpAddress());
		resourceLogDao.save(resourceLog);
		return 1;
	}

	/**
	 * 方法说明：getResourceLogs
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceLog> getResourceLogs(Resource resource)
	{
		return resourceLogDao.getResourceLogsByResource(resource);
	}

	/**
	 * 方法说明：updateResourceLog
	 * 
	 * @param resourceLog
	 *            resourceLog
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateResourceLog(ResourceLog resourceLog)
	{
		resourceLogDao.update(resourceLog);
		return 1;
	}

	/**
	 * 方法说明：getResourceLogs
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resource
	 *            resource
	 * @return
	 */
	/**
	 * 方法说明：getResourceLogs
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resource
	 *            resource
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getResourceLogs(int page, int pagesize, Resource resource)
	{
		return resourceLogDao.getResourceLogsByResource(page, pagesize, resource);
	}

	/**
	 * 方法说明：getDelResourceLogs
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param type
	 *            type
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getDelResourceLogs(int page, int pagesize, Integer type)
	{
		return resourceLogDao.getDelResourceLogs(page, pagesize, type);
	}

	/**
	 * 方法说明：getResourceLogsByEditTime
	 * 
	 * @param res
	 *            res
	 * @param beginDate
	 *            beginDate
	 * @param endDate
	 *            endDate
	 * @return List
	 */
	@Transactional
	public List<ResourceLog> getResourceLogsByEditTime(Resource res, String beginDate,
			String endDate)
	{
		Date begin = Calendar.getInstance().getTime();
		Date end = Calendar.getInstance().getTime();
		List<ResourceLog> list = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			begin = sdf.parse(beginDate);
			if (StringUtils.isNotBlank(endDate)) {
				end = sdf.parse(endDate);
			} else {
				end = Calendar.getInstance().getTime();
			}
			list = resourceLogDao.searchResourceLogsByEditDate(res, begin, end);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 方法说明：getUserMappingByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UseridMapping> getUserMappingByUser(User user)
	{
		return userMappingDao.getUserMappingByUser(user);
	}

	/**
	 * 方法说明：createServerLog
	 * 
	 * @param serverLog
	 *            serverLog
	 */
	@Transactional(readOnly = false)
	public void createServerLog(ServerLog serverLog)
	{
		serverLogDao.save(serverLog);

	}

	public ServerLog getLastServerLog()
	{

		return serverLogDao.getServerLogList().get(0);

	}

	/**
	 * 方法说明：deleteDepartmentAuthor
	 * 
	 * @param departmentAuthor
	 *            departmentAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteDepartmentAuthor(DepartmentAuthor departmentAuthor)
	{
		departmentAuthorDao.delete(departmentAuthor);
		return 1;
	}

	/**
	 * 方法说明：deleteUserAuthor
	 * 
	 * @param userAuthor
	 *            userAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteUserAuthor(UserAuthor userAuthor)
	{
		userAuthorDao.delete(userAuthor);
		return 1;
	}

	/**
	 * 方法说明：saveDepartmentAuthor
	 * 
	 * @param departmentAuthor
	 *            departmentAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveDepartmentAuthor(DepartmentAuthor departmentAuthor)
	{
		departmentAuthorDao.save(departmentAuthor);
		return 1;
	}

	/**
	 * 方法说明：saveUserAuthor
	 * 
	 * @param userAuthor
	 *            userAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveUserAuthor(UserAuthor userAuthor)
	{
		userAuthorDao.save(userAuthor);
		return 1;
	}

	/**
	 * 方法说明：updateDepartmentAuthor
	 * 
	 * @param departmentAuthor
	 *            departmentAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateDepartmentAuthor(DepartmentAuthor departmentAuthor)
	{
		departmentAuthorDao.update(departmentAuthor);
		return 1;
	}

	/**
	 * 方法说明：updateUserAuthor
	 * 
	 * @param userAuthor
	 *            userAuthor
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateUserAuthor(UserAuthor userAuthor)
	{
		userAuthorDao.update(userAuthor);
		return 1;
	}

	/**
	 * 方法说明：getDepartmentAuthorsByDepartment
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<DepartmentAuthor> getDepartmentAuthorsByDepartment(Department department)
	{
		return departmentAuthorDao.getDepartmentAuthorsByDepartment(department);
	}

	/**
	 * 方法说明：getUserAuthorsByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<UserAuthor> getUserAuthorsByUser(User user)
	{
		return userAuthorDao.getUserAuthorsByUser(user);
	}

	/**
	 * 方法说明：getAttributeTypeByResource
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeByResource(Resource resource, Integer type,
			String catagory)
	{
		return attributeTypeDao.getAttributeTypeByResource(resource, type, catagory);
	}

	/**
	 * 方法说明：deleteAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteAttributeType(AttributeType attributeType)
	{
		attributeTypeDao.delete(attributeType);
		return 1;
	}

	/**
	 * 方法说明：getAttributeTypeById
	 * 
	 * @param id
	 *            id
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeById(String id)
	{
		return attributeTypeDao.getAttributeTypeById(id);
	}

	/**
	 * 方法说明：saveAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveAttributeType(AttributeType attributeType)
	{
		attributeTypeDao.save(attributeType);
		return 1;
	}

	/**
	 * 方法说明：updateAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateAttributeType(AttributeType attributeType)
	{
		attributeTypeDao.update(attributeType);
		return 1;
	}

	/**
	 * 方法说明：getAttributeTypeByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return AttributeType
	 */
	@Transactional(readOnly = true)
	public AttributeType getAttributeTypeByUuid(String uuid)
	{
		return attributeTypeDao.getAttributeTypeByUuid(uuid);
	}

	/**
	 * 方法说明：getAttributeTypeAllByResource
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeAllByResource(Resource resource, Integer type)
	{
		return attributeTypeDao.getAttributeTypeAllByResource(resource, type);
	}

	/**
	 * 方法说明：getAttributeTypeByResourceAndAppGroup
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param appGroup
	 *            appGroup
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeByResourceAndAppGroup(Resource resource,
			Integer type, Group appGroup)
	{
		return attributeTypeDao.getAttributeTypeByResourceAndAppGroup(resource, type, appGroup);
	}

	/**
	 * 方法说明：getCandidateItemsByAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<CandidateItem> getCandidateItemsByAttributeType(AttributeType attributeType)
	{
		return candidateItemDao.getCandidateItembyAttributeType(attributeType);
	}

	/**
	 * 方法说明：saveCandidateItem
	 * 
	 * @param candidateItem
	 *            candidateItem
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveCandidateItem(CandidateItem candidateItem)
	{
		candidateItemDao.save(candidateItem);
		return 1;
	}

	/**
	 * 方法说明：updateCandidateItem
	 * 
	 * @param candidateItem
	 *            candidateItem
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateCandidateItem(CandidateItem candidateItem)
	{
		candidateItemDao.update(candidateItem);
		return 1;
	}

	/**
	 * 方法说明：deleteCandidateItem
	 * 
	 * @param candidateItem
	 *            candidateItem
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteCandidateItem(CandidateItem candidateItem)
	{
		candidateItemDao.delete(candidateItem);
		return 1;
	}

	/**
	 * 方法说明：searchAttributeTypesByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult searchAttributeTypesByResource(int page, int pagesize,
			Integer resourceType, Condition condition)
	{
		return attributeTypeDao.searchAttributeTypesByResource(page, pagesize, resourceType,
				condition);
	}

	/**
	 * 方法说明：getAttributeTypesByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getAttributeTypesByResource(int page, int pagesize, Integer resourceType)
	{
		return attributeTypeDao.getAttributeTypesByResource(page, pagesize, resourceType);
	}

	/**
	 * 方法说明：getAttributeTypeByResourceOnPage
	 * 
	 * @param resource
	 *            resource
	 * @param type
	 *            type
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeByResourceOnPage(Resource resource, Integer type,
			String catagory)
	{
		return attributeTypeDao.getAttributeTypeByResourceOnPage(resource, type, catagory);
	}

	/**
	 * 方法说明：deleteResourceSync
	 * 
	 * @param resourceSync
	 *            resourceSync
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteResourceSync(ResourceSync resourceSync)
	{
		resourceSyncDao.delete(resourceSync);
		return 1;
	}

	/**
	 * 方法说明：getResourceSyncByResource
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param resourceType
	 *            resourceType
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getResourceSyncByResource(int page, int pagesize, Integer resourceType)
	{
		return resourceSyncDao.getResourceSyncByResource(page, pagesize, resourceType);
	}

	/**
	 * 方法说明：getResourceSyncByResource
	 * 
	 * @param resourceType
	 *            resourceType
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceSync> getResourceSyncByResource(Integer resourceType)
	{
		return resourceSyncDao.getResourceSyncByResource(resourceType);
	}

	/**
	 * 方法说明：saveResourceSync
	 * 
	 * @param resourceSync
	 *            resourceSync
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveResourceSync(ResourceSync resourceSync)
	{
		resourceSyncDao.save(resourceSync);
		return 1;
	}

	/**
	 * 方法说明：updateResourceSync
	 * 
	 * @param resourceSync
	 *            resourceSync
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateResourceSync(ResourceSync resourceSync)
	{
		resourceSyncDao.update(resourceSync);
		return 1;
	}

	/**
	 * 方法说明：getResourceSyncByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return ResourceSync
	 */
	@Transactional(readOnly = true)
	public ResourceSync getResourceSyncByUuid(String uuid)
	{
		return resourceSyncDao.getResourceSyncByUuid(uuid);
	}

	/**
	 * 方法说明：existAttributeTypeId
	 * 
	 * @param id
	 *            id
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existAttributeTypeId(String id)
	{
		List<AttributeType> attList = attributeTypeDao.getAttributeTypeById(id);
		if (attList != null) {
			if (attList.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法说明：existAttributeTypeName
	 * 
	 * @param name
	 *            name
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existAttributeTypeName(String name)
	{
		List<AttributeType> attList = attributeTypeDao.getAttributeTypeByName(name);
		if (attList != null) {
			if (attList.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法说明：existResourceSyncPropName
	 * 
	 * @param propName
	 *            propName
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existResourceSyncPropName(String propName)
	{
		List<ResourceSync> attList = resourceSyncDao.getResourceSyncByPropName(propName);
		if (attList != null) {
			if (attList.size() > 0) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 方法说明：getResourceSyncByPorpName
	 * 
	 * @param porpName
	 *            porpName
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<ResourceSync> getResourceSyncByPorpName(String porpName)
	{
		return resourceSyncDao.getResourceSyncByPropName(porpName);
	}

	/**
	 * 方法说明：getAttributeTypesLikeIdKey
	 * 
	 * @param idKey
	 *            idKey
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypesLikeIdKey(String idKey)
	{
		return attributeTypeDao.getAttributeTypesLikeIdKey(idKey);
	}

	/**
	 * 方法说明：saveStringValue
	 * 
	 * @param stringValue
	 *            stringValue
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveStringValue(StringValue stringValue)
	{
		stringValueDao.save(stringValue);
		return 1;
	}

	/**
	 * 方法说明：getStringValuesUnderAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<StringValue> getStringValuesUnderAttribute(Attribute attribute)
	{
		return stringValueDao.getStringValuesUnderAttribute(attribute);
	}

	/**
	 * 方法说明：updateStringValue
	 * 
	 * @param stringValue
	 *            stringValue
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateStringValue(StringValue stringValue)
	{
		stringValueDao.update(stringValue);
		return 1;
	}

	/**
	 * 方法说明：deleteStringValue
	 * 
	 * @param stringValue
	 *            stringValue
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteStringValue(StringValue stringValue)
	{
		stringValueDao.delete(stringValue);
		return 1;
	}

	/**
	 * 方法说明：deleteSyncED
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteSyncED(SyncED syncED)
	{
		syncEDDao.delete(syncED);
		return 1;
	}

	/**
	 * 方法说明：getSyncED
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @return UserPageResult
	 */
	@Transactional(readOnly = true)
	public UserPageResult getSyncED(int page, int pagesize)
	{
		return syncEDDao.getSyncED(page, pagesize);
	}

	@Transactional(readOnly = true)
	public List<SyncED> getSyncEDList()
	{
		return syncEDDao.getSyncEDList();
	}

	/**
	 * 方法说明：saveSyncED
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveSyncED(SyncED syncED)
	{
		syncEDDao.save(syncED);
		return 1;
	}

	/**
	 * 方法说明：updateSyncED
	 * 
	 * @param syncED
	 *            syncED
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateSyncED(SyncED syncED)
	{
		syncEDDao.update(syncED);
		return 1;
	}

	/**
	 * 方法说明：getSyncEDByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return SyncED
	 */
	@Transactional(readOnly = true)
	public SyncED getSyncEDByUuid(String uuid)
	{
		return syncEDDao.getSyncEDByUuid(uuid);
	}

	/**
	 * 方法说明：getAttributeValueByAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeValue> getAttributeValueByAttribute(Attribute attribute)
	{
		return attributeValueDao.getAttributeValuesUnderAttribute(attribute);
	}

	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesUnderResource(Resource resource)
	{
		List<Attribute> list = attributeDao.getAttributesUnderResource(resource);
		return list;
	}

	/**
	 * 方法说明：getAttributeByUuid
	 * 
	 * @param uuid
	 *            uuid
	 * @return Attribute
	 */
	@Transactional(readOnly = true)
	public Attribute getAttributeByUuid(String uuid)
	{
		return attributeDao.getAttributeByUuid(uuid);
	}

	/**
	 * 方法说明：updateAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateAttribute(Attribute attribute)
	{
		attributeDao.update(attribute);
		// Set av = attribute.getAttValues();
		// List<StringValue> avold = stringValueDao
		// .getStringValuesUnderAttribute(attribute);
		// if (avold != null) {
		// for (int i = 0; i < avold.size(); i++) {
		// stringValueDao.delete(avold.get(i));
		// }
		// }
		// if (av != null && av.size() > 0) {
		// java.util.Iterator<AttributeValue> iterator = av.iterator();
		// while (iterator.hasNext()) {
		// stringValueDao.save((StringValue) iterator.next());
		// }
		// }
		return 1;
	}

	/**
	 * 方法说明：saveAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int saveAttribute(Attribute attribute)
	{
		attributeDao.save(attribute);
		return 1;
	}

	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesUnderResource(Resource resource, String catagory)
	{
		return attributeDao.getAttributesUnderResource(resource, catagory);
	}

	/**
	 * 方法说明：getAttributesUnderResourceOnPage
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesUnderResourceOnPage(Resource resource, String catagory)
	{
		return attributeDao.getAttributesUnderResourceOnPage(resource, catagory);
	}

	/**
	 * 方法说明：deleteAttribute
	 * 
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteAttribute(Attribute attribute)
	{
		List<StringValue> list = getStringValuesUnderAttribute(attribute);
		for (StringValue stringValue : list) {
			stringValueDao.delete(stringValue);
		}
		List<AttributeValue> values = getAttributeValueByAttribute(attribute);
		for (AttributeValue value : values) {
			attributeValueDao.delete(value);
		}
		attributeDao.delete(attribute);
		return 1;
	}

	/**
	 * 方法说明：getAttributesByAttributeType
	 * 
	 * @param attributeType
	 *            attributeType
	 * @return attributeType
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesByAttributeType(AttributeType attributeType)
	{
		return attributeDao.getAttributesByAttributeType(attributeType);
	}

	/**
	 * 方法说明：getAttributesUnderResourceAndGroup
	 * 
	 * @param resource
	 *            resource
	 * @param group
	 *            group
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesUnderResourceAndGroup(Resource resource, Group group)
	{
		return attributeDao.getAttributesUnderResourceAndGroup(resource, group);
	}

	/**
	 * 方法说明：getAttributesByAttributeTypeIdKey
	 * 
	 * @param resource
	 *            resource
	 * @param keys
	 *            keys
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesByAttributeTypeIdKey(Resource resource, List<String> keys)
	{
		List<Attribute> attrlist = new ArrayList<Attribute>();
		for (Attribute attribute : attributeDao.getAttributesUnderResource(resource)) {
			for (String key : keys) {
				if (attribute.getType().getId().contains(key)) {
					attrlist.add(attribute);
				}
			}
		}
		return attrlist;
	}

	/**
	 * 方法说明：getAttributesUnderResource
	 * 
	 * @param resource
	 *            resource
	 * @param catagory
	 *            catagory
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesUnderResource(Resource resource, List<String> catagory)
	{
		return attributeDao.getAttributesUnderResource(resource, catagory);
	}

	/**
	 * 方法说明：getResourceListByAttribute
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Resource> getResourceListByAttribute(String attrName, String attrValue)
	{
		return attributeDao.getResourceByAttribute(attrName, attrValue);
	}

	/**
	 * 方法说明：getAttributesByResAndType
	 * 
	 * @param res
	 *            res
	 * @param typeString
	 *            typeString
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesByResAndType(Resource res, String typeString)
	{
		List<AttributeType> atts = attributeTypeDao.getAttributeTypeById(typeString);
		if (CollectionUtils.isEmpty(atts)) {
			return new ArrayList<Attribute>();
		}

		return getAttributesByResAndType(res, atts.get(0));
	}

	/**
	 * 方法说明：getAttributesByResAndType
	 * 
	 * @param res
	 *            res
	 * @param attributeType
	 *            attributeType
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<Attribute> getAttributesByResAndType(Resource res, AttributeType attributeType)
	{
		List<Attribute> atts = attributeDao.getAttributesByResAndType(res, attributeType);
		return atts;
	}

	/**
	 * 方法说明：getUserByAttribute
	 * 
	 * @param attrName
	 *            attrName
	 * @param attrValue
	 *            attrValue
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> getUserByAttribute(String attrName, String attrValue)
	{
		List<User> users = new ArrayList<User>();
		List<Resource> list = attributeDao.getResourceByAttribute(attrName, attrValue);
		if (CollectionUtils.isNotEmpty(list)) {
			for (Resource resource : list) {
				if (resource != null && resource instanceof User) {
					users.add((User) resource);
				}
			}
		}
		return users;
	}

	/**
	 * 方法说明：searchUsersByERPCode
	 * 
	 * @param userCode
	 *            userCode
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> searchUsersByERPCode(String userCode)
	{
		if (StringUtils.isNotBlank(userCode)) {
			return getUserByAttribute("sgccEmployeeCode", userCode);
		} else {
			return null;
		}
	}

	/**
	 * 方法说明：updateResourceAttribute
	 * 
	 * @param res
	 *            res
	 * @param map
	 *            map
	 */
	@Transactional(readOnly = false)
	public void updateResourceAttribute(Resource res, Map<String, String> map)
	{
		for (Map.Entry<String, String> entry : map.entrySet()) {
			AttributeType at = attributeTypeDao.getAttributeTypeById(res, entry.getKey());
			if (at != null) {
				List<Attribute> attList = attributeDao.getAttributesByResAndType(res, at);
				if (CollectionUtils.isEmpty(attList)) {
					Attribute att = new Attribute();
					att.setOwnerResource(res);
					att.setOwnerResourceUUID(res.getUuid());
					att.setType(at);
					att.setTypeUUID(at.getUuid());
					att.setValue(entry.getValue());
					attributeDao.save(att);
				} else {
					for (Attribute att : attList) {
						att.setValue(entry.getValue());
						attributeDao.update(att);
					}
				}
			}
		}
	}

	/**
	 * 方法说明：searchUserByAttribute
	 * 
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<User> searchUserByAttribute(String key, String value)
	{
		List<AttributeType> types = attributeTypeDao.getAttributeTypeById(key);
		if (CollectionUtils.isEmpty(types)) {
			return new ArrayList<User>();
		} else {
			return attributeDao.searchUserByAttribute(types.get(0), value);
		}
	}

	/**
	 * 方法说明：createApplication
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int createApplication(Application application, Attribute attribute)
	{
		applicationDao.save(application);
		attribute.setOwnerResource(application);
		attributeDao.save(attribute);
		return 1;
	}

	/**
	 * 方法说明：deleteApplication
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteApplication(Application application, Attribute attribute)
	{
		attributeDao.delete(attribute);
		applicationDao.delete(application);
		return 1;
	}

	/**
	 * 方法说明：updateApplication
	 * 
	 * @param application
	 *            application
	 * @param attribute
	 *            attribute
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateApplication(Application application, Attribute attribute)
	{
		applicationDao.update(application);
		attribute.setOwnerResource(application);
		attributeDao.update(attribute);
		return 1;
	}

	/**
	 * 方法说明：modifyResourceAttribute
	 * 
	 * @param loginUser
	 *            loginUser
	 * @param res
	 *            res
	 * @param key
	 *            key
	 * @param value
	 *            value
	 */
	@Transactional(readOnly = false)
	public void modifyResourceAttribute(User loginUser, Resource res, String key, String value)
	{

		List<AttributeType> types = this.getAttributeTypeById(key);

		if (CollectionUtils.isEmpty(types)) {
			return;
		}
		AttributeType type = types.get(0);

		modifyResourceAttribute(loginUser, res, type, value);
	}

	/**
	 * 方法说明：modifyResourceAttribute
	 * 
	 * @param loginUser
	 *            loginUser
	 * @param res
	 *            res
	 * @param type
	 *            type
	 * @param value
	 *            value
	 */
	@Transactional(readOnly = false)
	public void modifyResourceAttribute(User loginUser, Resource res, AttributeType type,
			String value)
	{

		List<Attribute> atts = getAttributesByResAndType(res, type);

		if (CollectionUtils.isEmpty(atts)) {
			Attribute att = new Attribute(res, type);
			if (StringUtils.isNotBlank(value)) {
				att.setValue(value);
				saveAttribute(att);
			}
		} else {
			for (Attribute att : atts) {
				if (!StringUtils.equals(att.getValue(), value)) {
					att.setValue(value);
					updateAttribute(att);
				}
			}
		}

	}

	/**
	 * 方法说明：modifyResourceAttribute
	 * 
	 * @param res
	 *            res
	 * @param key
	 *            key
	 * @param value
	 *            value
	 * @return Map
	 */
	@Transactional(readOnly = false)
	public Map<String, String[]> modifyResourceAttribute(Resource res, String key, String value)
	{
		List<AttributeType> types = this.getAttributeTypeById(key);

		if (CollectionUtils.isEmpty(types)) {
			return new HashMap<String, String[]>();
		}
		AttributeType type = types.get(0);

		return modifyResourceAttribute(res, type, value);

	}

	/**
	 * 方法说明：modifyResourceAttribute
	 * 
	 * @param res
	 *            res
	 * @param type
	 *            type
	 * @param value
	 *            value
	 * @return Map
	 */
	@Transactional(readOnly = false)
	public Map<String, String[]> modifyResourceAttribute(Resource res, AttributeType type,
			String value)
	{
		Map<String, String[]> map = new HashMap<String, String[]>();
		List<Attribute> atts = getAttributesByResAndType(res, type);

		if (CollectionUtils.isEmpty(atts)) {
			Attribute att = new Attribute(res, type);
			if (StringUtils.isNotBlank(value)) {
				att.setValue(value);
				saveAttribute(att);
				map.put(type.getId(), new String[] { null, value });
			}
		} else {
			for (Attribute att : atts) {
				if (!StringUtils.equals(att.getValue(), value)) {
					map.put(type.getId(), new String[] { att.getValue(), value });
					att.setValue(value);
					updateAttribute(att);
				}
			}
		}
		return map;

	}

	/**
	 * 方法说明：saveResourceLog
	 * 
	 * @param user
	 *            user
	 * @param res
	 *            res
	 * @param logid
	 *            logid
	 * @param beforeValue
	 *            beforeValue
	 * @param afterValue
	 *            afterValue
	 * @param remark
	 *            remark
	 */
	@Transactional(readOnly = false)
	public void saveResourceLog(User user, Resource res, String logid, String beforeValue,
			String afterValue, String remark)
	{
		ResourceLog log = new ResourceLog();
		log.setAfterValue(afterValue);
		log.setBeforeValue(beforeValue);
		log.setEditDate(Calendar.getInstance().getTime());
		if (user != null) {
			log.setEditPerson(user.getName());
		}
		switch (res.getType()) {
		case 3:
			log.setFieldName(((Application) res).getName());
			break;
		case 2:
			log.setFieldName(((Department) res).getName());
			break;
		case 1:
			log.setFieldName(((Group) res).getName());
			break;
		case 0:
			log.setFieldName(((User) res).getName());
			break;
		default:
			log.setFieldName(res.getUuid());
			break;
		}
		if (res.getType().equals(0)) {
			log.setFieldName(((User) res).getName());
		}
		if (res.getType().equals(1)) {
			log.setFieldName(((User) res).getName());
		}
		log.setLogid(logid);
		log.setRemark(remark);
		log.setResourceuuid(res.getUuid());
		saveResourceLog(log);
	}

	/**
	 * 方法说明： getAttributeTypeByKeySet
	 * 
	 * @param keySet
	 *            keySet
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<AttributeType> getAttributeTypeByKeySet(Set<String> keySet)
	{
		List<AttributeType> ats = new ArrayList<AttributeType>();
		for (String string : keySet) {
			ats.addAll(getAttributeTypeById(string));
		}
		return ats;
	}

	/**
	 * 方法说明：isAuthorForTaskList
	 * 
	 * @param uuid
	 *            uuid
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean isAuthorForTaskList(String uuid)
	{
		TaskList tasklist = taskListDao.getTaskListByUuid(uuid);
		User loginUser = this.getLoginUser();
		Set<Group> adminGroup = tasklist.getAdminGroups();
		if (CollectionUtils.isNotEmpty(adminGroup)) {
			return CollectionUtils.containsAny(adminGroup, getUserGroups(loginUser));
		}
		return false;
	}

	// Added by Guo Tianci, -start 2011 -08 -12 --start
	/**
	 * 方法说明：countSubDepartment
	 * 
	 * @param dept
	 *            dept
	 * @return long
	 */
	@Transactional(readOnly = true)
	public long countSubDepartment(Department dept)
	{

		if (dept == null) {
			return 0;
		}

		return userDao.getSubDeptCount(dept);

	}

	/**
	 * 方法说明：hasSubDepartment
	 * 
	 * @param dept
	 *            dept
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean hasSubDepartment(Department dept)
	{
		return countSubDepartment(dept) != 0;
	}

	// --end
	/**
	 * 方法说明：addDepartmentManagerGroups
	 * 
	 * @param department
	 *            department
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addDepartmentManagerGroups(Department department, List<Group> admGroups)
	{

		for (Group adminGroup : admGroups) {
			resourceAdminGroupDao.add(adminGroup, department);
		}
		// if (department.getAdminGroups() == null) {
		// Set<Group> setGroup = new HashSet<Group>();
		// setGroup.addAll(admGroups);
		// department.setAdminGroups(setGroup);
		// } else {
		// department.getAdminGroups().addAll(admGroups);
		// }
		// departmentDao.update(department);

		return 1;
	}

	/**
	 * 方法说明：addUserManagerGroups
	 * 
	 * @param user
	 *            user
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addUserManagerGroups(User user, List<Group> admGroups)
	{
		for (Group group : admGroups) {
			ResourceAdminGroup rag = new ResourceAdminGroup();
			rag.setGroup(group);
			rag.setResource(user);
			resourceAdminGroupDao.saveOrUpdate(rag);
		}
		return 1;
	}

	/**
	 * 方法说明：updateDeartmentManagerGroups
	 * 
	 * @param department
	 *            department
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateDeartmentManagerGroups(Department department, List<Group> admGroups)
	{

		resourceAdminGroupDao.removeByResource(department);

		for (Group adminGroup : admGroups) {
			resourceAdminGroupDao.add(adminGroup, department);
		}
		return 1;
	}

	/**
	 * 方法说明：updateUserManagerGroups
	 * 
	 * @param user
	 *            user
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateUserManagerGroups(User user, List<Group> admGroups)
	{
		resourceAdminGroupDao.removeByResource(user);

		for (Group adminGroup : admGroups) {
			resourceAdminGroupDao.add(adminGroup, user);
		}
		return 1;
	}

	/**
	 * 方法说明：addGroupManagerGroups
	 * 
	 * @param group
	 *            group
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addGroupManagerGroups(Group group, List<Group> admGroups)
	{
		for (Group admin : admGroups) {
			ResourceAdminGroup rag = new ResourceAdminGroup();
			rag.setGroup(admin);
			rag.setResource(group);
			resourceAdminGroupDao.saveOrUpdate(rag);
		}
		return 1;
	}

	/**
	 * 方法说明：updateGroupManagerGroups
	 * 
	 * @param group
	 *            group
	 * @param admGroups
	 *            admGroups
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int updateGroupManagerGroups(Group group, List<Group> admGroups)
	{

		resourceAdminGroupDao.removeByResource(group);

		return addGroupManagerGroups(group, admGroups);
	}

	/**
	 * 方法说明：addUserManagerUnderGroup
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addUserManagerUnderGroup(Group group, User user)
	{
		resourceAdminGroupDao.add(group, user);
		return 1;
	}

	/**
	 * 方法说明：deleteUserManagedUnderGroup
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int deleteUserManagedUnderGroup(Group group, User user)
	{
		resourceAdminGroupDao.remove(group, user);
		return 1;
	}

	/**
	 * 方法说明：existResourceManagedUnderGroup
	 * 
	 * @param resource
	 *            resource
	 * @param adminGroup
	 *            adminGroup
	 * @return boolean
	 */
	private boolean existResourceManagedUnderGroup(Resource resource, Group adminGroup)
	{
		List<Group> groups = resourceAdminGroupDao.getAdminGroups(resource);

		if (CollectionUtils.isEmpty(groups)) {
			return false;
		}

		return groups.contains(adminGroup);
	}

	/**
	 * 方法说明：existDepartmentManagedUnderGroup
	 * 
	 * @param department
	 *            department
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existDepartmentManagedUnderGroup(Department department, Group group)
	{
		return existResourceManagedUnderGroup(department, group);
	}

	/**
	 * 方法说明：existGroupManagedUnderGroup
	 * 
	 * @param managedGroup
	 *            managedGroup
	 * @param group
	 *            group
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existGroupManagedUnderGroup(Group managedGroup, Group group)
	{
		return existResourceManagedUnderGroup(managedGroup, group);
		// return managedGroup.getAdminGroups().contains(group);
	}

	/**
	 * 方法说明：existUserManagerUnderGroup
	 * 
	 * @param group
	 *            group
	 * @param user
	 *            user
	 * @return boolean
	 */
	@Transactional(readOnly = true)
	public boolean existUserManagerUnderGroup(Group group, User user)
	{
		return existResourceManagedUnderGroup(user, group);
		// return user.getAdminGroups().contains(group);
	}

	/**
	 * 方法说明：addDepartmentManagedUnderGroup
	 * 
	 * @param department
	 *            department
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addDepartmentManagedUnderGroup(Department department, Group group)
	{
		resourceAdminGroupDao.add(group, department);
		// department.getAdminGroups().add(group);
		// departmentDao.update(department);
		return 1;
	}

	/**
	 * 方法说明：addGroupManagedUnderGroup
	 * 
	 * @param managedGroup
	 *            managedGroup
	 * @param managerGroup
	 *            managerGroup
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int addGroupManagedUnderGroup(Group managedGroup, Group managerGroup)
	{
		List<Group> adminGroups = resourceAdminGroupDao.getAdminGroups(managedGroup);
		if (!adminGroups.contains(managerGroup)) {
			resourceAdminGroupDao.add(managerGroup, managedGroup);
		}
		return 1;
	}

	/**
	 * 方法说明：removeDepartmentManagedUnderGroup
	 * 
	 * @param department
	 *            department
	 * @param group
	 *            group
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeDepartmentManagedUnderGroup(Department department, Group group)
	{
		List<Group> adminGroups = resourceAdminGroupDao.getAdminGroups(department);
		if (adminGroups.contains(group)) {
			resourceAdminGroupDao.remove(group, department);
			// department.getAdminGroups().remove(group);
		}
		// departmentDao.update(department);
		return 1;
	}

	/**
	 * 方法说明：removeGroupManagedUnderGroup
	 * 
	 * @param managedGroup
	 *            managedGroup
	 * @param managerGroup
	 *            managerGroup
	 * @return int
	 */
	@Transactional(readOnly = false)
	public int removeGroupManagedUnderGroup(Group managedGroup, Group managerGroup)
	{
		List<Group> adminGroups = resourceAdminGroupDao.getAdminGroups(managedGroup);
		if (adminGroups.contains(managerGroup)) {
			resourceAdminGroupDao.remove(managerGroup, managedGroup);
		}
		return 1;
	}

	/**
	 * 方法说明：deleteUserAdminGroups
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	@Transactional(readOnly = false)
	public void deleteUserAdminGroups(User user, Collection<Group> collection)
	{
		for (Group group : collection) {
			ResourceAdminGroup userGroup = new ResourceAdminGroup();
			userGroup.setGroup(group);
			userGroup.setResource(user);
			resourceAdminGroupDao.delete(userGroup);
		}
	}

	/**
	 * 方法说明：addUserAdminGroups
	 * 
	 * @param user
	 *            user
	 * @param collection
	 *            collection
	 */
	@Transactional(readOnly = false)
	public void addUserAdminGroups(User user, Collection<Group> collection)
	{
		for (Group group : collection) {
			ResourceAdminGroup userGroup = new ResourceAdminGroup();
			userGroup.setGroup(group);
			userGroup.setResource(user);
			resourceAdminGroupDao.save(userGroup);
		}
	}
	public Set<Group> getAttributeTypeManagedGroups(AttributeType type)
	{
		if(type==null){
			return null;
		}
		return attributeTypeDao.getAdminGroupsByUuid(type.getUuid());
	}

	public Set<Group> getAttributeTypeGroups(AttributeType type)
	{
		if(type==null){
			return null;
		}
		return attributeTypeDao.getGroupsByUuid(type.getUuid());
	}
}
