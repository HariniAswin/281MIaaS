package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class CloudResponse {
	
	private String cloudName;
	private String location;
	private Float usageIndex;
	private List<HostResponse> hosts;
	
	public String getCloudName() {
		return cloudName;
	}
	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Float getUsageIndex() {
		return usageIndex;
	}
	public void setUsageIndex(Float usageIndex) {
		this.usageIndex = usageIndex;
	}
	public List<HostResponse> getHosts() {
		return hosts;
	}
	public void setHosts(List<HostResponse> hosts) {
		this.hosts = hosts;
	}
	
	
	
}
