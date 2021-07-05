package com.altran.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;
import static com.altran.util.Constants.ROLE_FILTER;

/**
 * Represents roles of users
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "ROLES")
@JsonFilter(ROLE_FILTER)
public class Role implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Role. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Role.
	 */
	@Column(unique = true)
	@NotNull
	private String title;

	/**
	 * The description of this Role.
	 */
	@Column
	private String description;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "ROLE_PRIVILEGES")
	private Set<Privilege> privileges = new HashSet<>();

	/**
	 * Gets the ID of this Role.
	 * 
	 * @return this Role's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Role.
	 * 
	 * @param id This Role's ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the ID of this Role.
	 * 
	 * @return this Role's ID.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Role.
	 * 
	 * @param title This Role's title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the ID of this Role.
	 * 
	 * @return this Role's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Role.
	 * 
	 * @param description This Role's description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the list of privileges of this Role.
	 * 
	 * @return this Role's list of privileges.
	 */
	public Set<Privilege> getPrivileges() {
		return privileges;
	}

	/**
	 * Changes the list of privileges of this Role.
	 * 
	 * @param privileges This Technology's list of privileges.
	 */
	public void setPrivileges(Set<Privilege> privileges) {
		this.privileges = privileges;
	}

}
