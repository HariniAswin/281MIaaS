package com.cmpe281.team2.miaas.openstack.client.restws.model;

import java.util.List;

public class Image {
	
	private String status;
	private String updated;
	private List<Link> links;
	private String id;
	private String name;
	private String created;
	private Integer minDisk;
	private Integer progress;
	private Integer minRam;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdated() {
		return updated;
	}
	public void setUpdated(String updated) {
		this.updated = updated;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public Integer getMinDisk() {
		return minDisk;
	}
	public void setMinDisk(Integer minDisk) {
		this.minDisk = minDisk;
	}
	public Integer getProgress() {
		return progress;
	}
	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	public Integer getMinRam() {
		return minRam;
	}
	public void setMinRam(Integer minRam) {
		this.minRam = minRam;
	}
	
	
}
