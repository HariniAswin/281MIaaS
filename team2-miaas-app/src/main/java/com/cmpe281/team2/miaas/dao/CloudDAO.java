package com.cmpe281.team2.miaas.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.entity.Cloud;


@Repository
@Transactional
public class CloudDAO extends GenericDAO<Cloud> {
	
	@Autowired
	HostDAO hostDAO;
	
	public String createCloud(Cloud cloud) throws HibernateException {
		
		String cloudName = null;
		
		Object obj = dataAccess.save(cloud);
		
		if(obj instanceof String) {
			cloudName = (String) obj;
		}
		return cloudName;
	}
	
	public Cloud getCloudByName(String cloudName) throws HibernateException {
		String hql = " from Cloud where lower(name) = lower(?)";
        return dataAccess.getOneByHQL(hql, cloudName);
	}
	
	public List<Cloud> getClouds() throws HibernateException {
		String hql = " from Cloud";
        return dataAccess.getByHQL(hql);
	}
	
}
