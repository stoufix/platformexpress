package com.altran.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.altran.model.OnlineInterviewQuizResult;
import com.altran.model.OnlineInterviewQuizResultId;

/**
 * Represents implementation of OnlineInterviewQuizResult service
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
public interface OnlineInterviewQuizResultService {

	OnlineInterviewQuizResult findById(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

	Page<OnlineInterviewQuizResult> findAllByPage(Pageable pageable);

	OnlineInterviewQuizResult update(OnlineInterviewQuizResultId id,
			OnlineInterviewQuizResult onlineInterviewQuizResult);

	void delete(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

}
