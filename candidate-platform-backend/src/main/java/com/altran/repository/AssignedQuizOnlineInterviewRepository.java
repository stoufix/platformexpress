package com.altran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.AssignedQuizOnlineInterview;
import com.altran.model.AssignedQuizOnlineInterviewId;
@Repository
public interface AssignedQuizOnlineInterviewRepository  extends JpaRepository<AssignedQuizOnlineInterview, Long> {

	void deleteById(AssignedQuizOnlineInterviewId id);}


