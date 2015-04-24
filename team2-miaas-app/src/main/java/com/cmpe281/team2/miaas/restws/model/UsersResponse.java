package com.cmpe281.team2.miaas.restws.model;

import java.math.BigInteger;

public class UsersResponse {
	
	private String userName;
	private BigInteger numberOfRequests;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public BigInteger getNumberOfRequests() {
		return numberOfRequests;
	}
	public void setNumberOfRequests(BigInteger numberOfRequests) {
		this.numberOfRequests = numberOfRequests;
	}

}
