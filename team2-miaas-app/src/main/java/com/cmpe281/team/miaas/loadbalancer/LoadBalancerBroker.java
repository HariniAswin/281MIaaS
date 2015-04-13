package com.cmpe281.team.miaas.loadbalancer;

import java.util.List;

import org.apache.log4j.Logger;

import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;

public class LoadBalancerBroker {
	
	public static synchronized Host processRequestUsingFakeLoadBalancer(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		
		logger.info("Start Processing the request for Load Balancing");
		
		Host assignedHost = null;
		
		if(hosts.size() > 0) {
			
			for(Host host : hosts) {
				assignedHost = host;
				break;
			}
			
		}
		logger.info("End Processing the request for Load Balancing");
		
		return assignedHost;
		
	}
	
	private final static Logger logger = Logger.getLogger(LoadBalancerBroker.class);
}
