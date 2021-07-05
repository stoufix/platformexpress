package com.express.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.express.model.OnlineInterviewQuizResult;
import com.express.model.OnlineInterviewQuizResultId;
import com.express.service.OnlineInterviewQuizResultService;
import com.express.util.Translator;

import static com.express.util.Constants.ERROR;
import static com.express.util.Constants.MESSAGE;
import static com.express.util.Constants.OBJECT;
import static com.express.util.Constants.RESULT_FIND_ERROR;
import static com.express.util.Constants.RESULT_NOT_EXIST;
import static com.express.util.Constants.RESULT_EXIST;
import static com.express.util.Constants.RESULT_NOT_CREATED;
import static com.express.util.Constants.RESULT_CREATED;

/**
 * Represents Rest controller of OnlineInterviewQuizResult which permit to add,
 * find by id and gets all result of quiz On line Interview
 * 
 * @author Hasna.Fattouch
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/onlineInterviewQuizResult")
public class OnlineInterviewQuizResultController {

	private final OnlineInterviewQuizResultService onlineInterviewQuizResultService;

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
	 * Gets the list of all onlineInterviewQuizResult
	 * 
	 * @return list of all onlineInterviewQuizResult
	 */
	@GetMapping(value = "/all")
	public List<OnlineInterviewQuizResult> getAllResults() {
		return onlineInterviewQuizResultService.findAll();
	}

	/**
	 * Creates a new onlineInterviewQuizResult and all of his passed question
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID,
	 * EntityExistsException if there is already existing entity with such ID and
	 * any other exception
	 * 
	 * @param onlineInterviewQuizResult objects
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> createQuizResult(
			@RequestBody OnlineInterviewQuizResult onlineInterviewQuizResult) {
		Map<String, Object> response = new HashMap<>();
		try {
			onlineInterviewQuizResultService.save(onlineInterviewQuizResult);
			response.put(ERROR, false);
			response.put(MESSAGE, Translator.toLocale(RESULT_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (EntityNotFoundException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RESULT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		} catch (EntityExistsException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RESULT_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		} catch (Exception e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RESULT_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
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
	@GetMapping(value = "/{id}")
	public ResponseEntity<Map<String, Object>> getOnlineInterviewQuizResultById(
			@RequestBody OnlineInterviewQuizResultId id) {
		Map<String, Object> response = new HashMap<>();
		try {
			OnlineInterviewQuizResult onlineInterviewQuizResult = onlineInterviewQuizResultService.findById(id);
			response.put(ERROR, false);
			response.put(OBJECT, onlineInterviewQuizResult);
			return ResponseEntity.status(HttpStatus.OK).body(response);

		} catch (NoSuchElementException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RESULT_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} catch (Exception e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RESULT_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}
