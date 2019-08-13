package com.blueoptima.emdm.dto;

import com.blueoptima.emdm.entity.Organization;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author prafullkulshrestha
 *
 */
public class OrganizationDto {
	
	private Organization organization;

	public OrganizationDto(Organization org) {
		this.organization = org;
	}

	public long getOrganizationId() {
		return this.organization.getOrganizationId();
	}

	public void setOrganizationId(long organizationId) {
		this.organization.setOrganizationId(organizationId);
	}
	
	public String getName() {
		return this.organization.getName();
	}

	public void setName(String name) {
		this.organization.setName(name);
	}

	public String getAbout() {
		return this.organization.getAbout();
	}

	public void setAbout(String about) {
		this.organization.setAbout(about);
	}
	
	@JsonIgnore()
	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	
}
