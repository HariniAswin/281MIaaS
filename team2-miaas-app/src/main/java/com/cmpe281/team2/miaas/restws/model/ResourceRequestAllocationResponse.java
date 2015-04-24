package com.cmpe281.team2.miaas.restws.model;

public class ResourceRequestAllocationResponse {
	
	private Integer id;
	private String name;
	private String resourceType;
	private String os;
	private Float cpu;
	private Float ram;
	private Float storage;
	private String userName;
	private String assignedHost;
	private String assignedCloud;
	private String status;
	private Float ramHours;
	private Float storageHours;
	private Float cpuHours;
	private String ramCost;
	private String storageCost;
	private String cpuCost;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getOs() {
		return os;
	}
	public void setOs(String os) {
		this.os = os;
	}
	public Float getCpu() {
		return cpu;
	}
	public void setCpu(Float cpu) {
		this.cpu = cpu;
	}
	public Float getRam() {
		return ram;
	}
	public void setRam(Float ram) {
		this.ram = ram;
	}
	public Float getStorage() {
		return storage;
	}
	public void setStorage(Float storage) {
		this.storage = storage;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAssignedHost() {
		return assignedHost;
	}
	public void setAssignedHost(String assignedHost) {
		this.assignedHost = assignedHost;
	}
	public String getAssignedCloud() {
		return assignedCloud;
	}
	public void setAssignedCloud(String assignedCloud) {
		this.assignedCloud = assignedCloud;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Float getRamHours() {
		return ramHours;
	}
	public void setRamHours(Float ramHours) {
		this.ramHours = ramHours;
	}
	public Float getStorageHours() {
		return storageHours;
	}
	public void setStorageHours(Float storageHours) {
		this.storageHours = storageHours;
	}
	public Float getCpuHours() {
		return cpuHours;
	}
	public void setCpuHours(Float cpuHours) {
		this.cpuHours = cpuHours;
	}
	public String getRamCost() {
		return ramCost;
	}
	public void setRamCost(String ramCost) {
		this.ramCost = ramCost;
	}
	public String getStorageCost() {
		return storageCost;
	}
	public void setStorageCost(String storageCost) {
		this.storageCost = storageCost;
	}
	public String getCpuCost() {
		return cpuCost;
	}
	public void setCpuCost(String cpuCost) {
		this.cpuCost = cpuCost;
	}

}
