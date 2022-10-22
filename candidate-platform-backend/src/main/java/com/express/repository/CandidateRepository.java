package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.Candidate;

/**
 * Represents candidate's repository
 * 
 * @author Dhouha.Bejaoui
 * @version 1.0
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Candidate findByUsername(String username);
}
