package com.blueoptima.emdm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.emdm.entity.Organization;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, Long> {

}
