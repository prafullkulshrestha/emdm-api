package com.blueoptima.emdm.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.blueoptima.emdm.dao.DeveloperDao;
import com.blueoptima.emdm.dto.DeveloperDto;
import com.blueoptima.emdm.entity.Developer;
import com.blueoptima.emdm.exception.NoDataFoundException;
import com.blueoptima.emdm.service.DeveloperService;

/**
 * The implementation service class for defining developer specific business
 * logic
 * 
 * @author prafullkulshrestha
 *
 */
@Service("developerService")
public class DeveloperServiceImpl implements DeveloperService {

	private Logger log = LoggerFactory.getLogger(getClass());
	private DeveloperDao developerDao;

	@Autowired
	public DeveloperServiceImpl(DeveloperDao developerDao) {
		this.developerDao = developerDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.blueoptima.emdm.service.DeveloperService#getAllDevelopers()
	 */
	@Override
	public List<DeveloperDto> getAllDevelopers() {
		List<Developer> developers = this.developerDao.findAll();
		List<DeveloperDto> developersDtos = new ArrayList<>();
		if (CollectionUtils.isEmpty(developers)) {
			log.debug("No developers found");
			throw new NoDataFoundException("No developers found");
		}
		developers.stream().forEach(dev -> developersDtos.add(new DeveloperDto(dev)));
		return developersDtos;
	}
}
