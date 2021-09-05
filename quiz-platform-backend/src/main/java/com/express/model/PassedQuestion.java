package com.express.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

import static com.express.util.Constants.PASSEDQUESTION_FILTER;

/**
 * Represents passed questions of quiz by candidate
 * 

 * @version 1.0
 */
@Entity
@Table(name = "PASSED_QUESTIONS")
@JsonFilter(PASSEDQUESTION_FILTER)
public class PassedQuestion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of this PassedQuestion. This ID is generated automatically.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * The description of this PassedQuestion.
	 */
	@Column(columnDefinition = "text", length = 10485760)
	private String description;

	/**
	 * The quiz result who this PassedQuestion belongs to.
	 * 
	 * @see OnlineInterviewQuizResult
	 */
	@ManyToOne
	@JoinColumns(value = { @JoinColumn(name = "online_interview_quiz_result_quiz_id", referencedColumnName = "quiz_id"),
			@JoinColumn(name = "online_interview_quiz_result_onlineinterview_id", referencedColumnName = "online_interview_id") }, foreignKey = @ForeignKey(name = "FK_ONLINE_INTERVIEW_QUIZ_RESULT"))
	private OnlineInterviewQuizResult onlineinterviewQuizResult;

	/**
	 * The list of answered proposition of this PassedQuestion.
	 * 
	 * @see AnsweredProposition
	 */
	@OneToMany(fetch = FetchType.LAZY)
	private Set<AnsweredProposition> answeredProposition;

	/**
	 * Gets the ID of this PassedQuestion.
	 * 
	 * @return this PassedQuestion's ID.
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Changes the ID of this PassedQuestion.
	 * 
	 * @param id This PassedQuestion's new ID.
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the description of this PassedQuestion.
	 * 
	 * @return this PassedQuestion's description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Changes the description of this PassedQuestion.
	 * 
	 * @param description This PassedQuestion's new description.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Gets the quiz result who this PassedQuestion belong.
	 * 
	 * @return this PassedQuestion's quiz result.
	 * @see OnlineInterviewQuizResult
	 */
	public OnlineInterviewQuizResult getInterviewQuizResult() {
		return onlineinterviewQuizResult;
	}

	/**
	 * Changes the quiz result who this PassedQuestion belong.
	 * 
	 * @param onlineinterviewQuizResult This OnlineInterview's new belong quiz
	 *                                  result.
	 * @see OnlineInterviewQuizResult
	 */
	public void setInterviewQuizResult(OnlineInterviewQuizResult onlineinterviewQuizResult) {
		this.onlineinterviewQuizResult = onlineinterviewQuizResult;
	}

	/**
	 * Gets the list of proposition of this PassedQuestion.
	 * 
	 * @return this PassedQuestion's list of proposition.
	 * @see AnsweredProposition
	 */
	public Set<AnsweredProposition> getAnsweredProposition() {
		return answeredProposition;
	}

	/**
	 * Changes the list of proposition of this PassedQuestion.
	 * 
	 * @param answeredProposition This PassedQuestion's new list of proposition.
	 * @see AnsweredProposition
	 */
	public void setAnsweredProposition(Set<AnsweredProposition> answeredProposition) {
		this.answeredProposition = answeredProposition;
	}

}
