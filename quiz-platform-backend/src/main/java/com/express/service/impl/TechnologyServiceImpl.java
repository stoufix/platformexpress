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
import com.express.model.Technology;
import com.express.repository.TechnologyRepository;
import com.express.service.TechnologyService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of technology service
 * 
 Maha.BSaid
 * @version 1.0
 */
@Service
public class TechnologyServiceImpl implements TechnologyService {

	private final TechnologyRepository technologyRepository;

	/**
	 * Constructor of TechnologyServiceImpl
	 * 
	 * @param technologyRepository the repository of technology
	 */
	@Autowired
	public TechnologyServiceImpl(TechnologyRepository technologyRepository) {
		this.technologyRepository = technologyRepository;
	}

	/**
	 * Gets the list of all technologies sorted by title
	 * 
	 * @return list of all technologies sorted by title
	 */
	@Override
	public List<Technology> findAll() {
		return technologyRepository.findAllByOrderByTitleAsc();
	}

	/**
	 * Gets the list of all technologies by page and sorted by title
	 * 
	 * @return list of all technologies by page and sorted by title
	 */
	public Page<Technology> findAllByPage(Pageable pageable) {
		return technologyRepository.findAllByOrderByTitleAsc(pageable);
	}

	/**
	 * Gets one technology by his id
	 * 
	 * @param id the id of the technology
	 * @return technology object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Technology findById(Long id) {
		Optional<Technology> technology = technologyRepository.findById(id);
		if (technology.isPresent())
			return technology.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new technology
	 * 
	 * @param technology the technology to create
	 * @return the created technology
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Technology create(Technology technology) {
		if (technology.getId() != null && technologyRepository.existsById(technology.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return technologyRepository.save(technology);
	}

	/**
	 * Updates one technology
	 * 
	 * @param id         the id of the updated technology
	 * @param technology the new technology object with the new values
	 * @return the updated technology
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public Technology update(Long id, Technology technology) {
		if (id != null && !technologyRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		technology.setId(id);
		return technologyRepository.save(technology);

	}

	/**
	 * Deletes one technology
	 * 
	 * @param id the of the deleted technology
	 * @throws EntityNotFoundException if there is no entity with such ID
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !technologyRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		technologyRepository.deleteById(id);
	}

	/**
	 * Searches for technologies by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of technologies contains the input term by page
	 */
	@Override
	public Page<Technology> simpleSearch(String term, Pageable pageable) {
		return technologyRepository.simpleSearch(term, pageable);
	}

}
