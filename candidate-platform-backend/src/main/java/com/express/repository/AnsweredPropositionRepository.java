package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.AnsweredProposition;

/**
 * Represents repository of AnsweredPropositionRepository
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Repository
public interface AnsweredPropositionRepository extends JpaRepository<AnsweredProposition, Long> {
}