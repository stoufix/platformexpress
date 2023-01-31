package com.express.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.express.model.OnlineInterviewQuizResult;
import com.express.model.OnlineInterviewQuizResultId;

/**
 * Represents implementation of OnlineInterviewQuizResult service
 * 
 Hasna.fattouch
 * @version 1.0
 */
public interface OnlineInterviewQuizResultService {

	OnlineInterviewQuizResult findById(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

	Page<OnlineInterviewQuizResult> findAllByPage(Pageable pageable);

	OnlineInterviewQuizResult update(OnlineInterviewQuizResultId id,
			OnlineInterviewQuizResult onlineInterviewQuizResult);

	void delete(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

}
