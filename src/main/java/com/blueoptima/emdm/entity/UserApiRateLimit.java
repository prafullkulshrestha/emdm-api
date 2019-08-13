package com.blueoptima.emdm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author
 */

@Entity
@Table(name = "user_api_rate_limits")
public class UserApiRateLimit extends BaseEntity {

	private static final long serialVersionUID = -7793829972714824193L;
	private long userApiRateLimitId;
	private String username;
	private String api;
	private Integer rateLimit;

	public UserApiRateLimit() {
		//The default constructor
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "user_api_rate_limit_id", unique = true, nullable = false)
	public long getUserApiRateLimitId() {
		return this.userApiRateLimitId;
	}

	public void setUserApiRateLimitId(long userApiRateLimitId) {
		this.userApiRateLimitId = userApiRateLimitId;
	}

	@Column(name = "username", length = 50)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "api", length = 100)
	public String getApi() {
		return this.api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	@Column(name = "rate_limit")
	public Integer getRateLimit() {
		return this.rateLimit;
	}

	public void setRateLimit(Integer rateLimit) {
		this.rateLimit = rateLimit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("userApiRateLimitId", userApiRateLimitId).append("username", username).append("api", api)
				.append("rateLimit", rateLimit).toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object other) {

		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof UserApiRateLimit))
			return false;

		UserApiRateLimit castOther = (UserApiRateLimit) other;

		return new EqualsBuilder().append(userApiRateLimitId, castOther.userApiRateLimitId)
				.append(username, castOther.username).append(api, castOther.api).append(rateLimit, castOther.rateLimit)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return new HashCodeBuilder().append(userApiRateLimitId).append(username).append(api).append(rateLimit)
				.toHashCode();
	}

}
