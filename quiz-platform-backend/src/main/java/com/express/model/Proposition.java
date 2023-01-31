package com.express.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Represents proposition of question
 * 

 * @version 1.0
 */
@Entity
@Table(name = "PROPOSITIONS")
public class Proposition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Proposition. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The description of this Proposition.
	 */
	@Column
	@NotNull
	private String description;

	/**
	 * The validation of this Proposition. Indicate if this proposition is correct
	 * or not.
	 */
	@Column
	@NotNull
	private Boolean valid;

	/**
	 * The question who this Proposition belongs to.
	 * 
	 * @see Question
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "question_id", nullable = false, foreignKey = @ForeignKey(name = "FK_QUESTION"))
	private Question question;

	/**
	 * Gets the ID of this Proposition.
	 * 
	 * @return this Proposition's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Proposition.
	 * 
	 * @param id This Proposition's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description of this Proposition.
	 * 
	 * @return this Proposition's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Proposition.
	 * 
	 * @param id This Proposition's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if this Proposition is valid or not.
	 * 
	 * @return this Proposition's validation.
	 */
	public Boolean isValid() {
		return valid;
	}

	/**
	 * Changes the validation of this Proposition.
	 * 
	 * @param valid This Proposition's new validation.
	 */
	public void isValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * Gets the question who this Proposition belongs to.
	 * 
	 * @return this Proposition's question.
	 * @see Question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Changes the question who this Proposition belongs to.
	 * 
	 * @param question the new question who this Proposition belongs to.
	 * @see Question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

}
