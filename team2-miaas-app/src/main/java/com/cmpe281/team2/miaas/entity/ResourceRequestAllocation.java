package com.cmpe281.team2.miaas.entity;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.Period;

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
	
	@Column(name = "name", length = 100, nullable = false)
	private String name;
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt")
	private Date createdAt; 
	
	@Column(name = "externalResource")
	private Boolean externalResource;
	
	@Column(name = "externalResourceId", length = 100)
	private String externalResourceId;
	
	public ResourceRequestAllocation() { }
	
	public ResourceRequestAllocation(String name, String resourceType, String os,
			Float cpu, Float ram, Float storage, String userName) {
		this.name = name;
		this.resourceType = resourceType;
		this.os = os;
		this.cpu = cpu;
		this.ram = ram;
		this.storage = storage;
		this.userName = userName;
		
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, -(int)(Math.random() * 100));
		
		this.createdAt = calendar.getTime();
	}
	
	
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

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Boolean getExternalResource() {
		return externalResource;
	}

	public void setExternalResource(Boolean externalResource) {
		this.externalResource = externalResource;
	}

	public String getExternalResourceId() {
		return externalResourceId;
	}

	public void setExternalResourceId(String externalResourceId) {
		this.externalResourceId = externalResourceId;
	}
	
	public Float getRamHours() {
		return (ram * getResourceHours())/1024;
	}
	
	public Double getRamCost() {
		return getRamHours() * 0.05;
	}
	
	public Float getStorageHours() {
		return (storage * getResourceHours());
	}
	
	public Double getStorageCost() {
		return getRamHours() * 0.05;
	}
	
	public Float getCPUHours() {
		return (cpu * getResourceHours());
	}
	
	public Double getCPUCost() {
		return getCPUHours() * 0.1;
	}
	
	private Float getResourceHours() {
		if(createdAt != null) {
			return new Float(new Period(createdAt.getTime(), new Date().getTime()).getHours());
		}
		return 0f;
		
	}


}
