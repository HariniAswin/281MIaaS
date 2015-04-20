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
			assignedHost = processRequestsUsingPSO(hosts, request);
		}
		logger.info("End Processing the request for Load Balancing");
		
		return assignedHost;
		
	}
	
	/*Allocates the request to the best host based on available CPU, RAM and storage*/
	public static synchronized Host processRequestsUsingPSO(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Particle Swarm Optimization");
		
		Host assignedHost = null;
		
		if(hosts.size() > 0) {
			
			for(Host host : hosts) {
				if(host.getAvailableCPU() > request.getCpu() && host.getAvailableRAM() > request.getRam()
						&& host.getAvailableStorage() > request.getStorage()) {
					assignedHost = host;
					break;
				}
			}
		}
		logger.info("End Processing the request using Particle Swarm Optimization");
		
		return assignedHost;
	}
	
	public static synchronized Host processRequestsUsingACO(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Ant Colony Optimization");
		
		logger.info("End Processing the request using Ant Colony Optimization");
		return null;
	}
	
	/*Allocates the request to the best host based on maximum processing time and CPU utilization*/
	public static synchronized Host processRequestsUsingGA(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Genetic Algorithm Optimization");
		
		logger.info("End Processing the request using Genetic Algorithm Optimization");
		return null;
	}
	
	public static synchronized Host processRequestsUsingABC(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Artificial Bee Colony Optimization");
		
		logger.info("End Processing the request using Artificial Bee Colony Optimization");
		return null;
	}
	
	private final static Logger logger = Logger.getLogger(LoadBalancerBroker.class);
}
