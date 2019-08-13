package com.blueoptima.emdm;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.blueoptima.emdm.dao.DeveloperDao;
import com.blueoptima.emdm.dto.DeveloperDto;
import com.blueoptima.emdm.entity.Developer;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.exception.NoDataFoundException;
import com.blueoptima.emdm.service.DeveloperService;
import com.blueoptima.emdm.service.impl.DeveloperServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class DeveloperServiceImplTests {

	private DeveloperService developerService;
	@Mock
	private DeveloperDao developerDao;
	private ArrayList<Developer> developers;
	private Date on = new Date();
	private Developer developer1;
	private Developer developer2;
	@Before
	public void setup() {
		developer1 = new Developer();
		developer1.setDeveloperId(1l);
		developer1.setName("testDev1");
		developer1.setOrganization(new Organization(1l));
		developer1.setCreatedBy("testUser");
		developer1.setCreatedOn(new Date());
		developer1.setAge(41);
		developer1.setUpdatedBy("testUser");
		developer1.setUpdatedOn(on);
		developer2 = new Developer();
		developer2.setDeveloperId(2l);
		developer2.setName("testDev2");
		developer2.setOrganization(new Organization(2l));
		developer2.setCreatedBy("testUser");
		developer2.setCreatedOn(on);
		developer2.setAge(31);
		developer2.setUpdatedBy("testUser");
		developer2.setUpdatedOn(new Date());
		this.developers = new ArrayList<Developer>() {
			private static final long serialVersionUID = -1748706078381702183L;
			{
				add(developer1);
				add(developer2);
			}
		};
		this.developerService = new DeveloperServiceImpl(developerDao);
	}

	@Test
	public void getAllDevelopersForSize() throws Exception {
		when(developerDao.findAll()).thenReturn(this.developers);
		List<DeveloperDto> developersExp = this.developerService.getAllDevelopers();
		assertThat("The returned size is not as expected", developersExp.size(), equalTo(2));
		verify(developerDao, times(1)).findAll();
	}

	@Test
	public void getAllDevelopersForContent() throws Exception {
		ObjectMapper om = new ObjectMapper();
		when(developerDao.findAll()).thenReturn(this.developers);
		List<DeveloperDto> developersExp = this.developerService.getAllDevelopers();
		developersExp.stream().forEach(dev -> {
			assertThat("The id of developer is not as expected", dev.getDeveloper().getDeveloperId()+"",
					either(containsString("1")).or(containsString("2")));
			assertThat("The id of developer is not as expected", dev.getDeveloperId()+"",
					either(containsString("1")).or(containsString("2")));
			assertThat("The name of developer is not as expected", dev.getDeveloper().getName(),
					either(containsString("testDev1")).or(containsString("testDev2")));
			assertThat("The organization id of user is not as expected", dev.getDeveloper().getOrganization().getOrganizationId()+"",
					either(containsString("1")).or(containsString("2")));
			assertThat("The age of developer is not as expected", dev.getDeveloper().getAge().toString(),
					containsString("1"));
			assertThat("The created by user is not as expected", dev.getDeveloper().getCreatedBy(),
					containsString("testUser"));
			assertThat("The updated by user is not as expected", dev.getDeveloper().getUpdatedBy(),
					containsString("testUser"));
			assertThat("The developer is null", dev.getDeveloper(),
					not(equalTo(null)));
			assertThat("The developer is of organization type", dev.getDeveloper(),
					not(equalTo(new Organization())));
			assertThat("The developer is not as expected", dev.getDeveloper(),
					either(equalTo(developer1)).or(equalTo(developer2)));
			assertThat("The organization is not as expected", dev.getDeveloper().hashCode(),
					either(equalTo(developer1.hashCode())).or(equalTo(developer2.hashCode())));
		});
		assertThat("The returned content is not as expected", om.writeValueAsString(developersExp),
				containsString("\"developerId\":1"));
		verify(developerDao, times(1)).findAll();
	}

	@Test(expected = NoDataFoundException.class)
	public void getAllDevelopersForNoDataFound() throws Exception {
		when(developerDao.findAll()).thenReturn(Collections.emptyList());
		this.developerService.getAllDevelopers();
	}

}