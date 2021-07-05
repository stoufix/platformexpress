package com.altran.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import javax.persistence.ForeignKey;

/**
 * Represents answered propositions of question answered by candidate
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "ANSWERED_PROPOSITIONS")
public class AnsweredProposition implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this AnsweredProposition. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The description of this AnsweredProposition.
	 */
	@Column
	@NotNull
	private String description;

	/**
	 * The validation of this AnsweredProposition. Indicate if this proposition is
	 * correct or not.
	 */
	@Column
	@NotNull
	private Boolean valid;

	/**
	 * The response of candidate for this AnsweredProposition. Indicate if this
	 * proposition is indicate if the proposition is checked by the candidate or not
	 */
	@Column
	@NotNull
	private Boolean checked;

	/**
	 * The PassedQuestion who this AnsweredProposition belongs to.
	 * 
	 * @see PassedQuestion
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "passed_question_id", nullable = false, foreignKey = @ForeignKey(name = "FK_PASSED_QUESTION"))
	private PassedQuestion passedQuestion;

	/**
	 * Gets the ID of this AnsweredProposition.
	 * 
	 * @return this AnsweredProposition's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this AnsweredProposition.
	 * 
	 * @param id This AnsweredProposition's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description of this AnsweredProposition.
	 * 
	 * @return this AnsweredProposition's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this AnsweredProposition.
	 * 
	 * @param description This AnsweredProposition's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if this AnsweredProposition is valid or not.
	 * 
	 * @return this AnsweredProposition's validation.
	 */
	public Boolean isValid() {
		return valid;
	}

	/**
	 * Changes the validation of this AnsweredProposition.
	 * 
	 * @param valid This AnsweredProposition's new validation.
	 */
	public void setValid(Boolean valid) {
		this.valid = valid;
	}

	/**
	 * Checks if this AnsweredProposition is checked by the candidate or not.
	 * 
	 * @return this AnsweredProposition is checked or not.
	 */
	public Boolean isChecked() {
		return checked;
	}

	/**
	 * Changes if this AnsweredProposition is checked by the candidate or not.
	 * 
	 * @param checked this AnsweredProposition is checked or not.
	 */
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}

	/**
	 * Gets the PassedQuestion who this AnsweredProposition belongs to.
	 * 
	 * @return this AnsweredProposition's PassedQuestion.
	 * @see PassedQuestion
	 */
	public PassedQuestion getPassedQuestion() {
		return passedQuestion;
	}

	/**
	 * Changes the PassedQuestion who this AnsweredProposition belongs to.
	 * 
	 * @param passedQuestion the new PassedQuestion who this AnsweredProposition
	 *                       belongs to.
	 * @see PassedQuestion
	 */
	public void setPassedQuestion(PassedQuestion passedQuestion) {
		this.passedQuestion = passedQuestion;
	}

}
