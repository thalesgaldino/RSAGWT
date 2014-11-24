package org.jboss.tools.gwt.server.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.jboss.tools.gwt.client.dto.BranchDTO;


@Entity
public class Branch {
	
	@Id
	@GeneratedValue
	private Integer branchId;
	private String name;
	private String location;
	
	public Branch(){}
	
	public Branch(BranchDTO branchDTO){
		setBranchId(branchDTO.getBranchId());
		setName(branchDTO.getName());
		setLocation(branchDTO.getLocation());
	}
	
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
