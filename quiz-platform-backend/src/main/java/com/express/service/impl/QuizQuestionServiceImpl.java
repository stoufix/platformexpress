package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.express.model.Question;
import com.express.model.QuizQuestion;
import com.express.model.QuizQuestionId;
import com.express.repository.QuizQuestionRepository;
import com.express.service.QuizQuestionService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of quizQuestion service
 * 
 Maha.BSaid
 * @version 1.0
 */

@Service
@Transactional
public class QuizQuestionServiceImpl implements QuizQuestionService {

	private final QuizQuestionRepository quizQuestionRepository;

	/**
	 * Constructor of QuizQuestionServiceImpl
	 * 
	 * @param quizQuestionRepository the repository of quiz question
	 */
	@Autowired
	public QuizQuestionServiceImpl(QuizQuestionRepository quizQuestionRepository) {
		this.quizQuestionRepository = quizQuestionRepository;
	}

	/**
	 * Gets the list of all quizQuestions
	 * 
	 * @return list of all quizQuestions
	 */
	@Override
	public List<QuizQuestion> findAll() {
		return quizQuestionRepository.findAll();
	}

	/**
	 * Gets one quizQuestion by his id
	 * 
	 * @param id the id of the quizQuestion
	 * @return quizQuestion object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 * 
	 */
	@Override
	public QuizQuestion findById(QuizQuestionId id) {
		Optional<QuizQuestion> quizQuestion = quizQuestionRepository.findById(id);
		if (quizQuestion.isPresent())
			return quizQuestion.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new quizQuestion
	 * 
	 * @param quizQuestion the aspect to create
	 * @return the created quizQuestion
	 */
	@Override
	public QuizQuestion create(QuizQuestion quizQuestion) {
		return quizQuestionRepository.save(quizQuestion);
	}

	/**
	 * Updates one quizQuestion
	 * 
	 * @param id           the id of the updated quizQuestion
	 * @param quizQuestion the new aspect object with the new values
	 * @return the updated quizQuestion
	 */
	@Override
	public QuizQuestion update(Long quizId, Long questionId, QuizQuestion quizQuestion) {
		QuizQuestionId id = new QuizQuestionId();
		id.setQuizId(quizId);
		id.setQuestionId(questionId);
		quizQuestion.setId(id);
		return quizQuestionRepository.save(quizQuestion);
	}

	/**
	 * Deletes one quizQuestion
	 * 
	 * @param id the of the deleted quizQuestion
	 */
	@Override
	public void delete(QuizQuestionId id) {
		QuizQuestion quizQuestion = findById(id);
		quizQuestionRepository.delete(quizQuestion);
	}

	/**
	 * Check the existence of question in quizzes
	 * 
	 * @param question the question to check
	 */
	@Override
	public Boolean existQuestionInQuizzes(Question question) {
		List<QuizQuestion> quizQuestions = quizQuestionRepository.findByQuestion(question);
		return quizQuestions.isEmpty();
	}

}
