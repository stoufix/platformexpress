package com.express.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonFormat;

import static com.express.util.Constants.RESULT_FILTER;

/**
 * Represents many-to-many association between quiz and onlineInterview: result
 * of passed quiz by candidate
 * 

 * @version 1.0
 */
@Entity
@Table(name = "QUIZ_RESULTS")
@EntityListeners(AuditingEntityListener.class)
@JsonFilter(RESULT_FILTER)
public class OnlineInterviewQuizResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The embedded ID of this OnlineInterviewQuizResult. This ID composed by the id
	 * of quiz and the id of onlineInterview
	 * 
	 * @see OnlineInterviewQuizResultId
	 */
	@EmbeddedId
	private OnlineInterviewQuizResultId id = new OnlineInterviewQuizResultId();

	/**
	 * The note of this OnlineInterviewQuizResult.
	 */
	@Column
	@NotNull
	private Double note;

	/**
	 * The interview date of this OnlineInterviewQuizResult.
	 */
	@Column
	@CreatedDate
	@JsonFormat(pattern = "dd-MM-yyy HH:mm:ss")
	private Date interviewDate;

	/**
	 * The duration of this OnlineInterviewQuizResult.
	 */
	@Column
	private Double duration;

	/**
	 * The comments about this OnlineInterviewQuizResult.
	 */
	@Column
	private String comments;

	/**
	 * The type of this OnlineInterviewQuizResult.
	 */
	@Column
	private String type;

	/**
	 * The onlineInterview of this OnlineInterviewQuizResult.
	 * 
	 * @see OnlineInterview
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "online_interview_id", foreignKey = @ForeignKey(name = "FK_ONLINEINTERVIEW"))
	@MapsId("onlineInterviewId")
	private OnlineInterview onlineInterview;

	/**
	 * The quiz of this OnlineInterviewQuizResult.
	 * 
	 * @see Quiz
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "quiz_id", foreignKey = @ForeignKey(name = "FK_QUIZ"))
	@MapsId("quizId")
	private Quiz quiz;

	/**
	 * The list o passed questions of this OnlineInterviewQuizResult.
	 * 
	 * @see PassedQuestion
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private Set<PassedQuestion> passedQuestions;

	/**
	 * Gets the ID of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's ID.
	 * @see OnlineInterviewQuizResultId
	 */
	public OnlineInterviewQuizResultId getId() {
		return id;
	}

	/**
	 * Changes the ID of this OnlineInterviewQuizResult.
	 * 
	 * @param id This OnlineInterviewQuizResult's new ID.
	 * @see OnlineInterviewQuizResultId
	 */
	public void setId(OnlineInterviewQuizResultId id) {
		this.id = id;
	}

	/**
	 * Gets the note of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's note.
	 */
	public Double getNote() {
		return note;
	}

	/**
	 * Changes the note of this OnlineInterviewQuizResult.
	 * 
	 * @param note This OnlineInterviewQuizResult's new note.
	 */
	public void setNote(Double note) {
		this.note = note;
	}

	/**
	 * Gets the interview date of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's interview date.
	 */
	public Date getInterviewDate() {
		return interviewDate;
	}

	/**
	 * Changes the interview date of this OnlineInterviewQuizResult.
	 * 
	 * @param interviewDate This OnlineInterviewQuizResult's new interview date.
	 */
	public void setInterviewDate(Date interviewDate) {
		this.interviewDate = interviewDate;
	}

	/**
	 * Gets the duration of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's duration.
	 */
	public Double getDuration() {
		return duration;
	}

	/**
	 * Changes the duration of this OnlineInterviewQuizResult.
	 * 
	 * @param duration This OnlineInterviewQuizResult's new duration.
	 */
	public void setDuration(Double duration) {
		this.duration = duration;
	}

	/**
	 * Gets the list of passed questions of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's list of passed questions.
	 * @see PassedQuestion
	 */
	public Set<PassedQuestion> getPassedQuestions() {
		return passedQuestions;
	}

	/**
	 * Changes the list of passed questions of this OnlineInterviewQuizResult.
	 * 
	 * @param passedQuestions This OnlineInterviewQuizResult's new list of passed
	 *                        questions.
	 * @see PassedQuestion
	 */
	public void setPassedQuestions(Set<PassedQuestion> passedQuestions) {
		this.passedQuestions = passedQuestions;
	}

	/**
	 * Gets the comments about this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's comments.
	 */
	public String getComments() {
		return comments;
	}

	/**
	 * Changes the comments about this OnlineInterviewQuizResult.
	 * 
	 * @param comments This OnlineInterviewQuizResult's new comments.
	 */
	public void setComments(String comments) {
		this.comments = comments;
	}

	/**
	 * Gets the type about this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Changes the type about this OnlineInterviewQuizResult.
	 * 
	 * @param comments This OnlineInterviewQuizResult's new type.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Gets the onlineInterview of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's onlineInterview.
	 * @see OnlineInterview
	 */
	public OnlineInterview getOnlineInterview() {
		return onlineInterview;
	}

	/**
	 * Changes the onlineInterview of this OnlineInterviewQuizResult.
	 * 
	 * @return onlineInterview this OnlineInterviewQuizResult's new onlineInterview.
	 * @see OnlineInterview
	 */
	public void setOnlineInterview(OnlineInterview onlineInterview) {
		this.onlineInterview = onlineInterview;
	}

	/**
	 * Gets the quiz of this OnlineInterviewQuizResult.
	 * 
	 * @return this OnlineInterviewQuizResult's quiz.
	 * @see Quiz
	 */
	public Quiz getQuiz() {
		return quiz;
	}

	/**
	 * Changes the quiz of this OnlineInterviewQuizResult.
	 * 
	 * @return quiz this OnlineInterviewQuizResult's new quiz.
	 * @see Quiz
	 */
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}

}