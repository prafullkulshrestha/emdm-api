package com.blueoptima.emdm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.emdm.entity.Developer;

@Repository
public interface DeveloperDao extends JpaRepository<Developer, Long> {

}
