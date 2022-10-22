package com.express.service;

import java.util.List;

import com.express.model.Candidate;
import com.express.model.OnlineInterview;
import com.express.model.Quiz;

/**
 * Represents interface of candidate service
 * 
 * @author Dhouha.Bejaoui
 * @author Hasna.Fattouch
 * @version 1.0
 *
 */
public interface CandidateService {

	Quiz getQuizToStart(Long id);

	Candidate getCandidateByUsername(String username);

	List<Candidate> getAll();

	Candidate findById(Long id);

	List<OnlineInterview> getOnLineInterviews(Long id);

}