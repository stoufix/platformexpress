package com.express.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.express.model.Reclamation;

/**
 * Represents repository of reclamation
 * 

 * @version 1.0
 */
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

	@Query("SELECT t FROM Reclamation t where LOWER(CONCAT(t.candidate.firstName,' ',t.candidate.lastName)) LIKE CONCAT('%', LOWER ( :term ), '%')  OR  LOWER (t.subject) LIKE CONCAT('%', LOWER ( :term ), '%') ORDER BY createdAt asc ")
	public Page<Reclamation> simpleSearch(@Param("term") String term, Pageable pageable);
	public List<Reclamation> findAllByOrderByCreatedAtDesc();
	public Page<Reclamation> findAllByOrderByCreatedAtDesc(Pageable pageable);

}
