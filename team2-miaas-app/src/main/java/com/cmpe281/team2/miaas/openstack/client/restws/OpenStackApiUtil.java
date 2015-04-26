package com.cmpe281.team2.miaas.openstack.client.restws;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmpe281.team2.miaas.exception.BusinessException;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Flavor;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Flavors;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Image;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Images;
import com.fasterxml.jackson.databind.ObjectMapper;

public class OpenStackApiUtil {

	public static JSONObject addProject(String project)
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory
				.getOpenStackKeystoneClient();
		target = target.path("/projects");
		Invocation.Builder invocationBuilder = target.request();

		String jsonRequest = String
				.format("{\"project\": {\"description\": \"\",\"enabled\": true,\"name\": \"%s\"}}",
						project);

		Entity<String> entity = Entity.entity(jsonRequest.toString(),
				MediaType.APPLICATION_JSON_TYPE);
		Response response = invocationBuilder.post(entity);

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401) {
			OpenStackApiClientFactory.generateToken();
			response = invocationBuilder.post(entity);
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Add Project JSON Response : " + jsonResponse);

		if (response.getStatus() == 201) {
			try {
				return new JSONObject(jsonResponse);
			} catch (JSONException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Add tenant API call failed.");
			}

		} else {
			throw new BusinessException("Openstack Add tenant API call failed.");
		}

	}
	
	public static void grantRoleToProjectUser(String projectId, String userId, String roleId) 
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory
				.getOpenStackKeystoneClient();
		target = target.path("/projects/" + projectId + "/users/" + userId + "/roles/" + roleId);
		
		Invocation.Builder invocationBuilder = target.request();
		
		String jsonRequest = "{}";
		
		Entity<String> entity = Entity.entity(jsonRequest.toString(),
				MediaType.APPLICATION_JSON_TYPE);

		Response response = invocationBuilder.put(entity);

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401) {
			OpenStackApiClientFactory.generateToken();
			response = invocationBuilder.put(entity);
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Grant Role JSON Response : " + jsonResponse);

		if (response.getStatus() == 204) {
			// success
			logger.info(String.format("Successfully Granted role %s to user %s", roleId, userId));
		} else {
			throw new BusinessException("Openstack Grant Role API call failed.");
		}

	}
	
	public static JSONObject getUserByName(String name)
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory
				.getOpenStackKeystoneClient();
		target = target.path("/users");
		target = target.queryParam("name", name);
		Invocation.Builder invocationBuilder = target.request();

		Response response = invocationBuilder.get();
		
		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401) {
			OpenStackApiClientFactory.generateToken();
			response = invocationBuilder.get();
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Get Users JSON Response : " + jsonResponse);

		if (response.getStatus() == 200) {
			try {
				return new JSONObject(jsonResponse);
			} catch (JSONException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Get Users API call failed.");
			}

		} else {
			throw new BusinessException("Openstack Get Users API call failed.");
		}

	}
	
	public static JSONObject getRoleByName(String name)
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory
				.getOpenStackKeystoneClient();
		target = target.path("/roles");
		target = target.queryParam("name", name);
		Invocation.Builder invocationBuilder = target.request();

		Response response = invocationBuilder.get();
		
		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401) {
			OpenStackApiClientFactory.generateToken();
			response = invocationBuilder.get();
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Get Roles JSON Response : " + jsonResponse);

		if (response.getStatus() == 200) {
			try {
				return new JSONObject(jsonResponse);
			} catch (JSONException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Get Roles API call failed.");
			}

		} else {
			throw new BusinessException("Openstack Get Roles API call failed.");
		}

	}

	public static List<Flavor> getAllFlavorDetails(String hostName,
			String tenantId) throws BusinessException {
		WebTarget target = OpenStackApiClientFactory.getOpenStackNovaClient();
		target = target.path("/" + tenantId + "/flavors/detail");
		Invocation.Builder invocationBuilder = target.request();

		Response response = invocationBuilder.get();

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401 || response.getStatus() == 400) {
			OpenStackApiClientFactory.generateToken(hostName);
			response = invocationBuilder.get();
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Get Flavor Details JSON Response : "
				+ jsonResponse);

		if (response.getStatus() == 200) {
			try {

				ObjectMapper mapper = new CustomObjectMapper();

				Flavors flavors = mapper.readValue(jsonResponse, Flavors.class);

				return flavors.getFlavors();

			} catch (JSONException | IOException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Create Flavor API call failed.");
			}

		} else {
			throw new BusinessException(
					"Openstack Create Flavor API call failed.");
		}

	}

	public static JSONObject createFlavor(String hostName, String tenantId,
			String flavorName, Integer ram, Integer cpus, Integer disk)
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory.getOpenStackNovaClient();
		target = target.path("/" + tenantId + "/flavors");
		Invocation.Builder invocationBuilder = target.request();

		String jsonRequest = String
				.format("{\"flavor\": {\"name\": \"%s\",\"ram\": %d,\"vcpus\": %d,\"disk\": %d}}",
						flavorName, ram, cpus, disk);

		Entity<String> entity = Entity.entity(jsonRequest.toString(),
				MediaType.APPLICATION_JSON_TYPE);
		Response response = invocationBuilder.post(entity);

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401 || response.getStatus() == 400) {
			OpenStackApiClientFactory.generateToken(hostName);
			response = invocationBuilder.post(entity);
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Create Flavor JSON Response : " + jsonResponse);

		if (response.getStatus() == 200) {
			try {
				return new JSONObject(jsonResponse);
			} catch (JSONException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Create Flavor API call failed.");
			}

		} else {
			throw new BusinessException(
					"Openstack Create Flavor API call failed.");
		}

	}

	public static List<Image> getAllImagesDetails(String hostName,
			String tenantId) throws BusinessException {
		WebTarget target = OpenStackApiClientFactory.getOpenStackNovaClient();
		target = target.path("/" + tenantId + "/images/detail");
		Invocation.Builder invocationBuilder = target.request();

		Response response = invocationBuilder.get();

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401 || response.getStatus() == 400) {
			OpenStackApiClientFactory.generateToken(hostName);
			response = invocationBuilder.get();
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Get Images Details JSON Response : "
				+ jsonResponse);

		if (response.getStatus() == 200) {
			try {

				ObjectMapper mapper = new CustomObjectMapper();

				Images images = mapper.readValue(jsonResponse, Images.class);

				return images.getImages();

			} catch (JSONException | IOException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Create Flavor API call failed.");
			}

		} else {
			throw new BusinessException(
					"Openstack Create Flavor API call failed.");
		}

	}

	public static JSONObject createServer(String hostName, String tenantId, 
			String name, String flavorRef, String imageRef)
			throws BusinessException {
		WebTarget target = OpenStackApiClientFactory.getOpenStackNovaClient();
		target = target.path("/" + tenantId + "/servers");
		Invocation.Builder invocationBuilder = target.request();

		String jsonRequest = String
				.format("{\"server\": {\"name\": \"%s\",\"imageRef\": \"%s\",\"flavorRef\": \"%s\",\"max_count\": 1,\"min_count\": 1,\"security_groups\": [{\"name\": \"default\"}]}}",
						name, imageRef, flavorRef);

		Entity<String> entity = Entity.entity(jsonRequest.toString(),
				MediaType.APPLICATION_JSON_TYPE);
		Response response = invocationBuilder.post(entity);

		// response is 401 (meaning token is expired. regenerate the token and
		// call the api again).
		if (response.getStatus() == 401 || response.getStatus() == 400) {
			OpenStackApiClientFactory.generateToken(hostName);
			response = invocationBuilder.post(entity);
		}

		String jsonResponse = response.readEntity(String.class);

		logger.info("Openstack Create Server JSON Response : " + jsonResponse);

		if (response.getStatus() == 202) {
			try {
				return new JSONObject(jsonResponse);
			} catch (JSONException e) {
				logger.error(e);
				throw new BusinessException(
						"Openstack Create Server API call failed.");
			}

		} else {
			throw new BusinessException("Openstack Create Server API call failed.");
		}

	}

	private final static Logger logger = Logger
			.getLogger(OpenStackApiUtil.class);

}
