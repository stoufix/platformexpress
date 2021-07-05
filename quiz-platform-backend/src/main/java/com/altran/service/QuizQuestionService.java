package com.altran.service;

import java.util.List;

import com.altran.model.Question;
import com.altran.model.QuizQuestion;
import com.altran.model.QuizQuestionId;

/**
 * Represents the interface of quizQuestion service
 * 
 * @author Maha.BSaid
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
