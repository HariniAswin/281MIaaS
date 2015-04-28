package com.cmpe281.team2.miaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Host")
public class Host implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8609451558192283673L;

	@Id
	@Column(name = "name", length = 100, unique = true, nullable = false)
	private String name;
	
	@Column(name = "os", length = 100)
	private String os;
	
	@Column(name = "totalCPUUnits", length = 11)
	private Float totalCPUUnits;	
	
	@Column(name = "totalRam", length = 11)
	private Float totalRam;
	
	@Column(name = "totalStorage", length = 11)
	private Float totalStorage;
	
	@Column(name = "cpuAllocated")
	private Float cpuAllocated;
	
	@Column(name = "ramAllocated")
	private Float ramAllocated;
	
	@Column(name = "storageAllocated")
	private Float storageAllocated;
	
	@Column(name = "cloudName", length = 100, nullable = false)
	private String cloudName;
	
	@Column(name = "requests", length = 11)
	private Integer requests;
	
	@Column(name = "resourceType", length = 100, nullable = false)
	private String resourceType;
	
	@Column(name = "externalResource")
	private Boolean externalResource;
	
	@Column(name = "tenantId", length = 100)
	private String tenantId;
	
	public Float getAvailableCPU() {
		return (cpuAllocated!=null?(totalCPUUnits - cpuAllocated) : totalCPUUnits);
	}

	public Float getAvailableRAM() {
		return (ramAllocated!=null?(totalRam - ramAllocated) : totalRam);
	}

	public Float getAvailableStorage() {
		return (storageAllocated!=null?(totalStorage - storageAllocated) : totalStorage);
	}

	public Float getCpuUtilization() {
		return (cpuAllocated!=null?(cpuAllocated/totalCPUUnits) : 0.0f);
	}

	public Float getRamUtilization() {
		return (ramAllocated!=null?(ramAllocated/totalRam) : 0.0f);
	}

	public Float getStorageUtilization() {
		return (storageAllocated!=null?(storageAllocated/totalStorage) : 0.0f);
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "cloudName", insertable = false, updatable = false)
	private Cloud cloud;
	
	public Host() { }
	
	public Host(String name, String os, Float totalCPUUnits,
			Float totalRam, Float totalStorage, String cloudName,
			String resourceType, Boolean externalResource, String tenantId) {
		this.name = name;
		this.os = os;
		this.totalCPUUnits = totalCPUUnits;
		this.totalRam = totalRam;
		this.totalStorage = totalStorage;
		this.cloudName = cloudName;
		this.resourceType = resourceType;
		this.externalResource = externalResource;
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public Float getTotalCPUUnits() {
		return totalCPUUnits;
	}

	public void setTotalCPUUnits(Float totalCPUUnits) {
		this.totalCPUUnits = totalCPUUnits;
	}

	public Float getTotalRam() {
		return totalRam;
	}

	public void setTotalRam(Float totalRam) {
		this.totalRam = totalRam;
	}

	public Float getTotalStorage() {
		return totalStorage;
	}

	public void setTotalStorage(Float totalStorage) {
		this.totalStorage = totalStorage;
	}

	public Float getCpuAllocated() {
		return cpuAllocated;
	}

	public void setCpuAllocated(Float cpuAllocated) {
		this.cpuAllocated = cpuAllocated;
	}

	public Float getRamAllocated() {
		return ramAllocated;
	}

	public void setRamAllocated(Float ramAllocated) {
		this.ramAllocated = ramAllocated;
	}

	public Float getStorageAllocated() {
		return storageAllocated;
	}

	public void setStorageAllocated(Float storageAllocated) {
		this.storageAllocated = storageAllocated;
	}

	public String getCloudName() {
		return cloudName;
	}

	public void setCloudName(String cloudName) {
		this.cloudName = cloudName;
	}

	public Integer getRequests() {
		return requests;
	}

	public void setRequests(Integer requests) {
		this.requests = requests;
	}

	public String getResourceType() {
		return resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public Boolean getExternalResource() {
		return externalResource;
	}

	public void setExternalResource(Boolean externalResource) {
		this.externalResource = externalResource;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public Cloud getCloud() {
		return cloud;
	}

	public void setCloud(Cloud cloud) {
		this.cloud = cloud;
	}
	
	@Override
	public boolean equals(Object object) {
		boolean result = false;
		if (object == null || object.getClass() != getClass()) {
			result = false;
		} else {
			Host host = (Host) object;
			if (this.name.equals(host.getName())) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 7 * hash + this.name.hashCode();
		return hash;
	}

}	
