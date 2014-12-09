package com.wt.uum2.event;

/**
 * @author LiuYX
 *
 */
public enum EventType
{

	/**
	 * 
	 */
	ADD_USER_GROUP, // over addUserToGroup
	/**
	 * 
	 */
	REMOVE_USER_GROUP, // over removeUserFromGroup
	/**
	 * 
	 */
	ADD_GROUP_GROUP, // over addGroupToGroup
	/**
	 * 
	 */
	REMOVE_GROUP_GROUP, // over removeGroupFromGroup
	/**
	 * 
	 */
	DELETE_GROUP, // over deleteGroup
	/**
	 * 
	 */
	CREATE_GROUP, // over createGroup
	/**
	 * 
	 */
	UPDATE_GROUP, // over updateGroup
	/**
	 * 
	 */
	DELETE_USER, // over deleteUser
	/**
	 * 
	 */
	CREATE_USER, // over createUser
	/**
	 * 
	 */
	UPDATE_USER, // over updateUser
	/**
	 * 
	 */
	DELETE_DEPAREMENT, // over deleteDept
	/**
	 * 
	 */
	CREATE_DEPAREMENT, // over createDept
	/**
	 * 
	 */
	UPDATE_DEPAREMENT, // over updateDept
	/**
	 * 
	 */
	ADD_USER_DEPAREMENT, // over addUserToDept
	/**
	 * 
	 */
	REMOVE_USER_DEPAREMENT, // over removeUserFromDept
	/**
	 * 
	 */
	USER_APP_LOGIN_ENABLED,
	/**
	 * 
	 */
	USER_APP_LOGIN_DISABLED

}
