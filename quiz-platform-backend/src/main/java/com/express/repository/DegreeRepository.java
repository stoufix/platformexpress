package com.express.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.express.model.Degree;

/**
 * Represents repository of degree
 * 

 * @version 1.0
 */
@Repository
public interface DegreeRepository extends JpaRepository<Degree, Long> {

	List<Degree> findAllByOrderByTitleAsc();

	Page<Degree> findAllByOrderByTitleAsc(Pageable pageable);

	@Query("SELECT t FROM Degree t where LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Degree> simpleSearch(@Param("term") String term, Pageable pageable);
}
