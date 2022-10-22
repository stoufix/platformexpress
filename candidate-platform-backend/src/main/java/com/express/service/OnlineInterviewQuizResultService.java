package com.express.service;

import java.util.List;

import com.express.model.OnlineInterviewQuizResult;
import com.express.model.OnlineInterviewQuizResultId;

/**
 * Represents service of OnlineInterviewQuizResultService
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
public interface OnlineInterviewQuizResultService {

	OnlineInterviewQuizResult findById(OnlineInterviewQuizResultId onlineInterviewQuizResultId);

	List<OnlineInterviewQuizResult> findAll();

	OnlineInterviewQuizResult save(OnlineInterviewQuizResult onlineInterviewQuizResult);

	OnlineInterviewQuizResult update(OnlineInterviewQuizResultId id,
			OnlineInterviewQuizResult onlineInterviewQuizResult);

}
