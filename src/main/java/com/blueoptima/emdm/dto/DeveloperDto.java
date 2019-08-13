package com.blueoptima.emdm.dto;

import com.blueoptima.emdm.entity.Developer;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prafullkulshrestha
 *
 */
public class DeveloperDto {
	
	private Developer developer;

	public DeveloperDto(Developer developer) {
		this.developer = developer;
	}
	
	public long getDeveloperId() {
		return this.developer.getDeveloperId();
	}

	public void setDeveloperId(long developerId) {
		this.developer.setDeveloperId(developerId);
	}

	public OrganizationDto getOrganization() {
		return (new  OrganizationDto(this.developer.getOrganization()));
	}

	public void setOrganization(OrganizationDto organization) {
		this.developer.setOrganization(organization.getOrganization());
	}

	public String getName() {
		return this.developer.getName();
	}

	public void setName(String name) {
		this.developer.setName(name);
	}

	public Integer getAge() {
		return this.developer.getAge();
	}

	public void setAge(Integer age) {
		this.developer.setAge(age);
	}
	
	@JsonIgnore
	public Developer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Developer developer) {
		this.developer = developer;
	}

}
