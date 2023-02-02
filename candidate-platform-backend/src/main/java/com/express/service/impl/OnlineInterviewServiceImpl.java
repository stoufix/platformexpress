package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;
import com.express.repository.OnlineInterviewRepository;
import com.express.service.OnlineInterviewService;

@Service
public class OnlineInterviewServiceImpl implements OnlineInterviewService {

	@Autowired
	private OnlineInterviewRepository onlineInterviewRepository;

	/**
	 * Gets the list of all OnlineInterviews
	 * 
	 * @return list of all onlineInterviews
	 */
	@Override
	public List<OnlineInterview> findAll() {
		return onlineInterviewRepository.findAll();
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
		if (!onlineInterview.isPresent()) {
			throw new NoSuchElementException();
		}
		return onlineInterview.get();

	}

	/**
	 * Updates one onlineInterview
	 * 
	 * @param id              the id of the onlineInterview
	 * @param onlineInterview the new onlineInterview object with the new values
	 * @return the updated onlineInterview
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public OnlineInterview update(Long id, OnlineInterview onlineInterview) {
		if (id != null && !onlineInterviewRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		onlineInterview.setId(id);
		return onlineInterviewRepository.save(onlineInterview);
	}

	/**
	 * Gets the list of passed quizzes for one online interview
	 * 
	 * @param id the id of the online interview
	 */
	@Override
	public List<OnlineInterviewQuizResult> getPassedQuizzes(Long id) {
		return findById(id).getPassedQuizzes();
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
		if (id != null && !onlineInterviewRepository.existsById(id)) {
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		return findById(id).getAssignedQuizzes();
	}

}
