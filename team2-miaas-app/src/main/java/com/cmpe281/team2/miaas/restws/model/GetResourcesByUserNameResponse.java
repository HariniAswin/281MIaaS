package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class GetResourcesByUserNameResponse {
	
	private List<ResourceRequestAllocationResponse> userResources;
	
	private Float totalRamHours;
	
	private Float totalDiskHours;
	
	private Float totalCPUHours;
	
	private String totalRamCost;
	
	private String totalDiskCost;
	
	private String totalCPUCost;
	
	private String totalCost;

	public List<ResourceRequestAllocationResponse> getUserResources() {
		return userResources;
	}

	public void setUserResources(
			List<ResourceRequestAllocationResponse> userResources) {
		this.userResources = userResources;
	}

	public Float getTotalRamHours() {
		return totalRamHours;
	}

	public void setTotalRamHours(Float totalRamHours) {
		this.totalRamHours = totalRamHours;
	}

	public Float getTotalDiskHours() {
		return totalDiskHours;
	}

	public void setTotalDiskHours(Float totalDiskHours) {
		this.totalDiskHours = totalDiskHours;
	}

	public Float getTotalCPUHours() {
		return totalCPUHours;
	}

	public void setTotalCPUHours(Float totalCPUHours) {
		this.totalCPUHours = totalCPUHours;
	}

	public String getTotalRamCost() {
		return totalRamCost;
	}

	public void setTotalRamCost(String totalRamCost) {
		this.totalRamCost = totalRamCost;
	}

	public String getTotalDiskCost() {
		return totalDiskCost;
	}

	public void setTotalDiskCost(String totalDiskCost) {
		this.totalDiskCost = totalDiskCost;
	}

	public String getTotalCPUCost() {
		return totalCPUCost;
	}

	public void setTotalCPUCost(String totalCPUCost) {
		this.totalCPUCost = totalCPUCost;
	}

	public String getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}

}
