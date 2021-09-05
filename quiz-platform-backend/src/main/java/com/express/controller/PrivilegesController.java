package com.express.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.express.model.Privilege;
import com.express.service.PrivilegeService;

/**
 * Represents Rest controller of privilege
 * @version 1.0
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping(value = "/privileges")
public class PrivilegesController {

	private final PrivilegeService privilegeService;

	/**
	 * Constructor of PrivilegesController
	 * 
	 * @param privilegeService the service of privilege
	 */
	@Autowired
	public PrivilegesController(PrivilegeService privilegeService) {
		this.privilegeService = privilegeService;
	}

	/**
	 * Gets the list of all privileges
	 * 
	 * @return list of all privileges
	 */
	@GetMapping
	public List<Privilege> getPrivileges() {
		return privilegeService.findAll();
	}
}