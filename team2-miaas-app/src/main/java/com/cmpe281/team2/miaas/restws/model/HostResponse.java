package com.cmpe281.team2.miaas.restws.model;

public class HostResponse {
	
	private String hostName;
	private String os;
	private Float totalCPUs;
	private Float totalRAM;
	private Float totalStorage;
	private Float cpuAllocated;
	private Float ramAllocated;
	private Float storageAllocated;
	private String resourceType;
	private Float cpuUtilization;
	private Float memoryUtilization;
	private Float diskUtilization;
	private Float freeCpus;
	private Float freeRAM;
	private Float freeStorage;
	private Float usageIndex;
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
	public Float getTotalCPUs() {
		return totalCPUs;
	}
	public void setTotalCPUs(Float totalCPUs) {
		this.totalCPUs = totalCPUs;
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
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public Float getCpuUtilization() {
		return cpuUtilization;
	}
	public void setCpuUtilization(Float cpuUtilization) {
		this.cpuUtilization = cpuUtilization;
	}
	public Float getMemoryUtilization() {
		return memoryUtilization;
	}
	public void setMemoryUtilization(Float memoryUtilization) {
		this.memoryUtilization = memoryUtilization;
	}
	public Float getDiskUtilization() {
		return diskUtilization;
	}
	public void setDiskUtilization(Float diskUtilization) {
		this.diskUtilization = diskUtilization;
	}
	public Float getFreeCpus() {
		return freeCpus;
	}
	public void setFreeCpus(Float freeCpus) {
		this.freeCpus = freeCpus;
	}
	public Float getFreeRAM() {
		return freeRAM;
	}
	public void setFreeRAM(Float freeRAM) {
		this.freeRAM = freeRAM;
	}
	public Float getFreeStorage() {
		return freeStorage;
	}
	public void setFreeStorage(Float freeStorage) {
		this.freeStorage = freeStorage;
	}
	public Float getUsageIndex() {
		return usageIndex;
	}
	public void setUsageIndex(Float usageIndex) {
		this.usageIndex = usageIndex;
	}
	public String getCloud() {
		return cloud;
	}
	public void setCloud(String cloud) {
		this.cloud = cloud;
	}
	
	
}
