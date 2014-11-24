package org.jboss.tools.gwt.client.dto;

import java.io.Serializable;

public class BranchDTO implements Serializable{

	private Integer branchId;
	private String name;
	private String location;
	
	public BranchDTO(){}
	
	public Integer getBranchId() {
		return branchId;
	}

	public void setBranchId(Integer branchId) {
		this.branchId = branchId;
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
}
