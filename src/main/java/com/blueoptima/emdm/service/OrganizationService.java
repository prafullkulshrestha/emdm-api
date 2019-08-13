package com.blueoptima.emdm.service;

import java.util.List;

import com.blueoptima.emdm.dto.OrganizationDto;

/**
 * The service class has organizations' related business logic
 * 
 * @author prafullkulshrestha
 */
public interface OrganizationService {

	/**
	 * The method returns a list of all the available organization records in the
	 * database
	 * 
	 * @return
	 */
	List<OrganizationDto> getAllOrganizations();
}
