package com.altran.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Represents id of QuizQuestion: composed by id of quiz and id of question
 * @version 1.0
 */
@Embeddable
public class QuizQuestionId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The ID of the Quiz.
	 * 
	 * @see Quiz
	 */
	@Column(name = "quiz_id")
	private Long quizId;

	/**
	 * The ID of the Question.
	 * 
	 * @see Question
	 */
	@Column(name = "question_id")
	private Long questionId;

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
	 * Gets the ID of the Question.
	 * 
	 * @return the Question's ID.
	 * @see Question
	 */
	public Long getQuestionId() {
		return questionId;
	}

	/**
	 * Changes the ID of the Question.
	 * 
	 * @param questionId The Question's new ID.
	 * @see Question
	 */
	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

}
