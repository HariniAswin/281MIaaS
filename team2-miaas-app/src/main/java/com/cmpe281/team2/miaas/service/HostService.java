package com.cmpe281.team2.miaas.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.constants.ConstantsEnum;
import com.cmpe281.team2.miaas.dao.CloudDAO;
import com.cmpe281.team2.miaas.dao.HostDAO;
import com.cmpe281.team2.miaas.entity.Cloud;
import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackApiUtil;
import com.cmpe281.team2.miaas.restws.model.CreateHostRequest;

@Service
@Transactional
public class HostService {
	
	@Autowired
	private HostDAO hostDAO;
	
	@Autowired
	private CloudDAO cloudDAO;
	
	public String createHost(String cloud, CreateHostRequest request) throws BusinessException {
		
		if((request.getHostName() == null || request.getHostName().isEmpty())
			|| (request.getOs() == null || request.getOs().isEmpty())
			|| (request.getTotalCPUUnits() == null || request.getTotalCPUUnits() == 0)
			|| (request.getTotalRam() == null || request.getTotalRam() == 0)
			|| (request.getTotalStorage() == null || request.getTotalStorage() == 0)
			|| (request.getResourceType() == null || request.getResourceType().isEmpty())
			|| (cloud == null || cloud.isEmpty())
		) {
			throw new BusinessException(
					"The following fields are mandatory : hostName, os, totalCPUUnits, totalRam, totalStorage, resourceType");
		}
		
		if(!ConstantsEnum.getAllResourceTypes().contains(request.getResourceType())) {
			throw new BusinessException("Please pass a valid ResourceType. Valid are : Mobile Device, Emulator, Mobile Hub, Server Machine");
		}
		
		Cloud cloudObj = cloudDAO.getCloudByName(cloud);
		
		if(cloudObj == null) {
			throw new BusinessException("Please pass a valid cloud name");
		}
		
		// call Openstack API.
		
		String tenantId = null;
		boolean externalResource = false;
		
		if(request.getResourceType().equals(ConstantsEnum.ResourceType.SERVER_MACHINE.getName())) {
			JSONObject jsonResponse = OpenStackApiUtil.addProject(request.getHostName());
			
			tenantId = jsonResponse.getJSONObject("project").getString("id");
			externalResource = true;
		}
		
		
		Host host = new Host(request.getHostName(), request.getOs(),
				request.getTotalCPUUnits(), request.getTotalRam(),
				request.getTotalStorage(), cloud,
				request.getResourceType(), externalResource, tenantId);
		
		String hostName = hostDAO.createHost(host);
		
		return hostName;
		
	}
	

}
