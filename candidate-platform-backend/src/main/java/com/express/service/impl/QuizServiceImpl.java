package com.express.service.impl;

import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.express.model.Quiz;
import com.express.repository.QuizRepository;
import com.express.service.QuizService;

/**
 * Represents implementation of quiz service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Service
public class QuizServiceImpl implements QuizService {

	private final QuizRepository quizRepository;

	/**
	 * Constructor of QuizServiceImpl
	 * 
	 * @param quizRepository the repository of quiz
	 */
	@Autowired
	public QuizServiceImpl(QuizRepository quizRepository) {
		this.quizRepository = quizRepository;
	}

	/**
	 * Gets one quiz by his id
	 * 
	 * @param id the id of the quiz
	 * @return question object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Quiz findById(Long id) {
		Optional<Quiz> quiz = quizRepository.findById(id);
		if (!quiz.isPresent()) {
			throw new NoSuchElementException("\"There is no entity with such ID in the database.\"");
		}
		return quiz.get();
	}

}