package com.cmpe281.team2.miaas.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team.miaas.loadbalancer.LoadBalancerBroker;
import com.cmpe281.team2.miaas.constants.ConstantsEnum;
import com.cmpe281.team2.miaas.dao.HostDAO;
import com.cmpe281.team2.miaas.dao.ResourceRequestAllocationDAO;
import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.entity.ResourceRequestAllocation;
import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackApiUtil;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Flavor;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Image;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;
import com.cmpe281.team2.miaas.restws.model.GetResourcesByUserNameResponse;
import com.cmpe281.team2.miaas.restws.model.RequestLoader;
import com.cmpe281.team2.miaas.restws.model.ResourceRequestAllocationResponse;

@Service
@Transactional
public class ResourceRequestAllocationService {
	
	@Autowired
	ResourceRequestAllocationDAO resourceRequestAllocationDAO;
	
	@Autowired
	HostDAO hostDAO;
	
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
		
		// #1 and #2
		Host assignedHost = LoadBalancerBroker.processRequestUsingFakeLoadBalancer(matchingHosts, request);
		
		if(assignedHost != null) {
			
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
					JSONObject jsonObj = OpenStackApiUtil.createFlavor(assignedHost.getName(), assignedHost.getTenantId(), request.getOs() + "_" + request.getUserName() + "_flavor" , Math.round(request.getRam()), Math.round(request.getCpu()), Math.round(request.getStorage()));
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
	
	public GetResourcesByUserNameResponse getResourcesByUserName(String userName) throws BusinessException {
		
		GetResourcesByUserNameResponse response = new GetResourcesByUserNameResponse();
		
		List<ResourceRequestAllocation> userResources = resourceRequestAllocationDAO.getResourceRequestAllocationByUserName(userName);
		
		List<ResourceRequestAllocationResponse> userResourcesResponse = new ArrayList<ResourceRequestAllocationResponse>();
		
		/**
		 * TODO
		 * 1. Add a new Column in the ResourceRequestAllocation table (createDate)
		 * 2. Add the ORM mapping in the ResourceRequestAllocation.java
		 * 3. Build the userResources 
		 * 4. Based on the createDate and the number of cpu, ram and storage, 
		 * 	  calculate the ramHours, diskHours, cpuHours
		 * 5. From the ramHours, storageHours and cpuHours, calculate the ramCost, cpuCost and storageCost
		 * 	  by assigning a unit cost for ram, cpu and storage 
		 * 6. Build the response from the above values and return it
		 * 
		 */
		
		
		for(ResourceRequestAllocation rra : userResources) {
			//Build the GetResourcesByUserNameResponse
			ResourceRequestAllocationResponse rraResp = new ResourceRequestAllocationResponse();
			
			
			userResourcesResponse.add(rraResp);
		}
		
		
		return response;
		
	}

	private final static Logger logger = Logger.getLogger(ResourceRequestAllocationService.class);
	
}
