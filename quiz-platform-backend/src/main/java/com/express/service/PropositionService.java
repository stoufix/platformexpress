package com.express.service;

import java.util.List;

import com.express.model.Proposition;

/**
 * Represents the interface of proposition service
 * 
 Amal.Smaoui
 * @version 1.0
 */
public interface PropositionService {

	List<Proposition> findAll();

	Proposition findById(Long id);

	Proposition create(Proposition proposition);

	Proposition update(Long id, Proposition proposition);

	void delete(Long id);

}
