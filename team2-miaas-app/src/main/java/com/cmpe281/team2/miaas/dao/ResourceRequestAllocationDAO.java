package com.cmpe281.team2.miaas.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.entity.ResourceRequestAllocation;

@Repository
@Transactional
public class ResourceRequestAllocationDAO extends GenericDAO<ResourceRequestAllocation> {
	
	public Integer createResourceRequestAllocation(ResourceRequestAllocation resourceRequestAllocation) throws HibernateException {
		
		Integer id = null;
		
		Object obj = dataAccess.save(resourceRequestAllocation);
		
		if(obj instanceof Integer) {
			id = (Integer) obj;
		}
		return id;
	}
	
	public void updateResourceRequestAllocation(ResourceRequestAllocation resourceRequestAllocation) throws HibernateException {
		dataAccess.update(resourceRequestAllocation);
	}
	
	
	public ResourceRequestAllocation getResourceRequestAllocationById(Integer id) throws HibernateException {
		String hql = " from ResourceRequestAllocation where id = ?";
        return dataAccess.getOneByHQL(hql, id);
		
	}
	
	public List<ResourceRequestAllocation> getResourceRequestAllocations() throws HibernateException {
		String hql = " from ResourceRequestAllocation";
        return dataAccess.getByHQL(hql);
	}
	
	public List<ResourceRequestAllocation> getResourceRequestAllocationByResourceType(String resourceType) throws HibernateException {
		String hql = " from ResourceRequestAllocation where lower(resourceType) = lower(?)";
        return dataAccess.getByHQL(hql, resourceType);
	}
	
	public List<ResourceRequestAllocation> getResourceRequestAllocationByAssignedCloud(String assignedCloud) throws HibernateException {
		String hql = " from ResourceRequestAllocation where lower(assignedCloud) = lower(?)";
        return dataAccess.getByHQL(hql, assignedCloud);
	}
	
	public List<ResourceRequestAllocation> getResourceRequestAllocationByAssignedHost(String assignedHost) throws HibernateException {
		String hql = " from ResourceRequestAllocation where lower(assignedHost) = lower(?)";
        return dataAccess.getByHQL(hql, assignedHost);
	}
	
	public List<ResourceRequestAllocation> getResourceRequestAllocationByUserName(String userName) throws HibernateException {
		String hql = " from ResourceRequestAllocation where lower(userName) = lower(?)";
        return dataAccess.getByHQL(hql, userName);
	}
	
	
	
}
