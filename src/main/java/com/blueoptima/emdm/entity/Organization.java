package com.blueoptima.emdm.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author prafullkulshrestha
 *
 */
@Entity
@Table(name = "organizations", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Organization extends BaseEntity {

	private static final long serialVersionUID = -6912422684240024345L;
	private long organizationId;
	private String name;
	private String about;
	private Set<Developer> developers = new HashSet<>();

	public Organization() {
	}

	public Organization(long organizationId) {
		this.organizationId = organizationId;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "organization_id", unique = true, nullable = false)
	public long getOrganizationId() {
		return this.organizationId;
	}

	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}

	@Column(name = "name", unique = true, length = 100)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "about", length = 500)
	public String getAbout() {
		return this.about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "organization")
	public Set<Developer> getDevelopers() {
		return this.developers;
	}

	public void setDevelopers(Set<Developer> developers) {
		this.developers = developers;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {

		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("organizationId", organizationId)
				.append("organizationName", name).append("organizationAbout", about).toString();
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
		if (!(other instanceof Organization))
			return false;

		Organization castOther = (Organization) other;

		return new EqualsBuilder().append(organizationId, castOther.organizationId).append(name, castOther.name)
				.append(about, castOther.about).isEquals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {

		return new HashCodeBuilder().append(organizationId).append(name).append(about).toHashCode();
	}

}
