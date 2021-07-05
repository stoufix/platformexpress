package com.altran.service.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.altran.model.OnlineInterviewQuizResult;
import com.altran.model.OnlineInterviewQuizResultId;
import com.altran.model.PassedQuestion;
import com.altran.repository.OnlineInterviewQuizResultRepository;
import com.altran.service.OnlineInterviewQuizResultService;
import com.altran.service.PassedQuestionService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents serviceImpl of OnlineInterviewQuizResultServiceImpl
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Service
public class OnlineInterviewQuizResultServiceImpl implements OnlineInterviewQuizResultService {

	private final OnlineInterviewQuizResultRepository onlineInterviewQuizResultRepository;

	private PassedQuestionService passedQuestionService;

	/**
	 * Constructor of OnlineInterviewQuizResultServiceImpl
	 * 
	 * @param onlineInterviewQuizResultRepository the repository of online interview
	 *                                            quiz result
	 */
	@Autowired
	public OnlineInterviewQuizResultServiceImpl(
			OnlineInterviewQuizResultRepository onlineInterviewQuizResultRepository) {
		this.onlineInterviewQuizResultRepository = onlineInterviewQuizResultRepository;
	}

	/**
	 * Changes PassedQuestion service.
	 * 
	 * @param Passed question service.
	 */
	@Autowired
	public void setPassedQuestionService(PassedQuestionService passedQuestionService) {
		this.passedQuestionService = passedQuestionService;
	}

	/**
	 * Gets the list of all onlineInterviewQuizResult by page
	 * 
	 * @return list of all onlineInterviewQuizResult
	 */
	@Override
	public Page<OnlineInterviewQuizResult> findAllByPage(Pageable pageable) {
		return onlineInterviewQuizResultRepository.findAllByOrderByInterviewDateDesc(pageable);
	}

	/**
	 * Gets one onlineInterviewQuizResult by his id
	 * 
	 * @param onlineInterviewQuizResultId id of quiz and id of the online interview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public OnlineInterviewQuizResult findById(OnlineInterviewQuizResultId onlineInterviewQuizResultId) {
		Optional<OnlineInterviewQuizResult> onlineInterviewQuizResult = onlineInterviewQuizResultRepository
				.findById(onlineInterviewQuizResultId);
		if (onlineInterviewQuizResult.isPresent())
			return onlineInterviewQuizResult.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}

	}

	/**
	 * Update onlineInterviewQuizResult
	 * 
	 * @param onlineInterviewQuizResultId object
	 * @param id                          id of the OnlineInterviewQuizResultId
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * @throws EntityExistsException   if there is already existing entity with such
	 *                                 ID
	 * @throws NoSuchElementException  if no element is present with such ID
	 */
	@Override
	public OnlineInterviewQuizResult update(OnlineInterviewQuizResultId id,
			OnlineInterviewQuizResult onlineInterviewQuizResult) {
		onlineInterviewQuizResult.setId(id);
		return onlineInterviewQuizResultRepository.save(onlineInterviewQuizResult);
	}

	/**
	 * Delete quiz result by id
	 * 
	 * @param onlineInterviewQuizResultId id of the quiz and id of the online
	 *                                    interview
	 */
	@Override
	@Transactional
	public void delete(OnlineInterviewQuizResultId onlineInterviewQuizResultId) {
		OnlineInterviewQuizResult quizResult = findById(onlineInterviewQuizResultId);
		Set<PassedQuestion> passedQuestions = new HashSet<>();
		passedQuestions.addAll(quizResult.getPassedQuestions());
		for (PassedQuestion passedQuestion : passedQuestions) {

			passedQuestionService.delete(passedQuestion.getId());
		}
		onlineInterviewQuizResultRepository.delete(quizResult);

	}

}
