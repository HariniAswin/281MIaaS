package com.cmpe281.team2.miaas.restws.model;

public class CloudStatisticsResponse {
	
	private Integer cloudsCount;
	private Integer hostsCount;
	private Integer usersCount;
	private Integer requestsCount;
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
	
}
