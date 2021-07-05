package com.express.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.express.model.Reclamation;

/**
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface ReclamationRepository extends JpaRepository<Reclamation, Long> {

	@Query("SELECT t FROM Reclamation t where t.subject LIKE CONCAT('%', :term, '%')  OR t.body LIKE CONCAT('%', :term, '%') ")
	public Page<Reclamation> searchAll(@Param("term") String term, Pageable pageable);
}
