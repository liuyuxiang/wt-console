package com.wt.uum2.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.collections.CollectionUtils;

import com.hirisun.rd1.secret.Decryptor;
import com.hirisun.rd1.secret.Encryptor;
import com.wt.uum2.constants.InitParameters;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-8
 * 作者:	nautilus
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "user")
public class User extends Resource
{

	/**
	 * 
	 */
	private static Encryptor encryptor = new Encryptor();

	/**
	 * 
	 */
	private static Decryptor decryptor = new Decryptor();

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930475255743128792L;

	/**
	 * 用户所属机构
	 */
	private Set<Department> departments;

	/**
	 * 用户所属组
	 */
	private Set<Group> groups;

	/**
	 * 用户所在的审核组
	 */
	private List<UserAuthor> userAuthors;

	/**
	 * 用户id
	 */
	@XmlElement
	private String id;

	/**
	 * 用户名
	 */
	@XmlElement
	private String name;

	/**
	 * 用户密码
	 */
	@XmlElement
	private String password;


	/**
	 * 首要机构
	 */
	private Department primaryDepartment;

	/**
	 * 首要机构
	 */
	private String primaryDepartmentUUID;

	public String getPrimaryDepartmentUUID()
	{
		return primaryDepartmentUUID;
	}

	public void setPrimaryDepartmentUUID(String primaryDepartmentUUID)
	{
		this.primaryDepartmentUUID = primaryDepartmentUUID;
	}

	/**
	 * 关联用户
	 */
	private User primaryUser;

	/**
	 * 
	 */
	private String primaryUserUUID;

	/**
	 * 
	 */
	private Date lastUpdateTime;

	/**
	 * 当前用户审核级别
	 */
	private Long currentAuthorLevel;

	/**
	 * 
	 */
	public User()
	{
		super();
		setType(0);
	}

	public Long getCurrentAuthorLevel()
	{
		return currentAuthorLevel;
	}

	public void setCurrentAuthorLevel(Long currentAuthorLevel)
	{
		this.currentAuthorLevel = currentAuthorLevel;
	}

	public Date getLastUpdateTime()
	{
		return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime)
	{
		this.lastUpdateTime = lastUpdateTime;
	}

	public User getPrimaryUser()
	{
		return primaryUser;
	}

	public void setPrimaryUser(User primaryUser)
	{
		this.primaryUser = primaryUser;
	}

	public List<UserAuthor> getUserAuthors()
	{
		return userAuthors;
	}

	public void setUserAuthors(List<UserAuthor> userAuthors)
	{
		this.userAuthors = userAuthors;
	}

	public String getPassword()
	{
		return password;
	}

	public void setPassword(String password)
	{
		this.password = password;
	}

	public Set<Department> getDepartments()
	{
		return departments;
	}

	public Set<Group> getGroups()
	{
		return groups;
	}

	public String getId()
	{
		return id;
	}

	public String getName()
	{
		return name;
	}

	private Department getPrimaryDepartment()
	{
		return primaryDepartment;
	}

	public void setDepartments(Set<Department> departments)
	{
		this.departments = departments;
	}

	public void setGroups(Set<Group> groups)
	{
		this.groups = groups;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * 方法说明：setPrimaryDepartment
	 * 
	 * @param primaryDepartment
	 *            primaryDepartment
	 */
	public void setPrimaryDepartment(Department primaryDepartment)
	{
		this.primaryDepartment = primaryDepartment;
		if (primaryDepartment != null) {
			this.primaryDepartmentUUID = primaryDepartment.getUuid();
		}
	}

	/**
	 * 方法说明：getPlainPassword
	 * 
	 * @return String
	 */
	public String getPlainPassword()
	{
		if (!InitParameters.isPlainPassword()) {
			return password;
		}
		return decryptor.decryptBase642String(getUuid(), this.password);
	}

	/**
	 * 方法说明：setPlainPassword
	 * 
	 * @param plainPassword
	 *            plainPassword
	 */
	public void setPlainPassword(String plainPassword)
	{
		String regEx="\\$\\{userid\\}";
		if (Pattern.compile(regEx).matcher(plainPassword).find()) {
			plainPassword = plainPassword.replaceFirst(regEx, getId());
		}
		if (getUuid() != null) {
			this.password = encryptor.encryptString2Base64(getUuid(), plainPassword);
		} else {
			this.password = plainPassword;
		}

	}

	public String getPrimaryUserUUID()
	{
		return primaryUserUUID;
	}

	public void setPrimaryUserUUID(String primaryUserUUID)
	{
		this.primaryUserUUID = primaryUserUUID;
	}

	/**
	 * 方法说明：添加部门信息
	 * 
	 * @param dept
	 *            dept
	 */
	public void addDept(Department dept)
	{
		if (departments == null) {
			departments = new HashSet<Department>();
		}
		departments.add(dept);
	}

	/**
	 * 方法说明：移除部门信息
	 * 
	 * @param dept
	 *            dept
	 */
	public void removeDept(Department dept)
	{
		if (CollectionUtils.isNotEmpty(departments)) {
			departments.remove(dept);
		}
	}

	/**
	 * 方法说明：添加角色信息
	 * 
	 * @param group
	 *            group
	 */
	public void addGroup(Group group)
	{
		if (groups == null) {
			groups = new HashSet<Group>();
		}
		groups.add(group);
	}

	/**
	 * 方法说明：删除角色信息
	 * 
	 * @param group
	 *            group
	 */
	public void removeGroup(Group group)
	{
		if (CollectionUtils.isNotEmpty(groups)) {
			groups.remove(group);
		}
	}

}