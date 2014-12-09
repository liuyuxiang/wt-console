package com.wt.uum2.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.wt.uum2.dao.AttributeDao;
import com.wt.uum2.dao.UserDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.User;
import com.wt.uum2.service.UserListService;
import com.wt.uum2.userlist.UserCol;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-9
 * 作者:	LiuYX
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@Transactional
public class UserListServiceImpl implements UserListService
{

	/**
	 * 
	 */
	private List<UserCol> cols;

	/**
	 * 
	 */
	private Locale defaultLocale;

	/**
	 * 
	 */
	private MessageSource messageSource;

	/**
	 * 
	 */
	private int startNum;

	/**
	 * 
	 */
	private UserDao userDao;

	/**
	 * 
	 */
	private AttributeDao attributeDao;

	/**
	 * 
	 */
	public UserListServiceImpl()
	{
		super();
		startNum = 0;
	}

	public List<UserCol> getCols()
	{
		return cols;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UserListService#getDatas(java.util.List, int)
	 */
	/**
	 * 方法说明：getDatas
	 * 
	 * @param users
	 *            users
	 * @param nowNum
	 *            nowNum
	 * @return Map
	 */
	@Transactional(readOnly = true)
	public Map<String, Map<String, Object>> getDatas(List<User> users, int nowNum)
	{

		Map<String, Map<String, Object>> result = new LinkedHashMap<String, Map<String, Object>>(
				users.size());
		for (int c = 0; c < users.size(); c++) {
			User user = users.get(c);
			result.put(user.getUuid(), getRowDatas(user, c + nowNum));
		}
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UserListService#getHeaders()
	 */
	/**
	 * 方法说明：getHeaders
	 * 
	 * @return List
	 */
	@Transactional(readOnly = true)
	public List<String> getHeaders()
	{
		List<String> headers = new ArrayList<String>();
		for (UserCol col : cols) {
			headers.add(getMessage("colName_" + col.toString()));
		}
		return headers;
	}

	/**
	 * 方法说明：getMessage
	 * 
	 * @param key
	 *            key
	 * @return String
	 */
	@Transactional(readOnly = true)
	private String getMessage(String key)
	{
		String message = null;
		if (defaultLocale != null) {
			message = messageSource.getMessage(key, null, defaultLocale);
		} else {
			message = messageSource.getMessage(key, null, Locale.getDefault());
		}
		return message;
	}

	/**
	 * 方法说明：getRowDatas
	 * 
	 * @param user
	 *            user
	 * @param position
	 *            position
	 * @return Map
	 */
	@Transactional(readOnly = true)
	private Map<String, Object> getRowDatas(User user, int position)
	{
		Map<String, Object> rowDatas = new HashMap<String, Object>(cols.size() + 1);

		rowDatas.put("userObject", user);

		for (UserCol col : cols) {

			Object value = null;
			Set<Department> departments = user.getDepartments();

			switch (col) {
			case num:
				value = startNum + position;
				break;
			case userID:
				value = user.getId();
				break;
			case userName:
				value = user.getName();
				break;
			case relatedUser:
				List<User> rUsers = userDao.getRelationUsers(user);
				StringBuilder valueStrBuiilder = new StringBuilder();
				if (rUsers.size() == 0) {
					valueStrBuiilder.append("　");
				} else {
					for (int c = 0; c < rUsers.size(); c++) {
						User rUser = rUsers.get(c);
						valueStrBuiilder.append(rUser.getId());
						if (c != (rUsers.size() - 1)) {
							valueStrBuiilder.append(",");
						}
					}
				}
				value = valueStrBuiilder.toString();
				break;
			case orderNum:
				value = user.getOrder();
				break;
			case erpCode:
				String key = "sgccEmployeeCode";
				Map<String, String> m = userDao.getAttributesMapByResource(user);
				if (MapUtils.isNotEmpty(m) && StringUtils.isNotBlank(m.get(key))) {
					value = m.get(key).toString();
				} else {
					value = String.valueOf("");
				}
				break;
			case deptErpCode:
				StringBuilder sb = new StringBuilder();
				if (departments.size() == 0) {
					sb.append("&nbsp;");
				} else {
					List<Department> deptList = new ArrayList<Department>();
					deptList.addAll(departments);

					for (int c = 0; c < deptList.size(); c++) {
						Department dept = deptList.get(c);
						sb.append(dept.getCode());
						if (c != (deptList.size() - 1)) {
							sb.append(",");
						}
					}
				}
				value = sb.toString();
				break;
			case userDept:
				StringBuilder deptValueStrBuiilder = new StringBuilder();
				if (departments.size() == 0) {
					deptValueStrBuiilder.append("&nbsp;");
				} else {
					List<Department> deptList = new ArrayList<Department>();
					deptList.addAll(departments);

					for (int c = 0; c < deptList.size(); c++) {
						Department dept = deptList.get(c);
						deptValueStrBuiilder.append(dept.getPath());
						if (c != (deptList.size() - 1)) {
							deptValueStrBuiilder.append(",");
						}
					}
				}
				value = deptValueStrBuiilder.toString();
				break;
			default:
				break;
			}
			rowDatas.put(col.toString(), value);
		}

		return rowDatas;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UserListService#putAll2ModelMap(java.util.List, int, org.springframework.ui.ModelMap)
	 */
	/**
	 * 方法说明：putAll2ModelMap
	 * 
	 * @param userlist
	 *            userlist
	 * @param nowNum
	 *            nowNum
	 * @param modelMap
	 *            modelMap
	 */
	@Transactional(readOnly = true)
	public void putAll2ModelMap(List<User> userlist, int nowNum, ModelMap modelMap)
	{
		modelMap.addAttribute("userListHeaders", getHeaders());
		modelMap.addAttribute("userListCols", getCols());
		modelMap.addAttribute("userListDatas", getDatas(userlist, nowNum));
	}

	public void setCols(List<UserCol> cols)
	{
		this.cols = cols;
	}

	public void setDefaultLocale(Locale defaultLocale)
	{
		this.defaultLocale = defaultLocale;
	}

	public void setMessageSource(MessageSource messageSource)
	{
		this.messageSource = messageSource;
	}

	public void setStartNum(int startNum)
	{
		this.startNum = startNum;
	}

	public void setAttributeDao(AttributeDao attributeDao)
	{
		this.attributeDao = attributeDao;
	}

	public void setUserDao(UserDao userDao)
	{
		this.userDao = userDao;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.service.UserListService#getColsAndHeaders()
	 */
	/**
	 * 方法说明：getColsAndHeaders
	 * 
	 * @return Map
	 */
	public Map<String, String> getColsAndHeaders()
	{
		Map<String, String> map = new LinkedHashMap<String, String>();
		for (UserCol col : getCols()) {
			map.put(col.toString(), getMessage("colName_" + col.toString()));
		}
		return map;
	}
}
