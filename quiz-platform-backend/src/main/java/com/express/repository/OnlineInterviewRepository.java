package com.express.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.express.model.OnlineInterview;

/**
 * Represents repository of onlineInterview
 * 

 * @version 1.0
 */
@Repository
public interface OnlineInterviewRepository extends JpaRepository<OnlineInterview, Long> {

	List<OnlineInterview> findAllByOrderByCreatedAtDesc();

	Page<OnlineInterview> findAllByOrderByCreatedAtDesc(Pageable pageable);

	@Query("SELECT t FROM OnlineInterview t where LOWER (t.comments) LIKE CONCAT('%', LOWER ( :term ), '%')  OR LOWER (t.application.candidate.firstName) LIKE CONCAT('%', LOWER ( :term ), '%') OR LOWER (t.application.candidate.lastName) LIKE CONCAT('%', LOWER ( :term ), '%') ")
	public Page<OnlineInterview> simpleSearch(@Param("term") String term, Pageable pageable);

}
