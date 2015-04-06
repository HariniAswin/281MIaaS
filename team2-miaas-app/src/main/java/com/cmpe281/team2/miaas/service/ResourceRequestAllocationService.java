package com.cmpe281.team2.miaas.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cmpe281.team2.miaas.constants.ConstantsEnum;
import com.cmpe281.team2.miaas.dao.ResourceRequestAllocationDAO;
import com.cmpe281.team2.miaas.entity.ResourceRequestAllocation;
import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;
import com.cmpe281.team2.miaas.restws.model.RequestLoader;
import com.cmpe281.team2.miaas.restws.model.ResourceRequestAllocationResponse;

@Service
@Transactional
public class ResourceRequestAllocationService {
	
	@Autowired
	ResourceRequestAllocationDAO resourceRequestAllocationDAO;
	
	public Integer createResourceRequestAllocation(CreateResourceRequestAllocationRequest request) throws BusinessException {
		
		if((request.getResourceType() == null || request.getResourceType().isEmpty())
				|| (request.getOs() == null || request.getOs().isEmpty())
				|| (request.getCpu() == null || request.getCpu() == 0)
				|| (request.getRam() == null || request.getRam() == 0)
				|| (request.getStorage() == null || request.getStorage() == 0)
				|| (request.getUserName() == null || request.getUserName().isEmpty())
		) {
			throw new BusinessException(
						"The following fields are mandatory : resourceType, os, cpu, ram, storage, userName");
		}
		
		if(!ConstantsEnum.getAllResourceTypes().contains(request.getResourceType())) {
			throw new BusinessException("Please pass a valid ResourceType. Valid are : Mobile Device, Emulator, Mobile Hub, Server Machine");
		}
		
		ResourceRequestAllocation resourceRequestAllocation = new ResourceRequestAllocation(request.getResourceType(), request.getOs(), request.getCpu(), request.getRam(), request.getStorage(), request.getUserName());
		
		Integer id = resourceRequestAllocationDAO.createResourceRequestAllocation(resourceRequestAllocation);
		
		// TODO Async call to process the request by calling the Load Balancer Broker. 
		//The Load Balancer will update the resource once it identifies the appropriate host and cloud
		
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

	private final static Logger logger = Logger.getLogger(ResourceRequestAllocationService.class);
	
}
