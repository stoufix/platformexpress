package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.express.model.Quiz;
import com.express.model.filter.QuizFilter;

/**
 * Represents the interface of quiz service
 * 

 * @version 1.0
 */
public interface QuizService {

	List<Quiz> findAll();

	Page<Quiz> findAllByPage(Pageable pageable);

	Page<Quiz> findVisibleByPage(String username, Pageable pageable);

	Page<Quiz> findVisibleByAuthActivity(String username, Pageable pageable);

	Quiz findById(Long id);

	Quiz create(Quiz quiz);

	Quiz update(Long id, Quiz quiz);

	Page<Quiz> simpleSearchWithoutActivity(String username, String term, Pageable pageable);

	Page<Quiz> simpleSearchWithActivity(String username, String term, Pageable pageable);

	Page<Quiz> simpleSearchByUser(String username, String term, Pageable pageable);

	Page<Quiz> advancedSearch(QuizFilter quiz, Pageable pageable);

	void delete(Long id);

	Quiz changeShared(Long id);

	Page<Quiz> advancedSearchByUser(QuizFilter quiz, String username, Pageable pageable);

	Page<Quiz> advancedSearchWithActivity(QuizFilter quizFilter, String username, Pageable pageable);
}