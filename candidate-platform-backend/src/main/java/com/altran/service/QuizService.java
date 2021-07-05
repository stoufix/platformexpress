package com.altran.service;

import com.altran.model.Quiz;

/**
 * Represents interface of quiz service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface QuizService {

	Quiz findById(Long id);

}