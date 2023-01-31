package com.express.controller;

import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

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

import com.express.model.Question;
import com.express.model.filter.QuestionFilter;
import com.express.service.QuestionService;
import com.express.util.GenericResponse;
import com.express.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.express.util.Constants.*;

/**
 * Represents Rest controller of question
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/questions")
public class QuestionController {

	private final QuestionService questionService;

	private GenericResponse<Question> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of QuestionController
	 * 
	 * @param questionService the service of question
	 */
	@Autowired
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Question> objectResponse) {
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
	 * Gets all visible question without activity criteria *
	 * 
	 * @param username the user name of connected user
	 * @param pageable pagination information
	 * 
	 * @return list of visible questions
	 */
	// @PreAuthorize("hasAuthority('SHOW_QUESTION')")
	@GetMapping(value = "/allVisibleQuestions/{username}")
	public MappingJacksonValue getAllVisibleQuestions(@PathVariable(value = "username") String username,
			Pageable pageable) {
		Page<Question> questionList = questionService.findAllVisibleQuestions(username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets all visible question with quiz technology and degree and without
	 * activity criteria
	 * 
	 * @param username     the user name of connected user
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * 
	 * @return list of visible questions
	 */
	@GetMapping(value = "/allVisibleQuestions/{username}/technology/{technologyId}/degree/{degreeId}")
	public MappingJacksonValue getAllVisibleQuestionsByTechnologyAndDegree(
			@PathVariable(value = "username") String username, @PathVariable(value = "technologyId") Long technologyId,
			@PathVariable(value = "degreeId") Long degreeId, Pageable pageable) {
		Page<Question> questionList = questionService.findAllVisibleQuestionsByTechnologyAndDegree(technologyId,
				degreeId, username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets visible questions of activity related to authenticated user
	 * 
	 * @return list of visible questions
	 */
	@GetMapping(value = "/allVisibleQuestionsByAuthActivity/{username}")
	public MappingJacksonValue findAllVisibleQuestionsByAuthActivity(@PathVariable(value = "username") String username,
			Pageable pageable) {
		Page<Question> questionList = questionService.findAllVisibleQuestionsByAuthActivity(username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets visible questions of activity related to authenticated user by activity,
	 * quiz technology and degree
	 * 
	 * @param username     the user name of connected user
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * 
	 * @return list of visible questions
	 */
	@GetMapping(value = "/allVisibleQuestionsByAuthActivity/{username}/technology/{technologyId}/degree/{degreeId}")
	public MappingJacksonValue findAllVisibleQuestionsByAuthActivityTechnologyAndDegree(
			@PathVariable(value = "username") String username, @PathVariable(value = "technologyId") Long technologyId,
			@PathVariable(value = "degreeId") Long degreeId, Pageable pageable) {
		Page<Question> questionList = questionService
				.findAllVisibleQuestionsByAuthActivityTechnologyAndDegree(technologyId, degreeId, username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets the list of all questions by page
	 * 
	 * @param pageable pagination information
	 * @return list of all questions by page
	 */
	@GetMapping
	public MappingJacksonValue getQuestionsByPage(Pageable pageable) {
		Page<Question> questionList = questionService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets the list of questions by there Technologies and degrees
	 * 
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @return list of questions object with the same technology and degree
	 */
	@GetMapping(value = "/technology/{technologyId}/degree/{degreeId}")
	public MappingJacksonValue getQuestionsByTechnologyAndDegree(
			@PathVariable(value = "technologyId") Long technologyId, @PathVariable(value = "degreeId") Long degreeId) {
		List<Question> questionList = questionService.findByTechnologyAndDegree(technologyId, degreeId);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets the list of questions by there technologies and degrees by page
	 * 
	 * @param technologyId the id of the technology
	 * @param degreeId     the id of the degree
	 * @param pageable     pagination information
	 * @return list of questions object with the same technology and degree by page
	 */
	@GetMapping(value = "pages/technology/{technologyId}/degree/{degreeId}")
	public MappingJacksonValue getQuestionsByTechnologyAndDegreeByPage(
			@PathVariable(value = "technologyId") Long technologyId, @PathVariable(value = "degreeId") Long degreeId,
			Pageable pageable) {
		Page<Question> questionList = questionService.findByPageByTechnologyAndDegree(technologyId, degreeId, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Gets one question by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the question
	 * @return ResponseEntity: the object or the error to display when getting
	 *         question by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getQuestionById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with question object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(questionService.findById(id));
			/** Filtering data to send **/
			// Filter the question object
			SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
					CREATEDAT, "updatedAt", SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the technology object
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
					.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
					.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue questionMapping = new MappingJacksonValue(objectResponse);
			questionMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(questionMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new question and all of his propositions
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param question the question to create
	 * @return ResponseEntity: the object, the message or the error to display after
	 *         creating question with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<Object> createQuestion(@Valid @RequestBody Question question) {
		try {
			Question createdQuestion = questionService.create(question);
			objectResponse.setError(false);
			objectResponse.setValue(createdQuestion);
			/** Filtering data to send **/
			// Filter the question object
			SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
					CREATEDAT, "updatedAt", SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the technology object
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
					.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
					.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue questionMapping = new MappingJacksonValue(objectResponse);
			questionMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.CREATED).body(questionMapping);

		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one question
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param id              the id of the updated question
	 * @param questionRequest the new question object with the new values
	 * @return ResponseEntity: the object, the message or the error to display after
	 *         updating question with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateQuestion(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Question questionRequest) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			questionService.update(id, questionRequest);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(QUESTION_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one question
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this question is used and any other exception
	 * 
	 * @param id the id of the deleted question
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         question with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteQuestion(@PathVariable(value = "id") Long id) {
		try {
			if (questionService.delete(id)) {
				messageResponse.setError(false);
				messageResponse.setValue(Translator.toLocale(QUESTION_DELETED));
				return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
			} else {
				messageResponse.setError(true);
				messageResponse.setValue(Translator.toLocale(QUESTION_ASSIGNED_DELETE));
				return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
			}
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for questions by one term without activity
	 * 
	 * @param term     the term to base search on it
	 * @param username the user name of connected user
	 * @param pageable pagination information
	 * @return list of questions contains the input term by page
	 */
	@GetMapping(value = "/searchWithoutActivity/{username}/{term}")
	public MappingJacksonValue searchQuestionsWithoutActivity(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Question> questionList = questionService.simpleSearchWithoutActivity(username, term, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions by one term with activity
	 * 
	 * Handles Exception if this activity is used and any other exception
	 * 
	 * @param term     the term to base search on it
	 * @param username the user name of connected user
	 * @param pageable pagination information
	 * @return ResponseEntity: the list of questions contains the input term by
	 *         page, the error to display after if there is a problem
	 */
	@GetMapping(value = "/searchWithActivity/{username}/{term}")
	public MappingJacksonValue searchQuestionsWithActivity(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Question> questionList = questionService.simpleSearchWithActivity(username, term, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;

	}

	/**
	 * Searches for questions by one term without activity, with technology and
	 * degree
	 * 
	 * @param term       the term to base search on it
	 * @param username   the user name of connected user
	 * @param technology technology's id
	 * @param degree     degree's id
	 * @param pageable   pagination information
	 * @return list of questions contains the input term by page
	 */
	@GetMapping(value = "/searchWithoutActivity/{username}/{term}/{technology}/{degree}")
	public MappingJacksonValue searchQuestionsWithoutActivityWithTechnologyAndDegree(
			@PathVariable(value = "username") String username, @PathVariable(value = "term") String term,
			@PathVariable(value = "technology") Long technology, @PathVariable(value = "degree") Long degree,
			Pageable pageable) {
		Page<Question> questionList = questionService.simpleSearchWithoutActivityWithTechnologyAndDegree(username, term,
				technology, degree, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions by one term with activity
	 * 
	 * Handles Exception if this activity is used and any other exception
	 * 
	 * @param term       the term to base search on it
	 * @param username   the user name of connected user
	 * @param technology technology's id
	 * @param degree     degree's id
	 * @param pageable   pagination information
	 * @return ResponseEntity: the list of questions contains the input term by
	 *         page, the error to display after if there is a problem
	 */
	@GetMapping(value = "/searchWithActivity/{username}/{term}/{technology}/{degree}")
	public MappingJacksonValue searchQuestionsWithActivityTechnologyAndDegree(
			@PathVariable(value = "username") String username, @PathVariable(value = "term") String term,
			@PathVariable(value = "technology") Long technology, @PathVariable(value = "degree") Long degree,
			Pageable pageable) {
		Page<Question> questionList = questionService.simpleSearchWithActivityTechnologyAndDegree(username, term,
				technology, degree, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions by one term by user
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of questions contains the input term by page
	 */
	@GetMapping(value = "/searchByUser/{username}/{term}")
	public MappingJacksonValue searchQuestionsByUser(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Question> questionList = questionService.simpleSearchByUser(username, term, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions by multiple terms
	 * 
	 * @param technology technology of the question
	 * @param degree     degree of the question
	 * @param activity   activity of the question
	 * @param name       name of the question
	 * @param pagination information
	 * @return list of questions contains the input terms by page
	 */
	@PostMapping(value = "/search/advancedSearch")
	public MappingJacksonValue advancedSearchWithoutActivity(@Valid @RequestBody QuestionFilter question,
			Pageable pageable) {
		Page<Question> questionList = questionService.advancedSearch(question, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions by multiple terms
	 * 
	 * @param technology technology of the question
	 * @param degree     degree of the question
	 * @param activity   activity of the question
	 * @param name       name of the question
	 * @param pagination information
	 * @return list of questions contains the input terms by page
	 */
	@PostMapping(value = "/search/advancedSearch/{username}")
	public MappingJacksonValue advancedSearchWithActivity(@Valid @RequestBody QuestionFilter question,
			@PathVariable(value = "username") String username, Pageable pageable) {
		Page<Question> questionList = questionService.advancedSearchWithActivity(question, username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Searches for questions-list-by-user by multiple terms
	 * 
	 * @param technology technology of the question
	 * @param degree     degree of the question
	 * @param activity   activity of the question
	 * @param name       name of the question
	 * @param pagination information
	 * @return list of questions contains the input terms by page
	 */
	@PostMapping(value = "/search/advancedSearchByUser/{username}")
	public MappingJacksonValue advancedSearchByUser(@Valid @RequestBody QuestionFilter question,
			@PathVariable(value = "username") String username, Pageable pageable) {
		Page<Question> questionList = questionService.advancedSearchByUser(question, username, pageable);
		/** Filtering data to send **/
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				CREATEDAT, SHARED, CREATEDBY, TECHNOLOGY, DEGREE, ACTIVITY, PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUESTION_FILTER, questionFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter).addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue questionMapping = new MappingJacksonValue(questionList);
		questionMapping.setFilters(filters);
		return questionMapping;
	}

	/**
	 * Changes question visibility for other users(true/false)
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the of the updated question
	 * @return ResponseEntity: the message or the error to display after updating
	 *         question with HttpStatus status code
	 */
	@PutMapping(value = "/{id}/shared")
	public ResponseEntity<GenericResponse<String>> changeShared(@PathVariable(value = "id") Long id) {
		try {
			Question question = questionService.changeShared(id);
			if (question.getShared()) {
				messageResponse.setError(false);
				messageResponse.setValue(Translator.toLocale(QUESTION_VISIBLE));
			} else {
				messageResponse.setError(false);
				messageResponse.setValue(Translator.toLocale(QUESTION_INVISIBLE));
			}
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUESTION_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}
}
