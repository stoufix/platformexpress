package com.altran.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.OnlineInterview;
import com.altran.model.OnlineInterviewQuizResult;

/**
 * Represents the interface of onlineInetview service
 * 
 * @author Moez.Barkia
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
