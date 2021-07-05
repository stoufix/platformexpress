package com.altran.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altran.model.Application;
import com.altran.repository.ApplicationRepository;
import com.altran.service.ApplicationService;
import static com.altran.util.Constants.NO_ENTITY_DB;

/**
 * Represents implementation of application service
 * 
 * @author Moez.Barkia
 * @version 1.0
 */
@Service
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
	 * Gets the list of all applications
	 * 
	 * @return list of all applications
	 */
	@Override
	public List<Application> findAll() {
		return applicationRepository.findAll();
	}

	/**
	 * Gets one application by his id
	 * 
	 * @param id: the id of the application
	 * @return application object with the same id
	 * @throws NoSuchElementException if no element is present with such ID
	 */
	@Override
	public Application findById(Long id) {
		Optional<Application> application = applicationRepository.findById(id);
		if (application.isPresent())
			return application.get();
		else {
			throw new NoSuchElementException(NO_ENTITY_DB);
		}
	}
}
