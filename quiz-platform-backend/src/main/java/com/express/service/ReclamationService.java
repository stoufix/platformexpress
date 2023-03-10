package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.express.model.Reclamation;

/**
 * Represents the interface of reclamation service
 * 
 Ghassen.Soussi
 * @version 1.0
 */
public interface ReclamationService {

	List<Reclamation> findAll();

	Page<Reclamation> findAllByPage(Pageable pageable);

	Reclamation findById(Long id);

	Page<Reclamation> simpleSearch(String term, Pageable pageable);

	Reclamation create(Reclamation reclamation);

	void delete(Long id);

	void deleteAll(List<Reclamation> messages);

}