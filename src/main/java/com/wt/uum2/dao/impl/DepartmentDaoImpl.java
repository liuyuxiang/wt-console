package com.wt.uum2.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import nak.nsf.dao.support.BaseDaoSupport;
import nak.nsf.pager.Pager;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.hirisun.components.other.project.ProjectResolver;
import com.wt.uum2.constants.Condition;
import com.wt.uum2.constants.InitParameters;
import com.wt.uum2.constants.ResourceStatus;
import com.wt.uum2.constants.UserPageResult;
import com.wt.uum2.dao.DepartmentDao;
import com.wt.uum2.domain.Department;
import com.wt.uum2.domain.Group;
import com.wt.uum2.domain.User;

/**
 * <pre>
 * 业务名:
 * 功能说明: 
 * 编写日期:	2013-1-7
 * 作者:	fanglei
 * 
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class DepartmentDaoImpl extends BaseDaoSupport<Department> implements DepartmentDao
{

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#readByName(java.lang.String)
	 */
	/**
	 * 方法说明：readByName
	 * 
	 * @param deptName
	 *            deptName
	 * @return Department
	 */
	public Department readByName(String deptName)
	{
		return (Department) getSession().createQuery("from Department where name=:deptName")
				.setString("deptName", deptName).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#readChildren(java.lang.String)
	 */
	/**
	 * 方法说明：readChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<Department> readChildren(String parentUUID)
	{
		return getSession()
				.createQuery(
						"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus order by department.order,department.hasChildren,department.name desc")
				.setString("parentUUID", parentUUID)
				.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getAbnormalDepartmentsByParent(java.lang.String)
	 */
	/**
	 * 方法说明：getAbnormalDepartmentsByParent
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @return List
	 */
	public List<Department> getAbnormalDepartmentsByParent(String parentUUID)
	{
		return getSession()
				.createQuery(
						"select department from Department as department where department.parentUUID=:parentUUID and department.status<>:deptStatus order by department.hasChildren,department.order,department.name desc")
				.setString("parentUUID", parentUUID)
				.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#readChildren(java.lang.String, com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：readChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<Department> readChildren(String parentUUID, User loginUser)
	{
		Set<Department> departmentList = loginUser.getDepartments();
		boolean flag = false;
		Set<String> orgCode = new HashSet<String>();
		if (!CollectionUtils.isEmpty(departmentList)) {
			for (Department dept : departmentList) {
				orgCode.add(dept.getOrgCode());
			}
		}
		Set<Group> groups = loginUser.getGroups();
		//判断是否是管理员
		if (!CollectionUtils.isEmpty(groups)) {
			for (Group group : loginUser.getGroups()) {
				if (group.getCode().equals(InitParameters.getSuperGroupCode())) {
					flag = true;
					break;
				}
			}
		}
		//如果是重庆,而且是信通组的话
		if (!flag && InitParameters.isCqGroupAuthor()&&!CollectionUtils.isEmpty(groups)) {
			for (Group group : groups) {
				if (group.getCode()
						.equals(InitParameters.modifyUserGroupCode())) {
					flag = true;
					break;
				}
			}
		}
		//如果是xmsp,而且是管理组成员
		if (!flag && StringUtils.equals(ProjectResolver.getId(), "xmsp")) {
			if (!CollectionUtils.isEmpty(groups)) {
				for (Group group : groups) {
					for (String str : InitParameters.getManagerGroupCode()) {
						if(StringUtils.equals(group.getCode(), str)){
							flag = true;
							break;
						}
					}
					if(flag){
						break;
					}
				}
			}
		}
		if (flag) {
			return getSession()
					.createQuery(
							"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus order by department.order asc,department.hasChildren,department.name desc")
					.setString("parentUUID", parentUUID)
					.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
		} else if (!CollectionUtils.isEmpty(orgCode)) {
			return getSession()
					.createQuery(
							"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus and department.orgCode in (:orgCode) order by department.hasChildren,department.order,department.name desc")
					.setString("parentUUID", parentUUID).setParameterList("orgCode", orgCode)
					.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
		} else {
			return getSession()
					.createQuery(
							"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus order by department.hasChildren,department.order,department.name desc")
					.setString("parentUUID", parentUUID)
					.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#readChildren(java.lang.String, com.wt.uum2.domain.User, java.lang.String)
	 */
	/**
	 * 方法说明：readChildren
	 * 
	 * @param parentUUID
	 *            parentUUID
	 * @param loginUser
	 *            loginUser
	 * @param flag1
	 *            flag1
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<Department> readChildren(String parentUUID, User loginUser, String flag1)
	{
		boolean flag = false;
		String orgCode = "";
		Department department = this.getDeapartmentByUUID(loginUser.getPrimaryDepartmentUUID());
		if (department != null) {
			orgCode = department.getOrgCode();
		}
		if (!flag && loginUser.getGroups() != null && !loginUser.getGroups().isEmpty()) {
			for (Group group : loginUser.getGroups()) {
				if (group.getCode().equals(InitParameters.getSuperGroupCode())) {
					flag = true;
				}
			}
		}
		if (flag1 != null && !flag && !orgCode.equals("")) {
			return getSession()
					.createQuery(
							"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus and department.orgCode=:orgCode order by department.hasChildren,department.order,department.name desc")
					.setString("parentUUID", parentUUID).setString("orgCode", orgCode)
					.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
		} else {
			return getSession()
					.createQuery(
							"select department from Department as department where department.parentUUID=:parentUUID and department.status=:deptStatus order by department.hasChildren,department.order,department.name desc")
					.setString("parentUUID", parentUUID)
					.setInteger("deptStatus", ResourceStatus.NORMAL.intValue()).list();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#readRoot()
	 */
	/**
	 * 方法说明：readRoot
	 * 
	 * @return Department
	 */
	public Department readRoot()
	{
		return (Department) getSession().createQuery("from Department where parentUUID is null")
				.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Department> getAllDepartments()
	{
		return getSession().createCriteria(Department.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.addOrder(Order.asc("level")).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDeapartmentByUUID(java.lang.String)
	 */
	/**
	 * 方法说明：getDeapartmentByUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return Department
	 */
	public Department getDeapartmentByUUID(String uuid)
	{
		Criteria c = getSession().createCriteria(Department.class);
		c.setCacheable(true).add(Restrictions.eq("uuid", uuid))
		.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()));
		
		return (Department) 
				c.uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByKey(java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByKey
	 * 
	 * @param key
	 *            key
	 * @return List
	 */
	public List<Department> searchDepartmentsByKey(String key)
	{
		return getSession().createCriteria(Department.class).add(Restrictions.like("name", key))
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue())).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#updateDeptOrder(java.lang.String)
	 */
	/**
	 * 方法说明：updateDeptOrder
	 * 
	 * @param key
	 *            key
	 */
	public void updateDeptOrder(String key)
	{
		Query q = getSession().createQuery("update Department set order=" + key);
		q.executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#deleteDepartmentByUUID(java.lang.String)
	 */
	/**
	 * 方法说明：deleteDepartmentByUUID
	 * 
	 * @param uuid
	 *            uuid
	 * @return int
	 */
	public int deleteDepartmentByUUID(String uuid)
	{
		Department department = (Department) getSession().createCriteria(Department.class)
				.add(Restrictions.eq("uuid", uuid)).uniqueResult();
		department.setStatus(ResourceStatus.DELETE_LOGIC.intValue());
		this.update(department);
		return 1;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentsByParent(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getDepartmentsByParent
	 * 
	 * @param department
	 *            department
	 * @return List
	 */
	public List<Department> getDepartmentsByParent(Department department)
	{
		return getSession()
				.createQuery(
						"select dept from Department as dept,DepartmentPath dp where dp.elder=:department and dp.juniorUUID=dept.uuid")
				.setEntity("department", department).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentByAdminGroup(int, int, com.wt.uum2.domain.Group)
	 */
	/**
	 * 方法说明：getDepartmentByAdminGroup
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param group
	 *            group
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentByAdminGroup(int page, int pagesize, Group group)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		pager.setQuantityOfData((Long) getSession().createCriteria(Department.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
				.setProjection(Projections.rowCount()).uniqueResult());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession().createCriteria(Department.class)
				.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
				.createCriteria("adminGroups").add(Restrictions.idEq(group.getUuid()))
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByAdmGroups(int, int, com.wt.uum2.constants.Condition, java.util.List, boolean)
	 */
	/**
	 * 方法说明：searchDepartmentsByAdmGroups
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @param admGroups
	 *            admGroups
	 * @param isInSuperGroup
	 *            isInSuperGroup
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentsByAdmGroups(int page, int pagesize, Condition condition,
			List<Group> admGroups, boolean isInSuperGroup)
	{
		UserPageResult<Department> result = new UserPageResult<Department>();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}

		if (condition == null
				|| (condition.getUserId().equals("") && condition.getUserName().equals(""))) {
			pager.setQuantityOfData(0);

			pager.compute();

			result.setList(new ArrayList<Department>());
			result.setPager(pager);
			return result;
		}

		List<String> uuids = new ArrayList<String>();
		if (admGroups != null) {
			for (int i = 0; i < admGroups.size(); i++) {
				uuids.add(admGroups.get(i).getUuid());
			}
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			String from = null;
			if (isInSuperGroup) {
				from = " from Department as res "
						+ "where res.status=:status and res.name like :name";
			} else {
				from = " from Department as res,ResourceAdminGroup as rag "
						+ "where rag.resourceUUID=res.uuid and res.status=:status and  res.name like :name and rag.groupUUID in (:uuids)";
			}

			String countHql = "select count(distinct res.uuid) " + from;

			String listHql = "select distinct res " + from;

			Query countQuery = getSession().createQuery(countHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("name", "%" + condition.getUserName() + "%");

			Query listQuery = getSession().createQuery(listHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("name", "%" + condition.getUserName() + "%");

			if (!isInSuperGroup) {
				countQuery.setParameterList("uuids", uuids);
				listQuery.setParameterList("uuids", uuids);
			}

			pager.setQuantityOfData((Long) countQuery.uniqueResult());

			pager.compute();

			listQuery.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());

			result.setList(listQuery.list());

		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			String from = null;
			if (isInSuperGroup) {
				from = " from Department as res "
						+ "where res.status=:status and res.code like :code";
			} else {
				from = " from Department as res,ResourceAdminGroup as rag "
						+ "where rag.resourceUUID=dept.uuid and res.status=:status and  res.code like :code and rag.groupUUID in (:uuids)";
			}

			String countHql = "select count(distinct res.uuid) " + from;

			String listHql = "select distinct res " + from;

			Query countQuery = getSession().createQuery(countHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("code", "%" + condition.getUserId() + "%");

			Query listQuery = getSession().createQuery(listHql)
					.setInteger("status", ResourceStatus.NORMAL.intValue())
					.setString("code", "%" + condition.getUserId() + "%");

			if (!isInSuperGroup) {
				countQuery.setParameterList("uuids", uuids);
				listQuery.setParameterList("uuids", uuids);
			}

			pager.setQuantityOfData((Long) countQuery.uniqueResult());

			pager.compute();

			listQuery.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize());

			result.setList(listQuery.list());

		}
		result.setPager(pager);
		return result;

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByCondition(int, int, com.wt.uum2.constants.Condition)
	 */
	/**
	 * 方法说明：searchDepartmentsByCondition
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param condition
	 *            condition
	 * @return UserPageResult
	 */
	public UserPageResult searchDepartmentsByCondition(int page, int pagesize, Condition condition)
	{
		UserPageResult result = new UserPageResult();

		Pager pager = new Pager();
		pager.setCurrentPage(page);
		if (pagesize > 0) {
			pager.setPageSize(pagesize);
		}
		if (!condition.getUserName().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(Department.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(Department.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("name", "%" + condition.getUserName() + "%"))
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else if (!condition.getUserId().equalsIgnoreCase("")) {
			pager.setQuantityOfData((Long) getSession().createCriteria(Department.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("code", "%" + condition.getUserId() + "%").ignoreCase())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
					.setProjection(Projections.rowCount()).uniqueResult());

			pager.compute();

			result.setList(getSession().createCriteria(Department.class)
					.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()))
					.add(Restrictions.like("code", "%" + condition.getUserId() + "%").ignoreCase())
					.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
					.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());

			result.setPager(pager);
			return result;
		} else {
			return null;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentByDeptCode(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByDeptCode
	 * 
	 * @param deptCode
	 *            deptCode
	 * @return Department
	 */
	public Department getDepartmentByDeptCode(String deptCode)
	{
		return (Department) getSession().createQuery("from Department where deptCode=:deptCode")
				.setString("deptCode", deptCode).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentByNameAndOrgCode(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByNameAndOrgCode
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return Department
	 */
	public Department getDepartmentByNameAndOrgCode(String name, String orgCode)
	{
		return (Department) getSession()
				.createQuery("from Department where name=:deptName and orgCode=:orgCo")
				.setString("deptName", name).setString("orgCo", orgCode).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getCreatedDepartmentsManagedUnderGroups(int, int, java.util.List)
	 */
	/**
	 * 方法说明：getCreatedDepartmentsManagedUnderGroups
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param groups
	 *            groups
	 * @return UserPageResult
	 */
	public UserPageResult getCreatedDepartmentsManagedUnderGroups(int page, int pagesize,
			List<Group> groups)
	{
		UserPageResult result = new UserPageResult();
		List<String> list = new ArrayList<String>();
		if (groups != null && groups.size() > 0) {
			for (int i = 0; i < groups.size(); i++) {
				list.add("'" + groups.get(i).getUuid() + "'");
			}
		}
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		List<Integer> status = new ArrayList<Integer>();
		status.add(Integer.valueOf(ResourceStatus.CREATED.intValue()));
		pager.setQuantityOfData(((Long) getSession()
				.createQuery(
						"select count(department.uuid) from Department as department,DepartmentAuthor as departmentAuthor where departmentAuthor in "
								+ "elements(department.departmentAuthors) and department.currentAuthorLevel is not null and department.currentAuthorLevel>0 and "
								+ "department.currentAuthorLevel=departmentAuthor.levels and departmentAuthor.group.uuid in ("
								+ StringUtils.join(list, ",")
								+ ") and department.status="
								+ ResourceStatus.CREATED.intValue())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult())
				.intValue());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession()
				.createQuery(
						"select department from Department as department,DepartmentAuthor as departmentAuthor where departmentAuthor in "
								+ "elements(department.departmentAuthors) and department.currentAuthorLevel is not null and department.currentAuthorLevel>0 and "
								+ "department.currentAuthorLevel=departmentAuthor.levels and departmentAuthor.group.uuid in ("
								+ StringUtils.join(list, ",")
								+ ") and department.status="
								+ ResourceStatus.CREATED.intValue())
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#countDepartment()
	 */
	/**
	 * 方法说明：countDepartment
	 * 
	 * @return int
	 */
	public int countDepartment()
	{
		return ((Long) getSession().createCriteria(Department.class)
				.setProjection(Projections.rowCount()).uniqueResult()).intValue();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#existDepartmentRTXCode(long)
	 */
	/**
	 * 方法说明：existDepartmentRTXCode
	 * 
	 * @param rtxCode
	 *            rtxCode
	 * @return boolean
	 */
	public boolean existDepartmentRTXCode(long rtxCode)
	{
		Long deptListSize = (Long) getSession().createCriteria(Department.class)
				.add(Restrictions.eq("rtxCode", rtxCode)).setProjection(Projections.rowCount())
				.uniqueResult();
		if (deptListSize < 1) {
			return false;
		} else {
			return true;
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#countDepartmentForRtx()
	 */
	/**
	 * 方法说明：countDepartmentForRtx
	 * 
	 * @return int
	 */
	public int countDepartmentForRtx()
	{
		String hql = "select max(rtxCode) from Department";
		Query query = getSession().createQuery(hql);
		Long maxCount = (Long) query.list().get(0);
		if (maxCount == null) {
			return 0;
		} else {
			return maxCount.intValue();
		}
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#updateDeptPath(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：updateDeptPath
	 * 
	 * @param newPath
	 *            newPath
	 * @param oldPath
	 *            oldPath
	 */
	public void updateDeptPath(String newPath, String oldPath)
	{
		getSession()
				.createQuery(
						"update Department set path=:newPath || substring(path,"
								+ (oldPath.length() + 1) + ") where path like :oldPath")
				.setString("newPath", newPath).setString("oldPath", oldPath + "%").executeUpdate();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentByCode(java.lang.String)
	 */
	/**
	 * 方法说明：getDepartmentByCode
	 * 
	 * @param code
	 *            code
	 * @return Department
	 */
	public Department getDepartmentByCode(String code)
	{
		return (Department) getSession().createQuery("from Department where code=:code")
				.setString("code", code).uniqueResult();
	}

	public Department getNoDepartment()
	{
		return this.getDepartmentByDeptCode(InitParameters.getNoDepartment());
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentsByUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getDepartmentsByUser
	 * 
	 * @param user
	 *            user
	 * @return List
	 */
	public List<Department> getDepartmentsByUser(User user)
	{
		return getSession()
				.createQuery(
						"select department from Department as department,User as user where department in elements(user.departments) and user.uuid=:uuid")
				.setString("uuid", user.getUuid()).list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getPrimaryDepartmentByUser(com.wt.uum2.domain.User)
	 */
	/**
	 * 方法说明：getPrimaryDepartmentByUser
	 * 
	 * @param user
	 *            user
	 * @return Department
	 */
	public Department getPrimaryDepartmentByUser(User user)
	{
		return (Department) getSession()
				.createQuery(
						"select department from Department as department,User as user where department=user.primaryDepartment and user.uuid=:uuid")
				.setString("uuid", user.getUuid()).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getDepartmentsByParent(int, int, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getDepartmentsByParent
	 * 
	 * @param page
	 *            page
	 * @param pagesize
	 *            pagesize
	 * @param parentDept
	 *            parentDept
	 * @return UserPageResult
	 */
	public UserPageResult getDepartmentsByParent(int page, int pagesize, Department parentDept)
	{
		UserPageResult result = new UserPageResult();
		Pager pager = new Pager();
		pager.setCurrentPage(page);
		pager.setPageSize(pagesize);
		Long count = (Long) getSession()
				.createQuery(
						"select count(*) from Department as department where department.status="
								+ ResourceStatus.NORMAL.intValue() + " and "
								+ "department.parentUUID='" + parentDept.getUuid() + "'")
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).uniqueResult();
		pager.setQuantityOfData(count.intValue());

		pager.compute();

		result.setPager(pager);
		result.setList(getSession()
				.createQuery(
						"select department from Department as department where department.status="
								+ ResourceStatus.NORMAL.intValue() + " and "
								+ "department.parentUUID='" + parentDept.getUuid()
								+ "' order by department.order asc")
				.setFirstResult(pager.getDataStart()).setMaxResults(pager.getPageSize())
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY).list());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByName(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, String orgCode)
	{
		return searchDepartmentsByName(name, orgCode, -1, -1);
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByCode(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByCode
	 * 
	 * @param code
	 *            code
	 * @param orgCode
	 *            orgCode
	 * @return List
	 */
	public List<Department> searchDepartmentsByCode(String code, String orgCode)
	{
		Criteria c = getSession().createCriteria(Department.class);
		c.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()));
		c.add(Restrictions.or(Restrictions.eq("code", code), Restrictions.eq("deptCode", code)));
		if (StringUtils.isNotBlank(orgCode)) {
			c.add(Restrictions.eq("orgCode", orgCode));
		}
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return c.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#getGroupsByManagedDepartment(com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：getGroupsByManagedDepartment
	 * 
	 * @param dept
	 *            dept
	 * @return List
	 */
	public List<Group> getGroupsByManagedDepartment(Department dept)
	{
		Query q = getSession()
				.createQuery(
						"select group from Group as group,ResourceAdminGroup as ug where group.status=:status and group.uuid=ug.groupUUID and ug.resourceUUID=:useruuid)");
		q.setString("useruuid", dept.getUuid()).setInteger("status",
				ResourceStatus.NORMAL.intValue());
		return q.list();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#moveDeptUpdateLevel(com.wt.uum2.domain.Department, com.wt.uum2.domain.Department)
	 */
	/**
	 * 方法说明：moveDeptUpdateLevel
	 * 
	 * @param currentDept
	 *            currentDept
	 * @param toDept
	 *            toDept
	 */
	public void moveDeptUpdateLevel(Department currentDept, Department toDept)
	{
		getSession()
				.createQuery(
						"update Department dept set dept.level = dept.level+:levelOffset where exists (select p from DepartmentPath p where dept.uuid=p.juniorUUID and p.elderUUID=:elderUUID)")
				.setInteger("levelOffset", toDept.getLevel() - currentDept.getLevel() + 1)
				.setString("elderUUID", currentDept.getUuid()).executeUpdate();

	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByNameCount(java.lang.String, java.lang.String)
	 */
	/**
	 * 方法说明：searchDepartmentsByNameCount
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @return long
	 */
	public long searchDepartmentsByNameCount(String name, String orgCode)
	{
		Criteria c = getSession().createCriteria(Department.class);
		c.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()));
		c.add(Restrictions.ilike("name", "%" + name + "%"));
		if (StringUtils.isNotBlank(orgCode)) {
			c.add(Restrictions.eq("orgCode", orgCode));
		}
		return (Long) c.setProjection(Projections.rowCount()).uniqueResult();
	}

	/* (non-Javadoc)
	 * @see com.wt.uum2.dao.DepartmentDao#searchDepartmentsByName(java.lang.String, java.lang.String, int, int)
	 */
	/**
	 * 方法说明：searchDepartmentsByName
	 * 
	 * @param name
	 *            name
	 * @param orgCode
	 *            orgCode
	 * @param start
	 *            start
	 * @param max
	 *            max
	 * @return List
	 */
	public List<Department> searchDepartmentsByName(String name, String orgCode, int start, int max)
	{

		Criteria c = getSession().createCriteria(Department.class);
		c.add(Restrictions.eq("status", ResourceStatus.NORMAL.intValue()));
		c.add(Restrictions.ilike("name", "%" + name + "%"));
		if (StringUtils.isNotBlank(orgCode)) {
			c.add(Restrictions.eq("orgCode", orgCode));
		}
		if (start > 0) {
			c.setFirstResult(start);
		}
		if (max > 0) {
			c.setMaxResults(max);
		}
		c.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return c.list();
	}

}