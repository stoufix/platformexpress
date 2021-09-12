package com.express.controller;

import java.util.HashMap;
import java.util.Map;
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

import com.express.model.Quiz;
import com.express.model.filter.QuizFilter;
import com.express.service.QuizService;
import com.express.util.GenericResponse;
import com.express.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.express.util.Constants.*;

/**
 * Represents Rest controller of quiz
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/quizzes")
public class QuizController {

	private final QuizService quizService;

	private GenericResponse<Quiz> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of QuizController
	 * 
	 * @param quizService the service of quiz
	 */
	@Autowired
	public QuizController(QuizService quizService) {
		this.quizService = quizService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Quiz> objectResponse) {
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
	 * Gets the list of all quizzes with minimum information by page
	 * 
	 * @return list of all quizzes
	 */
	@GetMapping(value = "/min-info")
	public MappingJacksonValue getQuizzesWithMinInfoByPage(Pageable pageable) {
		Page<Quiz> quizList = quizService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DURATION,
				TECHNOLOGY, DEGREE, ACTIVITY);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(DEGREE_FILTER, degreeFilter).addFilter(TECHNOLOGY_FILTER, technologyFilter)
				.addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Gets the list of all quizzes by page
	 * 
	 * @param pageable pagination information
	 * @return list of all quizzes by page
	 */
	@GetMapping
	public MappingJacksonValue getQuizzesByPage(Pageable pageable) {
		Page<Quiz> quizList = quizService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * gets all visible quizzes without activity criteria
	 * 
	 * @param pageable pagination information
	 * @return list of visible quizzes by page
	 */
	@GetMapping(value = "/allVisibleQuizzes/{username}")
	public MappingJacksonValue getVisibleQuizzes(@PathVariable(value = "username") String username, Pageable pageable) {
		Page<Quiz> quizList = quizService.findVisibleByPage(username, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * gets visible quizzes of activity related to authenticated user
	 * 
	 * @param pageable pagination information
	 * @return list of visible quizzes by page
	 * @return ResponseEntity: list of visible quizzes by page, the error to display
	 *         after if there is a problem
	 */
	@GetMapping(value = "/allVisibleQuizzesByAuthActivity/{username}")
	public MappingJacksonValue getVisibleQuizzesByAuthActivity(@PathVariable(value = "username") String username,
			Pageable pageable) {
		Page<Quiz> quizList = quizService.findVisibleByAuthActivity(username, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);

		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Searches for quizzes by multiple terms
	 * 
	 * @param technology technology of the quiz
	 * @param degree     degree of the quiz
	 * @param activity   activity of the quiz
	 * @param name       name of the quiz
	 * @param pagination information
	 * @return list of quizzes contains the input terms by page
	 */
	@PostMapping(value = "/search/advancedQuizSearch")
	public MappingJacksonValue advancedSearch(@Valid @RequestBody QuizFilter quiz, Pageable pageable) {
		Page<Quiz> quizList = quizService.advancedSearch(quiz, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
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
	@PostMapping(value = "/search/advancedSearchWithActivity/{username}")
	public MappingJacksonValue advancedSearchWithActivity(@Valid @RequestBody QuizFilter quiz,
			@PathVariable(value = "username") String username, Pageable pageable) {
		Page<Quiz> quizList = quizService.advancedSearchWithActivity(quiz, username, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Searches for quizzes-list-by-user by multiple terms
	 * 
	 * @param technology technology of the quiz
	 * @param degree     degree of the quiz
	 * @param activity   activity of the quiz
	 * @param name       name of the quiz
	 * @param pagination information
	 * @return list of quizzes contains the input terms by page
	 */
	@PostMapping(value = "/search/advancedSearchByUser/{username}")
	public MappingJacksonValue advancedSearchByUser(@Valid @RequestBody QuizFilter quiz,
			@PathVariable(value = "username") String username, Pageable pageable) {
		Page<Quiz> quizList = quizService.advancedSearchByUser(quiz, username, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Gets one quiz by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the quiz
	 * @return ResponseEntity: the object or the error to display when getting quiz
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getQuizById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with question object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(quizService.findById(id));
			// Filter the quiz object
			SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
					CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY, QUESTIONS);
			// Filter the question object
			SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
					PROPOSITIONS);
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the technology object
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
					.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
					.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
					.addFilter(USER_FILTER, userFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue quizMapping = new MappingJacksonValue(objectResponse);
			quizMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(quizMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new quiz without questions
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param quiz the quiz to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         activity with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<Object> createQuiz(@Valid @RequestBody Quiz quiz) {
		try {
			Quiz createdQuiz = quizService.create(quiz);
			// Set the response with question object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(createdQuiz);
			// Filter the quiz object
			SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue quizMapping = new MappingJacksonValue(objectResponse);
			quizMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(quizMapping);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Update quiz and Adds questions(QuizQuestion model) to the quiz
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param the         id of the updated quiz
	 * @param quizRequest the new quiz object with the new values
	 * @return ResponseEntity: the message or the error to display after updating
	 *         activity with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateQuiz(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Quiz quizRequest) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			quizService.update(id, quizRequest);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(QUIZ_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one quiz
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this activity is used and any other exception
	 * 
	 * @param id the id of the deleted quiz
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         activity with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteQuiz(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		response.put(ERROR, false);
		try {
			quizService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(QUIZ_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for quizzes by one term without activity
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of questions contains the input term by page
	 */
	@GetMapping(value = "/searchWithoutActivity/{username}/{term}")
	public MappingJacksonValue searchQuizzesWithoutActivity(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Quiz> quizList = quizService.simpleSearchWithoutActivity(username, term, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Searches for quizzes by one term with activity
	 * 
	 * Handles Exception if this activity is used and any other exception
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return ResponseEntity: the list of questions contains the input term by
	 *         page, the error to display after if there is a problem
	 */
	@GetMapping(value = "/searchWithActivity/{username}/{term}")
	public MappingJacksonValue searchQuizzesWithActivity(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Quiz> quizList = quizService.simpleSearchWithActivity(username, term, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Searches for quizzes by one term by user
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of questions contains the input term by page
	 */
	@GetMapping(value = "/searchByUser/{username}/{term}")
	public MappingJacksonValue searchQuizzesByUser(@PathVariable(value = "username") String username,
			@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Quiz> quizList = quizService.simpleSearchByUser(username, term, pageable);
		/** Filtering data to send **/
		// Filter the quiz object
		SimpleBeanPropertyFilter quizFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION,
				CREATEDAT, DURATION, TECHNOLOGY, DEGREE, ACTIVITY, SHARED, CREATEDBY);
		// Filter the question object
		SimpleBeanPropertyFilter questionFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, DESCRIPTION,
				PROPOSITIONS);
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(QUIZ_FILTER, quizFilter)
				.addFilter(QUESTION_FILTER, questionFilter).addFilter(DEGREE_FILTER, degreeFilter)
				.addFilter(TECHNOLOGY_FILTER, technologyFilter).addFilter(ACTIVITY_FILTER, activityFilter)
				.addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue quizMapping = new MappingJacksonValue(quizList);
		quizMapping.setFilters(filters);
		return quizMapping;
	}

	/**
	 * Changes quiz visibility for other users(true/false)
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the of the updated quiz
	 * @return ResponseEntity: the message or the error to display after updating
	 *         quiz with HttpStatus status code
	 */
	@PutMapping(value = "/{id}/shared")
	public ResponseEntity<GenericResponse<String>> changeShared(@PathVariable(value = "id") Long id) {
		try {
			Quiz quiz = quizService.changeShared(id);
			if (quiz.getShared()) {
				messageResponse.setError(false);
				messageResponse.setValue(Translator.toLocale(QUIZ_VISIBLE));
			} else {
				messageResponse.setError(false);
				messageResponse.setValue(Translator.toLocale(QUIZ_INVISIBLE));
			}
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(QUIZ_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}

	}
}
