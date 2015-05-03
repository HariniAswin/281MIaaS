package com.cmpe281.team2.miaas.restws.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.restws.model.CloudResponse;
import com.cmpe281.team2.miaas.restws.model.CloudStatisticsResponse;
import com.cmpe281.team2.miaas.restws.model.CreateCloudRequest;
import com.cmpe281.team2.miaas.restws.model.CreateHostRequest;
import com.cmpe281.team2.miaas.restws.model.GenericResponse;
import com.cmpe281.team2.miaas.restws.model.HostResponse;
import com.cmpe281.team2.miaas.service.CloudService;
import com.cmpe281.team2.miaas.service.HostService;

@Component
@Path("/cloud")
public class CloudApi {

	private final static Logger logger = Logger.getLogger(CloudApi.class);

	@Autowired
	private CloudService cloudService;

	@Autowired
	private HostService hostService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createCloud(CreateCloudRequest request) {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		try {
			String cloudName = cloudService.createCloud(request);
			gr.setHasErrors(false);
			gr.setErrorMessage(null);
			gr.setStatusCode(Status.OK.getStatusCode());
			gr.setData(cloudName);
			response = Response.status(Status.OK).entity(gr).build();
		} catch (BusinessException e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr)
					.build();
		}

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/name/{cloudName}")
	public Response getCloudByName(@PathParam("cloudName") String cloudName) {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		try {
			CloudResponse resp = cloudService
					.getCloudStatisticsByName(cloudName);
			gr.setData(resp);
			response = Response.status(Status.OK).entity(gr).build();
		} catch (BusinessException e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr)
					.build();
		}

		return response;
	}
	

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("{cloudName}/host")
	public Response createHost(@PathParam("cloudName") String cloud,
			CreateHostRequest request) {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		try {

			String hostName = hostService.createHost(cloud, request);
			gr.setHasErrors(false);
			gr.setErrorMessage(null);
			gr.setStatusCode(Status.OK.getStatusCode());
			gr.setData(hostName);
			response = Response.status(Status.OK).entity(gr).build();
		} catch (BusinessException e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.OK).entity(gr)
					.build();
		}

		return response;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("setup")
	public Response setupCloud() {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		try {

			cloudService.setupCloud();

			gr.setData("Cloud Setup done");
			gr.setHasErrors(false);
			gr.setErrorMessage(null);
			gr.setStatusCode(Status.OK.getStatusCode());
			response = Response.status(Status.OK).entity(gr).build();
		} catch (Exception e) {
			logger.error(e);
			gr.setHasErrors(true);
			gr.setErrorMessage(e.getMessage());
			gr.setStatusCode(Status.INTERNAL_SERVER_ERROR.getStatusCode());
			response = Response.status(Status.INTERNAL_SERVER_ERROR).entity(gr)
					.build();
		}

		return response;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/statistics")
	public Response getCount() {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		CloudStatisticsResponse resp = cloudService.getCloudStatistics();
		gr.setData(resp);
		gr.setStatusCode(Status.OK.getStatusCode());
		response = Response.status(Status.OK).entity(gr).build();

		return response;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/hosts")
	public Response getHosts(@QueryParam("resourceType") String resourceType) {
		Response response = null;

		GenericResponse gr = new GenericResponse();
		List<HostResponse> resp = hostService.getHostsByResourceType(resourceType);
		gr.setData(resp);
		gr.setStatusCode(Status.OK.getStatusCode());
		response = Response.status(Status.OK).entity(gr).build();

		return response;
	}
	
}
