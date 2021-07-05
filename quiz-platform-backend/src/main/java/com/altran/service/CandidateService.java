package com.altran.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.Candidate;
import com.altran.model.OnlineInterview;

/**
 * Represents the interface of candidate service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface CandidateService {

	List<Candidate> findAll();

	Page<Candidate> findAllByPage(Pageable pageable);

	List<AssignedQuizOnlineInterview> getAssignedQuizzes(Long id);

	Candidate findById(Long id);

	Page<Candidate> simpleSearch(String term, Pageable pageable);

	Candidate update(Candidate user, Long id);

	Candidate changeAccountState(Long id);

	Candidate createAccountCandidate(Candidate candidate);

	List<OnlineInterview> getOnLineInterviews(Long id);

}