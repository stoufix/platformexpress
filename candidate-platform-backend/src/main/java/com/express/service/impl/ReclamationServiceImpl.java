package com.express.service.impl;

import javax.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.express.model.Reclamation;
import com.express.repository.ReclamationRepository;
import com.express.service.ReclamationService;

/**
 * Represents implementation of reclamation service
 * 
 * @author Ghassen.Soussi
 * @version 1.0
 */
@Service
public class ReclamationServiceImpl implements ReclamationService {

	private final ReclamationRepository reclamationRepository;

	/**
	 * Constructor of ReclamationServiceImpl
	 * 
	 * @param reclamationRepository the repository of reclamation
	 */
	@Autowired
	public ReclamationServiceImpl(ReclamationRepository reclamationRepository) {
		this.reclamationRepository = reclamationRepository;
	}

	/**
	 * Creates a new reclamation
	 * 
	 * @param reclamation the reclamation to create
	 * @return the created reclamation
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Reclamation create(Reclamation reclamation) {
		if (reclamation.getId() != null && reclamationRepository.existsById(reclamation.getId())) {
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
		}
		// set state of the reclamation as not consulted when creating
		reclamation.setConsulted(false);
		return reclamationRepository.save(reclamation);
	}

}
