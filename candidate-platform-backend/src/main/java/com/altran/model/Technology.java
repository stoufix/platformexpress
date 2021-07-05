package com.altran.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Represents technologies of quizzes
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "TECHNOLOGIES")
public class Technology implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Technology. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Technology.
	 */
	@Column
	@NotNull
	private String title;

	/**
	 * The description of this Technology.
	 */
	@Column
	private String description;

	/**
	 * Gets the ID of this Technology.
	 * 
	 * @return this Technology's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Technology.
	 * 
	 * @param id This Technology's ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title of this Technology.
	 * 
	 * @return this Technology's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Technology.
	 * 
	 * @param title This Technology's title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description of this Technology.
	 * 
	 * @return this Technology's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Technology.
	 * 
	 * @param description This Technology's description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
