package com.altran.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.altran.model.Reclamation;
import com.altran.service.ReclamationService;
import com.altran.util.GenericResponse;
import com.altran.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.altran.util.Constants.*;

/**
 * Represents Rest controller of reclamation
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/reclamations")
public class ReclamationController {

	private final ReclamationService reclamationsService;

	private GenericResponse<Reclamation> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of ReclamationController
	 * 
	 * @param reclamationsService the service of reclamation
	 */
	@Autowired
	public ReclamationController(ReclamationService reclamationsService) {
		this.reclamationsService = reclamationsService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Reclamation> objectResponse) {
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
	 * Gets the list of all reclamations by page
	 * 
	 * @param pageable pagination information
	 * @return list of all reclamations by page
	 */
	@GetMapping
	public MappingJacksonValue findAllByPage(Pageable pageable) {
		Page<Reclamation> reclamationList = reclamationsService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter reclamationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, SUBJECT, BODY,
				DESCRIPTION, CONSULTED, PHONENUMBER, CANDIDATE, CREATEDAT);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, PROFIL, STATUS);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RECLAMATION_FILTER, reclamationFilter)
				.addFilter(CANDIDATE_FILTER, candidateFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue reclamationMapping = new MappingJacksonValue(reclamationList);
		reclamationMapping.setFilters(filters);
		return reclamationMapping;
	}

	/**
	 * Gets one reclamation by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the reclamation
	 * @return ResponseEntity: the object or the error to display when getting
	 *         reclamation by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getReclamationById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with user object and error as false
			objectResponse.setError(false);
			objectResponse.setValue(reclamationsService.findById(id));
			/** Filtering data to send **/
			// Filter the user object
			SimpleBeanPropertyFilter reclamationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, SUBJECT, BODY,
					DESCRIPTION, CONSULTED, PHONENUMBER, CANDIDATE, CREATEDAT);
			// Filter the candidate object
			SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME,
					LASTNAME, EMAIL, ADDRESS, PHONENUMBER, PROFIL, STATUS);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(RECLAMATION_FILTER, reclamationFilter)
					.addFilter(CANDIDATE_FILTER, candidateFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue reclamationMapping = new MappingJacksonValue(objectResponse);
			reclamationMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(reclamationMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one reclamation
	 * 
	 * Handles exception if this activity is used and any other exception
	 * 
	 * @param id the id of the deleted reclamation
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         activity with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteReclamation(@PathVariable(value = "id") Long id) {
		Map<String, Object> response = new HashMap<>();
		response.put(ERROR, false);
		try {
			reclamationsService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Deletes all reclamations
	 * 
	 * Handles exception if this activity is used and any other exception
	 * 
	 * @param reclamations list of reclamations to delete
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         reclamations with HttpStatus status code
	 */
	@PostMapping(value = "/deleteMultipleReclamations")
	public ResponseEntity<GenericResponse<String>> deleteListOfReclamations(
			@RequestBody List<Reclamation> reclamations) {
		try {
			reclamationsService.deleteAll(reclamations);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(RECLAMATION_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for reclamations by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of reclamations contains the input term by page
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchAll(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Reclamation> reclamationList = reclamationsService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the user object
		SimpleBeanPropertyFilter reclamationFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, SUBJECT, BODY,
				DESCRIPTION, CONSULTED, PHONENUMBER, CANDIDATE);
		// Filter the candidate object
		SimpleBeanPropertyFilter candidateFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, FIRSTNAME, LASTNAME,
				EMAIL, ADDRESS, PHONENUMBER, PROFIL, STATUS);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(RECLAMATION_FILTER, reclamationFilter)
				.addFilter(CANDIDATE_FILTER, candidateFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue reclamationMapping = new MappingJacksonValue(reclamationList);
		reclamationMapping.setFilters(filters);
		return reclamationMapping;

	}

}