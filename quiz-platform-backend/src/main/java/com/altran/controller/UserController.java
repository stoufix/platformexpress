package com.altran.controller;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altran.model.LoginUser;
import com.altran.model.Question;
import com.altran.model.Quiz;
import com.altran.model.User;
import com.altran.service.UserService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.altran.util.Constants.*;

/**
 * Represents Rest controller of user
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/users")
public class UserController {

	private final UserService userService;

	private GenericResponse<User> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of UserController
	 * 
	 * @param userService the service of user
	 */
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<User> objectResponse) {
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
	 * 
	 * Gets the list of all users
	 * 
	 * @return list of all users
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getUsers() {
		List<User> userList = userService.findAll();
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets the list of all users by page
	 * 
	 * @param pageable pagination information
	 * @return list of all users by page
	 */
	@GetMapping
	public MappingJacksonValue getUsersByPage(Authentication auth, Pageable pageable) {
		Page<User> userList = userService.findAllByPage(auth, pageable);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVATED, ROLE, ACTIVITY);
		// Filter the role of the user object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
				.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets authenticated user
	 * 
	 * @param Authentication object
	 * @return authenticated user
	 */
	@GetMapping(value = "/auth")
	public MappingJacksonValue getAuth(Authentication auth) {
		User authUser = userService.getAuthenticatedUser(auth);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVITY, USERNAME, ACTIVATED, ROLE);
		// Filter the role of the user object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, "privileges");
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
				.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(authUser);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets one user by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the user
	 * @return ResponseEntity: the object or the error to display when getting user
	 *         by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getUserById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(userService.findById(id));
			/** Filtering data to send **/
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
					EMAIL, ADDRESS, PHONENUMBER, USERNAME, BIRTHDATE, ACTIVATED, ROLE, ACTIVITY);
			// Filter the role of the user object
			SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
					.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue userMapping = new MappingJacksonValue(objectResponse);
			userMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(userMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Gets one user by his userName
	 * 
	 * Handles UsernameNotFoundException if there is no user with such userName and
	 * any other exception
	 * 
	 * @param userName the userName of the user
	 * @return ResponseEntity: the object or the error to display when getting user
	 *         by userName with HttpStatus status code
	 */
	@GetMapping(value = "/username/{userName}")
	public ResponseEntity<Object> getUserByUserName(@PathVariable(value = "userName") String userName) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(userService.findByUsername(userName));
			/** Filtering data to send **/
			// Filter the user object
			SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
					EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVITY, USERNAME, ACTIVATED, ROLE);
			// Filter the role of the user object
			SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, "privileges");
			// Filter the activity object
			SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
					.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue userMapping = new MappingJacksonValue(objectResponse);
			userMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(userMapping);
		} catch (UsernameNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new user
	 * 
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID, AddressException if sender's address mail is not correct,
	 * MessagingException, IOException and any other exception
	 * 
	 * @param user the user to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         user with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createUser(@RequestBody User user) {
		try {
			userService.create(user);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_WRONG_MAIL_ADDRESS));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (AddressException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_WRONG_MAIL_ADDRESS));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (MessagingException | IOException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_MAIL_NOT_SEND));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param user the new user object with the new values
	 * @param id   the id of the updated user
	 * @return ResponseEntity: the message or the error to display after updating
	 *         user with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateUser(@RequestBody @Valid User user,
			@PathVariable(value = "id") Long id) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			userService.update(user, id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this activity is used and any other exception
	 * 
	 * @param id the of the deleted user
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         user with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteUser(@PathVariable(value = "id") Long id) {
		try {
			userService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Changes account state of a user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param id the id of the updated user
	 * @return ResponseEntity: the object or the error to display after updating
	 *         user with HttpStatus status code
	 */
	@PutMapping(value = "/{id}/account")
	public ResponseEntity<GenericResponse<String>> changeAccountState(@PathVariable(value = "id") Long id) {
		try {
			User updatedUser = userService.changeAccountState(id);
			messageResponse.setError(false);
			if (updatedUser.isActivated())
				messageResponse.setValue(Translator.toLocale(ACTIVATE_ACCOUNT));
			else
				messageResponse.setValue(Translator.toLocale(DISABLE_ACCOUNT));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Gets the created question list for one user
	 * 
	 * @param id       the of the user
	 * @param pageable pagination information
	 * @return the list of created questions by this user returned by page
	 */
	@GetMapping(value = "/{id}/createdquestions")
	public MappingJacksonValue getCreatedQuestions(@PathVariable(value = "id") Long id, Pageable pageable) {
		Page<Question> questionList = userService.getCreatedQuestions(id, pageable);
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
	 * Gets the created quizzes list for one user
	 * 
	 * @param id       the of the user
	 * @param pageable pagination information *
	 * @return the list of created quizzes by this user returned by page
	 */
	@GetMapping(value = "/{id}/createdquizzes")
	public MappingJacksonValue getCreatedQuiz(@PathVariable(value = "id") Long id, Pageable pageable) {
		Page<Quiz> quizList = userService.getCreatedQuizzes(id, pageable);
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
	 * Gets all userNames of all users
	 * 
	 * @return list of userNames
	 */
	@GetMapping(value = "/usernames")
	public List<String> getUsernames() {
		return userService.findAllUsernames();
	}

	/**
	 * Gets the list of from all users
	 * 
	 * @return list of mails
	 */
	@GetMapping(value = "/emails")
	public List<String> getEmails() {
		return userService.findAllEmails();
	}

	/**
	 * Updates login information for user
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param loginInfo
	 * @param loginInfo the loginInfo object with the new userName and password
	 *                  values
	 * @return loginInfo object with the new login information
	 */
	@PutMapping(value = "/password/{id}")
	public ResponseEntity<GenericResponse<String>> updatePassword(@RequestBody LoginUser loginInfo,
			@PathVariable(value = "id") Long id) {
		try {
			userService.changeLoginInfo(id, loginInfo);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(USER_PASS_UPDATED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(USER_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for activities by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of activities contains the input term by page
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchUsers(Authentication auth, @PathVariable(value = "term") String term,
			Pageable pageable) {
		Page<User> userList = userService.simpleSearch(auth, term, pageable);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter userFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, BIRTHDATE, ACTIVATED, ROLE, ACTIVITY);
		// Filter the role of the user object
		SimpleBeanPropertyFilter roleFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Filter the activity object
		SimpleBeanPropertyFilter activityFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(USER_FILTER, userFilter)
				.addFilter(ROLE_FILTER, roleFilter).addFilter(ACTIVITY_FILTER, activityFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(userList);
		userMapping.setFilters(filters);
		return userMapping;
	}

}
