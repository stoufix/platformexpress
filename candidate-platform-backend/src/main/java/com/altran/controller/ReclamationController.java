package com.altran.controller;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.altran.model.Reclamation;
import com.altran.service.ReclamationService;
import com.altran.util.Translator;
import static com.altran.util.Constants.ERROR;
import static com.altran.util.Constants.MESSAGE;
import static com.altran.util.Constants.RECLAMATION_EXIST;
import static com.altran.util.Constants.RECLAMATION_CREATED;
import static com.altran.util.Constants.RECLAMATION_NOT_CREATED;

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
	 * Creates a new reclamation
	 * 
	 * @param reclamation the reclamation to create
	 * @return ResponseEntity: the message or the error to display after creating
	 *         reclamation with HttpStatus status code
	 * 
	 *         Handles EntityExistsException if there is already existing entity
	 *         with such ID and any other exception
	 */
	@PostMapping
	public ResponseEntity<Map<String, Object>> createReclamation(@RequestBody Reclamation reclamation) {
		Map<String, Object> response = new HashMap<>();
		try {
			reclamationsService.create(reclamation);
			response.put(ERROR, false);
			response.put(MESSAGE, Translator.toLocale(RECLAMATION_CREATED));
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} catch (EntityExistsException e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RECLAMATION_EXIST));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		} catch (Exception e) {
			response.put(ERROR, true);
			response.put(MESSAGE, Translator.toLocale(RECLAMATION_NOT_CREATED));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

}