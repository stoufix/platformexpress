package com.express.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents privileges to attribute to users of the application by his role
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "PRIVILEGES")
public class Privilege implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Privilege. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Privilege.
	 */
	@Column
	private String title;

	/**
	 * The category of this Privilege.
	 */
	@Column
	private String category;

	/**
	 * Gets the ID of this Privilege.
	 * 
	 * @return this Privilege's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Privilege.
	 * 
	 * @param id This Privilege's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title of this Privilege.
	 * 
	 * @return this Privilege's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Privilege.
	 * 
	 * @param title This Privilege's new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the category of this Privilege.
	 * 
	 * @return this Privilege's category.
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Changes the category of this Privilege.
	 * 
	 * @param category This Privilege's new category.
	 */
	public void setCategory(String category) {
		this.category = category;
	}

}
