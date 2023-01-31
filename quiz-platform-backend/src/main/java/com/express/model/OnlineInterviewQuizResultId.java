package com.express.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents id of OnlineInterviewQuizResult: composed by id of quiz and id of
 * onlineInterview
 * 

 * @version 1.0
 */
@Embeddable
public class OnlineInterviewQuizResultId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of the OnlineInterview.
	 * 
	 * @see OnlineInterview
	 */
	@Column(name = "online_interview_id")
	private Long onlineInterviewId;

	/**
	 * The ID of the Quiz.
	 * 
	 * @see Quiz
	 */
	@Column(name = "quiz_id")
	private Long quizId;

	/**
	 * Gets the ID of the Quiz.
	 * 
	 * @return the Quiz's ID.
	 * @see Quiz
	 */
	public Long getQuizId() {
		return quizId;
	}

	/**
	 * Changes the ID of the Quiz.
	 * 
	 * @param quizId The Quiz's new ID.
	 * @see Quiz
	 */
	public void setQuizId(Long quizId) {
		this.quizId = quizId;
	}

	/**
	 * Gets the ID of the OnlineInterview.
	 * 
	 * @return the OnlineInterview's ID.
	 * @see OnlineInterview
	 */
	public Long getOnlineInterviewId() {
		return onlineInterviewId;
	}

	/**
	 * Changes the ID of the OnlineInterview.
	 * 
	 * @param id The OnlineInterview's new ID.
	 * @see OnlineInterview
	 */
	public void setOnlineInterviewId(Long onlineInterviewId) {
		this.onlineInterviewId = onlineInterviewId;
	}

}
