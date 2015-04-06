package com.cmpe281.team2.miaas.restws.model;

public class CreateHostRequest {
	
	private String hostName;
	private String os;
	private Float totalCPUUnits;
	private Float totalRam;
	private Float totalStorage;
	private String resourceType;
	private String cloud;
	
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Float getTotalCPUUnits() {
		return totalCPUUnits;
	}
	public void setTotalCPUUnits(Float totalCPUUnits) {
		this.totalCPUUnits = totalCPUUnits;
	}
	public Float getTotalRam() {
		return totalRam;
	}
	public void setTotalRam(Float totalRam) {
		this.totalRam = totalRam;
	}
	public Float getTotalStorage() {
		return totalStorage;
	}
	public void setTotalStorage(Float totalStorage) {
		this.totalStorage = totalStorage;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getCloud() {
		return cloud;
	}
	public void setCloud(String cloud) {
		this.cloud = cloud;
	}
	
}
