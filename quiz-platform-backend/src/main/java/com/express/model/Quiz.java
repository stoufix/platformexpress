package com.express.model;

import java.io.Serializable;

import java.util.Date;
import java.util.List;

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

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import static com.express.util.Constants.QUIZ_FILTER;

/**
 * Represents quiz
 * 

 * @version 1.0
 */
@Entity
@Table(name = "QUIZZES")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@JsonFilter(QUIZ_FILTER)
public class Quiz implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this Quiz. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The title of this Quiz.
	 */
	@Column
	private String title;

	/**
	 * The description of this Quiz.
	 */
	@Column
	private String description;

	/**
	 * The duration of this Quiz.
	 */
	@Column
	private Integer duration;

	/**
	 * The created date of this Quiz.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date createdAt;

	/**
	 * The last modified date of this Quiz.
	 */
	@Column
	@LastModifiedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date updatedAt;

	/**
	 * Indicate if this Quiz is shared with other users or not.
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
	 * The list of passed quizzes with their result.
	 * 
	 * @see OnlineInterviewQuizResult
	 */
	@OneToMany(mappedBy = "quiz")
	private List<OnlineInterviewQuizResult> passedBy;

	/**
	 * The list of assigned quiz interviews.
	 * 
	 * @see AssignedQuizOnlineInterview
	 */
	@OneToMany(mappedBy = "quiz")
	private List<AssignedQuizOnlineInterview> assignedInterviews;

	/**
	 * The degree of this Quiz
	 * 
	 * @see Degree
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "degree_id", nullable = false, foreignKey = @ForeignKey(name = "FK_DEGREE"))
	private Degree degree;

	/**
	 * The list of questions of this Quiz
	 * 
	 * @see QuizQuestion
	 */
	@OneToMany(mappedBy = "quiz")
	private List<QuizQuestion> questions;

	/**
	 * The technology of this Quiz
	 * 
	 * @see Technology
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "technology_id", nullable = false, foreignKey = @ForeignKey(name = "FK_TECHNOLOGY"))
	private Technology technology;

	/**
	 * The Activity of this Quiz
	 * 
	 * @see Activity
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "activity_id", foreignKey = @ForeignKey(name = "FK_ACTIVITY"))
	private Activity activity;

	/**
	 * Gets the ID of this Quiz.
	 * 
	 * @return this Quiz's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this Quiz.
	 * 
	 * @param id This Quiz's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the title of this Quiz.
	 * 
	 * @return this Quiz's title.
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Changes the title of this Quiz.
	 * 
	 * @param title This Quiz's new title.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the description of this Quiz.
	 * 
	 * @return this Quiz's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this Quiz.
	 * 
	 * @param description This Quiz's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the duration of this Quiz.
	 * 
	 * @return this Quiz's duration.
	 */
	public Integer getDuration() {
		return duration;
	}

	/**
	 * Changes the duration of this Quiz.
	 * 
	 * @param duration This Quiz's new duration.
	 */
	public void setDuration(Integer duration) {
		this.duration = duration;
	}

	/**
	 * Gets the created date of this Quiz.
	 * 
	 * @return this Quiz's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this Quiz.
	 * 
	 * @param createdAt This Quiz's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the last modified date of this Quiz.
	 * 
	 * @return this Quiz's last modified date.
	 */
	public Date getUpdatedAt() {
		return updatedAt;
	}

	/**
	 * Changes the last modified date of this Quiz.
	 * 
	 * @param updatedAt This Quiz's new last modified date.
	 */
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	/**
	 * Gets if this Quiz is shared for the other users or not.
	 * 
	 * @return if this Quiz is shared or not.
	 */
	public Boolean getShared() {
		return shared;
	}

	/**
	 * Changes if this Quiz is shared for the other users or not.
	 * 
	 * @param shared This Quiz is shared or not.
	 */
	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	/**
	 * Gets the list of questions of this Quiz
	 * 
	 * @return This Quiz's list of questions
	 * @see QuizQuestion
	 */
	public List<QuizQuestion> getQuestions() {
		return questions;
	}

	/**
	 * Changes the list of questions of this Quiz
	 * 
	 * @param questions TThis Quiz's new list of questions
	 * @see QuizQuestion
	 */
	public void setQuestions(List<QuizQuestion> questions) {
		this.questions = questions;
	}

	/**
	 * Gets the creator of this Quiz.
	 * 
	 * @return this Quiz's last modified date.
	 * @see User
	 */
	public User getCreatedBy() {
		return createdBy;
	}

	/**
	 * Changes the creator of this Quiz.
	 * 
	 * @param createdBy This Quiz's new creator.
	 * @see User
	 */
	public void setCreatedBy(User createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * Gets the list of passed quizzes of this Quiz.
	 * 
	 * @return this Quiz's list of passed quizzes.
	 * @see OnlineInterviewQuizResult
	 */
	public List<OnlineInterviewQuizResult> getPassedBy() {
		return passedBy;
	}

	/**
	 * Changes the list of passed quizzes of this Quiz.
	 * 
	 * @param passedBy This Quiz's new list of passed quizzes.
	 * @see OnlineInterviewQuizResult
	 */
	public void setPassedBy(List<OnlineInterviewQuizResult> passedBy) {
		this.passedBy = passedBy;
	}

	/**
	 * Gets the degree of this Quiz.
	 * 
	 * @return this Quiz's degree.
	 * @see Degree
	 */
	public Degree getDegree() {
		return degree;
	}

	/**
	 * Changes the degree of this Quiz.
	 * 
	 * @param degree This Quiz's degree.
	 * @see Degree
	 */
	public void setDegree(Degree degree) {
		this.degree = degree;
	}

	/**
	 * Gets the technology of this Quiz.
	 * 
	 * @return this Quiz's technology.
	 * @see Technology
	 */
	public Technology getTechnology() {
		return technology;
	}

	/**
	 * Changes the technology of this Quiz.
	 * 
	 * @param technology This Quiz's technology.
	 * @see Technology
	 */
	public void setTechnology(Technology technology) {
		this.technology = technology;
	}

	/**
	 * Gets the list of assigned quiz interviews of this Quiz.
	 * 
	 * @return this Quiz's list of assigned quiz interviews.
	 * @see AssignedQuizOnlineInterview
	 */
	public List<AssignedQuizOnlineInterview> getAssignedInterviews() {
		return assignedInterviews;
	}

	/**
	 * Changes the list of assigned quiz interviews of this Quiz.
	 * 
	 * @param assignedInterviews This Quiz's new list of assigned quizzes.
	 * @see AssignedQuizOnlineInterview
	 */
	public void setAssignedInterviews(List<AssignedQuizOnlineInterview> assignedInterviews) {
		this.assignedInterviews = assignedInterviews;
	}

	/**
	 * Gets the activity of this Quiz.
	 * 
	 * @return this Quiz's activity.
	 * @see Activity
	 */
	public Activity getActivity() {
		return activity;
	}

	/**
	 * Changes the activity of this Quiz.
	 * 
	 * @param activity This Quiz's activity.
	 * @see Activity
	 */
	public void setActivity(Activity activity) {
		this.activity = activity;
	}

}
