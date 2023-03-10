package com.express.service;

import java.util.List;

import com.express.model.Application;

/**
 * Represents interface of application service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public interface ApplicationService {

	Application findById(Long id);

	List<Application> findByStatus(String status);

}
