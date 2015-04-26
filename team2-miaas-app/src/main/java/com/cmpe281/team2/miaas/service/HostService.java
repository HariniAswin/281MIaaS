package com.cmpe281.team2.miaas.service;

import java.util.ArrayList;
import java.util.List;

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
import com.cmpe281.team2.miaas.restws.model.HostResponse;

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
	
	public List<HostResponse> getHostsByResourceType(String resourceType) {
		
		List<HostResponse> hostsResponse = new ArrayList<HostResponse>();
		
		List<Host> hosts = hostDAO.getHostsByResourceType(resourceType);
		
		for(Host host : hosts) {
			
			HostResponse hostResponse = new HostResponse();
			
			hostResponse.setHostName(host.getName());
			hostResponse.setOs(host.getOs());
			hostResponse.setTotalCPUs(host.getTotalCPUUnits());
			hostResponse.setTotalRAM(host.getTotalRam());
			hostResponse.setTotalStorage(host.getTotalStorage());
			hostResponse.setResourceType(host.getResourceType());
			
			Float cpuUtilization = ((host.getTotalCPUUnits() != null && host
					.getCpuAllocated() != null) ? host.getCpuAllocated()
					/ host.getTotalCPUUnits() : 0); 
			
			Float memoryUtilization = ((host.getTotalRam() != null && host
					.getRamAllocated() != null) ? host.getRamAllocated()
					/ host.getTotalRam() : 0);
			
			Float diskUtilization = ((host.getTotalStorage() != null && host
					.getStorageAllocated() != null) ? host.getStorageAllocated()
					/ host.getTotalStorage() : 0);
			
			Float storageAllocated = (host.getStorageAllocated() == null ? 0 : host.getStorageAllocated());
			Float cpuAllocated = (host.getCpuAllocated() == null ? 0 : host.getCpuAllocated());
			Float ramAllocated = (host.getRamAllocated() == null ? 0 : host.getRamAllocated());
			
			Float freeCpus = host.getTotalCPUUnits() - cpuAllocated;
			Float freeRAM = host.getTotalRam() - ramAllocated;
			Float freeStorage = host.getTotalStorage() - storageAllocated;
			
			float usageIndex = (cpuUtilization + memoryUtilization + diskUtilization) / 3;
			
			hostResponse.setCpuAllocated(cpuAllocated);
			hostResponse.setRamAllocated(ramAllocated);
			hostResponse.setStorageAllocated(storageAllocated);
			
			hostResponse.setCpuUtilization(cpuUtilization);
			hostResponse.setMemoryUtilization(memoryUtilization);
			hostResponse.setDiskUtilization(diskUtilization);
			
			hostResponse.setFreeCpus(freeCpus);
			hostResponse.setFreeRAM(freeRAM);
			hostResponse.setFreeStorage(freeStorage);
			hostResponse.setCloud(host.getCloudName());
			
			hostResponse.setUsageIndex(usageIndex);
			
			hostsResponse.add(hostResponse);
		}
		
		return hostsResponse;
		
	}
	

}
