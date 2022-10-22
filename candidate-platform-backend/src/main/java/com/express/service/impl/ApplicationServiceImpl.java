package com.express.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import com.express.model.Application;
import com.express.repository.ApplicationRepository;
import com.express.service.ApplicationService;

/**
 * Represents implementation of application service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
public class ApplicationServiceImpl implements ApplicationService {

	private final ApplicationRepository applicationRepository;

	/**
	 * Constructor of ApplicationServiceImpl
	 * 
	 * @param applicationRepository the repository of application
	 */
	@Autowired
	public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
		this.applicationRepository = applicationRepository;
	}

	/**
	 * Gets one application by his id
	 * 
	 * @param id the id of the application
	 * @return application object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Application findById(Long id) {
		Optional<Application> application = applicationRepository.findById(id);
		if (!application.isPresent()) {
			throw new NoSuchElementException();
		}
		return application.get();
	}

	/**
	 * Gets list of application by one status
	 * 
	 * @param status the status of applications
	 * @return list of application with the input status
	 */
	@Override
	public List<Application> findByStatus(String status) {
		return applicationRepository.findByStatus(status);
	}

}
