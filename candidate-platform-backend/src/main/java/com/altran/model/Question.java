package com.altran.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Represents question to use in quizzes
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "QUESTIONS")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class Question implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Question. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The description of this Question.
	 */
	@NotNull
	@Column(columnDefinition = "text", length = 10485760)
	private String description;

	/**
	 * The created date of this Question.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date createdAt;

	/**
	 * The last modified date of this Question.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date updatedAt;

	/**
	 * Indicate if this Question is shared with other users or not.
	 */
	@Column
	private Boolean shared;

	/**
	 * The creator of this Question.
	 * 
	 * @see User
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "FK_CREATED_BY"))
	private User createdBy;

	/**
	 * The quiz who this Question belong to. Indicate the association class between
	 * quiz and question.
	 * 
	 * @see QuizQuestion
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@OneToMany(mappedBy = "question")
	private List<QuizQuestion> quizList;

	/**
	 * The list of proposition of this Question
	 * 
	 * @see Proposition
	 */
	@OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
	private Set<Proposition> propositions;

	/**
	 * The technology of this Question
	 * 
	 * @see Technology
	 */
	@ManyToOne
	@JoinColumn(name = "technology_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TECHNOLOGY"))
	private Technology technology;

	/**
	 * The degree of this Question
	 * 
	 * @see Degree
	 */
	@ManyToOne
	@JoinColumn(name = "degree_id", nullable = false, foreignKey = @ForeignKey(name = "FK_DEGREE"))
	private Degree degree;

	/**
	 * The Activity of this Question
	 * 
	 * @see Activity
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "FK_ACTIVITY"))
	private Activity activity;

	/**
	 * Gets the ID of this Question.
	 * 
	 * @return this Question's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Question.
	 * 
	 * @param id This Question's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description of this Question.
	 * 
	 * @return this Question's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Question.
	 * 
	 * @param description This Question's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the created date of this Question.
	 * 
	 * @return this Question's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this Question.
	 * 
	 * @param createdAt This Question's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the last modified date of this Question.
	 * 
	 * @return this Question's last modified date.
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Changes the last modified date of this Question.
	 * 
	 * @param createdAt This Question's new last modified date.
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Gets if this Question is shared for the other users or not.
	 * 
	 * @return if this Question is shared or not.
	 */
	public Boolean getShared() {
		return shared;
	}

	/**
	 * Changes if this Question is shared for the other users or not.
	 * 
	 * @param shared This Question is shared or not.
	 */
	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	/**
	 * Gets the creator of this Question.
	 * 
	 * @return this Question's last modified date.
	 * @see User
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * Changes the creator of this Question.
	 * 
	 * @param createdBy This Question's new creator.
	 * @see User
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the list of quizzes who this Question belongs to.
	 * 
	 * @return this Question's list of quizzes.
	 * @see QuizQuestion
	 */
	public List<QuizQuestion> getQuizList() {
		return quizList;
	}

	/**
	 * Changes the list of quizzes who this Question belongs to.
	 * 
	 * @param quizList This Question's list of quizzes.
	 * @see QuizQuestion
	 */
	public void setQuizList(List<QuizQuestion> quizList) {
		this.quizList = quizList;
	}

	/**
	 * Gets the list of propositions of this Question.
	 * 
	 * @return this Question's list of propositions.
	 * @see Proposition
	 */
	public Set<Proposition> getPropositions() {
		return propositions;
	}

	/**
	 * Changes the list of propositions of this Question.
	 * 
	 * @param propositions This Question's list of propositions.
	 * @see Proposition
	 */
	public void setPropositions(Set<Proposition> propositions) {
		this.propositions = propositions;
	}

	/**
	 * Gets the technology of this Question.
	 * 
	 * @return this Question's technology.
	 * @see Technology
	 */
	public Technology getTechnology() {
		return technology;
	}

	/**
	 * Changes the technology of this Question.
	 * 
	 * @param technology This Question's technology.
	 * @see Technology
	 */
	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	/**
	 * Gets the degree of this Question.
	 * 
	 * @return this Question's degree.
	 * @see Degree
	 */
	public Degree getDegree() {
		return degree;
	}

	/**
	 * Changes the degree of this Question.
	 * 
	 * @param degree This Question's degree.
	 * @see Degree
	 */
	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	/**
	 * Gets the activity of this Question.
	 * 
	 * @return this Question's activity.
	 * @see Activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Changes the activity of this Question.
	 * 
	 * @param activity This Question's activity.
	 * @see Activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
