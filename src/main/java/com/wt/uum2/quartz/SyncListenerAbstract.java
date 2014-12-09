package com.wt.uum2.quartz;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.domain.Attribute;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.DepartmentTempLog;
import com.wt.uum2.domain.Event;
import com.wt.uum2.domain.User;
import com.wt.uum2.domain.UserTempLog;
import com.wt.uum2.event.EventFactory;
import com.wt.uum2.event.EventListenerHandler;
import com.wt.uum2.service.UUMDataService;
import com.wt.uum2.service.UUMService;

/**
 * <pre>
 * 业务名:反向同步的抽象实现
 * 功能说明: 
 * 编写日期:	2012-12-19
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public abstract class SyncListenerAbstract implements SyncListener
{
	/**
	 * 
	 */
	protected final Log logger = LogFactory.getLog(getClass());

	/**
	 * 
	 */
	protected static final String OPERATOR = "中间表信息维护";

	/**
	 * 
	 */
	private UUMDataService uumDataService;

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

	public void setEventFactory(EventFactory eventFactory)
	{
		this.eventFactory = eventFactory;
	}

	public void setEventListenerHandler(EventListenerHandler eventListenerHandler)
	{
		this.eventListenerHandler = eventListenerHandler;
	}

	public void setUumService(UUMService uumService)
	{
		this.uumService = uumService;
	}

	public void setUumDataService(UUMDataService uumDataService)
	{
		this.uumDataService = uumDataService;
	}

	/**
	 * @return ssssssss
	 */
	public UUMDataService getUumDataService()
	{
		return uumDataService;
	}

	/**
	 * @return ssssssss
	 */
	public UUMService getUumService()
	{
		return uumService;
	}

	/**
	 * @return ssssssss
	 */
	public EventFactory getEventFactory()
	{
		return eventFactory;
	}

	/**
	 * @return ssssssss
	 */
	public EventListenerHandler getEventListenerHandler()
	{
		return eventListenerHandler;
	}

	/**
	 * 方法说明：入口
	 * 
	 */
	public abstract void handle();

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#DepartmentSyncFrom()
	 */
	/**
	 * 方法说明：DepartmentSyncFrom
	 * 
	 */
	public void DepartmentSyncFrom()
	{
		List<DepartmentTempLog> nulist = uumDataService.getModifyDeptTemp();

		if (CollectionUtils.isNotEmpty(nulist)) {
			DepartmentSyncFrom(nulist);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#UserSyncFrom()
	 */
	/**
	 * 方法说明：UserSyncFrom
	 * 
	 */
	public void UserSyncFrom()
	{
		List<UserTempLog> nulist = uumDataService.getModifyUserTemp();
		if (CollectionUtils.isNotEmpty(nulist)) {
			UserSyncFrom(nulist);
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#DepartmentSyncFrom(java.util.List)
	 */
	/**
	 * 方法说明：DepartmentSyncFrom
	 * 
	 * @param depts
	 *            depts
	 */
	public void DepartmentSyncFrom(List<DepartmentTempLog> depts)
	{
		for (DepartmentTempLog dept : depts) {
			if (dept.isDelete()) {
				deleteDepartmentHandle(dept);
			} else {
				if (uumService.existDepartmentCode(dept.getDeptCode())) {
					updateDepartmentHandle(dept);
				} else {
					createDepartmentHandle(dept);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#UserSyncFrom(java.util.List)
	 */
	/**
	 * 方法说明：UserSyncFrom
	 * 
	 * @param users
	 *            users
	 */
	public void UserSyncFrom(List<UserTempLog> users)
	{
		for (UserTempLog user : users) {
			if (user.isDelete()) {
				deleteUserHandle(user);
			} else {
				if (uumService.existUserId(user.getUserId())) {
					updateUserHandle(user);
				} else {
					createUserHandle(user);
				}
			}
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#createDepartmentHandle(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：createDepartmentHandle
	 * 
	 * @param deptTemp
	 *            deptTemp
	 * @return Department
	 */
	public Department createDepartmentHandle(DepartmentTempLog deptTemp)
	{
		if (uumService.existDepartmentCode(deptTemp.getDeptCode())) {
			logger.warn("要创建的部门(" + deptTemp.getDeptCode() + ")已经存在!");
			return updateDepartmentHandle(deptTemp);
		}
		Department dept = new Department();
		Department parent = uumService.getDepartmentByDeptCode(deptTemp.getParentDeptCode());
		if (parent == null) {
			deptTemp.setModifyStatus(Constants.FAILED);
			deptTemp.setRemark("父部门(" + deptTemp.getParentDeptCode() + ")不存在!");
			uumDataService.updateDeptTemp(deptTemp);
			return null;
		}
		try {
			dept.setCode(deptTemp.getDeptCode());
			dept.setDeptCode(deptTemp.getDeptCode());
			dept.setHasChildren(true);
			dept.setName(deptTemp.getDeptName());
			dept.setOrgCode(getDeptOrgCode(deptTemp));
			dept.setParent(parent);
			dept.setStatus(ResourceStatus.NORMAL.intValue());
			uumService.createDepartment(dept);

			Event event = eventFactory.createEventCreateDept(dept.getUuid());
			event.setOperatorName(OPERATOR);
			eventListenerHandler.handle(event);
			deptTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "创建部门(" + deptTemp.getDeptName() + ":" + deptTemp.getDeptCode()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			deptTemp.setModifyStatus(Constants.FAILED);
			deptTemp.setRemark(msg);
		} finally {
			uumDataService.updateDeptTemp(deptTemp);
		}
		return dept;
	}

	/**
	 * 方法说明：获取公司编码
	 * 
	 * @param deptTemp
	 *            deptTemp
	 * @return String
	 */
	protected abstract String getDeptOrgCode(DepartmentTempLog deptTemp);

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#updateDepartmentHandle(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：updateDepartmentHandle
	 * 
	 * @param deptTemp
	 *            deptTemp
	 * @return Department
	 */
	public Department updateDepartmentHandle(DepartmentTempLog deptTemp)
	{
		Department dept = uumService.getDepartmentByDeptCode(deptTemp.getDeptCode());
		if (dept == null) {
			logger.warn("要更新的部门(" + deptTemp.getDeptCode() + ")不存在!");
			return createDepartmentHandle(deptTemp);
		}
		Department parent = uumService.getDepartmentByDeptCode(deptTemp.getParentDeptCode());
		if (parent == null) {
			deptTemp.setModifyStatus(Constants.FAILED);
			deptTemp.setRemark("父部门(" + deptTemp.getParentDeptCode() + ")不存在");
			uumDataService.updateDeptTemp(deptTemp);
			return null;
		}
		try {
			Map<String, String[]> map = new HashMap<String, String[]>();
			dept.setLastUpdateTime(Calendar.getInstance().getTime());
			if (!StringUtils.equals(deptTemp.getDeptName(), dept.getName())) {
				map.put("name", new String[] { dept.getName(), deptTemp.getDeptName() });
				dept.setName(deptTemp.getDeptName());
			}
			if (!StringUtils.equals(dept.getOrgCode(), getDeptOrgCode(deptTemp))) {
				map.put("orgCode", new String[] { dept.getOrgCode(), getDeptOrgCode(deptTemp) });
				dept.setOrgCode(getDeptOrgCode(deptTemp));
			}

			dept.setStatus(ResourceStatus.NORMAL.intValue());
			if (!parent.isHasChildren()) {
				parent.setHasChildren(true);
				uumService.updateDepartment(parent);
			}
			if (!StringUtils.equals(dept.getParentUUID(), parent.getUuid())) {
				map.put("parentUUID", new String[] { dept.getParentUUID(), parent.getUuid() });
				uumService.moveDepartmentToNewParent(dept, parent);
				dept.setParent(parent);
			}

			String oldPath = dept.getPath();
			String newPath = parent.getPath() + "→" + dept.getName();
			if (!StringUtils.equals(oldPath, newPath)) {
				uumService.updateDeptPath(newPath, oldPath);
				dept.setPath(newPath);
			}
			uumService.updateDepartment(dept);
			if (!map.isEmpty()) {
				Event event = eventFactory.createEventUpdateDept(dept.getUuid(), map);
				event.setOperatorName(OPERATOR);
				eventListenerHandler.handle(event);
			}
			deptTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "更新部门(" + deptTemp.getDeptName() + ":" + deptTemp.getDeptCode()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			deptTemp.setRemark(msg);
			deptTemp.setModifyStatus(Constants.FAILED);
		} finally {
			uumDataService.updateDeptTemp(deptTemp);
		}
		return dept;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#deleteDepartmentHandle(com.wt.uum2.domain.DepartmentTempLog)
	 */
	/**
	 * 方法说明：deleteDepartmentHandle
	 * 
	 * @param deptTemp
	 *            deptTemp
	 * @return Department
	 */
	public Department deleteDepartmentHandle(DepartmentTempLog deptTemp)
	{
		Department dept = uumService.getDepartmentByDeptCode(deptTemp.getDeptCode());

		if (dept == null) {
			deptTemp.setModifyStatus(Constants.SUCCESSFUL);
			deptTemp.setRemark("待删除部门(" + deptTemp.getDeptName() + ":" + deptTemp.getDeptCode()
					+ ")不存在");
			uumDataService.updateDeptTemp(deptTemp);
			return null;
		}

		try {
			Event event = eventFactory.createEventDeleteDept(dept.getUuid());
			uumService.deleteDepartment(dept);
			event.setOperatorName(OPERATOR);
			eventListenerHandler.handle(event);
			deptTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "删除部门(" + deptTemp.getDeptName() + ":" + deptTemp.getDeptCode()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			deptTemp.setRemark(msg);
			deptTemp.setModifyStatus(Constants.FAILED);
		} finally {
			uumDataService.updateDeptTemp(deptTemp);
		}
		return dept;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#createUserHandle(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：createUserHandle
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return User
	 */
	public User createUserHandle(UserTempLog userTemp)
	{
		if (uumService.existUserId(userTemp.getUserId())) {
			logger.warn("要创建的用户(" + userTemp.getUserName() + ":" + userTemp.getUserId() + ")已存在!");
			return updateUserHandle(userTemp);
		}
		User user = new User();
		try {
			Department department = getPrimaryDepartment(userTemp.getDeptCode());
			user.addDept(department);
			user.setId(userTemp.getUserId());
			user.setName(userTemp.getUserName() == null ? "无姓名" : userTemp.getUserName());
			user.setPrimaryDepartment(department);
			user.setStatus(getUserStatus(userTemp).intValue());
			user.setPlainPassword(getPlainPassword(userTemp));
			uumService.createUser(user);

			for (Map.Entry<String, String> entry : getTempAttrTypes(userTemp).entrySet()) {
				uumService.modifyResourceAttribute(user, entry.getKey(), entry.getValue());
			}
			Event event = eventFactory.createEventCreateUser(user.getUuid());
			event.setOperatorName(OPERATOR);
			eventListenerHandler.handle(event);
			userTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "创建用户(" + userTemp.getUserName() + ":" + userTemp.getUserId()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			userTemp.setRemark(msg);
			userTemp.setModifyStatus(Constants.FAILED);
		} finally {
			uumDataService.updateUserTemp(userTemp);
		}
		return user;
	}

	/**
	 * 方法说明：获取部门信息，如果获取失败则将根节点作为部门返回
	 * 
	 * @param deptCode
	 *            deptCode
	 * @return Department
	 */
	protected abstract Department getPrimaryDepartment(String deptCode);

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#updateUserHandle(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：updateUserHandle
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return User
	 */
	public User updateUserHandle(UserTempLog userTemp)
	{
		User user = uumService.getUserByUserId(userTemp.getUserId());
		if (user == null) {
			logger.warn("要更新的用户(" + userTemp.getUserName() + ":" + userTemp.getUserId() + ")不存在!");
			return createUserHandle(userTemp);
		}

		Map<String, String[]> map = new HashMap<String, String[]>();
		List<Event> events = new ArrayList<Event>();
		try {
			Department newDept = getPrimaryDepartment(userTemp.getDeptCode());
			if (newDept == null) {
				logger.warn("要更新的用户(" + userTemp.getUserName() + ":" + userTemp.getUserId()
						+ ")所在部门(" + userTemp.getDeptName() + ":" + userTemp.getDeptCode()
						+ ")不存在!");
				newDept = uumService.getDepartmentRoot();
			}
			Department oldDept = uumService.getUserPrimaryDepartment(user);
			List<Department> depts = uumService.getUserDepartments(user);

			Set<Department> depts1 = new HashSet<Department>();

			for (Department department : depts) {
				depts1.add(department);
			}

			user.setDepartments(depts1);
			if (!oldDept.equals(newDept)) {
				events.add(eventFactory.createUserEventRemoveDepartment(user.getUuid(),
						user.getPrimaryDepartmentUUID()));
				events.add(eventFactory.createUserEventAddDepartment(user.getUuid(),
						newDept.getUuid()));
				user.removeDept(oldDept);
				user.addDept(newDept);
				user.setPrimaryDepartment(newDept);
			}
			if (!StringUtils.equals(userTemp.getUserName(), user.getName())) {
				map.put("name", new String[] { user.getName(), userTemp.getUserName() });
				user.setName(StringUtils.defaultIfEmpty(userTemp.getUserName(), user.getName()));
			}
			user.setLastUpdateTime(Calendar.getInstance().getTime());
			user.setStatus(getUserStatus(userTemp).intValue());

			if (needSyncUserPassword()
					&& !StringUtils.equals(user.getPlainPassword(), getPlainPassword(userTemp))) {
				map.put("userPassword", new String[] { user.getPlainPassword(),
						getPlainPassword(userTemp) });
				user.setPlainPassword(getPlainPassword(userTemp));
			}
			uumService.updateUser(user);

			for (Map.Entry<String, String> entry : getTempAttrTypes(userTemp).entrySet()) {
				map.putAll(uumService.modifyResourceAttribute(user, entry.getKey(),
						entry.getValue()));
			}

			if (!map.isEmpty()) {
				events.add(eventFactory.createEventUpdateUser(user.getUuid(), map));
			}
			if (!events.isEmpty()) {
				for (Event event : events) {
					event.setOperatorName(OPERATOR);
				}
				eventListenerHandler.handle(events);
			}
			userTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "更新用户(" + userTemp.getUserName() + ":" + userTemp.getUserId()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			userTemp.setRemark(msg);
			userTemp.setModifyStatus(Constants.FAILED);
		} finally {
			uumDataService.updateUserTemp(userTemp);
		}
		return user;
	}

	/**
	 * 方法说明：是否同步用户密码的开关，默认是true
	 * 
	 * @return
	 */
	protected boolean needSyncUserPassword()
	{
		return true;
	}

	/**
	 * 方法说明：getTempAttrTypes
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return Map
	 */
	private Map<String, String> getTempAttrTypes(UserTempLog userTemp)
	{
		Map<String, String> map = new HashMap<String, String>();

		try {
			for (Entry<String, Object> entry : PackageAttributeUtils.packageAttribute(userTemp,
					getPropertieFileName()).entrySet()) {
				map.put(entry.getKey(), (String) entry.getValue());
			}
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 方法说明：getPropertieFileName
	 * 
	 * @return String
	 */
	protected abstract String getPropertieFileName();

	/* (non-Javadoc)
	 * @see com.wt.uum2.quartz.SyncListener#deleteUserHandle(com.wt.uum2.domain.UserTempLog)
	 */
	/**
	 * 方法说明：deleteUserHandle
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return User
	 */
	public User deleteUserHandle(UserTempLog userTemp)
	{
		User user = uumService.getUserByUserId(userTemp.getUserId());

		if (user == null) {
			userTemp.setModifyStatus(Constants.SUCCESSFUL);
			userTemp.setRemark("待删除用户(" + userTemp.getDeptName() + ":" + userTemp.getDeptCode()
					+ ")不存在");
			uumDataService.updateUserTemp(userTemp);
			return null;
		}

		try {
			Event event = eventFactory.createEventDeleteUser(user.getUuid());
			List<Attribute> atts = uumService.getAttributesUnderResource(user);
			for (Attribute attribute : atts) {
				uumService.deleteAttribute(attribute);
			}
			uumService.deleteUserWL(user);
			event.setOperatorName(OPERATOR);
			eventListenerHandler.handle(event);
			userTemp.setModifyStatus(Constants.SUCCESSFUL);
		} catch (Exception e) {
			String msg = "删除部门(" + userTemp.getDeptName() + ":" + userTemp.getDeptCode()
					+ ")异常请检查日志(" + Calendar.getInstance().getTime() + ")";
			logger.error(msg, e);
			userTemp.setRemark(msg);
			userTemp.setModifyStatus(Constants.FAILED);
			user = null;
		} finally {
			uumDataService.updateUserTemp(userTemp);
		}
		return user;
	}

	/**
	 * 方法说明：忽略信息
	 * 
	 * @param newUser
	 *            newUser
	 */
	public void ignoreUserTemp(UserTempLog newUser)
	{
		if (newUser.getModifyStatus() != Constants.SUCCESSFUL) {
			newUser.setModifyStatus(Constants.IGNORABLE);
			uumDataService.updateUserTemp(newUser);
		}
	}

	/**
	 * 方法说明：忽略信息
	 * 
	 * @param deptTemp
	 *            deptTemp
	 */
	public void ignoreDepartmentTemp(DepartmentTempLog deptTemp)
	{
		if (deptTemp.getModifyStatus() != Constants.SUCCESSFUL) {
			deptTemp.setModifyStatus(Constants.IGNORABLE);
			uumDataService.updateDeptTemp(deptTemp);
		}
	}

	/**
	 * 方法说明：忽略信息
	 * 
	 * @param users
	 *            users
	 */
	public void ignoreUserTemp(List<UserTempLog> users)
	{
		if (CollectionUtils.isNotEmpty(users)) {
			for (UserTempLog newUser : users) {
				ignoreUserTemp(newUser);
			}
		}
	}

	/**
	 * 方法说明：忽略信息
	 * 
	 * @param depts
	 *            depts
	 */
	public void ignoreDepartmentTemp(List<DepartmentTempLog> depts)
	{
		if (CollectionUtils.isNotEmpty(depts)) {
			for (DepartmentTempLog deptTemp : depts) {
				ignoreDepartmentTemp(deptTemp);
			}
		}
	}

	/**
	 * 方法说明：获取用户状态
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return ResourceStatus
	 */
	protected abstract ResourceStatus getUserStatus(UserTempLog userTemp);

	/**
	 * 方法说明：获取用户密码，如果password为空则返回默认密码
	 * 
	 * @param userTemp
	 *            userTemp
	 * @return String
	 */
	public String getPlainPassword(UserTempLog userTemp)
	{
		if (StringUtils.isNotBlank(userTemp.getPassword())) {
			return new String(Base64.decodeBase64(userTemp.getPassword().getBytes()));
		}
		return InitParameters.getUserPassword();
	}
}
