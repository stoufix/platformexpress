package com.altran.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.model.Question;
import com.altran.model.filter.QuestionFilter;

/**
 * Represents the interface of question service
 * 
 * @author Amal.Smaoui
 * @version 1.0
 */
public interface QuestionService {

	List<Question> findAll();

	Page<Question> findAllVisibleQuestions(String username, Pageable pageable);

	Page<Question> findAllVisibleQuestionsByAuthActivity(String username, Pageable pageable);

	Page<Question> findAllByPage(Pageable pageable);

	Question findById(Long id);

	List<Question> findByTechnologyAndDegree(Long technologyId, Long degreeId);

	Page<Question> findByPageByTechnologyAndDegree(Long technologyId, Long degreeId, Pageable pageable);

	Page<Question> simpleSearchWithoutActivity(String username, String term, Pageable pageable);

	Page<Question> simpleSearchWithActivity(String username, String term, Pageable pageable);

	Page<Question> simpleSearchByUser(String username, String term, Pageable pageable);

	Page<Question> advancedSearch(QuestionFilter question, Pageable pageable);
	
	Page<Question> advancedSearchWithActivity(QuestionFilter question, String username, Pageable pageable);

	Page<Question> advancedSearchByUser(QuestionFilter question, String username, Pageable pageable);

	Question create(Question question);

	Question update(Long id, Question question);

	Boolean delete(Long id);

	Question simpleUpdate(Long id, Question question);

	Question changeShared(Long id);

	Page<Question> findAllVisibleQuestionsByAuthActivityTechnologyAndDegree(Long technologyId, Long degreeId,
			String username, Pageable pageable);

	Page<Question> findAllVisibleQuestionsByTechnologyAndDegree(Long technologyId, Long degreeId, String username,
			Pageable pageable);

	Page<Question> simpleSearchWithoutActivityWithTechnologyAndDegree(String username, String term, Long technology,
			Long degree, Pageable pageable);

	Page<Question> simpleSearchWithActivityTechnologyAndDegree(String username, String term, Long technology,
			Long degree, Pageable pageable);

}