package com.altran.service;

import java.util.List;

import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.OnlineInterview;
import com.altran.model.OnlineInterviewQuizResult;

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
