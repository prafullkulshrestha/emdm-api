package com.blueoptima.emdm.api.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blueoptima.emdm.dto.OrganizationDto;
import com.blueoptima.emdm.service.OrganizationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * The controller exposes APIs for the organizations' related operations
 * @author prafullkulshrestha
 *
 */
@RestController
@Api(value = "/", tags = "Organizations API")

@RequestMapping("/")
public class OrganizationController {
	private Logger log = LoggerFactory.getLogger(getClass());
	@Autowired
	private OrganizationService organizationService;

	@ApiOperation(httpMethod = "GET", value = "The API for getting the details of all the organizations")
	@ApiImplicitParam(name = "User-Id", value = "User-Id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "User 1")
	@GetMapping(value = "/v1/organizations", produces = "application/json")
	public ResponseEntity<List<OrganizationDto>> getOrganizations() {
		log.debug("Request recieved for getting all the organizations details ");
		List<OrganizationDto> organizations = organizationService.getAllOrganizations();
		log.debug("Request completed for getting all the organizations details");
		return new ResponseEntity<>(organizations, HttpStatus.OK);
	}

}
