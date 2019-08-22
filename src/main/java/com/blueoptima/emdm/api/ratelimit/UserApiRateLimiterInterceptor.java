package com.blueoptima.emdm.api.ratelimit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.blueoptima.emdm.exception.BadRequestException;
import com.blueoptima.emdm.exception.TooManyRequestsException;
import com.blueoptima.emdm.service.UserApiRateLimitService;

/**
 * @author prafullkulshrestha
 *
 *
 */
public class UserApiRateLimiterInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(UserApiRateLimiterInterceptor.class);
	@Value("${emdm.application.rate.limit.enabled}")
	private boolean enabled;
	@Value("${emdm.application.rate.limit.default}")
	private int defaultLimit;
	@Value("${emdm.application.rate.limit.minutely.limit}")
	private int minutelyTimeLimit;
	@Value("${emdm.application.rate.limit.sheduler.numberOfThreads}")
	private int noOfThreads;

	private static final String TOO_MANY_REQUESTS_MESSAGE = "Too many requests, please try after sometime";
	private static final String USER_ID_HEADER = "User-Id";
	private static final String X_RATE_LIMIT_HEADER_KEY = "X-RateLimit-Limit";
	private static final String BAD_REQUESTS_MESSAGE = "Bad request";

	@Autowired
	private UserApiRateLimitService userApiRateLimitService;
	private Map<String, UserApiRateLimiter> limiters = new ConcurrentHashMap<>();
	private ScheduledExecutorService scheduler;

	@PostConstruct
	public void init() {
		logger.debug("Initializing the limiters scheduler thread pool");
		this.scheduler = Executors.newScheduledThreadPool(noOfThreads);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!enabled) {
			return true;
		}
		String userId = request.getHeader(USER_ID_HEADER);
		String api = request.getRequestURI();
		if (userId == null && !api.contains("swagger")) {
			throw new BadRequestException(BAD_REQUESTS_MESSAGE);
		}
		UserApiRateLimiter rateLimiter = getRateLimiter(userId, api);
		boolean allowRequest = rateLimiter.tryAcquire();
		response.addHeader(X_RATE_LIMIT_HEADER_KEY, String.valueOf(minutelyTimeLimit));
		if (!allowRequest) {
			throw new TooManyRequestsException(TOO_MANY_REQUESTS_MESSAGE);
		}
		return allowRequest;
	}

	private UserApiRateLimiter getRateLimiter(String userId, String api) {
		final String userIdApiKey = (userId + api).intern();
		if (limiters.containsKey(userIdApiKey)) {
			return limiters.get(userIdApiKey);
		} else {
			return createRateLimiter(userId, api);
		}
	}

	private UserApiRateLimiter createRateLimiter(String userId, String api) {
		final String userIdApiKey = (userId + api).intern();
		int rateLimitFromApi = userApiRateLimitService.getUserApiRateLimit(userId, api);
		int rateLimit = (rateLimitFromApi != 0) ? rateLimitFromApi : (this.defaultLimit);
		UserApiRateLimiter apiRateLimiter = new UserApiRateLimiter(rateLimit);
		logger.debug("Rate limit started for the userIdApiKey: {}", userIdApiKey );
		limiters.put(userIdApiKey, apiRateLimiter);
		replenishPermitsForUserAfterGivenTime(userIdApiKey, apiRateLimiter, rateLimit);
		return apiRateLimiter;
	}

	private void replenishPermitsForUserAfterGivenTime(String userIdApiKey, UserApiRateLimiter apiRateLimiter,
			int maxPermits) {
		scheduler.schedule(() -> {
			apiRateLimiter.releasePermits(maxPermits - apiRateLimiter.getAvailablePermits());
			limiters.remove(userIdApiKey);
			logger.debug("Replenishment completed for the user: {}", userIdApiKey);
		}, minutelyTimeLimit, TimeUnit.MINUTES);

	}

	@PreDestroy
	public void destroy() {
		scheduler.shutdown();
	}
}
