package com.express.service;

import java.util.List;

import com.express.model.Question;
import com.express.model.QuizQuestion;
import com.express.model.QuizQuestionId;

/**
 * Represents the interface of quizQuestion service
 * 
 Maha.BSaid
 * @version 1.0
 */
public interface QuizQuestionService {

	List<QuizQuestion> findAll();

	QuizQuestion findById(QuizQuestionId quizQuestionId);

	QuizQuestion create(QuizQuestion quizQuestion);

	QuizQuestion update(Long quizId, Long questionId, QuizQuestion quizQuestion);

	void delete(QuizQuestionId quizQuestionId);

	Boolean existQuestionInQuizzes(Question question);
	

}
