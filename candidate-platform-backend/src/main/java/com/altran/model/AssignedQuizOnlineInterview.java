package com.altran.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Represents many-to-many association class between quiz and onlineInterview:
 * assign quiz to an online interview of candidate
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@Entity
@Table(name = "ASSIGNED_QUIZ_ONLINE_INTERVIEW")
@EntityListeners(AuditingEntityListener.class)
public class AssignedQuizOnlineInterview implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The embedded ID of this AssignedQuizOnlineInterview. This ID composed by the
	 * id of quiz and the id of onlineInterview
	 * 
	 * @see AssignedQuizOnlineInterviewId
	 */
	@EmbeddedId
	private AssignedQuizOnlineInterviewId id = new AssignedQuizOnlineInterviewId();

	/**
	 * The assign date of this AssignedQuizOnlineInterview.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date assignedAt;

	/**
	 * The passed quiz date of this AssignedQuizOnlineInterview.
	 */
	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date passingAt;

	/**
	 * The quiz of this AssignedQuizOnlineInterview.
	 * 
	 * @see Quiz
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("quizId")
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	/**
	 * The onlineInterview of this AssignedQuizOnlineInterview.
	 * 
	 * @see OnlineInterview
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("onlineInterviewId")
	@JoinColumn(name = "online_interview_id")
	private OnlineInterview onlineInterview;

	/**
	 * The user who assigned this AssignedQuizOnlineInterview.
	 * 
	 * @see User
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "assignedby_id", nullable = false, foreignKey = @ForeignKey(name = "FK_ASSIGNEDBY"))
	private User assignedBy;

	/**
	 * Gets the ID of this AssignedQuizOnlineInterview.
	 * 
	 * @return this AssignedQuizOnlineInterview's ID.
	 * @see AssignedQuizOnlineInterviewId
	 */
	public AssignedQuizOnlineInterviewId getId() {
		return id;
	}

	/**
	 * Changes the ID of this AssignedQuizOnlineInterview.
	 * 
	 * @param id This AssignedQuizOnlineInterview's new ID.
	 * @see AssignedQuizOnlineInterviewId
	 */
	public void setId(AssignedQuizOnlineInterviewId id) {
		this.id = id;
	}

	/**
	 * Gets the date when this AssignedQuizOnlineInterview assigned.
	 * 
	 * @return this AssignedQuizOnlineInterview's assigning date.
	 */
	public Date getAssignedAt() {
		return assignedAt;
	}

	/**
	 * Changes the date when this AssignedQuizOnlineInterview assigned.
	 * 
	 * @param assignedAt This AssignedQuizOnlineInterview's new assigned date.
	 */
	public void setAssignedAt(Date assignedAt) {
		this.assignedAt = assignedAt;
	}

	/**
	 * Gets the date when this AssignedQuizOnlineInterview passed.
	 * 
	 * @return this AssignedQuizOnlineInterview's passed date.
	 */
	public Date getPassingAt() {
		return passingAt;
	}

	/**
	 * Changes the date when this AssignedQuizOnlineInterview passed.
	 * 
	 * @param passingAt This AssignedQuizOnlineInterview's new passed date.
	 */
	public void setPassingAt(Date passingAt) {
		this.passingAt = passingAt;
	}

	/**
	 * Gets the quiz of this AssignedQuizOnlineInterview.
	 * 
	 * @return this AssignedQuizOnlineInterview's quiz.
	 * @see Quiz
	 */
	public Quiz getQuiz() {
		return quiz;
	}

	/**
	 * Changes the quiz of this AssignedQuizOnlineInterview.
	 * 
	 * @return quiz this AssignedQuizOnlineInterview's new quiz.
	 * @see Quiz
	 */
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

	/**
	 * Gets the onlineInterview of this AssignedQuizOnlineInterview.
	 * 
	 * @return this AssignedQuizOnlineInterview's onlineInterview.
	 * @see OnlineInterview
	 */
	public OnlineInterview getOnlineInterview() {
		return onlineInterview;
	}

	/**
	 * Changes the onlineInterview of this AssignedQuizOnlineInterview.
	 * 
	 * @return onlineInterview this AssignedQuizOnlineInterview's new
	 *         onlineInterview.
	 * @see OnlineInterview
	 */
	public void setOnlineInterview(OnlineInterview onlineInterview) {
		this.onlineInterview = onlineInterview;
	}

	/**
	 * Gets the user who assigned this AssignedQuizOnlineInterview.
	 * 
	 * @return this AssignedQuizOnlineInterview's assigned by user.
	 * @see User
	 */
	public User getAssignedBy() {
		return assignedBy;
	}

	/**
	 * Changes the user who assigned this AssignedQuizOnlineInterview.
	 * 
	 * @return assignedBy this AssignedQuizOnlineInterview's new assigned by user.
	 * @see User
	 */
	public void setAssignedBy(User assignedBy) {
		this.assignedBy = assignedBy;
	}

}
