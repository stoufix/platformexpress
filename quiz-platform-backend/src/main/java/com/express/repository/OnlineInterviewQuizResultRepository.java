package com.express.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.express.model.OnlineInterviewQuizResult;
import com.express.model.OnlineInterviewQuizResultId;

/**
 * Represents repository of OnlineInterviewQuizResultRepository
 * 
 Hasna.fattouch
 * @version 1.0
 */
@Repository
public interface OnlineInterviewQuizResultRepository extends JpaRepository<OnlineInterviewQuizResult, Long> {

	Page<OnlineInterviewQuizResult> findAllByOrderByInterviewDateDesc(Pageable pageable);

	Boolean existsById(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

	void deleteById(OnlineInterviewQuizResultId id);

	Optional<OnlineInterviewQuizResult> findById(OnlineInterviewQuizResultId id);

}
