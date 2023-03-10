package com.express.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.express.model.PassedQuestion;
import com.express.repository.PassedQuestionRepository;
import com.express.service.PassedQuestionService;

@Service
public class PassedQuestionServiceImpl implements PassedQuestionService {

	private final PassedQuestionRepository passedQuestionRepository;

	/**
	 * Constructor of QuestionServPassedQuestionServiceImpliceImpl
	 * 
	 * @param passedQuestionRepository the repository of passed question
	 */
	@Autowired
	public PassedQuestionServiceImpl(PassedQuestionRepository passedQuestionRepository) {
		this.passedQuestionRepository = passedQuestionRepository;
	}

	/**
	 * Creates a new passed question
	 * 
	 * @param passedQuestion the passed question to create
	 * @return the created passed question
	 */
	@Override
	public PassedQuestion create(PassedQuestion passedQuestion) {
		return passedQuestionRepository.save(passedQuestion);
	}

	/**
	 * Updates one passed question
	 * 
	 * @param id             the id of the passed question
	 * @param passedQuestion the new passed question object with the new values
	 * @return the updated passed question
	 */
	@Override
	public PassedQuestion update(Long id, PassedQuestion passedQuestion) {
		passedQuestion.setId(id);
		return passedQuestionRepository.save(passedQuestion);
	}

}
