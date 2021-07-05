package com.altran.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.altran.model.AnsweredProposition;
import com.altran.model.OnlineInterviewQuizResult;
import com.altran.model.PassedQuestion;
import com.altran.repository.PassedQuestionRepository;
import com.altran.service.AnsweredPropositionService;
import com.altran.service.OnlineInterviewQuizResultService;
import com.altran.service.PassedQuestionService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of passedQuestion service
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Service
public class PassedQuestionServiceImpl implements PassedQuestionService {

	private final PassedQuestionRepository passedQuestionRepository;
	private AnsweredPropositionService answeredPropositionService;
	private OnlineInterviewQuizResultService onlineInterviewQuizResultService;

	/**
	 * Constructor of PassedQuestionServiceImpl
	 * 
	 * @param passedQuestionRepository the repository of passed question
	 */
	@Autowired
	public PassedQuestionServiceImpl(PassedQuestionRepository passedQuestionRepository) {
		this.passedQuestionRepository = passedQuestionRepository;
	}

	/**
	 * Changes Answered Proposition service.
	 * 
	 * @param Answered Proposition service.
	 */
	@Autowired
	public void setAnsweredPropositionService(AnsweredPropositionService answeredPropositionService) {
		this.answeredPropositionService = answeredPropositionService;
	}

	/**
	 * Changes onlineInterviewQuizResulte service.
	 * 
	 * @param onlineInterviewQuizResult service.
	 */
	@Autowired
	public void setOnlineInterviewQuizResultService(OnlineInterviewQuizResultService onlineInterviewQuizResultService) {
		this.onlineInterviewQuizResultService = onlineInterviewQuizResultService;
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

	/**
	 * Gets one passed question by his id
	 * 
	 * @param id the id of the passed question
	 * @return passed question object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public PassedQuestion findById(Long id) {
		Optional<PassedQuestion> passedQuestion = passedQuestionRepository.findById(id);
		if (passedQuestion.isPresent()) {
			return passedQuestion.get();
		} else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Deletes one passed question
	 * 
	 * @param id the id of the deleted passed question
	 * @throws EntityNotFoundException if there is no passed question with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !passedQuestionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		PassedQuestion passedQuestion = findById(id);
		// delete saved answeredPropositions from passedQuestion answeredProposition
		// list
		Set<AnsweredProposition> answeredpropositions = new HashSet<>();
		answeredpropositions.addAll(passedQuestion.getAnsweredProposition());
		for (AnsweredProposition answeredProposition : passedQuestion.getAnsweredProposition()) {
			answeredpropositions.remove(answeredProposition);
			passedQuestion.setAnsweredProposition(answeredpropositions);
			passedQuestionRepository.save(passedQuestion);
			answeredPropositionService.delete(answeredProposition.getId());
		}
		// delete passedQuestion from quiz result passedQuestion list
		OnlineInterviewQuizResult quizResult = passedQuestion.getInterviewQuizResult();
		Set<PassedQuestion> passedQuestions = quizResult.getPassedQuestions();
		passedQuestions.remove(passedQuestion);
		passedQuestion.setInterviewQuizResult(null);
		passedQuestionRepository.save(passedQuestion);
		quizResult.setPassedQuestions(passedQuestions);
		onlineInterviewQuizResultService.update(quizResult.getId(), quizResult);
		// delete passedQuestion
		passedQuestionRepository.delete(passedQuestion);

	}

}
