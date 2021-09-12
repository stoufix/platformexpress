package com.express.service;

import com.express.model.AnsweredProposition;

/**
 * Represents service of answeredProposition
 * 
 Hasna.fattouch
 * @version 1.0
 */
public interface AnsweredPropositionService {
	AnsweredProposition create(AnsweredProposition answeredProposition);

	AnsweredProposition update(Long id, AnsweredProposition answeredProposition);

	void delete(Long id);

}
