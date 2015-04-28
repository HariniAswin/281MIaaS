package com.cmpe281.team.miaas.loadbalancer;

import java.util.List;
import java.util.Map.Entry;
import java.util.TreeMap;

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
		Float usageIndex = 0.0f;
		TreeMap<Host, Float> map = new TreeMap<Host, Float>();
		
		if(hosts.size() > 0) {
			for(Host host : hosts) {
				usageIndex = (host.getCpuUtilization() + host.getRamUtilization() + host.getStorageUtilization()) / 3;
				if(host.getAvailableCPU() > request.getCpu() && host.getAvailableRAM() > request.getRam()
						&& host.getAvailableStorage() > request.getStorage()) {
					
					Float totalUsageIndex = host.getCloud().getUsageIndex() + usageIndex;
					
					map.put(host, totalUsageIndex);
				}
			}
		}
		
		Entry<Host, Float> min = null;
		
		for (Entry<Host, Float> entry : map.entrySet()) {
		    if (min == null || min.getValue() > entry.getValue()) {
		        min = entry;
		    }
		}
		
		assignedHost = min.getKey();
		
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
