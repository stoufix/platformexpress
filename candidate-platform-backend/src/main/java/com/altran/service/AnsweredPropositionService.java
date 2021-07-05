package com.altran.service;

import com.altran.model.AnsweredProposition;

/**
 * Represents service of AnsweredPropositionService
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
public interface AnsweredPropositionService {
	AnsweredProposition create(AnsweredProposition answeredProposition);

	AnsweredProposition update(Long id, AnsweredProposition answeredProposition);
}
