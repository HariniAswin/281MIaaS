package com.cmpe281.team2.miaas.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team.miaas.loadbalancer.LoadBalancerBroker;
import com.cmpe281.team2.miaas.constants.ConstantsEnum;
import com.cmpe281.team2.miaas.dao.CloudDAO;
import com.cmpe281.team2.miaas.dao.HostDAO;
import com.cmpe281.team2.miaas.dao.ResourceRequestAllocationDAO;
import com.cmpe281.team2.miaas.entity.Cloud;
import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.entity.ResourceRequestAllocation;
import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackApiUtil;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Flavor;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Image;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;
import com.cmpe281.team2.miaas.restws.model.GetResourceRequestsResponse;
import com.cmpe281.team2.miaas.restws.model.GetResourcesByUserNameResponse;
import com.cmpe281.team2.miaas.restws.model.RequestLoader;
import com.cmpe281.team2.miaas.restws.model.ResourceRequestAllocationResponse;
import com.cmpe281.team2.miaas.restws.model.UsersResponse;

@Service
@Transactional
public class ResourceRequestAllocationService {
	
	@Autowired
	ResourceRequestAllocationDAO resourceRequestAllocationDAO;
	
	@Autowired
	HostDAO hostDAO;
	
	@Autowired
	CloudDAO cloudDAO;
	
