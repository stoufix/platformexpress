package com.express.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.express.model.Privilege;
import com.express.repository.PrivilegeRepository;
import com.express.service.PrivilegeService;

/**
 * Represents implementation of privilege service
 * 
 Hasna.Fattouch
 * @version 1.0
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

	private final PrivilegeRepository privilegeRepository;

	/**
	 * Constructor of PrivilegeServiceImpl
	 * 
	 * @param privilegeRepository the repository of privilege
	 */
	@Autowired
	public PrivilegeServiceImpl(PrivilegeRepository privilegeRepository) {
		this.privilegeRepository = privilegeRepository;
	}
	
	/**
	 * Gets the list of all privileges
	 * 
	 * @return list of all privileges
	 */
	@Override
	public List<Privilege> findAll() {
		return privilegeRepository.findAll();
	}
}