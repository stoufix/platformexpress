package com.altran.service;

import java.util.List;

import com.altran.model.Privilege;

/**
 * Represents the interface of privilege service
 * 
 * @author Hasna.Fattouch
 * @version 1.0
 */
public interface PrivilegeService {

	List<Privilege> findAll();

}