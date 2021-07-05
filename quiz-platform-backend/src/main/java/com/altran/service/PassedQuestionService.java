package com.altran.service;

import com.altran.model.PassedQuestion;

/**
 * Represents service of PassedQuestion
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
public interface PassedQuestionService {

	PassedQuestion create(PassedQuestion passedQuestion);

	void delete(Long id);

	PassedQuestion update(Long id, PassedQuestion passedQuestion);

	PassedQuestion findById(Long id);

}