	public Integer createResourceRequestAllocation(CreateResourceRequestAllocationRequest request) throws BusinessException {
		
		if((request.getResourceType() == null || request.getResourceType().isEmpty())
				|| (request.getOs() == null || request.getOs().isEmpty())
				|| (request.getCpu() == null || request.getCpu() == 0)
				|| (request.getRam() == null || request.getRam() == 0)
				|| (request.getStorage() == null || request.getStorage() == 0)
				|| (request.getUserName() == null || request.getUserName().isEmpty())
				|| (request.getName() == null || request.getName().isEmpty())
		) {
			throw new BusinessException(
						"The following fields are mandatory : name, resourceType, os, cpu, ram, storage, userName");
		}
		
		if(!ConstantsEnum.getAllResourceTypes().contains(request.getResourceType())) {
			throw new BusinessException("Please pass a valid ResourceType. Valid are : Mobile Device, Emulator, Mobile Hub, Server Machine");
		}
		
		/**
		 * 1. call to process the request by calling the Load Balancer Broker.
		 * 2. The Load Balancer will return the host to assign this resource.
		 * 3. If the resourceType is Server Machine, call Open stack API
		 * 4. Create the ResourceRequestAllocation with the generated Id from #3
		 * 5. Update Host on the allocation
		 */
		List<Host> matchingHosts = hostDAO.getHostsByResourceTypeAndOs(request.getResourceType(), request.getOs()); 
		
		boolean externalResource = false;
		String externalResourceId = null;
		
		Host assignedHost = null;
		// #1 and #2
		String hostName = LoadBalancerBroker.processRequestsUsingPSO(matchingHosts, request);
		if (hostName == null) {
			throw new BusinessException("Sorry we are unable to process your request at this time! Please try later!");
		}
		if(hostName != null) {
			
			assignedHost = hostDAO.getHostByName(hostName);
			
			if(request.getResourceType().equals(ConstantsEnum.ResourceType.SERVER_MACHINE.getName())) {
				//Call Open Stack API #3
				
				// Get the appropriate flavor for the request.
				List<Flavor> flavors = OpenStackApiUtil.getAllFlavorDetails(assignedHost.getName(), assignedHost.getTenantId());
				
				String flavorRef = null;
				String imageRef = null;
				
				for(Flavor flavor : flavors) {
					if(flavor.getRam() == Math.round(request.getRam()) 
							&& flavor.getDisk() == Math.round(request.getStorage())
							&& flavor.getVcpus() == Math.round(request.getCpu())) {
						flavorRef = flavor.getId();
						break;
					}
				}
				// Flavor doesnt exist. Create one
				if(flavorRef == null) {
					JSONObject jsonObj = OpenStackApiUtil.createFlavor(assignedHost.getName(), assignedHost.getTenantId(), String.valueOf(UUID.randomUUID()) , Math.round(request.getRam()), Math.round(request.getCpu()), Math.round(request.getStorage()));
					flavorRef = jsonObj.getJSONObject("flavor").getString("id");
				}
				
				
				// Get the image for the request.
				List<Image> images = OpenStackApiUtil.getAllImagesDetails(assignedHost.getName(), assignedHost.getTenantId());
				
				for(Image image : images) {
					if(image.getName().equals("cirros-0.3.2-x86_64-uec")) {
						imageRef = image.getId();
						break;
					}
				}
				
				externalResource = true;
				
				JSONObject createServerJSON = OpenStackApiUtil.createServer(assignedHost.getName(), assignedHost.getTenantId(), request.getName(), flavorRef, imageRef);
				
				externalResourceId = createServerJSON.getJSONObject("server").getString("id");
				
			}
			
		}
		
		
		ResourceRequestAllocation resourceRequestAllocation = new ResourceRequestAllocation(request.getName(), request.getResourceType(), request.getOs(), request.getCpu(), request.getRam(), request.getStorage(), request.getUserName());
		
		resourceRequestAllocation.setStatus("Active");
		resourceRequestAllocation.setAssignedHost(assignedHost.getName());
		resourceRequestAllocation.setCloud(assignedHost.getCloudName());
		resourceRequestAllocation.setExternalResource(externalResource);
		resourceRequestAllocation.setExternalResourceId(externalResourceId);
		
		Integer id = resourceRequestAllocationDAO.createResourceRequestAllocation(resourceRequestAllocation);
		
		// #5 update host allocation
		
		Float cpuAllocated = (assignedHost.getCpuAllocated() != null ? assignedHost.getCpuAllocated() : 0);
		Float storageAllocated = (assignedHost.getStorageAllocated() != null ? assignedHost.getStorageAllocated() : 0);
		Float ramAllocated = (assignedHost.getRamAllocated() != null ? assignedHost.getRamAllocated() : 0);
		
		assignedHost.setCpuAllocated(cpuAllocated + request.getCpu());
		assignedHost.setStorageAllocated(storageAllocated + request.getStorage());
		assignedHost.setRamAllocated(ramAllocated + request.getRam()); 
		
		hostDAO.updateHost(assignedHost);
		
		// update cloud Usage Index
		
		Cloud cloud = assignedHost.getCloud();
		
		Float cloudUsageIndex = 0.0f;
		
		for(Host cloudHost : cloud.getHosts()) {
			Float cpuUtilization = ((cloudHost.getTotalCPUUnits() != null && cloudHost
					.getCpuAllocated() != null) ? cloudHost.getCpuAllocated()
					/ cloudHost.getTotalCPUUnits() : 0); 
			
			Float memoryUtilization = ((cloudHost.getTotalRam() != null && cloudHost
					.getRamAllocated() != null) ? cloudHost.getRamAllocated()
					/ cloudHost.getTotalRam() : 0);
			
			Float diskUtilization = ((cloudHost.getTotalStorage() != null && cloudHost
					.getStorageAllocated() != null) ? cloudHost.getStorageAllocated()
					/ cloudHost.getTotalStorage() : 0);
			
			cloudUsageIndex += (cpuUtilization + memoryUtilization + diskUtilization) / 3;
		}
		
		cloudUsageIndex = cloudUsageIndex/cloud.getHosts().size();
		
		cloud.setUsageIndex(cloudUsageIndex);
		cloudDAO.updateCloud(cloud);
		
		
		return id;
	}
	
