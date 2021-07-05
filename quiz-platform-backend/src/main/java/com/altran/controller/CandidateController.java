package com.altran.controller;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.model.Candidate;
import com.altran.service.CandidateService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.altran.util.Constants.*;

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

	private GenericResponse<Candidate> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of CandidateController
	 * 
	 * @param candidateService the service of candidate
	 */
	@Autowired
	public CandidateController(CandidateService candidateService) {
		this.candidateService = candidateService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Candidate> objectResponse) {
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
	 * Gets the list of all candidates by page
	 * 
	 * @param pageable pagination information
	 * @return list of all candidates by page
	 * 
	 */
	@GetMapping
	public MappingJacksonValue findCandidatesByPage(Pageable pageable) {
		Page<Candidate> candidateList = candidateService.findAllByPage(pageable);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVATED, PROFIL, STATUS);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(CANDIDATE_FILTER, candidateFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue candidateMapping = new MappingJacksonValue(candidateList);
		candidateMapping.setFilters(filters);
		return candidateMapping;
	}

	/**
	 * Gets one candidate by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the candidate
	 * @return ResponseEntity: the object or the error to display when getting
	 *         candidate by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getCandidateById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(candidateService.findById(id));
			// Filter the candidate object
			SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME,
					LASTNAME, USERNAME, EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, GRADUATIONYEAR, UNIVERSITY, EXPERIENCE,
					AVAILABILITYDATE, ACTIVATED, PROFIL, STATUS, COMMENTS, CREATEDAT, APPLICATIONS);
			// Filter the application object
			SimpleBeanPropertyFilter applicationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT,
					STATUS, ONLINEINTERVIEW);
			// Filter the interview object
			SimpleBeanPropertyFilter interviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, CREATEDAT,
					STATUS, ASSIGNEDQUIZZES, PASSEDQUIZZES);
			// Filter the assigned quiz interview object
			SimpleBeanPropertyFilter assignedQuizInterviewFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID,
					ASSIGNEDAT, PASSINGAT, QUIZ, ASSIGNEDBY);
			// Filter the assigned quiz interview object
			SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
					DURATION, DEGREE, TECHNOLOGY);
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the technology object
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			// Filter the result object
			SimpleBeanPropertyFilter resultFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, NOTE, INTERVIEWDATE,
					DURATION, QUIZ);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(CANDIDATE_FILTER, candidateFilter)
					.addFilter(APPLICATION_FILTER, applicationFilter).addFilter(ONLINEINTERVIEW_FILTER, interviewFilter)
					.addFilter(ASSIGNEDQUIZONLINEINTERVIEW_FILTER, assignedQuizInterviewFilter)
					.addFilter(CANDIDATE_FILTER, candidateFilter).addFilter(DEGREE_FILTER, degreeFilter)
					.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(USER_FILTER, userFilter)
					.addFilter(RESULT_FILTER, resultFilter).addFilter(QUIZ_FILTER, quizFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue candidateMapping = new MappingJacksonValue(objectResponse);
			candidateMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(candidateMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(CANDIDATE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(CANDIDATE_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Searches for candidates by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of candidates contains the input term by page
	 * 
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchCandidates(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Candidate> candidateList = candidateService.simpleSearch(term, pageable);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVATED, PROFIL, STATUS);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(CANDIDATE_FILTER, candidateFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue candidateMapping = new MappingJacksonValue(candidateList);
		candidateMapping.setFilters(filters);
		return candidateMapping;
	}

	/**
	 * Changes the state of candidate account(activated: true/false)
	 * 
	 * @param id the of the updated candidate
	 * @return ResponseEntity: the message or the error to display after updating
	 *         candidate with HttpStatus status code
	 * 
	 */
	@PutMapping(value = "/{id}/account")
	public ResponseEntity<GenericResponse<String>> changeAccountState(@PathVariable(value = "id") Long id) {
		try {
			Candidate updatedCandidate = candidateService.changeAccountState(id);
			messageResponse.setError(false);
			if (updatedCandidate.isActivated())
				messageResponse.setValue(Translator.toLocale(ACTIVATE_ACCOUNT));
			else
				messageResponse.setValue(Translator.toLocale(DISABLE_ACCOUNT));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(CANDIDATE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(CANDIDATE_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}
}