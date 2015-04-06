package com.cmpe281.team2.miaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ResourceRequestAllocation")
public class ResourceRequestAllocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9172583080035826013L;
	
	@Id
	@GeneratedValue
	@Column(name = "id", length = 11, unique = true, nullable = false)
	private Integer id;
	
	@Column(name = "resourceType", length = 100, nullable = false)
	private String resourceType;
	
	@Column(name = "os", length = 100)
	private String os;
	
	@Column(name = "cpu")
	private Float cpu;
	
	@Column(name = "ram")
	private Float ram;
	
	@Column(name = "storage")
	private Float storage;
	
	@Column(name = "userName", length = 100)
	private String userName;
	
	@Column(name = "assignedCloud", length = 100)
	private String cloud;
	
	@Column(name = "assignedHost", length = 100)
	private String assignedHost;
	
	@Column(name = "status", length = 100)
	private String status;
	
	public ResourceRequestAllocation() { }
	
	public ResourceRequestAllocation(String resourceType, String os,
			Float cpu, Float ram, Float storage, String userName) {
		this.resourceType = resourceType;
		this.os = os;
		this.cpu = cpu;
		this.ram = ram;
		this.storage = storage;
		this.userName = userName;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getCloud() {
		return cloud;
	}

	public void setCloud(String cloud) {
		this.cloud = cloud;
	}

	public String getAssignedHost() {
		return assignedHost;
	}

	public void setAssignedHost(String assignedHost) {
		this.assignedHost = assignedHost;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


}