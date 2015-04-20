package com.cmpe281.team2.miaas.restws.model;

import java.util.List;

public class GetResourceRequestsResponse {
	
	private List<ResourceRequestAllocationResponse> resourceRequests;

	public List<ResourceRequestAllocationResponse> getResourceRequests() {
		return resourceRequests;
	}

	public void setResourceRequests(List<ResourceRequestAllocationResponse> resourceRequests) {
		this.resourceRequests = resourceRequests;
	}

}
