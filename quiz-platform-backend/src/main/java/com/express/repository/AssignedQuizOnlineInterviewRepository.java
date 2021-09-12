package com.express.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.AssignedQuizOnlineInterviewId;
import com.express.model.OnlineInterview;

/**
 * Represents repository of assignedQuizOnlineInterview
 * 
 Maha.BSaid
 * @version 1.0
 */
@Repository
public interface AssignedQuizOnlineInterviewRepository extends JpaRepository<AssignedQuizOnlineInterview, Long> {

	@Query("SELECT DISTINCT t FROM OnlineInterview t join t.assignedQuizzes aq where LOWER (CONCAT(t.application.candidate.lastName,' ',t.application.candidate.firstName)) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (CONCAT(t.application.candidate.firstName,' ',t.application.candidate.lastName)) LIKE CONCAT('%', LOWER ( :term ), '%')"
			+ "OR LOWER (aq.quiz.title) LIKE CONCAT('%', LOWER ( :term ), '%')")
	public Page<OnlineInterview> simpleSearch(@Param("term") String term, Pageable pageable);

	Optional<AssignedQuizOnlineInterview> findById(AssignedQuizOnlineInterviewId id);

	Boolean existsById(AssignedQuizOnlineInterviewId id);

	void deleteById(AssignedQuizOnlineInterviewId id);

	void deleteByOnlineInterviewId(Long onlineInterviewId);

}
