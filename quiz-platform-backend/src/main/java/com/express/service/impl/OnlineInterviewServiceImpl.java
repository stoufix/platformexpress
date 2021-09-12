package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;
import com.express.repository.OnlineInterviewRepository;
import com.express.service.OnlineInterviewService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of onlineInterview service
 * 

 * @version 1.0
 */
@Service
public class OnlineInterviewServiceImpl implements OnlineInterviewService {

	private final OnlineInterviewRepository onlineInterviewRepository;

	/**
	 * Constructor of OnlineInterviewServiceImpl
	 * 
	 * @param onlineInterviewRepository the repository of online interview
	 */
	@Autowired
	public OnlineInterviewServiceImpl(OnlineInterviewRepository onlineInterviewRepository) {
		this.onlineInterviewRepository = onlineInterviewRepository;
	}

	/**
	 * Gets the list of all OnlineInterviews
	 * 
	 * @return list of all onlineInterviews
	 */
	@Override
	public List<OnlineInterview> findAll() {
		return onlineInterviewRepository.findAllByOrderByCreatedAtDesc();
	}

	/**
	 * Gets the list of all OnlineInterviews by page
	 * 
	 * @return list of all onlineInterviews by page
	 */
	@Override
	public Page<OnlineInterview> findAllByPage(Pageable pageable) {
		return onlineInterviewRepository.findAllByOrderByCreatedAtDesc(pageable);
	}

	/**
	 * Gets one Online Interview by his id
	 * 
	 * @param id the id of the Online Interview
	 * @return OnlineInterview object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public OnlineInterview findById(Long id) {
		Optional<OnlineInterview> onlineInterview = onlineInterviewRepository.findById(id);
		if (onlineInterview.isPresent())
			return onlineInterview.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Updates one onlineInterview
	 * 
	 * @param id              the id of the onlineInterview
	 * @param onlineInterview the new onlineInterview object with the new values
	 * @return the updated onlineInterview
	 * @throws EntityNotFoundException if no entity is present with such ID
	 */
	@Override
	public OnlineInterview update(Long id, OnlineInterview onlineInterview) {
		if (id != null && !onlineInterviewRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		onlineInterview.setId(id);
		return onlineInterviewRepository.save(onlineInterview);
	}

	/**
	 * Gets the list of assigned Quizzes for one online interview
	 * 
	 * @param id the id of the online interview
	 * @return the list of assigned Quizzes
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public List<AssignedQuizOnlineInterview> getAssignedQuizzes(Long id) {
		return findById(id).getAssignedQuizzes();
	}

	/**
	 * Gets the list of passed quizzes for one online interview
	 * 
	 * @param id the id of the online interview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public List<OnlineInterviewQuizResult> getPassedQuizzes(Long id) {
		return findById(id).getPassedQuizzes();
	}

	/**
	 * Searches for onlineInterview by one term
	 * 
	 * @param term the term to base search on it
	 * @return list of questions contains the input term by page
	 */
	@Override
	public Page<OnlineInterview> simpleSearch(String term, Pageable pageable) {
		return onlineInterviewRepository.simpleSearch(term, pageable);
	}

	/**
	 * Check the existence of online interview
	 * 
	 * @param id the id of onlineInterview
	 * @return true if exist , false if not
	 */
	@Override
	public Boolean existsById(Long id) {
		return onlineInterviewRepository.existsById(id);
	}

}
