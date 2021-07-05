package com.altran.controller;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.altran.model.AssignedQuizOnlineInterviewId;
import com.altran.model.OnlineInterview;
import com.altran.service.AssignedQuizOnlineInterviewService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.altran.util.Constants.*;

/**
 * Represents Rest controller of AssignedQuizOnlineInterview witch permit to
 * assign quiz to an online interview of candidate
 * 
 * @author Maha.BSaid
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/assignquizonlineinterview")
public class AssignedQuizOnlineInterviewController {

	private final AssignedQuizOnlineInterviewService assignedQuizOnlineInterviewService;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of AssignedQuizOnlineInterviewController
	 * 
	 * @param assignedQuizOnlineInterviewService the service of assign online
	 *                                           interview
	 */
	@Autowired
	public AssignedQuizOnlineInterviewController(
			AssignedQuizOnlineInterviewService assignedQuizOnlineInterviewService) {
		this.assignedQuizOnlineInterviewService = assignedQuizOnlineInterviewService;
	}

	/**
	 * Changes message response object for sending message
	 * 
	 * @param messageResponse generic response with string as object.
	 */
	@Autowired
	public void setMessageResponse(GenericResponse<String> messageResponse) {
		this.messageResponse = messageResponse;
	}

	/**
	 * Gets the list of onlineInterviews with assigned quiz
	 * 
	 * @param pageable
	 * @return list of onlineInterview with list of assigned quizzes by page
	 */
	@GetMapping
	public MappingJacksonValue getCandidatesWithAssignedQuizzes(Pageable pageable) {
		Page<OnlineInterview> interviewList = assignedQuizOnlineInterviewService
				.findOnLineInterviewsWithAssignedQuizzes(pageable);
		/** Filtering data to send **/
		// Filter the interview object
		SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT, STATUS,
				COMMENTS, ASSIGNEDQUIZZES, APPLICATION);
		// Filter the assigned quiz interview object
		SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
				ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the application object
		SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				PHONENUMBER, ADDRESS, EMAIL, PROFIL);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ONLINEINTERVIEW_FILTER, interviewFilter)
				.addFilter(ASSIGNEDQUIZONLINEINTERVIEW_FILTER, assignedQuizInterviewFilter)
				.addFilter(QUIZ_FILTER, quizFilter).addFilter(APPLICATION_FILTER, applicationFilter)
				.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue interviewMapping = new MappingJacksonValue(interviewList);
		interviewMapping.setFilters(filters);
		return interviewMapping;
	}

	/**
	 * Assign list of quizzes to an online interviews
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID,
	 * EntityExistsException if there is already existing entity with such ID and
	 * any other exception
	 * 
	 * @param onlineInterviews Online interviews objects with the assigned quizzes
	 *                         list
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> assignQuizzes(@RequestBody List<OnlineInterview> onlineInterviews) {
		try {
			assignedQuizOnlineInterviewService.assignQuizzes(onlineInterviews);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ASSIGN_SUCCESS));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_FAIL));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_FAIL));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Update list of assigned quizzes of an online interview
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID,
	 * EntityExistsException if there is already existing entity with such ID and
	 * any other exception
	 * 
	 * @param id              id of the on line interview
	 * @param onlineInterview Online interview object with the assigned quizzes list
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateAssignQuizzes(@PathVariable(value = "id") Long id,
			@RequestBody OnlineInterview onlineInterview) {
		try {
			assignedQuizOnlineInterviewService.updateAssignedQuizzes(id, onlineInterview);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ASSIGN_UPDATED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_FAIL));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_FAIL));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Delete one assigned quiz from an online interview
	 * 
	 * @param assignQuizOnlineInterviewId id of the assigned quiz and id of the
	 *                                    online interview
	 */
	@DeleteMapping
	public ResponseEntity<GenericResponse<String>> deletAssignedQuiz(@RequestBody AssignedQuizOnlineInterviewId id) {
		try {
			assignedQuizOnlineInterviewService.deleteAssignedQuiz(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ASSIGN_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Delete all assigned quizzes form an online interview
	 * 
	 * @param onlineInterviewId id of the interview
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteAssignedQuizzes(@PathVariable(value = "id") Long id) {
		try {
			assignedQuizOnlineInterviewService.deleteAssignedQuizzes(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(ALL_ASSIGN_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ASSIGN_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for online interviews
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of online interviews contains the input term by page
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchAssignedQuizOnlineInterview(@PathVariable(value = "term") String term,
			Pageable pageable) {
		Page<OnlineInterview> interviewList = assignedQuizOnlineInterviewService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the interview object
		SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT, STATUS,
				COMMENTS, ASSIGNEDQUIZZES, APPLICATION);
		// Filter the assigned quiz interview object
		SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
				ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the application object
		SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				PHONENUMBER, ADDRESS, EMAIL, PROFIL);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(ONLINEINTERVIEW_FILTER, interviewFilter)
				.addFilter(ASSIGNEDQUIZONLINEINTERVIEW_FILTER, assignedQuizInterviewFilter)
				.addFilter(QUIZ_FILTER, quizFilter).addFilter(APPLICATION_FILTER, applicationFilter)
				.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue interviewMapping = new MappingJacksonValue(interviewList);
		interviewMapping.setFilters(filters);
		return interviewMapping;
	}

}
