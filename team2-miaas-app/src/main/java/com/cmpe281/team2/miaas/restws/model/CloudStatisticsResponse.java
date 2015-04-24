package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class CloudStatisticsResponse {
	
	private Integer cloudsCount;
	private Integer hostsCount;
	private Integer usersCount;
	private Integer requestsCount;
	
	private List<CloudRequestsResponse> cloudRequestStats;
	
	private List<CloudUtilizationResponse> cloudUsageStats;
	
	public Integer getCloudsCount() {
		return cloudsCount;
	}
	public void setCloudsCount(Integer cloudsCount) {
		this.cloudsCount = cloudsCount;
	}
	public Integer getHostsCount() {
		return hostsCount;
	}
	public void setHostsCount(Integer hostsCount) {
		this.hostsCount = hostsCount;
	}
	public Integer getUsersCount() {
		return usersCount;
	}
	public void setUsersCount(Integer usersCount) {
		this.usersCount = usersCount;
	}
	public Integer getRequestsCount() {
		return requestsCount;
	}
	public void setRequestsCount(Integer requestsCount) {
		this.requestsCount = requestsCount;
	}
	public List<CloudRequestsResponse> getCloudRequestStats() {
		return cloudRequestStats;
	}
	public void setCloudRequestStats(List<CloudRequestsResponse> cloudRequestStats) {
		this.cloudRequestStats = cloudRequestStats;
	}
	public List<CloudUtilizationResponse> getCloudUsageStats() {
		return cloudUsageStats;
	}
	public void setCloudUsageStats(List<CloudUtilizationResponse> cloudUsageStats) {
		this.cloudUsageStats = cloudUsageStats;
	}
	
}
