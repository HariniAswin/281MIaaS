package com.cmpe281.team2.miaas.openstack.client.restws.filter;

import java.io.IOException;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;

import org.apache.log4j.Logger;

import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackTokenClientFactory;

public class OpenstackApiClientRequestFilter implements ClientRequestFilter {

	private final static Logger logger = Logger
			.getLogger(OpenstackApiClientRequestFilter.class);

	@Override
	public void filter(ClientRequestContext requestContext) throws IOException {
		try {
			requestContext.getHeaders().putSingle("Content-Type",
					"application/json");
			requestContext.getHeaders().putSingle("X-Auth-Token",
					OpenStackTokenClientFactory.getToken(false));
		} catch (Exception e) {
			logger.error(e);
		}

		logger.info("URL: " + requestContext.getUri());
		logger.info("Method: " + requestContext.getMethod());
		logger.info("Body: " + requestContext.getEntity());
		logger.info("Headers: " + requestContext.getHeaders());
	}

}
