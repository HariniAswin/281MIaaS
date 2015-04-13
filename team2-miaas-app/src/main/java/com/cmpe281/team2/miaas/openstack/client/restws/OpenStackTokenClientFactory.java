package com.cmpe281.team2.miaas.openstack.client.restws;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.json.JSONException;
import org.json.JSONObject;

import com.cmpe281.team2.miaas.commons.util.CacheMapUtil;
import com.cmpe281.team2.miaas.commons.util.PropsUtil;
import com.cmpe281.team2.miaas.exception.BusinessException;

public class OpenStackTokenClientFactory {
	
	protected static Client getClient() {
		return ClientBuilder.newClient().register(CustomObjectMapper.class);
	}
	
	public static final String getToken(boolean forceNew) throws Exception {
		String token = CacheMapUtil.get("openstackApiToken");
		if (token == null || forceNew) {
			token = generateToken();
			logger.info("Token Expired. New Token Generated : " + token);
		}
		return token;
	}
	
	public static final String getTenantId(boolean forceNew) throws BusinessException {
		String tenantId = CacheMapUtil.get("tenantId");
		if (tenantId == null) {
			generateToken();
			tenantId = CacheMapUtil.get("tenantId");
			logger.info("TenantId is null. Regenerate token to retrieve tenantId : " + tenantId);
		}
		return tenantId;
	}
	
	public static final String generateToken() throws BusinessException {
		return generateToken(PropsUtil.get("openstack.api.tenant"));
	}
	
	public static final String generateToken(String tenantName) throws BusinessException {
		
		Client client = getClient();
		
		WebTarget target = client.target(PropsUtil.get("openstack.api.token.url"));
		target.register(JacksonJsonProvider.class);
		
		Invocation.Builder invocationBuilder = target.request();
		
		invocationBuilder.header("Content-Type", "application/json");
		
		String jsonRequest = String
				.format("{\"auth\":{\"passwordCredentials\":{\"username\":\"%s\",\"password\":\"%s\"},\"tenantName\":\"%s\"}}",
						PropsUtil.get("openstack.api.token.username"),
						PropsUtil.get("openstack.api.token.password"),
						tenantName);
		
		Entity<String> entity = Entity.entity(jsonRequest, MediaType.APPLICATION_JSON_TYPE);
		
		Response response = invocationBuilder.post(entity);
		
		String jsonResponse = response.readEntity(String.class);
		
		if(response.getStatus() == 200) {
			
			logger.info("Openstack API Response : " + jsonResponse);
			
			try {
				JSONObject json = new JSONObject(jsonResponse);
				
				String token = json.getJSONObject("access").getJSONObject("token").getString("id");
				String tenantId = json.getJSONObject("access").getJSONObject("token").getJSONObject("tenant").getString("id");
				
				CacheMapUtil.put("openstackApiToken", token);
				CacheMapUtil.put("tenantId", tenantId);
				
				return json.getJSONObject("access").getJSONObject("token").getString("id");
				
			} catch(JSONException e) {
				logger.error(e);
				throw new BusinessException("Openstack Token Generation failed");
			}
			
		} else {
			throw new BusinessException("Openstack Token Generation failed");
		}
		
	}
	
	
	private final static Logger logger = Logger.getLogger(OpenStackTokenClientFactory.class);
}
