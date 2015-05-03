package com.cmpe281.team2.miaas.restws.model;

public class CloudRequestsResponse {
	
	private String cloudName;
	private Integer numberOfRequests;
	
	private Integer numberOfHosts;
	
	private Float totalCPU;
	private Float totalRAM;
	private Float totalStorage;
	
	private Float cpuAllocated;
	private Float ramAllocated;
	private Float storageAllocated;
	
	public String getCloudName() {
		return cloudName;
	}
	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}
	public Integer getNumberOfRequests() {
		return numberOfRequests;
	}
	public void setNumberOfRequests(Integer numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}
	public Integer getNumberOfHosts() {
		return numberOfHosts;
	}
	public void setNumberOfHosts(Integer numberOfHosts) {
		this.numberOfHosts = numberOfHosts;
	}
	public Float getTotalCPU() {
		return totalCPU;
	}
	public void setTotalCPU(Float totalCPU) {
		this.totalCPU = totalCPU;
	}
	public Float getTotalRAM() {
		return totalRAM;
	}
	public void setTotalRAM(Float totalRAM) {
		this.totalRAM = totalRAM;
	}
	public Float getTotalStorage() {
		return totalStorage;
	}
	public void setTotalStorage(Float totalStorage) {
		this.totalStorage = totalStorage;
	}
	public Float getCpuAllocated() {
		return cpuAllocated;
	}
	public void setCpuAllocated(Float cpuAllocated) {
		this.cpuAllocated = cpuAllocated;
	}
	public Float getRamAllocated() {
		return ramAllocated;
	}
	public void setRamAllocated(Float ramAllocated) {
		this.ramAllocated = ramAllocated;
	}
	public Float getStorageAllocated() {
		return storageAllocated;
	}
	public void setStorageAllocated(Float storageAllocated) {
		this.storageAllocated = storageAllocated;
	}

}
