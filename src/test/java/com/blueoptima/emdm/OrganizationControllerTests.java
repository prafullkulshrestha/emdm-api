package com.blueoptima.emdm;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import com.blueoptima.emdm.api.controller.OrganizationController;
import com.blueoptima.emdm.dto.OrganizationDto;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.service.OrganizationService;
import com.blueoptima.emdm.service.UserApiRateLimitService;
import com.fasterxml.jackson.databind.ObjectMapper;
@Ignore
@RunWith(SpringRunner.class)
@WebMvcTest(OrganizationController.class)
public class OrganizationControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private OrganizationService organizationService;
	@MockBean
	private UserApiRateLimitService userApiRateLimitService;
	private List<OrganizationDto> organizationDtos;
	private String HEADER_KEY;
	private String HEADER_VALUE;
	private String ORGANIZATIONS_URI;
	private Organization org1;
	private Organization org2;

	@Before
	public void setup() {
		this.HEADER_KEY = "User-Id";
		this.HEADER_VALUE = "User 2";
		this.ORGANIZATIONS_URI = "/v1/organizations";
		org1 = new Organization(1l);
		org2 = new Organization(2l);
		this.organizationDtos = new ArrayList<OrganizationDto>() {
			private static final long serialVersionUID = -1748706078381702183L;
			{
				add(new OrganizationDto(org1));
				add(new OrganizationDto(org2));
			}
		};
	}

	@Test
	public void getAllOrganizationsForSize() throws Exception {

		when(organizationService.getAllOrganizations()).thenReturn(organizationDtos);
		ResultActions resultActions = this.mockMvc
				.perform(get(this.ORGANIZATIONS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
		ObjectMapper om = new ObjectMapper();
		assertThat("Response data is null", resultActions.andReturn().getResponse().getContentAsString(),
				not(nullValue()));
		@SuppressWarnings("unchecked")
		List<OrganizationDto> responseOrganizationDtos = om
				.readValue(resultActions.andReturn().getResponse().getContentAsString(), List.class);
		assertThat("Response list size is not equal to 2", responseOrganizationDtos.size(), equalTo(2));
	}

	@Test
	public void getAllOrganizationsForContent() throws Exception {

		when(organizationService.getAllOrganizations()).thenReturn(organizationDtos);
		this.mockMvc.perform(get(ORGANIZATIONS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("\"organizationId\":1")));
	}

	@Test
	public void getAllOrganizationsForHeader() throws Exception {

		when(organizationService.getAllOrganizations()).thenReturn(organizationDtos);
		ResultActions resultActions = this.mockMvc
				.perform(get(ORGANIZATIONS_URI).header(HEADER_KEY, HEADER_VALUE).accept(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk());
		assertThat("Response header value is null",
				resultActions.andReturn().getResponse().getHeader("X-RateLimit-Limit"), not(nullValue()));
	}
}
