package com.altran.controller;

import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.model.OnlineInterviewQuizResult;
import com.altran.model.OnlineInterviewQuizResultId;
import com.altran.service.OnlineInterviewQuizResultService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.altran.util.Constants.*;

/**
 * Represents Rest controller of OnlineInterviewQuizResult
 * 
 * @author Hasna.Fattouch
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/onlineInterviewQuizResult")
public class OnlineInterviewQuizResultController {

	private final OnlineInterviewQuizResultService onlineInterviewQuizResultService;

	private GenericResponse<OnlineInterviewQuizResult> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of OnlineInterviewQuizResultController
	 * 
	 * @param onlineInterviewQuizResultService the service of online interview quiz
	 *                                         result
	 */
	@Autowired
	public OnlineInterviewQuizResultController(OnlineInterviewQuizResultService onlineInterviewQuizResultService) {
		this.onlineInterviewQuizResultService = onlineInterviewQuizResultService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<OnlineInterviewQuizResult> objectResponse) {
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
	 * Gets the list of all onlineInterviewQuizResult by page
	 * 
	 * @param pageable pagination information
	 * @return list of all onlineInterviewQuizResult by page
	 */
	@GetMapping
	public MappingJacksonValue getResultsByPage(Pageable pageable) {
		Page<OnlineInterviewQuizResult> resultList = onlineInterviewQuizResultService.findAllByPage(pageable);
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
		MappingJacksonValue resultMapping = new MappingJacksonValue(resultList);
		resultMapping.setFilters(filters);
		return resultMapping;
	}

	/**
	 * Gets one onlineInterviewQuizResult by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the onlineInterviewQuizResult
	 * @return ResponseEntity: the object or the error to display when getting
	 *         question by id with HttpStatus status code
	 */
	@PostMapping(value = "/id")
	public ResponseEntity<Object> getResultById(@RequestBody OnlineInterviewQuizResultId id) {
		try {
			// Set the response with question object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(onlineInterviewQuizResultService.findById(id));
			/** Filtering data to send **/
			// Filter the quiz object
			SimpleBeanPropertyFilter resultFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NOTE, INTERVIEWDATE,
					DURATION, ONLINEINTERVIEW, QUIZ, PASSEDQUESTIONS);
			// Filter the quiz object
			SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, TECHNOLOGY,
					DEGREE, ACTIVITY, DURATION);
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the technology object
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the interview object
			SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, APPLICATION);
			// Filter the application object
			SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CANDIDATE);
			// Filter the candidate object
			SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME,
					LASTNAME, EMAIL, PROFIL, STATUS);
			// Filter the passed question object
			SimpleBeanPropertyFilter passedQuestionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
					ANSWEREDPROPOSITION);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(RESULT_FILTER, resultFilter)
					.addFilter(ONLINEINTERVIEW_FILTER, interviewFilter).addFilter(APPLICATION_FILTER, applicationFilter)
					.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(QUIZ_FILTER, quizFilter)
					.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
					.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(PASSEDQUESTION_FILTER, passedQuestionFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue resultMapping = new MappingJacksonValue(objectResponse);
			resultMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(resultMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RESULT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RESULT_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Delete quiz result by id
	 * 
	 * @param OnlineInterviewQuizResultId id of the quiz and id of the online
	 *                                    interview
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deletOnlineInterviewQuizResult(
			@RequestBody OnlineInterviewQuizResultId id) {
		onlineInterviewQuizResultService.delete(id);
		messageResponse.setError(false);
		messageResponse.setValue(Translator.toLocale(RESULT_DELETED));
		return ResponseEntity.status(HttpStatus.OK).body(messageResponse);

	}

}
