package com.altran.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.altran.model.OnlineInterviewQuizResult;
import com.altran.model.OnlineInterviewQuizResultId;

/**
 * Represents repository of OnlineInterviewQuizResultRepository
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Repository
public interface OnlineInterviewQuizResultRepository extends JpaRepository<OnlineInterviewQuizResult, Long> {
	Boolean existsById(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

	Optional<OnlineInterviewQuizResult> findById(OnlineInterviewQuizResultId id);

}
