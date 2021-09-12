package com.express.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.AssignedQuizOnlineInterviewId;
import com.express.model.OnlineInterview;

/**
 * Represents the interface of AssignedQuizOnlineInterview service
 * 
 Maha.BSaid
 * @version 1.0
 */
public interface AssignedQuizOnlineInterviewService {

	AssignedQuizOnlineInterview findById(AssignedQuizOnlineInterviewId quizOnlineInterviewId);

	Page<OnlineInterview> findOnLineInterviewsWithAssignedQuizzes(Pageable pageable);

	AssignedQuizOnlineInterview create(AssignedQuizOnlineInterview quizOnlineInterview);

	AssignedQuizOnlineInterview update(Long quizId, Long onlineInterviewId,
			AssignedQuizOnlineInterview quizOnlineInterview);

	void assignQuizzesToOneOnlineInterview(OnlineInterview onlineInterviews);

	void assignQuizzes(List<OnlineInterview> onlineInterviews);

	void updateAssignedQuizzes(Long id, OnlineInterview onlineInterviews);

	void deleteAssignedQuiz(AssignedQuizOnlineInterviewId assignquizOnlineInterviewId);

	void deleteAssignedQuizzes(Long onlineInterview);

	Page<OnlineInterview> simpleSearch(String term, Pageable pageable);

}
