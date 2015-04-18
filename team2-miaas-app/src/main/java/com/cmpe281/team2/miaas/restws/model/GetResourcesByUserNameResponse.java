package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class GetResourcesByUserNameResponse {
	
	private List<ResourceRequestAllocationResponse> userResources;
	
	private Float ramHours;
	
	private Float diskHours;
	
	private Float cpuHours;
	
	private Float ramCost;
	
	private Float diskCost;
	
	private Float cpuCost;
	
	private Float totalCost;

	public List<ResourceRequestAllocationResponse> getUserResources() {
		return userResources;
	}

	public void setUserResources(
			List<ResourceRequestAllocationResponse> userResources) {
		this.userResources = userResources;
	}

	public Float getRamHours() {
		return ramHours;
	}

	public void setRamHours(Float ramHours) {
		this.ramHours = ramHours;
	}

	public Float getDiskHours() {
		return diskHours;
	}

	public void setDiskHours(Float diskHours) {
		this.diskHours = diskHours;
	}

	public Float getCpuHours() {
		return cpuHours;
	}

	public void setCpuHours(Float cpuHours) {
		this.cpuHours = cpuHours;
	}

	public Float getRamCost() {
		return ramCost;
	}

	public void setRamCost(Float ramCost) {
		this.ramCost = ramCost;
	}

	public Float getDiskCost() {
		return diskCost;
	}

	public void setDiskCost(Float diskCost) {
		this.diskCost = diskCost;
	}

	public Float getCpuCost() {
		return cpuCost;
	}

	public void setCpuCost(Float cpuCost) {
		this.cpuCost = cpuCost;
	}

	public Float getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(Float totalCost) {
		this.totalCost = totalCost;
	}

}
