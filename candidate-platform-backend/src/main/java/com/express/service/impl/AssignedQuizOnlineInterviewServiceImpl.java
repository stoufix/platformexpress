package com.express.service.impl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.express.model.AssignedQuizOnlineInterviewId;
import com.express.repository.AssignedQuizOnlineInterviewRepository;
import com.express.service.AssignedQuizOnlineInterviewService;
@Service
public class AssignedQuizOnlineInterviewServiceImpl implements AssignedQuizOnlineInterviewService {
	private final AssignedQuizOnlineInterviewRepository assignedQuizOnlineInterviewRepository;

	@Autowired
	public AssignedQuizOnlineInterviewServiceImpl(
			AssignedQuizOnlineInterviewRepository assignedQuizOnlineInterviewRepository) {
		this.assignedQuizOnlineInterviewRepository = assignedQuizOnlineInterviewRepository;
	}

	@Override
	@Transactional
	public void delete(AssignedQuizOnlineInterviewId id) {
		
		assignedQuizOnlineInterviewRepository.deleteById(id);
		
	}
	
	

}
