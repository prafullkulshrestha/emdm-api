package com.blueoptima.emdm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.blueoptima.emdm.dao.OrganizationDao;
import com.blueoptima.emdm.dto.OrganizationDto;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.exception.NoDataFoundException;
import com.blueoptima.emdm.service.OrganizationService;

/**
 * The implementation service class for defining organization specific business
 * logic
 * 
 * @author prafullkulshrestha
 *
 */
@Service("organizationService")
public class OrganizationServiceImpl implements OrganizationService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private OrganizationDao organizationDao;

	@Autowired
	public OrganizationServiceImpl(OrganizationDao organizationDao) {
		this.organizationDao = organizationDao;
	}

	/*
	 * (non-Javadoc) 
	 * 
	 * @see com.blueoptima.emdm.service.OrganizationService#getAllOrganizations()
	 */
	@Override
	public List<OrganizationDto> getAllOrganizations() {
		List<Organization> organizations = this.organizationDao.findAll();
		List<OrganizationDto> organizationDtos = new ArrayList<>();
		if (CollectionUtils.isEmpty(organizations)) {
			log.debug("No organizatios found");
			throw new NoDataFoundException("No orgaizations found");
		}
		organizations.stream().forEach(org -> organizationDtos.add(new OrganizationDto(org)));
		return organizationDtos;
	}
}
