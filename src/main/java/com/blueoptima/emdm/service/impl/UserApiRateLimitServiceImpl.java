package com.blueoptima.emdm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blueoptima.emdm.dao.UserApiRateLimitDao;
import com.blueoptima.emdm.entity.UserApiRateLimit;
import com.blueoptima.emdm.service.UserApiRateLimitService;

/**
 * The implementation service class for defining user, api and it's rate limit
 * specific business logic
 * 
 * @author prafullkulshrestha
 *
 */
@Service("userApiRateLimitService")
public class UserApiRateLimitServiceImpl implements UserApiRateLimitService {

	private UserApiRateLimitDao userApiRateLimitDao;

	@Autowired
	public UserApiRateLimitServiceImpl(UserApiRateLimitDao userApiRateLimitDao) {
		this.userApiRateLimitDao = userApiRateLimitDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.blueoptima.emdm.service.UserApiRateLimitService#getUserApiRateLimit(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public int getUserApiRateLimit(String username, String api) {
		UserApiRateLimit apiRateLimit = userApiRateLimitDao.findByUsernameAndApi(username, api);
		return (apiRateLimit != null) ? apiRateLimit.getRateLimit() : 0;
	}
}
