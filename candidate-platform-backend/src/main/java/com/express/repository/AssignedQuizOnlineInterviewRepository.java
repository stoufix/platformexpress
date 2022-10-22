package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.AssignedQuizOnlineInterview;
import com.express.model.AssignedQuizOnlineInterviewId;
@Repository
public interface AssignedQuizOnlineInterviewRepository  extends JpaRepository<AssignedQuizOnlineInterview, Long> {

	void deleteById(AssignedQuizOnlineInterviewId id);}


