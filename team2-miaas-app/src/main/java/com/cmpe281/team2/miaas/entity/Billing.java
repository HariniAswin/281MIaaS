package com.cmpe281.team2.miaas.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Billing")

public class Billing implements serializable {
 

 @Id 
 @GeneratedValue
 @Column(name = "id", unique=true, nullable=false)
 private Integer id;

 @Column(name = "cpu cost")
 private Float cpu_cost;

 @Column(name = "ram cost")
 private Float ram_cost;

 @Column(name = "Storage cost")
 private Float storage_cost;

 @Column(name="request_allocation", nullable=false)
 private Integer request_allocation;

 public Billing() {}

 public Billing(Float cpu_cost, Float ram_cost, Float storage_cost,
		Integer request_allocation) {
    
	this.cpu_cost = cpu_cost;
 	this.ram_cost = ram_cost;
	this.storage_cost = storage_cost;
	this.request_allocation = request_allocation;
	}

	public void setCPUCost(Float cpu_cost) {
	
        this.cpu_cost = cpu_cost;
	}

	public void setRamCost(Float ram_cost) {

	this.ram_cost = ram_cost;
	}

	public void setStorageCost(Float storage_cost) {
	
	this.storage_cost = storage_cost;
	}
        public void setRequestAllocation(Integer request_allocation) 
	{
		this.request_allocation = request_allocation;
	}

	public getCpuCost() {
		return this.cpu_cost;
	}
	public getStorageCost() {
		return this.storage_cost;
	}
	public getRamCost() {
		return this.ram_cost;
	}
	public getResourceAllocation()
	{
		return this.request_allocation;
	}
}
