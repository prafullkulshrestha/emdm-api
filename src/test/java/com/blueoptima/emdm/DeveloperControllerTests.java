package com.blueoptima.emdm;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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
import org.springframework.test.web.servlet.ResultActions;
import com.blueoptima.emdm.api.controller.DeveloperController;
import com.blueoptima.emdm.dto.DeveloperDto;
import com.blueoptima.emdm.entity.Developer;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.service.DeveloperService;
import com.blueoptima.emdm.service.UserApiRateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(DeveloperController.class)
public class DeveloperControllerTests {

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
	public void getAllDevelopersForSize() throws Exception {

		when(developerService.getAllDevelopers()).thenReturn(developerDtos);

		ResultActions resultActions = this.mockMvc
				.perform(get(DEVELOPERS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
		ObjectMapper om = new ObjectMapper();
		assertThat("Response data is null", resultActions.andReturn().getResponse().getContentAsString(),
				not(nullValue()));
		@SuppressWarnings("unchecked")
		List<DeveloperDto> responseDeveloperDtos = om
				.readValue(resultActions.andReturn().getResponse().getContentAsString(), List.class);
		assertThat("Response list size is not equal to 2", responseDeveloperDtos.size(), equalTo(2));
	}

	@Test
	public void getAllDevelopersForContent() throws Exception {

		when(developerService.getAllDevelopers()).thenReturn(developerDtos);
		this.mockMvc.perform(get(DEVELOPERS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("\"developerId\":1")));
	}

	@Test
	public void getAllDevelopersForHeader() throws Exception {

		when(developerService.getAllDevelopers()).thenReturn(developerDtos);
		ResultActions resultActions = this.mockMvc
				.perform(get(DEVELOPERS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
		assertThat("Response header value is null",
				resultActions.andReturn().getResponse().getHeader("X-RateLimit-Limit"), not(nullValue()));
	}
}
