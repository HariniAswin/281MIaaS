package com.cmpe281.team2.miaas.restws.model;

public class CloudRequestsResponse {
	
	private String cloudName;
	private Integer numberOfRequests;
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

}
