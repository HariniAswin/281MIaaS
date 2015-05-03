package com.cmpe281.team2.miaas.service;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.dao.CloudDAO;
import com.cmpe281.team2.miaas.dao.HostDAO;
import com.cmpe281.team2.miaas.dao.ResourceRequestAllocationDAO;
import com.cmpe281.team2.miaas.entity.Cloud;
import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.entity.ResourceRequestAllocation;
import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.restws.model.CloudRequestsResponse;
import com.cmpe281.team2.miaas.restws.model.CloudResponse;
import com.cmpe281.team2.miaas.restws.model.CloudStatisticsResponse;
import com.cmpe281.team2.miaas.restws.model.CloudUtilizationResponse;
import com.cmpe281.team2.miaas.restws.model.CreateCloudRequest;
import com.cmpe281.team2.miaas.restws.model.CreateHostRequest;
import com.cmpe281.team2.miaas.restws.model.HostResponse;
import com.cmpe281.team2.miaas.restws.model.SetupCloudRequest;


@Service
@Transactional
public class CloudService {
	
	@Autowired
	private HostService hostService;
	
	@Autowired
	private CloudDAO cloudDAO;
	
	@Autowired
	private HostDAO hostDAO;
	
	@Autowired
	private ResourceRequestAllocationDAO resourceRequestAllocationDAO;
	
	public String createCloud(CreateCloudRequest request) throws BusinessException {
		
		if(request.getName() == null || request.getName().isEmpty()) {
			throw new BusinessException("please provide a valid cloud name");
		}
		
		if(request.getLocation() == null || request.getLocation().isEmpty()) {
			throw new BusinessException("please provide a valid location");
		}
		
		Cloud cloud = new Cloud(request.getName(), request.getLocation());
		
		String cloudName = cloudDAO.createCloud(cloud);
		
		return cloudName;
		
	}
	
	public CloudStatisticsResponse getCloudStatistics() {
		
		CloudStatisticsResponse response = new CloudStatisticsResponse();
		
		List<Cloud> clouds = cloudDAO.getClouds();
		List<Host> hosts = hostDAO.getHosts();
		List<ResourceRequestAllocation> requests = resourceRequestAllocationDAO.getResourceRequestAllocations();
		BigInteger usersCount = resourceRequestAllocationDAO.getTotalNumberOfUsers();
		
		response.setCloudsCount(clouds.size());
		response.setHostsCount(hosts.size());
		response.setRequestsCount(requests.size());
		response.setUsersCount(usersCount.intValue());
		
		List<CloudRequestsResponse> cloudRequests = new ArrayList<CloudRequestsResponse>();
		
		List<CloudUtilizationResponse> cloudUtilizations = new ArrayList<CloudUtilizationResponse>();
		
		for(Cloud cloud : clouds) {
			
			CloudUtilizationResponse cloudUtilizationResponse = new CloudUtilizationResponse();
			
			CloudRequestsResponse cloudRequestResponse = new CloudRequestsResponse();
			
			cloudUtilizationResponse.setCloudName(cloud.getName());
			
			cloudRequestResponse.setCloudName(cloud.getName());
			
			List<Host> cloudHosts = hostDAO.getHostsByCloudName(cloud.getName());
			
			List<ResourceRequestAllocation> cloudResourceRequests = resourceRequestAllocationDAO.getResourceRequestAllocationByAssignedCloud(cloud.getName());
			
			float usageIndex = 0;
			
			float totalCPU = 0;
			float totalRAM = 0;
			float totalStorage = 0;
			float cpuAllocated = 0;
			float ramAllocated = 0;
			float storageAllocated = 0;
			
			for(Host cloudHost : cloudHosts) {
				
				totalCPU += cloudHost.getTotalCPUUnits();
				totalRAM += cloudHost.getTotalRam();
				totalStorage += cloudHost.getTotalStorage();
				
				cpuAllocated += (cloudHost.getCpuAllocated() != null) ? cloudHost.getCpuAllocated() : 0;
				ramAllocated += (cloudHost.getRamAllocated() != null) ? cloudHost.getRamAllocated() : 0;
				storageAllocated += (cloudHost.getStorageAllocated() != null) ? cloudHost.getStorageAllocated() : 0;
				
				
				Float cpuUtilization = ((cloudHost.getTotalCPUUnits() != null && cloudHost
						.getCpuAllocated() != null) ? cloudHost.getCpuAllocated()
						/ cloudHost.getTotalCPUUnits() : 0); 
				
				Float memoryUtilization = ((cloudHost.getTotalRam() != null && cloudHost
						.getRamAllocated() != null) ? cloudHost.getRamAllocated()
						/ cloudHost.getTotalRam() : 0);
				
				Float diskUtilization = ((cloudHost.getTotalStorage() != null && cloudHost
						.getStorageAllocated() != null) ? cloudHost.getStorageAllocated()
						/ cloudHost.getTotalStorage() : 0);
				
				usageIndex += (cpuUtilization + memoryUtilization + diskUtilization) / 3;
				
			}
			
			if(cloudHosts.size() > 0) {
				usageIndex = usageIndex/cloudHosts.size();
			}
			
			cloudRequestResponse.setNumberOfRequests(cloudResourceRequests.size());
			cloudRequestResponse.setNumberOfHosts(cloudHosts.size());
			
			cloudRequestResponse.setTotalCPU(totalCPU);
			cloudRequestResponse.setTotalRAM(totalRAM);
			cloudRequestResponse.setTotalStorage(totalStorage);
			cloudRequestResponse.setCpuAllocated(cpuAllocated);
			cloudRequestResponse.setRamAllocated(ramAllocated);
			cloudRequestResponse.setStorageAllocated(storageAllocated);
			
			cloudUtilizationResponse.setUsageIndex(df.format(usageIndex));
			
			cloudRequests.add(cloudRequestResponse);
			cloudUtilizations.add(cloudUtilizationResponse);
		}
		
		response.setCloudRequestStats(cloudRequests);
		response.setCloudUsageStats(cloudUtilizations);
		
		return response;
		
	}
	
