package com.cmpe281.team2.miaas.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Cloud")
public class Cloud implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4571322015506121106L;
	
	@Id
	@Column(name = "name", length = 100, unique = true, nullable = false)
	private String name;
	
	@Column(name = "location", length = 100)
	private String location;
	
	@Column(name = "usageIndex")
	private Float usageIndex;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "cloud") 
    private Set<Host> hosts;
	
	public Cloud() { }
	
	
	public Cloud(String name, String location) {
		this.name = name;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Float getUsageIndex() {
		return usageIndex;
	}

	public void setUsageIndex(Float usageIndex) {
		this.usageIndex = usageIndex;
	}

	public Set<Host> getHosts() {
		return hosts;
	}

	public void setHosts(Set<Host> hosts) {
		this.hosts = hosts;
	}
	
	

}
