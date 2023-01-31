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
import com.express.model.Degree;
import com.express.repository.DegreeRepository;
import com.express.service.DegreeService;
import static com.express.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of degree service
 * 

 * @version 1.0
 */
@Service
public class DegreeServiceImpl implements DegreeService {

	private final DegreeRepository degreeRepository;

	/**
	 * Constructor of Degree
	 * 
	 * @param degreeRepository the repository of Degree
	 * 
	 */
	@Autowired
	public DegreeServiceImpl(DegreeRepository degreeRepository) {
		this.degreeRepository = degreeRepository;
	}

	/**
	 * Gets the list of all degrees sorted by title
	 * 
	 * @return list of all degrees sorted by title
	 * 
	 */
	@Override
	public List<Degree> findAll() {
		return degreeRepository.findAllByOrderByTitleAsc();
	}

	/**
	 * Gets the list of all degrees by page and sorted by title
	 * 
	 * @param pageable pagination information
	 * @return list of all degrees by page and sorted by title
	 * 
	 */
	public Page<Degree> findAllByPage(Pageable pageable) {
		return degreeRepository.findAllByOrderByTitleAsc(pageable);
	}

	/**
	 * Gets one degree by his id
	 * 
	 * @param id the id of the degree
	 * @return degree object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 * 
	 */
	@Override
	public Degree findById(Long id) {
		Optional<Degree> degree = degreeRepository.findById(id);
		if (degree.isPresent())
			return degree.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}

	/**
	 * Creates a new degree
	 * 
	 * @param degree the degree to create
	 * @return the created degree
	 * @throws EntityExistsException if there is already existing entity with such
	 *                               ID
	 */
	@Override
	public Degree create(Degree degree) {
		if (degree.getId() != null && degreeRepository.existsById(degree.getId())) {
			throw new EntityExistsException(NO_ENTITY_DB);
		}
		return degreeRepository.save(degree);
	}

	/**
	 * Updates one degree
	 * 
	 * @param id     the id of the degree
	 * @param degree the new degree object with the new values
	 * @return the updated degree
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * 
	 */
	@Override
	public Degree update(Long id, Degree degree) {
		if (id != null && !degreeRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		degree.setId(id);
		return degreeRepository.save(degree);
	}

	/**
	 * Deletes one degree
	 * 
	 * @param id the of the deleted degree
	 * @throws EntityNotFoundException if there is no entity with such ID
	 * 
	 */
	@Override
	public void delete(Long id) {
		if (id != null && !degreeRepository.existsById(id)) {
			throw new EntityNotFoundException(NO_ENTITY_DB);
		}
		degreeRepository.deleteById(id);
	}

	/**
	 * Searches for degrees by one term
	 * 
	 * @param term     the term to base search on it
	 * @param pageable pagination information
	 * @return list of degrees contains the input term by page
	 * 
	 */
	@Override
	public Page<Degree> simpleSearch(String term, Pageable pageable) {
		return degreeRepository.simpleSearch(term, pageable);
	}

}