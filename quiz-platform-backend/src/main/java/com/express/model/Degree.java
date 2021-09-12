package com.express.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFilter;

import static com.express.util.Constants.DEGREE_FILTER;

/**
 * Represents Degree of question or quiz
 * @version 1.0
 */
@Entity
@Table(name = "DEGREES")
@JsonFilter(DEGREE_FILTER)
public class Degree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Degree. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Degree.
	 */
	@Column
	@NotNull
	private String title;

	/**
	 * The description of this Degree.
	 */
	@Column
	private String description;

	/**
	 * Gets the ID of this Degree.
	 * 
	 * @return this Degree's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Degree.
	 * 
	 * @param id This Degree's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title of this Degree.
	 * 
	 * @return this Degree's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Degree.
	 * 
	 * @param title This Degree's new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description of this Degree.
	 * 
	 * @return this Degree's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Degree.
	 * 
	 * @param description This Degree's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
