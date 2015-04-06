package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class SetupCloudRequest {
	
	private List<CreateCloudRequest> clouds;
	private List<CreateHostRequest> hosts;
	
	public List<CreateCloudRequest> getClouds() {
		return clouds;
	}
	public void setClouds(List<CreateCloudRequest> clouds) {
		this.clouds = clouds;
	}
	public List<CreateHostRequest> getHosts() {
		return hosts;
	}
	public void setHosts(List<CreateHostRequest> hosts) {
		this.hosts = hosts;
	}
	

}
