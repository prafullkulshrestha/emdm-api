package com.blueoptima.emdm;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.blueoptima.emdm.dao.UserApiRateLimitDao;
import com.blueoptima.emdm.entity.UserApiRateLimit;
import com.blueoptima.emdm.service.UserApiRateLimitService;
import com.blueoptima.emdm.service.impl.UserApiRateLimitServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class UserApiRateLimitServiceImplTests {

	private UserApiRateLimitService userApiRateLimitService;
	@Mock
	private UserApiRateLimitDao userApiRateLimitDao;
	private UserApiRateLimit userApiRateLimit;

	@Before
	public void setup() {
		this.userApiRateLimit = new UserApiRateLimit();
		this.userApiRateLimit.setUserApiRateLimitId(1l);
		this.userApiRateLimit.setUsername("testUser");
		this.userApiRateLimit.setApi("testApi");
		this.userApiRateLimit.setRateLimit(10);
		this.userApiRateLimit.setCreatedBy("testUser");
		this.userApiRateLimit.setCreatedOn(new Date());
		this.userApiRateLimit.setUpdatedBy("testUser");
		this.userApiRateLimit.setUpdatedOn(new Date());
		this.userApiRateLimitService = new UserApiRateLimitServiceImpl(userApiRateLimitDao);
	}

	@Test
	public void getAllUserApiRateLimitsForExistingRecord() throws Exception {
		when(userApiRateLimitDao.findByUsernameAndApi(anyString(), anyString())).thenReturn(this.userApiRateLimit);
		int userApiRateLimit = this.userApiRateLimitService.getUserApiRateLimit("testUser", "testApi");
		verify(userApiRateLimitDao, times(1)).findByUsernameAndApi(anyString(), anyString());
		assertThat("The returned limit value is not as expected", userApiRateLimit, equalTo(10));
	}

	@Test
	public void getAllUserApiRateLimitsForNonExistingRecord() throws Exception {
		int userApiRateLimit = this.userApiRateLimitService.getUserApiRateLimit("testUser", "testApi");
		verify(userApiRateLimitDao, times(1)).findByUsernameAndApi(anyString(), anyString());
		assertThat("The returned limit value is not as expected", userApiRateLimit, equalTo(0));

	}
}