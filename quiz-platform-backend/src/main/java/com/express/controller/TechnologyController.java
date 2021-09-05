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

import com.express.model.Technology;
import com.express.service.TechnologyService;
import com.express.util.GenericResponse;
import com.express.util.Translator;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import static com.express.util.Constants.*;

/**
 * Represents Rest controller of technology
 * @version 1.0
 */

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/technologies")
public class TechnologyController {

	private final TechnologyService technologyService;

	private GenericResponse<Technology> objectResponse;

	private GenericResponse<String> messageResponse;

	/**
	 * Constructor of TechnologyController
	 * 
	 * @param technologyService the service of technology
	 */
	@Autowired
	public TechnologyController(TechnologyService technologyService) {
		this.technologyService = technologyService;
	}

	/**
	 * Changes valid object response object for sending data
	 * 
	 * @param validResponse generic response with role as object.
	 */
	@Autowired
	public void setObjectResponse(GenericResponse<Technology> objectResponse) {
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
	 * Gets the list of all technologies
	 * 
	 * @return list of all technologies
	 */
	@GetMapping(value = "/all")
	public MappingJacksonValue getTechnologies() {
		List<Technology> technologyList = technologyService.findAll();
		/** Filtering data to send **/
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(TECHNOLOGY_FILTER, technologyFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue userMapping = new MappingJacksonValue(technologyList);
		userMapping.setFilters(filters);
		return userMapping;
	}

	/**
	 * Gets the list of all technologies by page
	 * 
	 * @param pageable pagination information
	 * @return list of all technologies by page
	 */
	@GetMapping
	public MappingJacksonValue getTechnologiesByPage(Pageable pageable) {
		Page<Technology> technologyList = technologyService.findAllByPage(pageable);
		/** Filtering data to send **/
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(TECHNOLOGY_FILTER, technologyFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue technologyMapping = new MappingJacksonValue(technologyList);
		technologyMapping.setFilters(filters);
		return technologyMapping;
	}

	/**
	 * Gets one technology by his id
	 * 
	 * Handles NoSuchElementException if no element is present with such ID and any
	 * other exception
	 * 
	 * @param id the id of the technology
	 * @return ResponseEntity: the object or the error to display when getting
	 *         technology by id with HttpStatus status code
	 */
	@GetMapping(value = "/{id}")
	public ResponseEntity<Object> getTechnologyById(@PathVariable(value = "id") Long id) {
		try {
			// Set the response with technology object and error as false
			objectResponse.setError(false);
			objectResponse.setValue((technologyService.findById(id)));
			/** Filtering data to send **/
			SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE,
					DESCRIPTION);
			// Add filters to filter provider
			FilterProvider filters = new SimpleFilterProvider().addFilter(TECHNOLOGY_FILTER, technologyFilter);
			// Create the mapping object and set the filters to the mapping
			MappingJacksonValue technologyMapping = new MappingJacksonValue(objectResponse);
			technologyMapping.setFilters(filters);
			return ResponseEntity.status(HttpStatus.OK).body(technologyMapping);
		} catch (NoSuchElementException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_FIND_ERROR));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Creates a new technology
	 * 
	 * Handles EntityExistsException if there is already existing entity with such
	 * ID and any other exception
	 * 
	 * @param technology the technology to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         technology with HttpStatus status code
	 */
	@PostMapping
	public ResponseEntity<GenericResponse<String>> createTechnology(@Valid @RequestBody Technology technology) {
		try {
			technologyService.create(technology);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityExistsException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Updates one technology
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID and any
	 * other exception
	 * 
	 * @param id                the id of the updated technology
	 * @param technologyRequest the new technology object with the new values
	 * @return ResponseEntity: the message or the error to display after updating
	 *         technology with HttpStatus status code
	 */
	@PutMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> updateTechnology(@PathVariable(value = "id") Long id,
			@Valid @RequestBody Technology technologyRequest) {
		if (id == null) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		}
		try {
			technologyService.update(id, technologyRequest);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_UPDATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_UPDATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(messageResponse);
		}
	}

	/**
	 * Deletes one technology
	 * 
	 * Handles EntityNotFoundException if there is no entity with such ID, exception
	 * if this activity is used and any other exception
	 * 
	 * @param id the of the deleted technology
	 * @return ResponseEntity: the message or the error to display after deleting
	 *         activity with HttpStatus status code
	 */
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<GenericResponse<String>> deleteTechnologie(@PathVariable(value = "id") Long id) {
		try {
			technologyService.delete(id);
			messageResponse.setError(false);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_DELETED));
			return ResponseEntity.status(HttpStatus.OK).body(messageResponse);
		} catch (EntityNotFoundException e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_EXIST));
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(messageResponse);
		} catch (Exception e) {
			messageResponse.setError(true);
			messageResponse.setValue(Translator.toLocale(TECHNOLOGY_NOT_DELETED));
			return ResponseEntity.status(HttpStatus.IM_USED).body(messageResponse);
		}
	}

	/**
	 * Searches for technologies by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of technologies contains the input term by page
	 */
	@GetMapping(value = "/search/{term}")
	public MappingJacksonValue searchTechnologies(@PathVariable(value = "term") String term, Pageable pageable) {
		Page<Technology> technologyList = technologyService.simpleSearch(term, pageable);
		/** Filtering data to send **/
		// Filter the technology object
		SimpleBeanPropertyFilter technologyFilter = SimpleBeanPropertyFilter.filterOutAllExcept(ID, TITLE, DESCRIPTION);
		// Add filters to filter provider
		FilterProvider filters = new SimpleFilterProvider().addFilter(TECHNOLOGY_FILTER, technologyFilter);
		// Create the mapping object and set the filters to the mapping
		MappingJacksonValue technologyMapping = new MappingJacksonValue(technologyList);
		technologyMapping.setFilters(filters);
		return technologyMapping;
	}
}
