package com.cmpe281.team.miaas.loadbalancer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import com.cmpe281.team2.miaas.entity.Host;
import com.cmpe281.team2.miaas.restws.model.CreateResourceRequestAllocationRequest;

public class LoadBalancerBroker {
	
	
	/*Allocates the request to the best host based on available CPU, RAM and storage*/
	public static synchronized String processRequestsUsingWeightedLeastConnections(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Weighted Least Connections");
		
		String assignedHost = null;
		Float usageIndex = 0.0f;
		TreeMap<String, Float> map = new TreeMap<String, Float>();
		
		if(hosts.size() > 0) {
			for(Host host : hosts) {
				usageIndex = (host.getCpuUtilization() + host.getRamUtilization() + host.getStorageUtilization()) / 3;
				if(host.getAvailableCPU() > request.getCpu() && host.getAvailableRAM() > request.getRam()
						&& host.getAvailableStorage() > request.getStorage()) {
					
					Float cloudUsageIndex = host.getCloud().getUsageIndex();
					
					Float bestFitUsageIndex = usageIndex + (cloudUsageIndex != null ? cloudUsageIndex : 0.0f);
					
					map.put(host.getName(), bestFitUsageIndex);
				}
			}
		}
		
		Entry<String, Float> min = null;
		
		for (Entry<String, Float> entry : map.entrySet()) {
		    if (min == null || min.getValue() > entry.getValue()) {
		        min = entry;
		    }
		}
		
		if(min != null)
			assignedHost = min.getKey();
		
		logger.info("End Processing the request using Weighted Least Connections");
		
		return assignedHost;
	}
	
	public static synchronized String processRequestsUsingRandomAlgorithm(List<Host> hosts, CreateResourceRequestAllocationRequest request) {
		logger.info("Start Processing the request using Random Algorithm");
		
		String assignedHost = null;
		
		List<Host> randomAssignableHosts = new ArrayList<Host>(); 
		
		if(hosts.size() > 0) {
			for(Host host : hosts) {
				if(host.getAvailableCPU() > request.getCpu() && host.getAvailableRAM() > request.getRam()
						&& host.getAvailableStorage() > request.getStorage()) {
					randomAssignableHosts.add(host);
				}
			}
		}
		
		if(randomAssignableHosts.size() > 0) {
			
			int randInt = randInt(0, randomAssignableHosts.size() - 1);
			
			logger.info("Host of size : " + randomAssignableHosts.size() + " generated a random index to assign : " + randInt);
			
			assignedHost = randomAssignableHosts.get(randInt).getName();
		}
		
		logger.info("End Processing the request using Random Algorithm");
		
		return assignedHost;
	}
	
	/**
	 * Returns a pseudo-random number between min and max, inclusive.
	 * The difference between min and max can be at most
	 * <code>Integer.MAX_VALUE - 1</code>.
	 *
	 * @param min Minimum value
	 * @param max Maximum value.  Must be greater than min.
	 * @return Integer between min and max, inclusive.
	 * @see java.util.Random#nextInt(int)
	 */
	private static int randInt(int min, int max) {

	    // NOTE: Usually this should be a field rather than a method
	    // variable so that it is not re-seeded every call.
	    Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	private final static Logger logger = Logger.getLogger(LoadBalancerBroker.class);
}
