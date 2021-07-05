package com.altran.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altran.model.Application;

/**
 * Represents repository of application
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
