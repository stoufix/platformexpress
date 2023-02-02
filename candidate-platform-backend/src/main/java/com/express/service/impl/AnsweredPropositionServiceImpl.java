package com.express.service.impl;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.express.model.AnsweredProposition;
import com.express.repository.AnsweredPropositionRepository;

import com.express.service.AnsweredPropositionService;


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
	 * Creates a new degree
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
			throw new EntityExistsException("There is already existing entity with such ID in the database.");
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
			throw new EntityNotFoundException("\"There is no entity with such ID in the database.");
		}
		answeredProposition.setId(id);
		return answeredPropositionRepository.save(answeredProposition);
	}

}
