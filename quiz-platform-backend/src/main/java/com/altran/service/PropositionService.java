package com.altran.service;

import java.util.List;

import com.altran.model.Proposition;

/**
 * Represents the interface of proposition service
 * 
 * @author Amal.Smaoui
 * @version 1.0
 */
public interface PropositionService {

	List<Proposition> findAll();

	Proposition findById(Long id);

	Proposition create(Proposition proposition);

	Proposition update(Long id, Proposition proposition);

	void delete(Long id);

}
