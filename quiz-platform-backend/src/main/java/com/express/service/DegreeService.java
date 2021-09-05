package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.express.model.Degree;

/**
 * Represents the interface of degree service
 * 

 * @version 1.0
 */
public interface DegreeService {

	List<Degree> findAll();

	Page<Degree> findAllByPage(Pageable pageable);

	Degree findById(Long id);

	Page<Degree> simpleSearch(String term, Pageable pageable);

	Degree create(Degree degree);

	Degree update(Long id, Degree degree);

	void delete(Long id);

}
