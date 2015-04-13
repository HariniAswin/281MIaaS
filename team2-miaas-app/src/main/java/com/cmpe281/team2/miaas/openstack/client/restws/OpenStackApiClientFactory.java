package com.cmpe281.team2.miaas.openstack.client.restws;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.WebTarget;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;

import com.cmpe281.team2.miaas.commons.util.PropsUtil;
import com.cmpe281.team2.miaas.openstack.client.restws.filter.OpenstackApiClientRequestFilter;

public class OpenStackApiClientFactory extends OpenStackTokenClientFactory {
	
	public static final WebTarget getOpenStackNovaClient() {
		Client client = getClient()
				.register(OpenstackApiClientRequestFilter.class);
		WebTarget target = client.target(PropsUtil.get("openstack.api.nova.host"));
		target.register(JacksonJsonProvider.class);
		return target;
	}
	
	public static final WebTarget getOpenStackKeystoneClient() {
		Client client = getClient()
				.register(OpenstackApiClientRequestFilter.class);
		WebTarget target = client.target(PropsUtil.get("openstack.api.keystone.host"));
		target.register(JacksonJsonProvider.class);
		return target;
	}
}	
