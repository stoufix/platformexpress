package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.express.model.Reclamation;
import com.express.repository.ReclamationRepository;
import com.express.service.ReclamationService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of reclamation service
 * 
 Ghassen.Soussi
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
	 * Gets the list of all reclamations
	 * 
	 * @return list of all reclamations
	 */
	@Override
	public List<Reclamation> findAll() {
		return reclamationRepository.findAllByOrderByCreatedAtDesc();
	}

	/**
	 * Gets the list of all reclamations by page
	 * 
	 * @param pageable pagination information
	 * @return list of all reclamations by page
	 */
	public Page<Reclamation> findAllByPage(Pageable pageable) {
		return reclamationRepository.findAllByOrderByCreatedAtDesc(pageable);
	}

	/**
	 * Gets one reclamation by his id
	 * 
	 * @param id the id of the reclamation
	 * @return reclamation object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Reclamation findById(Long id) {
		Optional<Reclamation> reclamation = reclamationRepository.findById(id);
		if (reclamation.isPresent()) {
			// set state of the reclamation as consulted when first time open it
			if (!reclamation.get().isConsulted()) {
				reclamation.get().setConsulted(true);
				reclamationRepository.save(reclamation.get());
			}
			return reclamation.get();
		} else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
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
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		// set state of the reclamation as not consulted when creating
		reclamation.setConsulted(false);
		return reclamationRepository.save(reclamation);
	}

	/**
	 * Deletes one reclamation
	 * 
	 * @param id the id of the deleted reclamation
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !reclamationRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		reclamationRepository.deleteById(id);
	}

	/**
	 * Deletes all reclamations
	 * 
	 * @param reclamations list of reclamations to delete
	 */
	@Override
	public void deleteAll(List<Reclamation> reclamations) {
		for (Reclamation reclamation : reclamations) {
			reclamationRepository.deleteById(reclamation.getId());
		}
	}

	/**
	 * Searches for reclamations by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of reclamations contains the input term by page
	 */
	@Override
	public Page<Reclamation> simpleSearch(String term, Pageable pageable) {
		return reclamationRepository.simpleSearch(term, pageable);
	}

}
