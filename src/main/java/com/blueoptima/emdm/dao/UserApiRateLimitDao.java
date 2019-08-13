package com.blueoptima.emdm.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blueoptima.emdm.entity.UserApiRateLimit;

@Repository
public interface UserApiRateLimitDao extends JpaRepository<UserApiRateLimit, Long> {

	UserApiRateLimit findByUsernameAndApi(String username, String api);

}
