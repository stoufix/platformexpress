package com.express.service;

import java.util.List;

import com.express.model.Application;

/**
 * Represents the interface of application service
 * @version 1.0
 */
public interface ApplicationService {

	List<Application> findAll();

	Application findById(Long id);

}
