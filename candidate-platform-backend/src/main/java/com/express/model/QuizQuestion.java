package com.express.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

/**
 * Represents many-to-many association between quiz and question: assign
 * question to quiz
 * 
 * @author Maha.BSaid
 * @version 1.0
 */

@Entity
@Table(name = "QUIZ_QUESTION")
public class QuizQuestion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * The embedded ID of this QuizQuestion. This ID composed by the id of quiz and
	 * the id of question
	 * 
	 * @see QuizQuestionId
	 */
	@EmbeddedId
	private QuizQuestionId id = new QuizQuestionId();

	/**
	 * The quiz of this QuizQuestion.
	 * 
	 * @see Quiz
	 */
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("quizId")
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;

	/**
	 * The question of this QuizQuestion.
	 * 
	 * @see Question
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("questionId")
	@JoinColumn(name = "question_id")
	private Question question;

	/**
	 * The mark of the question in the quiz
	 */
	@Column
	@NotNull
	private Float mark;

	/**
	 * Gets the ID of this QuizQuestion.
	 * 
	 * @return this QuizQuestion's ID.
	 * @see QuizQuestionId
	 */
	public QuizQuestionId getId() {
		return id;
	}

	/**
	 * Changes the ID of this QuizQuestion.
	 * 
	 * @param id This QuizQuestion's new ID.
	 * @see QuizQuestionId
	 */
	public void setId(QuizQuestionId id) {
		this.id = id;
	}

	/**
	 * Gets the quiz of this QuizQuestion.
	 * 
	 * @return this QuizQuestion's quiz.
	 * @see Quiz
	 */
	public Quiz getquiz() {
		return quiz;
	}

	/**
	 * Changes the quiz of this QuizQuestion.
	 * 
	 * @return quiz this QuizQuestion's new quiz.
	 * @see Quiz
	 */
	public void setquiz(Quiz quiz) {
		this.quiz = quiz;
	}

	/**
	 * Gets the question of this QuizQuestion.
	 * 
	 * @return this QuizQuestion's question.
	 * @see Question
	 */
	public Question getQuestion() {
		return question;
	}

	/**
	 * Changes the question of this QuizQuestion.
	 * 
	 * @return question this QuizQuestion's new question.
	 * @see Question
	 */
	public void setQuestion(Question question) {
		this.question = question;
	}

	/**
	 * Gets the mark of the question in the quiz.
	 * 
	 * @return this QuizQuestion's mark.
	 */
	public Float getMark() {
		return mark;
	}

	/**
	 * Changes the mark of the question in the quiz.
	 * 
	 * @return mark this QuizQuestion's new mark.
	 */
	public void setMark(Float mark) {
		this.mark = mark;
	}

}
