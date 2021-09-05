package com.express.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.express.model.Application;

/**
 * Represents repository of application
 * 

 * @version 1.0
 */
@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

}
