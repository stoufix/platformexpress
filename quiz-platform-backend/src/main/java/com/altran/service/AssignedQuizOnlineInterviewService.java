package com.altran.service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.AssignedQuizOnlineInterviewId;
import com.altran.model.OnlineInterview;

/**
 * Represents the interface of AssignedQuizOnlineInterview service
 * 
 * @author Maha.BSaid
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
