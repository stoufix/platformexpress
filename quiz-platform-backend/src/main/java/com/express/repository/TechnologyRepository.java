package com.express.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.express.model.Technology;

/**
 * Represents repository of technology
 * 
 Maha.BSaid
 * @version 1.0
 */
@Repository
public interface TechnologyRepository extends JpaRepository<Technology, Long> {

	List<Technology> findAllByOrderByTitleAsc();

	Page<Technology> findAllByOrderByTitleAsc(Pageable pageable);

	@Query("SELECT t FROM Technology t where LOWER (t.title) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.description) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<Technology> simpleSearch(@Param("term") String term, Pageable pageable);

}
