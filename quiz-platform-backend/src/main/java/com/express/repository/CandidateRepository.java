package com.express.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.express.model.Candidate;

/**
 * Represents repository of candidate
 * 

 * @version 1.0
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Page<Candidate> findAllByOrderByFirstNameAsc(Pageable pageable);

	Candidate findByUsername(String username);

	@Query("SELECT t FROM Candidate t where  LOWER (t.firstName) LIKE CONCAT('%', LOWER ( :term ), '%')  OR  LOWER (t.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') "
			+ "OR  LOWER (t.email) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.address) LIKE CONCAT('%', LOWER ( :term ), '%') OR  LOWER (t.phoneNumber) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Candidate> simpleSearch(@Param("term") String term, Pageable pageable);

}
