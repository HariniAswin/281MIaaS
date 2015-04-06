package com.cmpe281.team2.miaas.restws.api;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmpe281.team2.miaas.restws.model.GenericResponse;
import com.cmpe281.team2.miaas.service.ResourceRequestAllocationService;

@Component
@Path("/request-loader")
public class RequestLoaderApi {
	
	private final static Logger logger = Logger.getLogger(RequestLoaderApi.class);
	
	@Autowired
	ResourceRequestAllocationService resourceRequestAllocationService;
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response requestLoader() {
		Response response = null;
		
		GenericResponse gr = new GenericResponse();
		try {
			
			resourceRequestAllocationService.loadResourceRequestAllocationRequests();
			
			gr.setData("Request Loader done");
			gr.setHasErrors(false);
			gr.setErrorMessage(null);
			gr.setStatusCode(Status.OK.getStatusCode());
			response = Response.status(Status.OK).entity(gr).build();
		} catch (Exception e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr).build();
		} 
		
		return response;
	}
	
}
