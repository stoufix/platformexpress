package com.altran.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

import static com.altran.util.Constants.ACTIVITY_FILTER;

/**
 * Represents activities of the company
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "ACTIVITIES")
@JsonFilter(ACTIVITY_FILTER)
public class Activity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Activity. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Activity.
	 */
	@Column
	@NotNull
	private String title;

	/**
	 * The description of this Activity.
	 */
	@Column
	private String description;

	/**
	 * Gets the ID of this Activity.
	 * 
	 * @return this Activity's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Activity.
	 * 
	 * @param id This Activity's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title of this Activity.
	 * 
	 * @return this Activity's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Activity.
	 * 
	 * @param title This Activity's new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description of this Activity.
	 * 
	 * @return this Activity's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Activity.
	 * 
	 * @param description This Activity's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
