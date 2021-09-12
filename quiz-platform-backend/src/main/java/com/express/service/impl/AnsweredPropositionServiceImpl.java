package com.express.service.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.express.model.AnsweredProposition;
import com.express.repository.AnsweredPropositionRepository;

import com.express.service.AnsweredPropositionService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of AnsweredPropositionS service
 * 
 Hasna.fattouch
 * @version 1.0
 */
@Service
public class AnsweredPropositionServiceImpl implements AnsweredPropositionService {

	private final AnsweredPropositionRepository answeredPropositionRepository;

	/**
	 * Constructor of AnsweredPropositionServiceImpl
	 * 
	 * @param answeredPropositionRepository the repository of answered proposition
	 */
	@Autowired
	public AnsweredPropositionServiceImpl(AnsweredPropositionRepository answeredPropositionRepository) {
		this.answeredPropositionRepository = answeredPropositionRepository;
	}

	/**
	 * Creates a new answered proposition
	 * 
	 * @param answeredProposition the answered proposition to create
	 * @return the created answered proposition
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public AnsweredProposition create(AnsweredProposition answeredProposition) {
		if (answeredProposition.getId() != null
				&& answeredPropositionRepository.existsById(answeredProposition.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return answeredPropositionRepository.save(answeredProposition);
	}

	/**
	 * Updates one answered proposition
	 * 
	 * @param id                  the id of the answered proposition
	 * @param answeredProposition the new answered proposition object with the new
	 *                            values
	 * @return the updated answered proposition
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * 
	 */
	@Override
	public AnsweredProposition update(Long id, AnsweredProposition answeredProposition) {
		if (id != null && !answeredPropositionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		answeredProposition.setId(id);
		return answeredPropositionRepository.save(answeredProposition);
	}

	@Override
	public void delete(Long id) {
		if (id != null && !answeredPropositionRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		answeredPropositionRepository.deleteById(id);
	}

}
