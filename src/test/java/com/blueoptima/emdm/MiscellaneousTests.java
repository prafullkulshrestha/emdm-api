package com.blueoptima.emdm;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.blueoptima.emdm.api.controller.DeveloperController;
import com.blueoptima.emdm.api.ratelimit.UserApiRateLimiter;
import com.blueoptima.emdm.dto.DeveloperDto;
import com.blueoptima.emdm.entity.Developer;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.entity.UserApiRateLimit;
import com.blueoptima.emdm.service.DeveloperService;
import com.blueoptima.emdm.service.UserApiRateLimitService;

@RunWith(SpringRunner.class)
@WebMvcTest(DeveloperController.class)
public class MiscellaneousTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DeveloperService developerService;
	@MockBean
	private UserApiRateLimitService userApiRateLimitService;
	private List<DeveloperDto> developerDtos;
	private String HEADER_KEY;
	private String HEADER_VALUE;
	private String DEVELOPERS_URI;

	@Before
	public void setup() {
		this.HEADER_KEY = "User-Id";
		this.HEADER_VALUE = "User 2";
		this.DEVELOPERS_URI = "/v1/developers";
		this.developerDtos = new ArrayList<DeveloperDto>() {
			private static final long serialVersionUID = -1748706078381702183L;
			{
				add(new DeveloperDto(new Developer(1l, new Organization(1l))));
				add(new DeveloperDto(new Developer(2l, new Organization(1l))));
			}
		};
	}

	@Test
	public void tooManyRequests() throws Exception {

		when(developerService.getAllDevelopers()).thenReturn(developerDtos);
		when(userApiRateLimitService.getUserApiRateLimit(anyString(), anyString())).thenReturn(1);

		StringBuilder statusCode = new StringBuilder(this.mockMvc
				.perform(get(DEVELOPERS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andReturn().getResponse().getStatus() + "");
		for (int i = 0; i < 40; i++) {
			statusCode.append(this.mockMvc
					.perform(get(DEVELOPERS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
					.andReturn().getResponse().getStatus());
		}
		assertThat("The status code is not as expected", statusCode.toString(), containsString("429"));
	}

	@Test
	public void checkUserApiRateLimit() throws Exception {
		UserApiRateLimiter apiRateLimiter = new UserApiRateLimiter(2);
		assertThat("The permits not as expected", apiRateLimiter.getAvailablePermits(), equalTo(2));
		apiRateLimiter.tryAcquire();
		apiRateLimiter.tryAcquire();
		assertThat("The permits not as expected", apiRateLimiter.getAvailablePermits(), equalTo(0));
		apiRateLimiter.releasePermits(2);
		assertThat("The permits not as expected", apiRateLimiter.getAvailablePermits(), equalTo(2));
	}

	@Test
	public void organization() throws Exception {
		Organization organization = new Organization(1l);
		assertThat("The organization toString return not as expected", organization.toString(), not(nullValue()));
	}

	@Test
	public void developer() throws Exception {
		Developer developer = new Developer();
		developer.setDeveloperId(1l);
		assertThat("The developer toString return not as expected", developer.toString(), not(nullValue()));
	}

	@Test
	public void userApiLimit() throws Exception {
		UserApiRateLimit userApiRateLimit = new UserApiRateLimit();
		userApiRateLimit.setUserApiRateLimitId(1l);
		assertThat("The userApiRateLimit toString return not as expected", userApiRateLimit.toString(),
				not(nullValue()));
		assertThat("The userApiRateLimit record is null", userApiRateLimit, not(nullValue()));
		assertThat("The userApiRateLimit record is not equal to itself", userApiRateLimit, equalTo(userApiRateLimit));
		assertThat("The userApiRateLimit record is developer type", userApiRateLimit, not(new Developer()));
	}

	@Test
	public void badRequest() throws Exception {
		when(developerService.getAllDevelopers()).thenReturn(developerDtos);
		when(userApiRateLimitService.getUserApiRateLimit(anyString(), anyString())).thenReturn(1);

		this.mockMvc.perform(get(DEVELOPERS_URI).accept(MediaType.APPLICATION_JSON)).andDo(print())
				.andExpect(status().isBadRequest());
	}
}
