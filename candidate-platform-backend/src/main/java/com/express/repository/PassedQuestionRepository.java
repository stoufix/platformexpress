package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.PassedQuestion;

/**
 * Represents repository of PassedQuestionRepository
 * 
 * @author Hasna.fattouch
 * @version 1.0
 */
@Repository
public interface PassedQuestionRepository extends JpaRepository<PassedQuestion, Long> {

}