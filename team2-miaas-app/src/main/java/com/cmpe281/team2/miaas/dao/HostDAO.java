package com.cmpe281.team2.miaas.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.entity.Cloud;
import com.cmpe281.team2.miaas.entity.Host;

@Repository
@Transactional
public class HostDAO extends GenericDAO<Cloud> {
	
	public String createHost(Host host) throws HibernateException {
		
		String hostName = null;
		
		Object obj = dataAccess.save(host);
		
		if(obj instanceof String) {
			hostName = (String) obj;
		}
		return hostName;
	}
	
	public void updateHost(Host host) throws HibernateException {
		dataAccess.update(host);
	}
	
	public List<Host> getHostsByCloudName(String cloudName) throws HibernateException {
		String hql = " from Host where lower(cloudName) = lower(?)";
        return dataAccess.getByHQL(hql, cloudName);
	}
	
	public List<Host> getHostsByResourceType(String resourceType) throws HibernateException {
		String hql = " from Host where lower(resourceType) = lower(?)";
        return dataAccess.getByHQL(hql, resourceType);
	}
	
	public List<Host> getHostsByResourceTypeAndOs(String resourceType, String os) throws HibernateException {
		String hql = " from Host where lower(resourceType) = lower(?) and lower(os) =lower(?)";
        return dataAccess.getByHQL(hql, resourceType, os);
	}

}