	public CloudResponse getCloudStatisticsByName(String cloudName) throws BusinessException {
		
		
		if(cloudName == null || cloudName.isEmpty()) {
			throw new BusinessException("please provide a valid cloud name");
		}
		
		Cloud cloud = cloudDAO.getCloudByName(cloudName);
		
		CloudResponse cloudResponse = new CloudResponse();
		
		if(cloud != null) {
			
			cloudResponse.setCloudName(cloud.getName());
			cloudResponse.setLocation(cloud.getLocation());
			
			List<HostResponse> hostsResponse = new ArrayList<HostResponse>();
			
			List<Host> hosts = hostDAO.getHostsByCloudName(cloudName);
			
			Float cloudUsage = 0f;
			Float cloudUsageIndex = 0f;
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
				
				hostResponse.setUsageIndex(usageIndex);
				
				cloudUsageIndex += usageIndex;

				hostsResponse.add(hostResponse);
			}
			
			if(hostsResponse.size() > 0) {
				cloudUsageIndex = cloudUsage/hostsResponse.size();
			}
			 
			
			cloudResponse.setUsageIndex(cloudUsageIndex);
			
			cloudResponse.setHosts(hostsResponse);
			
		}
		
		
		return cloudResponse;
		
	}
	
	public void setupCloud() throws IOException {
		
		//read json file data to String
        InputStream is = getClass().getClassLoader().getResourceAsStream("cloud.json");
        
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
         
        //convert json string to object
        SetupCloudRequest request = objectMapper.readValue(is, SetupCloudRequest.class);
        
		logger.info("request cloud size : " + request.getClouds().size()
				+ " host size : " + request.getHosts().size());
		
		for(CreateCloudRequest cloudRequest : request.getClouds()) {
			try {
				createCloud(cloudRequest);
			} catch (BusinessException e) {
				logger.error(e);
			} catch (HibernateException e) {
				logger.error(e);
			}
		}
		
		for(CreateHostRequest hostRequest : request.getHosts()) {
			try {
				hostService.createHost(hostRequest.getCloud(), hostRequest);
			} catch (BusinessException e) {
				logger.error(e);
			} catch (HibernateException e) {
				logger.error(e);
			}
		}
		
	}
	
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	private final static Logger logger = Logger.getLogger(CloudService.class);
	
}
