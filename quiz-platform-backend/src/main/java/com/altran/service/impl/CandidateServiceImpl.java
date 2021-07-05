package com.altran.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.altran.model.Application;
import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.Candidate;
import com.altran.model.OnlineInterview;
import com.altran.repository.CandidateRepository;
import com.altran.service.CandidateService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of candidate service
 * @version 1.0
 */
@Service
public class CandidateServiceImpl implements CandidateService {

	private final CandidateRepository candidateRepository;

	private BCryptPasswordEncoder bcryptEncoder;

	/**
	 * Constructor of AssignedQuizOnlineInterviewServiceImpl
	 * 
	 * @param candidateRepository the repository of Candidate
	 */
	@Autowired
	public CandidateServiceImpl(CandidateRepository candidateRepository, BCryptPasswordEncoder bcryptEncoder) {
		this.candidateRepository = candidateRepository;
		this.bcryptEncoder = bcryptEncoder;
	}

	/**
	 * Changes bcryptEncoder service.
	 * 
	 * @param bcryptEncoder bcryptEncoder service.
	 */
	@Autowired
	public void setBcryptEncoder(BCryptPasswordEncoder bcryptEncoder) {
		this.bcryptEncoder = bcryptEncoder;
	}

	/**
	 * Gets the list of all candidates
	 * 
	 * @return list of all candidates
	 */
	public List<Candidate> findAll() {
		return candidateRepository.findAll();
	}

	/**
	 * Gets the list of all candidates by page and sorted by firstName
	 * 
	 * @param pageable pagination information
	 * @return list of all candidates by page
	 */
	public Page<Candidate> findAllByPage(Pageable pageable) {
		return candidateRepository.findAllByOrderByFirstNameAsc(pageable);
	}

	/**
	 * Gets one candidate by his id
	 * 
	 * @param id the id of the candidate
	 * @return candidate object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Candidate findById(Long id) {
		Optional<Candidate> candidate = candidateRepository.findById(id);
		if (candidate.isPresent())
			return candidate.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Updates one candidate
	 * 
	 * @param id        the id of the candidate to update
	 * @param candidate the new candidate object with the new values
	 * @return the updated candidate
	 * @throws EntityNotFoundException if there is already existing entity with such
	 *                                 ID
	 */
	@Override
	public Candidate update(Candidate candidate, Long id) {
		if (id == null && !candidateRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		candidate.setId(id);
		return candidateRepository.save(candidate);
	}

	/**
	 * Searches for candidates by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of candidates contains the input term
	 */
	@Override
	public Page<Candidate> simpleSearch(String term, Pageable pageable) {
		return candidateRepository.simpleSearch(term, pageable);
	}

	/**
	 * Changes the state of candidate account
	 * 
	 * @param id the of the updated candidate
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Candidate changeAccountState(Long id) {
		Candidate candidate = findById(id);
		if (candidate.isActivated())
			candidate.setActivated(false);
		else
			candidate.setActivated(true);
		return candidateRepository.save(candidate);
	}

	/**
	 * Creates userName and password of candidate
	 * 
	 * @param candidate candidate to create userName and password
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Candidate createAccountCandidate(Candidate candidate) {
		// set userName
		candidate.setUsername(candidate.getFirstName() + "_" + candidate.getLastName());
		// set password
		candidate.setPassword(bcryptEncoder.encode(candidate.getFirstName()));
		// set activated to true when the user created
		candidate.setActivated(true);
		return update(candidate, candidate.getId());
	}

	/**
	 * Gets the list of assigned quizzes of one candidate
	 * 
	 * @param id The id of the candidate
	 * @return candidate's list of assigned quizzes
	 * @throws NoSuchElementException if no element is present with such ID
	 * 
	 */
	@Override
	public List<AssignedQuizOnlineInterview> getAssignedQuizzes(Long id) {
		// Result table
		List<AssignedQuizOnlineInterview> assignedQuizzes = new ArrayList<>();
		// Get candidate
		Candidate candidate = findById(id);
		// Get list of application of the candidate
		List<Application> applications = candidate.getApplications();
		// Get Assigned quizzes of all applications
		for (Application application : applications) {
			assignedQuizzes.addAll(application.getOnlineInterview().getAssignedQuizzes());
		}
		return assignedQuizzes;
	}

	/**
	 * Gets the list of assigned quizzes of one candidate
	 * 
	 * @param id The id of the candidate
	 * @return candidate's list of assigned quizzes
	 */
	@Override
	public List<OnlineInterview> getOnLineInterviews(Long id) {
		// Result table
		List<OnlineInterview> onlineInterviews = new ArrayList<>();
		// Get candidate
		Candidate candidate = findById(id);
		// Get list of application of the candidate
		List<Application> applications = candidate.getApplications();
		// Get Assigned quizzes of all applications
		for (Application application : applications) {
			onlineInterviews.add(application.getOnlineInterview());
		}
		return onlineInterviews;
	}
}
