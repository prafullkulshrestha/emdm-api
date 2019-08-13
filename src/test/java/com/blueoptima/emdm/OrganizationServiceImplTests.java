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
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.blueoptima.emdm.dao.OrganizationDao;
import com.blueoptima.emdm.dto.OrganizationDto;
import com.blueoptima.emdm.entity.Developer;
import com.blueoptima.emdm.entity.Organization;
import com.blueoptima.emdm.exception.NoDataFoundException;
import com.blueoptima.emdm.service.OrganizationService;
import com.blueoptima.emdm.service.impl.OrganizationServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class OrganizationServiceImplTests {

	private OrganizationService organizationService;
	@Mock
	private OrganizationDao organizationDao;
	private ArrayList<Organization> organizations;
	private Organization org1;
	private Organization org2;
	private Organization orgWithName;
	
	@Before
	public void setup() {
		orgWithName = new Organization();
		orgWithName.setOrganizationId(3l);
		orgWithName.setName("testName");
		orgWithName.setAbout("testAbout");
		orgWithName.setDevelopers(null);
		org1 = new Organization();
		org1.setOrganizationId(1l);
		org2 = new Organization();
		org2.setOrganizationId(2l);
		this.organizations = new ArrayList<Organization>() {
			private static final long serialVersionUID = -1748706078381702183L;
			{
				add(org1);
				add(org2);
				add(orgWithName);
			}
		};
		this.organizationService = new OrganizationServiceImpl(organizationDao);
	}

	@Test
	public void getAllOrganizationsForSizeAndContent() throws Exception {
		when(organizationDao.findAll()).thenReturn(this.organizations);
		List<OrganizationDto> organizationsExp = this.organizationService.getAllOrganizations();
		Organization organization = organizationsExp.stream().filter(odto -> odto.getName()!=null).findFirst().get().getOrganization();
		assertThat("The organization name is not as expected", organization.getName(), equalTo("testName"));
		assertThat("The organization about is not as expected", organization.getAbout(), equalTo("testAbout"));
		assertThat("The organizations list not as expected", organization.getDevelopers(), equalTo(null));
		assertThat("The returned object is of developer type", organization, not(equalTo(new Developer())));
		assertThat("The returned object is null", organization, not(equalTo(null)));;
		assertThat("The returned org id is not as expected", organization.getOrganizationId(), equalTo(3l));
		assertThat("The returned size is not as expected", organizationsExp.size(), equalTo(3));
		
		organizationsExp.stream().forEach(oE -> {
			assertThat("The organization is not as expected", oE.getOrganization(),
					either(equalTo(org1)).or(equalTo(org2)).or(equalTo(orgWithName)));
			assertThat("The organization is not as expected", oE.getOrganization().hashCode(),
					either(equalTo(org1.hashCode())).or(equalTo(org2.hashCode())).or(equalTo(orgWithName.hashCode())));
			assertThat("The id of organization is not as expected", oE.getOrganizationId()+"",
					either(containsString("1")).or(containsString("2")).or(containsString("3")));
		});
		verify(organizationDao, times(1)).findAll();
	}

	@Test
	public void getAllOrganizationsForContent() throws Exception {
		ObjectMapper om = new ObjectMapper();
		when(organizationDao.findAll()).thenReturn(this.organizations);
		List<OrganizationDto> OrganizationsExp = this.organizationService.getAllOrganizations();
		assertThat("The returned content is not as expected", om.writeValueAsString(OrganizationsExp),
				containsString("\"organizationId\":1"));
		verify(organizationDao, times(1)).findAll();
	}

	@Test(expected = NoDataFoundException.class)
	public void getAllOrganizationsForNoDataFound() throws Exception {
		when(organizationDao.findAll()).thenReturn(Collections.emptyList());
		this.organizationService.getAllOrganizations();
	}

}