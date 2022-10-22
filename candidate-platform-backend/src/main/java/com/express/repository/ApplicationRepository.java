package com.express.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.Application;

/**
 * Represents application's repository
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

	List<Application> findByStatus(String status);
}
