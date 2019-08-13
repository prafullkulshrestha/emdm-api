package com.blueoptima.emdm.service;

import java.util.List;
import com.blueoptima.emdm.dto.DeveloperDto;

/**
 * The service class has developers' related business logic.
 * 
 * @author prafullkulshrestha
 */
public interface DeveloperService {

	/**
	 * The method returns a list of all the available developers in the database
	 * 
	 * @return
	 */
	List<DeveloperDto> getAllDevelopers();
}
