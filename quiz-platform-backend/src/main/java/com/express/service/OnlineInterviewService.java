package com.express.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.OnlineInterview;
import com.express.model.OnlineInterviewQuizResult;

/**
 * Represents the interface of onlineInetview service
 * 

 * @version 1.0
 */
public interface OnlineInterviewService {

	List<OnlineInterview> findAll();

	Page<OnlineInterview> findAllByPage(Pageable pageable);

	OnlineInterview findById(Long id);

	Page<OnlineInterview> simpleSearch(String term, Pageable pageable);

	OnlineInterview update(Long id, OnlineInterview onlineInterview);

	List<AssignedQuizOnlineInterview> getAssignedQuizzes(Long id);

	List<OnlineInterviewQuizResult> getPassedQuizzes(Long id);

	Boolean existsById(Long id);

}
