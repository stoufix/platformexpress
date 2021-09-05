package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.express.model.Proposition;
import com.express.repository.PropositionRepository;
import com.express.service.PropositionService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of proposition service
 * 
 Amal.Smaoui
 * @version 1.0
 */
@Service
public class PropositionServiceImpl implements PropositionService {

	private final PropositionRepository propositionRepository;

	/**
	 * Constructor of PropositionServiceImpl
	 * 
	 * @param propositionRepository the repository of proposition
	 */
	@Autowired
	public PropositionServiceImpl(PropositionRepository propositionRepository) {
		this.propositionRepository = propositionRepository;
	}

	/**
	 * Gets the list of all propositions
	 * 
	 * @return list of all propositions
	 */
	@Override
	public List<Proposition> findAll() {
		return propositionRepository.findAll();
	}

	/**
	 * Gets one proposition by his id
	 * 
	 * @param id the id of the proposition
	 * @return proposition object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 * 
	 */
	@Override
	public Proposition findById(Long id) {
		Optional<Proposition> proposition = propositionRepository.findById(id);
		if (proposition.isPresent())
			return proposition.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Gets the list of propositions of one question
	 * 
	 * @param id the id of the question
	 * @return list of all propositions of the question
	 */
	public List<Proposition> findByQuestionId(Long questionId) {
		return propositionRepository.findByQuestionId(questionId);
	}

	/**
	 * Creates a new proposition
	 * 
	 * @param proposition the proposition to create
	 * @return the created proposition
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Proposition create(Proposition proposition) {
		if (proposition.getId() != null && propositionRepository.existsById(proposition.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return propositionRepository.save(proposition);
	}

	/**
	 * Updates one proposition
	 * 
	 * @param id          the id of the proposition
	 * @param proposition the new proposition object with the new values
	 * @return the updated proposition
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Proposition update(Long id, Proposition proposition) {
		if (id != null && !propositionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		proposition.setId(id);
		return propositionRepository.save(proposition);
	}

	/**
	 * Deletes one proposition
	 * 
	 * @param id: the of the deleted proposition
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !propositionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		propositionRepository.deleteById(id);
	}

}
