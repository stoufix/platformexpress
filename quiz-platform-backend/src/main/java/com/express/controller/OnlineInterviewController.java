package com.express.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;
import com.express.service.OnlineInterviewService;
import com.express.util.GenericResponse;
import com.express.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import static com.express.util.Constants.APPLICATION;
import static com.express.util.Constants.APPLICATION_FILTER;
import static com.express.util.Constants.ASSIGNEDAT;
import static com.express.util.Constants.ASSIGNEDBY;
import static com.express.util.Constants.ASSIGNEDQUIZONLINEINTERVIEW_FILTER;
import static com.express.util.Constants.ASSIGNEDQUIZZES;
import static com.express.util.Constants.CANDIDATE;
import static com.express.util.Constants.CANDIDATE_FILTER;
import static com.express.util.Constants.COMMENTS;
import static com.express.util.Constants.CREATEDAT;
import static com.express.util.Constants.DURATION;
import static com.express.util.Constants.EMAIL;
import static com.express.util.Constants.FIRSTNAME;
import static com.express.util.Constants.ID;
import static com.express.util.Constants.INTERVIEWDATE;
import static com.express.util.Constants.LASTNAME;
import static com.express.util.Constants.NOTE;
import static com.express.util.Constants.ONLINEINTERVIEW;
import static com.express.util.Constants.ONLINEINTERVIEW_FILTER;
import static com.express.util.Constants.ONLINEINTERVIEW_FIND_ERROR;
import static com.express.util.Constants.ONLINEINTERVIEW_NOT_EXIST;
import static com.express.util.Constants.PASSEDQUIZZES;
import static com.express.util.Constants.PASSINGAT;
import static com.express.util.Constants.PROFIL;
import static com.express.util.Constants.QUIZ;
import static com.express.util.Constants.QUIZ_FILTER;
import static com.express.util.Constants.RESULT_FILTER;
import static com.express.util.Constants.STATUS;
import static com.express.util.Constants.TITLE;
import static com.express.util.Constants.USER_FILTER;

/**
 * Represents Rest controller of OnlineInterview
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/interviews")
public class OnlineInterviewController {

	private final OnlineInterviewService onlineInterviewService;

	private GenericResponse<OnlineInterview> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of OnlineInterviewController
	 * 
	 * @param onlineInterviewService the service of online interview
	 */
	@Autowired
	public OnlineInterviewController(OnlineInterviewService onlineInterviewService) {
		this.onlineInterviewService = onlineInterviewService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<OnlineInterview> objectResponse) {
		this.objectResponse = objectResponse;
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
	 * Gets the list of all OnlineInterviews by page
	 * 
	 * @param pageable pagination information
	 * @return list of all onlineInterviews by page
	 */
	@GetMapping
	public MappingJacksonValue getInterviews(Pageable pageable) {
		Page<OnlineInterview> interviewList = onlineInterviewService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the interview object
		SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT, STATUS,
				COMMENTS, APPLICATION, ASSIGNEDQUIZZES);
		// Filter the assigned quiz interview object
		SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
				ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the application object
		SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, PROFIL);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
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
	 * Gets one onlineInterview by his id
	 * 
	 * @param id onlineInterview id
	 * @return onlineInterviews with the input id
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getInterviewById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with question object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(onlineInterviewService.findById(id));
			/** Filtering data to send **/
			// Filter the interview object
			SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT,
					STATUS, COMMENTS, ASSIGNEDQUIZZES, PASSEDQUIZZES, APPLICATION);
			// Filter the assigned quiz interview object
			SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
					ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
			// Filter the quiz object
			SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the application object
			SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
			// Filter the candidate object
			SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME,
					LASTNAME, EMAIL, PROFIL);
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			// Filter the result object
			SimpleBeanPropertyFilter resultFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NOTE, INTERVIEWDATE,
					QUIZ);
			FilterProvider filters = new SimpleFilterProvider().addFilter(ONLINEINTERVIEW_FILTER, interviewFilter)
					.addFilter(ASSIGNEDQUIZONLINEINTERVIEW_FILTER, assignedQuizInterviewFilter)
					.addFilter(QUIZ_FILTER, quizFilter).addFilter(APPLICATION_FILTER, applicationFilter)
					.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(USER_FILTER, userFilter)
					.addFilter(RESULT_FILTER, resultFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue interviewMapping = new MappingJacksonValue(objectResponse);
			interviewMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(interviewMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ONLINEINTERVIEW_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(ONLINEINTERVIEW_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Gets the list of assignedQuizzes by onlineInterview
	 * 
	 * @param id the id of the onlineInterview
	 * @return list of assignedQuizzes by onlineInterview
	 */
	@GetMapping(value = "/assignedQuizzes/{id}")
	public MappingJacksonValue getAssignedQuizOnlineInterview(@PathVariable(value = "id") Long id) {
		List<AssignedQuizOnlineInterview> assignedQuizzesList = onlineInterviewService.getAssignedQuizzes(id);
		/** Filtering data to send **/
		// Filter the assigned quiz interview object
		SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
				ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter(ASSIGNEDQUIZONLINEINTERVIEW_FILTER, assignedQuizInterviewFilter)
				.addFilter(QUIZ_FILTER, quizFilter).addFilter(USER_FILTER, userFilter);

		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue assignedQuizzesMapping = new MappingJacksonValue(assignedQuizzesList);
		assignedQuizzesMapping.setFilters(filters);
		return assignedQuizzesMapping;
	}

	/**
	 * Gets the list of passedQuizzes by onlineInterview
	 * 
	 * @param id the id of the onlineInterview
	 * @return list of assignedQuizzes by onlineInterview
	 */
	@GetMapping(value = "/passedQuizzes/{id}")
	public MappingJacksonValue getPassedQuizOnlineInterview(@PathVariable(value = "id") Long id) {
		List<OnlineInterviewQuizResult> passedQuizzesList = onlineInterviewService.getPassedQuizzes(id);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter resultFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NOTE, INTERVIEWDATE,
				DURATION, ONLINEINTERVIEW, QUIZ);
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the interview object
		SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, APPLICATION);
		// Filter the application object
		SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, PROFIL, STATUS);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RESULT_FILTER, resultFilter)
				.addFilter(ONLINEINTERVIEW_FILTER, interviewFilter).addFilter(APPLICATION_FILTER, applicationFilter)
				.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(QUIZ_FILTER, quizFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue passedQuizzesMapping = new MappingJacksonValue(passedQuizzesList);
		passedQuizzesMapping.setFilters(filters);
		return passedQuizzesMapping;
	}

	/**
	 * Searches for onlineInterview by one term
	 * 
	 * @param term
	 * @param pageable pagination information
	 * @return term the term to base search on it
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchOnlineInterview(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<OnlineInterview> interviewList = onlineInterviewService.simpleSearch(term, pageable);
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
				EMAIL, PROFIL);
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
