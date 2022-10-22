package com.express.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.express.model.Candidate;
import com.express.model.OnlineInterview;
import com.express.model.Quiz;
import com.express.service.CandidateService;
import com.express.util.Translator;

import static com.express.util.Constants.ERROR;
import static com.express.util.Constants.OBJECT;
import static com.express.util.Constants.MESSAGE;
import static com.express.util.Constants.CANDIDATE_NOT_EXIST;
import static com.express.util.Constants.CANDIDATE_FIND_ERROR;
import static com.express.util.Constants.QUIZ_NOT_EXIST;
import static com.express.util.Constants.QUIZ_FIND_ERROR;

/**
 * Represents Rest controller of candidate
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/candidates")
public class CandidateController {

	private final CandidateService candidateService;

	/**
	 * Constructor of CandidateController
	 * 
	 * @param candidateService the service of candidate
	 */
	@Autowired
	public CandidateController(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	@GetMapping
	public List<Candidate> getAllCandidates() {
		return candidateService.getAll();
	}

	/**
	 * Gets the list of assigned quizzes of one candidate
	 * 
	 * @param id id of candidate
	 * @return list of all assigned quizzes
	 */
	@GetMapping(value = "onlineInterviews/{id}")
	public List<OnlineInterview> getOnlineInterviews(@PathVariable(value = "id") Long id) {
		return candidateService.getOnLineInterviews(id);
	}

	/**
	 * Gets one quiz for candidate to start passing
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the assignedQuiz
	 * @return ResponseEntity: the object or the error to display when getting quiz
	 *         to start by id of the assignedQuiz with HttpStatus status code
	 */
	@GetMapping(value = "/start-quiz/{id}")
	public ResponseEntity<Map<String, Object>> getQuizToStart(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		try {
			Quiz quiz = candidateService.getQuizToStart(id);
			response.put(ERROR, false);
			response.put(OBJECT, quiz);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (NoSuchElementException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(QUIZ_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	/**
	 * Gets one candidate by his userName
	 * 
	 * Handles UsernameNotFoundException if no element is present with such userName
	 * and any other exception
	 * 
	 * @param userName the userName of the candidate
	 * @return ResponseEntity: the object or the error to display when getting
	 *         candidate by userName with HttpStatus status code
	 */
	@GetMapping(value = "/username/{userName}")
	public ResponseEntity<Map<String, Object>> getByUserName(@PathVariable(value = "userName") String username) {
		Map<String, Object> response = new HashMap<>();
		try {
			response.put(ERROR, false);
			response.put(OBJECT, candidateService.getCandidateByUsername(username));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (UsernameNotFoundException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(CANDIDATE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(CANDIDATE_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		}
	}

}
