package com.express.service;

import com.express.model.PassedQuestion;

/**
 * Represents service of PassedQuestionService
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
public interface PassedQuestionService {

	PassedQuestion create(PassedQuestion passedQuestion);

	PassedQuestion update(Long id, PassedQuestion passedQuestion);

}