	public ResourceRequestAllocationResponse getById(Integer id) throws BusinessException {
		
		ResourceRequestAllocationResponse response = new ResourceRequestAllocationResponse();
		
		ResourceRequestAllocation resourceRequestAllocation = 
				resourceRequestAllocationDAO.getResourceRequestAllocationById(id);
		
		if(resourceRequestAllocation == null) {
			throw new BusinessException("Resource not found with id : " + id); 
		}
		response.setId(id);
		response.setResourceType(resourceRequestAllocation.getResourceType());
		response.setOs(resourceRequestAllocation.getOs());
		response.setCpu(resourceRequestAllocation.getCpu());
		response.setRam(resourceRequestAllocation.getRam());
		response.setStorage(resourceRequestAllocation.getStorage());
		response.setAssignedCloud(resourceRequestAllocation.getCloud());
		response.setAssignedHost(resourceRequestAllocation.getAssignedHost());
		response.setUserName(resourceRequestAllocation.getUserName());
		
		if(resourceRequestAllocation.getAssignedHost() == null || resourceRequestAllocation.getCloud() == null) {
			response.setStatus("Pending");
		} else {
			response.setStatus(resourceRequestAllocation.getStatus());
		}
		
		return response;
		
	}
	
	public void loadResourceRequestAllocationRequests() throws IOException {
		
		//read json file data to String
        InputStream is = getClass().getClassLoader().getResourceAsStream("requestLoader.json");
        
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
         
        //convert json string to object
        RequestLoader request = objectMapper.readValue(is, RequestLoader.class);
        
		logger.info("request loader size : " + request.getRequests().size());
		
		for(CreateResourceRequestAllocationRequest rraRequest : request.getRequests()) {
			try {
				createResourceRequestAllocation(rraRequest);
			} catch (BusinessException e) {
				logger.error(e);
			} catch (HibernateException e) {
				logger.error(e);
			}
		}
	}
	
	public GetResourceRequestsResponse getResourceRequests(String assignedHost, String assignedCloud, String resourceType) {
		
		GetResourceRequestsResponse response = new GetResourceRequestsResponse();
		
		List<ResourceRequestAllocationResponse> resourceRequestsResponses = new ArrayList<ResourceRequestAllocationResponse>();
		
		List<ResourceRequestAllocation> listOfResourceRequests = null;
		
		if(assignedHost != null && !assignedHost.isEmpty()) {
			listOfResourceRequests = resourceRequestAllocationDAO.getResourceRequestAllocationByAssignedHost(assignedHost); 
		} else if(assignedCloud != null && !assignedCloud.isEmpty()) {
			listOfResourceRequests = resourceRequestAllocationDAO.getResourceRequestAllocationByAssignedCloud(assignedCloud);
		} else if(resourceType != null && !resourceType.isEmpty()) {
			listOfResourceRequests = resourceRequestAllocationDAO.getResourceRequestAllocationByResourceType(resourceType);
		} else {
			listOfResourceRequests = resourceRequestAllocationDAO.getResourceRequestAllocations();
		}
		
		for(ResourceRequestAllocation resourceRequestAllocation : listOfResourceRequests) {
			
			ResourceRequestAllocationResponse rraResp = new ResourceRequestAllocationResponse();
			
			rraResp.setResourceType(resourceRequestAllocation.getResourceType());
			rraResp.setName(resourceRequestAllocation.getName());
			rraResp.setOs(resourceRequestAllocation.getOs());
			rraResp.setCpu(resourceRequestAllocation.getCpu());
			rraResp.setRam(resourceRequestAllocation.getRam());
			rraResp.setStorage(resourceRequestAllocation.getStorage());
			rraResp.setAssignedCloud(resourceRequestAllocation.getCloud());
			rraResp.setAssignedHost(resourceRequestAllocation.getAssignedHost());
			rraResp.setUserName(resourceRequestAllocation.getUserName());
			rraResp.setCpuHours(resourceRequestAllocation.getCPUHours());
			rraResp.setRamHours(resourceRequestAllocation.getRamHours());
			rraResp.setStorageHours(resourceRequestAllocation.getStorageHours());
			
			rraResp.setCpuCost(df.format(resourceRequestAllocation.getCPUCost()));
			rraResp.setRamCost(df.format(resourceRequestAllocation.getRamCost()));
			rraResp.setStorageCost(df.format(resourceRequestAllocation.getStorageCost()));
			
			rraResp.setStatus(resourceRequestAllocation.getStatus());
			
			resourceRequestsResponses.add(rraResp);
		}
		
		response.setResourceRequests(resourceRequestsResponses);
		
		return response;
		
	}
	
