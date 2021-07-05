package com.express.service;

import java.util.List;

import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;

/**
 * Represent the interface of OnlineInterview service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface OnlineInterviewService {

	List<OnlineInterview> findAll();

	OnlineInterview findById(Long id);

	OnlineInterview update(Long id, OnlineInterview onlineInterview);

	List<AssignedQuizOnlineInterview> getAssignedQuizzes(Long id);

	List<OnlineInterviewQuizResult> getPassedQuizzes(Long id);
}
