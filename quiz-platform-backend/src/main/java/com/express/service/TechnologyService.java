package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.express.model.Technology;

/**
 * Represents the interface of technology service
 * @version 1.0
 */
public interface TechnologyService {

	List<Technology> findAll();

	Page<Technology> findAllByPage(Pageable pageable);

	Technology findById(Long id);

	Page<Technology> simpleSearch(String term, Pageable pageable);

	Technology create(Technology technology);

	Technology update(Long id, Technology technology);

	void delete(Long id);

}