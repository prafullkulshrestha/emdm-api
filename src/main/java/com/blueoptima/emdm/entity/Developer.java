package com.blueoptima.emdm.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author prafullkulshrestha
 *
 */
@Entity
@Table(name = "developers")
public class Developer extends BaseEntity {

	private static final long serialVersionUID = -6640048403947049875L;
	private long developerId;
	private Organization organization;
	private String name;
	private Integer age;

	public Developer() {
	}

	public Developer(long developerId, Organization organization) {
		this.developerId = developerId;
		this.organization = organization;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "developer_id", unique = true, nullable = false)
	public long getDeveloperId() {
		return this.developerId;
	}

	public void setDeveloperId(long developerId) {
		this.developerId = developerId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "organization_id", nullable = false)
	public Organization getOrganization() {
		return this.organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Column(name = "name", length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "age")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("developerId", developerId)
				.append("organization", organization).append("name", name).append("age", age).toString();
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
		if (!(other instanceof Developer))
			return false;

		Developer castOther = (Developer) other;

		return new EqualsBuilder().append(developerId, castOther.developerId)
				.append(organization, castOther.organization).append(name, castOther.name).append(age, castOther.age)
				.isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return new HashCodeBuilder().append(developerId).append(organization).append(name).append(age).toHashCode();
	}

}
