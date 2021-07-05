package com.express.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
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

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Represents online interview to pass by candidate: contain list of online
 * quizzes
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Entity
@Table(name = "ONLINE_INTERVIEWS")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
public class OnlineInterview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this OnlineInterview. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The created date of this OnlineInterview.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy")
	private Date createdAt;

	/**
	 * The comments of this OnlineInterview.
	 */
	@Column
	private String comments;

	/**
	 * The status of this OnlineInterview.
	 */
	@Column
	private String status;

	/**
	 * The list of assigned quiz of this OnlineInterview.
	 * 
	 * @see AssignedQuizOnlineInterview
	 */
	@OneToMany(mappedBy = "onlineInterview")
	private List<AssignedQuizOnlineInterview> assignedQuizzes;

	/**
	 * The list of quiz result of this OnlineInterview.
	 * 
	 * @see OnlineInterviewQuizResult
	 */
	@JsonIgnore
	@OneToMany(mappedBy = "onlineInterview")
	private List<OnlineInterviewQuizResult> passedQuizzes;

	/**
	 * The application who this OnlineInterview belongs to.
	 * 
	 * @see Application
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "application_id", foreignKey = @ForeignKey(name = "FK_APPLICATION"))
	private Application application;

	/**
	 * Gets the ID of this OnlineInterview.
	 * 
	 * @return this OnlineInterview's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this OnlineInterview.
	 * 
	 * @param id This OnlineInterview's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the created date of this OnlineInterview.
	 * 
	 * @return this OnlineInterview's created date.
	 */
	public Date getCreatedAt() {
		return createdAt;
	}

	/**
	 * Changes the created date of this OnlineInterview.
	 * 
	 * @param createdAt This OnlineInterview's new created date.
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	/**
	 * Gets the comments of this OnlineInterview.
	 * 
	 * @return this OnlineInterview's comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Changes the comments of this OnlineInterview.
	 * 
	 * @param comments This OnlineInterview's new comments.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Gets the list of assigned quiz of this OnlineInterview.
	 * 
	 * @return this OnlineInterview's list of assigned quiz.
	 * @see AssignedQuizOnlineInterview
	 */
	public List<AssignedQuizOnlineInterview> getAssignedQuizzes() {
		return assignedQuizzes;
	}

	/**
	 * Changes the list of assigned quiz of this OnlineInterview.
	 * 
	 * @param assignedQuizzes This OnlineInterview's new list of assigned quiz.
	 * @see AssignedQuizOnlineInterview
	 */
	public void setAssignedQuizzes(List<AssignedQuizOnlineInterview> assignedQuizzes) {
		this.assignedQuizzes = assignedQuizzes;
	}

	/**
	 * Gets the list of quiz result of this OnlineInterview.
	 * 
	 * @return this OnlineInterview's list of quiz result.
	 * @see OnlineInterviewQuizResult
	 */
	public List<OnlineInterviewQuizResult> getPassedQuizzes() {
		return passedQuizzes;
	}

	/**
	 * Changes the list of quiz result of this OnlineInterview.
	 * 
	 * @param passedQuizzes This OnlineInterview's new list of quiz result.
	 * @see OnlineInterviewQuizResult
	 */
	public void setPassedQuizzes(List<OnlineInterviewQuizResult> passedQuizzes) {
		this.passedQuizzes = passedQuizzes;
	}

	/**
	 * Gets the application who this OnlineInterview belong.
	 * 
	 * @return this OnlineInterview's application.
	 * @see Application
	 */
	public Application getApplication() {
		return application;
	}

	/**
	 * Changes the application who this OnlineInterview belong.
	 * 
	 * @param application This OnlineInterview's new belong application.
	 * @see Application
	 */
	public void setApplication(Application application) {
		this.application = application;
	}

}