	public List<UsersResponse> getUsers() {
		return resourceRequestAllocationDAO.getUsers();
	}
	
	public GetResourcesByUserNameResponse getResourcesByUserName(String userName) throws BusinessException {
		
		GetResourcesByUserNameResponse response = new GetResourcesByUserNameResponse();
		
		List<ResourceRequestAllocation> userResources = resourceRequestAllocationDAO.getResourceRequestAllocationByUserName(userName);
		
		List<ResourceRequestAllocationResponse> userResourcesResponse = new ArrayList<ResourceRequestAllocationResponse>();
		
		Float totalRamHours = 0f;
		Float totalDiskHours = 0f;
		Float totalCPUHours = 0f;
		Double totalRamCost = 0d;
		Double totalDiskCost = 0d;
		Double totalCPUCost = 0d;
		Double totalCost = 0d;
		
		
		for(ResourceRequestAllocation resourceRequestAllocation : userResources) {
			//Build the GetResourcesByUserNameResponse
			ResourceRequestAllocationResponse rraResp = new ResourceRequestAllocationResponse();
			
			rraResp.setResourceType(resourceRequestAllocation.getResourceType());
			rraResp.setName(resourceRequestAllocation.getName());
			rraResp.setOs(resourceRequestAllocation.getOs());
			rraResp.setCpu(resourceRequestAllocation.getCpu());
			rraResp.setRam(resourceRequestAllocation.getRam());
			rraResp.setStorage(resourceRequestAllocation.getStorage());
			rraResp.setAssignedCloud(resourceRequestAllocation.getCloud());
			rraResp.setAssignedHost(resourceRequestAllocation.getAssignedHost());
			rraResp.setUserName(resourceRequestAllocation.getUserName());
			rraResp.setCpuHours(resourceRequestAllocation.getCPUHours());
			rraResp.setRamHours(resourceRequestAllocation.getRamHours());
			rraResp.setStorageHours(resourceRequestAllocation.getStorageHours());
			
			rraResp.setCpuCost(df.format(resourceRequestAllocation.getCPUCost()));
			rraResp.setRamCost(df.format(resourceRequestAllocation.getRamCost()));
			rraResp.setStorageCost(df.format(resourceRequestAllocation.getStorageCost()));
			
			rraResp.setStatus(resourceRequestAllocation.getStatus());
			
			Double ramCost = resourceRequestAllocation.getRamCost();
			Double diskCost = resourceRequestAllocation.getStorageCost();
			Double cpuCost = resourceRequestAllocation.getCPUCost();
			
			totalRamHours += resourceRequestAllocation.getRamHours();
			totalDiskHours += resourceRequestAllocation.getStorageHours();
			totalCPUHours += resourceRequestAllocation.getCPUHours();
			
			totalRamCost += ramCost;
			totalDiskCost += diskCost;
			totalCPUCost += cpuCost;
			
			userResourcesResponse.add(rraResp);
		}
		
		totalCost = totalCPUCost + totalDiskCost + totalRamCost;
		
		response.setTotalCPUHours(totalCPUHours);
		response.setTotalDiskHours(totalDiskHours);
		response.setTotalRamHours(totalRamHours);
		response.setTotalCPUCost(df.format(totalCPUCost));
		response.setTotalDiskCost(df.format(totalDiskCost));
		response.setTotalRamCost(df.format(totalRamCost));
		
		response.setTotalCost(df.format(totalCost)); 
		
		response.setUserResources(userResourcesResponse);
		
		return response;
		
	}
	
	private static DecimalFormat df = new DecimalFormat("#.##");
	
	private final static Logger logger = Logger.getLogger(ResourceRequestAllocationService.class);
	
}
