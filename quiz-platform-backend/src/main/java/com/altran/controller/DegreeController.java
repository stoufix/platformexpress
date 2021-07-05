package com.altran.controller;

import java.util.HashMap;
import java.util.List;
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

import com.altran.model.Degree;
import com.altran.service.DegreeService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import static com.altran.util.Constants.*;

/**
 * Represents Rest controller of degree
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/degrees")
public class DegreeController {

	private final DegreeService degreeService;

	private GenericResponse<Degree> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of DegreeController
	 * 
	 * @param degreeService the service of degree
	 */
	@Autowired
	public DegreeController(DegreeService degreeService) {
		this.degreeService = degreeService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Degree> objectResponse) {
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
	 * Gets the list of all degrees
	 * 
	 * @return list of all degrees
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getDegrees() {
		List<Degree> degreeList = degreeService.findAll();
		/** Filtering data to send **/
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(DEGREE_FILTER, degreeFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue degreeMapping = new MappingJacksonValue(degreeList);
		degreeMapping.setFilters(filters);
		return degreeMapping;
	}

	/**
	 * Gets the list of all degrees by page
	 * 
	 * @param pageable pagination information
	 * @return list of all degrees by page
	 * 
	 */
	@GetMapping
	public MappingJacksonValue getDegreesByPage(Pageable pageable) {
		Page<Degree> degreeList = degreeService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(DEGREE_FILTER, degreeFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue degreeMapping = new MappingJacksonValue(degreeList);
		degreeMapping.setFilters(filters);
		return degreeMapping;
	}

	/**
	 * Gets one degree by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the degree
	 * @return ResponseEntity: the object or the error to display when getting
	 *         degree by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getDegreeById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with activity object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(degreeService.findById(id));
			/** Filtering data to send **/
			// Filter the degree object
			SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(DEGREE_FILTER, degreeFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue degreeMapping = new MappingJacksonValue(objectResponse);
			degreeMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(degreeMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new degree
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param degree the degree to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         degree with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createDegree(@Valid @RequestBody Degree degree) {
		try {
			degreeService.create(degree);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(DEGREE_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one degree
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param id            the id of the degree
	 * @param degreeRequest the new degree object with the new values
	 * @return ResponseEntity: the message or the error to display after updating
	 *         degree with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateDegree(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Degree degreeRequest) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			degreeService.update(id, degreeRequest);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(DEGREE_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one degree
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this activity is used and any other exception
	 * 
	 * @param id the of the deleted degree
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         degree with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteDegree(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		response.put(ERROR, false);
		try {
			degreeService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(DEGREE_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(DEGREE_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for degrees by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of degrees contains the input term by page
	 * 
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchDegrees(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Degree> degreeList = degreeService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the degree object
		SimpleBeanPropertyFilter degreeFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(DEGREE_FILTER, degreeFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue degreeMapping = new MappingJacksonValue(degreeList);
		degreeMapping.setFilters(filters);
		return degreeMapping;
	}

}