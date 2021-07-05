package com.altran.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.Proposition;

/**
 * Represents repository of proposition
 * 
 * @author Amal.Smaoui
 * @version 1.0
 */
@Repository
public interface PropositionRepository extends JpaRepository<Proposition, Long> {

	List<Proposition> findByQuestionId(Long questionId);

}
