package com.express.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.express.model.Application;
import com.express.model.Candidate;
import com.express.model.OnlineInterview;
import com.express.model.Quiz;
import com.express.repository.CandidateRepository;
import com.express.service.CandidateService;
import com.express.service.QuizService;

/**
 * Represents implementation of candidate service
 * @version 1.0
 *
 */
@Service(value = "candidateService")
public class CandidateServiceImpl implements UserDetailsService, CandidateService {

	@Autowired
	private CandidateRepository candidateRepository;

	@Autowired
	private QuizService quizService;

	/**
	 * Gets userDetails object by userName (needed by Spring security)
	 * 
	 * @param username: user name of the user
	 * @return userDetails object with the input userName
	 */
	public UserDetails loadUserByUsername(String username) {
		Candidate candidate = candidateRepository.findByUsername(username);
		Collection<GrantedAuthority> authorities = new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority("CANDIDATE"));
		if (candidate == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(candidate.getUsername(), candidate.getPassword(),
				authorities);
	}

	/**
	 * Gets the list of all candidates
	 * 
	 * @return list of candidates
	 */
	@Override
	public List<Candidate> getAll() {
		return candidateRepository.findAll();
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
			throw new NoSuchElementException("\"There is no entity with such ID in the database.\"");
		}
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

	/**
	 * Gets one quiz for candidate to start passing
	 * 
	 * @param id the id of the assignedQuiz
	 * @return the selected quiz to start passing
	 */
	@Override
	public Quiz getQuizToStart(Long id) {
		return quizService.findById(id);
	}

	/**
	 * Gets one candidate by his userName
	 * 
	 * @param userName the userName of the candidate
	 * @return candidate object with the same userName
	 * @throws NoSuchElementException if no element is present with such userName
	 */
	@Override
	public Candidate getCandidateByUsername(String username) {
		Candidate candidate = candidateRepository.findByUsername(username);
		if (candidate == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return candidate;
	}
}
