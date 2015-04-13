package com.cmpe281.team2.miaas.openstack.client.restws.test;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackApiUtil;
import com.cmpe281.team2.miaas.openstack.client.restws.OpenStackTokenClientFactory;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Flavor;
import com.cmpe281.team2.miaas.openstack.client.restws.model.Image;

public class OpenStackClientTest {
	
	private static final Logger _log = Logger.getLogger(OpenStackClientTest.class);

	@Test
	public void testToken() {
		
		try {
			String token1 = OpenStackTokenClientFactory.getToken(false);
			_log.info("token1 = "+ token1);
			
			String token2 = OpenStackTokenClientFactory.getToken(false);
			_log.info("token2 = "+ token2);
			
			Assert.assertEquals(token1, token2);
		} catch(Exception e) {
			_log.info(e);
		}
	}
	
	@Test
	public void testTenantId() {
		
		try {
			String tenantId = OpenStackTokenClientFactory.getTenantId(false);
			_log.info("TenantId = "+ tenantId);
			
			Assert.assertNotNull(tenantId);
		} catch(Exception e) {
			_log.info(e);
		}
	}
	
	@Test
	public void testFlavorsApi() {
		
		try {
			List<Flavor> flavors = OpenStackApiUtil.getAllFlavorDetails("US-West-Unix", "9859cbd549e94317b4fc230c09ea09c2");
			_log.info("flavors size = "+ flavors.size());
			
			Assert.assertNotNull(flavors);
			
		} catch(Exception e) {
			_log.info(e);
		}
	}
	
	@Test
	public void testImages() {
		
		try {
			List<Image> flavors = OpenStackApiUtil.getAllImagesDetails("US-West-Unix", "9859cbd549e94317b4fc230c09ea09c2");
			_log.info("images size = "+ flavors.size());
			
			Assert.assertNotNull(flavors);
			
		} catch(Exception e) {
			_log.info(e);
		}
	}
	
	

}
