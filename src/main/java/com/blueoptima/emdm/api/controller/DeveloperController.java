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

import com.blueoptima.emdm.dto.DeveloperDto;
import com.blueoptima.emdm.service.DeveloperService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * The Controller for exposing all the developers' related endpoints.
 * @author prafullkulshrestha
 *
 */
@RestController
@Api(value = "/", tags = "Devlopers API")
@RequestMapping("/")
public class DeveloperController {
	private Logger log = LoggerFactory.getLogger(DeveloperController.class);
	@Autowired
	private DeveloperService developerService;
	
	@ApiOperation(httpMethod = "GET", value = "The API for getting the details of all the developers")
	@ApiImplicitParam(name = "User-Id", value = "User-Id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "User 1")
	@GetMapping(value = "/v2/developers", produces = "application/json")
	public ResponseEntity<List<DeveloperDto>> getAllDevelopers() {
		log.debug("Request recieved for getting all the developers");
		List<DeveloperDto> dvelopers = developerService.getAllDevelopers();
		log.debug("Request completed for getting all the developers");
		return new ResponseEntity<>(dvelopers, HttpStatus.OK);
	}

}
