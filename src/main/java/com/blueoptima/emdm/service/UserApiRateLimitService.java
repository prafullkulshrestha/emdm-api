package com.blueoptima.emdm.service;

/**
 * The service class has the business logic to return user, api and its rate
 * limited operations
 * 
 * @author prafullkulshrestha
 */
public interface UserApiRateLimitService {

	/**
	 * The method returns the rate limit for a user on a particular api
	 * 
	 * @param username
	 *            username of the user
	 * @param api
	 *            api the user is accessing to
	 * @return
	 */
	int getUserApiRateLimit(String username, String api);
}
