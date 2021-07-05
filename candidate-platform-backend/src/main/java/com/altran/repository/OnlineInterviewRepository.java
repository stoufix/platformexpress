package com.altran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.OnlineInterview;

/**
 * Represents onlineInterview's repository
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface OnlineInterviewRepository extends JpaRepository<OnlineInterview, Long> {

}
