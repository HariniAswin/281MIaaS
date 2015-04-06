package com.cmpe281.team2.miaas.restws.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;
import com.cmpe281.team2.miaas.restws.model.GenericResponse;
import com.cmpe281.team2.miaas.restws.model.ResourceRequestAllocationResponse;
import com.cmpe281.team2.miaas.service.ResourceRequestAllocationService;

@Component
@Path("/resource")
public class ResourceRequestAllocationApi {
	
	private final static Logger logger = Logger.getLogger(ResourceRequestAllocationApi.class);
	
	@Autowired
    private ResourceRequestAllocationService resourceRequestAllocationService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/allocate")
	public Response allocateResource(CreateResourceRequestAllocationRequest request) {
		Response response = null;
		
		GenericResponse gr = new GenericResponse();
		try {
			Integer requestId = resourceRequestAllocationService.createResourceRequestAllocation(request);
			gr.setHasErrors(false);
			gr.setErrorMessage(null);
			gr.setStatusCode(Status.OK.getStatusCode());
			gr.setData(requestId);
			response = Response.status(Status.OK).entity(gr).build();
		} catch (BusinessException e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr).build();
		} 
		
		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Response getCloudByName(@PathParam("id") Integer id) {
		Response response = null;
		
		GenericResponse gr = new GenericResponse();
		try {
			ResourceRequestAllocationResponse resp = resourceRequestAllocationService.getById(id);
			gr.setData(resp);
			response = Response.status(Status.OK).entity(gr).build();
		} catch (BusinessException e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr).build();
		}
		
		return response;
	}
	
}
